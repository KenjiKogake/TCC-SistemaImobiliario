package br.com.imobiliaria.modelo;

import java.util.Calendar;

import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@MappedSuperclass
public class EntityPadrao extends Entity{
	private static final long serialVersionUID = 1L;

	@Temporal(TemporalType.TIMESTAMP)
	private Calendar dataCadastro;
	
	@ManyToOne
	private Funcionario quemCadastrou;
	
	@PrePersist
	public void prePersist(){
		this.dataCadastro = Calendar.getInstance();
		this.quemCadastrou = FuncionarioLogado.getFuncionarioLogado();
	}
	
	public Calendar getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(Calendar dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public Funcionario getQuemCadastrou() {
		return quemCadastrou;
	}

	public void setQuemCadastrou(Funcionario quemCadastrou) {
		this.quemCadastrou = quemCadastrou;
	}
}
