package com.project.step_definitions;

import com.project.pages_mobile.*;
import com.project.pages_web.*;
import com.project.utilities.*;
import io.cucumber.java.en.*;

import org.openqa.selenium.WebDriver;


public class MobileSearchDefs {
    WebDriver driver = Driver.get();
    MobileSearchPage mobileSearchPage = new MobileSearchPage();


    @And("click mobile find button")
    public void clickMobileFindButton() {
            mobileSearchPage.clickMobileFindButton();
    }


}
