package br.com.imobiliaria.modelo;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.PostPersist;
import javax.persistence.PostRemove;
import javax.persistence.PostUpdate;

import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import br.com.imobiliaria.bean.AcaoFeitaBean;
import br.com.imobiliaria.util.ContextUtil;

@NamedQueries({
	@NamedQuery(name="contatosDoCondominio", query="SELECT c FROM ContatoCondominio c WHERE c.condominio.id = :pId")
})

@Entity
public class ContatoCondominio extends EntityPadrao{
	private static final long serialVersionUID = 1L;

	@ManyToOne
	private Condominio condominio;
	@NotEmpty(message="{campo.vazio}Nome")
	private String nome;
	@Enumerated(EnumType.STRING)
	private CargoContatoCondominio cargo;
	private String telefone;
	private String celular;
	@Email(message="E-mail digitado inválido.")
	private String email;
	
	@PostPersist
	public void postPersist(){
		new AcaoFeitaBean().gravar(new AcaoFeita(TipoAcao.Incluiu, Cadastro.Contato_Condomínio, this.getId(), this.toString()));
		ContextUtil.getPersistMessage(this.toString());
	}
	
	@PostRemove
	public void postRemove(){
		new AcaoFeitaBean().gravar(new AcaoFeita(TipoAcao.Deletou, Cadastro.Contato_Condomínio, this.getId(), this.toString()));
		ContextUtil.getDeleteMessage(this.toString());
	}
	
	@PostUpdate
	public void postUpdate(){
		new AcaoFeitaBean().gravar(new AcaoFeita(TipoAcao.Alterou, Cadastro.Contato_Condomínio, this.getId(), this.toString()));
		ContextUtil.getUpdateMessage(this.toString());
	}
	
	public Condominio getCondominio() {
		return condominio;
	}

	public void setCondominio(Condominio condominio) {
		this.condominio = condominio;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public CargoContatoCondominio getCargo() {
		return cargo;
	}

	public void setCargo(CargoContatoCondominio cargo) {
		this.cargo = cargo;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getCelular() {
		return celular;
	}

	public void setCelular(String celular) {
		this.celular = celular;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return this.getNome() + " do condomínio " + this.condominio;
	}
}	
