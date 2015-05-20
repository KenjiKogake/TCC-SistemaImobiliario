package br.com.imobiliaria.modelo;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PostPersist;
import javax.persistence.PostRemove;
import javax.persistence.PostUpdate;
import javax.validation.constraints.DecimalMin;

import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;

import br.com.imobiliaria.bean.AcaoFeitaBean;
import br.com.imobiliaria.dao.DAO;
import br.com.imobiliaria.util.ContextUtil;

@NamedQueries({
	@NamedQuery(name="areasUteisDaTorre", query="SELECT a FROM AreaUtil a WHERE a.torre.id = :pId")
})

@Entity
public class AreaUtil extends EntityPadrao{
	private static final long serialVersionUID = 1L;

	@DecimalMin(value="1.0", message="Área Útil inválida.")
	private double areaUtil;
	
	@OneToMany(mappedBy="areaUtil", fetch=FetchType.LAZY)
	private List<Apartamento> apartamentos;

	private String descricao;
	
	@ManyToOne(optional=false)
	private Torre torre;

	@PostPersist
	public void postPersist(){
		new AcaoFeitaBean().gravar(new AcaoFeita(TipoAcao.Incluiu, Cadastro.Áreas_Úteis_da_Torre, this.getId(), this.toString()));
		ContextUtil.getPersistMessage(this.toString());
	}
	
	@PostRemove
	public void postRemove(){
		new AcaoFeitaBean().gravar(new AcaoFeita(TipoAcao.Deletou, Cadastro.Áreas_Úteis_da_Torre, this.getId(), this.toString()));
		ContextUtil.getDeleteMessage(this.toString());
	}
	
	@PostUpdate
	public void postUpdate(){
		new AcaoFeitaBean().gravar(new AcaoFeita(TipoAcao.Alterou, Cadastro.Áreas_Úteis_da_Torre, this.getId(), this.toString()));
		ContextUtil.getUpdateMessage(this.toString());
	}

	public double getAreaUtil() {
		return areaUtil;
	}

	public void setAreaUtil(double areaUtil) {
		this.areaUtil = areaUtil;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Torre getTorre() {
		return torre;
	}

	public void setTorre(Torre torre) {
		this.torre = torre;
	}
	
	public List<Apartamento> getApartamentos() {
		return new DAO<Apartamento>(Apartamento.class).listaPorId("apartamentosDaTorre", this.getId());
	}
	
	public void setApartamentos(List<Apartamento> apartamentos) {
		this.apartamentos = apartamentos;
	}

	@Override
	public String toString() {
		return this.getTorre() + " - " + this.getAreaUtil();
	}
}
