package br.com.imobiliaria.modelo;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;

@Embeddable
public class TransportesDoCondominioId implements Serializable{
	private static final long serialVersionUID = 1L;

	@ManyToOne(optional=false)
	private Transporte transporte;
	@ManyToOne(optional=false)
	private Condominio condominio;
	
	public Transporte getTransporte() {
		return transporte;
	}

	public void setTransporte(Transporte transporte) {
		this.transporte = transporte;
	}

	public Condominio getCondominio() {
		return condominio;
	}

	public void setCondominio(Condominio condominio) {
		this.condominio = condominio;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((condominio == null) ? 0 : condominio.hashCode());
		result = prime * result
				+ ((transporte == null) ? 0 : transporte.hashCode());
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
		TransportesDoCondominioId other = (TransportesDoCondominioId) obj;
		if (condominio == null) {
			if (other.condominio != null)
				return false;
		} else if (!condominio.equals(other.condominio))
			return false;
		if (transporte == null) {
			if (other.transporte != null)
				return false;
		} else if (!transporte.equals(other.transporte))
			return false;
		return true;
	}
	
}
