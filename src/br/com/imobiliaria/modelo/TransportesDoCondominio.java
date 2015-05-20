package br.com.imobiliaria.modelo;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.PostPersist;
import javax.persistence.PostRemove;

import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;

import br.com.imobiliaria.bean.AcaoFeitaBean;

@NamedQueries({
	@NamedQuery(name="transportesDoCondominio", query="SELECT t FROM TransportesDoCondominio t where t.id.condominio.id = :pId"),
	@NamedQuery(name="condominiosDoTransporte", query="SELECT t FROM TransportesDoCondominio t where t.id.transporte.id = :pId"),
})

@Entity
public class TransportesDoCondominio {
	@EmbeddedId
	private TransportesDoCondominioId id = new TransportesDoCondominioId();
	
	private int tempo;
	
	private int distancia;
	
	public TransportesDoCondominio(Transporte t, Condominio c, int tempo, int distancia){
		this.tempo = tempo;
		this.distancia = distancia;
		this.id.setCondominio(c);
		this.id.setTransporte(t);
	}
	
	public TransportesDoCondominio(){}
	
	@PostPersist
	public void postPersist(){
		new AcaoFeitaBean().gravar(new AcaoFeita(TipoAcao.Incluiu, Cadastro.Apartamento_Favorito, (long) 0, this.toString()));
	}
	
	@PostRemove
	public void postRemove(){
		new AcaoFeitaBean().gravar(new AcaoFeita(TipoAcao.Deletou, Cadastro.Apartamento_Favorito, (long) 0, this.toString()));
	}
	
	public TransportesDoCondominioId getId() {
		return id;
	}

	public void setId(TransportesDoCondominioId id) {
		this.id = id;
	}

	public int getTempo() {
		return tempo / 60;
	}

	public void setTempo(int tempo) {
		this.tempo = tempo;
	}

	public int getDistancia() {
		return distancia;
	}

	public void setDistancia(int distancia) {
		this.distancia = distancia;
	}
	
	@Override
	public String toString() {
		return this.id.getTransporte().getNome() + " no " + this.id.getCondominio().getNome();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TransportesDoCondominio other = (TransportesDoCondominio) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
