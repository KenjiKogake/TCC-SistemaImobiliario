package br.com.imobiliaria.modelo;

import javax.persistence.Entity;
import javax.persistence.PostPersist;
import javax.persistence.PostRemove;
import javax.persistence.PostUpdate;

import br.com.imobiliaria.bean.AcaoFeitaBean;


@Entity
public class CaracteristicaCondominio extends EntityPadrao{
	private static final long serialVersionUID = 1L;

	//VERIFICAR COMO UTILIZAR TREE VIEW
	
	@PostPersist
	public void postPersist(){
		new AcaoFeitaBean().gravar(new AcaoFeita(TipoAcao.Incluiu, Cadastro.Características_Condomínio, this.getId(), this.toString()));
	}
	
	@PostRemove
	public void postRemove(){
		new AcaoFeitaBean().gravar(new AcaoFeita(TipoAcao.Deletou, Cadastro.Características_Condomínio, this.getId(), this.toString()));
	}
	
	@PostUpdate
	public void postUpdate(){
		new AcaoFeitaBean().gravar(new AcaoFeita(TipoAcao.Alterou, Cadastro.Características_Condomínio, this.getId(), this.toString()));
	}

}
