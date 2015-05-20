package br.com.imobiliaria.modelo;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
public class NivelAcesso extends br.com.imobiliaria.modelo.Entity{
	private static final long serialVersionUID = 1L;

	@NotEmpty(message="{campo.vazio}Descrição.") @Column(unique=true)
	private String descricao;
	
	@OneToMany(mappedBy="nivelAcesso", fetch=FetchType.LAZY)
	private List<Funcionario> funcionarios;
	
	@Enumerated(EnumType.ORDINAL)
	private EnumNivelAcesso acessarCargo;

	@Enumerated(EnumType.ORDINAL)
	private EnumNivelAcesso acessarDepartamento;

	@Enumerated(EnumType.ORDINAL)
	private EnumNivelAcesso acessarFuncionario;

	@Enumerated(EnumType.ORDINAL)
	private EnumNivelAcesso acessarNivelAcesso;

	@Enumerated(EnumType.ORDINAL)
	private EnumNivelAcesso acessarAcoes;
	
	@Enumerated(EnumType.ORDINAL)
	private EnumNivelAcesso acessarNumero;
	
	@Enumerated(EnumType.ORDINAL)
	private EnumNivelAcesso acessarAparelho;
	
	@Enumerated(EnumType.ORDINAL)
	private EnumNivelAcesso acessarConstrutora;
	
	@Enumerated(EnumType.ORDINAL)
	private EnumNivelAcesso acessarDistrito;
	
	@Enumerated(EnumType.ORDINAL)
	private EnumNivelAcesso acessarSetorizacao;
	
	@Enumerated(EnumType.ORDINAL)
	private EnumNivelAcesso acessarTransporte;
	
	@Enumerated(EnumType.ORDINAL)
	private EnumNivelAcesso acessarPeriodicidade;
	
	@Enumerated(EnumType.ORDINAL)
	private EnumNivelAcesso acessarTipoVaga;
	
	@Enumerated(EnumType.ORDINAL)
	private EnumNivelAcesso acessarTipoApartamento;
	
	@Enumerated(EnumType.ORDINAL)
	private EnumNivelAcesso acessarEstagioAtendimento;
	
	@Enumerated(EnumType.ORDINAL)
	private EnumNivelAcesso acessarMidia;
	
	@Enumerated(EnumType.ORDINAL)
	private EnumNivelAcesso acessarInvestimentoMidia;
	
	@Enumerated(EnumType.ORDINAL)
	private EnumNivelAcesso acessarEventoMidia;
	
	@Enumerated(EnumType.ORDINAL)
	private EnumNivelAcesso acessarCondominio;
	
	@Enumerated(EnumType.ORDINAL)
	private EnumNivelAcesso acessarTransporteDoCondominio;
	
	@Enumerated(EnumType.ORDINAL)
	private EnumNivelAcesso acessarTorre;
	
	@Enumerated(EnumType.ORDINAL)
	private EnumNivelAcesso acessarContatoCondominio;
	
	@Enumerated(EnumType.ORDINAL)
	private EnumNivelAcesso acessarPlantaTorre;
	
	@Enumerated(EnumType.ORDINAL)
	private EnumNivelAcesso acessarComprador;
	
	@Enumerated(EnumType.ORDINAL)
	private EnumNivelAcesso acessarFollowUpComprador;

	@Enumerated(EnumType.ORDINAL)
	private EnumNivelAcesso acessarCliente;
	
	@Enumerated(EnumType.ORDINAL)
	private EnumNivelAcesso acessarPerfilInteresse;
	
	@Enumerated(EnumType.ORDINAL)
	private EnumNivelAcesso acessarApartamentosFavoritos;
	
	@Enumerated(EnumType.ORDINAL)
	private EnumNivelAcesso acessarTransferirCorretor;
	
	@Enumerated(EnumType.ORDINAL)
	private EnumNivelAcesso acessarHistoricoCorretores;
	
	@Enumerated(EnumType.ORDINAL)
	private EnumNivelAcesso acessarVisita;
	
	@Enumerated(EnumType.ORDINAL)
	private EnumNivelAcesso acessarAtribuirVisita;
	
	@Enumerated(EnumType.ORDINAL)
	private EnumNivelAcesso acessarNegociacao;
	
	@Enumerated(EnumType.ORDINAL)
	private EnumNivelAcesso acessarCorretorNegociacao;
	
