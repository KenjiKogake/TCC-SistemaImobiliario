package br.com.imobiliaria.modelo;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.PostPersist;
import javax.persistence.PostRemove;
import javax.persistence.PostUpdate;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;
import org.hibernate.validator.constraints.NotEmpty;

import br.com.imobiliaria.bean.AcaoFeitaBean;
import br.com.imobiliaria.util.ContextUtil;

@NamedQueries({
	@NamedQuery(name="vagasDoApartamento", query="SELECT v FROM VagaGaragem v WHERE v.apartamento.id = :pId")
})

@Entity
@Table(uniqueConstraints={@UniqueConstraint(columnNames={"numeroDaVaga", "apartamento_id"})})
public class VagaGaragem extends EntityPadrao{
	private static final long serialVersionUID = 1L;
	
	@ManyToOne(optional=false) @NotNull(message="{campo.vazio}Tipo")
	private TipoVagaGaragem tipoVagaGaragem;
	
	@ManyToOne(optional=false) @NotNull(message="{campo.vazio}Periodicidade")
	private PeriodicidadeVagaGaragem periodicidadeVagaGaragem;

	@NotEmpty(message="{campo.vazio}Número da Vaga.")
	private String numeroDaVaga;
	
	private String andarDaVaga;

	@ManyToOne(optional=false)
	private Apartamento apartamento;
	
	@PostPersist
	public void postPersist(){
		new AcaoFeitaBean().gravar(new AcaoFeita(TipoAcao.Incluiu, Cadastro.Vaga_Garagem_do_Apartamento, this.getId(), this.toString()));
		ContextUtil.getPersistMessage(this.toString());
	}
	
	@PostRemove
	public void postRemove(){
		new AcaoFeitaBean().gravar(new AcaoFeita(TipoAcao.Deletou, Cadastro.Vaga_Garagem_do_Apartamento, this.getId(), this.toString()));
		ContextUtil.getDeleteMessage(this.toString());
	}
	
	@PostUpdate
	public void postUpdate(){
		new AcaoFeitaBean().gravar(new AcaoFeita(TipoAcao.Alterou, Cadastro.Vaga_Garagem_do_Apartamento, this.getId(), this.toString()));
		ContextUtil.getUpdateMessage(this.toString());
	}
	
	public TipoVagaGaragem getTipoVagaGaragem() {
		return tipoVagaGaragem;
	}

	public void setTipoVagaGaragem(TipoVagaGaragem tipoVagaGaragem) {
		this.tipoVagaGaragem = tipoVagaGaragem;
	}

	public PeriodicidadeVagaGaragem getPeriodicidadeVagaGaragem() {
		return periodicidadeVagaGaragem;
	}

	public void setPeriodicidadeVagaGaragem(
			PeriodicidadeVagaGaragem periodicidadeVagaGaragem) {
		this.periodicidadeVagaGaragem = periodicidadeVagaGaragem;
	}

	public String getNumeroDaVaga() {
		return numeroDaVaga;
	}

	public void setNumeroDaVaga(String numeroDaVaga) {
		this.numeroDaVaga = numeroDaVaga;
	}
	
	public Apartamento getApartamento() {
		return apartamento;
	}
	
	public String getAndarDaVaga() {
		return andarDaVaga;
	}

	public void setAndarDaVaga(String andarDaVaga) {
		this.andarDaVaga = andarDaVaga;
	}
	
	public void setApartamento(Apartamento apartamento) {
		this.apartamento = apartamento;
	}
	
	@Override
	public String toString() {
		return "Vaga " + this.numeroDaVaga;
	}
}
