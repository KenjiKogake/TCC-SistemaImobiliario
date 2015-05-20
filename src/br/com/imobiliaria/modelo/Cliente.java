package br.com.imobiliaria.modelo;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.PostPersist;
import javax.persistence.PostRemove;
import javax.persistence.PostUpdate;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Min;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import br.com.caelum.stella.bean.validation.CPF;
import br.com.imobiliaria.bean.AcaoFeitaBean;
import br.com.imobiliaria.util.ContextUtil;

@NamedQueries({
	@NamedQuery(name="loginCliente", query="SELECT c FROM Cliente c WHERE c.email=:pEmail AND c.senha=:pSenha")
})

@Entity
public class Cliente extends br.com.imobiliaria.modelo.Entity{
	
	private static final long serialVersionUID = 1L;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar dataCadastro;
	
	@ManyToOne
	private Funcionario quemCadastrou;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar dataUltimaAlteracao;
	
	@ManyToOne
	private Funcionario quemAlterou;
	
	@OneToOne(mappedBy="cliente")
	private Comprador comprador;
	
	@OneToOne(mappedBy="cliente")
	private Vendedor vendedor;

	@NotEmpty(message="{campo.vazio}Nome.")
	private String nome;
	
	@NotEmpty(message="{campo.vazio}Sobrenome.")
	private String sobrenome;
	
	@Temporal(TemporalType.DATE)
	private Calendar dataNascimento = Calendar.getInstance();
	
	@CPF
	private String cpf;
	
	private String rg;
	
	private String telefone;
	
	private String celular;
	
	@Email(message="E-mail Inválido") 
	@NotEmpty(message="{campo.vazio}E-mail.")
	@Column(unique=true)
	private String email;
	
	private String nomeConjuge;
	
	private String sobrenomeConjuge;
	@Temporal(TemporalType.DATE)
	private Calendar dataNascimentoConjuge = Calendar.getInstance();
	@CPF
	private String cpfConjuge;
	private String rgConjuge;
	private String telefoneConjuge;
	private String celularConjuge;
	@Email
	private String emailConjuge;
	
	private String cep;
	
	@NotEmpty(message="{campo.vazio}Logradouro.")
	private String logradouro;
	
	@Min(value=1, message="{campo.vazio}Número")
	private int numero;
	private String complemento;
	@NotEmpty(message="{campo.vazio}Bairro.")
	private String bairro;
	@NotEmpty(message="{campo.vazio}Cidade.")
	private String cidade;
	@Enumerated(EnumType.ORDINAL)
	private Estado estado;
	
	private String cepTrabalho;
	
	private String logradouroTrabalho;
	
	private int numeroTrabalho;
	
	private String complementoTrabalho;
	
	private String bairroTrabalho;

	private String cidadeTrabalho;
	
	@Enumerated(EnumType.ORDINAL)
	private Estado estadoTrabalho;
	
