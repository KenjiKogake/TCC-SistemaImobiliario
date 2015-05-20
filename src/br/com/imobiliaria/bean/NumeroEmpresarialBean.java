package br.com.imobiliaria.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.persistence.PersistenceException;

import br.com.imobiliaria.dao.DAO;
import br.com.imobiliaria.modelo.NumeroEmpresarial;
import br.com.imobiliaria.util.ContextUtil;

@ManagedBean
@ViewScoped
public class NumeroEmpresarialBean implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private NumeroEmpresarial numero = new NumeroEmpresarial();
	private final DAO<NumeroEmpresarial> dao = new DAO<NumeroEmpresarial>(NumeroEmpresarial.class);
	private List<NumeroEmpresarial> numeros;
	private List<NumeroEmpresarial> numerosFiltrados;
	
	@PostConstruct
	public void postConstruct(){
		atualizaLista();
	}
	
	private void atualizaLista() {
		this.numeros = dao.listaTodos();
	}

	public List<NumeroEmpresarial> getNumeros() {
		return numeros;
	}

	public void setNumeros(List<NumeroEmpresarial> numeros) {
		this.numeros = numeros;
	}

	public List<NumeroEmpresarial> getNumerosFiltrados() {
		return numerosFiltrados;
	}

	public void setNumerosFiltrados(List<NumeroEmpresarial> numerosFiltrados) {
		this.numerosFiltrados = numerosFiltrados;
	}

	public NumeroEmpresarial getNumero() {
		return numero;
	}
	
	public void setNumero(NumeroEmpresarial numero) {
		this.numero = numero;
	}
	
	public List<NumeroEmpresarial> getNumerosDisponiveis() {
		List<NumeroEmpresarial> todos = dao.listaTodos();
		List<NumeroEmpresarial> disponiveis = new ArrayList<NumeroEmpresarial>();
		for (NumeroEmpresarial numero : todos) {
			if(numero.getFuncionario() == null){
				disponiveis.add(numero);
			}
		}
		return disponiveis;
	}
	
	public void gravar(){
		try {
			if(numero.getId() == null)
				dao.adiciona(numero);
			else
				dao.atualiza(numero);
			
		}catch (PersistenceException e) {
			if(e.getCause().toString().contains("Duplicate")) ContextUtil.getDuplicateMessage("Número inserido");
		}
		catch(Exception e){
			ContextUtil.getAnyMessage("Ocorreu um erro!");
		}finally{
			atualizaLista();
		}
	}
	
	public void deletar(){
		try {
			dao.remove(numero);
			atualizaLista();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void limparNumero(){
		this.numero = new NumeroEmpresarial();
	}
	
	public boolean isNovo(){
		return this.numero.getId() == null;
	}
	
	public boolean isComFuncionario(){
		return this.numero.getFuncionario() != null;
	}
	
	public boolean isDeletavel(){
		return !isComFuncionario() && !isNovo();
	}
}
