package br.com.imobiliaria.util;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JPAUtil {

	private static EntityManagerFactory factory = Persistence.createEntityManagerFactory("imobiliaria-pu");
	
	public static EntityManager criaEntityManager(){
		return factory.createEntityManager();
	}
	
	public void close(EntityManager em){
		em.close();
	}
}
