package br.com.imobiliaria.modelo;

import java.util.Calendar;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.PostPersist;
import javax.persistence.PostRemove;
import javax.persistence.PostUpdate;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Min;

import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;

import br.com.imobiliaria.bean.AcaoFeitaBean;
import br.com.imobiliaria.util.ContextUtil;

@NamedQueries({
	@NamedQuery(name="investimentosDaMidia", query="SELECT i FROM InvestimentoMidia i WHERE i.midia.id = :pId")
})

@Entity
@Table(uniqueConstraints={@UniqueConstraint(columnNames={"midia_id", "mes", "ano"})})
public class InvestimentoMidia extends EntityPadrao{
	private static final long serialVersionUID = 1L;
	
	private int mes = Calendar.getInstance().get(Calendar.MONTH) + 1;
	
	private int ano = Calendar.getInstance().get(Calendar.YEAR);
	
	@Min(value=1, message="{campo.vazio}Investimento.")
	private double investimento;
	
	@ManyToOne
	private Midia midia;
	
	@PostPersist
	public void postPersist(){
		new AcaoFeitaBean().gravar(new AcaoFeita(TipoAcao.Incluiu, Cadastro.Investimento_Mídia, this.getId(), this.toString()));
		ContextUtil.getPersistMessage(this.toString());
	}
	
	@PostRemove
	public void postRemove(){
		new AcaoFeitaBean().gravar(new AcaoFeita(TipoAcao.Deletou, Cadastro.Investimento_Mídia, this.getId(), this.toString()));
		ContextUtil.getDeleteMessage(this.toString());
	}
	
	@PostUpdate
	public void postUpdate(){
		new AcaoFeitaBean().gravar(new AcaoFeita(TipoAcao.Alterou, Cadastro.Investimento_Mídia, this.getId(), this.toString()));
		ContextUtil.getUpdateMessage(this.toString());
	}

	public Midia getMidia() {
		return midia;
	}

	public void setMidia(Midia midia) {
		this.midia = midia;
	}

	public int getMes() {
		return mes;
	}

	public void setMes(int mes) {
		this.mes = mes;
	}

	public int getAno() {
		return ano;
	}

	public void setAno(int ano) {
		this.ano = ano;
	}

	public double getInvestimento() {
		return investimento;
	}

	public void setInvestimento(double investimento) {
		this.investimento = investimento;
	}
	
	@Override
	public String toString() {
		return this.getMidia() + " - " + this.getMes() + " - " + this.getInvestimento();
	}
	
}
