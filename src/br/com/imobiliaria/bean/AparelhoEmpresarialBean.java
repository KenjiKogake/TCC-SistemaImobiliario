package br.com.imobiliaria.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.persistence.PersistenceException;

import br.com.imobiliaria.dao.DAO;
import br.com.imobiliaria.modelo.AparelhoEmpresarial;
import br.com.imobiliaria.util.ContextUtil;

@ManagedBean
@ViewScoped
public class AparelhoEmpresarialBean implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private final DAO<AparelhoEmpresarial> dao = new DAO<AparelhoEmpresarial>(AparelhoEmpresarial.class);
	private AparelhoEmpresarial aparelho = new AparelhoEmpresarial();
	private List<AparelhoEmpresarial> aparelhosFiltrados;
	private List<AparelhoEmpresarial> aparelhos;
	
	@PostConstruct
	public void postConstruct(){
		atualizaLista();
	}

	private void atualizaLista() {
		this.aparelhos = dao.listaTodos();
	}
	
	public List<AparelhoEmpresarial> getAparelhosDisponiveis(){
		List<AparelhoEmpresarial> todos = dao.listaTodos();
		List<AparelhoEmpresarial> disponiveis = new ArrayList<AparelhoEmpresarial>();
		for (AparelhoEmpresarial aparelho : todos) {
			if(aparelho.getFuncionario() == null){
				disponiveis.add(aparelho);
			}
		}
		return disponiveis;
	}
	
	public List<AparelhoEmpresarial> getAparelhosFiltrados() {
		return aparelhosFiltrados;
	}

	public void setAparelhosFiltrados(List<AparelhoEmpresarial> aparelhosFiltrados) {
		this.aparelhosFiltrados = aparelhosFiltrados;
	}

	public List<AparelhoEmpresarial> getAparelhos() {
		return aparelhos;
	}

	public void setAparelhos(List<AparelhoEmpresarial> aparelhos) {
		this.aparelhos = aparelhos;
	}

	public AparelhoEmpresarial getAparelho() {
		return aparelho;
	}
	
	public void setAparelho(AparelhoEmpresarial aparelho) {
		this.aparelho = aparelho;
	}
	
	public void gravar(){
		try {
			if(aparelho.getId() == null)
				dao.adiciona(aparelho);
			else
				dao.atualiza(aparelho);
			
		}catch (PersistenceException e) {
			if(e.getCause().toString().contains("Duplicate")) ContextUtil.getDuplicateMessage("Aparelho inserido");
		}
		catch(Exception e){
			ContextUtil.getAnyMessage("Ocorreu um erro!");
		}finally{
			atualizaLista();
		}
	}
	
	public void deletar(){
		try {
			dao.remove(aparelho);
			atualizaLista();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void limparAparelho(){
		this.aparelho = new AparelhoEmpresarial();
	}
	
	public boolean isNovo(){
		return this.aparelho.getId() == null;
	}
	
	public boolean isComFuncionario(){
		return this.aparelho.getFuncionario() != null;
	}
	
	public boolean isDeletavel(){
		return !isComFuncionario() && !isNovo();
	}
}