	@NotEmpty(message="{campo.vazio}Senha.")
	private String senha;
	
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
		new AcaoFeitaBean().gravar(new AcaoFeita(TipoAcao.Incluiu, Cadastro.Cliente, this.getId(), this.toString()));
		ContextUtil.getPersistMessage(this.toString());
		
	}
	
	@PostRemove
	public void postRemove(){
		new AcaoFeitaBean().gravar(new AcaoFeita(TipoAcao.Deletou, Cadastro.Cliente, this.getId(), this.toString()));
		ContextUtil.getDeleteMessage(this.toString());
	}
	
	@PostUpdate
	public void postUpdate(){
		new AcaoFeitaBean().gravar(new AcaoFeita(TipoAcao.Alterou, Cadastro.Cliente, this.getId(), this.toString()));
		ContextUtil.getUpdateMessage(this.toString());
	}
	
	public Calendar getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(Calendar dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public Calendar getDataUltimaAlteracao() {
		return dataUltimaAlteracao;
	}

	public void setDataUltimaAlteracao(Calendar dataUltimaAlteracao) {
		this.dataUltimaAlteracao = dataUltimaAlteracao;
	}
	
	public Funcionario getQuemCadastrou() {
		return quemCadastrou;
	}

	public void setQuemCadastrou(Funcionario quemCadastrou) {
		this.quemCadastrou = quemCadastrou;
	}

	public Funcionario getQuemAlterou() {
		return quemAlterou;
	}

	public void setQuemAlterou(Funcionario quemAlterou) {
		this.quemAlterou = quemAlterou;
	}
	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSobrenome() {
		return sobrenome;
	}

	public void setSobrenome(String sobrenome) {
		this.sobrenome = sobrenome;
	}

	public Calendar getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(Calendar dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getRg() {
		return rg;
	}

	public void setRg(String rg) {
		this.rg = rg;
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

	public String getNomeConjuge() {
		return nomeConjuge;
	}

	public void setNomeConjuge(String nomeConjuge) {
		this.nomeConjuge = nomeConjuge;
	}

	public String getSobrenomeConjuge() {
		return sobrenomeConjuge;
	}

	public void setSobrenomeConjuge(String sobrenomeConjuge) {
		this.sobrenomeConjuge = sobrenomeConjuge;
	}

	public Calendar getDataNascimentoConjuge() {
		return dataNascimentoConjuge;
	}

	public void setDataNascimentoConjuge(Calendar dataNascimentoConjuge) {
		this.dataNascimentoConjuge = dataNascimentoConjuge;
	}

	public String getCpfConjuge() {
		return cpfConjuge;
	}

	public void setCpfConjuge(String cpfConjuge) {
		this.cpfConjuge = cpfConjuge;
	}

	public String getRgConjuge() {
		return rgConjuge;
	}

	public void setRgConjuge(String rgConjuge) {
		this.rgConjuge = rgConjuge;
	}

	public String getTelefoneConjuge() {
		return telefoneConjuge;
	}

	public void setTelefoneConjuge(String telefoneConjuge) {
		this.telefoneConjuge = telefoneConjuge;
	}

	public String getCelularConjuge() {
		return celularConjuge;
	}

	public void setCelularConjuge(String celularConjuge) {
		this.celularConjuge = celularConjuge;
	}

	public String getEmailConjuge() {
		return emailConjuge;
	}

	public void setEmailConjuge(String emailConjuge) {
		this.emailConjuge = emailConjuge;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public Estado getEstado() {
		return estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}
	
	public String getCepTrabalho() {
		return cepTrabalho;
	}

	public void setCepTrabalho(String cepTrabalho) {
		this.cepTrabalho = cepTrabalho;
	}

	public String getLogradouroTrabalho() {
		return logradouroTrabalho;
	}

	public void setLogradouroTrabalho(String logradouroTrabalho) {
		this.logradouroTrabalho = logradouroTrabalho;
	}

	public int getNumeroTrabalho() {
		return numeroTrabalho;
	}

	public void setNumeroTrabalho(int numeroTrabalho) {
		this.numeroTrabalho = numeroTrabalho;
	}

	public String getComplementoTrabalho() {
		return complementoTrabalho;
	}

	public void setComplementoTrabalho(String complementoTrabalho) {
		this.complementoTrabalho = complementoTrabalho;
	}

	public String getBairroTrabalho() {
		return bairroTrabalho;
	}

	public void setBairroTrabalho(String bairroTrabalho) {
		this.bairroTrabalho = bairroTrabalho;
	}

	public String getCidadeTrabalho() {
		return cidadeTrabalho;
	}

	public void setCidadeTrabalho(String cidadeTrabalho) {
		this.cidadeTrabalho = cidadeTrabalho;
	}

	public Estado getEstadoTrabalho() {
		return estadoTrabalho;
	}

	public void setEstadoTrabalho(Estado estadoTrabalho) {
		this.estadoTrabalho = estadoTrabalho;
	}

	public Comprador getComprador() {
		return comprador;
	}

	public void setComprador(Comprador comprador) {
		this.comprador = comprador;
	}

	public Vendedor getVendedor() {
		return vendedor;
	}

	public void setVendedor(Vendedor vendedor) {
		this.vendedor = vendedor;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	@Override
	public String toString() {
		return this.nome + " " + this.sobrenome;
	}
}
