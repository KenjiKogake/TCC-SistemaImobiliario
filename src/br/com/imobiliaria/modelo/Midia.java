package br.com.imobiliaria.modelo;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.PostPersist;
import javax.persistence.PostUpdate;

import org.hibernate.validator.constraints.NotEmpty;

import br.com.imobiliaria.bean.AcaoFeitaBean;
import br.com.imobiliaria.dao.DAO;
import br.com.imobiliaria.util.ContextUtil;

@Entity
public class Midia extends EntityPadrao{
	private static final long serialVersionUID = 1L;

	@NotEmpty(message="{campo.vazio}Mídia") @Column(unique=true)
	private String midia;
	
	@Enumerated(EnumType.STRING)
	private Status status = Status.Ativo;
	
	@OneToMany(fetch=FetchType.LAZY, mappedBy="midia")
	private List<Comprador> compradores;
	
	@OneToMany(fetch=FetchType.LAZY, mappedBy="midia")
	private List<EventoMidia> eventosMidia;
	
	@OneToMany(fetch=FetchType.LAZY, mappedBy="midia")
	private List<InvestimentoMidia> investimentosMidia;
	
	@PostPersist
	public void postPersist(){
		new AcaoFeitaBean().gravar(new AcaoFeita(TipoAcao.Incluiu, Cadastro.Mídia, this.getId(), this.toString()));
		ContextUtil.getPersistMessage(this.toString());
	}
	
	@PostUpdate
	public void postUpdate(){
		new AcaoFeitaBean().gravar(new AcaoFeita(TipoAcao.Alterou, Cadastro.Mídia, this.getId(), this.toString()));
		ContextUtil.getUpdateMessage(this.toString());
	}

	public String getMidia() {
		return midia;
	}

	public void setMidia(String midia) {
		this.midia = midia;
	}
	
	public Status getStatus() {
		return status;
	}
	
	public void setStatus(Status status) {
		this.status = status;
	}

	public List<EventoMidia> getEventosMidia() {
		return new DAO<EventoMidia>(EventoMidia.class).listaPorId("eventoDaMidia", this.getId());
	}
		
	public void setEventosMidia(List<EventoMidia> eventosMidia) {
		this.eventosMidia = eventosMidia;
	}

	public List<InvestimentoMidia> getInvestimentosMidia() {
		return new DAO<InvestimentoMidia>(InvestimentoMidia.class).listaPorId("investimentosDaMidia", this.getId());
	}

	public void setInvestimentosMidia(List<InvestimentoMidia> investimentosMidia) {
		this.investimentosMidia = investimentosMidia;
	}
	
	public List<Comprador> getCompradores() {
		return new DAO<Comprador>(Comprador.class).listaPorId("compradoresDaMidia", this.getId());
	}

	public void setCompradores(List<Comprador> compradores) {
		this.compradores = compradores;
	}

	@Override
	public String toString() {
		return this.getMidia();
	}
}
