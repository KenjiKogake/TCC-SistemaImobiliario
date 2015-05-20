package br.com.imobiliaria.modelo;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
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
import javax.persistence.PreUpdate;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;

import br.com.imobiliaria.bean.AcaoFeitaBean;
import br.com.imobiliaria.dao.DAO;
import br.com.imobiliaria.dao.DAOCliente;
import br.com.imobiliaria.util.ContextUtil;

@NamedQueries({
	@NamedQuery(name="compradoresDoEstagio", query="SELECT c FROM Comprador c WHERE c.estagioAtendimento.id = :pId"),
	@NamedQuery(name="compradoresDaMidia", query="SELECT c FROM Comprador c WHERE c.midia.id = :pId"),
	@NamedQuery(name="compradoresDecrescente", query="SELECT c FROM Comprador c ORDER BY c.id DESC")
})

@Entity
public class Comprador extends EntityPadrao{
	
	private static final long serialVersionUID = 1L;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar dataUltimaAlteracao;
	
	@ManyToOne
	private Funcionario quemAlterou;
	
	@OneToOne
	private Cliente cliente;
	
	private Integer grauInteresse;
	
	private String personalidade;
	
	private String poderDeDecisao;
	
	private String recursosParaPagamento;
	
	private String motivoDaCompra;
	
	private String comentario;
	
	@ManyToOne
	private EstagioAtendimento estagioAtendimento;
	
	@ManyToOne(optional=false) @NotNull(message="{campo.vazio} Mídia")
	private Midia midia;
	
	@OneToMany(mappedBy = "comprador", fetch=FetchType.LAZY)
	private List<PerfilInteresse> perfisInteresse;
	
	@OneToMany(fetch=FetchType.LAZY, mappedBy="id.comprador")
	private List<ApartamentosFavoritos> apartamentosFavoritos = new ArrayList<ApartamentosFavoritos>();
	
	@OneToMany(mappedBy="comprador", fetch=FetchType.LAZY)
	private List<FollowUpComprador> followups;
	
	@OneToMany(fetch=FetchType.LAZY, mappedBy="comprador")
	private List<CompradorDoCorretor> corretoresDoComprador = new ArrayList<CompradorDoCorretor>();
	
	@OneToMany(fetch=FetchType.LAZY, mappedBy="comprador")
	private List<Visita> visitas = new ArrayList<Visita>();
	
	@OneToMany(fetch=FetchType.LAZY, mappedBy="comprador")
	private List<Negociacao> negociacoes = new ArrayList<Negociacao>();
	
	@Transient
	private CompradorDoCorretor corretorResponsavel = new CompradorDoCorretor();
	
	@Transient
	private FollowUpComprador ultimoFollowUp = new FollowUpComprador();
	
	@PreUpdate
	public void preUpdate(){
		this.setDataUltimaAlteracao(Calendar.getInstance());
		this.setQuemAlterou(FuncionarioLogado.getFuncionarioLogado());
	}
	
	@PostPersist
	public void postPersist(){
		new AcaoFeitaBean().gravar(new AcaoFeita(TipoAcao.Incluiu, Cadastro.Comprador, this.getId(), this.toString()));
		ContextUtil.getPersistMessage(this.toString());
	}
	
	@PostRemove
	public void postRemove(){
		new AcaoFeitaBean().gravar(new AcaoFeita(TipoAcao.Deletou, Cadastro.Comprador, this.getId(), this.toString()));
		ContextUtil.getDeleteMessage(this.toString());
	}
	
	@PostUpdate
	public void postUpdate(){
		new AcaoFeitaBean().gravar(new AcaoFeita(TipoAcao.Alterou, Cadastro.Comprador, this.getId(), this.toString()));
		ContextUtil.getUpdateMessage(this.toString());
	}
	
	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Integer getGrauInteresse() {
		return grauInteresse;
	}

