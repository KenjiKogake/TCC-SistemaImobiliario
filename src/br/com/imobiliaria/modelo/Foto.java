package br.com.imobiliaria.modelo;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;
import org.hibernate.validator.constraints.NotEmpty;

@NamedQueries({
	@NamedQuery(name="fotosDoCondominio", query="SELECT f FROM Foto f WHERE f.idVinculado = :pId AND tipo = 'Condomínio'"),
	@NamedQuery(name="fotosDaTorre", query="SELECT f FROM Foto f WHERE f.idVinculado = :pId AND tipo = 'Torre'"),
	@NamedQuery(name="fotosDoApartamento", query="SELECT f FROM Foto f WHERE f.idVinculado = :pId AND tipo = 'Apartamento'"),
})

@javax.persistence.Entity
public class Foto extends Entity {
	private static final long serialVersionUID = 1L;

	@NotEmpty
    private String local;
    private String descricao;
    
    @Enumerated(EnumType.STRING)
    private TipoFoto tipo;
    
    private Long idVinculado;

    public String getDescricao() {
        return descricao;
    }
 
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

	public String getLocal() {
		return local;
	}

	public void setLocal(String local) {
		this.local = local;
	}

	public TipoFoto getTipo() {
		return tipo;
	}

	public void setTipo(TipoFoto tipo) {
		this.tipo = tipo;
	}

	public Long getIdVinculado() {
		return idVinculado;
	}

	public void setIdVinculado(Long idVinculado) {
		this.idVinculado = idVinculado;
	}

}


