package br.com.imobiliaria.modelo;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.PostPersist;
import javax.persistence.PostRemove;
import javax.persistence.PostUpdate;

import org.hibernate.validator.constraints.NotEmpty;

import br.com.imobiliaria.bean.AcaoFeitaBean;
import br.com.imobiliaria.dao.DAO;
import br.com.imobiliaria.util.ContextUtil;

@Entity
public class EstagioAtendimento extends EntityPadrao{
	private static final long serialVersionUID = 1L;

	@Column(unique=true) @NotEmpty(message="{campo.vazio}Estágio")
	private String estagio;

	private String descricao;

	//Tempo em horas para concluir o estágio
	private int horasNecessarias;

	@OneToMany(fetch=FetchType.LAZY, mappedBy="estagioAtendimento")
	private List<Comprador> compradores;
	
	@PostPersist
	public void postPersist(){
		new AcaoFeitaBean().gravar(new AcaoFeita(TipoAcao.Incluiu, Cadastro.Estágio_Atendimento, this.getId(), this.toString()));
		ContextUtil.getPersistMessage(this.estagio);
	}
	
	@PostRemove
	public void postRemove(){
		new AcaoFeitaBean().gravar(new AcaoFeita(TipoAcao.Deletou, Cadastro.Estágio_Atendimento, this.getId(), this.toString()));
		ContextUtil.getDeleteMessage(this.estagio);
	}
	
	@PostUpdate
	public void postUpdate(){
		new AcaoFeitaBean().gravar(new AcaoFeita(TipoAcao.Alterou, Cadastro.Estágio_Atendimento, this.getId(), this.toString()));
		ContextUtil.getUpdateMessage(this.estagio);
	}

	public String getEstagio() {
		return estagio;
	}

	public void setEstagio(String estagio) {
		this.estagio = estagio;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public int getHorasNecessarias() {
		return horasNecessarias;
	}

	public void setHorasNecessarias(int horasNecessarias) {
		this.horasNecessarias = horasNecessarias;
	}
	
	public List<Comprador> getCompradores() {
		return new DAO<Comprador>(Comprador.class).listaPorId("compradoresDoEstagio", this.getId());
	}

	public void setCompradores(List<Comprador> compradores) {
		this.compradores = compradores;
	}
	
	@Override
	public String toString() {
		return this.getEstagio() + " - " + this.getHorasNecessarias() + " horas.";
	}
}
