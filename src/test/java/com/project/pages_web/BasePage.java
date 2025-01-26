package com.project.pages_web;

import com.project.utilities.BrowserUtils;
import com.project.utilities.Driver;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.time.Duration;
import java.util.List;


public abstract class BasePage {

    @FindBy(xpath = "//div[@cms-key='call_center_label']")
    public WebElement callCenterLocator;

    @FindBy(xpath = "//p[@cms-key='call_center']")
    public WebElement callCenterTextLocator;

    @FindBy(xpath = "//p[@cms-key='call_center_no']")
    public WebElement callCenterPhoneLocator;

    @FindBy(css = "#dropdown-currency>div")
    public WebElement currencyLocator;

    @FindBy(css = "#dropdown-currency>div")
    public List<WebElement> currencyListLocator;

    @FindBy(css = "#dropdown-lang>div")
    public List<WebElement> languageListLocator;

    @FindBy(xpath = "//span[text()='TRY']")
    public WebElement TRYLocator;

    @FindBy(xpath = "//span[text()='EUR']")
    public WebElement EURLocator;

    @FindBy(xpath = "//span[text()='USD']")
    public WebElement USDLocator;

    @FindBy(xpath = "//span[text()='GBP']")
    public WebElement GBPLocator;

    @FindBy(xpath = "//div[@class='slider-container-supplier']")
    public WebElement partnerLogo;

    @FindBy(xpath = "(//span[@cms-key='partner_label'])[1]")
    public WebElement Yolcu360PartnerLocator;

    @FindBy(xpath = "(//span[@cms-key='find_reservation'])[1]")
    public WebElement findResLocator;

    @FindBy(xpath = "(//span[@cms-key='signin_signup_label'])[1]")
    public WebElement loginLocator;

    @FindBy(xpath = "//a[@cms-key='signin_signup_label']")
    public WebElement loginLocatorMobil;

    @FindBy(xpath = "(//img[@alt='language'])[1]")
    public WebElement languageImgLocator;

    @FindBy(css = "#dropdown-lang>div")
    public WebElement languageLocator;

    @FindBy(xpath = "(//img[@cms-key='img_logo_yolcu'])[1]")
    public WebElement Yolcu360LogoLocator;

    @FindBy(xpath = "//*[@id='logo' or @cms-key='img_logo_yolcu']")
    public WebElement Yolcu360LogoLocator2;

    @FindBy(xpath = "//img[@cms-key='img_logo_yolcu_white']")
    public WebElement Yolcu360LogoLocatorFooter;

    @FindBy(xpath = "//div[@cms-key='welcome_primary_text']")
    public WebElement mainPageTextLocator1;

    @FindBy(xpath = "//div[@cms-key='welcome_secondary_text']")
    public WebElement mainPageTextLocator2;

    @FindBy(xpath = "//span[@cms-key='daily_rental']")
    public WebElement dailyRentalLocator;

    @FindBy(xpath = "//span[@cms-key='monthly_rental']")
    public WebElement monthlyRentalLocator;

    @FindBy(css = "#PickUpLocation")
    public WebElement searchBoxLocator;

    @FindBy(css = "#search")
    public WebElement searchLocator;

    @FindBy(xpath = "(//input[@type='checkbox'])[1]")
    public WebElement differentLocationCheckboxLocator;

    @FindBy(xpath = "(//*[@type='checkbox'])[2]")
    public WebElement driverCountryAndAgeCheckBoxLocator;

    @FindBy(css = "#campaign_section_button_text")
    public WebElement allCampaign;

    @FindBy(xpath = "(//i[@class='icon icon-chevron-right text-sm text-steel'])[2]")     //locatora id atanmalı
    public WebElement allCampaignRightArrow;

    @FindBy(xpath = "(//i[@class='icon icon-chevron-left text-sm text-steel'])[2]")     //locatora id atanmalı
    public WebElement allCampaignLeftArrow;

    @FindBy(xpath = "//i[@class='icon icon-chevron-right text-sm text-steel']")     //locatora id atanmalı
    public WebElement allCampaignRightArrowOnLocation;

    @FindBy(xpath = "//i[@class='icon icon-chevron-left text-sm text-steel']")     //locatora id atanmalı
    public WebElement allCampaignLeftArrowOnLocation;

    @FindBy(css = "span>span>i.icon.icon-chevron-left")     //locatora id atanmalı
    public WebElement popularLocationLeftArrow;

    @FindBy(css = "span>span>i.icon.icon-chevron-right")    //locatora id atanmalı
    public WebElement popularLocationRightArrow;

    @FindBy(xpath = "//a[@cms-key='all_partners']")
    public List<WebElement> allPartners;

    @FindBy(xpath = "//a[@cms-key='footer_car_1']")
    public WebElement footerAllPartners;

    @FindBy(xpath = "//*[@cms-key='faqs_title']")
    public WebElement faqTitle;

    @FindBy(xpath = "//div[@cms-key='footer_mobile_app']")
    public WebElement MobilUygulamamızText;

    @FindBy(xpath = "//div[@id='otherLocationsList']")
    public List<WebElement> otherLocationLoc;

    @FindBy(css = "div.self-end")
    public WebElement faqFooterTextLocator;

    @FindBy(css = "div.self-end>a")
    public WebElement faqHelpLocator;

    @FindBy(xpath = "//div[@cms-key='seo.seoContent']")
    public WebElement seoLoc;

    @FindBy(xpath = "//*[@cms-key='subscriber_input_placeholder']")
    public WebElement bultenEpostaBoxLocator;

    @FindBy(xpath = "//*[@cms-key='subscriber_button_text']")
    public WebElement aboneOlLocator;

    @FindBy(xpath = "//*[@cms-key='footer_about_us']")   // footer linklerinin tamamı tek id ile listeli bir şekilde bulunabilmeli!!
    public WebElement aboutUsLocator;

