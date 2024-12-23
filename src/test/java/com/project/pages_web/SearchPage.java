package com.project.pages_web;

import com.project.utilities.BrowserUtils;
import com.project.utilities.Driver;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.text.NumberFormat;

import java.text.ParseException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class SearchPage extends BasePage {

    @FindBy(css = "h1")
    public WebElement searchResultKeyword;

    @FindBy(xpath = "//div[starts-with(@id, \"ProductList\")]//div[@data-test-id='price-current-price']")
    public List<WebElement> productPriceList;

    @FindBy(xpath = "//div[@data-test-id='filterbox-item-display-name']")
    public List<WebElement> selectedFilterLocator;



    WebDriver driver = Driver.get();

    public SearchPage() {
        PageFactory.initElements(driver, this);
    }

    public void selectFilter(String filterTitle, String filterName){
        filterTitle= filterTitle.toLowerCase();
        filterTitle= filterTitle.trim().replaceAll(" ", "");
         //   filterName = filterName.toLowerCase();
            //driver.findElement(By.xpath("//input[@type='checkbox' and @value='"+filterName+"']")).click();
        WebElement element = driver.findElement(By.xpath("//*[@name='" + filterTitle + "']/../div/a[text()='" + filterName + "']"));
            BrowserUtils.clickWithJS(element);
            BrowserUtils.waitFor(1);

            driver.navigate().refresh();


    }

    public void highestPrice(){

        List<Double> prices = new ArrayList<>();
        for (WebElement element : productPriceList) {
            String priceText = element.getText();
            // Fiyatı string olarak al ve double'a çevir
         //   String priceText = element.getText().replace("$", "").replace(",", "").trim();
         //   prices.add(Double.parseDouble(priceText));

            try {
                // Türkçe yerel ayarına göre fiyatı parse et
                NumberFormat format = NumberFormat.getInstance(new Locale("tr", "TR"));
                Number number = format.parse(priceText);
                prices.add(number.doubleValue()); // Double olarak ekle
            } catch (ParseException e) {
                e.printStackTrace();
            }

        }


        // Fiyatları sıralama
        Collections.sort(prices);
        for (int i = 0; i < prices.size(); i++) {
            System.out.println("prices.Sort get("+i+") = " + prices.get(i));
        }

        selectProduct(prices.size()-1);
        BrowserUtils.switchWindowTab();//new tab
    }

    private void selectProduct(int index){
        productPriceList.get(index).click();
    }



}
