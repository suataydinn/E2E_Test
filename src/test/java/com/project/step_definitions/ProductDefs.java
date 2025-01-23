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


}
