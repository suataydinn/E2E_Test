package com.project.step_definitions;

import com.project.pages_web.MainPage;
import com.project.pages_web.SearchPage;
import com.project.utilities.BrowserUtils;
import com.project.utilities.Driver;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class SearchDefs {
    WebDriver driver = Driver.get();
    MainPage page = new MainPage();
    SearchPage searchPage = new SearchPage();

//    @Given("search {string} product")
//    public void searchProduct(String product) {
//        searchPage.searchProduct(product);
//    }

    @Then("search result should be {string}")
    public void searchResultShouldBe(String product) {
        Assert.assertEquals("verify search result title" ,searchPage.searchResultKeyword.getText() , product);
    }


    @Given("the user navigates to {string} to {string}")
    public void theUserNavigatesToTo(String arg0, String arg1) {
        
    }

    @Given("the user navigates to {string} to {string} to {string}")
    public void theUserNavigatesToToTo(String tab, String subTab, String module) {
        page.navigateToModule(tab, subTab, module);
    }

    @When("select title {string} filter {string}")
    public void selectTitleFilter(String filterTitle, String filterName) {
        searchPage.selectFilter(filterTitle, filterName);
    }

    @When("click highest price product")
    public void click_highest_price_product() {
        searchPage.highestPrice();

    }


    @Then("selected filter should be {int}")
    public void selectedFilterShouldBe(int filterSize) {
        Assert.assertEquals(filterSize, searchPage.selectedFilterLocator.size());
    }

    @And("selected filter name should be same")
    public void selectedFilterNameShouldBeSame(List<String> filter) {
        List<String> actual = BrowserUtils.createList(searchPage.selectedFilterLocator);
        Set<String> actualSet = new HashSet<>(actual);
        Set<String> filterSet = new HashSet<>(filter);

        Assert.assertTrue("Actual list does not match filter list.", actualSet.containsAll(filterSet) && filterSet.containsAll(actualSet));
    }
}