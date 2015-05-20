package br.com.imobiliaria.bean;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.imobiliaria.dao.DAO;
import br.com.imobiliaria.modelo.Comprador;
import br.com.imobiliaria.modelo.FollowUpComprador;

@ManagedBean
@ViewScoped
public class FollowUpCompradorBean {
	private DAO<FollowUpComprador> dao = new DAO<FollowUpComprador>(FollowUpComprador.class);
	private FollowUpComprador followUp = new FollowUpComprador();
	private Long id;
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public FollowUpComprador getFollowUp() {
		return followUp;
	}

	public void setFollowUp(FollowUpComprador followUp) {
		this.followUp = followUp;
	}

	public void gravar(Comprador comprador){
		try {
			followUp.setComprador(comprador);
			dao.adiciona(followUp);
			followUp = new FollowUpComprador();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
