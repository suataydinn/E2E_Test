package com.project.step_definitions;


import com.project.pages_web.*;
import com.project.utilities.BrowserUtils;
import com.project.utilities.Driver;
import io.cucumber.java.en.*;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;



public class SearchResultDefs {
    WebDriver driver = Driver.get();
    SearchResultPage searchResultPage = new SearchResultPage();
    MainPage page= new MainPage();


    @And("click find button")
    public void clickFindButton() {
        searchResultPage.clickFindButton();
    }

    @And("Select vehicle in row {int}")
    public void selectVehicleInRow(int rowNumber) {
        searchResultPage.selectSpecificCar(rowNumber);
        BrowserUtils.waitFor(2);
    }

    @And("Select vehicle in row {int} and verify {string}")
    public void selectVehicleInRowAndVerify(int rowNumber, String vendor) {
        searchResultPage.selectSpecificCar(rowNumber);
        BrowserUtils.waitFor(2);
        BrowserUtils.verifyElementDisplayed(driver.findElement(By.cssSelector("#"+BrowserUtils.capitalize(vendor))));
    }

    @Then("verify open search result page")
    public void verifyOpenSearchResultPage() {
        BrowserUtils.waitFor(1);
        BrowserUtils.waitForClickability(page.firstCarForWait,9);
        BrowserUtils.verifyElementDisplayed(page.changeDateLocator);

        String scenarioTag = Hooks.publicScenario;
        if (scenarioTag.equals("@browserMobile")) {
            BrowserUtils.verifyElementDisplayed(searchResultPage.mobileSort);
        }else{
            BrowserUtils.verifyElementDisplayed(searchResultPage.sort);
        }
    }


    @And("select vendor {string}")
    public void selectVendor(String vendor) {
        searchResultPage.selectVendor(vendor);
    }

    @And("verify provision filter and car card provision info are seen")
    public void verifyProvisionFilterAndCarCardProvisionInfoAreSeen() {
        Assert.assertTrue(searchResultPage.provisionLoc.get(0).isDisplayed()); //filtre sayfası görünürlik kontrolü

        // araç listesinde ki tüm araç kartlarının kontrolü
        for (int j = 1; j < searchResultPage.provisionLoc.size(); j=j+2) {
            Assert.assertTrue(searchResultPage.provisionLoc.get(j).isDisplayed());
        }
    }

    @Then("verify provision info is seen")
    public void verifyProvisionInfoIsSeen() {
        Assert.assertTrue(searchResultPage.provisionLoc.get(0).isDisplayed()); //araç detay sayfası görünürlik kontrolü
    }

    @And("sort by {string}")
    public void sortBy(String priceType) {
        BrowserUtils.waitHalfSecond();
        searchResultPage.sortSpecial(priceType);
        BrowserUtils.waitHalfSecond();
    }

    @And("save first car price")
    public void saveFirstCarPrice() {
        searchResultPage.savePrice();
    }

    @And("click back to previous page")
    public void clickBackToPreviousPage() {
        driver.navigate().back();
    }
}

