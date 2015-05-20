package br.com.imobiliaria.modelo;

import java.util.Calendar;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.PostPersist;
import javax.persistence.PrePersist;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.validator.constraints.NotEmpty;

import br.com.imobiliaria.bean.AcaoFeitaBean;
import br.com.imobiliaria.util.ContextUtil;

@NamedQueries({
	@NamedQuery(name="followupsdoComprador", query="SELECT f FROM FollowUpComprador f WHERE f.comprador.id = :pId"),
	@NamedQuery(name="ultimoFollowUp", query="SELECT f FROM FollowUpComprador f WHERE f.comprador.id = :pId ORDER BY f.data DESC"),
})

@Entity
public class FollowUpComprador extends br.com.imobiliaria.modelo.Entity {
	private static final long serialVersionUID = 1L;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar data;
	
	@ManyToOne
	private Funcionario funcionario;
	
	@NotEmpty(message="{campo.vazio}Follow-Up")
	private String descricao;
	
	private boolean atendimento;
	
	@Enumerated(EnumType.STRING)
	private FormaContato formaContato;
	
	@Enumerated(EnumType.STRING)
	private TipoContato tipoContato;
	
	@ManyToOne(optional=false)
	private Comprador comprador;
	
	@PrePersist
	public void prePersist(){
		this.setData(Calendar.getInstance());
		this.setFuncionario(FuncionarioLogado.getFuncionarioLogado());
	}
	
	@PostPersist
	public void postPersist(){
		new AcaoFeitaBean().gravar(new AcaoFeita(TipoAcao.Incluiu, Cadastro.Follow_Up_Cliente, this.getId(), this.descricao));
		ContextUtil.getPersistMessage(this.toString());
	}

	public Comprador getComprador() {
		return comprador;
	}

	public void setComprador(Comprador comprador) {
		this.comprador = comprador;
	}

	public Calendar getData() {
		return data;
	}
	
	public void setData(Calendar data) {
		this.data = data;
	}
	
	public Funcionario getFuncionario() {
		return funcionario;
	}
	
	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}
	
	public String getDescricao() {
		return descricao;
	}
	
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	public boolean isAtendimento() {
		return atendimento;
	}
	
	public void setAtendimento(boolean atendimento) {
		this.atendimento = atendimento;
	}

	public TipoContato getTipoContato() {
		return tipoContato;
	}

	public void setTipoContato(TipoContato tipoContato) {
		this.tipoContato = tipoContato;
	}

	public FormaContato getFormaContato() {
		return formaContato;
	}

	public void setFormaContato(FormaContato formaContato) {
		this.formaContato = formaContato;
	}
	
	@Override
	public String toString() {
		return this.getDescricao();
	}

}
