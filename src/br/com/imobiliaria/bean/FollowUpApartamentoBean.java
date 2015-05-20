package br.com.imobiliaria.bean;

import java.util.Calendar;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.imobiliaria.dao.DAO;
import br.com.imobiliaria.modelo.Apartamento;
import br.com.imobiliaria.modelo.FollowUpApartamento;
import br.com.imobiliaria.modelo.FuncionarioLogado;
import br.com.imobiliaria.modelo.StatusFollowUp;
import br.com.imobiliaria.modelo.TipoFollowUp;
import br.com.imobiliaria.util.ContextUtil;

@ManagedBean
@ViewScoped
public class FollowUpApartamentoBean {
	private DAO<FollowUpApartamento> dao = new DAO<FollowUpApartamento>(FollowUpApartamento.class);
	private FollowUpApartamento followUp = new FollowUpApartamento();
	
	private Long id;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public FollowUpApartamento getFollowUp() {
		return followUp;
	}

	public void setFollowUp(FollowUpApartamento followUp) {
		this.followUp = followUp;
	}
	
	public void onActionView() {
		if (this.followUp.getId() != this.getId() && this.getId() != null){
			this.followUp = dao.buscaPorId(this.getId());
			if(followUp.getTipoFollowUp() != TipoFollowUp.Alteracao || followUp.getStatus() != StatusFollowUp.Pendente) followUp.setId(null);
		}
	}

	public void gravar(Apartamento apartamento){
		try {
			followUp.setApartamento(apartamento);
			if(followUp.getTipoFollowUp() == TipoFollowUp.Alteracao) followUp.setStatus(StatusFollowUp.Pendente);
			dao.adiciona(followUp);
			
			followUp = new FollowUpApartamento();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public List<FollowUpApartamento> getFollowUpsPendentes() {
		return dao.listaNamedQuery("followUpsPendentes");
	}
	
	public void atualizar(){
		try {
			this.followUp.setRevisor(FuncionarioLogado.getFuncionarioLogado());
			this.followUp.setDataRevisao(Calendar.getInstance().getTime());
			dao.atualiza(followUp);
		} catch (Exception e) {
			e.printStackTrace();
			ContextUtil.getAnyMessage("Ocorreu um erro!");
		}
	}

	public void redirect(){
		ContextUtil.redirect("solicitacaoAlteracao.xhtml");
	}
	
	public void aprovar(){
		this.followUp.setStatus(StatusFollowUp.Aprovado);
		atualizar();
		redirect();
	}
	
	public void recusar(){
		this.followUp.setStatus(StatusFollowUp.Recusado);
		atualizar();
		redirect();
	}
	
	public void abrirFicha(){
		ContextUtil.redirect("apartamento.xhtml?id=" + this.followUp.getApartamento().getId() + "&solicitacao=" + this.followUp.getId());
	}
	
	public boolean isNovo(){
		return this.followUp.getId() == null;
	}
}
