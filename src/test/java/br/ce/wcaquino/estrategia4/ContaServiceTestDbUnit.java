package br.ce.wcaquino.estrategia4;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;

import org.dbunit.Assertion;
import org.dbunit.assertion.DiffCollectingFailureHandler;
import org.dbunit.assertion.Difference;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.junit.Assert;
import org.junit.Test;

import br.ce.wcaquino.dao.utils.ConnectionFactory;
import br.ce.wcaquino.dbunit.ImportExport;
import br.ce.wcaquino.entidades.Conta;
import br.ce.wcaquino.entidades.Usuario;
import br.ce.wcaquino.service.ContaService;
import br.ce.wcaquino.service.UsuarioService;

public class ContaServiceTestDbUnit {
	
	ContaService service = new ContaService();
	UsuarioService userService = new UsuarioService();
	
	@Test
	public void testInserir() throws Exception {
		ImportExport.importarBanco("est4_inserirConta.xml");
		Usuario usuario = userService.findById(1L);
		Conta conta = new Conta("Conta salva", usuario);
		Conta contaSalva = service.salvar(conta);
		Assert.assertNotNull(contaSalva.getId());
		userService.printAll();
		service.printAll();
	}
	
	@Test
	public void testInserir_Assertion() throws Exception {
		ImportExport.importarBanco("est4_inserirConta.xml");
		Usuario usuario = userService.findById(1L);
		Conta conta = new Conta("Conta salva", usuario);
		Conta contaSalva = service.salvar(conta);
		//contaSalva.setId(1L); // Forçando erro de Id
		
		//estado atual do banco
		DatabaseConnection dbConn = new DatabaseConnection(ConnectionFactory.getConnection());
		IDataSet estadoFinalbanco = dbConn.createDataSet();
		
		//estado esperado (XML)
		FlatXmlDataSetBuilder builder = new FlatXmlDataSetBuilder();
		FlatXmlDataSet dataSetEsperado = builder.build(new FileInputStream("massas" + File.separator + "est4_inserirConta_saida.xml"));
		
		//comparar os dois estados
//		Assertion.assertEquals(dataSetEsperado, estadoFinalbanco); // sem diff handler
		
		DiffCollectingFailureHandler handler = new DiffCollectingFailureHandler();
		Assertion.assertEquals(dataSetEsperado, estadoFinalbanco, handler);
		List<Difference> diffList = handler.getDiffList();
		boolean erroReal = false;
		for(Difference diff: diffList) {
			System.out.println(diff.toString());
			if(diff.getActualTable().getTableMetaData().getTableName().equals("contas")) {
				if(diff.getColumnName().equals("id")) {
					if(diff.getActualValue().toString().equals(contaSalva.getId().toString())) {
						continue;
					} else {
						System.out.println("Id errado mesmo!");
						erroReal = true;
					}
				} else {
					erroReal = true;
				}
			} else {
				erroReal = true;
			}
		}
		Assert.assertFalse(erroReal);
		
		userService.printAll();
		service.printAll();
	}
	
	@Test
	public void testAlterar() throws Exception {
		ImportExport.importarBanco("est4_umaConta.xml");
		Conta contaTeste = service.findByName("Conta para testes");
		contaTeste.setNome("Conta alterada");
		service.printAll();
		Conta contaAlterada = service.salvar(contaTeste);
		Assert.assertEquals("Conta alterada", contaAlterada.getNome());
		service.printAll();
	}
	
	@Test
	public void testConsultar() throws Exception {
		ImportExport.importarBanco("est4_umaConta.xml");
		Conta contaBuscada = service.findById(1L);
		Assert.assertEquals("Conta para testes",contaBuscada.getNome());
		service.printAll();
	}
	
	@Test
	public void testExcluir() throws Exception {
		ImportExport.importarBanco("est4_umaConta.xml");
		Conta contaTeste = service.findByName("Conta para testes");
		service.printAll();
		service.delete(contaTeste);
		Conta contaBuscada = service.findById(contaTeste.getId());
		Assert.assertNull(contaBuscada);
		service.printAll();
	}
	
}
