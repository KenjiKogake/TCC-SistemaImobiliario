package br.com.imobiliaria.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaQuery;

import br.com.imobiliaria.util.JPAUtil;

public class DAO<T> implements Serializable{

	private static final long serialVersionUID = 1L;

	private EntityManager em;
	private final Class<T> classe;
	
	public DAO(Class<T> classe) {
		this.classe = classe;
	}
	
	public void adiciona(T t) throws Exception{
		try {
			em = JPAUtil.criaEntityManager();
			// abre transacao
			em.getTransaction().begin();
			
			// persiste o objeto
			em.persist(t);
			
			// commita a transacao
			em.getTransaction().commit();
			
			// fecha a entity manager
			
		}finally{
			em.close();
		}			
	}

	public void remove(T t) throws Exception{
		try {
			em = JPAUtil.criaEntityManager();
			
			em.getTransaction().begin();
			
			em.remove(em.merge(t));
			
			em.getTransaction().commit();
		} finally{
			em.close();
		}
	}

	public void atualiza(T t) throws Exception{
		try {
			em = JPAUtil.criaEntityManager();
			
			em.getTransaction().begin();
			
			em.merge(t);
	
			em.getTransaction().commit();
			
		}finally{
			em.close();
		}	
	}

	public List<T> listaTodos() {
		List<T> lista = new ArrayList<T>();
		try {
			em = JPAUtil.criaEntityManager();
			
			CriteriaQuery<T> query = em.getCriteriaBuilder().createQuery(classe);
			query.select(query.from(classe));
			
			lista = em.createQuery(query).getResultList();
			
			return lista;
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			em.close();
		}
		return lista;
	}
	
	public List<T> listaPorId(String namedQuery, Long id){
		List<T> lista = new ArrayList<T>();
		try {
			em = JPAUtil.criaEntityManager();
			
			lista = em.createNamedQuery(namedQuery, classe)
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
	
	public List<T> listaNamedQuery(String namedQuery){
		List<T> lista = new ArrayList<T>();
		try {
			em = JPAUtil.criaEntityManager();
			
			lista = em.createNamedQuery(namedQuery, classe)
				.getResultList();
			
			return lista;
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			em.close();
		}
		return lista;
	}
	
	public List<T> listaNamedQuery(String namedQuery, int maxResult){
		List<T> lista = new ArrayList<T>();
		try {
			em = JPAUtil.criaEntityManager();
			
			lista = em.createNamedQuery(namedQuery, classe)
				.setMaxResults(maxResult)
				.getResultList();
			
			return lista;
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			em.close();
		}
		return lista;
	}
	
	public T buscaPorId(Long id) {
		try{
			System.out.println("Busca por Id");
			em = JPAUtil.criaEntityManager();
			T instancia = em.find(classe, id);
			return instancia;
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			em.close();
		}
		return null;
	}
	
}
