package br.com.imobiliaria.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.persistence.PersistenceException;

import br.com.imobiliaria.dao.DAO;
import br.com.imobiliaria.modelo.Cargo;
import br.com.imobiliaria.util.ContextUtil;


@ManagedBean
@ViewScoped
public class CargoBean implements Serializable{
	private static final long serialVersionUID = 1L;

	private Cargo cargo = new Cargo();
	private DAO<Cargo> dao = new DAO<Cargo>(Cargo.class);
	private List<Cargo> cargosFiltrados;
	private List<Cargo> cargos;

	@PostConstruct
	public void postConstruct(){
		atualizaLista();
	}
	
	public void atualizaLista(){
		this.cargos = dao.listaTodos();
	}
	
	public Cargo getCargo() {
		return cargo;
	}
	
	public void setCargo(Cargo cargo) {
		this.cargo = cargo;
	}
	public List<Cargo> getCargos(){
		return cargos;
	}
	
	public void setCargos(List<Cargo> cargos) {
		this.cargos = cargos;
	}

	public List<Cargo> getCargosFiltrados() {
		return cargosFiltrados;
	}

	public void setCargosFiltrados(List<Cargo> cargosFiltrados) {
		this.cargosFiltrados = cargosFiltrados;
	}

	public void gravar(){
		try {
			if(cargo.getId() == null)
				dao.adiciona(cargo);
			else
				dao.atualiza(cargo);
		}
		catch (PersistenceException e) {
			try {
				if(e.getCause().toString().contains("Duplicate")) ContextUtil.getDuplicateMessage("Cargo inserido");
			}catch(Exception er){
				er.printStackTrace();
			}
		}
		catch(Exception e){
			ContextUtil.getAnyMessage("Ocorreu um erro!");
		}finally{
			atualizaLista();
		}
	}

	public void deletar(){
		try {
			dao.remove(cargo);
			atualizaLista();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void limparCargo(){
		this.cargo = new Cargo();
	}
	
	public boolean isNovo(){
		return this.cargo.getId() == null;
	}
	
	public boolean isSemFuncionarios(){
		return this.cargo.getFuncionarios() == null || 
					this.cargo.getFuncionarios().isEmpty();
	}
	
	public boolean isDeletavel(){
		return !isNovo() && isSemFuncionarios();
	}
}
