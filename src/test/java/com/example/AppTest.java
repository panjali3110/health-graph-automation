package com.example;

import java.time.Duration;
import java.util.concurrent.TimeUnit;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.Select;

/**
 * Automation test for simple App.
 */
public class AppTest 
{
    WebDriver driver;
    /**
     * Rigorous Test :-)
     */
    private String getChromeDriverPath() {
        String osName =  System.getProperty("os.name").toLowerCase();
        System.out.println("Print value for OS **********  "+osName);
        if (osName.contains("win")) {
            System.out.println("INSIDE ***********");
            return "/drivers/windows/chromedriver.exe";
        }
        if (osName.contains("mac")){
            return "/drivers/mac/chromedriver";
        }
        return "/drivers/linux/chromedriver";
    }

    @BeforeTest
    public void launchApp() throws InterruptedException {
        
        String chromeDriverPath = System.getProperty("user.dir").replace('\\', '/') + getChromeDriverPath();
        System.setProperty("webdriver.chrome.driver", chromeDriverPath);
        driver = new ChromeDriver();
       // Puts an Implicit wait, Will wait for 10 seconds before throwing exception
       driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
       
       // Launch website
       driver.navigate().to("http://testfire.net/index.jsp");
       driver.manage().window().maximize();
    }

    @Test
    public void validateTransactionTest()
    {
        driver.findElement(By.xpath("//*[text()='ONLINE BANKING LOGIN']")).click();

        WebElement incorrectusername = driver.findElement(By.id("uid"));
        incorrectusername.sendKeys("demo_user");

        WebElement incorrectpassword = driver.findElement(By.id("passw"));
        incorrectpassword.sendKeys("demo_password");

        WebElement loginbtn = driver.findElement(By.name("btnSubmit"));
        loginbtn.click();

        WebElement correctusername = driver.findElement(By.id("uid"));
        correctusername.sendKeys("admin");

        WebElement correctpassword = driver.findElement(By.id("passw"));
        correctpassword.sendKeys("admin");
        
        loginbtn = driver.findElement(By.name("btnSubmit"));
        loginbtn.click();

        Assert.assertTrue(driver.findElement(By.xpath("//*[contains(text(),'Hello Admin User')]")).isDisplayed());

        WebElement viewaccsum = driver.findElement(By.linkText("View Account Summary"));
        viewaccsum.click();

        Select accdetail = new Select(driver.findElement(By.id("listAccounts")));
		accdetail.selectByVisibleText("800001 Checking");
		
        driver.findElement(By.id("btnGetAccount")).click();
        
        WebElement availableBalance = driver.findElement(By.xpath("//td[contains(text(), 'Available balance')]/following-sibling::td"));

        System.out.println(availableBalance.getText());
        String expectedData = "$100000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000.00";

        Assert.assertTrue(availableBalance.getText().contains(expectedData));
        
        driver.findElement(By.linkText("Transfer Funds")).click();

        Select fromacc = new Select(driver.findElement(By.id("fromAccount")));
		fromacc.selectByVisibleText("800000 Corporate");

        Select toacc = new Select(driver.findElement(By.id("toAccount")));
		toacc.selectByVisibleText("800001 Checking");

        String transferAmount = "9876.0";

        driver.findElement(By.id("transferAmount")).sendKeys(transferAmount);

        driver.findElement(By.id("transfer")).click();

        WebElement successsMessageElement = driver.findElement(By.xpath("//span[@id='_ctl0__ctl0_Content_Main_postResp']/span"));

        Assert.assertTrue(successsMessageElement.getText().contains(transferAmount+" was successfully transferred from Account 800000 into Account 800001"));

        driver.findElement(By.linkText("View Recent Transactions")).click();

        WebElement firstRowAmountCellElement = driver.findElement(By.xpath("//table[@id='_ctl0__ctl0_Content_Main_MyTransactions']/tbody/tr[2]/td[5]"));

        Assert.assertTrue(firstRowAmountCellElement.getText().contains(transferAmount), "Assertion for deposit amount has failed. Expected: "+transferAmount+" Actual: "+firstRowAmountCellElement.getText());

        WebElement secondRowAmountCellElement = driver.findElement(By.xpath("//table[@id='_ctl0__ctl0_Content_Main_MyTransactions']/tbody/tr[3]/td[5]"));

        Assert.assertTrue(secondRowAmountCellElement.getText().contains(transferAmount));

        driver.findElement(By.linkText("Contact Us")).click();

        driver.findElement(By.linkText("online form")).click();

        driver.findElement(By.name("email_addr")).sendKeys("test@email.com");

        driver.findElement(By.name("subject")).sendKeys("Subject Test");

        driver.findElement(By.name("comments")).sendKeys("Test Comment");

        driver.findElement(By.name("submit")).click();

        Assert.assertTrue(driver.findElement(By.xpath("//*[contains(text(),'Thank you for your comments, Admin User.')]")).isDisplayed(), "Assertion for Thank you message is not displayed.");

        driver.findElement(By.xpath("//*[text()='Sign Off']")).click();

        Assert.assertTrue(driver.findElement(By.xpath("//*[text()='Sign In']")).isDisplayed(), "Assertion for Sign Off.");

    }

   @AfterTest
   public void terminateTest() {
      driver.quit();
   }
}
