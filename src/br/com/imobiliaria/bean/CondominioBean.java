package br.com.imobiliaria.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.event.DragDropEvent;
import org.primefaces.event.map.OverlaySelectEvent;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;

import br.com.imobiliaria.dao.DAO;
import br.com.imobiliaria.modelo.Condominio;
import br.com.imobiliaria.modelo.Estado;
import br.com.imobiliaria.modelo.Transporte;
import br.com.imobiliaria.modelo.TransportesDoCondominio;
import br.com.imobiliaria.util.ContextUtil;
import br.com.imobiliaria.ws.BuscaCEP;
import br.com.imobiliaria.ws.GoogleDirections;
import br.com.imobiliaria.ws.GoogleGeocoding;

@ManagedBean
@ViewScoped
public class CondominioBean implements Serializable {
	private static final long serialVersionUID = 1L;

	private DAO<Condominio> dao = new DAO<Condominio>(Condominio.class);
	private Condominio condominio = new Condominio();
	private List<Condominio> condominios;
	private List<Condominio> condominiosFiltrados;
	private TransportesDoCondominio transporteDoCondominio;
	
	private List<Condominio> ultimosCadastrados;
	
	private MapModel markerMap;
	
	private final static MapModel advancedModel = new DefaultMapModel();
	
	private Long id;

	private String endereco = "";

	public void atualizaLista(){
		this.condominios = dao.listaTodos();
	}
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public Condominio getCondominio() {
		return this.condominio;
	}
	
	public void setCondominio(Condominio condominio) {
		this.condominio = condominio;
	}
	
	public List<Condominio> getUltimosCadastrados() {
		if(ultimosCadastrados == null) atualizaUltimosCadastrados();
		return ultimosCadastrados;
	}
	
	public void atualizaUltimosCadastrados(){
		ultimosCadastrados = dao.listaNamedQuery("condominiosDecrescente", 10);
	}

	public TransportesDoCondominio getTransporteDoCondominio() {
		return transporteDoCondominio;
	}

	public void setTransporteDoCondominio(
			TransportesDoCondominio transporteDoCondominio) {
		this.transporteDoCondominio = transporteDoCondominio;
	}

	public List<Transporte> getTransportesDisponiveis(){
		
		List<Transporte> disponiveis = new DAO<Transporte>(Transporte.class).listaTodos();
		for (TransportesDoCondominio t : this.condominio.getTransportesDoCondominio()) {
			disponiveis.remove(t.getId().getTransporte());
		}
		
		return disponiveis;
	}
	
	public List<Condominio> getCondominios() {
		return condominios;
	}

	public void setCondominios(List<Condominio> condominios) {
		this.condominios = condominios;
	}

	public List<Condominio> getCondominiosFiltrados() {
		return condominiosFiltrados;
	}

	public void setCondominiosFiltrados(List<Condominio> condominiosFiltrados) {
		this.condominiosFiltrados = condominiosFiltrados;
	}

	public void gravar(){
		verificarEndereco();
		
		try {
			if(condominio.getId() == null){
				dao.adiciona(condominio);
				redirectWithParam();
				
			}else{
				dao.atualiza(condominio);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("static-access")
	private void verificarEndereco() {
		if(!endereco.equals(condominio.getEndereco())){
			List<Double> result = new GoogleGeocoding().calcular(condominio.getEndereco());
			this.condominio.setLatitude(result.get(0));
			this.condominio.setLongitude(result.get(1));
		}
	}
	
	public void onActionView(){
		if(this.condominio.getId() != this.getId() && this.getId() != null){
			this.condominio = dao.buscaPorId(this.getId());
			endereco = this.condominio.getEndereco();
		}
	}
	
	public void onRowSelectNoRedirect(Condominio condominio){
		this.condominio = condominio;
	}
	
	public void onRowSelect(){
		redirectWithParam();
	}
	
	public void redirectWithParam(){
		ContextUtil.redirect("condominio.xhtml?id=" + condominio.getId());
	}
	
	public boolean isNovo(){
		return this.condominio.getId() == null;
	}
	
	public boolean isSemTorres(){
		return this.condominio.getTorres() == null || this.condominio.getTorres().isEmpty();
	}
	
	public boolean isDeletavel(){
		return !isNovo() && isSemTorres();
	}
	
	public void buscaCep(){
		ArrayList<String> busca = BuscaCEP.buscarCEP(this.condominio.getCep());
		if(busca.size() != 0){
			this.condominio.setLogradouro(busca.get(1));
			this.condominio.setBairro(busca.get(2));
			this.condominio.setCidade(busca.get(3));
			this.condominio.setEstado(Estado.valueOf(busca.get(4)));
		}
	}
	
	public void onDrop(DragDropEvent ddEvent) {
        Transporte t = ((Transporte) ddEvent.getData());
  
		List<Integer> direcoes = GoogleDirections.calcular(t.getEndereco(), this.condominio.getEndereco());
		
        try {
			new DAO<TransportesDoCondominio>(TransportesDoCondominio.class).adiciona(new TransportesDoCondominio(t, this.condominio, direcoes.get(1), direcoes.get(0)));
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
	
	public void removerTransporte(){
		try {
			new DAO<TransportesDoCondominio>(TransportesDoCondominio.class).remove(this.transporteDoCondominio);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void marcarPontos(){
		markerMap = new DefaultMapModel();
		
		LatLng coordCond = new LatLng(this.condominio.getLatitude(), this.condominio.getLongitude());
		
		markerMap.addOverlay(new Marker(coordCond, this.condominio.getNome()));
	}
	
	public MapModel getMarkerMap() {
		marcarPontos();
		
		return markerMap;
	}
	
	public void marcarCondominios(){
		for (Condominio condominio : condominios) {
			try {
				LatLng coordCond = new LatLng(condominio.getLatitude(), condominio.getLongitude());
				advancedModel.addOverlay(new Marker(coordCond, condominio.getNome(), condominio));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public void onMarkerSelect(OverlaySelectEvent event) {
        this.condominio =  (Condominio) event.getOverlay().getData();
    }
	
	public MapModel getAdvancedModel() {
		marcarCondominios();
		
		return advancedModel;
	}
	
}
