package br.com.imobiliaria.modelo;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

public class FuncionarioLogado {
	
	public static void setFuncionarioLogado(Funcionario funcionarioLogado){
		HttpSession session = getSession();
		session.setAttribute("funcionarioLogado", funcionarioLogado);
	}
	
	public static Funcionario getFuncionarioLogado(){
		try {
			HttpSession session = getSession();
			Funcionario f = (Funcionario) session.getAttribute("funcionarioLogado");

			return f;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static void logoff(){
		if(ClienteLogado.getClienteLogado() != null)
			setFuncionarioLogado(null);
		else
			getSession().invalidate();
	}

	private static HttpSession getSession() {
		return (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
	}
}
