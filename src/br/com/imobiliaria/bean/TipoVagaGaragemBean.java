package br.com.imobiliaria.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.persistence.PersistenceException;

import br.com.imobiliaria.dao.DAO;
import br.com.imobiliaria.modelo.TipoVagaGaragem;
import br.com.imobiliaria.util.ContextUtil;

@ManagedBean
@ViewScoped
public class TipoVagaGaragemBean implements Serializable{
	private static final long serialVersionUID = 1L;

	private TipoVagaGaragem tipoVaga = new TipoVagaGaragem();
	private final DAO<TipoVagaGaragem> dao = new DAO<TipoVagaGaragem>(TipoVagaGaragem.class);
	private List<TipoVagaGaragem> tiposVaga;
	private List<TipoVagaGaragem> tiposVagaFiltradas;
	
	@PostConstruct
	public void postConstruct(){
		atualizaLista();
	}
	
	private void atualizaLista() {
		this.tiposVaga = dao.listaTodos();
	}

	public TipoVagaGaragem getTipoVaga() {
		return tipoVaga;
	}

	public void setTipoVaga(TipoVagaGaragem tipoVaga) {
		this.tipoVaga = tipoVaga;
	}

	public List<TipoVagaGaragem> getTiposVaga() {
		return tiposVaga;
	}

	public void setTiposVaga(List<TipoVagaGaragem> tiposVaga) {
		this.tiposVaga = tiposVaga;
	}

	public List<TipoVagaGaragem> getTiposVagaFiltradas() {
		return tiposVagaFiltradas;
	}

	public void setTiposVagaFiltradas(List<TipoVagaGaragem> tiposVagaFiltradas) {
		this.tiposVagaFiltradas = tiposVagaFiltradas;
	}

	public void gravar(){
		try {
			if(tipoVaga.getId() == null)
				dao.adiciona(tipoVaga);
			else
				dao.atualiza(tipoVaga);
			
		}catch (PersistenceException e) {
			if(e.getCause().toString().contains("Duplicate")) ContextUtil.getDuplicateMessage("Tipo de Vaga inserida");
		}
		catch(Exception e){
			ContextUtil.getAnyMessage("Ocorreu um erro!");
		}finally{
			atualizaLista();
		}
	}

	public void deletar(){
		try {
			dao.remove(tipoVaga);
		} catch (PersistenceException e) {
			ContextUtil.getAnyMessage("Não pode ser excluído, existem vagas de garagem vinculadas.");
		} catch(Exception e){
			ContextUtil.getAnyMessage("Ocorreu um erro!");
		}finally{
			atualizaLista();
		}
	}
	
	public void limparTipoVaga(){
		this.tipoVaga = new TipoVagaGaragem();
	}
	
	public boolean isNovo(){
		return this.tipoVaga.getId() == null;
	}
}
