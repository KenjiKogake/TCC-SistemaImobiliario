package br.com.imobiliaria.bean;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import br.com.imobiliaria.dao.DAO;
import br.com.imobiliaria.modelo.AcaoFeita;

@RequestScoped
@ManagedBean
public class AcaoFeitaBean {
	private AcaoFeita acao = new AcaoFeita();
	private DAO<AcaoFeita> dao = new DAO<AcaoFeita>(AcaoFeita.class);
	private List<AcaoFeita> acoes;
	private List<AcaoFeita> acoesFiltradas;
	
	public AcaoFeita getAcao() {
		return acao;
	}
	
	public void setAcao(AcaoFeita acao) {
		this.acao = acao;
	}
	
	public void atualizaLista() {
		acoes = dao.listaTodos();
	}

	public List<AcaoFeita> getAcoes() {
		return acoes;
	}

	public void setAcoes(List<AcaoFeita> acoes) {
		this.acoes = acoes;
	}

	public List<AcaoFeita> getAcoesFiltradas() {
		return acoesFiltradas;
	}

	public void setAcoesFiltradas(List<AcaoFeita> acoesFiltradas) {
		this.acoesFiltradas = acoesFiltradas;
	}

	public void gravar(AcaoFeita acao) {
		try {
			dao.adiciona(acao);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
