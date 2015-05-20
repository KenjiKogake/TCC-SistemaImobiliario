package br.com.imobiliaria.modelo;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.PostPersist;
import javax.persistence.PostRemove;

import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;

import br.com.imobiliaria.bean.AcaoFeitaBean;

@NamedQueries({
	@NamedQuery(name="apartamentosFavoritosInativos", query="SELECT a FROM ApartamentosFavoritos a where a.id.comprador.id = :pId AND a.status = 'Inativo'"),
	@NamedQuery(name="apartamentosFavoritosAtivos", query="SELECT a FROM ApartamentosFavoritos a where a.id.comprador.id = :pId AND a.status = 'Ativo'"),
	@NamedQuery(name="compradoresInteressados", query="SELECT a FROM ApartamentosFavoritos a where a.id.apartamento.id = :pId AND a.status = 'Ativo'"),
})

@Entity
public class ApartamentosFavoritos{
	@EmbeddedId
	private ApartamentosFavoritosId id = new ApartamentosFavoritosId();
	
	private String comentario;
	
	@Enumerated(EnumType.STRING)
	private Status status = Status.Ativo;
	
	@PostPersist
	public void postPersist(){
		new AcaoFeitaBean().gravar(new AcaoFeita(TipoAcao.Incluiu, Cadastro.Apartamento_Favorito, (long) 0, this.toString()));
	}
	
	@PostRemove
	public void postRemove(){
		new AcaoFeitaBean().gravar(new AcaoFeita(TipoAcao.Deletou, Cadastro.Apartamento_Favorito, (long) 0, this.toString()));
	}
	
	public ApartamentosFavoritos(){}
	
	public ApartamentosFavoritos(Apartamento a, Comprador c){
		id.setApartamento(a);
		id.setComprador(c);
	}
	
	public ApartamentosFavoritosId getId() {
		return id;
	}

	public void setId(ApartamentosFavoritosId id) {
		this.id = id;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}
	
	public boolean isAtivo(){
		return this.status == Status.Ativo;
	}
	
	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	@Override
	public String toString() {
		return "Apartamento " + this.id.getApartamento().getNumeroApartamento() + 
				" favorito para " + this.id.getComprador().getCliente().getNome();
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
		ApartamentosFavoritos other = (ApartamentosFavoritos) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
