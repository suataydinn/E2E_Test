package com.project.pages_web;


import com.project.step_definitions.Hooks;
import com.project.utilities.BrowserUtils;
import com.project.utilities.Driver;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.*;

public class SearchResultPage extends MainPage {

    WebDriver driver = Driver.get();

    public static String[] searchResultPriceText = new String[3];

    @FindBy(xpath = "//*[@cms-key='button_rent_now']/..")
    public List<WebElement> rentNowButtonLocator;

    @FindBy(xpath = "//span[@cms-key='sorting']")
    public WebElement sort;

    @FindBy(xpath = "(//span[@cms-key='sorting'])[2]")
    public WebElement mobileSort;

    @FindBy(xpath = "//*[@class='text-dark-gray font-bold']")
    public List<WebElement> selectedFilterLocator2;

    @FindBy(xpath = "//div[@id='car_total_price' and contains(@class, 'whitespace-nowrap')]\n")
    public List<WebElement> allMobileCarsTotalPrice;

    @FindBy(xpath = "//div[@id='car_total_price' and not(contains(@class, 'whitespace-nowrap'))]\n")
    public List<WebElement> allWebCarsTotalPrice;

    @FindBy(xpath = "(//div[@cms-key='important_informations'])[1]")
    public WebElement rentalConditionLoc;

    @FindBy(xpath = "//div[@cms-key='filter_vendor']")
    public WebElement filterVendor;

    @FindBy(xpath = "//*[@cms-key='filter_provision']")
    public List<WebElement> provisionLoc;


    public void clickFindButton() {
        BrowserUtils.waitHalfSecond();
        findButton.click();
        BrowserUtils.waitForClickability(firstCarForWait,15);
    }


    public SearchResultPage() {
        PageFactory.initElements(driver, this);
    }

    public void selectSpecificCar(int rowNumber) {
        BrowserUtils.waitForClickability(firstCarForWait, 7);
        BrowserUtils.clickWithJS(rentNowButtonLocator.get(rowNumber - 1));
        BrowserUtils.waitForVisibility(rentalConditionLoc, 5);
    }


    public static String extractNumbersAndDots(String input) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (Character.isDigit(c) || c == '.') {

                result.append(c);
            }
        }
        return result.toString();
    }

    public static String extractNumbers(String input) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (Character.isDigit(c)) {
                result.append(c);
            }
        }
        return result.toString();
    }

    public void selectVendor(String vendor) {
        BrowserUtils.waitForClickability(firstCarForWait, 4);
        filterVendor.click();
        driver.findElement(By.xpath("//div[contains(text(),'" + vendor + "')]")).click();
        Assert.assertTrue(driver.findElement(By.xpath("//div[contains(text(),'" + vendor + "')]/../../input")).isSelected());
        boolean isVendorSelected = false;
        for (WebElement element : selectedFilterLocator2) {
            if (element.getText().equals(vendor)) {
                isVendorSelected = true;
                break;
            }
        }
        Assert.assertTrue(isVendorSelected);
        Assert.assertTrue( "The vendor '" + vendor + "' is not selected" , isVendorSelected);

    }

    public double sortSpecial(String str) {
        return 0;
    }

    public double checkSortlistPrice(String str) {
        BrowserUtils.waitFor(1);
        for (int i = 0; i < 9; i++) {
            BrowserUtils.scrollToFooter();
        }

        ArrayList<String> UI_List;


        String scenarioTag = Driver.getCurrentScenarioTag();
            if (scenarioTag.equals("@browserMobile")) {
                UI_List = BrowserUtils.createArrayList(allMobileCarsTotalPrice);
            } else {
                UI_List = BrowserUtils.createArrayList(allWebCarsTotalPrice);
            }



        // UI_List i double olarak yeniden tanımlıyoruz
        ArrayList<Double> doubleUIList = new ArrayList<>();

        for (String value : UI_List) {

            String valueWithoutCurrencySymbol = extractNumbersAndDots(value);
            if (valueWithoutCurrencySymbol.contains(".")) {
                double doubleValue = Double.parseDouble(valueWithoutCurrencySymbol.replace(".", ""));
                doubleUIList.add(doubleValue);
            } else {
                double doubleValue = Double.parseDouble(valueWithoutCurrencySymbol);
                doubleUIList.add(doubleValue);
            }

        }
        System.out.println(doubleUIList);

        switch (str) {
            case "En düşük fiyat":
                System.out.println("En Düşük Fiyat için assertion yapılıyor.");

                for (int i = 0; i < doubleUIList.size() - 1; i++) {
                    Assert.assertTrue(doubleUIList.get(i) <= doubleUIList.get(i + 1));
                }

                System.out.println("Sıralama doğru!");

                break;
            case "En yüksek fiyat":
                System.out.println("En Yüksek Fiyat için assertion yapılıyor.");

                for (int i = 0; i < doubleUIList.size() - 1; i++) {
                    Assert.assertTrue(doubleUIList.get(i) >= doubleUIList.get(i + 1));
                }

                System.out.println("Sıralama doğru!");
                break;

        }
        return doubleUIList.get(0);
    }


    public void savePrice() {
    }
}