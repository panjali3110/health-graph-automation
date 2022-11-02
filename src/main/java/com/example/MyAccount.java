package com.example;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.Select;

public class MyAccount {
    RemoteWebDriver driver;
    String url = "http://testfire.net/index.jsp";

    public MyAccount(RemoteWebDriver driver) {
        this.driver = driver;
    }

    public void navigateToHome() {
        if (!this.driver.getCurrentUrl().equals(this.url)) {
            this.driver.get(this.url);
        }
    }

    public void openCheckinAccount() {
        WebElement viewaccsum = driver.findElement(By.linkText("View Account Summary"));
        viewaccsum.click();

        Select accdetail = new Select(driver.findElement(By.id("listAccounts")));
		accdetail.selectByVisibleText("800001 Checking");
		
        driver.findElement(By.id("btnGetAccount")).click();
    }

    public Boolean VerifyAvailableBalance() {
        try{
            WebElement availableBalance = driver.findElement(By.xpath("//td[contains(text(), 'Available balance')]/following-sibling::td"));
            return availableBalance.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}
