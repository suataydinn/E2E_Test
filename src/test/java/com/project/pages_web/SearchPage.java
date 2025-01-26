package com.project.pages_web;

import com.project.utilities.BrowserUtils;
import com.project.utilities.Driver;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.List;

public class SearchPage extends MainPage{
    WebDriver driver = Driver.get();

    @FindBy(css = "div.dp__range_start")
    public WebElement startDateSelected;

    @FindBy(css = "div.dp__range_end")
    public WebElement finishDateSelected;

    @FindBy(css = "div.dp__today")
    public WebElement todayInCalender;

    @FindBy(css = "div.dp__range_between")
    public List <WebElement> calenderDayBetweenSelected;

    @FindBy(css = "button.dp__month_year_select")
    public List <WebElement> calenderMonthYear;

    @FindBy(css = "#rich-country")
    public WebElement countryDropDown;

    @FindBy(css = ".country-menu__item-wrapper__item")
    public List <WebElement> countryList;

    @FindBy(css = "#inputSearchCountry")
    public WebElement countrySearch;

    @FindBy(css = "div.country-search")
    public WebElement selectedCountry;

    @FindBy(css = "div.age-search")
    public WebElement selectedAge;

    @FindBy(css = "span.dp__inner_nav")
    public List <WebElement> calenderArrow;


    public SearchPage() {
        PageFactory.initElements(driver, this);
    }

    int[] getDate= new int[7];
    String[] getDateString= new String[6];
    String[] getTime= new String[2];
    public static String[] topFullDateTimeSearchResultArray = new String[2];

    int rentalDay;

    public void locationSearchAndClick(String location){
        String subString1= "";
        String subString2= "";
        String subString3= "";

        if(location.length()>12){
            subString1= location.substring(0,4);
            subString2= location.substring(4,10);
            subString3= location.substring(10);
        }else if(location.length()>=6){
            subString1= location.substring(0,4);
            subString2= location.substring(4);
        }else if(location.length()>=3){
            subString1= location.substring(0,2);
            subString2= location.substring(2);
        }
        else{
            subString1=location;
        }

            BrowserUtils.waitForClickability(pickUpInputClick,1);
            pickUpInputClick.click();

            BrowserUtils.waitForVisibility(pickUpInput,1);
            pickUpInput.sendKeys(subString1);
            BrowserUtils.waitForVisibility(pickUpInput,1);
            pickUpInput.sendKeys(subString2);
            BrowserUtils.waitForVisibility(pickUpInput,1);
            pickUpInput.sendKeys(subString3);

            BrowserUtils.waitForVisibility(firstLocationLocator,20);

            firstLocationLocator.click();

        Assert.assertTrue(pickUpInput.getAttribute("value").contains(location));
        }



    public void locationDropOffSearch(String location){

        String subString1= "";
        String subString2= "";
        String subString3= "";

        if(location.length()>12){
            subString1= location.substring(0,4);
            subString2= location.substring(4,10);
            subString3= location.substring(10,location.length());
        }else if(location.length()>=6){
            subString1= location.substring(0,4);
            subString2= location.substring(4);
        }else{
            subString1=location;
        }

        BrowserUtils.verifyElementDisplayed(dropOffInput);
        dropOffInput.click();
        BrowserUtils.waitForVisibility(dropOffInput,1);
        dropOffInput.sendKeys(Keys.BACK_SPACE);

        BrowserUtils.waitForVisibility(dropOffInput,1);
        dropOffInput.sendKeys(subString1);
        BrowserUtils.waitForVisibility(dropOffInput,1);
        dropOffInput.sendKeys(subString2);
        BrowserUtils.waitForVisibility(dropOffInput,1);
        dropOffInput.sendKeys(subString3);

        BrowserUtils.waitForClickability(firstLocationLocator,2);
        firstLocationLocator.click();
        Assert.assertTrue(dropOffInput.getAttribute("value").contains(location));

    }

    public void selectDate(){
        pickUpDate.click();
        BrowserUtils.waitForClickability(defaultCalendarDate.get(3),3);
        defaultCalendarDate.get(7).click();
        defaultCalendarDate.get(12).click();
        pickUpTime.click();
        BrowserUtils.waitForClickability(timeSelected,3);
        timeSelected.click();
        dropOfTime.click();
        BrowserUtils.waitForClickability(timeSelected,3);
        timeSelected.click();
    }

