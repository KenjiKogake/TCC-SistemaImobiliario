package br.com.imobiliaria.modelo;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.PostPersist;
import javax.persistence.PostRemove;
import javax.persistence.PostUpdate;

import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;

import br.com.imobiliaria.bean.AcaoFeitaBean;
import br.com.imobiliaria.util.ContextUtil;

@NamedQueries({
	@NamedQuery(name="perfilDeInteresseDoComprador", query="SELECT p FROM PerfilInteresse p WHERE p.comprador.id = :pId"),
})

@Entity
public class PerfilInteresse extends EntityPadrao{
	private static final long serialVersionUID = 1L;

	@ManyToOne
	private Comprador comprador;
	
	private String enderecoReferencia;
	
	private double valorInicial;
	
	private double valorFinal;
	
	private int dormsInicial;
	
	private int dormsFinal;
	
	private int vagasInicial;
	
	private int vagasFinal;
	
	private int suitesInicial;
	
	private int suitesFinal;
	
	private boolean varanda;
	
	private boolean varandaGourmet;
	
	private double areaUtilInicial;
	
	private double areaUtilFinal;
	
	@ManyToOne
	private TipoApartamento tipoApartamento;
	
	@PostPersist
	public void postPersist(){
		new AcaoFeitaBean().gravar(new AcaoFeita(TipoAcao.Incluiu, Cadastro.Perfil_de_Interesse, this.getId(), this.toString()));
		ContextUtil.getPersistMessage(this.toString());
	}
	
	@PostRemove
	public void postRemove(){
		new AcaoFeitaBean().gravar(new AcaoFeita(TipoAcao.Deletou, Cadastro.Perfil_de_Interesse, this.getId(), this.toString()));
		ContextUtil.getDeleteMessage(this.toString());
	}
	
	@PostUpdate
	public void postUpdate(){
		new AcaoFeitaBean().gravar(new AcaoFeita(TipoAcao.Alterou, Cadastro.Perfil_de_Interesse, this.getId(), this.toString()));
		ContextUtil.getUpdateMessage(this.toString());
	}
	
	public Comprador getComprador() {
		return comprador;
	}

	public void setComprador(Comprador comprador) {
		this.comprador = comprador;
	}

	public String getEnderecoReferencia() {
		return enderecoReferencia;
	}

	public void setEnderecoReferencia(String enderecoReferencia) {
		this.enderecoReferencia = enderecoReferencia;
	}

	public double getValorInicial() {
		return valorInicial;
	}

	public void setValorInicial(double valorInicial) {
		this.valorInicial = valorInicial;
	}

	public double getValorFinal() {
		return valorFinal;
	}

	public void setValorFinal(double valorFinal) {
		this.valorFinal = valorFinal;
	}

	public int getDormsInicial() {
		return dormsInicial;
	}

	public void setDormsInicial(int dormsInicial) {
		this.dormsInicial = dormsInicial;
	}

	public int getDormsFinal() {
		return dormsFinal;
	}

	public void setDormsFinal(int dormsFinal) {
		this.dormsFinal = dormsFinal;
	}

	public int getVagasInicial() {
		return vagasInicial;
	}

	public void setVagasInicial(int vagasInicial) {
		this.vagasInicial = vagasInicial;
	}

	public int getVagasFinal() {
		return vagasFinal;
	}

	public void setVagasFinal(int vagasFinal) {
		this.vagasFinal = vagasFinal;
	}

	public int getSuitesInicial() {
		return suitesInicial;
	}

	public void setSuitesInicial(int suitesInicial) {
		this.suitesInicial = suitesInicial;
	}

	public int getSuitesFinal() {
		return suitesFinal;
	}

	public void setSuitesFinal(int suitesFinal) {
		this.suitesFinal = suitesFinal;
	}

	public boolean isVaranda() {
		return varanda;
	}

	public void setVaranda(boolean varanda) {
		this.varanda = varanda;
	}

	public boolean isVarandaGourmet() {
		return varandaGourmet;
	}

	public void setVarandaGourmet(boolean varandaGourmet) {
		this.varandaGourmet = varandaGourmet;
	}

	public double getAreaUtilInicial() {
		return areaUtilInicial;
	}

	public void setAreaUtilInicial(double areaUtilInicial) {
		this.areaUtilInicial = areaUtilInicial;
	}

	public double getAreaUtilFinal() {
		return areaUtilFinal;
	}

	public void setAreaUtilFinal(double areaUtilFinal) {
		this.areaUtilFinal = areaUtilFinal;
	}

	public TipoApartamento getTipoApartamento() {
		return tipoApartamento;
	}

	public void setTipoApartamento(TipoApartamento tipoApartamento) {
		this.tipoApartamento = tipoApartamento;
	}
	
	@Override
	public String toString() {
		return "Perfil de interesse do comprador " + this.comprador.getCliente().getNome(); 
	}
}