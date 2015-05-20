package br.com.imobiliaria.util;

import javax.faces.application.NavigationHandler;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

import br.com.imobiliaria.modelo.FuncionarioLogado;

public class LogPhaseListener implements PhaseListener{

	private static final long serialVersionUID = 1L;
	
	@Override
	public void afterPhase(PhaseEvent event) {
		FacesContext context = event.getFacesContext();
		String pagina = context.getViewRoot().getViewId();
		
		if(!pagina.contains("acessoCliente")){
			boolean ehPaginaLogin = pagina.endsWith("login.xhtml");
			boolean ehPaginaAlterarSenha = pagina.endsWith("alterarSenha.xhtml");
			
			NavigationHandler nh = context.getApplication().getNavigationHandler();
			
			if(!ehPaginaLogin && !isLogado()){
				nh.handleNavigation(context, null, "login?faces-redirect=true");
			}
			
			if(isLogado()){
				if(FuncionarioLogado.getFuncionarioLogado().isAlterarSenhaProximoAcesso() && !ehPaginaAlterarSenha){
					nh.handleNavigation(context, null, "alterarSenha?faces-redirect=true");
				}else if(ehPaginaAlterarSenha && !FuncionarioLogado.getFuncionarioLogado().isAlterarSenhaProximoAcesso() || ehPaginaLogin){
					nh.handleNavigation(context, null, "dashboard?faces-redirect=true");
				}
			}
		}
	}

	private boolean isLogado() {
		return FuncionarioLogado.getFuncionarioLogado() != null;
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
