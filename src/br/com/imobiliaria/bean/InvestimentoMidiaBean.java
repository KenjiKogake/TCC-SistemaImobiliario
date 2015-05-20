package br.com.imobiliaria.bean;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.persistence.PersistenceException;

import org.primefaces.event.SelectEvent;

import br.com.imobiliaria.dao.DAO;
import br.com.imobiliaria.modelo.InvestimentoMidia;
import br.com.imobiliaria.modelo.Midia;
import br.com.imobiliaria.util.ContextUtil;

@ManagedBean
@ViewScoped
public class InvestimentoMidiaBean {
	private DAO<InvestimentoMidia> dao = new DAO<InvestimentoMidia>(InvestimentoMidia.class);
	private InvestimentoMidia investimentoMidia = new InvestimentoMidia();
	
	public InvestimentoMidia getInvestimentoMidia() {
		return investimentoMidia;
	}

	public void setInvestimentoMidia(InvestimentoMidia investimentoMidia) {
		this.investimentoMidia = investimentoMidia;
	}

	public void gravar(Midia midia){
		if(investimentoMidia.getMidia() == null) investimentoMidia.setMidia(midia);
		try {
			if(investimentoMidia.getId() == null)
				dao.adiciona(investimentoMidia);
			else
				dao.atualiza(investimentoMidia);
			
			novoInvestimento();
		}  catch (PersistenceException e) {
			if (e.getCause().toString().contains("Duplicate"))
				ContextUtil.getAnyMessage("Já existem valores para este mês");
		} catch (Exception e) {
			ContextUtil.getAnyMessage("Ocorreu um erro!");
		}
	}

	public void deletar(){
		try {
			dao.remove(investimentoMidia);
			novoInvestimento();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
		
	public void novoInvestimento(){
		this.investimentoMidia = new InvestimentoMidia();
	}
	
	public boolean isNovo(){
		return this.investimentoMidia.getId() == null;
	}
	
	public void onRowSelect(SelectEvent event) {
        this.investimentoMidia = (InvestimentoMidia) event.getObject();
    }
}
