package br.com.imobiliaria.modelo;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PostPersist;
import javax.persistence.PostRemove;
import javax.persistence.PostUpdate;

import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;
import org.hibernate.validator.constraints.NotEmpty;

import br.com.imobiliaria.bean.AcaoFeitaBean;
import br.com.imobiliaria.dao.DAO;
import br.com.imobiliaria.util.ContextUtil;

@NamedQueries({ @NamedQuery(name = "plantasDaTorre", query = "SELECT p FROM PlantaTorre p WHERE p.torre.id = :pId") })
@Entity
public class PlantaTorre extends EntityPadrao {

	private static final long serialVersionUID = 1L;
	
	@OneToMany(mappedBy="planta", fetch=FetchType.LAZY)
	private List<Apartamento> apartamentos;

	@NotEmpty(message = "{campo.vazio}Descrição.")
	private String descricao;
	@NotEmpty(message = "{campo.vazio}Andares.")
	private String andares;
	@NotEmpty(message = "{campo.vazio}Finais.")
	private String finais;

	private int dormitorios; //OK
	private int suites; //OK
	private int cozinhas; //OK
	private int closets; //OK
	private int areasDeServico; //OK
	private int salaDeEstar; //OK
	private int salaDeJantar; //OK
	private int sala; //OK
	private int banheirosSociais; //OK
	private int homeOffices; //OK
	private int copas; //OK
	private int despensas; //OK
	private int dependenciasEmpregada; 
	private int banheirosServico; //OK
	private int depositos; //OK
	private int varandas; //OK
	private int varandasGourmet; //OK
	private int terceiroOpcional; //OK

	@ManyToOne(optional=false)
	private Torre torre;
	
	@PostPersist
	public void postPersist(){
		new AcaoFeitaBean().gravar(new AcaoFeita(TipoAcao.Incluiu, Cadastro.Planta_da_Torre, this.getId(), this.toString()));
		ContextUtil.getPersistMessage(this.toString());
	}
	
	@PostRemove
	public void postRemove(){
		new AcaoFeitaBean().gravar(new AcaoFeita(TipoAcao.Deletou, Cadastro.Planta_da_Torre, this.getId(), this.toString()));
		ContextUtil.getDeleteMessage(this.toString());
	}
	
	@PostUpdate
	public void postUpdate(){
		new AcaoFeitaBean().gravar(new AcaoFeita(TipoAcao.Alterou, Cadastro.Planta_da_Torre, this.getId(), this.toString()));
		ContextUtil.getUpdateMessage(this.toString());
	}
	
	public List<Apartamento> getApartamentos() {
		return new DAO<Apartamento>(Apartamento.class).listaPorId("apartamentosDaPlanta", this.getId());
	}
	
	public void setApartamentos(List<Apartamento> apartamentos) {
		this.apartamentos = apartamentos;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getAndares() {
		return andares;
	}

	public void setAndares(String andares) {
		this.andares = andares;
	}

	public String getFinais() {
		return finais;
	}

	public void setFinais(String finais) {
		this.finais = finais;
	}

	public int getDormitorios() {
		return dormitorios;
	}

	public void setDormitorios(int dormitorios) {
		this.dormitorios = dormitorios;
	}

	public int getSuites() {
		return suites;
	}

	public void setSuites(int suites) {
		this.suites = suites;
	}

	public int getCozinhas() {
		return cozinhas;
	}

	public void setCozinhas(int cozinhas) {
		this.cozinhas = cozinhas;
	}

	public int getClosets() {
		return closets;
	}

	public void setClosets(int closets) {
		this.closets = closets;
	}

	public int getAreasDeServico() {
		return areasDeServico;
	}

	public void setAreasDeServico(int areasDeServico) {
		this.areasDeServico = areasDeServico;
	}

	public int getSalaDeEstar() {
		return salaDeEstar;
	}

	public void setSalaDeEstar(int salaDeEstar) {
		this.salaDeEstar = salaDeEstar;
	}

	public int getSalaDeJantar() {
		return salaDeJantar;
	}

	public void setSalaDeJantar(int salaDeJantar) {
		this.salaDeJantar = salaDeJantar;
	}

	public int getSala() {
		return sala;
	}

	public void setSala(int sala) {
		this.sala = sala;
	}

	public int getBanheirosSociais() {
		return banheirosSociais;
	}

	public void setBanheirosSociais(int banheirosSociais) {
		this.banheirosSociais = banheirosSociais;
	}

	public int getHomeOffices() {
		return homeOffices;
	}

	public void setHomeOffices(int homeOffices) {
		this.homeOffices = homeOffices;
	}

	public int getCopas() {
		return copas;
	}

	public void setCopas(int copas) {
		this.copas = copas;
	}

	public int getDespensas() {
		return despensas;
	}

	public void setDespensas(int despensas) {
		this.despensas = despensas;
	}

	public int getDependenciasEmpregada() {
		return dependenciasEmpregada;
	}

	public void setDependenciasEmpregada(int dependenciasEmpregada) {
		this.dependenciasEmpregada = dependenciasEmpregada;
	}

	public int getBanheirosServico() {
		return banheirosServico;
	}

	public void setBanheirosServico(int banheirosServico) {
		this.banheirosServico = banheirosServico;
	}

	public int getDepositos() {
		return depositos;
	}

	public void setDepositos(int depositos) {
		this.depositos = depositos;
	}

	public int getVarandas() {
		return varandas;
	}

	public void setVarandas(int varandas) {
		this.varandas = varandas;
	}

	public int getVarandasGourmet() {
		return varandasGourmet;
	}

	public void setVarandasGourmet(int varandasGourmet) {
		this.varandasGourmet = varandasGourmet;
	}

	public int getTerceiroOpcional() {
		return terceiroOpcional;
	}

	public void setTerceiroOpcional(int terceiroOpcional) {
		this.terceiroOpcional = terceiroOpcional;
	}

	public Torre getTorre() {
		return torre;
	}

	public void setTorre(Torre torre) {
		this.torre = torre;
	}
	
	@Override
	public String toString() {
		return this.descricao;
	}
}
