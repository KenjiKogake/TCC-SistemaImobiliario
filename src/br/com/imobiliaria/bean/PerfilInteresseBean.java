package br.com.imobiliaria.bean;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;

import br.com.imobiliaria.dao.DAO;
import br.com.imobiliaria.modelo.Apartamento;
import br.com.imobiliaria.modelo.Comprador;
import br.com.imobiliaria.modelo.PerfilInteresse;
import br.com.imobiliaria.util.ContextUtil;
import br.com.imobiliaria.util.JPAUtil;

@ManagedBean
@ViewScoped
public class PerfilInteresseBean {
	private DAO<PerfilInteresse> dao = new DAO<PerfilInteresse>(PerfilInteresse.class);
	private PerfilInteresse perfilInteresse = new PerfilInteresse();
	private EntityManager em;
	private List<Apartamento> apartamentos;
	
	public List<Apartamento> getApartamentos() {
		return apartamentos;
	}

	public void setApartamentos(List<Apartamento> apartamentos) {
		this.apartamentos = apartamentos;
	}

	public PerfilInteresse getPerfilInteresse() {
		return perfilInteresse;
	}

	public void setPerfilInteresse(PerfilInteresse perfilInteresse) {
		this.perfilInteresse = perfilInteresse;
	}

	public void limparPerfilInteresse(){
		this.perfilInteresse = new PerfilInteresse();
	}
	
	public boolean isNovo(){
		return this.perfilInteresse.getId() == null;
	}
	
	public void gravar(Comprador comprador){
		try {
			if(perfilInteresse.getId() == null){
				perfilInteresse.setComprador(comprador);
				dao.adiciona(perfilInteresse);
			}else
				dao.atualiza(perfilInteresse);
		} catch (Exception e) {
			ContextUtil.getAnyMessage("Ocorreu um erro. Entre em contato com o suporte.");
			e.printStackTrace();
		}
	}
	
	public void deletar(){
		try {
			dao.remove(perfilInteresse);
		} catch (Exception e) {
			ContextUtil.getAnyMessage("Ocorreu um erro. Entre em contato com o suporte.");
			e.printStackTrace();
		}
	}
	
	public void buscarApartamentos(){
		testeListaApartamentosFiltrados();
	}
	
	public void testeListaApartamentosFiltrados() {
		try {
			System.out.println("Lista apartamentos filtrados");
			
			em = JPAUtil.criaEntityManager();

			CriteriaBuilder cb = em.getCriteriaBuilder();
			
			CriteriaQuery<Apartamento> cq = cb.createQuery(Apartamento.class);
			
			Root<Apartamento> rootApto = cq.from(Apartamento.class);
//			Root <AreaUtil> rootArea = cq.from(AreaUtil.class);
			
			cq.select(rootApto);
			
			
			if(perfilInteresse.getAreaUtilInicial() != 0){
				Path<Double> path = rootApto.get("valorVenda");
				cq.where(cb.and(cb.between(path, perfilInteresse.getValorInicial(), perfilInteresse.getValorFinal())));
			}
			
			TypedQuery<Apartamento> query = em.createQuery(cq);
			 
			apartamentos = query.getResultList();
			
			System.out.println(apartamentos.size());
			
//				http://www.devmedia.com.br/hibernate-api-criteria-realizando-consultas/29627
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			em.close();
		}
	}
}
