package br.com.imobiliaria.bean;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.persistence.PersistenceException;

import br.com.imobiliaria.dao.DAO;
import br.com.imobiliaria.modelo.AreaUtil;
import br.com.imobiliaria.modelo.Torre;
import br.com.imobiliaria.util.ContextUtil;

@ManagedBean
@ViewScoped
public class AreaUtilBean {
	private AreaUtil areaUtil = new AreaUtil();
	private DAO<AreaUtil> dao = new DAO<AreaUtil>(AreaUtil.class);
	private List<AreaUtil> areasUteis;

	public AreaUtil getAreaUtil() {
		return areaUtil;
	}

	public void setAreaUtil(AreaUtil areaUtil) {
		this.areaUtil = areaUtil;
	}
	
	public List<AreaUtil> getAreasUteis() {
		return areasUteis;
	}

	public void setAreasUteis(List<AreaUtil> areasUteis) {
		this.areasUteis = areasUteis;
	}

	public void gravar(Torre torre){
		try {
			if(areaUtil.getId() == null){
				areaUtil.setTorre(torre);
				dao.adiciona(areaUtil);
			}else
				dao.atualiza(areaUtil);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void excluir(){
		try {
			dao.remove(areaUtil);
		} catch (PersistenceException e) {
			ContextUtil.getAnyMessage("Não pode ser excluído, existem apartamentos vinculados.");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void limparAreaUtil(){
		this.areaUtil = new AreaUtil();
	}
	
	public boolean isNovo(){
		return this.areaUtil.getId() == null;
	}
}