	@Enumerated(EnumType.ORDINAL)
	private EnumNivelAcesso acessarFechamento;
	
	@Enumerated(EnumType.ORDINAL)
	private EnumNivelAcesso acessarApartamento;
	
	@Enumerated(EnumType.ORDINAL)
	private EnumNivelAcesso acessarVagaApartamento;
	
	@Enumerated(EnumType.ORDINAL)
	private EnumNivelAcesso acessarProprietario;
	
	@Enumerated(EnumType.ORDINAL)
	private EnumNivelAcesso acessarFollowUpNegociacao;
	
	@Enumerated(EnumType.ORDINAL)
	private EnumNivelAcesso acessarFollowUpFechamento;
	
	@Enumerated(EnumType.ORDINAL)
	private EnumNivelAcesso acessarFollowUpApartamento;
	
	@PostPersist
	public void postPersist(){
//		new AcaoFeitaBean().gravar(new AcaoFeita(TipoAcao.Incluiu, Cadastro.Nível_de_Acesso, this.getId(), this.toString()));
//		ContextUtil.getPersistMessage(this.toString());
	}
	
	@PostRemove
	public void postRemove(){
		new AcaoFeitaBean().gravar(new AcaoFeita(TipoAcao.Deletou, Cadastro.Nível_de_Acesso, this.getId(), this.toString()));
		ContextUtil.getDeleteMessage(this.toString());
	}
	
	@PostUpdate
	public void postUpdate(){
		new AcaoFeitaBean().gravar(new AcaoFeita(TipoAcao.Alterou, Cadastro.Nível_de_Acesso, this.getId(), this.toString()));
		ContextUtil.getUpdateMessage(this.toString());
	}
	
	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public List<Funcionario> getFuncionarios() {
		return new DAO<Funcionario>(Funcionario.class).listaPorId("funcionariosDoNivelAcesso", this.getId());
	}

	public void setFuncionarios(List<Funcionario> funcionarios) {
		this.funcionarios = funcionarios;
	}

	public EnumNivelAcesso getAcessarCargo() {
		return acessarCargo;
	}

	public void setAcessarCargo(EnumNivelAcesso acessarCargo) {
		this.acessarCargo = acessarCargo;
	}
	
	public EnumNivelAcesso getAcessarFuncionario() {
		return acessarFuncionario;
	}

	public void setAcessarFuncionario(EnumNivelAcesso acessarFuncionario) {
		this.acessarFuncionario = acessarFuncionario;
	}

	public EnumNivelAcesso getAcessarNivelAcesso() {
		return acessarNivelAcesso;
	}

	public void setAcessarNivelAcesso(EnumNivelAcesso acessarNivelAcesso) {
		this.acessarNivelAcesso = acessarNivelAcesso;
	}

	public EnumNivelAcesso getAcessarAcoes() {
		return acessarAcoes;
	}

	public void setAcessarAcoes(EnumNivelAcesso acessarAcoes) {
		this.acessarAcoes = acessarAcoes;
	}

	public void setAcessarDepartamento(EnumNivelAcesso acessarDepartamento) {
		this.acessarDepartamento = acessarDepartamento;
	}
	
	public EnumNivelAcesso getAcessarDepartamento() {
		return acessarDepartamento;
	}
	
	public EnumNivelAcesso getAcessarNumero() {
		return acessarNumero;
	}

	public void setAcessarNumero(EnumNivelAcesso acessarNumero) {
		this.acessarNumero = acessarNumero;
	}

	public EnumNivelAcesso getAcessarAparelho() {
		return acessarAparelho;
	}

	public void setAcessarAparelho(EnumNivelAcesso acessarAparelho) {
		this.acessarAparelho = acessarAparelho;
	}

	public EnumNivelAcesso getAcessarConstrutora() {
		return acessarConstrutora;
	}

	public void setAcessarConstrutora(EnumNivelAcesso acessarConstrutora) {
		this.acessarConstrutora = acessarConstrutora;
	}

	public EnumNivelAcesso getAcessarDistrito() {
		return acessarDistrito;
	}

	public void setAcessarDistrito(EnumNivelAcesso acessarDistrito) {
		this.acessarDistrito = acessarDistrito;
	}

	public EnumNivelAcesso getAcessarSetorizacao() {
		return acessarSetorizacao;
	}

	public void setAcessarSetorizacao(EnumNivelAcesso acessarSetorizacao) {
		this.acessarSetorizacao = acessarSetorizacao;
	}

