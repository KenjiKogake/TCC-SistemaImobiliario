package br.com.imobiliaria.modelo;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.PostPersist;
import javax.persistence.PostRemove;
import javax.persistence.PostUpdate;

import org.hibernate.validator.constraints.NotEmpty;

import br.com.imobiliaria.bean.AcaoFeitaBean;
import br.com.imobiliaria.dao.DAO;
import br.com.imobiliaria.util.ContextUtil;

@Entity
public class Departamento extends EntityPadrao{
	private static final long serialVersionUID = 1L;

	@NotEmpty(message="{campo.vazio}Departamento.") @Column(unique=true)
	private String descricao;
	
	@OneToMany(mappedBy="departamento", fetch=FetchType.LAZY)
	private List<Funcionario> funcionarios;
	
	@PostPersist
	public void postPersist(){
		new AcaoFeitaBean().gravar(new AcaoFeita(TipoAcao.Incluiu, Cadastro.Departamento, this.getId(), this.toString()));
		ContextUtil.getPersistMessage(this.descricao);
	}
	
	@PostRemove
	public void postRemove(){
		new AcaoFeitaBean().gravar(new AcaoFeita(TipoAcao.Deletou, Cadastro.Departamento, this.getId(), this.toString()));
		ContextUtil.getDeleteMessage(this.descricao);
	}
	
	@PostUpdate
	public void postUpdate(){
		new AcaoFeitaBean().gravar(new AcaoFeita(TipoAcao.Alterou, Cadastro.Departamento, this.getId(), this.toString()));
		ContextUtil.getUpdateMessage(this.descricao);
	}
	
	public String getDescricao() {
		return descricao;
	}
	
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public List<Funcionario> getFuncionarios() {
		return new DAO<Funcionario>(Funcionario.class).listaPorId("funcionariosDoDepartamento", this.getId());
	}

	public void setFuncionarios(List<Funcionario> funcionarios) {
		this.funcionarios = funcionarios;
	}
	
	@Override
	public String toString() {
		return this.getDescricao();
	}
}
