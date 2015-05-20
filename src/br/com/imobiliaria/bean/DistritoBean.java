package br.com.imobiliaria.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.persistence.PersistenceException;

import br.com.imobiliaria.dao.DAO;
import br.com.imobiliaria.modelo.Distrito;
import br.com.imobiliaria.util.ContextUtil;

@ManagedBean
@ViewScoped
public class DistritoBean implements Serializable{
	private static final long serialVersionUID = 1L;

	private Distrito distrito = new Distrito();
	private DAO<Distrito> dao = new DAO<Distrito>(Distrito.class);
	private List<Distrito> distritos;
	private List<Distrito> distritosFiltrados;
	
	@PostConstruct
	public void postConstruct(){
		atualizaLista();
	}
	
	private void atualizaLista() {
		this.distritos = dao.listaTodos();
	}

	public Distrito getDistrito() {
		return distrito;
	}
	
	public void setDistrito(Distrito distrito) {
		this.distrito = distrito;
	}
	
	public List<Distrito> getDistritos() {
		return distritos;
	}

	public void setDistritos(List<Distrito> distritos) {
		this.distritos = distritos;
	}

	public List<Distrito> getDistritosFiltrados() {
		return distritosFiltrados;
	}

	public void setDistritosFiltrados(List<Distrito> distritosFiltrados) {
		this.distritosFiltrados = distritosFiltrados;
	}

	public void gravar() {
		try {
			if (distrito.getId() == null)
				dao.adiciona(distrito);
			else
				dao.atualiza(distrito);

		}catch (PersistenceException e) {
			if(e.getCause().toString().contains("Duplicate")) ContextUtil.getDuplicateMessage("Distrito inserido");
		}
		catch(Exception e){
			ContextUtil.getAnyMessage("Ocorreu um erro!");
		}finally{
			atualizaLista();
		}
	}

	public void deletar() {
		try {
			dao.remove(distrito);
			atualizaLista();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void limparDistrito() {
		this.distrito = new Distrito();
	}

	public boolean isNovo() {
		return this.distrito.getId() == null;
	}

	public boolean isSemCondominios() {
		return this.distrito.getCondominios() == null
				|| this.distrito.getCondominios().isEmpty();
	}

	public boolean isDeletavel() {
		return !isNovo() && isSemCondominios();
	}
}
