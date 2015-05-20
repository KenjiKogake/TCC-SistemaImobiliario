package br.com.imobiliaria.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.persistence.PersistenceException;

import br.com.imobiliaria.dao.DAO;
import br.com.imobiliaria.modelo.NivelAcesso;
import br.com.imobiliaria.util.ContextUtil;

@ManagedBean
@ViewScoped
public class NivelAcessoBean implements Serializable{
	private static final long serialVersionUID = 1L;

	private NivelAcesso nivelAcesso = new NivelAcesso();
	private DAO<NivelAcesso> dao = new DAO<NivelAcesso>(NivelAcesso.class);
	private List<NivelAcesso> niveisAcessoFiltrados;
	private List<NivelAcesso> niveisAcesso;

	@PostConstruct
	public void postConstruct(){
		atualizaLista();
	}
	
	public void atualizaLista(){
		this.niveisAcesso = dao.listaTodos();
	}
	
	public NivelAcesso getNivelAcesso() {
		return nivelAcesso;
	}

	public void setNivelAcesso(NivelAcesso nivelAcesso) {
		this.nivelAcesso = nivelAcesso;
	}

	public List<NivelAcesso> getNiveisAcessoFiltrados() {
		return niveisAcessoFiltrados;
	}

	public void setNiveisAcessoFiltrados(List<NivelAcesso> niveisAcessoFiltrados) {
		this.niveisAcessoFiltrados = niveisAcessoFiltrados;
	}

	public List<NivelAcesso> getNiveisAcesso() {
		return niveisAcesso;
	}

	public void setNiveisAcesso(List<NivelAcesso> niveisAcesso) {
		this.niveisAcesso = niveisAcesso;
	}

	public void gravar(){
		try {
			if(isNovo())
				dao.adiciona(nivelAcesso);
			else
				dao.atualiza(nivelAcesso);
		}catch (PersistenceException e) {
			if(e.getCause().toString().contains("Duplicate")) ContextUtil.getDuplicateMessage("Nivel de Acesso inserido");
		}
		catch(Exception e){
			ContextUtil.getAnyMessage("Ocorreu um erro!");
		}finally{
			atualizaLista();
		}
	}

	public void deletar(){
		try {
			dao.remove(nivelAcesso);
			atualizaLista();
		}  catch (PersistenceException e) {
			ContextUtil.getAnyMessage("Não pode ser excluído, existem funcionários vinculados.");
		} catch(Exception e){
			ContextUtil.getAnyMessage("Ocorreu um erro!");
		}
	}
	
	public void limparNivelAcesso(){
		this.nivelAcesso = new NivelAcesso();
	}
	
	public boolean isNovo(){
		return this.nivelAcesso.getId() == null;
	}
	
	public boolean isSemFuncionarios(){
		return this.nivelAcesso.getFuncionarios() == null || 
					this.nivelAcesso.getFuncionarios().isEmpty();
	}
	
	public boolean isDeletavel(){
		return !isNovo() && isSemFuncionarios();
	}
}
