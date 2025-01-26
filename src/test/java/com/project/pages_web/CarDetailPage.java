package com.project.pages_web;

import com.project.pages_api.ApiPage;
import com.project.utilities.BrowserUtils;
import com.project.utilities.Driver;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.text.DecimalFormat;
import java.util.List;

public class CarDetailPage extends MainPage{
    WebDriver driver = Driver.get();
    SearchResultPage searchResultPagePage = new SearchResultPage();
    String extraProductPrice;

    @FindBy(xpath = "//span[contains(@class,'desktop:order')]")
    public List<WebElement> carDetailObjectLocator;

    @FindBy(xpath = "//div[starts-with(@cms-key, 'button_rent_now')]")
    public WebElement RentNowButton;

    @FindBy(xpath = "//*[@cms-key='tooltip_extra_product_text']/div")
    public List <WebElement> getAddExtraProductList;

    @FindBy(css = "#payment_pricebox_text1")
    public WebElement cardPayment ;

    @FindBy(css = "#payment_pricebox_text2")
    public WebElement officePrice ;

    @FindBy(css = "#payment_pricebox_total_price")
    public WebElement totalPrice ;

    @FindBy(xpath = "//div[@class='font-bold text-lg text-dark-gray']")
    public WebElement totalPriceMobil;

    @FindBy(xpath = "(//div[@class='other-price'])[1]")
    public WebElement carRentalPrice;

    @FindBy(xpath = "(//div[@class='other-price'])[2]")
    public WebElement taxPrice ;

    @FindBy(css = "#payment_pricebox_info")
    public WebElement rentalDay ;

    @FindBy(css = "#text_daily_price")
    public WebElement dailyPrice ;

    @FindBy(xpath = "//*[@cms-key='text_delivery_type']")
    public  WebElement deliveryType;

    @FindBy(xpath = "//*[@cms-key='office_address_text']")
    public  WebElement officeAddress;

    @FindBy(xpath = "//i[contains(@class,'icon-seat')]")
    public  WebElement iconSeat;

    @FindBy(xpath = "//span[starts-with(@cms-key, 'filter_transmission_')]")
    public  WebElement transmissionDetailPage;

    @FindBy(xpath = "//span[starts-with(@cms-key, 'filter_fuel_')]")
    public  WebElement fuelDetailPage;

    @FindBy(xpath = "//*[starts-with(@cms-key, 'filter_class_type_')]")
    public  WebElement segmentDetailPage;

    @FindBy(xpath = "//div[@cms-key='pricing_officeServiceFee']//../div[2]")
    public  WebElement feePrice;

    @FindBy(xpath = "//*[@cms-key='product_childSeat']")
    public  List<WebElement> extraProText;



    public String carDetailUrl ="";
    String[] carDetailUI= new String[5];


    public CarDetailPage() {
        PageFactory.initElements(driver, this);
    }


    public void verifyPriceSearchResultToCarDetail(){
        new SearchResultPage();
        Assert.assertEquals("Toplam fiyat araç detay ve araç listesinde aynı olmalı",totalPrice.getText(), SearchResultPage.searchResultPriceText[0]);
        Assert.assertEquals("Günlük Fiyat : "+ dailyPrice.getText(),SearchResultPage.searchResultPriceText[1]);
        Assert.assertEquals("("+rentalDay.getText()+")",SearchResultPage.searchResultPriceText[2]);
    }

    public void verifyPriceSearchResultToCarDetail(double fee){
        Assert.assertEquals(totalPrice.getText(),SearchResultPage.searchResultPriceText[0]);
        double doubleFeePrice = BrowserUtils.convertToDoubleWithCurrencyIcon(feePrice.getText());
        double doubleOfficePrice = BrowserUtils.convertToDoubleWithCurrencyIcon(officePrice.getText());
        double doubleCardPayment= BrowserUtils.convertToDoubleWithCurrencyIcon(cardPayment.getText());
        double doubleTotalPrice= BrowserUtils.convertToDoubleWithCurrencyIcon(totalPrice.getText());
        Assert.assertEquals(fee, doubleFeePrice, 0.0);
        Assert.assertTrue(doubleCardPayment+fee==doubleTotalPrice);
        Assert.assertEquals("Günlük Fiyat : "+ dailyPrice.getText(),SearchResultPage.searchResultPriceText[1]);
        Assert.assertEquals("("+rentalDay.getText()+")",SearchResultPage.searchResultPriceText[2]);
    }

