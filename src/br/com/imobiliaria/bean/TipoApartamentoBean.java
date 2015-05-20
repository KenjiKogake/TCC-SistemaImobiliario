package br.com.imobiliaria.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.persistence.PersistenceException;

import br.com.imobiliaria.dao.DAO;
import br.com.imobiliaria.modelo.TipoApartamento;
import br.com.imobiliaria.util.ContextUtil;

@ManagedBean
@ViewScoped
public class TipoApartamentoBean implements Serializable{
	private static final long serialVersionUID = 1L;

	private TipoApartamento tipoApartamento = new TipoApartamento();
	private final DAO<TipoApartamento> dao = new DAO<TipoApartamento>(TipoApartamento.class);
	private List<TipoApartamento> tiposApartamento;
	private List<TipoApartamento> tiposFiltrados;
	
	@PostConstruct
	public void postConstruct(){
		atualizaLista();
	}

	private void atualizaLista() {
		this.tiposApartamento = dao.listaTodos();
	}
	
	public List<TipoApartamento> getTiposApartamento() {
		return tiposApartamento;
	}

	public void setTiposApartamento(List<TipoApartamento> tiposApartamento) {
		this.tiposApartamento = tiposApartamento;
	}

	public List<TipoApartamento> getTiposFiltrados() {
		return tiposFiltrados;
	}

	public void setTiposFiltrados(List<TipoApartamento> tiposFiltrados) {
		this.tiposFiltrados = tiposFiltrados;
	}

	public TipoApartamento getTipoApartamento() {
		return tipoApartamento;
	}
	
	public void setTipoApartamento(TipoApartamento cargo) {
		this.tipoApartamento = cargo;
	}
	
	public void gravar(){
		try {
			if(tipoApartamento.getId() == null)
				dao.adiciona(tipoApartamento);
			else
				dao.atualiza(tipoApartamento);
			
		}catch (PersistenceException e) {
			if(e.getCause().toString().contains("Duplicate")) ContextUtil.getDuplicateMessage("Tipo Apartamento inserido");
		}
		catch(Exception e){
			ContextUtil.getAnyMessage("Ocorreu um erro!");
		}finally{
			atualizaLista();
		}
	}

	public void deletar(){
		try {
			dao.remove(tipoApartamento);
			atualizaLista();
		} catch (PersistenceException e) {
			ContextUtil.getAnyMessage("Não pode ser excluído, existem apartamentos vinculados.");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void limparTipoApartamento(){
		this.tipoApartamento = new TipoApartamento();
	}
	
	public boolean isNovo(){
		return this.tipoApartamento.getId() == null;
	}
	
	public boolean isSemApartamentos(){
		return this.tipoApartamento.getApartamentos() == null || 
					this.tipoApartamento.getApartamentos().isEmpty();
	}
	
	public boolean isDeletavel(){
		return !isNovo() && isSemApartamentos();
	}
}
