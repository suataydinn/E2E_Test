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


}