package br.com.imobiliaria.modelo;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PostPersist;
import javax.persistence.PostRemove;
import javax.persistence.PostUpdate;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;
import org.hibernate.validator.constraints.NotEmpty;

import br.com.imobiliaria.bean.AcaoFeitaBean;
import br.com.imobiliaria.dao.DAO;
import br.com.imobiliaria.util.ContextUtil;

@NamedQueries({
	@NamedQuery(name="condominiosDaConstrutora", query="SELECT c FROM Condominio c WHERE c.construtora.id = :pId"),
	@NamedQuery(name="condominiosDoDistrito", query="SELECT c FROM Condominio c WHERE c.distrito.id = :pId"),
	@NamedQuery(name="condominiosDoSetor", query="SELECT c FROM Condominio c WHERE c.setorizacao.id = :pId"),
	@NamedQuery(name="condominiosDecrescente", query="SELECT c FROM Condominio c ORDER BY c.id DESC")
})

@Entity
public class Condominio extends br.com.imobiliaria.modelo.Entity{

	private static final long serialVersionUID = 1L;

	@Temporal(TemporalType.TIMESTAMP)
	private Calendar dataCadastro;
	
	@ManyToOne
	private Funcionario quemCadastrou;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataUltimaAlteracao;
	
	@ManyToOne
	private Funcionario quemAlterou;
	
	@Enumerated(EnumType.STRING)
	private Status status;
	
	@Size(min=3, message="{condominio.nome}")
	private String nome;
	
	@Enumerated(EnumType.STRING)
	private FaseCondominio fase;
	
	@Temporal(TemporalType.DATE)
	private Calendar dataEntrega = Calendar.getInstance();
	
	@ManyToOne
	private Construtora construtora;
	
	private String cep;
	
	@NotEmpty(message="{campo.vazio}Logradouro.")
	private String logradouro;
	
	@Min(value=1, message="{campo.vazio}Número")
	private int numero;
	private String complemento;
	
	@NotEmpty(message="{campo.vazio}Bairro.")
	private String bairro;
	@ManyToOne
	private Distrito distrito;
	@NotEmpty(message="{campo.vazio}Cidade.")
	private String cidade;
	@Enumerated(EnumType.STRING)
	private Estado estado;
	@ManyToOne
	private Setorizacao setorizacao;
	
	private Double latitude;
	private Double longitude;
	
	@OneToMany(fetch=FetchType.LAZY, mappedBy="id.condominio")
	private List<TransportesDoCondominio> transportesDoCondominio = new ArrayList<TransportesDoCondominio>();
	
	private String textoLocalizacao;
	private String textoIntroducao;
	private String textoAreaDeLazer;

	@OneToMany(mappedBy = "condominio", fetch=FetchType.LAZY)
	private List<ContatoCondominio> contatos;
	@OneToMany(mappedBy="condominio", fetch=FetchType.LAZY)
	private List<Torre> torres;

	@PrePersist
	public void prePersist(){
		this.dataCadastro = Calendar.getInstance();
		this.quemCadastrou = FuncionarioLogado.getFuncionarioLogado();
	}
	
	@PreUpdate
	public void preUpdate(){
		this.dataUltimaAlteracao = Calendar.getInstance().getTime();
		this.quemAlterou = FuncionarioLogado.getFuncionarioLogado();
	}
	
	@PostPersist
	public void postPersist(){
		new AcaoFeitaBean().gravar(new AcaoFeita(TipoAcao.Incluiu, Cadastro.Condomínio, this.getId(), this.toString()));
		ContextUtil.getPersistMessage(this.getNome());
	}
	
	@PostRemove
	public void postRemove(){
		new AcaoFeitaBean().gravar(new AcaoFeita(TipoAcao.Deletou, Cadastro.Condomínio, this.getId(), this.toString()));
	}
	
	@PostUpdate
	public void postUpdate(){
		new AcaoFeitaBean().gravar(new AcaoFeita(TipoAcao.Alterou, Cadastro.Condomínio, this.getId(), this.toString()));
		ContextUtil.getUpdateMessage(this.getNome());
	}
	
	public Calendar getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(Calendar dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public Date getDataUltimaAlteracao() {
		return dataUltimaAlteracao;
	}

	public void setDataUltimaAlteracao(Date dataUltimaAlteracao) {
		this.dataUltimaAlteracao = dataUltimaAlteracao;
	}

	public Status getStatus() {
		return status;
	}
	
	public void setStatus(Status status) {
		this.status = status;
	}
	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public FaseCondominio getFase() {
		return fase;
	}

	public void setFase(FaseCondominio fase) {
		this.fase = fase;
	}

	public Calendar getDataEntrega() {
		return dataEntrega;
	}

	public void setDataEntrega(Calendar dataEntrega) {
		this.dataEntrega = dataEntrega;
	}

	public Construtora getConstrutora() {
		return construtora;
	}

	public void setConstrutora(Construtora construtora) {
		this.construtora = construtora;
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

	public Distrito getDistrito() {
		return distrito;
	}

	public void setDistrito(Distrito distrito) {
		this.distrito = distrito;
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

	public Setorizacao getSetorizacao() {
		return setorizacao;
	}

	public void setSetorizacao(Setorizacao setorizacao) {
		this.setorizacao = setorizacao;
	}

	public String getTextoLocalizacao() {
		return textoLocalizacao;
	}

	public void setTextoLocalizacao(String textoLocalizacao) {
		this.textoLocalizacao = textoLocalizacao;
	}

	public String getTextoIntroducao() {
		return textoIntroducao;
	}

	public void setTextoIntroducao(String textoIntroducao) {
		this.textoIntroducao = textoIntroducao;
	}

	public String getTextoAreaDeLazer() {
		return textoAreaDeLazer;
	}

	public void setTextoAreaDeLazer(String textoAreaDeLazer) {
		this.textoAreaDeLazer = textoAreaDeLazer;
	}

	public List<ContatoCondominio> getContatos() {
		return new DAO<ContatoCondominio>(ContatoCondominio.class).listaPorId("contatosDoCondominio", this.getId());
	}

	public void setContatos(List<ContatoCondominio> contatos) {
		this.contatos = contatos;
	}

	public List<Torre> getTorres() {
		return new DAO<Torre>(Torre.class).listaPorId("torresDoCondominio", this.getId());
	}

	public void setTorres(List<Torre> torres) {
		this.torres = torres;
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
	
	public List<TransportesDoCondominio> getTransportesDoCondominio() {
		return new DAO<TransportesDoCondominio>(TransportesDoCondominio.class).listaPorId("transportesDoCondominio", this.getId());
	}
	
	public void setTransportesDoCondominio(
			List<TransportesDoCondominio> transportesDoCondominio) {
		this.transportesDoCondominio = transportesDoCondominio;
	}

	public Double getLatitude() {
		return latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}
	
	public List<Foto> getFotos() {
		return new DAO<Foto>(Foto.class).listaPorId("fotosDoCondominio", this.getId());
	}
	
	public String getEndereco(){
		return this.logradouro + ", " + this.numero + " - " + this.bairro + ", " + this.cidade + " - " + this.estado;
	}

	public boolean isComFotos(){
		return !getFotos().isEmpty();
	}

	@Override
	public String toString() {
		return this.getNome();
	}

}
