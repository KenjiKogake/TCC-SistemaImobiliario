package br.com.imobiliaria.dao;

import java.util.Calendar;

import javax.persistence.EntityManager;

import br.com.imobiliaria.util.JPAUtil;

public class ApartamentoStatus {
	public void desativarRelacionados(Long id) {
		try {
			EntityManager em = JPAUtil.criaEntityManager();
			
			em.getTransaction().begin();
			
			em.createQuery("UPDATE ApartamentosFavoritos a set a.status = 'Inativo' where a.id.apartamento.id = :pId and a.status = 'Ativo'")
				.setParameter("pId", id).executeUpdate();
			
			System.out.println("Desativou apartamento dos favoritos!");
			
			em.createQuery("UPDATE Visita v set v.status = 'Cancelada', v.dataCancelamento = :pData where v.apartamento.id = :pId and (v.status = 'Pendente' or v.status = 'Confirmada')")
				.setParameter("pData", Calendar.getInstance().getTime())
				.setParameter("pId", id)
				.executeUpdate();
			
			System.out.println("Cancelou as Visitas neste Apartamento!");
			
			em.createQuery("UPDATE Negociacao n set n.status = 'Cancelada' WHERE n.apartamento.id = :pId and (n.status = 'Negociando')")
				.setParameter("pId", id)
				.executeUpdate();
			
			System.out.println("Cancelou as Negociações neste Apartamento!");
			
			em.getTransaction().commit();
			
			em.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
