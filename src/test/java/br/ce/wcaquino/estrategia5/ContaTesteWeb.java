package br.ce.wcaquino.estrategia5;

import java.sql.SQLException;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class ContaTesteWeb {
	
	private ChromeDriver driver;
	
	@BeforeClass
	public static void reset() {
		WebDriver driverLogin = new ChromeDriver();
		driverLogin.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		driverLogin.get("https://seubarriga.wcaquino.me/");
		driverLogin.findElement(By.id("email")).sendKeys("aldrovcamargo@gmail.com");
		driverLogin.findElement(By.id("senha")).sendKeys("lobo2018#");
		driverLogin.findElement(By.tagName("button")).click();
		
		driverLogin.findElement(By.linkText("reset")).click();
		driverLogin.quit();
	}

	@Before
	public void login() {
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		driver.get("https://seubarriga.wcaquino.me/");
		driver.findElement(By.id("email")).sendKeys("aldrovcamargo@gmail.com");
		driver.findElement(By.id("senha")).sendKeys("lobo2018#");
		driver.findElement(By.tagName("button")).click();
		
	}
	
	@Test
	public void inserir() throws ClassNotFoundException, SQLException {
		driver.findElement(By.linkText("Contas")).click();
		driver.findElement(By.linkText("Adicionar")).click();
		driver.findElement(By.id("nome")).sendKeys("Conta estrategia #5");
		driver.findElement(By.tagName("button")).click();
		
		String msg = driver.findElement(By.xpath("//div[@class='alert alert-success']")).getText();
		Assert.assertEquals("Conta adicionada com sucesso!", msg);
	}
	
	
	@Test
	public void consultar() throws ClassNotFoundException, SQLException {
		driver.findElement(By.linkText("Contas")).click();
		driver.findElement(By.linkText("Listar")).click();
		driver.findElement(By.xpath("//td[contains(text(),'Conta para saldo')]/..//a")).click();
		
		String nomeConta = driver.findElement(By.id("nome")).getAttribute("value");
		
		Assert.assertEquals("Conta para saldo", nomeConta);
	}
	
	@Test
	public void alterar() throws ClassNotFoundException, SQLException {
		driver.findElement(By.linkText("Contas")).click();
		driver.findElement(By.linkText("Listar")).click();
		driver.findElement(By.xpath("//td[contains(text(),'Conta para alterar')]/..//a")).click();
		driver.findElement(By.id("nome")).sendKeys(" Alterada");
		driver.findElement(By.tagName("button")).click();
		
		String msg = driver.findElement(By.xpath("//div[@class='alert alert-success']")).getText();
		
		Assert.assertEquals("Conta alterada com sucesso!", msg);
	}
		
	@Test
	public void excluir() throws ClassNotFoundException, SQLException {
		driver.findElement(By.linkText("Contas")).click();
		driver.findElement(By.linkText("Listar")).click();
		driver.findElement(By.xpath("//td[contains(text(),'Conta mesmo nome')]/..//a[2]")).click();
		
		String msg = driver.findElement(By.xpath("//div[@class='alert alert-success']")).getText();
		
		Assert.assertEquals("Conta removida com sucesso!", msg);
	}
	
	@After
	public void fechar() {
		driver.quit();
	}
}
