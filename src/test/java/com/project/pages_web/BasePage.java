package com.project.pages_web;

import com.project.utilities.BrowserUtils;
import com.project.utilities.Driver;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.time.Duration;
import java.util.List;


public abstract class BasePage {

    @FindBy(xpath = "//div[@data-test-id='nonPremium-logo']")
    public WebElement nonPremiumLogo;

    WebDriver driver = Driver.get();




}

