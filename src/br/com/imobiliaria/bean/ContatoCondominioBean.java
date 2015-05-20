package br.com.imobiliaria.bean;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.imobiliaria.dao.DAO;
import br.com.imobiliaria.modelo.Condominio;
import br.com.imobiliaria.modelo.ContatoCondominio;

@ManagedBean
@ViewScoped
public class ContatoCondominioBean {
	private ContatoCondominio contato = new ContatoCondominio();
	private DAO<ContatoCondominio> dao = new DAO<ContatoCondominio>(ContatoCondominio.class);

	public ContatoCondominio getContato() {
		return contato;
	}
	
	public void setContato(ContatoCondominio contato) {
		this.contato = contato;
	}
	
	public List<ContatoCondominio> getContatos(){
		return dao.listaTodos();
	}
	
	public void gravar(Condominio condominio){
		if(contato.getCondominio() == null) contato.setCondominio(condominio);
		try {
			if(contato.getId() == null)
				dao.adiciona(contato);
			else
				dao.atualiza(contato);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void excluir(){
		try {
			dao.remove(contato);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void limparContato(){
		this.contato = new ContatoCondominio();
//		RequestContext.getCurrentInstance().openDialog("dialog/dialog");
	}
	
	public boolean isNovo(){
		return this.contato.getId() == null;
	}
	
}
