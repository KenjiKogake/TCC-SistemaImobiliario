package br.com.imobiliaria.modelo;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@NamedQueries({
	@NamedQuery(name="acoesDoFuncionario", query="SELECT a FROM AcaoFeita a WHERE a.quemCadastrou.id = :pId")
})

@Entity
public class AcaoFeita extends EntityPadrao{

	private static final long serialVersionUID = 1L;

	@Enumerated(EnumType.STRING)
	private TipoAcao tipoAcao;
	@Enumerated(EnumType.STRING)
	private Cadastro cadastro;
	
	private Long idDoCadastro;
	
	private String descricao;
	
	public AcaoFeita(TipoAcao tipoAcao, Cadastro cadastro, Long id, String descricao){
		this.tipoAcao = tipoAcao;
		this.cadastro = cadastro;
		this.idDoCadastro = id;
		this.descricao = descricao;
	}
	
	public AcaoFeita() {
	}

	public TipoAcao getTipoAcao() {
		return tipoAcao;
	}

	public void setTipoAcao(TipoAcao tipoAcao) {
		this.tipoAcao = tipoAcao;
	}

	public Cadastro getCadastro() {
		return cadastro;
	}

	public void setCadastro(Cadastro cadastro) {
		this.cadastro = cadastro;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Long getIdDoCadastro() {
		return idDoCadastro;
	}

	public void setIdDoCadastro(Long idDoCadastro) {
		this.idDoCadastro = idDoCadastro;
	}
}
