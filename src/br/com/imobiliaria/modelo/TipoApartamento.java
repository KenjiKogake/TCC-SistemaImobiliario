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
public class TipoApartamento extends EntityPadrao{
	private static final long serialVersionUID = 1L;

	@NotEmpty(message="{campo.vazio}Tipo de Apartamento") @Column(unique=true)
	private String nome;
	
	private String descricao;
	
	@OneToMany(mappedBy="tipoApartamento", fetch=FetchType.LAZY)
	private List<Apartamento> apartamentos;
	
	@PostPersist
	public void postPersist(){
		new AcaoFeitaBean().gravar(new AcaoFeita(TipoAcao.Incluiu, Cadastro.Perfil_de_Interesse, this.getId(), this.toString()));
		ContextUtil.getPersistMessage(this.toString());
	}
	
	@PostRemove
	public void postRemove(){
		new AcaoFeitaBean().gravar(new AcaoFeita(TipoAcao.Deletou, Cadastro.Perfil_de_Interesse, this.getId(), this.toString()));
		ContextUtil.getDeleteMessage(this.toString());
	}
	
	@PostUpdate
	public void postUpdate(){
		new AcaoFeitaBean().gravar(new AcaoFeita(TipoAcao.Alterou, Cadastro.Perfil_de_Interesse, this.getId(), this.toString()));
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

	public List<Apartamento> getApartamentos() {
		return new DAO<Apartamento>(Apartamento.class).listaPorId("apartamentosPorTipo", this.getId());
	}
	
	public void setApartamentos(List<Apartamento> apartamentos) {
		this.apartamentos = apartamentos;
	}
	
	@Override
	public String toString() {
		return this.getNome();
	}
	
}