    public void calculatePrice(){
        double officePriceDouble =0.00;
        String officePriceAllText="";

        BrowserUtils.waitFor(5);
        int rentalDayCount = Integer.parseInt(rentalDay.getText().substring(0, 1));

        String totalPriceAllText =totalPrice.getText();
        String dailyPriceAllText = dailyPrice.getText();
        String cardPriceAllText = cardPayment.getText();

        try {
            officePriceAllText = officePrice.getText();
        } catch (org.openqa.selenium.NoSuchElementException e) {
            e.printStackTrace();
        }



        if (cardPriceAllText.contains(".")) {
            cardPriceAllText = cardPriceAllText.replace(".", "");
        }
        if (totalPriceAllText.contains(".")) {
            totalPriceAllText = totalPriceAllText.replace(".", "");
        }
        if (dailyPriceAllText.contains(".")) {
            dailyPriceAllText = dailyPriceAllText.replace(".", "");
        }
        if (officePriceAllText.contains(".")) {
            officePriceAllText = officePriceAllText.replace(".", "");
        }

        cardPriceAllText = cardPriceAllText.replace(",", ".");
        totalPriceAllText = totalPriceAllText.replace(",", ".");
        dailyPriceAllText = dailyPriceAllText.replace(",", ".");
        officePriceAllText = officePriceAllText.replace(",", ".");

        String cardPriceText = SearchResultPage.extractNumbersAndDots(cardPriceAllText);
        String totalPriceText = SearchResultPage.extractNumbersAndDots(totalPriceAllText);
        String dailyPriceText = SearchResultPage.extractNumbersAndDots(dailyPriceAllText);
        String officePriceText = SearchResultPage.extractNumbersAndDots(officePriceAllText);

        double cardPrice = Double.parseDouble(cardPriceText);
        double totalPrice = Double.parseDouble(totalPriceText);
        double dailyPrice = Double.parseDouble(dailyPriceText);
        if(!officePriceAllText.isEmpty()){
            officePriceDouble=  Double.parseDouble(officePriceText);

        }

        double v = totalPrice / rentalDayCount;//günlük ücreti bulduk

        System.out.println("v = " + v);
        System.out.println("BrowserUtils.RoundingUpDouble(v) = " + BrowserUtils.roundingUpDouble(v));
        System.out.println("dailyPrice = " + dailyPrice);
        Assert.assertTrue(BrowserUtils.roundingUpDouble(v)==dailyPrice || v == dailyPrice) ;//üste yuvarlama
        System.out.println("dailyPrice = " + dailyPrice);
        DecimalFormat toTheFormat = new DecimalFormat("#.##");
        double sum = Double.sum(officePriceDouble, cardPrice);
        System.out.println("toTheFormat.format(sum) = " + toTheFormat.format(sum));
        double d= Double.parseDouble(toTheFormat.format(sum));
        System.out.println("totalPrice = " + totalPrice);
        System.out.println("d = " + d);
        Assert.assertTrue(totalPrice==d);

        String reservationNoText = new ReservationDetailPage().reservationNo.getText();
        Assert.assertEquals(new ApiPage().getPaymentDetail(reservationNoText).get("remainingAmount"), BrowserUtils.replaceTextPrice(cardPriceText));

    }

    public void verifyPriceAfterSearchResultWithExtraProduct(){

        Assert.assertEquals(totalPrice.getText(),carDetailPriceText[0]);
        Assert.assertEquals(dailyPrice.getText(),carDetailPriceText[1]);
        Assert.assertEquals(rentalDay.getText(),carDetailPriceText[2]);
        Assert.assertEquals(officePrice.getText(),carDetailPriceText[3]);
        Assert.assertEquals(cardPayment.getText(),carDetailPriceText[4]);
    }

    public static String[] carDetailPriceText = new String[6];

