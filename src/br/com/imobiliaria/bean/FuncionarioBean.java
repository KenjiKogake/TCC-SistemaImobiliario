package br.com.imobiliaria.bean;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;

import br.com.imobiliaria.dao.DAO;
import br.com.imobiliaria.modelo.Funcionario;
import br.com.imobiliaria.modelo.FuncionarioLogado;
import br.com.imobiliaria.modelo.Status;
import br.com.imobiliaria.util.ContextUtil;
import br.com.imobiliaria.util.JPAUtil;

@ManagedBean
@ViewScoped
public class FuncionarioBean implements Serializable{
	private static final long serialVersionUID = 1L;

	private Funcionario funcionario = new Funcionario();
	private DAO<Funcionario> dao = new DAO<Funcionario>(Funcionario.class);
	private List<Funcionario> funcionariosFiltrados;
	private List<Funcionario> funcionarios;
	private String novaSenha;
	
	public void atualizaLista(){
		this.funcionarios = dao.listaTodos();
	}
	
	public Funcionario getFuncionario() {
		return funcionario;
	}
	
	public List<Funcionario> getFuncionariosAtivos(){
		return dao.listaNamedQuery("funcionariosAtivos");
	}
	
	public List<Funcionario> getCorretores(){
		return dao.listaNamedQuery("funcionariosAtivosQueAtendemComprador");
	}
	
	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}
	
	public List<Funcionario> getFuncionarios(){
		return this.funcionarios;
	}
	
	public void setFuncionarios(List<Funcionario> funcionarios) {
		this.funcionarios = funcionarios;
	}

	public List<Funcionario> getFuncionariosFiltrados() {
		return funcionariosFiltrados;
	}

	public void setFuncionariosFiltrados(List<Funcionario> funcionariosFiltrados) {
		this.funcionariosFiltrados = funcionariosFiltrados;
	}

	public String getNovaSenha() {
		return novaSenha;
	}
	
	public void setNovaSenha(String novaSenha) {
		this.novaSenha = novaSenha;
	}
	
	public void gravarSenha(){
		if(this.funcionario.getSenha().equals(this.novaSenha)){
			ContextUtil.getAnyMessage("Senha Inválida");
		}else{
			this.funcionario.setAlterarSenhaProximoAcesso(false);
			this.funcionario.setSenha(this.novaSenha);
			
			gravar();
			
			FuncionarioLogado.setFuncionarioLogado(funcionario);
		}
	}
	
	public void limparFuncionario(){
		this.funcionario = new Funcionario();
	}
	
	public void gravar(){
		if(funcionario.getSenha() == null) funcionario.setSenha(funcionario.getUsuario());
		
		try {
			if(funcionario.getId() == null){
				dao.adiciona(funcionario);
			}
			else{
				dao.atualiza(funcionario);
			}
		}
		catch (PersistenceException e) {
			System.out.println("Persistence Exception");
			e.printStackTrace();
			if(e.getCause().toString().contains("Duplicate")){
				if (e.getCause().toString().contains("nome")) ContextUtil.getDuplicateMessage("Nome inserido");
				else if (e.getCause().toString().contains("celular")) ContextUtil.getDuplicateMessage("Celular inserido");
				else if (e.getCause().toString().contains("cpf")) ContextUtil.getDuplicateMessage("CPF inserido");
				else if (e.getCause().toString().contains("usuario")) ContextUtil.getDuplicateMessage("Usuário inserido");
			}
		}
		catch(Exception e){
			ContextUtil.getAnyMessage("Ocorreu um erro!");
			e.printStackTrace();
		}finally{
			atualizaLista();
		}
	}
	
	public void mudarStatus(){
		if(funcionario.getStatus() == Status.Ativo){
			funcionario.setStatus(Status.Inativo);
			funcionario.setDataSaida(Calendar.getInstance().getTime());
			ContextUtil.getDisableMessage(funcionario.toString());
		}else{
			funcionario.setStatus(Status.Ativo);
			ContextUtil.getReactivateMessage(funcionario.toString());
		}
		gravar();
	}
	
	public void resetarSenha(){
		funcionario.setAlterarSenhaProximoAcesso(true);
		funcionario.setSenha(funcionario.getUsuario());
		gravar();
	}
	
	public void entrar(){
		try {
			EntityManager em = JPAUtil.criaEntityManager();
			
			Funcionario funcionarioDoBanco = em.createNamedQuery("login", Funcionario.class)
					.setParameter("pUsuario", funcionario.getUsuario())
					.setParameter("pSenha", funcionario.getSenha())
					.getSingleResult();
			
			em.close();
			FuncionarioLogado.setFuncionarioLogado(funcionarioDoBanco);
		} catch (NoResultException e) {
			ContextUtil.getAnyMessage("Usuário ou Senha Inválidos.");
		}
	}
	
	public void logoff(){
		FuncionarioLogado.setFuncionarioLogado(null);
	}
	
	public boolean isNovo(){
		return this.funcionario.getId() == null;
	}
	
	public boolean isAtivo(){
		return this.funcionario.getStatus() == Status.Ativo;
	}
	
	public void onActionViewAlterarSenha(){
		this.funcionario = FuncionarioLogado.getFuncionarioLogado();
	}
	
	public void onActionView(){
		atualizaLista();
	}
}
