package br.com.imobiliaria.bean;

import java.util.Arrays;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.persistence.EntityManager;

import br.com.imobiliaria.util.JPAUtil;

@ManagedBean
@ViewScoped
public class MinimosMaximosBean {
	private int dormMax = 0;
	private int dormMin = 0;
	private int suiteMax = 0;
	private int suiteMin = 0;
	private double valorMax = 0;
	private double valorMin = 0;
	private double areaUtilMin = 0;
	private double areaUtilMax = 0;
	
	public MinimosMaximosBean(){
		pegaValores();
	}
	
	@SuppressWarnings("unchecked")
	public void pegaValores(){
		EntityManager em = null;
		try{
			em = JPAUtil.criaEntityManager();
			
			//Informações da Planta
			List<Integer> resultList = em.createQuery("SELECT MAX(p.dormitorios), MIN(p.dormitorios), MAX(p.suites), MIN(p.suites) FROM PlantaTorre p")
					.getResultList();
			
			List<Integer> list = null;
			for (Integer i : resultList) {
				list = Arrays.asList(i);
			}

			if(list.get(0) != null){
				dormMax = (int) list.get(0); 
				dormMin = (int) list.get(1);
				suiteMax = (int) list.get(2);
				suiteMin = (int) list.get(3);
			}
			
			//Valor Venda
			resultList = em.createQuery("SELECT MAX(a.valorVenda), MIN(a.valorVenda) FROM Apartamento a")
					.getResultList();
			
			list = null;
			for (Integer i : resultList) {
				list = Arrays.asList(i);
			}
				
			if(list.get(0) != null){
				valorMax = (double) list.get(0);
				valorMin = (double) list.get(1);
			}
			
			System.out.println(valorMax);
			System.out.println(valorMin);
			
			//Area Util
			resultList = em.createQuery("SELECT MAX(a.areaUtil), MIN(a.areaUtil) FROM AreaUtil a")
					.getResultList();
			
			list = null;
			for (Integer i : resultList) {
				list = Arrays.asList(i);
			}
				
			if(list.get(0) != null){
				areaUtilMax = (double) list.get(0);
				areaUtilMin = (double) list.get(1);
			}
			
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			em.close();
		}
	}
	
	public static void main(String[] args) {
		new MinimosMaximosBean().pegaValores();
	}
	
	public int getDormMax() {
		return dormMax;
	}

	public int getDormMin() {
		return dormMin;
	}
	
	public int getSuiteMax() {
		return suiteMax;
	}
	
	public int getSuiteMin() {
		return suiteMin;
	}
	
	public double getValorMax() {
		return valorMax;
	}
	
	public double getValorMin() {
		return valorMin;
	}
	
	public double getAreaUtilMax() {
		return areaUtilMax;
	}
	
	public double getAreaUtilMin() {
		return areaUtilMin;
	}
}