    public String[] saveCarDetailPrice() {
        String rentalDayText = rentalDay.getText();
        String dailyPriceText = dailyPrice.getText();
        String totalPriceText = totalPrice.getText();
        String officePriceText = "";

        try {
            officePriceText = officePrice.getText();
        } catch (org.openqa.selenium.NoSuchElementException e) {
            e.printStackTrace();
        }

        String cardPriceText = cardPayment.getText();
        String carRentalPriceText = carRentalPrice.getText();

        carDetailPriceText[0]=totalPriceText;
        carDetailPriceText[1]=dailyPriceText;
        carDetailPriceText[2]=rentalDayText;
        carDetailPriceText[3]=officePriceText;
        carDetailPriceText[4]=cardPriceText;
        carDetailPriceText[5]=carRentalPriceText;


        return carDetailPriceText;
    }

    public void checkRentalCondition() {
        BrowserUtils.clickWithJS(searchResultPagePage.rentalConditionLoc);
        BrowserUtils.waitFor(4);
        Assert.assertTrue(driver.findElement(By.xpath("//h2[@cms-key='rent_condition_text']")).isDisplayed());
        BrowserUtils.waitForVisibility(driver.findElement(By.xpath("(//i[contains(@class,'icon-dropdown')])[1]")),5);
        driver.findElement(By.xpath("(//i[contains(@class,'icon-dropdown')])[2]")).click();
        BrowserUtils.waitHalfSecond();
        Assert.assertTrue(driver.findElement(By.xpath("(//div[contains(@class,'bg-white px-6 py-3')])[1]")).isDisplayed());
        BrowserUtils.waitHalfSecond();
        driver.findElement(By.xpath("(//i[contains(@class,'icon-dropdown')])[3]")).click();
        BrowserUtils.waitHalfSecond();
        Assert.assertTrue(driver.findElement(By.xpath("(//div[contains(@class,'bg-white px-6 py-3')])[2]")).isDisplayed());

        driver.findElement(By.xpath("//i[contains(@class,'icon-close')]")).click();
        Assert.assertEquals(driver.findElements(By.xpath("//h2[@cms-key='rent_condition_text']")).size(),0);
    }

    public void CarDetailInfo(){
        carDetailUrl = Driver.get().getCurrentUrl();
        String carDetailTransmissionType = transmissionDetailPage.getText();
        String carDetailFuelType = fuelDetailPage.getText();
        String carModel = driver.findElement(By.xpath("//div[@cms-key='or_similar']/../div")).getText();
        String carSegment = segmentDetailPage.getText();
        String vendor = driver.findElement(By.xpath("(//figure/img)[2]")).getAttribute("alt");
        System.out.println("vendor = " + vendor);
        carDetailUI[0]= carDetailTransmissionType;
        carDetailUI[1]= carDetailFuelType;
        carDetailUI[2]= carModel;
        carDetailUI[3]= carSegment;
        carDetailUI[4]= vendor;

    }

    public void verifyUrlAndCarDetailPage(){//this method run together CarDetailInfo() method
        Assert.assertEquals(carDetailUI[0],transmissionDetailPage.getText());
        Assert.assertEquals(carDetailUI[1],fuelDetailPage.getText());
        Assert.assertEquals(carDetailUI[2],driver.findElement(By.xpath("//div[@cms-key='or_similar']/../div")).getText());
        Assert.assertEquals(carDetailUI[3],segmentDetailPage.getText());
        Assert.assertEquals(carDetailUI[4],driver.findElement(By.xpath("(//figure/img)[2]")).getAttribute("alt"));

    }

