package br.com.imobiliaria.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.persistence.PersistenceException;

import br.com.imobiliaria.dao.DAOCliente;
import br.com.imobiliaria.modelo.Cliente;
import br.com.imobiliaria.modelo.Estado;
import br.com.imobiliaria.util.ContextUtil;
import br.com.imobiliaria.ws.BuscaCEP;

@ManagedBean
@ViewScoped
public class ClienteBean implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private DAOCliente dao = new DAOCliente();
	private Cliente cliente = new Cliente();
	private List<Cliente> clientesFiltrados;
	private List<Cliente> clientes;

	//Id vindo da View, para buscar o cliente
	private Long id;

	public void postConstruct() {
		atualizaLista();
	}

	public void atualizaLista() {
		this.clientes = dao.listaTodos();
	}
	
	public List<Cliente> getClientesFiltrados() {
		return clientesFiltrados;
	}

	public void setClientesFiltrados(List<Cliente> clientesFiltrados) {
		this.clientesFiltrados = clientesFiltrados;
	}

	public void setClientes(List<Cliente> clientes) {
		this.clientes = clientes;
	}

	public List<Cliente> getClientes() {
		return clientes;
	}
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public Cliente getCliente() {
		return this.cliente;
	}
	
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	
	public void gravar(){
		if(this.cliente.getSenha() == null) this.cliente.setSenha(cliente.getEmail());
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
		} finally {
			atualizaLista();
		}
	}
	
	public void onActionViewVendedor(){
		if(this.cliente.getId() != this.getId() && this.getId() != null){
			this.cliente = dao.buscaPorVendedor(this.getId());
		}
	}
	
	public void onActionViewComprador(){
		if(this.cliente.getId() != this.getId() && this.getId() != null){
			this.cliente = dao.buscaPorComprador(this.getId());
		}
	}
	
	public void onActionView(){
		if(this.cliente.getId() != this.getId() && this.getId() != null){
			this.cliente = dao.buscaPorId(this.getId());
		}
	}
	
	public void redirectWithParam(){
		ContextUtil.redirect("cliente.xhtml?id=" + cliente.getId());
	}
	
	public void limparCliente(){
		this.cliente = new Cliente();
	}
	
	public boolean isNovo(){
		if(this.cliente == null) return true;
		if(this.cliente.getId() == null) return true;

		return false;
	}
	
	public boolean isComprador(){
		if(isNovo()) return true;
		else if(this.cliente.getComprador() == null) return false;
		else if(this.cliente.getComprador().getId() == null) return false;
		return true;
	}
	
	public void buscaCEP(){
		ArrayList<String> buscaCep = BuscaCEP.buscarCEP(this.cliente.getCep());
		if(buscaCep.size()!=0){
			this.cliente.setCep(buscaCep.get(0));
			this.cliente.setLogradouro(buscaCep.get(1));
			this.cliente.setBairro(buscaCep.get(2));
			this.cliente.setCidade(buscaCep.get(3));
			this.cliente.setEstado(Enum.valueOf(Estado.class, buscaCep.get(4)));
		}
	}
	
	public void buscaCEPTrabalho(){
		ArrayList<String> buscaCep = BuscaCEP.buscarCEP(this.cliente.getCepTrabalho());
		if(buscaCep.size()!=0){
			this.cliente.setCepTrabalho(buscaCep.get(0));
			this.cliente.setLogradouroTrabalho(buscaCep.get(1));
			this.cliente.setBairroTrabalho(buscaCep.get(2));
			this.cliente.setCidadeTrabalho(buscaCep.get(3));
			this.cliente.setEstadoTrabalho(Enum.valueOf(Estado.class, buscaCep.get(4)));
		}
	}
	
	public void entrar(){
		dao.logar(cliente.getEmail(), cliente.getSenha());
	}
}