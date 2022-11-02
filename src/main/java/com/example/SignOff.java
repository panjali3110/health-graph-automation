package com.example;

import org.openqa.selenium.By;
import org.openqa.selenium.remote.RemoteWebDriver;

public class SignOff {
    RemoteWebDriver driver;
    
    public SignOff(RemoteWebDriver driver) {
        this.driver = driver;
    }

    public void clickSignOff(){
        driver.findElement(By.xpath("//*[text()='Sign Off']")).click();
    }

    public Boolean verifyUserSignedOff(){
        try{
            return driver.findElement(By.xpath("//*[text()='Sign In']")).isDisplayed();
        }
        catch(Exception e){
            return false;
        }
    }
}
