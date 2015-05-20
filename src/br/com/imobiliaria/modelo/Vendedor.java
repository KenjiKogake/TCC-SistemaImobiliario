package br.com.imobiliaria.modelo;

import java.util.Calendar;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PostPersist;
import javax.persistence.PostRemove;
import javax.persistence.PostUpdate;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.com.imobiliaria.bean.AcaoFeitaBean;
import br.com.imobiliaria.dao.DAO;

@Entity
public class Vendedor extends br.com.imobiliaria.modelo.Entity{
	
	private static final long serialVersionUID = 1L;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar dataCadastro;
	
	@ManyToOne
	private Funcionario quemCadastrou;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar dataUltimaAlteracao;
	
	@ManyToOne
	private Funcionario quemAlterou;
	
	@OneToOne
	private Cliente cliente;
	
	@OneToMany(mappedBy = "vendedor", fetch=FetchType.LAZY)
	private List<Apartamento> apartamentos;
	
	@PrePersist
	public void prePersist(){
		this.setDataCadastro(Calendar.getInstance());
		this.setQuemCadastrou(FuncionarioLogado.getFuncionarioLogado());
	}
	
	@PreUpdate
	public void preUpdate(){
		this.setDataUltimaAlteracao(Calendar.getInstance());
		this.setQuemAlterou(FuncionarioLogado.getFuncionarioLogado());
	}
	
	@PostPersist
	public void postPersist(){
		new AcaoFeitaBean().gravar(new AcaoFeita(TipoAcao.Incluiu, Cadastro.Vendedor, this.getId(), this.toString()));
		
	}
	
	@PostRemove
	public void postRemove(){
		new AcaoFeitaBean().gravar(new AcaoFeita(TipoAcao.Deletou, Cadastro.Vendedor, this.getId(), this.toString()));
	}
	
	@PostUpdate
	public void postUpdate(){
		new AcaoFeitaBean().gravar(new AcaoFeita(TipoAcao.Alterou, Cadastro.Vendedor, this.getId(), this.toString()));
	}
	
	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		System.out.println("vendedor pegou cliente "+cliente.getNome());
		this.cliente = cliente;
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

	public Calendar getDataUltimaAlteracao() {
		return dataUltimaAlteracao;
	}

	public void setDataUltimaAlteracao(Calendar dataUltimaAlteracao) {
		this.dataUltimaAlteracao = dataUltimaAlteracao;
	}

	public Funcionario getQuemAlterou() {
		return quemAlterou;
	}

	public void setQuemAlterou(Funcionario quemAlterou) {
		this.quemAlterou = quemAlterou;
	}

	public List<Apartamento> getApartamentos() {
		return new DAO<Apartamento>(Apartamento.class).listaPorId("apartamentosDoVendedor", this.getId());
	}

	public void setApartamentos(List<Apartamento> apartamentos) {
		this.apartamentos = apartamentos;
	}
	
	@Override
	public String toString() {
		return this.cliente.toString();
	}
	
}
