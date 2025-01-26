package com.project.step_definitions;

import com.project.pages_web.*;
import com.project.pages_mobile.*;
import com.project.utilities.*;
import io.cucumber.java.en.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class MobileReservationDefs {
    WebDriver driver = Driver.get();
    MainPage page= new MainPage();
    MobileReservationPage mobileReservationPage= new MobileReservationPage();


    @Then("the user should be able to make reservation in mobile view")
    public void theUserShouldBeAbleToMakeReservationInMobileView() {
        mobileReservationPage.verifyGuestMobileReservation();
    }

    @Then("verify mobile price on car detail & search result")
    public void verifyMobilePriceOnCarDetailSearchResult() {
        mobileReservationPage.paymentInfoBar.click();
        mobileReservationPage.verifyMobilePriceSearchResultToCarDetail();
    }

    @Then("verify mobile price on car detail & search result on reservation detail page")
    public void verifyMobilePriceOnCarDetailSearchResultOnReservationDetailPage() {
        mobileReservationPage.verifyMobilePriceSearchResultToCarDetail();
    }


}
