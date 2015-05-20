package br.com.imobiliaria.bean;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;

import br.com.imobiliaria.modelo.Cadastro;
import br.com.imobiliaria.modelo.CargoContatoCondominio;
import br.com.imobiliaria.modelo.EnumNivelAcesso;
import br.com.imobiliaria.modelo.Estado;
import br.com.imobiliaria.modelo.FaseCondominio;
import br.com.imobiliaria.modelo.FormaContato;
import br.com.imobiliaria.modelo.LocalChaves;
import br.com.imobiliaria.modelo.SituacaoApartamento;
import br.com.imobiliaria.modelo.Status;
import br.com.imobiliaria.modelo.StatusApartamento;
import br.com.imobiliaria.modelo.StatusFechamento;
import br.com.imobiliaria.modelo.StatusFollowUp;
import br.com.imobiliaria.modelo.StatusNegociacao;
import br.com.imobiliaria.modelo.StatusVisita;
import br.com.imobiliaria.modelo.TipoAcao;
import br.com.imobiliaria.modelo.TipoContato;
import br.com.imobiliaria.modelo.TipoFollowUp;
import br.com.imobiliaria.modelo.TipoTransporte;

@ManagedBean
public class EnumBean {
	
	public Estado[] getEstados(){
		return Estado.values();
	}
	
	public TipoFollowUp[] getTiposFollowUp(){
		return TipoFollowUp.values();
	}

	public CargoContatoCondominio[] getCargoContatosCondominio(){
		return CargoContatoCondominio.values();
	}
	
	public TipoTransporte[] getTiposTransporte(){
		return TipoTransporte.values();
	}
	
	public FaseCondominio[] getFases() {
		return FaseCondominio.values();
	}
	
	public List<Integer> getMonths(){
		List<Integer> meses = new ArrayList<Integer>();
		for (int i = 1; i <= 12; i++) {
			meses.add(i);
		}
		return meses;
	}
	
	public Status[] getStatus(){
		return Status.values();
	}
	
	public StatusVisita[] getStatusVisita(){
		return StatusVisita.values();
	}
	
	public TipoContato[] getTiposContato(){
		return TipoContato.values();
	}
	
	public FormaContato[] getFormasContato(){
		return FormaContato.values();
	}
	
	public EnumNivelAcesso[] getNiveisAcesso(){
		return EnumNivelAcesso.values();
	}

	public SituacaoApartamento[] getSituacoesApartamento(){
		return SituacaoApartamento.values();
	}
	
	public LocalChaves[] getLocaisChaves(){
		return LocalChaves.values();
	}
	
	public StatusNegociacao[] getStatusNegociacao(){
		return StatusNegociacao.values();
	}
	
	public StatusFechamento[] getStatusFechamento(){
		return StatusFechamento.values();
	}
	
	public StatusFollowUp[] getStatusFollowUp(){
		return StatusFollowUp.values();
	}
	
	public Cadastro[] getCadastros(){
		return Cadastro.values();
	}
	
	public TipoAcao[] getTiposAcao(){
		return TipoAcao.values();
	}
	
	public StatusApartamento[] getStatusApartamento(){
		return StatusApartamento.values();
	}
}