	public EnumNivelAcesso getAcessarTransporte() {
		return acessarTransporte;
	}

	public void setAcessarTransporte(EnumNivelAcesso acessarTransporte) {
		this.acessarTransporte = acessarTransporte;
	}

	public EnumNivelAcesso getAcessarPeriodicidade() {
		return acessarPeriodicidade;
	}

	public void setAcessarPeriodicidade(EnumNivelAcesso acessarPeriodicidade) {
		this.acessarPeriodicidade = acessarPeriodicidade;
	}

	public EnumNivelAcesso getAcessarTipoVaga() {
		return acessarTipoVaga;
	}

	public void setAcessarTipoVaga(EnumNivelAcesso acessarTipoVaga) {
		this.acessarTipoVaga = acessarTipoVaga;
	}

	public EnumNivelAcesso getAcessarTipoApartamento() {
		return acessarTipoApartamento;
	}

	public void setAcessarTipoApartamento(EnumNivelAcesso acessarTipoApartamento) {
		this.acessarTipoApartamento = acessarTipoApartamento;
	}

	public EnumNivelAcesso getAcessarEstagioAtendimento() {
		return acessarEstagioAtendimento;
	}

	public void setAcessarEstagioAtendimento(
			EnumNivelAcesso acessarEstagioAtendimento) {
		this.acessarEstagioAtendimento = acessarEstagioAtendimento;
	}

	public EnumNivelAcesso getAcessarMidia() {
		return acessarMidia;
	}

	public void setAcessarMidia(EnumNivelAcesso acessarMidia) {
		this.acessarMidia = acessarMidia;
	}

	public EnumNivelAcesso getAcessarInvestimentoMidia() {
		return acessarInvestimentoMidia;
	}

	public void setAcessarInvestimentoMidia(EnumNivelAcesso acessarInvestimentoMidia) {
		this.acessarInvestimentoMidia = acessarInvestimentoMidia;
	}

	public EnumNivelAcesso getAcessarEventoMidia() {
		return acessarEventoMidia;
	}

	public void setAcessarEventoMidia(EnumNivelAcesso acessarEventoMidia) {
		this.acessarEventoMidia = acessarEventoMidia;
	}
	
	public EnumNivelAcesso getAcessarCondominio() {
		return acessarCondominio;
	}

	public void setAcessarCondominio(EnumNivelAcesso acessarCondominio) {
		this.acessarCondominio = acessarCondominio;
	}

	public EnumNivelAcesso getAcessarTransporteDoCondominio() {
		return acessarTransporteDoCondominio;
	}

	public void setAcessarTransporteDoCondominio(
			EnumNivelAcesso acessarTransporteDoCondominio) {
		this.acessarTransporteDoCondominio = acessarTransporteDoCondominio;
	}

	public EnumNivelAcesso getAcessarTorre() {
		return acessarTorre;
	}

	public void setAcessarTorre(EnumNivelAcesso acessarTorre) {
		this.acessarTorre = acessarTorre;
	}

	public EnumNivelAcesso getAcessarContatoCondominio() {
		return acessarContatoCondominio;
	}

	public void setAcessarContatoCondominio(EnumNivelAcesso acessarContatoCondominio) {
		this.acessarContatoCondominio = acessarContatoCondominio;
	}

	public EnumNivelAcesso getAcessarPlantaTorre() {
		return acessarPlantaTorre;
	}

	public void setAcessarPlantaTorre(EnumNivelAcesso acessarPlantaTorre) {
		this.acessarPlantaTorre = acessarPlantaTorre;
	}

	public EnumNivelAcesso getAcessarComprador() {
		return acessarComprador;
	}

	public void setAcessarComprador(EnumNivelAcesso acessarComprador) {
		this.acessarComprador = acessarComprador;
	}

	public EnumNivelAcesso getAcessarFollowUpComprador() {
		return acessarFollowUpComprador;
	}

	public void setAcessarFollowUpComprador(EnumNivelAcesso acessarFollowUpComprador) {
		this.acessarFollowUpComprador = acessarFollowUpComprador;
	}

	public EnumNivelAcesso getAcessarCliente() {
		return acessarCliente;
	}

	public void setAcessarCliente(EnumNivelAcesso acessarCliente) {
		this.acessarCliente = acessarCliente;
	}

