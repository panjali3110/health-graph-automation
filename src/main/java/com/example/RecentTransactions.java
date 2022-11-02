package com.example;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;

public class RecentTransactions {
    RemoteWebDriver driver;
    
    public RecentTransactions(RemoteWebDriver driver) {
        this.driver = driver;
    }

    public void goToRecentTransactions(){
        driver.findElement(By.linkText("View Recent Transactions")).click();
    }

    public String getFirstTransactionAmount(){

        WebElement firstRowAmountCellElement = driver.findElement(By.xpath("//table[@id='_ctl0__ctl0_Content_Main_MyTransactions']/tbody/tr[2]/td[5]"));

        return firstRowAmountCellElement.getText();

    }

    public String getSecondTransactionAmount(){

        WebElement firstRowAmountCellElement = driver.findElement(By.xpath("//table[@id='_ctl0__ctl0_Content_Main_MyTransactions']/tbody/tr[2]/td[5]"));

        return firstRowAmountCellElement.getText();

    }
}