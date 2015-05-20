package br.com.imobiliaria.modelo;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PostPersist;
import javax.persistence.PostUpdate;
import javax.validation.constraints.Max;

import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;
import org.hibernate.validator.constraints.NotEmpty;

import br.com.imobiliaria.bean.AcaoFeitaBean;
import br.com.imobiliaria.dao.DAO;
import br.com.imobiliaria.util.ContextUtil;

@NamedQueries({
	@NamedQuery(name="torresDoCondominio", query="SELECT t FROM Torre t WHERE t.condominio.id = :pId")
})

@Entity
public class Torre extends EntityPadrao{
	private static final long serialVersionUID = 1L;

	@NotEmpty(message="{campo.vazio}Torre.")
	private String nome;
	private String identificacao;
	
	@Max(value=50, message="{numero.invalido}Elevadores.")
	private int elevadores;
	@Max(value=50, message="{numero.invalido}Andares.")
	private int andares;
	@Max(value=40, message="{numero.invalido}Aptos por Andar.")
	private int apartamentosPorAndar;
	
	@ManyToOne(optional=false)
	private Condominio condominio;
	
	@OneToMany(mappedBy="torre", fetch=FetchType.LAZY)
	private List<PlantaTorre> plantas;

	@OneToMany(mappedBy="torre", fetch=FetchType.LAZY)
	private List<Apartamento> apartamentos;

	@OneToMany(mappedBy="torre", fetch=FetchType.LAZY)
	private List<AreaUtil> areasUteis;
	
	@PostPersist
	public void postPersist(){
		new AcaoFeitaBean().gravar(new AcaoFeita(TipoAcao.Incluiu, Cadastro.Torre, this.getId(), this.toString()));
		ContextUtil.getPersistMessage(this.toString());
	}
	
	@PostUpdate
	public void postUpdate(){
		new AcaoFeitaBean().gravar(new AcaoFeita(TipoAcao.Alterou, Cadastro.Torre, this.getId(), this.toString()));
		ContextUtil.getUpdateMessage(this.toString());
	}
	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getIdentificacao() {
		return identificacao;
	}

	public void setIdentificacao(String identificacao) {
		this.identificacao = identificacao;
	}

	public Condominio getCondominio() {
		return condominio;
	}

	public void setCondominio(Condominio condominio) {
		this.condominio = condominio;
	}

	public int getElevadores() {
		return elevadores;
	}

	public void setElevadores(int elevadores) {
		this.elevadores = elevadores;
	}

	public int getAndares() {
		return andares;
	}

	public void setAndares(int andares) {
		this.andares = andares;
	}

	public int getApartamentosPorAndar() {
		return apartamentosPorAndar;
	}

	public void setApartamentosPorAndar(int apartamentosPorAndar) {
		this.apartamentosPorAndar = apartamentosPorAndar;
	}
	
	public List<PlantaTorre> getPlantas() {
		return new DAO<PlantaTorre>(PlantaTorre.class).listaPorId("plantasDaTorre", this.getId());
	}
	
	public void setPlantas(List<PlantaTorre> plantas) {
		this.plantas = plantas;
	}

	public List<Apartamento> getApartamentos() {
		return new DAO<Apartamento>(Apartamento.class).listaPorId("apartamentosDaTorre", this.getId());
	}
	
	public void setApartamentos(List<Apartamento> apartamentos) {
		this.apartamentos = apartamentos;
	}
	
	public List<AreaUtil> getAreasUteis() {
		return new DAO<AreaUtil>(AreaUtil.class).listaPorId("areasUteisDaTorre", this.getId());
	}
	
	public void setAreasUteis(List<AreaUtil> areasUteis) {
		this.areasUteis = areasUteis;
	}
	
	public List<Foto> getFotos() {
		return new DAO<Foto>(Foto.class).listaPorId("fotosDaTorre", this.getId());
	}

	public boolean isComFotos(){
		return !getFotos().isEmpty();
	}
	
	@Override
	public String toString() {
		return this.getCondominio() + " - " + this.getNome();
	}
}
