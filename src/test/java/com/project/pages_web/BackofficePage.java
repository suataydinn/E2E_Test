package com.project.pages_web;

import com.project.utilities.Driver;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class BackofficePage {

    WebDriver driver = Driver.get();
    public BackofficePage() {
        PageFactory.initElements(driver, this);
    }

}
