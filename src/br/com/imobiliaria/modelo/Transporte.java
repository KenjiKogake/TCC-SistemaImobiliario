package br.com.imobiliaria.modelo;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.PostPersist;
import javax.persistence.PostRemove;
import javax.persistence.PostUpdate;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.validator.constraints.NotEmpty;

import br.com.imobiliaria.bean.AcaoFeitaBean;
import br.com.imobiliaria.dao.DAO;
import br.com.imobiliaria.util.ContextUtil;

@Entity
@Table(uniqueConstraints={@UniqueConstraint(columnNames={"nome", "linha"})})
public class Transporte extends EntityPadrao{

	private static final long serialVersionUID = 1L;

	@Enumerated(EnumType.STRING)
	private TipoTransporte tipo;
	
	@NotEmpty(message="{campo.vazio}Transporte")
	private String nome;
	
	@NotEmpty(message="{campo.vazio}Linha")
	private String linha;

	@NotEmpty(message="{campo.vazio}Endereço")
	private String endereco;
	
	@OneToMany(fetch=FetchType.LAZY, mappedBy="id.transporte")
	private List<TransportesDoCondominio> condominiosDoTransporte = new ArrayList<TransportesDoCondominio>();
	
	@PostPersist
	public void postPersist(){
		new AcaoFeitaBean().gravar(new AcaoFeita(TipoAcao.Incluiu, Cadastro.Transporte_Público, this.getId(), this.toString()));
		ContextUtil.getPersistMessage(this.getNome());
	}
	
	@PostRemove
	public void postRemove(){
		new AcaoFeitaBean().gravar(new AcaoFeita(TipoAcao.Deletou, Cadastro.Transporte_Público, this.getId(), this.toString()));
		ContextUtil.getDeleteMessage(this.getNome());
	}
	
	@PostUpdate
	public void postUpdate(){
		new AcaoFeitaBean().gravar(new AcaoFeita(TipoAcao.Alterou, Cadastro.Transporte_Público, this.getId(), this.toString()));
		ContextUtil.getUpdateMessage(this.getNome());
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getLinha() {
		return linha;
	}

	public void setLinha(String linha) {
		this.linha = linha;
	}

	public List<TransportesDoCondominio> getCondominiosDoTransporte() {
		return new DAO<TransportesDoCondominio>(TransportesDoCondominio.class).listaPorId("condominiosDoTransporte", this.getId());
	}

	public void setCondominiosDoTransporte(
			List<TransportesDoCondominio> condominiosDoTransporte) {
		this.condominiosDoTransporte = condominiosDoTransporte;
	}

	public String getEndereco() {
		return endereco;
	}
	
	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}
	
	public TipoTransporte getTipo() {
		return tipo;
	}

	public void setTipo(TipoTransporte tipo) {
		this.tipo = tipo;
	}
	
	@Override
	public String toString() {
		return this.getNome();
	}
}
