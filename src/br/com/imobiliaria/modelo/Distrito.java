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
public class Distrito extends EntityPadrao{
	private static final long serialVersionUID = 1L;

	@NotEmpty(message="{campo.vazio}Distrito.") @Column(unique=true)
	private String nome;
	@NotEmpty(message="{campo.vazio}Descrição.")
	private String descricao;

	@OneToMany(mappedBy = "distrito", fetch=FetchType.LAZY)
	private List<Condominio> condominios;

	@PostPersist
	public void postPersist(){
		new AcaoFeitaBean().gravar(new AcaoFeita(TipoAcao.Incluiu, Cadastro.Distrito, this.getId(), this.toString()));
		ContextUtil.getPersistMessage(this.toString());
	}
	
	@PostRemove
	public void postRemove(){
		new AcaoFeitaBean().gravar(new AcaoFeita(TipoAcao.Deletou, Cadastro.Distrito, this.getId(), this.toString()));
		ContextUtil.getDeleteMessage(this.toString());
	}
	
	@PostUpdate
	public void postUpdate(){
		new AcaoFeitaBean().gravar(new AcaoFeita(TipoAcao.Alterou, Cadastro.Distrito, this.getId(), this.toString()));
		ContextUtil.getUpdateMessage(this.toString());
	}
	
	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public List<Condominio> getCondominios() {
		return new DAO<Condominio>(Condominio.class).listaPorId("condominiosDoDistrito", this.getId());
	}

	public void setCondominios(List<Condominio> condominios) {
		this.condominios = condominios;
	}

	@Override
	public String toString() {
		return this.getNome();
	}

}
