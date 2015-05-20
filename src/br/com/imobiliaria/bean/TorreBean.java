package br.com.imobiliaria.bean;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.imobiliaria.dao.DAO;
import br.com.imobiliaria.modelo.Condominio;
import br.com.imobiliaria.modelo.Torre;

@ManagedBean
@ViewScoped
public class TorreBean {
	private Torre torre = new Torre();
	private DAO<Torre> dao = new DAO<Torre>(Torre.class);

	public Torre getTorre() {
		return torre;
	}
	
	public void setTorre(Torre torre) {
		this.torre = torre;
	}
	
	public List<Torre> getTorres(){
		return dao.listaTodos();
	}
	
	public void gravar(Condominio condominio){
		if(torre.getCondominio() == null) torre.setCondominio(condominio);
		try {
			if(torre.getId() == null)
				dao.adiciona(torre);
			else
				dao.atualiza(torre);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void limparTorre(){
		this.torre = new Torre();
	}
	
	public boolean isNovo(){
		return this.torre.getId() == null;
	}
}
