package br.com.imobiliaria.dao;

import javax.persistence.EntityManager;

import br.com.imobiliaria.modelo.EnumNivelAcesso;
import br.com.imobiliaria.modelo.Funcionario;
import br.com.imobiliaria.modelo.NivelAcesso;
import br.com.imobiliaria.util.JPAUtil;

public class PopulaBanco {
	public static void main(String[] args) {
		EntityManager em = JPAUtil.criaEntityManager();
		
//		NumeroEmpresarial numero2 = new NumeroEmpresarial();
//		numero2.setNumero("(11)99965-4908");
//		
//		NumeroEmpresarial numeroEmpresarial = new NumeroEmpresarial();
//		numeroEmpresarial.setNumero("(11)97229-9380");
		
		em.getTransaction().begin();
		
		
//		em.persist(numeroEmpresarial);
//		em.persist(numero2);
		
//		AparelhoEmpresarial aparelhoEmpresarial = new AparelhoEmpresarial();
//		aparelhoEmpresarial.setAparelho("Iphone");
//		aparelhoEmpresarial.setMarca("Apple");
//		aparelhoEmpresarial.setImei("111111-11-111111-1");
		
//		em.persist(aparelhoEmpresarial);
		
//		Cargo cargo = new Cargo();
//		cargo.setDescricao("Cargo 1");
		
//		em.persist(cargo);
		
//		Departamento departamento = new Departamento();
//		departamento.setDescricao("TI");
//		
//		em.persist(departamento);
		
		Funcionario funcionario1 = new Funcionario();
		funcionario1.setNome("Eduardo ");
		funcionario1.setSobrenome("Kogake");
		funcionario1.setUsuario("user");
		funcionario1.setSenha("kenji15");
		funcionario1.setCelular("(11)99965-4908");
		
//		em.persist(funcionario1);
		
		
		NivelAcesso n = new NivelAcesso();
		n.setDescricao("Totalzao");
		n.setAcessarAcoes(EnumNivelAcesso.Total);
		n.setAcessarCargo(EnumNivelAcesso.Total);
		n.setAcessarDepartamento(EnumNivelAcesso.Total);
		n.setAcessarFuncionario(EnumNivelAcesso.Total);
		n.setAcessarNivelAcesso(EnumNivelAcesso.Total);
		n.setAcessarAparelho(EnumNivelAcesso.Total);
		n.setAcessarConstrutora(EnumNivelAcesso.Total);
		n.setAcessarDistrito(EnumNivelAcesso.Total);
		n.setAcessarEstagioAtendimento(EnumNivelAcesso.Total);
		n.setAcessarEventoMidia(EnumNivelAcesso.Total);
		n.setAcessarInvestimentoMidia(EnumNivelAcesso.Total);
		n.setAcessarMidia(EnumNivelAcesso.Total);
		n.setAcessarNivelAcesso(EnumNivelAcesso.Total);
		n.setAcessarNumero(EnumNivelAcesso.Total);
		n.setAcessarPeriodicidade(EnumNivelAcesso.Total);
		n.setAcessarSetorizacao(EnumNivelAcesso.Total);
		n.setAcessarTipoApartamento(EnumNivelAcesso.Total);
		n.setAcessarTipoVaga(EnumNivelAcesso.Total);
		n.setAcessarTransporte(EnumNivelAcesso.Total);
		n.setAcessarCondominio(EnumNivelAcesso.Total);
		
//		em.persist(n);
		
		Funcionario funcionario2 = new Funcionario();
		funcionario2.setNome("Eduardo Kenji");
		funcionario2.setSobrenome("Kogake");
		funcionario2.setUsuario("kenji");
		funcionario2.setSenha("kenji15");
		funcionario2.setCelular("(11)97229-9380");
		funcionario2.setNivelAcesso(n);
		
//		em.persist(funcionario2);
		
//		Construtora construtora = new Construtora();
//		construtora.setNome("MRV");
//		
//		Construtora construtora2 = new Construtora();
//		construtora2.setNome("Cyrela");
//		
//		em.persist(construtora);
//		em.persist(construtora2);
//		
//		Metro metro = new Metro();
//		metro.setNome("Sa�de");
//		
//		Metro metro1 = new Metro();
//		metro1.setNome("Pra�a da �rvore");
//		
//		em.persist(metro1);
//		
//		em.persist(metro);
//		
//		Trem trem = new Trem();
//		trem.setDescricao("Luz");
//		
//		em.persist(trem);
//		
//		Setorizacao setor = new Setorizacao();
//		setor.setNome("1");
//		
//		em.persist(setor);
//		
//		Regiao regiao = new Regiao();
//		regiao.setDescricao("Zona Sul");
//		
//		em.persist(regiao);
//		
//		Distrito distrito = new Distrito();
//		distrito.setDescricao("Distrito da Sa�de");
//		distrito.setNome("Sa�de");
//		
//		em.persist(distrito);
//		
//		/*Condominio condominio = new Condominio();
//		condominio.setNome("Condominio1");
//		condominio.setDistrito(distrito);
//		condominio.setSetorizacao(setor);
//		condominio.setMetro(metro);
//		condominio.setRegiao(regiao);
//		condominio.setConstrutora(construtora);
//		
//		em.persist(condominio);*/
//
//		
//		em.getTransaction().commit();
		em.close();
	}
}
