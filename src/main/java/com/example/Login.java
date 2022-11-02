package com.example;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;

public class Login {
    RemoteWebDriver driver;
    
    public Login(RemoteWebDriver driver) {
        this.driver = driver;
    }

    public void PerformLogin(String Username, String Password) throws InterruptedException {

        driver.findElement(By.xpath("//*[text()='ONLINE BANKING LOGIN']")).click();

        WebElement username = driver.findElement(By.id("uid"));
        username.sendKeys(Username);
        WebElement password = driver.findElement(By.id("passw"));
        password.sendKeys(Password);
        WebElement loginbtn = driver.findElement(By.name("btnSubmit"));
        loginbtn.click();
    }

    public Boolean VerifyUserLoggedIn() {
        try {
            // Find the username label (present on the top of the page)
            return driver.findElement(By.xpath("//*[contains(text(),'Hello Admin User')]")).isDisplayed();
        } catch (Exception e) {
            return false;
        }

    }

    public Boolean VerifyUserLoggedFailed() {
        try {
            // Find the username login failed label 
            WebElement loginFailedMessage = driver.findElement(By.id("_ctl0__ctl0_Content_Main_message"));
            return loginFailedMessage.getText().contains("Login Failed");
        } catch (Exception e) {
            return false;
        }
    }
}
