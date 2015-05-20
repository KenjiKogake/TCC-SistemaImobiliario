package br.com.imobiliaria.bean;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;

import br.com.imobiliaria.dao.ApartamentoStatus;
import br.com.imobiliaria.dao.DAO;
import br.com.imobiliaria.modelo.Apartamento;
import br.com.imobiliaria.modelo.ApartamentosFavoritos;
import br.com.imobiliaria.modelo.Cliente;
import br.com.imobiliaria.modelo.Comprador;
import br.com.imobiliaria.modelo.PlantaTorre;
import br.com.imobiliaria.modelo.StatusApartamento;
import br.com.imobiliaria.modelo.Torre;
import br.com.imobiliaria.modelo.Vendedor;
import br.com.imobiliaria.util.ContextUtil;

@ManagedBean
@ViewScoped
public class ApartamentoBean implements Serializable {
	private static final long serialVersionUID = 1L;

	private DAO<Apartamento> dao = new DAO<Apartamento>(Apartamento.class);
	private Apartamento apartamento = new Apartamento();
	private List<Apartamento> ultimosCadastrados;
	private List<Apartamento> apartamentosFiltrados;
	private List<Apartamento> apartamentos;
	private List<Apartamento> apartamentosSelecionados;
	private List<Apartamento> apartamentosAtivos;
	
	private StatusApartamento statusAnterior;
	
	private Long id;
	private Long idTorre;

	private MapModel markerMap;


	public List<Apartamento> getUltimosCadastrados() {
		if(ultimosCadastrados == null) atualizaUltimosCadastrados();
		return ultimosCadastrados;
	}

	public void atualizaUltimosCadastrados(){
		ultimosCadastrados = dao.listaNamedQuery("apartamentosDecrescente", 10);
	}
	
	public List<Apartamento> getApartamentosAtivos() {
		if(apartamentosAtivos == null) atualizaApartamentosAtivos();
		return apartamentosAtivos;
	}
	
	public void atualizaApartamentosAtivos(){
		apartamentosAtivos = dao.listaNamedQuery("apartamentosAtivos");
	}
	
	public List<Apartamento> getApartamentosFiltrados() {
		return apartamentosFiltrados;
	}

	public void setApartamentosFiltrados(List<Apartamento> apartamentosFiltrados) {
		this.apartamentosFiltrados = apartamentosFiltrados;
	}

	public List<Apartamento> getApartamentosSelecionados() {
		return apartamentosSelecionados;
	}

	public void setApartamentosSelecionados(
			List<Apartamento> apartamentosSelecionados) {
		this.apartamentosSelecionados = apartamentosSelecionados;
	}

	public List<Apartamento> getApartamentos() {
		return apartamentos;
	}

	public void setApartamentos(List<Apartamento> apartamentos) {
		this.apartamentos = apartamentos;
	}

	public Long getIdTorre() {
		return idTorre;
	}

