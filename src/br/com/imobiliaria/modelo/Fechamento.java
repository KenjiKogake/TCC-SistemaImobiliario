package br.com.imobiliaria.modelo;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
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
	@NamedQuery(name="fechamentosDoComprador", query="SELECT f FROM Fechamento f WHERE f.negociacao.comprador.id = :pId"),
	@NamedQuery(name="fechamentosDoApartamento", query="SELECT f FROM Fechamento f WHERE f.negociacao.apartamento.id = :pId")
})

@Entity
public class Fechamento extends br.com.imobiliaria.modelo.Entity{
	private static final long serialVersionUID = 1L;

	@Temporal(TemporalType.DATE)
	private Calendar dataFechamento = Calendar.getInstance();
	
	@OneToOne(optional=false) @NotNull(message="O Fechamento não possui uma negociação.")
	private Negociacao negociacao;
	
	@Enumerated(EnumType.STRING)
	private StatusFechamento status = StatusFechamento.Andamento;
	
	@OneToMany(mappedBy="fechamento", fetch=FetchType.LAZY)
	private List<FollowUpFechamento> followUps = new ArrayList<FollowUpFechamento>();

	@PostPersist
	public void postPersist(){
		new AcaoFeitaBean().gravar(new AcaoFeita(TipoAcao.Incluiu, Cadastro.Fechamento, this.getId(), this.toString()));
		
		ContextUtil.getPersistMessage(this.toString());
	}
	
	@PostUpdate
	public void postUpdate(){
		new AcaoFeitaBean().gravar(new AcaoFeita(TipoAcao.Alterou, Cadastro.Fechamento, this.getId(), this.toString()));
		
		ContextUtil.getUpdateMessage(this.toString());
	}
	
	public Calendar getDataFechamento() {
		return dataFechamento;
	}

	public void setDataFechamento(Calendar dataFechamento) {
		this.dataFechamento = dataFechamento;
	}

	public Negociacao getNegociacao() {
		return negociacao;
	}

	public void setNegociacao(Negociacao negociacao) {
		this.negociacao = negociacao;
	}

	public List<FollowUpFechamento> getFollowUps() {
		return new DAO<FollowUpFechamento>(FollowUpFechamento.class).listaPorId("followUpsDoFechamento", this.getId());
	}

	public void setFollowUps(List<FollowUpFechamento> followUps) {
		this.followUps = followUps;
	}

	public StatusFechamento getStatus() {
		return status;
	}

	public void setStatus(StatusFechamento status) {
		this.status = status;
	}
	
	@Override
	public String toString() {
		return this.getNegociacao().getComprador().getCliente() + " X " + this.getNegociacao().getApartamento().getVendedor().getCliente();
	}
}
