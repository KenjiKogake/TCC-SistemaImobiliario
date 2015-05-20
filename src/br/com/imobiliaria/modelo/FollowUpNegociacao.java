package br.com.imobiliaria.modelo;

import java.util.Calendar;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.PrePersist;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@NamedQueries({
	@NamedQuery(name="followUpsDaNegociacao", query="SELECT f FROM FollowUpNegociacao f WHERE f.negociacao.id = :pId"),
})

@Entity
public class FollowUpNegociacao extends br.com.imobiliaria.modelo.Entity{
	private static final long serialVersionUID = 1L;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar data;
	
	@ManyToOne
	private Funcionario funcionario;
	
	private String descricao;
	
	@ManyToOne(optional=false)
	private Negociacao negociacao;

	@PrePersist
	public void prePersist(){
		data = Calendar.getInstance();
		funcionario = FuncionarioLogado.getFuncionarioLogado();
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
	
	public Negociacao getNegociacao() {
		return negociacao;
	}

	public void setNegociacao(Negociacao negociacao) {
		this.negociacao = negociacao;
	}

	@Override
	public String toString() {
		return this.getDescricao();
	}
}
