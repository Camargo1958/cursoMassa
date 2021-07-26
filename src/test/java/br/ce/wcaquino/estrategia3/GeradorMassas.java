package br.ce.wcaquino.estrategia3;

import java.sql.SQLException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;

import com.github.javafaker.Faker;

public class GeradorMassas {
	
	public static final String CHAVE_CONTA_SB = "CONTA_SB";
	
	public void geradorContaSeuBarriga() throws ClassNotFoundException, SQLException {
		ChromeDriver driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		driver.get("https://seubarriga.wcaquino.me/");
		driver.findElement(By.id("email")).sendKeys("aldrovcamargo@gmail.com");
		driver.findElement(By.id("senha")).sendKeys("lobo2018#");
		driver.findElement(By.tagName("button")).click();
		
		Faker faker = new Faker();
		String registro = faker.gameOfThrones().character() + " " + faker.gameOfThrones().dragon();
		driver.findElement(By.linkText("Contas")).click();
		driver.findElement(By.linkText("Adicionar")).click();
		driver.findElement(By.id("nome")).sendKeys(registro);
		driver.findElement(By.tagName("button")).click();
		driver.quit();
		
		new MassaDAOImpl().inserirMassa(CHAVE_CONTA_SB, registro);
		
	}
	
	public static void main(String[] args) throws ClassNotFoundException, SQLException {
//		GeradorMassas gerador = new GeradorMassas();
//		for(int i =0; i <5; i++) {
//			gerador.geradorContaSeuBarriga();
//		}
		String massa = new MassaDAOImpl().obterMassa(CHAVE_CONTA_SB);
		System.out.println(massa);
	}
}
