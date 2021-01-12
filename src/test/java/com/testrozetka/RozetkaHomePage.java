package com.testrozetka;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class RozetkaHomePage {
    private static final String WEB_HOST = "https://rozetka.com.ua/ua/";
    private static final String X_PATH_LOADED_LOCATOR = "/html/body/app-root/div/div[1]/app-rz-main-page/div/main";
    private static final long WAIT_TIME_OUT_IN_SECONDS = 10;

    private final WebDriver driver;
    private final WebDriverWait wait;

    public RozetkaHomePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, WAIT_TIME_OUT_IN_SECONDS);
    }

    private final By categoryLinkLocator = By.xpath("/html/body/app-root/div/div[1]/app-rz-main-page/div/aside/main-page-sidebar/sidebar-fat-menu/div/ul/li[1]");
    private final By laptopsLocator = By.xpath("/html/body/app-root/div/div[1]/rz-super-portal/div/main/section/div[2]/rz-dynamic-widgets/rz-widget-list[1]/section/ul/li[1]/rz-list-tile/div");
    private final By laptopsLinkLocator = By.xpath("/html/body/app-root/div/div[1]/app-rz-header/header/div/div[2]/div[1]/fat-menu/div/ul/li[1]/div/div[2]/div[1]/div[1]/ul[1]/li/a");

    @FindBy(xpath = "/html/body/app-root/div/div[1]/app-rz-main-page/div")
    private WebElement elementToHideCategoryList;

    public RozetkaHomePage open() {
        driver.get(WEB_HOST);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(X_PATH_LOADED_LOCATOR)));
        return this;
    }

    public RozetkaHomePage getLaptopsAndComputersCategory() {
        WebElement element = driver.findElement(categoryLinkLocator);
        element.click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(laptopsLocator));
        return this;
    }

    public LaptopsPage getAllLaptops() {
        driver.findElement(laptopsLinkLocator).click();
        return PageFactory.initElements(driver, LaptopsPage.class);
    }


}
