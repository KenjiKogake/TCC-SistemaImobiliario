package br.com.imobiliaria.bean;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.persistence.PersistenceException;

import br.com.imobiliaria.dao.DAO;
import br.com.imobiliaria.modelo.PlantaTorre;
import br.com.imobiliaria.modelo.Torre;
import br.com.imobiliaria.util.ContextUtil;

@ManagedBean
@ViewScoped
public class PlantaTorreBean {
	private PlantaTorre planta = new PlantaTorre();
	private DAO<PlantaTorre> dao = new DAO<PlantaTorre>(PlantaTorre.class);

	public PlantaTorre getPlanta() {
		return planta;
	}
	
	public void setPlanta(PlantaTorre planta) {
		this.planta = planta;
	}
	
	public void gravar(Torre torre){
		if(planta.getTorre() == null) planta.setTorre(torre);
		try {
			if(planta.getId() == null)
				dao.adiciona(planta);
			else
				dao.atualiza(planta);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void excluir(){
		try {
			dao.remove(planta);
		} catch (PersistenceException e) {
			ContextUtil.getAnyMessage("Não pode ser excluído, existem apartamentos vinculados.");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void limparPlanta(){
		this.planta = new PlantaTorre();
	}
	
	public boolean isNovo(){
		return this.planta.getId() == null;
	}
}
