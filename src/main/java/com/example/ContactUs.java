package com.example;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.RemoteWebDriver;

public class ContactUs {
    RemoteWebDriver driver;
    
    public ContactUs(RemoteWebDriver driver) {
        this.driver = driver;
    }

    public void goToContactUs(){
        driver.findElement(By.linkText("Contact Us")).click();
        //Assert.assertTrue(driver.findElement(By.xpath("//*[contains(text(),'Thank you for your comments, Admin User.')]")).isDisplayed(), "Assertion for Thank you message is not displayed.");
    }

    public void goToOnlineForm(){
        driver.findElement(By.linkText("online form")).click();
    }

    public void sendFeedback(String emailAddress, String subject, String comment){

        driver.findElement(By.name("email_addr")).sendKeys(emailAddress);

        driver.findElement(By.name("subject")).sendKeys(subject);

        driver.findElement(By.name("comments")).sendKeys(comment);

        driver.findElement(By.name("submit")).click();
    }

    public Boolean verifyThankYouMessage(){
        try{
            return driver.findElement(By.xpath("//*[contains(text(),'Thank you for your comments, Admin User.')]")).isDisplayed();
        }
        catch(Exception e){
            return false;
        }
    }
}