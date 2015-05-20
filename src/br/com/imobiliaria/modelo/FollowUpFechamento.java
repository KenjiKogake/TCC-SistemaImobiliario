package br.com.imobiliaria.modelo;

import java.util.Calendar;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.PrePersist;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.validator.constraints.NotEmpty;

@NamedQueries({
	@NamedQuery(name="followUpsDoFechamento", query="SELECT f FROM FollowUpFechamento f WHERE f.fechamento.id = :pId"),
})

@Entity
public class FollowUpFechamento extends br.com.imobiliaria.modelo.Entity{
private static final long serialVersionUID = 1L;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar data;
	
	@ManyToOne(optional=false)
	private Funcionario funcionario;
	
	@NotEmpty(message="{campo.vazio}Follow-Up.")
	private String descricao;
	
	@ManyToOne(optional=false)
	private Fechamento fechamento;

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

	public Fechamento getFechamento() {
		return fechamento;
	}

	public void setFechamento(Fechamento fechamento) {
		this.fechamento = fechamento;
	}
	
	@Override
	public String toString() {
		return this.descricao;
	}
}
