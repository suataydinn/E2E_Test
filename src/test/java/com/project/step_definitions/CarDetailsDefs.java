package com.project.step_definitions;

import com.project.pages_web.*;
import com.project.utilities.BrowserUtils;
import com.project.utilities.Driver;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class CarDetailsDefs {
    WebDriver driver = Driver.get();
    CarDetailPage carDetailPage = new CarDetailPage();


    @And("click rent now button")
    public void clickRentNowButton() {
        BrowserUtils.clickWithJS(carDetailPage.RentNowButton);
        BrowserUtils.waitFor(3);
    }

    @And("save data car detail URL and UI")
    public void saveDataCarDetailURLAndUI() {
        carDetailPage.CarDetailInfo();
    }

    @Then("match and verify car detail and URL")
    public void matchAndVerifyCarDetailAndURL() {
        BrowserUtils.waitForVisibility(carDetailPage.fuelDetailPage,10);
        carDetailPage.verifyUrlAndCarDetailPage();
    }

    @Then("check included price is NOT visible")
    public void checkIncludedPriceIsNOTVisible() {
        Assert.assertEquals("included price should not visible",0,driver.findElements(By.xpath("//div[@cms-key='included_in_price']")).size());
    }


    @And("check price extra product")
    public void checkPriceExtraProduct() {
        carDetailPage.checkExtraProPrice();
    }

    @Then("verify price on car detail & search result Office Service Fee in Amount to Pay at Office {int}")
    public void verifyPriceOnCarDetailSearchResultOfficeServiceFeeInAmountToPayAtOffice(double fee) {
        carDetailPage.verifyPriceSearchResultToCarDetail(fee);
    }

    @And("verify match reservation detail and API")
    public void verifyMatchReservationDetailAndAPI() {
        String reservationNumber = new ReservationDetailPage().onlyReservationNumber.getText();
        carDetailPage.verifyAPI_reservationDetailPage(reservationNumber);
    }

    @Then("match and verify car detail and reservation detail")
    public void matchAndVerifyCarDetailAndReservationDetail() {
        carDetailPage.verifyCarDetail_reservationDetailPage();
    }
}

