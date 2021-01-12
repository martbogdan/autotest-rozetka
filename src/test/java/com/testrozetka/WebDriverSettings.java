package com.testrozetka;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

public class WebDriverSettings {
    protected WebDriver driver;
    protected WebDriverWait wait;

    private static final String CHROME_DRIVER = "src/main/resources/drivers/chromedriver87.0.4280.88";
    private static final long WAIT_TIME_OUT_IN_SECONDS = 10;

    @BeforeTest
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", CHROME_DRIVER);
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, WAIT_TIME_OUT_IN_SECONDS);
    }

    @AfterTest
    public void close() {
        driver.quit();
    }

}
