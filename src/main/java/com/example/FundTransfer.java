package com.example;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.Select;

public class FundTransfer {
    RemoteWebDriver driver;
    
    public FundTransfer(RemoteWebDriver driver) {
        this.driver = driver;
    }

    public void TransferFundFromCorporateToChecking(String transferAmount){

        driver.findElement(By.linkText("Transfer Funds")).click();

        Select fromacc = new Select(driver.findElement(By.id("fromAccount")));
		fromacc.selectByVisibleText("800000 Corporate");

        Select toacc = new Select(driver.findElement(By.id("toAccount")));
		toacc.selectByVisibleText("800001 Checking");

        driver.findElement(By.id("transferAmount")).sendKeys(transferAmount);

        driver.findElement(By.id("transfer")).click();
    }

    public Boolean VerifyFundTransfer(String transferAmount){
        WebElement successsMessageElement = driver.findElement(By.xpath("//span[@id='_ctl0__ctl0_Content_Main_postResp']/span"));
        return successsMessageElement.getText().contains(transferAmount+" was successfully transferred");
    }
}
