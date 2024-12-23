package com.project.pages_web;

import com.project.utilities.BrowserUtils;
import com.project.utilities.Driver;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.List;
import java.util.Locale;

public class ProductPage {

    @FindBy(css = "div[data-test-id='price']")
    public WebElement productDetailPrice;

    @FindBy(css = "div[data-test-id='buyBox-seller']")
    public  WebElement productDetailSeller;

    @FindBy(css = "button[data-test-id='addToCart']")
    public  WebElement addCartButton;

    @FindBy(css = "div.product_price_uXU6Q")
    public List <WebElement> cartPriceList;

    @FindBy(css = "#onboarding_item_list")
    public  WebElement onboardingItemList;

    @FindBy(xpath = "//*[text()='Sepete git']")
    public  WebElement cartButton;

    @FindBy(css = "div.product_cart_1KKhg")
    public List <WebElement> cartProductCount;

    WebDriver driver = Driver.get();
    public ProductPage() {
        PageFactory.initElements(driver, this);
    }

    double detailPrice ;

    private double getProductPrice(){
        BrowserUtils.waitForVisibility(productDetailPrice,2);
        String priceText = productDetailPrice.getText().split("\n")[0];
        System.out.println("productDetailPrice.getText() = " + priceText);
        double doubleCurrency = 0.0;
        try {
            NumberFormat format = NumberFormat.getInstance(new Locale("tr", "TR"));
            Number number = format.parse(priceText);
            doubleCurrency = number.doubleValue();

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return doubleCurrency;
    }

    public void saveProductPrice(){
        detailPrice = getProductPrice();
        System.out.println("detailPrice = " + detailPrice);
    }

    public void cartPrice(){
        System.out.println("cartPriceList.get(0).getText() = " + cartPriceList.get(0).getText());
        double d = 0;
        try {
            // Türkçe yerel ayarına göre fiyatı parse et
            NumberFormat format = NumberFormat.getInstance(new Locale("tr", "TR"));
            Number number = format.parse(cartPriceList.get(0).getText());
            d = number.doubleValue();

        } catch (ParseException e) {
            e.printStackTrace();
        }
        Assert.assertEquals(detailPrice, d , 0.0);
    }



}
