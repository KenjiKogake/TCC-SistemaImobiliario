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
import javax.persistence.PostUpdate;
import javax.persistence.PreUpdate;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;

import br.com.imobiliaria.bean.AcaoFeitaBean;
import br.com.imobiliaria.dao.DAO;
import br.com.imobiliaria.util.ContextUtil;

@NamedQueries({
	@NamedQuery(name="apartamentosPorTipo", query="SELECT a FROM Apartamento a WHERE a.tipoApartamento.id = :pId"),
	@NamedQuery(name="apartamentosDaTorre", query="SELECT a FROM Apartamento a WHERE a.torre.id = :pId"),
	@NamedQuery(name="apartamentosDoVendedor", query="SELECT a FROM Apartamento a WHERE a.vendedor.id = :pId"),
	@NamedQuery(name="apartamentosDaPlanta", query="SELECT a FROM Apartamento a WHERE a.planta.id = :pId"),
	@NamedQuery(name="apartamentosDaAreaUtil", query="SELECT a FROM Apartamento a WHERE a.areaUtil.id = :pId"),
	@NamedQuery(name="apartamentosDecrescente", query="SELECT a FROM Apartamento a ORDER BY a.id DESC"),
	@NamedQuery(name="apartamentosAtivos", query="SELECT a FROM Apartamento a WHERE a.status = 'À_Venda' OR a.status = 'Negociando' ORDER BY a.id DESC")
})

@Entity
public class Apartamento extends EntityPadrao{
	private static final long serialVersionUID = 1L;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar dataUltimaAlteracao;
	
	@ManyToOne
	private Funcionario quemAlterou;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataAtivacao;
	
	@ManyToOne(optional=false) @NotNull(message="{campo.vazio}Tipo de Apartamento")
	private TipoApartamento tipoApartamento;
	
	@ManyToOne(optional=false) @NotNull(message="{campo.vazio}Condomínio")
	private Torre torre;
	
	@ManyToOne(optional=false) @NotNull(message="{campo.vazio}Área Útil")
	private AreaUtil areaUtil;
	
	@ManyToOne(optional=false) @NotNull(message="{campo.vazio}Planta")
	private PlantaTorre planta;
	
	@Enumerated(EnumType.STRING) @NotNull(message="{campo.vazio}Situação")
	private SituacaoApartamento situacao;
	
	@Enumerated(EnumType.STRING)
	private LocalChaves localChaves;
	
	private String comentarioChaves;

	@Min(value=1, message="{campo.vazio}Nº Apto")
	private int numeroApartamento;
	
	@Min(value=1, message="{campo.vazio}Andar")
	private int andar;
	
	@Min(value=1, message="{campo.vazio}Valor de Venda")
	private double valorVenda;
	
	@Min(value=1, message="{campo.vazio}Valor do Condomínio")
	private double valorCondominio;
	
	private double valorIptu;
	
	@Enumerated(EnumType.STRING)
	private StatusApartamento status = StatusApartamento.À_Venda;
	
	@ManyToOne(optional=false) @NotNull(message="{campo.vazio}Vendedor")
	private Vendedor vendedor;
	
	@OneToMany(mappedBy="apartamento", fetch=FetchType.LAZY)
	private List<VagaGaragem> vagas;

	@OneToMany(fetch=FetchType.LAZY, mappedBy="id.apartamento")
	private List<ApartamentosFavoritos> clientesInteressados = new ArrayList<ApartamentosFavoritos>();

	@OneToMany(fetch=FetchType.LAZY, mappedBy="apartamento")
	private List<FollowUpApartamento> followUps = new ArrayList<FollowUpApartamento>();
	
	@OneToMany(fetch=FetchType.LAZY, mappedBy="apartamento")
	private List<Visita> visitas = new ArrayList<Visita>();
	
	@OneToMany(fetch=FetchType.LAZY, mappedBy="apartamento")
	private List<Negociacao> negociacoes = new ArrayList<Negociacao>();
	
	@PreUpdate
	public void preUpdate(){
		this.setDataUltimaAlteracao(Calendar.getInstance());
		this.setQuemAlterou(FuncionarioLogado.getFuncionarioLogado());
	}
	
	@PostPersist
	public void postPersist(){
		new AcaoFeitaBean().gravar(new AcaoFeita(TipoAcao.Incluiu, Cadastro.Apartamento, this.getId(), this.getTorre().getCondominio() + " - " + this.getTorre() + " - " + this.toString()));
		ContextUtil.getPersistMessage(this.toString());
	}
	
	@PostUpdate
	public void postUpdate(){
		new AcaoFeitaBean().gravar(new AcaoFeita(TipoAcao.Alterou, Cadastro.Apartamento, this.getId(), this.getTorre().getCondominio() + " - " + this.getTorre() + " - " + this.toString()));
		ContextUtil.getUpdateMessage(this.toString());
	}
	
	public List<VagaGaragem> getVagas() {
		return new DAO<VagaGaragem>(VagaGaragem.class).listaPorId("vagasDoApartamento", this.getId());
	}
	