    public void selectSpecificDate(int day1, int day2) {
        pickUpDate.click();
        BrowserUtils.waitForClickability(defaultCalendarDate.get(3),1);
        defaultCalendarDate.get(day1).click();
        defaultCalendarDate.get(day2).click();
        pickUpTime.click();
        BrowserUtils.waitForClickability(timeSelected,1);
        timeSelected.click();
        dropOfTime.click();
        BrowserUtils.waitForClickability(timeSelected,1);
        timeSelected.click();
    }

    public void defaultDailySearch(){
        BrowserUtils.waitFor(1);

        int pickUpDate= getDate[0];
        int pickUpMontDate= getDate[1];
        int dropOffDate= getDate[3];
        int dropOffMontDate= getDate[4];

        int selectedDates= calenderDayBetweenSelected.size()+2;
        BrowserUtils.waitFor(2);
        Assert.assertEquals(selectedDates,4);

        int pickupYear = getDate[2];
        int dropOfYear = getDate[5];

        LocalDate pickupFullDate = LocalDate.of(pickupYear, pickUpMontDate, pickUpDate);
        LocalDate dropOfFullDate = LocalDate.of(dropOfYear, dropOffMontDate, dropOffDate);

        long between = ChronoUnit.DAYS.between(pickupFullDate, dropOfFullDate);
        Assert.assertEquals(3,between);

        if(pickUpMontDate==dropOffMontDate){
            Calendar calender = Calendar.getInstance();
            int lastDayMonth = calender.getActualMaximum(Calendar.DAY_OF_MONTH);

            if(lastDayMonth==getDate[6]){
                getDate[6]=0;
            }
            Assert.assertEquals(pickUpDate, getDate[6] + 1);
            Assert.assertEquals(dropOffDate, getDate[6] + 4);
        }else{
            Assert.assertTrue(getDate[2] == getDate[5] ? dropOffMontDate > pickUpMontDate : dropOffMontDate < pickUpMontDate);
        }
    }

    public String[] getDayInCalenderString(){
        String pickUp= startDateSelected.getText();
        String dropUp= finishDateSelected.getText();
        getDateString[0]= pickUp;
        getDateString[1]= calenderMonthYear.get(0).getText();
        getDateString[2]= calenderMonthYear.get(1).getText();
        getDateString[3]= dropUp;
        if(Integer.parseInt(getDateString[0])>Integer.parseInt(getDateString[3])){
            getDateString[4]= calenderMonthYear.get(2).getText();
        }else{
            getDateString[4]= calenderMonthYear.get(0).getText();
        }
        getDateString[5]= driver.findElement(By.xpath("(//div[contains(@class, 'dp__range_end')]/../../../../../..//button)[3]")).getText();

        return getDateString;
    }

    public int[] getDayInCalender(){
        getDayInCalenderString();

        int today=0;
        BrowserUtils.waitFor(2);
        if(driver.findElements(By.cssSelector("div.dp__today")).size()==0){
            calenderArrow.get(0).click();
            BrowserUtils.waitForVisibility(todayInCalender,2);
            today= Integer.parseInt(todayInCalender.getText());
            calenderArrow.get(1).click();
        }else{
            today= Integer.parseInt(todayInCalender.getText());
        }

        getDate[0] = Integer.parseInt(getDateString[0]);
        getDate[1] = BrowserUtils.convertMonthNumber().get(getDateString[1].toUpperCase());
        getDate[2]= Integer.parseInt(getDateString[2]);
        getDate[3]= Integer.parseInt(getDateString[3]);
        getDate[4]= BrowserUtils.convertMonthNumber().get(getDateString[4].toUpperCase());
        getDate[5]= Integer.parseInt(getDateString[5]);
        getDate[6]= today;
        for (int i = 0; i < getDate.length; i++) {
            System.out.println("getDate["+i+"] = " + getDate[i]);

        }
        for (int i = 0; i < getDateString.length; i++) {
            System.out.println("getDateString["+i+"] = " + getDateString[i]);

        }
        return getDate;
    }

