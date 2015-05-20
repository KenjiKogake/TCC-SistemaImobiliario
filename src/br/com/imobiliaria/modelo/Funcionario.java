package br.com.imobiliaria.modelo;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PostPersist;
import javax.persistence.PostUpdate;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import br.com.caelum.stella.bean.validation.CPF;
import br.com.imobiliaria.dao.DAO;
import br.com.imobiliaria.util.ContextUtil;

@NamedQueries({
	@NamedQuery(name="login", query="SELECT f FROM Funcionario f WHERE f.usuario=:pUsuario AND f.senha=:pSenha AND f.status='Ativo'"),
	
	@NamedQuery(name="funcionariosDoDepartamento", query="SELECT f FROM Funcionario f WHERE f.departamento.id = :pId"),
	@NamedQuery(name="funcionariosDoCargo", query="SELECT f FROM Funcionario f WHERE f.cargo.id = :pId"),
	@NamedQuery(name="funcionariosDoNivelAcesso", query="SELECT f FROM Funcionario f WHERE f.nivelAcesso.id = :pId"),
	@NamedQuery(name="funcionariosAtivos", query="SELECT f FROM Funcionario f WHERE f.status = 'Ativo'"),
	@NamedQuery(name="funcionariosAtivosQueAtendemComprador", query="SELECT f FROM Funcionario f WHERE f.status = 'Ativo' AND f.podeAtenderComprador = 1")
})

@Entity
@Table(uniqueConstraints={@UniqueConstraint(columnNames={"nome", "sobrenome"})})
public class Funcionario extends br.com.imobiliaria.modelo.Entity{
	
	private static final long serialVersionUID = 1L;
	
	@Column(nullable=true)
	@Temporal(TemporalType.DATE)
	private Date dataEntrada = Calendar.getInstance().getTime();
	
	@Column(nullable=true)
	@Temporal(TemporalType.DATE)
	private Date dataSaida = null;
	
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
	
	@NotEmpty(message="{campo.vazio}Celular.") @Column(unique=true)
	private String celular;
	
	@Email
	private String email;
	
	@OneToOne
	private AparelhoEmpresarial aparelhoEmpresarial;
	
	@OneToOne
	private NumeroEmpresarial numeroEmpresarial;
	
	private int ramal;
	private String banco;
	private String agencia;
	private String conta;
	
	@Enumerated(EnumType.STRING)
	private Status status = Status.Ativo;
	
	@ManyToOne
	private Departamento departamento;
	
	@ManyToOne
	private Cargo cargo;

	@ManyToOne(optional=false) @NotNull(message="{campo.vazio} Nível de Acesso")
	private NivelAcesso nivelAcesso;
	
	@NotEmpty(message="{campo.vazio}Usuário.") @Column(unique=true)
	private String usuario;
	
	@NotEmpty(message="{campo.vazio}Senha.")
	private String senha;
	
	private boolean alterarSenhaProximoAcesso;
	
	private boolean podeAtenderComprador;
	
	@OneToMany(mappedBy="quemCadastrou", fetch=FetchType.LAZY)
	private List<AcaoFeita> acoes;
	
	@OneToMany(fetch=FetchType.LAZY, mappedBy="corretor")
	private List<CompradorDoCorretor> compradoresDoCorretor = new ArrayList<CompradorDoCorretor>();
	
	@PostPersist
	public void postPersist(){
//		new AcaoFeitaBean().gravar(new AcaoFeita(TipoAcao.Incluiu, Cadastro.Funcionário, this.getId(), this.toString()));
		ContextUtil.getPersistMessage(this.toString());
	}
	
	@PostUpdate
	public void postUpdate(){
//		new AcaoFeitaBean().gravar(new AcaoFeita(TipoAcao.Alterou, Cadastro.Funcionário, this.getId(), this.toString()));
		ContextUtil.getUpdateMessage(this.toString());
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
	
	public AparelhoEmpresarial getAparelhoEmpresarial() {
		return aparelhoEmpresarial;
	}
	
	public void setAparelhoEmpresarial(AparelhoEmpresarial aparelhoEmpresarial) {
		this.aparelhoEmpresarial = aparelhoEmpresarial;
	}
	
	public NumeroEmpresarial getNumeroEmpresarial() {
		return numeroEmpresarial;
	}
	
	public void setNumeroEmpresarial(NumeroEmpresarial numeroEmpresarial) {
		this.numeroEmpresarial = numeroEmpresarial;
	}
	
	public int getRamal() {
		return ramal;
	}
	
	public void setRamal(int ramal) {
		this.ramal = ramal;
	}
	
	public String getBanco() {
		return banco;
	}
	
	public void setBanco(String banco) {
		this.banco = banco;
	}
	
	public String getAgencia() {
		return agencia;
	}
	
	public void setAgencia(String agencia) {
		this.agencia = agencia;
	}
	
	public String getConta() {
		return conta;
	}
	
	public void setConta(String conta) {
		this.conta = conta;
	}
	
	public Departamento getDepartamento() {
		return departamento;
	}
	
	public void setDepartamento(Departamento departamento) {
		this.departamento = departamento;
	}
	
	public Cargo getCargo() {
		return cargo;
	}
	
	public void setCargo(Cargo cargo) {
		this.cargo = cargo;
	}
	
	public String getUsuario() {
		return usuario;
	}
	
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	
	public String getSenha() {
		return senha;
	}
	
	public void setSenha(String senha) {
		this.senha = senha;
	}

	public boolean isAlterarSenhaProximoAcesso() {
		return alterarSenhaProximoAcesso;
	}
	
	public void setAlterarSenhaProximoAcesso(boolean alterarSenhaProximoAcesso) {
		this.alterarSenhaProximoAcesso = alterarSenhaProximoAcesso;
	}
	
	public Status getStatus() {
		return status;
	}
	
	public Date getDataEntrada() {
		return dataEntrada;
	}
	
	public void setDataEntrada(Date dataEntrada) {
		this.dataEntrada = dataEntrada;
	}
	
	public Date getDataSaida() {
		return dataSaida;
	}
	
	public void setDataSaida(Date dataSaida) {
		this.dataSaida = dataSaida;
	}
	
	public void setStatus(Status status) {
		this.status = status;
	}
	
	public List<AcaoFeita> getAcoes() {
		return new DAO<AcaoFeita>(AcaoFeita.class).listaPorId("acoesDoFuncionario", this.getId());
	}
	
	public void setAcoes(List<AcaoFeita> acoes) {
		this.acoes = acoes;
	}
	
	public NivelAcesso getNivelAcesso() {
		return nivelAcesso;
	}
	public void setNivelAcesso(NivelAcesso nivelAcesso) {
		this.nivelAcesso = nivelAcesso;
	}
	
	public List<CompradorDoCorretor> getCompradoresDoCorretor() {
		return new DAO<CompradorDoCorretor>(CompradorDoCorretor.class).listaPorId("compradoresDoCorretor", this.getId());
	}
	public void setCompradoresDoCorretor(
			List<CompradorDoCorretor> compradoresDoCorretor) {
		this.compradoresDoCorretor = compradoresDoCorretor;
	}
	
	public boolean isPodeAtenderComprador() {
		return podeAtenderComprador;
	}

	public void setPodeAtenderComprador(boolean podeAtenderComprador) {
		this.podeAtenderComprador = podeAtenderComprador;
	}

	@Override
	public String toString() {
		return this.nome + " " + this.sobrenome;
	}
	
	
}