	public void setVagas(List<VagaGaragem> vagas) {
		this.vagas = vagas;
	}

	public Calendar getDataUltimaAlteracao() {
		return dataUltimaAlteracao;
	}
	
	public void setDataUltimaAlteracao(Calendar dataUltimaAlteracao) {
		this.dataUltimaAlteracao = dataUltimaAlteracao;
	}
	
	public TipoApartamento getTipoApartamento() {
		return tipoApartamento;
	}

	public void setTipoApartamento(TipoApartamento tipoApartamento) {
		this.tipoApartamento = tipoApartamento;
	}

	public Torre getTorre() {
		return torre;
	}

	public void setTorre(Torre torre) {
		this.torre = torre;
	}

	public SituacaoApartamento getSituacao() {
		return situacao;
	}

	public void setSituacao(SituacaoApartamento situacao) {
		this.situacao = situacao;
	}

	public LocalChaves getLocalChaves() {
		return localChaves;
	}

	public void setLocalChaves(LocalChaves localChaves) {
		this.localChaves = localChaves;
	}

	public String getComentarioChaves() {
		return comentarioChaves;
	}

	public void setComentarioChaves(String comentarioChaves) {
		this.comentarioChaves = comentarioChaves;
	}

	public int getNumeroApartamento() {
		return numeroApartamento;
	}

	public void setNumeroApartamento(int numeroApartamento) {
		this.numeroApartamento = numeroApartamento;
	}

	public int getAndar() {
		return andar;
	}

	public void setAndar(int andar) {
		this.andar = andar;
	}

	public double getValorVenda() {
		return valorVenda;
	}

	public void setValorVenda(double valorVenda) {
		this.valorVenda = valorVenda;
	}

	public double getValorCondominio() {
		return valorCondominio;
	}

	public void setValorCondominio(double valorCondominio) {
		this.valorCondominio = valorCondominio;
	}

	public double getValorIptu() {
		return valorIptu;
	}

	public void setValorIptu(double valorIptu) {
		this.valorIptu = valorIptu;
	}

	public Funcionario getQuemAlterou() {
		return quemAlterou;
	}

	public void setQuemAlterou(Funcionario quemAlterou) {
		this.quemAlterou = quemAlterou;
	}
	
	public Date getDataAtivacao() {
		return dataAtivacao;
	}

	public void setDataAtivacao(Date dataAtivacao) {
		this.dataAtivacao = dataAtivacao;
	}

	public List<ApartamentosFavoritos> getClientesInteressados() {
		return new DAO<ApartamentosFavoritos>(ApartamentosFavoritos.class).listaPorId("compradoresInteressados", this.getId());
	}

	public void setClientesInteressados(
			List<ApartamentosFavoritos> clientesInteressados) {
		this.clientesInteressados = clientesInteressados;
	}

	public PlantaTorre getPlanta() {
		return planta;
	}

	public void setPlanta(PlantaTorre planta) {
		this.planta = planta;
	}

	public Vendedor getVendedor() {
		return vendedor;
	}

	public void setVendedor(Vendedor vendedor) {
		this.vendedor = vendedor;
	}

	public AreaUtil getAreaUtil() {
		return areaUtil;
	}

	public void setAreaUtil(AreaUtil areaUtil) {
		this.areaUtil = areaUtil;
	}
	
	public List<FollowUpApartamento> getFollowUps() {
		return new DAO<FollowUpApartamento>(FollowUpApartamento.class).listaPorId("followUpsDoApartamento", this.getId());
	}

	public void setFollowUps(List<FollowUpApartamento> followUps) {
		this.followUps = followUps;
	}

	public List<Visita> getVisitas() {
		return new DAO<Visita>(Visita.class).listaPorId("visitasDoApartamento", this.getId());
	}

	public List<Negociacao> getNegociacoes() {
		return new DAO<Negociacao>(Negociacao.class).listaPorId("negociacoesDoApartamento", this.getId());
	}
	
	public List<Negociacao> getNegociacoesAtivas(){
		return new DAO<Negociacao>(Negociacao.class).listaPorId("negociacoesAtivasDoApartamento", this.getId());
	}

	public List<Fechamento> getFechamentos() {
		return new DAO<Fechamento>(Fechamento.class).listaPorId("fechamentosDoApartamento", this.getId());
	}
	
	public int getNumeroVagas(){
		return this.vagas.size();
	}
	
	public List<Foto> getFotos() {
		return new DAO<Foto>(Foto.class).listaPorId("fotosDoApartamento", this.getId());
	}
	
	public boolean isComFotos(){
		return !getFotos().isEmpty();
	}

	public StatusApartamento getStatus() {
		return status;
	}

	public void setStatus(StatusApartamento status) {
		this.status = status;
	}
	
	public boolean isVendendo(){
		return status == StatusApartamento.À_Venda || status == StatusApartamento.Negociando;
	}

	@Override
	public String toString() {
		return "Apto " + this.getNumeroApartamento();
	}
	
}
