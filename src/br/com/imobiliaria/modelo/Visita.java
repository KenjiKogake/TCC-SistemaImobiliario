package br.com.imobiliaria.modelo;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.PostPersist;
import javax.persistence.PostUpdate;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import br.com.imobiliaria.bean.AcaoFeitaBean;
import br.com.imobiliaria.util.ContextUtil;

@NamedQueries({
	@NamedQuery(name="visitasDoComprador", query="SELECT v FROM Visita v WHERE v.comprador.id = :pId"),
	@NamedQuery(name="visitasDoApartamento", query="SELECT v FROM Visita v WHERE v.apartamento.id = :pId"),
})

@Entity
public class Visita extends br.com.imobiliaria.modelo.Entity{
	private static final long serialVersionUID = 1L;

	@ManyToOne(optional=false) @NotNull(message="É necessário um Apartamento")
	private Apartamento apartamento;
	
	@ManyToOne(optional=false) @NotNull(message="É necessário um Comprador")
	private Comprador comprador;
	
	@ManyToOne(optional=false) @NotNull(message="É necessário um Corretor")
	private Funcionario corretor;
	
	@Enumerated(EnumType.STRING) @NotNull
	private StatusVisita status = StatusVisita.Pendente;

	@Column(nullable=true)
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataConfirmacao;
	
	@Column(nullable=true)
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataCancelamento;
	
	private Calendar dataVisita = Calendar.getInstance();
	
	@OneToOne(mappedBy = "visita")
	private Negociacao negociacao;
	
	@PostPersist
	public void postPersist(){
		new AcaoFeitaBean().gravar(new AcaoFeita(TipoAcao.Incluiu, Cadastro.Visita, this.getId(), this.toString()));
		ContextUtil.getPersistMessage(this.toString());
	}
	
	@PostUpdate
	public void postUpdate(){
		new AcaoFeitaBean().gravar(new AcaoFeita(TipoAcao.Alterou, Cadastro.Visita, this.getId(), this.toString()));
		ContextUtil.getUpdateMessage(this.toString());
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

	public StatusVisita getStatus() {
		return status;
	}

	public void setStatus(StatusVisita status) {
		this.status = status;
	}

	public Date getDataConfirmacao() {
		return dataConfirmacao;
	}

	public void setDataConfirmacao(Date dataConfirmacao) {
		this.dataConfirmacao = dataConfirmacao;
	}

	public Date getDataCancelamento() {
		return dataCancelamento;
	}

	public void setDataCancelamento(Date dataCancelamento) {
		this.dataCancelamento = dataCancelamento;
	}

	public Calendar getDataVisita() {
		return dataVisita;
	}

	public void setDataVisita(Calendar dataVisita) {
		this.dataVisita = dataVisita;
	}

	public Negociacao getNegociacao() {
		return negociacao;
	}

	public void setNegociacao(Negociacao negociacao) {
		this.negociacao = negociacao;
	}
	
	@Override
	public String toString() {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy HH:mm");
		if(this.getId() != null) return "Visita ao Apto " + this.apartamento.getNumeroApartamento() + " às " + sdf.format(this.dataVisita.getTime());
		return "Visita " + this.getId();
	}
	
}
