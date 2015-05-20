package br.com.imobiliaria.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.imobiliaria.dao.DAO;
import br.com.imobiliaria.modelo.Cliente;
import br.com.imobiliaria.modelo.Vendedor;
import br.com.imobiliaria.util.ContextUtil;


@ManagedBean
@ViewScoped
public class VendedorBean implements Serializable{
	private static final long serialVersionUID = 1L;
	private DAO<Vendedor> dao = new DAO<Vendedor>(Vendedor.class);
	private Vendedor vendedor = new Vendedor();
	private List<Vendedor> vendedoresFiltrados;
	private List<Vendedor> vendedores;
	private Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@PostConstruct
	public void postConstruct() {
		this.vendedores = dao.listaTodos();
	}

	public void atualizaLista() {
		this.vendedores = dao.listaTodos();
	}
	
	public List<Vendedor> getVendedoresFiltrados() {
		return vendedoresFiltrados;
	}

	public void setVendedoresFiltrados(
			List<Vendedor> vendedoresFiltrados) {
		this.vendedoresFiltrados = vendedoresFiltrados;
	}

	public void setVendedores(
			List<Vendedor> vendedores) {
		this.vendedores = vendedores;
	}
	
	public Vendedor getVendedor() {
		System.out.println("Chamando GetVendedor --------- vendedorbean: " + this.vendedor);
		return this.vendedor;
	}
	
	public void setVendedor(Vendedor Vendedor) {
		this.vendedor = Vendedor;
	}
	
	public List<Vendedor> getVendedores(){
		return this.vendedores;
	}

	public void limparVendedor(){
		this.vendedor = new Vendedor();
	}
	
	public boolean isNovo(){
		return this.vendedor.getId() == null;
	}
	
	public void desativar(){
		
	}
	
	public void gravar(Cliente c){
		System.out.println(this.vendedor.toString());
		this.vendedor.setCliente(c);
		try {
			if(vendedor.getId() == null)
				dao.adiciona(vendedor);
			else
				dao.atualiza(vendedor);
		} catch (Exception e) {
			e.printStackTrace();
			ContextUtil.getAnyMessage("Ocorreu um erro!");
		}
	}
}
