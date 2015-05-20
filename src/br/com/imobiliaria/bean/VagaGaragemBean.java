package br.com.imobiliaria.bean;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.persistence.PersistenceException;

import br.com.imobiliaria.dao.DAO;
import br.com.imobiliaria.modelo.Apartamento;
import br.com.imobiliaria.modelo.VagaGaragem;
import br.com.imobiliaria.util.ContextUtil;


@ManagedBean
@ViewScoped
public class VagaGaragemBean implements Serializable{
	private static final long serialVersionUID = 1L;
	private DAO<VagaGaragem> dao = new DAO<VagaGaragem>(VagaGaragem.class);
	private VagaGaragem vagaGaragem = new VagaGaragem();
	
	public VagaGaragem getVagaGaragem() {
		return this.vagaGaragem;
	}
	
	public void setVagaGaragem(VagaGaragem vagaGaragem) {
		this.vagaGaragem = vagaGaragem;
	}

	public void limparVagaGaragem(){
		this.vagaGaragem = new VagaGaragem();
	}
	
	public boolean isNovo(){
		return this.vagaGaragem.getId() == null;
	}
	
	public void gravar(Apartamento apartamento){
		this.vagaGaragem.setApartamento(apartamento);
		try {
			if(vagaGaragem.getId() == null)
				dao.adiciona(vagaGaragem);
			else
				dao.atualiza(vagaGaragem);
		} catch(PersistenceException e){
			if(e.getCause().toString().contains("Duplicate")){
				ContextUtil.getDuplicateMessage("Vaga de Garagem ");
			}
		} catch (Exception e) {
			e.printStackTrace();
			ContextUtil.getAnyMessage("Ocorreu um erro!");
		}
	}
	
	public void deletar(){
		try {
			dao.remove(vagaGaragem);
		} catch (Exception e) {
			e.printStackTrace();
			ContextUtil.getAnyMessage("Ocorreu um erro!");
		}
	}
}