	public void setGrauInteresse(Integer grauInteresse) {
		this.grauInteresse = grauInteresse;
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

	public List<PerfilInteresse> getPerfisInteresse() {
		return new DAO<PerfilInteresse>(PerfilInteresse.class).listaPorId("perfilDeInteresseDoComprador", this.getId());
	}

	public void setPerfisInteresse(List<PerfilInteresse> perfilInteresse) {
		this.perfisInteresse = perfilInteresse;
	}

	public List<ApartamentosFavoritos> getApartamentosFavoritos() {
		return new DAO<ApartamentosFavoritos>(ApartamentosFavoritos.class).listaPorId("apartamentosFavoritosAtivos", this.getId());
	}

	public void setApartamentosFavoritos(List<ApartamentosFavoritos> apartamentosFavoritos) {
		this.apartamentosFavoritos = apartamentosFavoritos;
	}
	
	public List<ApartamentosFavoritos> getApartamentosFavoritosInativos() {
		return new DAO<ApartamentosFavoritos>(ApartamentosFavoritos.class).listaPorId("apartamentosFavoritosInativos", this.getId());
	}
	
	public List<Visita> getVisitas() {
		return new DAO<Visita>(Visita.class).listaPorId("visitasDoComprador", this.getId());
	}
	
	public List<Negociacao> getNegociacoes() {
		return new DAO<Negociacao>(Negociacao.class).listaPorId("negociacoesDoComprador", this.getId());
	}

	public void setNegociacoes(List<Negociacao> negociacoes) {
		this.negociacoes = negociacoes;
	}

	public void setVisitas(List<Visita> visitas) {
		this.visitas = visitas;
	}

	public EstagioAtendimento getEstagioAtendimento() {
		return estagioAtendimento;
	}

	public void setEstagioAtendimento(EstagioAtendimento estagioAtendimento) {
		this.estagioAtendimento = estagioAtendimento;
	}
	
	public Midia getMidia() {
		return midia;
	}

	public void setMidia(Midia midia) {
		this.midia = midia;
	}

	public String getPersonalidade() {
		return personalidade;
	}

	public void setPersonalidade(String personalidade) {
		this.personalidade = personalidade;
	}

	public String getPoderDeDecisao() {
		return poderDeDecisao;
	}

	public void setPoderDeDecisao(String poderDeDecisao) {
		this.poderDeDecisao = poderDeDecisao;
	}

	public String getRecursosParaPagamento() {
		return recursosParaPagamento;
	}

	public void setRecursosParaPagamento(String recursosParaPagamento) {
		this.recursosParaPagamento = recursosParaPagamento;
	}

	public String getMotivoDaCompra() {
		return motivoDaCompra;
	}

	public void setMotivoDaCompra(String motivoDaCompra) {
		this.motivoDaCompra = motivoDaCompra;
	}

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	public List<FollowUpComprador> getFollowups() {
		return new DAO<FollowUpComprador>(FollowUpComprador.class).listaPorId("followupsdoComprador", this.getId());
	}
	
	public void setFollowup(List<FollowUpComprador> followups) {
		this.followups = followups;
	}
	
	public List<CompradorDoCorretor> getCorretoresDoComprador() {
		return new DAO<CompradorDoCorretor>(CompradorDoCorretor.class).listaPorId("corretoresDoComprador", this.getId());
	}

	public void setCorretoresDoComprador(
			List<CompradorDoCorretor> corretoresDoComprador) {
		this.corretoresDoComprador = corretoresDoComprador;
	}
	
	public CompradorDoCorretor getCorretorResponsavel() {
		return new DAO<CompradorDoCorretor>(CompradorDoCorretor.class).listaPorId("corretorDoComprador", this.getId()).get(0);
	}
	
	public List<Fechamento> getFechamentos(){
		return new DAO<Fechamento>(Fechamento.class).listaPorId("fechamentosDoComprador", this.getId());
	}
	
	public FollowUpComprador getUltimoFollowUp() {
		try {
			return new DAOCliente().buscaFollowUpPorComprador(this.getId());
		} catch (Exception e) {
			e.printStackTrace();
			return new FollowUpComprador();
		}
		
		
		//		teste.minus(agoraPouco.)
	}
	
	@Override
	public String toString() {
		return this.cliente.getNome();
	}
	
	public static void main(String[] args) {
//		Instant instant = teste.atZone(ZoneId.systemDefault()).toInstant();
		Calendar calendar = Calendar.getInstance();
//		calendar.setTimeInMillis(instant.toEpochMilli());

		Instant instant = Instant.ofEpochMilli(calendar.getTimeInMillis());
		LocalDateTime teste = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
		
		
//		LocalDateTime teste = LocalDateTime.from();
		LocalDateTime agoraPouco = LocalDateTime.of(2014, 11, 25, 20, 0);
		System.out.println(Duration.between(agoraPouco, teste).toHours());
		System.out.println(Duration.between(agoraPouco, teste).toMinutes());
		System.out.println(Duration.between(agoraPouco, teste).toMinutes() % 60);
		System.out.println(Duration.between(agoraPouco, teste).toString());
	}
}
