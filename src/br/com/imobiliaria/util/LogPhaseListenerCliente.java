package br.com.imobiliaria.util;

import javax.faces.application.NavigationHandler;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

import br.com.imobiliaria.modelo.ClienteLogado;

public class LogPhaseListenerCliente implements PhaseListener{

	private static final long serialVersionUID = 1L;
	
	@Override
	public void afterPhase(PhaseEvent event) {
		FacesContext context = event.getFacesContext();
		String pagina = context.getViewRoot().getViewId();
		
		if(pagina.contains("acessoCliente")){
			boolean ehPaginaLogin = pagina.endsWith("login.xhtml");

			NavigationHandler nh = context.getApplication().getNavigationHandler();
			
			if(!ehPaginaLogin && !isLogado()){
				nh.handleNavigation(context, null, "login?faces-redirect=true");
			}
			
			if(isLogado()){
				if(ehPaginaLogin){
					nh.handleNavigation(context, null, "bem-vindo?faces-redirect=true");
				}
			}
		}
	}

	private boolean isLogado() {
		return ClienteLogado.getClienteLogado() != null;
	}

	@Override
	public void beforePhase(PhaseEvent event) {
		System.out.println("FASE: " + event.getPhaseId());
	}

	@Override
	public PhaseId getPhaseId() {
		return PhaseId.ANY_PHASE;
	}

}
