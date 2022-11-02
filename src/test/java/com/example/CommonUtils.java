package com.example;

import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;


public class CommonUtils {

    private final Logger logger = Logger.getLogger(CommonUtils.class);
    public WebDriver driver;

    private String getChromeDriverPath() {
        String osName =  System.getProperty("os.name").toLowerCase();
        if (osName.contains("win")) {
            return "/drivers/windows/chromedriver.exe";
        }
        if (osName.contains("mac")){
            return "/drivers/mac/chromedriver";
        }
        return "/drivers/linux/chromedriver";
    }

    private void setExecutableMode(String path) {
        final File file = new File(path);
        file.setReadable(true, false);
        file.setExecutable(true, false);
        file.setWritable(true, false);
    }

    public WebDriver openChromeBrowser (String baseURL) {
        WebDriver driver = null;
        try{
            String chromeDriverPath = System.getProperty("user.dir") + getChromeDriverPath();
            setExecutableMode(chromeDriverPath);
            System.out.println("---- Opening chrome browser");
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--incognito");
            options.setAcceptInsecureCerts(true);
            options.setPageLoadStrategy(PageLoadStrategy.EAGER);
            
            System.setProperty("webdriver.chrome.driver", chromeDriverPath);

            driver = new ChromeDriver(options);
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
            driver.manage().window().maximize();
            driver.manage().deleteAllCookies();
            driver.get(baseURL);
        }
        catch (Exception e){
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
        return driver;
    }
}
