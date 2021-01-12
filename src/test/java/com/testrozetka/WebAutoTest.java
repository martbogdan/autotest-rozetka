package com.testrozetka;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class WebAutoTest extends WebDriverSettings {

    @Test
    public void getHomePage() {
        RozetkaHomePage homePage = new RozetkaHomePage(driver);
        homePage.open();
    }

    @Test
    public void getLaptops() {
        RozetkaHomePage homePage = PageFactory.initElements(driver, RozetkaHomePage.class);
        homePage
                .open()
                .getLaptopsAndComputersCategory()
                .getAllLaptops();
    }

    @Test
    public void priceFilterTest() {
        LaptopsPage laptopsPage = PageFactory.initElements(driver, LaptopsPage.class);
        laptopsPage
                .getLaptopsPage()
                .fillLowPrice(3500)
                .fillHighPrice(80000)
                .submitPriceForm()
                .openAllItemsOnOnePage();

        List<WebElement> elements = laptopsPage.getAllItems();
        System.out.println("Number of elements: " + elements.size());
        List<Integer> integers = laptopsPage.getPricesFromItem(elements);
        System.out.println("Number of prices: " + integers.size());
        System.out.println("Max price: " + integers.stream().mapToInt(Integer::intValue).max());
        System.out.println("Min price: " + integers.stream().mapToInt(Integer::intValue).min());

        Assert.assertTrue(laptopsPage.checkPriceBoundary(3500, 80000, integers));
    }
}
