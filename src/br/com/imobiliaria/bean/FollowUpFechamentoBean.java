package br.com.imobiliaria.bean;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.imobiliaria.dao.DAO;
import br.com.imobiliaria.modelo.Fechamento;
import br.com.imobiliaria.modelo.FollowUpFechamento;

@ManagedBean
@ViewScoped
public class FollowUpFechamentoBean {
	private DAO<FollowUpFechamento> dao = new DAO<FollowUpFechamento>(FollowUpFechamento.class);
	private FollowUpFechamento followUp = new FollowUpFechamento();
	
	public FollowUpFechamento getFollowUp() {
		return followUp;
	}

	public void setFollowUp(FollowUpFechamento followUp) {
		this.followUp = followUp;
	}

	public void gravar(Fechamento fechamento){
		try {
			followUp.setFechamento(fechamento);
			dao.adiciona(followUp);
			followUp = new FollowUpFechamento();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