    @FindBy(xpath = "//*[@cms-key='footer_phone']")
    public WebElement footerPhoneLocator;

    @FindBy(xpath = "//*[@cms-key='footer_mail']")
    public WebElement footerMailLocator;

    @FindBy(xpath = "//img[@cms-key='img_app_apple_logo']")
    public List <WebElement> appStoreLocator;

    @FindBy(xpath = "//img[@cms-key='img_app_google_logo']")
    public List <WebElement> googlePlayLocator;

    @FindBy(xpath = "//img[@cms-key='img_app_huawei_logo']")
    public List <WebElement> huaweiLocator;

    @FindBy(xpath = "//a[@cms-key='footer_secret_text']")
    public WebElement secretLocator;

    @FindBy(xpath = "//a[@cms-key='footer_terms_text']")
    public WebElement termsLocator;

    @FindBy(xpath = "//a[@cms-key='footer_protect_data']")
    public WebElement protectDataLocator;

    @FindBy(css = "span.text-steel")
    public WebElement rentalPeriod;

    @FindBy(css = "ol.flex.gap-x-1>li")
    public List <WebElement> breadcrumbLinkList;

    @FindBy(xpath = "//div[@class='country-search']")
    public WebElement countryLocator;

    @FindBy(xpath = "//div[@class='age-search']")
    public WebElement ageLocator;

    @FindBy(css = "i.icon-delivery")
    public WebElement currentLocationLocator;

    @FindBy(xpath = "//a[@href='https://yolcu360.com/tr']")
    public WebElement ticariElektroniletiLocator;

    @FindBy(xpath = "//*[@cms-key='logo_sikayetvar']")
    public WebElement sikayetVarTextLocator;

    @FindBy(xpath = "//*[@cms-key='logo_google']")
    public WebElement googleTextLocator;

    @FindBy(xpath = "(//*[@cms-key='logo_google'])[1]")
    public WebElement trustPilotTextLocator;

    @FindBy(css = "#inputPickUpLocation")
    public WebElement pickUpInput;

    @FindBy(css = "#inputPickUpLocation-label>div")
    public WebElement pickUpInputClick;

    @FindBy(css = "#inputDropOffLocation")
    public WebElement dropOffInput;

    @FindBy(xpath = "(//input[@id='inputDropOffLocation'])[2]")
    public WebElement mobileDropOffInput;

    @FindBy(xpath = "(//input[@id='inputPickUpLocation'])[2]")
    public WebElement mobilePickUpInput;

    @FindBy(xpath = "//div[@cms-key='flight_welcome_from']/../div[3]")
    public WebElement mobilePickUpInputClick;

    @FindBy(xpath = "//div[@cms-key='flight_welcome_to']/../div[3]")
    public WebElement mobileDropOffInputClick;

    @FindBy(xpath = "(//button[@id='search'])[1]")
    public WebElement findButton;

    @FindBy(xpath = "(//button[@id='search'])[2]")
    public WebElement mobileFindButton;

    @FindBy(css = "#flight_search")
    public WebElement findButtonFlight;

    @FindBy(xpath = "//div[@class='search-autocomplete__item']/div")
    public List <WebElement> pickUpMenu;

    @FindBy(xpath = "(//div[@class='search-autocomplete']/div/div)[1]")
    public WebElement firstLocationLocator;

    @FindBy(xpath = "(//div[@class='search-autocomplete']/div)[1]")
    public WebElement mobileFirstLocationLocator;

    @FindBy(xpath = "((//div[@class='search-autocomplete']/div)[3]/div)[1]")
    public WebElement mobileFlightFirstLocationLocator;

    @FindBy(xpath = "//div[contains(@class,'dp__cell_inner dp__pointer')]")
    public List <WebElement> defaultCalendarDate;

    @FindBy(xpath = "(//div[contains(@class,'flex items-center cursor-pointer')])[1]")
    public WebElement pickUpTime;

    @FindBy(xpath = "(//div[contains(@class,'flex items-center cursor-pointer')])[2]")
    public WebElement mobilePickUpTime;

    @FindBy(xpath = "(//div[contains(@class,'flex items-center cursor-pointer')])[3]")
    public WebElement dropOfTime;

    @FindBy(css = "#headerMenuButton")
    public WebElement hamburgerMenu;

    @FindBy(css = "li.text-ui-primary")
    public WebElement timeSelected;

    @FindBy(xpath = "(//p[@cms-key='pickup_date'])[1]")
    public WebElement pickUpDate;

    @FindBy(xpath = "//*[@cms-key='text_change']")
    public WebElement changeDateLocator;

    @FindBy(xpath = "//div[starts-with(@cms-key, 'billing_card_')]")
    public List <WebElement> addressTypeCheckout;

    @FindBy(css = "div.whitespace-nowrap")
    public List <WebElement> topFullDateTime;

    @FindBy(xpath = "//span[@cms-key='text_day']")
    public WebElement rentalDayCount;

    @FindBy(css = "#rich-age")
    public WebElement ageDropDown;


    @FindBy(css = "i.icon-close")
    public List <WebElement> xButtonList;

    @FindBy(xpath = "(//*[@cms-key='button_rent_now']/..)[1]")
    public WebElement firstCarForWait;

    @FindBy(css = "#id-summary-dropdown-phone")
    public WebElement areaCodeLocator;

    @FindBy(css = "#inputSearchPhone")
    public WebElement areaCodeSearchLocator;
    @FindBy(xpath = "//*[@cms-key='label_billing_add_new_driver_info']")
    public WebElement addNewAddress;


    WebDriver driver = Driver.get();

    public BasePage() {
        PageFactory.initElements(driver, this);
    }




}

