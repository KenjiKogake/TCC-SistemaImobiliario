package br.com.imobiliaria.bean;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;

import br.com.imobiliaria.modelo.FuncionarioLogado;

@ManagedBean
public class AcessoBean implements Serializable{
	private static final long serialVersionUID = 1L;

	public int getAcessoCargo(){
		return FuncionarioLogado.getFuncionarioLogado().getNivelAcesso().getAcessarCargo().ordinal();
	}
	
	public int getAcessoDepartamento(){
		return FuncionarioLogado.getFuncionarioLogado().getNivelAcesso().getAcessarDepartamento().ordinal();
	}
	
	public int getAcessoFuncionario(){
		return FuncionarioLogado.getFuncionarioLogado().getNivelAcesso().getAcessarFuncionario().ordinal();
	}
	
	public int getAcessoNivelAcesso(){
		return FuncionarioLogado.getFuncionarioLogado().getNivelAcesso().getAcessarNivelAcesso().ordinal();
	}
	
	public int getAcessoAcoes(){
		return FuncionarioLogado.getFuncionarioLogado().getNivelAcesso().getAcessarAcoes().ordinal();
	}
	
	public int getAcessoNumero(){
		return FuncionarioLogado.getFuncionarioLogado().getNivelAcesso().getAcessarNumero().ordinal();
	}
	
	public int getAcessoAparelho(){
		return FuncionarioLogado.getFuncionarioLogado().getNivelAcesso().getAcessarAparelho().ordinal();
	}
	
	public int getAcessoConstrutora(){
		return FuncionarioLogado.getFuncionarioLogado().getNivelAcesso().getAcessarConstrutora().ordinal();
	}

	public int getAcessoDistrito(){
		return FuncionarioLogado.getFuncionarioLogado().getNivelAcesso().getAcessarDistrito().ordinal();
	}

	public int getAcessoSetorizacao(){
		return FuncionarioLogado.getFuncionarioLogado().getNivelAcesso().getAcessarSetorizacao().ordinal();
	}

	public int getAcessoTransporte(){
		return FuncionarioLogado.getFuncionarioLogado().getNivelAcesso().getAcessarTransporte().ordinal();
	}
	
	public int getAcessoPeriodicidade(){
		return FuncionarioLogado.getFuncionarioLogado().getNivelAcesso().getAcessarPeriodicidade().ordinal();
	}
		
	public int getAcessoTipoVaga(){
		return FuncionarioLogado.getFuncionarioLogado().getNivelAcesso().getAcessarTipoVaga().ordinal();
	}
	
	public int getAcessoTipoApartamento(){
		return FuncionarioLogado.getFuncionarioLogado().getNivelAcesso().getAcessarTipoApartamento().ordinal();
	}
	
	public int getAcessoEstagioAtendimento(){
		return FuncionarioLogado.getFuncionarioLogado().getNivelAcesso().getAcessarEstagioAtendimento().ordinal();
	}
	
	public int getAcessoMidia(){
		return FuncionarioLogado.getFuncionarioLogado().getNivelAcesso().getAcessarMidia().ordinal();
	}
		
	public int getAcessoInvestimentoMidia(){
		return FuncionarioLogado.getFuncionarioLogado().getNivelAcesso().getAcessarInvestimentoMidia().ordinal();
	}
	
	public int getAcessoEventoMidia(){
		return FuncionarioLogado.getFuncionarioLogado().getNivelAcesso().getAcessarEventoMidia().ordinal();
	}
	
	public int getAcessoCondominio(){
		return FuncionarioLogado.getFuncionarioLogado().getNivelAcesso().getAcessarCondominio().ordinal();
	}
	
	public int getAcessoTransporteCondominio(){
		return FuncionarioLogado.getFuncionarioLogado().getNivelAcesso().getAcessarTransporteDoCondominio().ordinal();
	}
	
