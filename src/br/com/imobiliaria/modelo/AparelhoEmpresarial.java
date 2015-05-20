package br.com.imobiliaria.modelo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.PostPersist;
import javax.persistence.PostRemove;
import javax.persistence.PostUpdate;

import org.hibernate.validator.constraints.NotEmpty;

import br.com.imobiliaria.bean.AcaoFeitaBean;
import br.com.imobiliaria.util.ContextUtil;

@Entity
public class AparelhoEmpresarial extends EntityPadrao {

	private static final long serialVersionUID = 1L;

	@OneToOne(mappedBy = "aparelhoEmpresarial")
	private Funcionario funcionario;

	@NotEmpty(message="{campo.vazio}Aparelho.")
	private String aparelho;
	
	@NotEmpty(message="{campo.vazio}Marca.")
	private String marca;
	
	@Column(unique=true) @NotEmpty(message="{campo.vazio}IMEI.")
	private String imei;

	@PostPersist
	public void postPersist(){
		new AcaoFeitaBean().gravar(new AcaoFeita(TipoAcao.Incluiu, Cadastro.Aparelho_Empresarial, this.getId(), this.toString()));
		ContextUtil.getPersistMessage(this.toString());
	}
	
	@PostRemove
	public void postRemove(){
		new AcaoFeitaBean().gravar(new AcaoFeita(TipoAcao.Deletou, Cadastro.Aparelho_Empresarial, this.getId(), this.toString()));
		ContextUtil.getDeleteMessage(this.toString());
	}
	
	@PostUpdate
	public void postUpdate(){
		new AcaoFeitaBean().gravar(new AcaoFeita(TipoAcao.Alterou, Cadastro.Aparelho_Empresarial, this.getId(), this.toString()));
		ContextUtil.getUpdateMessage(this.toString());
	}
	
	public Funcionario getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}

	public String getAparelho() {
		return aparelho;
	}

	public void setAparelho(String aparelho) {
		this.aparelho = aparelho;
	}

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	@Override
	public String toString() {
		return this.getImei();
	}
}
