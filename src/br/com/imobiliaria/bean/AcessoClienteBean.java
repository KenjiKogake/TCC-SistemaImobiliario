package br.com.imobiliaria.bean;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.persistence.PersistenceException;

import br.com.imobiliaria.dao.DAOCliente;
import br.com.imobiliaria.modelo.Cliente;
import br.com.imobiliaria.modelo.ClienteLogado;
import br.com.imobiliaria.util.ContextUtil;

@ManagedBean
@ViewScoped
public class AcessoClienteBean implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private DAOCliente dao = new DAOCliente();
	private Cliente cliente = ClienteLogado.getClienteLogado();

	public Cliente getCliente() {
		return this.cliente;
	}
	
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	
	public void gravar(){
		if(this.cliente.getSenha().isEmpty()) this.cliente.setSenha(cliente.getEmail());
		try {
			if(cliente.getId() == null){
				dao.adiciona(cliente);
			}else{
				dao.atualiza(cliente);
			}
		} catch (PersistenceException e) {
			if (e.getCause().toString().contains("Duplicate")) {
				ContextUtil.getDuplicateMessage("Cliente inserido");
			}
		} catch (Exception e) {
			ContextUtil.getAnyMessage("Ocorreu um erro!");
		}
	}
	
	public boolean isComprador(){
		if(this.cliente.getComprador() == null) return false;
		else if(this.cliente.getComprador().getId() == null) return false;
		return true;
	}
	
	public boolean isVendedor(){
		if(this.cliente.getVendedor() == null) return false;
		else if(this.cliente.getVendedor().getId() == null) return false;
		return true;
	}
	
	public void logoff(){
		ClienteLogado.logoff();
	}
	
	public void desativar(){
		
	}
}