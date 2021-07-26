package br.ce.wcaquino.teste;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;

public class DriverTest {

	public static void main(String[] args) {
		System.out.println("Main...");
		ChromeDriver driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.get("https://seubarriga.wcaquino.me/");
		driver.findElement(By.id("email")).sendKeys("aldrovcamargo@gmail.com");
		driver.findElement(By.id("senha")).sendKeys("lobo2018#");
		driver.findElement(By.tagName("button")).click();
		
		driver.quit();
	}

}
