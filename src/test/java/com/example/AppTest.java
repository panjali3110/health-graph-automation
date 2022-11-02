package com.example;

import java.time.Duration;
import java.util.concurrent.TimeUnit;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.checkerframework.checker.units.qual.C;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.Select;

/**
 * Automation test for simple App.
 */
public class AppTest 
{
    RemoteWebDriver driver;
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
        
        String chromeDriverPath = System.getProperty("user.dir") + getChromeDriverPath();
        System.setProperty("webdriver.chrome.driver", chromeDriverPath);
        driver = new ChromeDriver();
       // Puts an Implicit wait, Will wait for 10 seconds before throwing exception
       driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
       
       // Launch website
       driver.navigate().to("http://testfire.net/index.jsp");
       driver.manage().window().maximize();
    }

    @Test
    public void validateTransactionTest() throws InterruptedException
    {
        Login login = new Login(driver);

        //perform login with incorrect user
        login.PerformLogin("demo_password", "demo_password");

        // Verify User login failed
        Assert.assertTrue(login.VerifyUserLoggedFailed(), "Assertion for login failed.");

        //perform login with correct user
        login.PerformLogin("admin", "admin");

        // Verify User login succeeded 
        Assert.assertTrue(login.VerifyUserLoggedIn(), "Assertion for login succeeded.");

        MyAccount myAccount = new MyAccount(driver);

        //Open checkin Account
        myAccount.openCheckinAccount();

        //Verify Available balance is showing
        Assert.assertTrue(myAccount.VerifyAvailableBalance(), "Assertion for Available balance is showing.");

        String transferAmount = "9876.0";
        FundTransfer fundTransfer = new FundTransfer(driver);

        //Transfer fund from corporate to checking
        fundTransfer.TransferFundFromCorporateToChecking(transferAmount);

        //Verify fund transferred successfully
        Assert.assertTrue(fundTransfer.VerifyFundTransfer(transferAmount), "Assertion for fund transferred successfully.");

        RecentTransactions recentTransactions = new RecentTransactions(driver);

        // Go to recent Transactions
        recentTransactions.goToRecentTransactions();

        //Fetch Amount from first row of the transactions table
        String firstTransactionAmount = recentTransactions.getFirstTransactionAmount();

        //Verify Amount from first row of the transactions table
        Assert.assertTrue(firstTransactionAmount.contains(transferAmount), "Assertion for deposit amount has failed. Expected: " + transferAmount+" Actual: " + firstTransactionAmount);
        
        //Fetch Amount from second row of the transactions table
        String secondTransactionAmount = recentTransactions.getSecondTransactionAmount();

        //Verify Amount from second row of the transactions table
        Assert.assertTrue(secondTransactionAmount.contains(transferAmount), "Assertion for Withdrawal amount has failed. Expected: " + transferAmount+" Actual: " + secondTransactionAmount);

        ContactUs contactUs = new ContactUs(driver);

        //click on Contact Us link from left menu
        contactUs.goToContactUs();

        //Click on online form link
        contactUs.goToOnlineForm();

        //fill the feedback form and send it
        contactUs.sendFeedback("test@email.com", "Subject Test", "Test Comment");

        //Verify Thank you message
        Assert.assertTrue(contactUs.verifyThankYouMessage(), "Assertion for Thank you message");

        SignOff signOff = new SignOff(driver);

        //Click sign off
        signOff.clickSignOff();

        //Verify user signed off
        Assert.assertTrue(signOff.verifyUserSignedOff(), "Assertion for Sign Off.");

    }

   @AfterTest
   public void terminateTest() {
      driver.quit();
   }
}
