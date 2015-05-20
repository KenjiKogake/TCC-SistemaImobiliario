package br.com.imobiliaria.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.persistence.PersistenceException;

import br.com.imobiliaria.dao.DAO;
import br.com.imobiliaria.modelo.Transporte;
import br.com.imobiliaria.modelo.TransportesDoCondominio;
import br.com.imobiliaria.util.ContextUtil;

@ManagedBean
@ViewScoped
public class TransporteBean implements Serializable{
	private static final long serialVersionUID = 1L;

	private Transporte transporte = new Transporte();
	private DAO<Transporte> dao = new DAO<Transporte>(Transporte.class);
	private List<Transporte> transportes;
	private List<Transporte> transportesFiltrados;
	
	@PostConstruct
	public void postConstruct(){
		atualizaLista();
	}
	
	private void atualizaLista() {
		this.transportes = dao.listaTodos();
	}
	
	public List<Transporte> getTransportesFiltrados() {
		return transportesFiltrados;
	}

	public void setTransportesFiltrados(List<Transporte> transportesFiltrados) {
		this.transportesFiltrados = transportesFiltrados;
	}
	
	public List<Transporte> getTransportes() {
		return transportes;
	}

	public void setTransportes(List<Transporte> transportes) {
		this.transportes = transportes;
	}

	public Transporte getTransporte() {
		return transporte;
	}
	
	public void setTransporte(Transporte transporte) {
		this.transporte = transporte;
	}
	
	public void gravar() {
		try {
			if (transporte.getId() == null)
				dao.adiciona(transporte);
			else
				dao.atualiza(transporte);
		}catch (PersistenceException e) {
			if(e.getCause().toString().contains("Duplicate")) ContextUtil.getDuplicateMessage("Transporte inserido");
		}
		catch(Exception e){
			ContextUtil.getAnyMessage("Ocorreu um erro!");
		}finally{
			atualizaLista();
		}
	}

	public void deletar() {
		try {
			dao.remove(transporte);
			atualizaLista();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void limparTransporte() {
		this.transporte = new Transporte();
	}

	public boolean isNovo() {
		return this.transporte.getId() == null;
	}

	public boolean isSemCondominios() {
		List<TransportesDoCondominio> lista = this.transporte.getCondominiosDoTransporte();
		
		return lista == null || lista.isEmpty();
	}

	public boolean isDeletavel() {
		return !isNovo() && isSemCondominios();
	}

}
