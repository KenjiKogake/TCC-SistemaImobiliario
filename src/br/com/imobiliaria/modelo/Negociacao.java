package br.com.imobiliaria.modelo;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PostPersist;
import javax.persistence.PostUpdate;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import br.com.imobiliaria.bean.AcaoFeitaBean;
import br.com.imobiliaria.dao.DAO;
import br.com.imobiliaria.util.ContextUtil;


@NamedQueries({
	@NamedQuery(name="negociacoesDoComprador", query="SELECT n FROM Negociacao n WHERE n.comprador.id = :pId"),
	@NamedQuery(name="negociacoesDoApartamento", query="SELECT n FROM Negociacao n WHERE n.apartamento.id = :pId"),
	@NamedQuery(name="negociacoesAtivasDoApartamento", query="SELECT n FROM Negociacao n WHERE n.apartamento.id = :pId AND n.status = 'Negociando'"),
})

@Entity
public class Negociacao extends br.com.imobiliaria.modelo.Entity{
	private static final long serialVersionUID = 1L;
	
	@Temporal(TemporalType.DATE)
	private Calendar dataProposta = Calendar.getInstance();
	
	@ManyToOne(optional=false) @NotNull(message="É necessário um Apartamento")
	private Apartamento apartamento;
	
	@ManyToOne(optional=false) @NotNull(message="É necessário um Comprador")
	private Comprador comprador;
	
	@ManyToOne(optional=false) @NotNull(message="É necessário um Corretor")
	private Funcionario corretor;
	
	@OneToOne
	private Visita visita;
	
	@OneToOne(mappedBy="negociacao")
	private Fechamento fechamento;
	
	@Enumerated(EnumType.STRING) @NotNull
	private StatusNegociacao status = StatusNegociacao.Negociando;
	
	private BigDecimal valorApartamento = new BigDecimal(0.0);
	
	private BigDecimal valorProposto = new BigDecimal(0.0);
	
	private BigDecimal valorImobiliaria = new BigDecimal(0.0);
	
	@OneToMany(mappedBy="negociacao", fetch=FetchType.LAZY)
	private List<FollowUpNegociacao> followUps = new ArrayList<FollowUpNegociacao>();
	
	@PostPersist
	public void postPersist(){
		new AcaoFeitaBean().gravar(new AcaoFeita(TipoAcao.Incluiu, Cadastro.Negociação, this.getId(), this.toString()));
		
		ContextUtil.getPersistMessage(this.toString());
	}
	
	
	@PostUpdate
	public void postUpdate(){
		new AcaoFeitaBean().gravar(new AcaoFeita(TipoAcao.Alterou, Cadastro.Negociação, this.getId(), this.toString()));
		ContextUtil.getUpdateMessage(this.toString());
	}
	
	public Calendar getDataProposta() {
		return dataProposta;
	}
	
	public String getDataFormatada(){
		return new SimpleDateFormat("dd/MM/yyyy").format(dataProposta.getTime());
	}

	public void setDataProposta(Calendar dataProposta) {
		this.dataProposta = dataProposta;
	}

	public Apartamento getApartamento() {
		return apartamento;
	}

	public void setApartamento(Apartamento apartamento) {
		this.apartamento = apartamento;
	}

	public Comprador getComprador() {
		return comprador;
	}

	public void setComprador(Comprador comprador) {
		this.comprador = comprador;
	}

	public Funcionario getCorretor() {
		return corretor;
	}

	public void setCorretor(Funcionario corretor) {
		this.corretor = corretor;
	}

	public Visita getVisita() {
		return visita;
	}

	public void setVisita(Visita visita) {
		this.visita = visita;
	}

	public BigDecimal getValorApartamento() {
		return valorApartamento;
	}

	public void setValorApartamento(BigDecimal valorApartamento) {
		this.valorApartamento = valorApartamento;
	}

	public BigDecimal getValorProposto() {
		return valorProposto;
	}

	public void setValorProposto(BigDecimal valorProposto) {
		this.valorProposto = valorProposto;
	}

	public BigDecimal getValorImobiliaria() {
		return valorImobiliaria;
	}

	public void setValorImobiliaria(BigDecimal valorImobiliaria) {
		this.valorImobiliaria = valorImobiliaria;
	}

	public BigDecimal getValorProprietario() {
		return valorProposto.subtract(valorImobiliaria);
	}
	
	public double getPercentualProposto(){
		return valorProposto.doubleValue() / valorApartamento.doubleValue();
	}
	
	public double getPercentualImobiliaria(){
		return valorImobiliaria.doubleValue() / valorProposto.doubleValue();
	}

	public StatusNegociacao getStatus() {
		return status;
	}

	public void setStatus(StatusNegociacao status) {
		this.status = status;
	}

	public List<FollowUpNegociacao> getFollowUps() {
		return new DAO<FollowUpNegociacao>(FollowUpNegociacao.class).listaPorId("followUpsDaNegociacao", this.getId());
	}

	public void setFollowUps(List<FollowUpNegociacao> followUps) {
		this.followUps = followUps;
	}

	public Fechamento getFechamento() {
		return fechamento;
	}

	public void setFechamento(Fechamento fechamento) {
		this.fechamento = fechamento;
	}
	
	@Override
	public String toString() {
		if(this.getId() != null) return this.getComprador().getCliente() + " propôs no " + 
			this.getApartamento() + " - " + this.getApartamento().getTorre();
		return "Negociação " + this.getId();
	}
}
