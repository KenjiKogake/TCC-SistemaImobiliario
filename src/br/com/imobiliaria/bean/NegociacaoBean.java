package br.com.imobiliaria.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.imobiliaria.dao.ApartamentoStatus;
import br.com.imobiliaria.dao.DAO;
import br.com.imobiliaria.modelo.Apartamento;
import br.com.imobiliaria.modelo.Comprador;
import br.com.imobiliaria.modelo.Fechamento;
import br.com.imobiliaria.modelo.Funcionario;
import br.com.imobiliaria.modelo.Negociacao;
import br.com.imobiliaria.modelo.StatusApartamento;
import br.com.imobiliaria.modelo.StatusNegociacao;
import br.com.imobiliaria.modelo.StatusVisita;
import br.com.imobiliaria.modelo.Visita;
import br.com.imobiliaria.util.ContextUtil;

@ManagedBean
@ViewScoped
public class NegociacaoBean implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private DAO<Negociacao> dao = new DAO<Negociacao>(Negociacao.class);
	private Negociacao negociacao = new Negociacao();
	private List<Negociacao> negociacoesFiltradas;
	private List<Negociacao> negociacoes;
	
	private Long id;
	private Long visitaId;
	
	public void atualizaLista(){
		negociacoes = dao.listaTodos();
	}
	
	public Long getVisitaId() {
		return visitaId;
	}

	public void setVisitaId(Long visitaId) {
		this.visitaId = visitaId;
	}

	public Negociacao getNegociacao() {
		return negociacao;
	}

	public void setNegociacao(Negociacao negociacao) {
		this.negociacao = negociacao;
	}

	public List<Negociacao> getNegociacoesFiltradas() {
		return negociacoesFiltradas;
	}

	public void setNegociacoesFiltradas(List<Negociacao> negociacoesFiltradas) {
		this.negociacoesFiltradas = negociacoesFiltradas;
	}

	public List<Negociacao> getNegociacoes() {
		return negociacoes;
	}

	public void setNegociacoes(List<Negociacao> negociacoes) {
		this.negociacoes = negociacoes;
	}
	
	public Calendar getToday(){
		return Calendar.getInstance();
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public void onActionView(){
		if(this.negociacao.getId() != this.getId() && this.getId() != null){
			this.negociacao = dao.buscaPorId(this.getId());
		}
	}
	
	public void onActionViewVisita(){
		Visita v = new DAO<Visita>(Visita.class).buscaPorId(getVisitaId());
		if(v != null && v.getStatus() == StatusVisita.Concluída){
			if(v.getNegociacao() != null) negociacao = v.getNegociacao();
			else{
				negociacao.setApartamento(v.getApartamento());
				negociacao.setComprador(v.getComprador());
				negociacao.setCorretor(v.getCorretor());
				negociacao.setValorApartamento(BigDecimal.valueOf(v.getApartamento().getValorVenda()));
				negociacao.setValorImobiliaria(BigDecimal.valueOf(v.getApartamento().getValorVenda() * 0.06));
				negociacao.setVisita(v);
			}
		}
	}
	
	public void populaNegociacao(Apartamento apartamento, Comprador comprador, Funcionario corretor){
		negociacao.setApartamento(apartamento);
		negociacao.setComprador(comprador);
		negociacao.setCorretor(corretor);
		negociacao.setValorApartamento(BigDecimal.valueOf(apartamento.getValorVenda()));
		negociacao.setValorImobiliaria(BigDecimal.valueOf(apartamento.getValorVenda() * 0.06));
	}
	
	public void gravar(){
		try {
			if(negociacao.getId() == null){
				dao.adiciona(negociacao);
				
				atualizaStatusApartamento();
				
				redirectWithParam();
			}else
				dao.atualiza(negociacao);
		} catch (Exception e) {
			e.printStackTrace();
			ContextUtil.getAnyMessage("Ocorreu um erro!");
		}
	}
	
	public void atualizaStatusApartamento() throws Exception{
		this.negociacao.getApartamento().setStatus(StatusApartamento.Negociando);
		new DAO<Apartamento>(Apartamento.class).atualiza(this.negociacao.getApartamento());
	}
	
	public void limparNegociacao(){
		this.negociacao = new Negociacao();
	}
	
	public boolean isNovo(){
		return this.negociacao.getId() == null;
	}
	
	public void onRowSelect(){
		redirectWithParam();
	}
	
	public boolean isComVisita(){
		return this.negociacao.getVisita() != null;
	}
	
	public boolean isComCorretor(){
		return this.negociacao.getCorretor() != null;
	}

	private void redirectWithParam() {
		ContextUtil.redirect("negociacao.xhtml?id=" + negociacao.getId());
	}
	
	public boolean isNegociando(){
		return this.negociacao.getStatus() == StatusNegociacao.Negociando;
	}
	
	public boolean isCancelada(){
		return this.negociacao.getStatus() == StatusNegociacao.Cancelada;
	}
	
	public boolean isConcluida(){
		return this.negociacao.getStatus() == StatusNegociacao.Concluída;
	}
	
	public void cancelar(){
		this.negociacao.setStatus(StatusNegociacao.Cancelada);
		
		gravar();
		
		//Caso tenham negociações em andamento no apartamento, não retorna o apartamento à venda
		if(this.negociacao.getApartamento().getNegociacoesAtivas().size() == 0){
			System.out.println("Não possui negociações ativas");
			this.negociacao.getApartamento().setStatus(StatusApartamento.À_Venda);
			try {
				new DAO<Apartamento>(Apartamento.class).atualiza(this.negociacao.getApartamento());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public void concluir(){
		try {
			this.negociacao.setStatus(StatusNegociacao.Concluída);
			gravar();
			Fechamento f = new Fechamento();
			f.setNegociacao(this.negociacao);
			new DAO<Fechamento>(Fechamento.class).adiciona(f);
			
			this.negociacao.getApartamento().setStatus(StatusApartamento.Vendemos);
			new DAO<Apartamento>(Apartamento.class).atualiza(this.negociacao.getApartamento());
			
			ContextUtil.redirect("fechamento.xhtml?negociacao=" + this.negociacao.getId());

			new ApartamentoStatus().desativarRelacionados(this.getNegociacao().getApartamento().getId());
		} catch (Exception e) {
			ContextUtil.getAnyMessage("Ocorreu um erro.");
		}
	}
	
	public void reativar(){
		this.negociacao.setStatus(StatusNegociacao.Negociando);
		
		gravar();

		try {
			atualizaStatusApartamento();
		} catch (Exception e) {
			ContextUtil.getAnyMessage("Ocorreu um erro!");
			e.printStackTrace();
		}
	}
}
