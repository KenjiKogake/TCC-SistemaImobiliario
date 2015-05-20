package br.com.imobiliaria.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.persistence.PersistenceException;

import br.com.imobiliaria.dao.DAO;
import br.com.imobiliaria.modelo.Midia;
import br.com.imobiliaria.modelo.Status;
import br.com.imobiliaria.util.ContextUtil;

@ManagedBean
@ViewScoped
public class MidiaBean implements Serializable {
	private static final long serialVersionUID = 1L;

	private Midia midia = new Midia();
	private DAO<Midia> dao = new DAO<Midia>(Midia.class);
	private List<Midia> midiasFiltradas;
	private List<Midia> midias;

	@PostConstruct
	public void postConstruct() {
		atualizaLista();
	}

	public void atualizaLista() {
		this.midias = dao.listaTodos();
	}

	public Midia getMidia() {
		return midia;
	}

	public void setMidia(Midia midia) {
		this.midia = midia;
	}

	public List<Midia> getMidias() {
		return midias;
	}

	public void setMidias(List<Midia> midias) {
		this.midias = midias;
	}

	public List<Midia> getMidiasFiltradas() {
		return midiasFiltradas;
	}

	public void setMidiasFiltradas(List<Midia> midiasFiltradas) {
		this.midiasFiltradas = midiasFiltradas;
	}

	public void gravar() {
		try {
			if (midia.getId() == null)
				dao.adiciona(midia);
			else
				dao.atualiza(midia);

		} catch (PersistenceException e) {
			if (e.getCause().toString().contains("Duplicate"))
				ContextUtil.getDuplicateMessage("Midia inserida");
		} catch (Exception e) {
			ContextUtil.getAnyMessage("Ocorreu um erro!");
		} finally {
			atualizaLista();
		}
	}

	public void mudarStatus(){
		if(midia.getStatus() == Status.Ativo){
			midia.setStatus(Status.Inativo);
			ContextUtil.getDisableMessage(midia.toString());
		}else{
			midia.setStatus(Status.Ativo);
			ContextUtil.getReactivateMessage(midia.toString());
		}
		gravar();
	}
	
	public void limparMidia() {
		this.midia = new Midia();
	}

	public boolean isNovo() {
		return this.midia.getId() == null;
	}
	
	public boolean isAtivo(){
		if(this.midia == null) return false;
		return this.midia.getStatus() == Status.Ativo;
	}
}
