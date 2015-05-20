package br.com.imobiliaria.modelo;

import java.util.Calendar;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.PostPersist;
import javax.persistence.PostRemove;
import javax.persistence.PostUpdate;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;
import org.hibernate.validator.constraints.NotEmpty;

import br.com.imobiliaria.bean.AcaoFeitaBean;
import br.com.imobiliaria.util.ContextUtil;

@NamedQueries({
	@NamedQuery(name="eventoDaMidia", query="SELECT e FROM EventoMidia e WHERE e.midia.id = :pId")
})

@Entity
public class EventoMidia extends EntityPadrao{
	private static final long serialVersionUID = 1L;

	@Temporal(TemporalType.DATE)
	private Calendar dataEvento = Calendar.getInstance();
	
	@NotEmpty(message="{campo.vazio}Evento")
	private String evento;
	
	@NotEmpty(message="{campo.vazio}Motivo")
	private String motivo;
	
	@NotEmpty(message="{campo.vazio}Expectativa")
	private String expectativa;
	
	@ManyToOne(optional=false)
	private Midia midia;
	
	@PostPersist
	public void postPersist(){
		new AcaoFeitaBean().gravar(new AcaoFeita(TipoAcao.Incluiu, Cadastro.Eventos_Mídia, this.getId(), this.toString()));
		ContextUtil.getPersistMessage(this.toString());
	}
	
	@PostRemove
	public void postRemove(){
		new AcaoFeitaBean().gravar(new AcaoFeita(TipoAcao.Deletou, Cadastro.Eventos_Mídia, this.getId(), this.toString()));
		ContextUtil.getDeleteMessage(this.toString());
	}
	
	@PostUpdate
	public void postUpdate(){
		new AcaoFeitaBean().gravar(new AcaoFeita(TipoAcao.Alterou, Cadastro.Eventos_Mídia, this.getId(), this.toString()));
		ContextUtil.getUpdateMessage(this.toString());
	}

	public Midia getMidia() {
		return midia;
	}

	public void setMidia(Midia midia) {
		this.midia = midia;
	}

	public Calendar getDataEvento() {
		return dataEvento;
	}

	public void setDataEvento(Calendar dataEvento) {
		this.dataEvento = dataEvento;
	}

	public String getEvento() {
		return evento;
	}

	public void setEvento(String evento) {
		this.evento = evento;
	}

	public String getMotivo() {
		return motivo;
	}

	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}

	public String getExpectativa() {
		return expectativa;
	}

	public void setExpectativa(String expectativa) {
		this.expectativa = expectativa;
	}
	
	@Override
	public String toString() {
		return this.evento + " " + this.midia;
	}
}