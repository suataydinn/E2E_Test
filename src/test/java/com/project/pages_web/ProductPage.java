package com.project.pages_web;

import com.project.utilities.Driver;
import org.openqa.selenium.WebDriver;

import org.openqa.selenium.support.PageFactory;



public class ProductPage {

WebDriver driver = Driver.get();

    public ProductPage() {
        PageFactory.initElements(driver, this);
    }
}