    public String[] getTimeInCalender(){
        getTime[0] = pickUpTime.getText();
        getTime[1] = dropOfTime.getText();
        return getTime;
    }

    public String weekOfDayPickUp(){
        int year = getDate[2];
        int month = getDate[1];
        int day = getDate[0];

        LocalDate date = LocalDate.of(year, month, day);
        DayOfWeek weekOfDay = date.getDayOfWeek();

        return BrowserUtils.weekOfDay().get(weekOfDay);
    }

    public String weekOfDayDropOf(){
        int year = getDate[5];
        int month = getDate[4];
        int day = getDate[3];

        LocalDate date = LocalDate.of(year, month, day);
        DayOfWeek weekOfDay = date.getDayOfWeek();

        return BrowserUtils.weekOfDay().get(weekOfDay);
    }

    public int rentalDayCount(){
        rentalDay = Integer.parseInt(rentalDayCount.getText().substring(0, 1));
        return rentalDay;
    }

    public void verifyDateMain_searchResult(){
        String strPickUp= weekOfDayPickUp() + ", " + getDateString[0] + " " + getDateString[1].substring(0,3) + ", " + getDate[2] + " " + getTime[0] ;
        String strDropOf= weekOfDayDropOf() + ", " + getDateString[3] + " " + getDateString[4].substring(0,3) + ", " + getDate[5] + " " + getTime[1] ;

        Assert.assertEquals(topFullDateTimeSearchResultArray[0],strPickUp);
        Assert.assertEquals(topFullDateTimeSearchResultArray[1],strDropOf);


    }

    public void selectCountryMainPage(String country){
        countryDropDown.click();
        driver.findElement(By.xpath("//div[text()='"+country+"']")).click();
        Assert.assertEquals(country,selectedCountry.getText());
    }

    public void selectCountryEnterTextMainPage(String text, String country){
        countryDropDown.click();
        countrySearch.sendKeys(text);
        countryList.get(0).click();
        Assert.assertEquals(country,selectedCountry.getText());
    }

    public void selectAge(String age){
        ageDropDown.click();
        driver.findElement(By.cssSelector("#dropdownAge"+age+"")).click();
        Assert.assertEquals(age,selectedAge.getText());

    }

    public void verifyRentalDayCount(int day){
        if(defaultCalendarDate.size()==0)pickUpDate.click();
        BrowserUtils.waitForVisibility(pickUpInput,3);
        Assert.assertEquals(calenderDayBetweenSelected.size()+1,day);

        pickUpDate.click();
    }

    public void verifyCountryAge(String country, String age){
        Assert.assertEquals(selectedCountry.getText(),country);
        Assert.assertEquals(selectedAge.getText(),age);

    }

    public void verifyDateTimeUrl(){
        String[] splitUrl = Driver.get().getCurrentUrl().split("=");

        Assert.assertEquals(getDateString[2]+"-"+String.format("%02d", getDate[1])+"-"+String.format("%02d", getDate[0]),getUrlDate()[0]);
        Assert.assertEquals(getDateString[5]+"-"+String.format("%02d", getDate[4])+"-"+String.format("%02d", getDate[3]),getUrlDate()[1]);
        Assert.assertEquals(getTime[0],splitUrl[4].substring(0,5));
        Assert.assertEquals(getTime[1],splitUrl[5].substring(0,5));

    }

    public String[] getUrlDate(){
        String[] splitUrl = Driver.get().getCurrentUrl().split("=");

        String picUpUrlString = splitUrl[2].substring(0, 10);
        String dropOffUrlString = splitUrl[3].substring(0, 10);

        String[] dates = {picUpUrlString, dropOffUrlString};
        return dates;
    }

    public void verifyUrlDate(int rentalDay){

        LocalDate pickupFullDate = LocalDate.parse(getUrlDate()[0]);
        LocalDate dropOfFullDate = LocalDate.parse((getUrlDate()[1]));

        long between = ChronoUnit.DAYS.between(pickupFullDate, dropOfFullDate);
        Assert.assertEquals(rentalDay,between);

    }

}