	public int getAcessoTorre(){
		return FuncionarioLogado.getFuncionarioLogado().getNivelAcesso().getAcessarTorre().ordinal();
	}
	
	public int getAcessoContatoCondominio(){
		return FuncionarioLogado.getFuncionarioLogado().getNivelAcesso().getAcessarContatoCondominio().ordinal();
	}
	
	public int getAcessoPlantaTorre(){
		return FuncionarioLogado.getFuncionarioLogado().getNivelAcesso().getAcessarPlantaTorre().ordinal();
	}
	
	public int getAcessoComprador(){
		return FuncionarioLogado.getFuncionarioLogado().getNivelAcesso().getAcessarComprador().ordinal();
	}
	
	public int getAcessoFollowUpComprador(){
		return FuncionarioLogado.getFuncionarioLogado().getNivelAcesso().getAcessarFollowUpComprador().ordinal();
	}
	
	public int getAcessoCliente(){
		return FuncionarioLogado.getFuncionarioLogado().getNivelAcesso().getAcessarCliente().ordinal();
	}
	
	public int getAcessoPerfilInteresse(){
		return FuncionarioLogado.getFuncionarioLogado().getNivelAcesso().getAcessarPerfilInteresse().ordinal();
	}
	
	public int getAcessoApartamentosFavoritos(){
		return FuncionarioLogado.getFuncionarioLogado().getNivelAcesso().getAcessarApartamentosFavoritos().ordinal();
	}
	
	public int getAcessoTransferirCorretor(){
		return FuncionarioLogado.getFuncionarioLogado().getNivelAcesso().getAcessarTransferirCorretor().ordinal();
	}
	
	public int getAcessoHistoricoCorretores(){
		return FuncionarioLogado.getFuncionarioLogado().getNivelAcesso().getAcessarHistoricoCorretores().ordinal();
	}
	
	public int getAcessoVisita(){
		return FuncionarioLogado.getFuncionarioLogado().getNivelAcesso().getAcessarVisita().ordinal();
	}
	
	public int getAcessoAtribuirVisita(){
		return FuncionarioLogado.getFuncionarioLogado().getNivelAcesso().getAcessarAtribuirVisita().ordinal();
	}
	
	public int getAcessoNegociacao(){
		return FuncionarioLogado.getFuncionarioLogado().getNivelAcesso().getAcessarNegociacao().ordinal();
	}
	
	public int getAcessoCorretorNegociacao(){
		return FuncionarioLogado.getFuncionarioLogado().getNivelAcesso().getAcessarCorretorNegociacao().ordinal();
	}
	
	public int getAcessoFechamento(){
		return FuncionarioLogado.getFuncionarioLogado().getNivelAcesso().getAcessarFechamento().ordinal();
	}
	
	public int getAcessoApartamento(){
		return FuncionarioLogado.getFuncionarioLogado().getNivelAcesso().getAcessarApartamento().ordinal();
	}
	
	public int getAcessoVagaApartamento(){
		return FuncionarioLogado.getFuncionarioLogado().getNivelAcesso().getAcessarVagaApartamento().ordinal();
	}
	
	public int getAcessoProprietario(){
		return FuncionarioLogado.getFuncionarioLogado().getNivelAcesso().getAcessarProprietario().ordinal();
	}
	
	public int getAcessoFollowUpNegociacao(){
		return FuncionarioLogado.getFuncionarioLogado().getNivelAcesso().getAcessarFollowUpNegociacao().ordinal();
	}
	
	public int getAcessoFollowUpFechamento(){
		return FuncionarioLogado.getFuncionarioLogado().getNivelAcesso().getAcessarFollowUpFechamento().ordinal();
	}
	
	public int getAcessoFollowUpApartamento(){
		return FuncionarioLogado.getFuncionarioLogado().getNivelAcesso().getAcessarFollowUpApartamento().ordinal();
	}
	
	public static void main(String[] args) {
		new AcessoBean().getAcessoCargo();
	}
	
}
