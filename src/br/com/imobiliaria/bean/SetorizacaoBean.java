package br.com.imobiliaria.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.persistence.PersistenceException;

import br.com.imobiliaria.dao.DAO;
import br.com.imobiliaria.modelo.Setorizacao;
import br.com.imobiliaria.util.ContextUtil;

@ManagedBean
@ViewScoped
public class SetorizacaoBean implements Serializable{
	private static final long serialVersionUID = 1L;

	private Setorizacao setorizacao = new Setorizacao();
	private DAO<Setorizacao> dao = new DAO<Setorizacao>(Setorizacao.class);
	private List<Setorizacao> setores;
	private List<Setorizacao> setoresFiltrados;
	
	@PostConstruct
	public void postConstruct(){
		atualizaLista();
	}
	
	private void atualizaLista() {
		this.setores = dao.listaTodos();
	}

	public List<Setorizacao> getSetoresFiltrados() {
		return setoresFiltrados;
	}

	public void setSetoresFiltrados(List<Setorizacao> setoresFiltrados) {
		this.setoresFiltrados = setoresFiltrados;
	}

	public List<Setorizacao> getSetores() {
		return setores;
	}
	
	public void setSetores(List<Setorizacao> setores) {
		this.setores = setores;
	}

	public Setorizacao getSetorizacao() {
		return setorizacao;
	}
	
	public void setSetorizacao(Setorizacao setorizacao) {
		this.setorizacao = setorizacao;
	}
	
	public void gravar() {
		try {
			if (setorizacao.getId() == null)
				dao.adiciona(setorizacao);
			else
				dao.atualiza(setorizacao);

		}catch (PersistenceException e) {
			if(e.getCause().toString().contains("Duplicate")) ContextUtil.getDuplicateMessage("Setor inserido");
		}
		catch(Exception e){
			ContextUtil.getAnyMessage("Ocorreu um erro!");
		}finally{
			atualizaLista();
		}
	}

	public void deletar() {
		try {
			dao.remove(setorizacao);
			atualizaLista();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void limparSetorizacao() {
		this.setorizacao = new Setorizacao();
	}

	public boolean isNovo() {
		return this.setorizacao.getId() == null;
	}

	public boolean isSemCondominios() {
		return this.setorizacao.getCondominios() == null
				|| this.setorizacao.getCondominios().isEmpty();
	}

	public boolean isDeletavel() {
		return !isNovo() && isSemCondominios();
	}
}
