package br.com.imobiliaria.bean;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.imobiliaria.dao.ApartamentoStatus;
import br.com.imobiliaria.dao.DAO;
import br.com.imobiliaria.modelo.Apartamento;
import br.com.imobiliaria.modelo.Fechamento;
import br.com.imobiliaria.modelo.Negociacao;
import br.com.imobiliaria.modelo.StatusApartamento;
import br.com.imobiliaria.modelo.StatusFechamento;
import br.com.imobiliaria.modelo.StatusNegociacao;
import br.com.imobiliaria.util.ContextUtil;

@ManagedBean
@ViewScoped
public class FechamentoBean implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private DAO<Fechamento> dao = new DAO<Fechamento>(Fechamento.class);
	private Fechamento fechamento = new Fechamento();
	private List<Fechamento> fechamentosFiltrados;
	private List<Fechamento> fechamentos;
	
	private Long id;
	private Long negociacaoId;
	
	public void atualizaLista(){
		fechamentos = dao.listaTodos();
	}
	
	public Long getNegociacaoId() {
		return negociacaoId;
	}

	public void setNegociacaoId(Long negociacaoId) {
		this.negociacaoId = negociacaoId;
	}

	public Fechamento getFechamento() {
		return fechamento;
	}

	public void setFechamento(Fechamento fechamento) {
		this.fechamento = fechamento;
	}

	public List<Fechamento> getFechamentosFiltrados() {
		return fechamentosFiltrados;
	}

	public void setFechamentosFiltrados(List<Fechamento> fechamentosFiltrados) {
		this.fechamentosFiltrados = fechamentosFiltrados;
	}

	public List<Fechamento> getFechamentos() {
		return fechamentos;
	}

	public void setFechamentos(List<Fechamento> fechamentos) {
		this.fechamentos = fechamentos;
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
		if(this.fechamento.getId() != this.getId() && this.getId() != null){
			this.fechamento = dao.buscaPorId(this.getId());
		}
	}
	
	public void onActionViewNegociacao(){
		Negociacao n = new DAO<Negociacao>(Negociacao.class).buscaPorId(getNegociacaoId());
		if(n != null && n.getStatus() == StatusNegociacao.Concluída){
			if(n.getFechamento() != null) fechamento = n.getFechamento();
			else{
				fechamento.setNegociacao(n);
			}
		}
	}
	
	public void gravar(){
		try {
			if(fechamento.getId() == null){
				dao.adiciona(fechamento);
				redirectWithParam();
			}else
				dao.atualiza(fechamento);
		} catch (Exception e) {
			e.printStackTrace();
			ContextUtil.getAnyMessage("Ocorreu um erro!");
		}
	}
	
	public void limparFechamento(){
		this.fechamento = new Fechamento();
	}
	
	public boolean isNovo(){
		return this.fechamento.getId() == null;
	}
	
	public void onRowSelect(){
		redirectWithParam();
	}
	
	private void redirectWithParam() {
		ContextUtil.redirect("fechamento.xhtml?id=" + fechamento.getId());
	}
	
	public boolean isEmAndamento(){
		return this.fechamento.getStatus() == StatusFechamento.Andamento;
	}
	
	public boolean isCancelado(){
		return this.fechamento.getStatus() == StatusFechamento.Cancelado;
	}
	
	public boolean isConcluido(){
		return this.fechamento.getStatus() == StatusFechamento.Concluído;
	}
	
	public void cancelar(){
		this.fechamento.setStatus(StatusFechamento.Cancelado);
		gravar();
		
		cancelaVendaApartamento();
	}
	
	public void cancelaVendaApartamento(){
		this.fechamento.getNegociacao().getApartamento().setStatus(StatusApartamento.À_Venda);
		try {
			new DAO<Apartamento>(Apartamento.class).atualiza(this.fechamento.getNegociacao().getApartamento());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void vendeApartamento(){
		this.fechamento.getNegociacao().getApartamento().setStatus(StatusApartamento.Vendemos);
		try {
			new DAO<Apartamento>(Apartamento.class).atualiza(this.fechamento.getNegociacao().getApartamento());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		new ApartamentoStatus().desativarRelacionados(this.fechamento.getNegociacao().getApartamento().getId());
	}
	
	public void concluir(){
		this.fechamento.setStatus(StatusFechamento.Concluído);
		gravar();
		
	}
	
	public void reativar(){
		this.fechamento.setStatus(StatusFechamento.Andamento);
		gravar();
		
		vendeApartamento();
	}
}
