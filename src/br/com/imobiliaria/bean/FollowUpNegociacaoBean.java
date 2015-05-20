package br.com.imobiliaria.bean;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.imobiliaria.dao.DAO;
import br.com.imobiliaria.modelo.FollowUpNegociacao;
import br.com.imobiliaria.modelo.Negociacao;

@ManagedBean
@ViewScoped
public class FollowUpNegociacaoBean {
	private DAO<FollowUpNegociacao> dao = new DAO<FollowUpNegociacao>(FollowUpNegociacao.class);
	private FollowUpNegociacao followUp = new FollowUpNegociacao();
	
	public FollowUpNegociacao getFollowUp() {
		return followUp;
	}

	public void setFollowUp(FollowUpNegociacao followUp) {
		this.followUp = followUp;
	}

	public void gravar(Negociacao negociacao){
		try {
			followUp.setNegociacao(negociacao);
			dao.adiciona(followUp);
			followUp = new FollowUpNegociacao();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
