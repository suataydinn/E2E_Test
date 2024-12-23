package com.project.step_definitions;

import com.project.pages_web.MainPage;
import com.project.pages_web.ProductPage;
import com.project.pages_web.SearchPage;
import com.project.utilities.BrowserUtils;
import com.project.utilities.Driver;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;

public class ProductDefs {

    WebDriver driver = Driver.get();
    MainPage page = new MainPage();
    SearchPage searchPage = new SearchPage();
    ProductPage productPage = new ProductPage();

    @Then("verify open product detail page")
    public void verify_open_product_detail_page() {
        BrowserUtils.verifyElementDisplayed(productPage.productDetailSeller);
        BrowserUtils.verifyElementDisplayed(productPage.addCartButton);
    }

    @Then("save product information")
    public void save_product_information() {
        productPage.saveProductPrice();
    }
    @Then("click add cart")
    public void click_add_cart() {
        productPage.addCartButton.click();
    }
    @Then("verify open cart")
    public void verify_open_cart() {
        BrowserUtils.verifyElementDisplayed(productPage.onboardingItemList);
    }

    @And("cart price should be same")
    public void cartPriceShouldBeSame() {
        productPage.cartPrice();
    }

    @And("go to cart")
    public void goToCart() {
        BrowserUtils.waitForClickablility(productPage.cartButton,3);
        productPage.cartButton.click();
    }

    @Then("cart product count should be {int}")
    public void cartProductCountShouldBe(int expectedCount) {
        BrowserUtils.waitForVisibility(productPage.cartProductCount.get(0),8);
        int actualCartCount = productPage.cartProductCount.size();
        Assert.assertEquals("verify cart count", expectedCount, actualCartCount);
    }
}
