package com.project.step_definitions;

import com.project.pages_web.DriverInformationPage;
import com.project.utilities.BrowserUtils;
import com.project.utilities.Driver;
import io.cucumber.java.en.And;

import org.openqa.selenium.WebDriver;

public class DriverInformationsDefs {
    WebDriver driver = Driver.get();
    DriverInformationPage driverInformationPage = new DriverInformationPage();


    @And("enter abroad guest driver information")
    public void enterAbroadGuestDriverInformation() {
        driverInformationPage.guestAbroadDriverInfo();
    }

    @And("enter guest TCKN driver information")
    public void enterGuestTCKNDriverInformation() {
        BrowserUtils.waitForVisibility(driverInformationPage.nameBox,3);
        driverInformationPage.guestDriverInfo();
    }


}

