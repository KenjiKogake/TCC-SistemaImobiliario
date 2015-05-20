package br.com.imobiliaria.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaQuery;

import br.com.imobiliaria.modelo.Cliente;
import br.com.imobiliaria.modelo.ClienteLogado;
import br.com.imobiliaria.modelo.FollowUpComprador;
import br.com.imobiliaria.util.ContextUtil;
import br.com.imobiliaria.util.JPAUtil;

public class DAOCliente implements Serializable{

	private static final long serialVersionUID = 1L;

	private EntityManager em;
	
	public void adiciona(Cliente c) throws Exception{
		try {
			em = JPAUtil.criaEntityManager();
			// abre transacao
			em.getTransaction().begin();
			
			// persiste o objeto
			em.persist(c);
			
			// commita a transacao
			em.getTransaction().commit();
			
			// fecha a entity manager
			
		}finally{
			em.close();
		}			
	}

	public void remove(Cliente c) throws Exception{
		try {
			em = JPAUtil.criaEntityManager();
			
			em.getTransaction().begin();
			
			em.remove(em.merge(c));
			
			em.getTransaction().commit();
		} finally{
			em.close();
		}
	}

	public void atualiza(Cliente c) throws Exception{
		try {
			em = JPAUtil.criaEntityManager();
			
			em.getTransaction().begin();
			
			em.merge(c);
	
			em.getTransaction().commit();
			
		}finally{
			em.close();
		}	
	}

	public List<Cliente> listaTodos() {
		List<Cliente> lista = new ArrayList<Cliente>();
		try {
			em = JPAUtil.criaEntityManager();
			
			CriteriaQuery<Cliente> query = em.getCriteriaBuilder().createQuery(Cliente.class);
			query.select(query.from(Cliente.class));
			
			lista = em.createQuery(query).getResultList();
			
			return lista;
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			em.close();
		}
		return lista;
	}
	
	public List<Cliente> listaPorId(String namedQuery, Long id){
		List<Cliente> lista = new ArrayList<Cliente>();
		try {
			em = JPAUtil.criaEntityManager();
			
			lista = em.createNamedQuery(namedQuery, Cliente.class)
				.setParameter("pId", id)
				.getResultList();
			
			return lista;
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			em.close();
		}
		return lista;
	}

	public Cliente buscaPorComprador(Long id) {
		try{
			em = JPAUtil.criaEntityManager();
			Cliente cliente = em.createQuery("SELECT c FROM Cliente c WHERE c.comprador.id = :pId", Cliente.class)
					.setParameter("pId", id)
					.getSingleResult();
			return cliente;
		}catch (Exception e) {
			e.printStackTrace();
			return new Cliente();
		}finally{
			em.close();
		}
	}

	public Cliente buscaPorVendedor(Long id) {
		try{
			em = JPAUtil.criaEntityManager();
			Cliente cliente = em.createNamedQuery("SELECT c FROM Cliente c WHERE c.vendedor.id = :pId", Cliente.class)
					.setParameter("pId", id)
					.getSingleResult();
			return cliente;
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			em.close();
		}
		return null;
	}
	
	public void logar(String email, String senha){
		try {
			em = JPAUtil.criaEntityManager();
			Cliente clienteDoBanco = em.createNamedQuery("loginCliente", Cliente.class)
					.setParameter("pEmail", email)
					.setParameter("pSenha", senha)
					.getSingleResult();
			
			em.close();
			ClienteLogado.setClienteLogado(clienteDoBanco);
		} catch (NoResultException e) {
			e.printStackTrace();
			ContextUtil.getAnyMessage("Usuário ou Senha Inválidos.");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public Cliente buscaPorId(Long id) {
		try{
			em = JPAUtil.criaEntityManager();
			Cliente cliente = em.find(Cliente.class, id);
			return cliente;
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			em.close();
		}
		return null;
	}
	
	public FollowUpComprador buscaFollowUpPorComprador(Long id){
		try{
			em = JPAUtil.criaEntityManager();
			FollowUpComprador followUp = em.createNamedQuery("ultimoFollowUp", FollowUpComprador.class)
					.setParameter("pId", id)
					.setMaxResults(1)
					.getSingleResult();
			return followUp;
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			em.close();
		}
		return null;
	}
}
