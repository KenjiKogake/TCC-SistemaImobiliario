package br.com.imobiliaria.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.persistence.PersistenceException;

import org.hibernate.exception.ConstraintViolationException;

import br.com.imobiliaria.dao.DAO;
import br.com.imobiliaria.modelo.Departamento;
import br.com.imobiliaria.util.ContextUtil;

@ManagedBean
@ViewScoped
public class DepartamentoBean implements Serializable{
	private static final long serialVersionUID = 1L;

	private Departamento departamento = new Departamento();
	private DAO<Departamento> dao = new DAO<Departamento>(Departamento.class);
	private List<Departamento> departamentos;
	private List<Departamento> departamentosFiltrados;

	@PostConstruct
	public void postConstruct(){
		atualizaLista();
	}
	
	public void atualizaLista(){
		this.departamentos = dao.listaTodos();
	}
	
	public Departamento getDepartamento() {
		return departamento;
	}

	public void setDepartamento(Departamento departamento) {
		this.departamento = departamento;
	}

	public List<Departamento> getDepartamentos() {
		return this.departamentos;
	}
	
	public void setDepartamentos(List<Departamento> departamentos) {
		this.departamentos = departamentos;
	}
	
	public List<Departamento> getDepartamentosFiltrados() {
		return departamentosFiltrados;
	}

	public void setDepartamentosFiltrados(List<Departamento> departamentosFiltrados) {
		this.departamentosFiltrados = departamentosFiltrados;
	}

	public void gravar() {
		try {
			if (departamento.getId() == null)
				dao.adiciona(departamento);
			else
				dao.atualiza(departamento);

		} catch (PersistenceException e) {
			try {
				ConstraintViolationException erro = (ConstraintViolationException) e.getCause();	
				if(erro.getSQLException().getMessage().contains("Duplicate")) ContextUtil.getDuplicateMessage("Departamento inserido");
			}catch(Exception er){
				er.printStackTrace();
			}
			
			//if(e.getCause().toString().contains("Duplicate")) ContextUtil.getDuplicateMessage("Departamento inserido");
		} catch(Exception e){
			ContextUtil.getAnyMessage("Ocorreu um erro!");
		}finally{
			atualizaLista();
		}
	}

	public void deletar() {
		try {
			dao.remove(departamento);
			atualizaLista();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void limparDepartamento() {
		this.departamento = new Departamento();
	}

	public boolean isNovo() {
		return this.departamento.getId() == null;
	}

	public boolean isSemFuncionarios() {
		return this.departamento.getFuncionarios() == null
				|| this.departamento.getFuncionarios().isEmpty();
	}

	public boolean isDeletavel() {
		return !isNovo() && isSemFuncionarios();
	}
}
