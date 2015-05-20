package br.com.imobiliaria.modelo;

import java.util.Calendar;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;
import org.hibernate.validator.constraints.NotEmpty;

@NamedQueries({ 
	@NamedQuery(name = "followUpsDoApartamento", query = "SELECT f FROM FollowUpApartamento f WHERE f.apartamento.id = :pId ORDER BY f.dataFollowUp DESC"),
	@NamedQuery(name = "followUpsPendentes", query = "SELECT f FROM FollowUpApartamento f WHERE f.status = 'Pendente' AND f.tipoFollowUp = 'Alteração' ORDER BY f.dataFollowUp DESC")
})

@Entity
public class FollowUpApartamento extends br.com.imobiliaria.modelo.Entity {
	private static final long serialVersionUID = 1L;

	@Temporal(TemporalType.TIMESTAMP)
	private Calendar dataFollowUp = Calendar.getInstance();
	
	@ManyToOne
	private Funcionario autor = FuncionarioLogado.getFuncionarioLogado();
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataRevisao;
	
	@ManyToOne
	private Funcionario revisor;

	@Enumerated(EnumType.STRING)
	private TipoFollowUp tipoFollowUp;

	@NotEmpty(message = "{campo.vazio}Comentário.")
	private String followUp;

	@ManyToOne(optional=false)
	private Apartamento apartamento;
	
	@Enumerated(EnumType.STRING)
	private StatusFollowUp status;

	public Calendar getDataFollowUp() {
		return dataFollowUp;
	}

	public void setDataFollowUp(Calendar dataFollowUp) {
		this.dataFollowUp = dataFollowUp;
	}

	public Funcionario getAutor() {
		return autor;
	}

	public void setAutor(Funcionario autor) {
		this.autor = autor;
	}

	public Date getDataRevisao() {
		return dataRevisao;
	}

	public void setDataRevisao(Date dataRevisao) {
		this.dataRevisao = dataRevisao;
	}

	public Apartamento getApartamento() {
		return apartamento;
	}

	public void setApartamento(Apartamento apartamento) {
		this.apartamento = apartamento;
	}

	public Funcionario getRevisor() {
		return revisor;
	}

	public void setRevisor(Funcionario revisor) {
		this.revisor = revisor;
	}

	public TipoFollowUp getTipoFollowUp() {
		return tipoFollowUp;
	}

	public void setTipoFollowUp(TipoFollowUp tipoFollowUp) {
		this.tipoFollowUp = tipoFollowUp;
	}

	public String getFollowUp() {
		return followUp;
	}

	public void setFollowUp(String followUp) {
		this.followUp = followUp;
	}

	public StatusFollowUp getStatus() {
		return status;
	}

	public void setStatus(StatusFollowUp status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return this.followUp;
	}
}
