package br.ce.wcaquino.estrategia2;

import java.util.Calendar;
import java.util.Date;

import org.junit.Assert;
import org.junit.Test;

import br.ce.wcaquino.dao.SaldoDAO;
import br.ce.wcaquino.dao.impl.SaldoDAOImpl;
import br.ce.wcaquino.entidades.Conta;
import br.ce.wcaquino.entidades.TipoTransacao;
import br.ce.wcaquino.entidades.Transacao;
import br.ce.wcaquino.entidades.Usuario;
import br.ce.wcaquino.service.ContaService;
import br.ce.wcaquino.service.TransacaoService;
import br.ce.wcaquino.service.UsuarioService;
import br.ce.wcaquino.utils.DataUtils;

public class CalculoSaldoTest {
	
	// 1 usuario
	// 1 conta
	// 1 transacao
	
	//Regras a serem verificadas:
	//1-deve considerar transacoes do mesmo usuario
	//2-deve considerar transacoes da mesma conta
	//3-deve considerar transacoes pagas
	//4-deve considerar transacoes at� a data corrente
		// ontem / hoje / amanh�
	//5-deve somar receitas
	//6-deve subtrair despesas
	
	@SuppressWarnings("deprecation")
	@Test
	public void deveCalcularSaldoCorreto() throws Exception {
		UsuarioService usuarioService = new UsuarioService();
		ContaService contaService = new ContaService();
		TransacaoService transacaoService = new TransacaoService();
		
		//usu�rios
		Usuario usuario = usuarioService.salvar(new Usuario("Usuario", "email@email.com", "123"));
		Usuario usuarioAlternativo = usuarioService.salvar(new Usuario("Usuario Alternativo", "email2@qualquer.com", "123"));
		
		//contas
		Conta conta1 = contaService.salvar(new Conta("Conta principal", usuario.getId()));
		Conta conta2 = contaService.salvar(new Conta("Conta secundaria", usuario.getId()));
		Conta conta3 = contaService.salvar(new Conta("Conta usuario alternativo", usuarioAlternativo.getId()));

		//transacoes (2/4/8/16)
		
		//Transacao Correta / Saldo = 2
		transacaoService.salvar(new Transacao("Transacao inicial","Envolvido", TipoTransacao.RECEITA, 
				new Date(), 2d, true, conta1, usuario));
		
		//Transacao usuario alternativo / Saldo = 2
		transacaoService.salvar(new Transacao("Transacao outro usuario","Envolvido", TipoTransacao.RECEITA, 
				new Date(), 4d, true, conta3, usuarioAlternativo));
		
		//Transacao de outra conta / Saldo = 2
		transacaoService.salvar(new Transacao("Transacao outra conta","Envolvido", TipoTransacao.RECEITA, 
				new Date(), 8d, true, conta2, usuario ));
		
		//Transacao pendente / Saldo = 2
		transacaoService.salvar(new Transacao("Transacao pendente","Envolvido", TipoTransacao.RECEITA, 
				new Date(), 16d, false, conta1, usuario ));
		
		//Transacao passada / Saldo = 34
		transacaoService.salvar(new Transacao("Transacao passada","Envolvido", TipoTransacao.RECEITA, 
				DataUtils.obterDataComDiferencaDias(-1), 32d, true, conta1, usuario ));
		
		//Transacao futura / Saldo = 34
		transacaoService.salvar(new Transacao("Transacao futuraa","Envolvido", TipoTransacao.RECEITA, 
				DataUtils.obterDataComDiferencaDias(1), 64d, true, conta1, usuario ));
		
		//Transacao despesa / Saldo = 34 - 128 = -94
		transacaoService.salvar(new Transacao("Transacao despesa","Envolvido", TipoTransacao.DESPESA, 
				new Date(), 128d, true, conta1, usuario ));
		
		//Testes de saldo com valor negativo d� azar / Saldo = -94 + 256 = 162
		transacaoService.salvar(new Transacao("Transacao da sorte","Envolvido", TipoTransacao.RECEITA, 
				new Date(), 256d, true, conta1, usuario ));
		
		SaldoDAO saldoDAO = new SaldoDAOImpl();
		Assert.assertEquals(new Double(162d), saldoDAO.getSaldoConta(conta1.getId()));
		Assert.assertEquals(new Double(8d), saldoDAO.getSaldoConta(conta2.getId()));
		Assert.assertEquals(new Double(4d), saldoDAO.getSaldoConta(conta3.getId()));
	}
	
//	public Date obterDataComDiferencaDias(int dias) {
//		Calendar cal = Calendar.getInstance();
//		cal.add(Calendar.DAY_OF_MONTH, dias);
//		return cal.getTime();
//	}
}
