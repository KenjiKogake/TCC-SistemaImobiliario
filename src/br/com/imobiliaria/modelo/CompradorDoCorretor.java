package br.com.imobiliaria.modelo;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.PostPersist;
import javax.persistence.PostRemove;

import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;

import br.com.imobiliaria.bean.AcaoFeitaBean;

@NamedQueries({
	@NamedQuery(name="compradoresDoCorretor", query="SELECT c FROM CompradorDoCorretor c where c.corretor.id = :pId"),
	@NamedQuery(name="corretoresDoComprador", query="SELECT c FROM CompradorDoCorretor c where c.comprador.id = :pId"),
	@NamedQuery(name="corretorDoComprador", query="SELECT c FROM CompradorDoCorretor c WHERE c.comprador.id = :pId AND c.status = 'Ativo'")
})

@Entity
public class CompradorDoCorretor implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne(optional=false)
	private Funcionario corretor;
	@ManyToOne(optional=false)
	private Comprador comprador;
	
	private Calendar dataInicio = Calendar.getInstance();
	
	private Date dataFim;
	
	@Enumerated(EnumType.STRING)
	private Status status = Status.Ativo;
	
	public CompradorDoCorretor(Comprador comprador, Funcionario corretor){
		this.setComprador(comprador);
		this.setCorretor(corretor);
	}
	
	public CompradorDoCorretor(){}
	
	@PostPersist
	public void postPersist(){
		new AcaoFeitaBean().gravar(new AcaoFeita(TipoAcao.Incluiu, Cadastro.Comprador_para_Corretor, (long) 0, this.toString()));
	}
	
	@PostRemove
	public void postRemove(){
		new AcaoFeitaBean().gravar(new AcaoFeita(TipoAcao.Deletou, Cadastro.Comprador_para_Corretor, (long) 0, this.toString()));
	}
	
	public Funcionario getCorretor() {
		return corretor;
	}

	public void setCorretor(Funcionario corretor) {
		this.corretor = corretor;
	}

	public Comprador getComprador() {
		return comprador;
	}

	public void setComprador(Comprador comprador) {
		this.comprador = comprador;
	}
	
	public Calendar getDataInicio() {
		return dataInicio;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setDataInicio(Calendar dataInicio) {
		this.dataInicio = dataInicio;
	}

	public Date getDataFim() {
		return dataFim;
	}

	public void setDataFim(Date dataFim) {
		this.dataFim = dataFim;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
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
		CompradorDoCorretor other = (CompradorDoCorretor) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