    public void verifyAPI_reservationDetailPage(String reservationNumber){
        String[][] reservationDetail = new ApiPage().getReservationDetail(reservationNumber);

        Assert.assertEquals(reservationDetail[0][12],transmissionDetailPage.getText());
        Assert.assertEquals(reservationDetail[0][10],fuelDetailPage.getText());
        Assert.assertEquals(reservationDetail[0][8]+" "+reservationDetail[0][11],driver.findElement(By.xpath("//div[@cms-key='or_similar']/../h5")).getText());
        Assert.assertEquals(reservationDetail[0][9],segmentDetailPage.getText());

        String text= "";
        if(reservationDetail[1][11].equals("individual")){
            text= "Bireysel Fatura";
        }else{
            text= "Kurumsal Fatura";
        }
        Assert.assertEquals(addressTypeCheckout.get(0).getText(),text);

        List<WebElement> elements = driver.findElements(By.xpath("//div[contains(text(),'2024')]/../div"));

        String[] s1 = elements.get(0).getText().split(" ");
        String[] splitPickupCountry = s1[0].split("\n");
        String[] s2 = elements.get(2).getText().split(" ");
        String[] splitDropOffCountry = s2[0].split("\n");

        Assert.assertEquals(BrowserUtils.parsDateTime(reservationDetail[0][2]),elements.get(1).getText());
        Assert.assertEquals(BrowserUtils.parsDateTime(reservationDetail[0][3]),elements.get(3).getText());



        if(s1.length>3){
//Yurtdışı lokasyon için düzenlenecek

        }else if(s1.length==3){
            System.out.println("s2[0] = " + s2[2]);
            System.out.println("s1[2] = " + s1[2]);

            Assert.assertEquals(reservationDetail[0][5],splitPickupCountry[1] + " " + s1[1] + " " + s1[2]);
            Assert.assertEquals(reservationDetail[0][7],splitDropOffCountry[1] + " " + s2[1] + " " + s2[2]);

        }else {
            Assert.assertEquals(reservationDetail[0][5],splitPickupCountry[1] + " " + s1[1]);
            Assert.assertEquals(reservationDetail[0][7],splitDropOffCountry[1] + " " + s2[1]);
        }


        Assert.assertEquals(reservationDetail[0][4],splitPickupCountry[0]);
        Assert.assertEquals(reservationDetail[0][6],splitDropOffCountry[0]);

    }

    public void verifyCarDetail_reservationDetailPage(){//this method run together CarDetailInfo() method
        Assert.assertEquals(carDetailUI[0],transmissionDetailPage.getText());
        Assert.assertEquals(carDetailUI[1],fuelDetailPage.getText());
        Assert.assertEquals(carDetailUI[2] ,driver.findElement(By.xpath("//div[@cms-key='or_similar']/../h5")).getText());
        Assert.assertEquals(carDetailUI[3],segmentDetailPage.getText());
        Assert.assertEquals(carDetailUI[4],driver.findElement(By.cssSelector("#rich-vendor")).getText());

    }

    public void saveExtraProductPrice() {
            extraProductPrice=driver.findElement(By.xpath("(//strong[@class='text-dark'])[1]")).getText().substring(1);
            System.out.println("extraProductPrice = " + extraProductPrice);
    }

    public void checkTotalPrice() {
        // burada ki tax price locatoru esasında kart içinde ki extra product fiyatı olarak kullanılıyor bu senaryoda
        System.out.println("taxPrice.getText() = " + taxPrice.getText());
        Assert.assertEquals(extraProductPrice,carDetailPriceText[3].substring(1));
        Assert.assertEquals(extraProductPrice,taxPrice.getText().substring(1));

        int IntTotalPrice= Integer.parseInt(carDetailPriceText[0].substring(1).replace(".","").replace(",",""));
        int IntOfisPrice= Integer.parseInt(carDetailPriceText[3].substring(1).replace(".","").replace(",",""));
        int IntCardPrice= Integer.parseInt(carDetailPriceText[4].substring(1).replace(".","").replace(",",""));
        Assert.assertEquals((IntOfisPrice+IntCardPrice),IntTotalPrice);


    }

    public void checkExtraProPrice() {

        int IntTotalPrice= Integer.parseInt(carDetailPriceText[0].substring(1).replace(".","").replace(",",""));
        int IntOfisPrice= Integer.parseInt(carDetailPriceText[3].substring(1).replace(".","").replace(",",""));
        int IntCardPrice= Integer.parseInt(carDetailPriceText[4].substring(1).replace(".","").replace(",",""));
        Assert.assertEquals((IntOfisPrice+IntCardPrice),IntTotalPrice);

        String priceT=driver.findElement(By.xpath("//*[@cms-key='product_childSeat']/..//div//strong")).getText();
        int extraProSimplePrice=Integer.parseInt(SearchResultPage.extractNumbers(priceT));
        Assert.assertEquals(extraProSimplePrice*2,IntOfisPrice);
    }



}
