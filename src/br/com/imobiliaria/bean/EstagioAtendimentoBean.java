package br.com.imobiliaria.bean;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.persistence.PersistenceException;

import br.com.imobiliaria.dao.DAO;
import br.com.imobiliaria.modelo.EstagioAtendimento;
import br.com.imobiliaria.util.ContextUtil;

@ManagedBean
@ViewScoped
public class EstagioAtendimentoBean {
	private EstagioAtendimento estagio = new EstagioAtendimento();
	private DAO<EstagioAtendimento> dao = new DAO<EstagioAtendimento>(EstagioAtendimento.class);
	private List<EstagioAtendimento> estagiosAtendimentoFiltrados;
	private List<EstagioAtendimento> estagiosAtendimento;
	
	private Long id;
	
	@PostConstruct
	public void postConstruct() {
		atualizaLista();
	}

	public void atualizaLista() {
		this.estagiosAtendimento = dao.listaTodos();
	}

	public EstagioAtendimento getEstagioAtendimento() {
		return estagio;
	}

	public void setEstagioAtendimento(EstagioAtendimento estagio) {
		this.estagio = estagio;
	}

	public List<EstagioAtendimento> getEstagiosAtendimento() {
		return this.estagiosAtendimento;
	}

	public EstagioAtendimento getEstagio() {
		return estagio;
	}

	public void setEstagio(EstagioAtendimento estagio) {
		this.estagio = estagio;
	}

	public List<EstagioAtendimento> getEstagiosAtendimentoFiltrados() {
		return estagiosAtendimentoFiltrados;
	}

	public void setEstagiosAtendimentoFiltrados(
			List<EstagioAtendimento> estagiosAtendimentoFiltrados) {
		this.estagiosAtendimentoFiltrados = estagiosAtendimentoFiltrados;
	}

	public void setEstagiosAtendimento(
			List<EstagioAtendimento> estagiosAtendimento) {
		this.estagiosAtendimento = estagiosAtendimento;
	}
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}

	public void gravar() {
		try {
			if (estagio.getId() == null)
				dao.adiciona(estagio);
			else
				dao.atualiza(estagio);

		} catch (PersistenceException e) {
			if (e.getCause().toString().contains("Duplicate")) {
				if (e.getCause().toString().contains("estagio"))
					ContextUtil.getDuplicateMessage("Estágio inserido");
			}
		} catch (Exception e) {
			ContextUtil.getAnyMessage("Ocorreu um erro!");
		} finally {
			atualizaLista();
		}
	}

	public void deletar() {
		try {
			dao.remove(estagio);
			atualizaLista();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void limparEstagioAtendimento() {
		this.estagio = new EstagioAtendimento();
	}

	public boolean isNovo() {
		return this.estagio.getId() == null;
	}

	public boolean isSemClientes() {
		return this.estagio.getCompradores() == null
				|| this.estagio.getCompradores().isEmpty();
	}

	public boolean isDeletavel() {
		return !isNovo() && isSemClientes();
	}
}
