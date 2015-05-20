package br.com.imobiliaria.bean;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.event.SelectEvent;

import br.com.imobiliaria.dao.DAO;
import br.com.imobiliaria.modelo.EventoMidia;
import br.com.imobiliaria.modelo.Midia;
import br.com.imobiliaria.util.ContextUtil;

@ManagedBean
@ViewScoped
public class EventoMidiaBean {
	private DAO<EventoMidia> dao = new DAO<EventoMidia>(EventoMidia.class);
	private EventoMidia eventoMidia = new EventoMidia();
	private List<EventoMidia> eventosFiltrados;
	
	public List<EventoMidia> getEventosFiltrados() {
		return eventosFiltrados;
	}

	public void setEventosFiltrados(List<EventoMidia> eventosFiltrados) {
		this.eventosFiltrados = eventosFiltrados;
	}

	public EventoMidia getEventoMidia() {
		return eventoMidia;
	}

	public void setEventoMidia(EventoMidia eventoMidia) {
		this.eventoMidia = eventoMidia;
	}

	public void gravar(Midia midia){
		if(eventoMidia.getMidia() == null) eventoMidia.setMidia(midia);
		try {
			if(eventoMidia.getId() == null)
				dao.adiciona(eventoMidia);
			else
				dao.atualiza(eventoMidia);
			
		} catch (Exception e) {
			ContextUtil.getAnyMessage("Ocorreu um erro!");
		}
	}

	public void deletar(){
		try {
			dao.remove(eventoMidia);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
		
	public void limparEvento(){
		this.eventoMidia = new EventoMidia();
	}
	
	public boolean isNovo(){
		return this.eventoMidia.getId() == null;
	}
	
	public void onRowSelect(SelectEvent event) {
        this.eventoMidia = (EventoMidia) event.getObject();
    }
}
