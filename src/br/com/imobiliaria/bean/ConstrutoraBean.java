package br.com.imobiliaria.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.persistence.PersistenceException;

import br.com.imobiliaria.dao.DAO;
import br.com.imobiliaria.modelo.Construtora;
import br.com.imobiliaria.util.ContextUtil;

@ManagedBean
@ViewScoped
public class ConstrutoraBean implements Serializable{
	private static final long serialVersionUID = 1L;

	private Construtora construtora = new Construtora();
	private DAO<Construtora> dao = new DAO<Construtora>(Construtora.class);
	private List<Construtora> construtoras;
	private List<Construtora> construtorasFiltradas;
	
	@PostConstruct
	public void postConstruct(){
		atualizaLista();
	}
	
	public Construtora getConstrutora() {
		return construtora;
	}
	
	public void setConstrutora(Construtora construtora) {
		this.construtora = construtora;
	}
	
	public List<Construtora> getConstrutoras() {
		return construtoras;
	}

	public void setConstrutoras(List<Construtora> construtoras) {
		this.construtoras = construtoras;
	}

	public List<Construtora> getConstrutorasFiltradas() {
		return construtorasFiltradas;
	}

	public void setConstrutorasFiltradas(List<Construtora> construtorasFiltradas) {
		this.construtorasFiltradas = construtorasFiltradas;
	}

	public void atualizaLista(){
		this.construtoras = dao.listaTodos();
	}
	
	public void gravar(){
		try {
			if(construtora.getId() == null)
				dao.adiciona(construtora);
			else
				dao.atualiza(construtora);
			
		}catch (PersistenceException e) {
			if(e.getCause().toString().contains("Duplicate")) ContextUtil.getDuplicateMessage("Construtora inserida");
		}
		catch(Exception e){
			ContextUtil.getAnyMessage("Ocorreu um erro!");
		}finally{
			atualizaLista();
		}
	}
	
	public void deletar(){
		try {
			dao.remove(construtora);
			atualizaLista();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void limparConstrutora(){
		this.construtora = new Construtora();
	}
	
	public boolean isNovo(){
		return this.construtora.getId() == null;
	}
	
	public boolean isSemCondominios(){
		return this.construtora.getCondominios() == null ||
			 this.construtora.getCondominios().isEmpty();
	}
	
	public boolean isDeletavel(){
		return !isNovo() && isSemCondominios();
	}
}