	public EnumNivelAcesso getAcessarPerfilInteresse() {
		return acessarPerfilInteresse;
	}

	public void setAcessarPerfilInteresse(EnumNivelAcesso acessarPerfilInteresse) {
		this.acessarPerfilInteresse = acessarPerfilInteresse;
	}

	public EnumNivelAcesso getAcessarApartamentosFavoritos() {
		return acessarApartamentosFavoritos;
	}

	public void setAcessarApartamentosFavoritos(
			EnumNivelAcesso acessarApartamentosFavoritos) {
		this.acessarApartamentosFavoritos = acessarApartamentosFavoritos;
	}

	public EnumNivelAcesso getAcessarTransferirCorretor() {
		return acessarTransferirCorretor;
	}

	public void setAcessarTransferirCorretor(
			EnumNivelAcesso acessarTransferirCorretor) {
		this.acessarTransferirCorretor = acessarTransferirCorretor;
	}

	public EnumNivelAcesso getAcessarHistoricoCorretores() {
		return acessarHistoricoCorretores;
	}

	public void setAcessarHistoricoCorretores(
			EnumNivelAcesso acessarHistoricoCorretores) {
		this.acessarHistoricoCorretores = acessarHistoricoCorretores;
	}
	
	public EnumNivelAcesso getAcessarVisita() {
		return acessarVisita;
	}

	public void setAcessarVisita(EnumNivelAcesso acessarVisita) {
		this.acessarVisita = acessarVisita;
	}
	
	public EnumNivelAcesso getAcessarAtribuirVisita() {
		return acessarAtribuirVisita;
	}

	public void setAcessarAtribuirVisita(EnumNivelAcesso acessarAtribuirVisita) {
		this.acessarAtribuirVisita = acessarAtribuirVisita;
	}

	public EnumNivelAcesso getAcessarNegociacao() {
		return acessarNegociacao;
	}

	public void setAcessarNegociacao(EnumNivelAcesso acessarNegociacao) {
		this.acessarNegociacao = acessarNegociacao;
	}

	public EnumNivelAcesso getAcessarFechamento() {
		return acessarFechamento;
	}
	
	public void setAcessarFechamento(EnumNivelAcesso acessarFechamento) {
		this.acessarFechamento = acessarFechamento;
	}

	public EnumNivelAcesso getAcessarApartamento() {
		return acessarApartamento;
	}

	public void setAcessarApartamento(EnumNivelAcesso acessarApartamento) {
		this.acessarApartamento = acessarApartamento;
	}

	public EnumNivelAcesso getAcessarVagaApartamento() {
		return acessarVagaApartamento;
	}

	public void setAcessarVagaApartamento(EnumNivelAcesso acessarVagaApartamento) {
		this.acessarVagaApartamento = acessarVagaApartamento;
	}

	public EnumNivelAcesso getAcessarProprietario() {
		return acessarProprietario;
	}

	public void setAcessarProprietario(EnumNivelAcesso acessarProprietario) {
		this.acessarProprietario = acessarProprietario;
	}

	public EnumNivelAcesso getAcessarCorretorNegociacao() {
		return acessarCorretorNegociacao;
	}

	public void setAcessarCorretorNegociacao(
			EnumNivelAcesso acessarCorretorNegociacao) {
		this.acessarCorretorNegociacao = acessarCorretorNegociacao;
	}

	public EnumNivelAcesso getAcessarFollowUpNegociacao() {
		return acessarFollowUpNegociacao;
	}

	public void setAcessarFollowUpNegociacao(
			EnumNivelAcesso acessarFollowUpNegociacao) {
		this.acessarFollowUpNegociacao = acessarFollowUpNegociacao;
	}

	public EnumNivelAcesso getAcessarFollowUpFechamento() {
		return acessarFollowUpFechamento;
	}

	public void setAcessarFollowUpFechamento(
			EnumNivelAcesso acessarFollowUpFechamento) {
		this.acessarFollowUpFechamento = acessarFollowUpFechamento;
	}
	
	public EnumNivelAcesso getAcessarFollowUpApartamento() {
		return acessarFollowUpApartamento;
	}

	public void setAcessarFollowUpApartamento(
			EnumNivelAcesso acessarFollowUpApartamento) {
		this.acessarFollowUpApartamento = acessarFollowUpApartamento;
	}

	@Override
	public String toString() {
		return this.descricao;
	}


}
