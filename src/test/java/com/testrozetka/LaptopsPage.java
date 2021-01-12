package com.testrozetka;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.stream.Collectors;

public class LaptopsPage {
    private static final String URL = "https://rozetka.com.ua/ua/notebooks/c80004/";
    private static final long WAIT_TIME_OUT_IN_SECONDS = 20;

    private final WebDriver driver;
    private final WebDriverWait wait;

    public LaptopsPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, WAIT_TIME_OUT_IN_SECONDS);
    }

    private final By itemLocator = By.cssSelector("[class=\"goods-tile\"]");
    private final By showMoreLocator = By.cssSelector("[class=\"show-more show-more--horizontal\"]");
    private final By footerLocator = By.cssSelector("footer");
    private final By itemPriceActualLocator = By.cssSelector("[class=\"goods-tile__price-value\"]");

    @FindBy(xpath = "/html/body/app-root/div/div[1]/rz-category/div/main/rz-catalog/div/div/aside/rz-filter-stack/div[4]/div/rz-scrollbar/div/div[1]/div/div/rz-filter-slider/form/fieldset/div/input[1]")
    private WebElement lowPriceInput;

    @FindBy(xpath = "/html/body/app-root/div/div[1]/rz-category/div/main/rz-catalog/div/div/aside/rz-filter-stack/div[4]/div/rz-scrollbar/div/div[1]/div/div/rz-filter-slider/form/fieldset/div/input[2]")
    private WebElement highPriceInput;

    @FindBy(xpath = "/html/body/app-root/div/div[1]/rz-category/div/main/rz-catalog/div/div/aside/rz-filter-stack/div[4]/div/rz-scrollbar/div/div[1]/div/div/rz-filter-slider/form/fieldset/div/button")
    private WebElement submitPriceFilter;

    public LaptopsPage getLaptopsPage() {
        driver.get(URL);
        checkLoadingPage();
        return this;
    }

    public LaptopsPage fillLowPrice(int lowPrice) {
        lowPriceInput.clear();
        lowPriceInput.sendKeys(String.valueOf(lowPrice));
        return this;
    }

    public LaptopsPage fillHighPrice(int highPrice) {
        highPriceInput.clear();
        highPriceInput.sendKeys(String.valueOf(highPrice));
        return this;
    }

    public LaptopsPage submitPriceForm() {
        submitPriceFilter.submit();
        checkLoadingPage();
        return this;
    }

    public void checkLoadingPage() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(footerLocator));
    }

    private boolean isShowMoreLocatorPresent(By locator) {
        return !driver.findElements(locator).isEmpty();
    }

    public void openAllItemsOnOnePage() {
        while (isShowMoreLocatorPresent(showMoreLocator)) {
            driver.findElement(showMoreLocator).click();
            checkLoadingPage();
        }
    }

    public List<WebElement> getAllItems() {
        return driver.findElements(itemLocator);
    }

    public List<Integer> getPricesFromItem(List<WebElement> elements) {
        return elements.stream()
                .map(element -> element.findElement(itemPriceActualLocator).getText().replaceAll(" ", ""))
                .map(Integer::parseInt)
                .collect(Collectors.toList());
    }

    public boolean checkPriceBoundary(int lowPrice, int highPrice, List<Integer> itemPrices) {
        return itemPrices.stream().anyMatch(price -> price <= lowPrice || price >= highPrice);
    }
}
