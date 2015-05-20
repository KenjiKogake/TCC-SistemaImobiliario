package br.com.imobiliaria.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.persistence.PersistenceException;

import br.com.imobiliaria.dao.DAO;
import br.com.imobiliaria.modelo.PeriodicidadeVagaGaragem;
import br.com.imobiliaria.util.ContextUtil;

@ManagedBean
@ViewScoped
public class PeriodicidadeVagaGaragemBean implements Serializable{
	private static final long serialVersionUID = 1L;

	private PeriodicidadeVagaGaragem periodicidade = new PeriodicidadeVagaGaragem();
	private final DAO<PeriodicidadeVagaGaragem> dao = new DAO<PeriodicidadeVagaGaragem>(PeriodicidadeVagaGaragem.class);
	private List<PeriodicidadeVagaGaragem> periodicidades;
	private List<PeriodicidadeVagaGaragem> periodicidadesFiltradas;
	
	@PostConstruct
	public void postConstruct(){
		atualizaLista();
	}
	
	private void atualizaLista() {
		this.periodicidades = dao.listaTodos();
	}
	
	public List<PeriodicidadeVagaGaragem> getPeriodicidades() {
		return periodicidades;
	}

	public void setPeriodicidades(List<PeriodicidadeVagaGaragem> periodicidades) {
		this.periodicidades = periodicidades;
	}

	public List<PeriodicidadeVagaGaragem> getPeriodicidadesFiltradas() {
		return periodicidadesFiltradas;
	}

	public void setPeriodicidadesFiltradas(
			List<PeriodicidadeVagaGaragem> periodicidadesFiltradas) {
		this.periodicidadesFiltradas = periodicidadesFiltradas;
	}

	public PeriodicidadeVagaGaragem getPeriodicidade() {
		return periodicidade;
	}
	
	public void setPeriodicidade(PeriodicidadeVagaGaragem periodicidade) {
		this.periodicidade = periodicidade;
	}
	
	public void gravar(){
		try {
			if(periodicidade.getId() == null)
				dao.adiciona(periodicidade);
			else
				dao.atualiza(periodicidade);
			
		}catch (PersistenceException e) {
			if(e.getCause().toString().contains("Duplicate")) ContextUtil.getDuplicateMessage("Pariodicidade inserida");
		}
		catch(Exception e){
			ContextUtil.getAnyMessage("Ocorreu um erro!");
		}finally{
			atualizaLista();
		}
	}

	public void deletar(){
		try {
			dao.remove(periodicidade);
		} catch (PersistenceException e) {
			ContextUtil.getAnyMessage("Não pode ser excluído, existem vagas de garagem vinculadas.");
		} catch(Exception e){
			ContextUtil.getAnyMessage("Ocorreu um erro!");
		}finally{
			atualizaLista();
		}
	}
	
	public void limparPeriodicidade(){
		this.periodicidade = new PeriodicidadeVagaGaragem();
	}
	
	public boolean isNovo(){
		return this.periodicidade.getId() == null;
	}
}
