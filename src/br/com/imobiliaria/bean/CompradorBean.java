package br.com.imobiliaria.bean;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.imobiliaria.dao.DAO;
import br.com.imobiliaria.modelo.ApartamentosFavoritos;
import br.com.imobiliaria.modelo.Cliente;
import br.com.imobiliaria.modelo.Comprador;
import br.com.imobiliaria.modelo.CompradorDoCorretor;
import br.com.imobiliaria.modelo.Email;
import br.com.imobiliaria.modelo.Funcionario;
import br.com.imobiliaria.modelo.Status;
import br.com.imobiliaria.util.ContextUtil;

@ManagedBean
@ViewScoped
public class CompradorBean implements Serializable{
	private static final long serialVersionUID = 1L;
	private DAO<Comprador> dao = new DAO<Comprador>(Comprador.class);
	private Comprador comprador = new Comprador();
	private List<Comprador> compradoresFiltrados;
	private List<Comprador> compradores;
	private List<Comprador> ultimosCadastrados;
	
	private List<ApartamentosFavoritos> favoritosSelecionados;
	
	private ApartamentosFavoritos favoritoSelecionado;
	
	DAO<CompradorDoCorretor> daoCorretor = new DAO<CompradorDoCorretor>(CompradorDoCorretor.class);
	
	private Long id;
	
	private Funcionario corretor;
	
	public List<ApartamentosFavoritos> getFavoritosSelecionados() {
		return favoritosSelecionados;
	}
	
	public void setFavoritosSelecionados(
			List<ApartamentosFavoritos> favoritosSelecionados) {
		this.favoritosSelecionados = favoritosSelecionados;
	}
	
	public ApartamentosFavoritos getFavoritoSelecionado() {
		return favoritoSelecionado;
	}

	public void setFavoritoSelecionado(ApartamentosFavoritos favoritoSelecionado) {
		this.favoritoSelecionado = favoritoSelecionado;
	}
	
	public List<Comprador> getUltimosCadastrados() {
		if(ultimosCadastrados == null) atualizaUltimosCadastrados();
		return ultimosCadastrados;
	}
	
	public void atualizaUltimosCadastrados(){
		ultimosCadastrados = dao.listaNamedQuery("compradoresDecrescente", 10);
	}
	
	public Funcionario getCorretor() {
		return corretor;
	}

	public void setCorretor(Funcionario corretor) {
		this.corretor = corretor;
	}
	
	public void atualizaLista() {
		this.compradores = dao.listaTodos();
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<Comprador> getCompradoresFiltrados() {
		return compradoresFiltrados;
	}

	public void setCompradoresFiltrados(List<Comprador> compradoresFiltrados) {
		this.compradoresFiltrados = compradoresFiltrados;
	}

	public void setCompradores(List<Comprador> compradores) {
		this.compradores = compradores;
	}
	
	public Comprador getComprador() {
		return this.comprador;
	}
	
	public void setComprador(Comprador Comprador) {
		this.comprador = Comprador;
	}
	
	public List<Comprador> getCompradores(){
		return this.compradores;
	}
	
	public void onActionView(){
		System.out.println("Action View");
		if(this.comprador.getId() != this.getId() && this.getId() != null){
			this.comprador = dao.buscaPorId(this.getId());
		}
	}
	
	public void gravar(Cliente cliente){
		try {
			if(comprador.getId() == null){
				comprador.setCliente(cliente);
				dao.adiciona(comprador);
				inserirCorretor();
				redirectWithParam();
			}else
				dao.atualiza(comprador);
		} catch (Exception e) {
			ContextUtil.getAnyMessage("Ocorreu um erro!");
		}
	}
	
	public void escolherCliente(Cliente cliente){
		if(cliente.getComprador() != null)
			ContextUtil.getAnyMessage("O Cliente selecionado já é um comprador.");
		else
			this.comprador.setCliente(cliente);
	}
	
	public void limparComprador(){
		this.comprador = new Comprador();
	}
	
	public boolean isNovo(){
		return this.comprador.getId() == null;
	}
	
	public boolean isSemCliente(){
		if(isClienteNull())
			return true;
		if(this.comprador.getCliente().getId() == null)
			return true;
		
		return false;
	}
	
	public boolean isClienteNull(){
		return this.comprador.getCliente() == null;
	}
	
	public void novoCliente(){
		this.comprador.setCliente(new Cliente());
	}
	
	public void desativar(){
		
	}
	
	public void onRowSelect(){
		redirectWithParam();
	}

	private void redirectWithParam() {
		ContextUtil.redirect("comprador.xhtml?id=" + comprador.getId());
	}
	
	public void transferirCorretor(){
		CompradorDoCorretor corretorDoComprador = this.comprador.getCorretorResponsavel();
		if(!corretor.equals(corretorDoComprador.getCorretor())){
			corretorDoComprador.setStatus(Status.Inativo);
			corretorDoComprador.setDataFim(Calendar.getInstance().getTime());
			
			try {
				daoCorretor.atualiza(corretorDoComprador);
				inserirCorretor();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private void inserirCorretor() throws Exception {
		daoCorretor.adiciona(new CompradorDoCorretor(this.comprador, this.corretor));
	}
	
	public void enviaPorEmail(){
		if(favoritosSelecionados.isEmpty())
			ContextUtil.getAnyMessage("Selecione um apartamento para ser enviado");
		else{
			new Email().enviaEmailApartamentosFavoritos(favoritosSelecionados);
		}
	}
	
	public void alterarStatusFavorito(ApartamentosFavoritos a){
		if(a.getStatus() == Status.Ativo){
			a.setStatus(Status.Inativo);
		}else
			a.setStatus(Status.Ativo);
		try {
			new DAO<ApartamentosFavoritos>(ApartamentosFavoritos.class).atualiza(a);
		} catch (Exception e) {
			ContextUtil.getAnyMessage("Ocorreu um erro.");
			e.printStackTrace();
		}
	}
}