	public void setIdTorre(Long idTorre) {
		this.idTorre = idTorre;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Apartamento getApartamento() {
		return this.apartamento;
	}

	public void setApartamento(Apartamento apartamento) {
		this.apartamento = apartamento;
	}

	public void atualizaLista() {
		apartamentos = dao.listaTodos();
	}

	public void gravar() {
		try {
			if (apartamento.getId() == null) {
				if (apartamento.getVendedor() == null) {
					ContextUtil.getAnyMessage("Não foi possível salvar o apartamento, é necessária a seleção de um vendedor.");
					return;
				} else {
					if (apartamento.getVendedor().getId() == null) {
						DAO<Vendedor> daoV = new DAO<Vendedor>(Vendedor.class);
						daoV.adiciona(apartamento.getVendedor());
					}
				}
				dao.adiciona(apartamento);
				redirectWithParam();
			} else {
				dao.atualiza(apartamento);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void redirectWithParam() {
		ContextUtil.redirect("apartamento.xhtml?id=" + apartamento.getId());
	}

	public void onActionView() {
		if (this.apartamento.getId() != this.getId() && this.getId() != null){
			this.apartamento = dao.buscaPorId(this.getId());
			this.statusAnterior = apartamento.getStatus();
		}
	}

	public void onActionViewTorre() {
		if (this.apartamento.getId() == null) {
			Torre torre = new DAO<Torre>(Torre.class).buscaPorId(this.getIdTorre());
			if (torre != null) {
				this.apartamento.setTorre(torre);
			}
		}
	}

	public void onRowSelectVendedor(Cliente cliente) {
		if (cliente.getVendedor() == null) {
			Vendedor v = new Vendedor();
			v.setCliente(cliente);
			apartamento.setVendedor(v);
		} else {
			this.apartamento.setVendedor(cliente.getVendedor());
		}
	}

	public void onRowSelectTorre(Torre torre) {
		this.apartamento.setTorre(torre);
	}

	public void onRowSelectPlanta(PlantaTorre planta) {
		this.apartamento.setPlanta(planta);
	}

	public void onRowSelect() throws IOException {
		redirectWithParam();
	}

	public boolean isNovo() {
		return this.apartamento.getId() == null;
	}

	public boolean isDeletavel() {
		return !isNovo();
	}
	
	public void alterarStatus(){
		if(statusAnterior != apartamento.getStatus()){
			if(statusAnterior == StatusApartamento.À_Venda){
				new ApartamentoStatus().desativarRelacionados(this.getApartamento().getId());
			}
			gravar();
			statusAnterior = apartamento.getStatus();
		}
	}
	
	public boolean isComVendedor(){
		return this.apartamento.getVendedor() != null;
	}
	
	public boolean isComTorre(){
		return this.apartamento.getTorre() != null;
	}
	
	public void adicionarFavoritos(Comprador c){
		DAO<ApartamentosFavoritos> daoFavoritos = new DAO<ApartamentosFavoritos>(ApartamentosFavoritos.class);
		for (Apartamento apartamento : apartamentosSelecionados) {
			try {
				daoFavoritos.adiciona(new ApartamentosFavoritos(apartamento, c));
			} catch (Exception e) {
				if(e.getCause().toString().contains("Duplicate")) ContextUtil.getDuplicateMessage("Favorito " + apartamento.getId());
				e.printStackTrace();
			}
		}
	}
	
	public boolean isVendendo(){
		return this.apartamento.getStatus() == StatusApartamento.À_Venda;
	}
	
	public boolean isNegociando(){
		return this.apartamento.getStatus() == StatusApartamento.Negociando;
	}
	
	public boolean isVendido(){
		return this.apartamento.getStatus() == StatusApartamento.Vendemos ||
				this.apartamento.getStatus() == StatusApartamento.Vendido;
	}
	
	public boolean isVendemos(){
		return this.apartamento.getStatus() == StatusApartamento.Vendemos;
	}
	
	public void marcarPontos(){
		markerMap = new DefaultMapModel();
		
		LatLng coordCond = new LatLng(this.apartamento.getTorre().getCondominio().getLatitude(), this.apartamento.getTorre().getCondominio().getLongitude());
		
		markerMap.addOverlay(new Marker(coordCond, this.apartamento.getTorre().getCondominio().getNome()));
	}
	
	public MapModel getMarkerMap() {
		marcarPontos();
		
		return markerMap;
	}
	
	public void statusVendeu(){
		this.apartamento.setStatus(StatusApartamento.Vendido);
		alterarStatus();
	}
	
	public void statusVendendo(){
		this.apartamento.setStatus(StatusApartamento.À_Venda);
		alterarStatus();
	}
	
	public void statusSuspendeu(){
		this.apartamento.setStatus(StatusApartamento.Suspendeu);
		alterarStatus();
	}
	
	public void statusDesistiu(){
		this.apartamento.setStatus(StatusApartamento.Desistiu);
		alterarStatus();		
	}
	
	public void statusAlugou(){
		this.apartamento.setStatus(StatusApartamento.Alugou);
		alterarStatus();
	}
	
}
