package br.com.imobiliaria.util;

import java.io.IOException;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;

public class ContextUtil {
	public static Flash getFlash(){
		return FacesContext.getCurrentInstance().getExternalContext().getFlash();
	}
	
	public static void redirect(String arquivo){
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect(arquivo);
		} catch (IOException e) {
			getAnyMessage("Ocorreu um erro");
			e.printStackTrace();
		}
	}
	
	public static void getAnyMessage(String mensagem){
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(mensagem));
	}
	
	public static void getPersistMessage(String name){
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(name + " Inserido com sucesso!"));
	}
	
	public static void getUpdateMessage(String name){
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(name + " Atualizado com sucesso!"));
	}
	
	public static void getDisableMessage(String name){
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(name + " foi Desativado!"));
	}
	
	public static void getDeleteMessage(String name){
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(name + " foi Deletado!"));
	}
	
	public static void getReactivateMessage(String name){
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(name + " foi Reativado!"));
	}
	
	public static void getDuplicateMessage(String item){
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(item + " já existe."));
	}
}
