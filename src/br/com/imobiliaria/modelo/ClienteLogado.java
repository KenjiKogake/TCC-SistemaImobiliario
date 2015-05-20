package br.com.imobiliaria.modelo;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

public class ClienteLogado {
	
	public static void setClienteLogado(Cliente clienteLogado){
	 	HttpSession session = getSession();
		session.setAttribute("clienteLogado", clienteLogado);
	}
	
	public static Cliente getClienteLogado(){
		try {
			HttpSession session = getSession();
			Cliente c = (Cliente) session.getAttribute("clienteLogado");
			System.out.println("Get Cliente Logado");
			System.out.println(c);
			return c;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static void logoff(){
		if(FuncionarioLogado.getFuncionarioLogado() != null) 
			setClienteLogado(null);
		else
			getSession().invalidate();
	}

	private static HttpSession getSession() {
		return (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
	}
}
