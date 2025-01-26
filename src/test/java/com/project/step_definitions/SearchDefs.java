package com.project.step_definitions;

import com.project.pages_web.*;
import com.project.utilities.*;
import io.cucumber.java.en.*;
import org.openqa.selenium.*;

public class SearchDefs {
    WebDriver driver = Driver.get();
    MainPage page= new MainPage();
    SearchPage searchPage = new SearchPage();

    @Given("search {string}")
    public void search(String location) {
        searchPage.locationSearchAndClick(location);
    }

    @When("select date and time from calendar")
    public void selectDateAndTimeFromCalendar() {
        searchPage.selectDate();
    }

    @And("enter different drop location {string}")
    public void enterDifferentDropLocation(String location) {
        searchPage.locationDropOffSearch(location);
    }

    @Then("the user should be able to see default daily search")
    public void theUserShouldBeAbleToSeeDefaultDailySearch() {
        searchPage.pickUpDate.click();
        BrowserUtils.waitForVisibility(searchPage.startDateSelected,3);
        searchPage.getDayInCalender();
        searchPage.defaultDailySearch();
        BrowserUtils.doubleClick(searchPage.pickUpDate);
    }

    @And("save date and time main page")
    public void saveDateAndTimeMainPage() {
        BrowserUtils.waitFor(1);
        if(page.defaultCalendarDate.size()==0){
            searchPage.pickUpDate.click();}

        BrowserUtils.waitHalfSecond();
        searchPage.getDayInCalender();
        searchPage.getTimeInCalender();
    }

    @Then("verify rental day count {int}")
    public void verifyRentalDayCount(int day) {
        searchPage.verifyRentalDayCount(day);
    }

    @Given("select {string} country")
    public void selectCountry(String country) {
        BrowserUtils.waitForVisibility(searchPage.countryDropDown, 1);
        searchPage.selectCountryMainPage(country);
    }

    @Given("search {string} select {string} country")
    public void searchSelectCountry(String text, String country) {
        BrowserUtils.waitForVisibility(page.findButton, 20);
        searchPage.selectCountryEnterTextMainPage(text,country);
    }

    @And("select {string} age")
    public void selectAge(String age) {
        searchPage.selectAge(age);
    }

    @And("click mobile rent now button")
    public void clickMobileRentNowButton() {
        new CarDetailPage().RentNowButton.click();
    }

}
