package br.com.imobiliaria.bean;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.imobiliaria.dao.DAO;
import br.com.imobiliaria.modelo.Apartamento;
import br.com.imobiliaria.modelo.Comprador;
import br.com.imobiliaria.modelo.Funcionario;
import br.com.imobiliaria.modelo.StatusVisita;
import br.com.imobiliaria.modelo.Visita;
import br.com.imobiliaria.util.ContextUtil;

@ManagedBean
@ViewScoped
public class VisitaBean implements Serializable{
	private static final long serialVersionUID = 1L;

	private Visita visita = new Visita();
	private DAO<Visita> dao = new DAO<Visita>(Visita.class);
	private List<Visita> visitasFiltradas;
	private List<Visita> visitas;
	
	public void atualizaLista(){
		this.visitas = dao.listaTodos();
	}
	
	public Visita getVisita() {
		return visita;
	}

	public void setVisita(Visita visita) {
		this.visita = visita;
	}

	public List<Visita> getVisitas() {
		return visitas;
	}

	public void setVisitas(List<Visita> visitas) {
		this.visitas = visitas;
	}

	public List<Visita> getVisitasFiltradas() {
		return visitasFiltradas;
	}

	public void setVisitasFiltradas(List<Visita> visitasFiltradas) {
		this.visitasFiltradas = visitasFiltradas;
	}

	public void gravar(){
		try {
			if(visita.getId() == null)
				dao.adiciona(visita);
			else
				dao.atualiza(visita);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void cancelarVisita(){
		this.visita.setDataCancelamento(Calendar.getInstance().getTime());
		this.visita.setStatus(StatusVisita.Cancelada);
		gravar();
	}
	
	public void concluirVisita(){
		this.visita.setStatus(StatusVisita.Concluída);
		gravar();
	}
	
	public void confirmarVisita(){
		this.visita.setDataConfirmacao(Calendar.getInstance().getTime());
		this.visita.setStatus(StatusVisita.Confirmada);
		gravar();
	}
	
	public void concluirENegociar(){
		concluirVisita();
		criarNegociacao();
	}
	
	public void criarNegociacao(){
		ContextUtil.redirect("negociacao.xhtml?visita=" + this.visita.getId());
	}
	
	public void limparVisita(){
		this.visita = new Visita();
	}
	
	public boolean isNovo(){
		return this.visita.getId() == null;
	}
	
	public void populaVisita(Apartamento apartamento, Comprador comprador, Funcionario corretor){
		limparVisita();
		
		this.visita.setApartamento(apartamento);
		this.visita.setComprador(comprador);
		this.visita.setCorretor(corretor);
	}
	
	public void popularVisita(Comprador comprador, Funcionario corretor){
		limparVisita();
		
		this.visita.setComprador(comprador);
		this.visita.setCorretor(corretor);
	}
	
	public boolean isCancelada(){
		return this.visita.getStatus() == StatusVisita.Cancelada;
	}
	
	public boolean isConfirmada(){
		return isConcluida() ||	this.visita.getStatus() == StatusVisita.Confirmada;
	}
	
	public boolean isConcluida(){
		return this.visita.getStatus() == StatusVisita.Concluída;
	}
	
	public boolean isPendente(){
		return this.visita.getStatus() == StatusVisita.Pendente;
	}
	
	public boolean isComCorretor(){
		return this.visita.getCorretor() != null;
	}
}
