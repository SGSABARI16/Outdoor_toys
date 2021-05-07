package com.datadriven.framework.base;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;


import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;

/**
 *Author:SabariGirsan 
 *Date:27/01/2021
 *Description:This function is used to find the product 
 *            of "outdoor toys",and verify the "portable" 
 *            product.
 * */

public class BaseUI {

	public WebDriver driver;
	public Properties property;

	public void openBrowser(String browserName) 
	{
		if (browserName.equalsIgnoreCase("chrome")) {
			String chrome=System.getProperty("user.dir");
			System.setProperty("webdriver.chrome.driver",chrome+
					"/src/test/resources/drivers/chromedriver.exe");
			driver = new ChromeDriver();

		} else if (browserName.equalsIgnoreCase("firefox")) {
			String firefox=System.getProperty("user.dir");
			System.setProperty("webdriver.gecko.driver",firefox+
					"/src/test/resources/drivers/geckodriver.exe");
			driver = new FirefoxDriver();
		}


		driver.manage().timeouts().implicitlyWait(100, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		driver.manage().timeouts().pageLoadTimeout(180, TimeUnit.SECONDS);


		if (property == null) { 
			property = new Properties(); 
			try { 
				String path =System.getProperty("user.dir");
				FileInputStream	file = new FileInputStream(path +
						"/src/test/resources/objectRepository/config.properties"); 
				property.load(file); 
			}
			catch (Exception e) {

				e.printStackTrace(); 
			}
		}

	}


	public void openURL(String websiteURL) {
		driver.get(property.getProperty(websiteURL)); 
	}

	public void enterText(String id,String data) {
		driver.findElement(By.id(property.getProperty(id))).sendKeys(data);
	}

	public void elementClick(String id) {
		driver.findElement(By.id(property.getProperty(id))).click();			
	}

	public void verifyTitle() {
		String pageTitle = driver.getTitle();
		System.out.println("Page Title is :" +pageTitle);
		Assert.assertEquals(pageTitle, "outdoor toys | eBay");
	}


	public void getLinks() throws InterruptedException {
		List<WebElement> getLinks=driver.findElements(By.xpath("//a"));

		for(WebElement link:getLinks) {
			//Iterator the href link in <a>tag
			System.out.println(link.getAttribute("href"));          
		}

		int totallinks=getLinks.size();
		
		//Print the total link in Result Page
		System.out.println("Total Outdoor toys links: "+totallinks); 
	}

	public void portable(String data,String id) throws InterruptedException {
		WebElement port= driver.findElement(By.partialLinkText(property.getProperty(data))); 
		String link=  port.getAttribute("href");
		driver.get(link);
		
		//Print the first value of Outdoor Product.
		System.out.println("Portable Product links : " +link);          
		String title=driver.findElement(By.id(property.getProperty(id))).getText();
		
		//First item of the Product.
		System.out.println("Product title: "+title);                   
	}

	public void screenshot() throws Exception{
		TakesScreenshot srcShot=((TakesScreenshot) driver);
		try {
			File Source=srcShot.getScreenshotAs(OutputType.FILE);
			File copyTo=new File(System.getProperty("user.dir")+"\\screenShot\\Output.jpg");
			FileUtils.copyFile(Source, copyTo);
		}
		catch(IOException e) {
			System.out.println(e.getMessage());
		}
	}

	public void closeBrowser() {
		driver.quit();
	}


}
