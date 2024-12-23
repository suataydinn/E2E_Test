package com.project.utilities;

import com.project.pages_web.MainPage;
import org.junit.Assert;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAccessor;
import java.util.*;


public class BrowserUtils {
    WebDriver driver = Driver.get();
    MainPage page= new MainPage();
    /**
     * Moves the mouse to given element
     * @param element on which to hover
     */
    public static void hover(WebElement element) {
        Actions actions = new Actions(Driver.get());
        actions.moveToElement(element).perform();
    }


    /**
     * Performs a pause
     */
    public static void waitFor(int seconds) {
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void waitHalfSecond() {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    /**
     * Waits for the provided element to be visible on the page
     */
    public static WebElement waitForVisibility(WebElement element, int timeToWaitInSec) {
        WebDriverWait wait = new WebDriverWait(Driver.get(), Duration.ofSeconds(timeToWaitInSec));
        return wait.until(ExpectedConditions.visibilityOf(element));
    }

    /**
     * Waits for element matching the locator to be visible on the page
     */
    public static WebElement waitForVisibility(By locator, int timeout) {
        WebDriverWait wait = new WebDriverWait(Driver.get(), Duration.ofSeconds(timeout));
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }


    /**
     * Waits for provided element to be clickable
     */
    public static WebElement waitForClickablility(WebElement element, int timeout) {
            try {
            WebDriverWait wait = new WebDriverWait(Driver.get(), Duration.ofSeconds(timeout));
            return wait.until(ExpectedConditions.elementToBeClickable(element));
        } catch (TimeoutException e) {
                System.err.println("Element is not clickable within the given timeout: " + timeout + " seconds");
            }
        return element;
    }


    /**
     * Waits for element matching the locator to be clickable
     */
    public static WebElement waitForClickablility(By locator, int timeout) {
        WebDriverWait wait = new WebDriverWait(Driver.get(), Duration.ofSeconds(timeout));
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }


    /**
     * waits for backgrounds processes on the browser to complete
     */
    public static void waitForPageToLoad(long timeOutInSeconds) {
        ExpectedCondition<Boolean> expectation = new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver driver) {
                return ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete");
            }
        };
        try {
            WebDriverWait wait = new WebDriverWait(Driver.get(), Duration.ofSeconds(timeOutInSeconds));
            wait.until(expectation);
        } catch (Throwable error) {
            error.printStackTrace();
        }
    }

    public static void waitForPresenceOfElement(By by, Duration time) {
        new WebDriverWait(Driver.get(), time).until(ExpectedConditions.presenceOfElementLocated(by));
    }

    public static void clickWithTimeOut(WebElement element, int timeout) {
        for (int i = 0; i < timeout; i++) {
            try {
                element.click();
                return;
            } catch (WebDriverException e) {
                waitFor(1);
            }
        }
    }

    /**
     * Clicks on an element using JavaScript
     */
    public static void clickWithJS(WebElement element) {
        ((JavascriptExecutor) Driver.get()).executeScript("arguments[0].scrollIntoView(true);", element);
        ((JavascriptExecutor) Driver.get()).executeScript("arguments[0].click();", element);
    }

    public static void clickWithJSNotScroll(WebElement element) {
        ((JavascriptExecutor) Driver.get()).executeScript("arguments[0].click();", element);
    }

    /**
     * Scrolls down to an element using JavaScript
     */
    public static void scrollToElement(WebElement element) {
        ((JavascriptExecutor) Driver.get()).executeScript("arguments[0].scrollIntoView(true);", element);
    }

    public static void scrollToSize(int start, int finish) {
        JavascriptExecutor js = (JavascriptExecutor) Driver.get();
        js.executeScript("window.scrollBy("+start+","+finish+")");
        BrowserUtils.waitFor(1);
    }

    public static void scrollToFooter() {
        JavascriptExecutor js = (JavascriptExecutor) Driver.get();
        js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
    }

    public static void scrollToHeader() {
        JavascriptExecutor js = (JavascriptExecutor) Driver.get();
        js.executeScript("window.scrollTo(0, 0)");
    }

    /**
     * Performs double click action on an element
     */
    public static void doubleClick(WebElement element) {
        new Actions(Driver.get()).doubleClick(element).build().perform();
    }

    public static void verifyElementDisplayed(WebElement element) {
        try {
            Assert.assertTrue("Element not visible: " + element, element.isDisplayed());
        } catch (NoSuchElementException e) {
            e.printStackTrace();
            Assert.fail("Element not found: " + element);

        }
    }

    public static void clickCoordinate(int x, int y){
        BrowserUtils.waitFor(1);
        Actions actions = new Actions(Driver.get());
        actions.moveByOffset(x,y).click().perform();
        BrowserUtils.waitFor(1);
    }

    public static void mouseHoverJScript(WebElement HoverElement) {
        try {
            if (isElementPresent(HoverElement)) {

                String mouseOverScript = "if(document.createEvent){var evObj = document.createEvent('MouseEvents');evObj.initEvent('mouseover', true, false); arguments[0].dispatchEvent(evObj);} else if(document.createEventObject) { arguments[0].fireEvent('onmouseover');}";

                ((JavascriptExecutor) Driver.get()).executeScript(mouseOverScript,
                        HoverElement);

            } else {
                System.out.println("Element was not visible to hover " + "\n");

            }
        } catch (StaleElementReferenceException e) {
            System.out.println("Element with " + HoverElement
                    + "is not attached to the page document"
                    + e.getStackTrace());
        } catch (NoSuchElementException e) {
            System.out.println("Element " + HoverElement + " was not found in DOM"
                    + e.getStackTrace());
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error occurred while hovering"
                    + e.getStackTrace());
        }
    }

    public static boolean isElementPresent(WebElement element) {
        boolean flag = false;
        try {
            if (element.isDisplayed()
                    || element.isEnabled())
                flag = true;
        } catch (NoSuchElementException e) {
            flag = false;
        } catch (StaleElementReferenceException e) {
            flag = false;
        }
        return flag;
    }

    public static String[] saveDateTime(){
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        Date date = cal.getTime();

        String todaysdate = dateFormat.format(date);
        String[] dateAndTime = todaysdate.split(" ");

        System.out.println("Today's date : " + todaysdate);
        return dateAndTime;
    }

    public static String getDate(){
        //String[] date = saveDateTime()[0].split("/");
        return saveDateTime()[0];
    }

    public static String getTime(){
       // String[] time = saveDateTime()[1].split(":");
        return saveDateTime()[1];
    }

    public static String setDate(String date){
        SimpleDateFormat fromUser = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat myFormat = new SimpleDateFormat("dd.MM.yyyy");
        String reformattedStr="";

        try {

            reformattedStr = myFormat.format(fromUser.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return reformattedStr;
    }

    public static String parsDateTime(String inputDate) {
        String outputDate ="";
        try {
            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            SimpleDateFormat outputFormat = new SimpleDateFormat("EEE, MMM d, yyyy HH:mm", new Locale("tr", "TR"));

            Date date = inputFormat.parse(inputDate);
            outputDate = outputFormat.format(date);

            System.out.println(outputDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return  outputDate;
    }

    public static String parsDate(String inputDate) {
        String outputDate ="";
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        inputFormat.setTimeZone(TimeZone.getTimeZone("UTC"));

        SimpleDateFormat outputFormat = new SimpleDateFormat("dd.MM.yyyy");

        try {
            // Tarihi çözümle
            Date date = inputFormat.parse(inputDate);

            // Yeni biçime dönüştür
            outputDate = outputFormat.format(date);
            System.out.println("Çevrilen tarih: " + outputDate);
        } catch (ParseException e) {
            System.out.println("Tarih çözümlenemedi: " + e.getMessage());
            e.printStackTrace();
        }
        return  outputDate;
    }

    public static List<String> createList(List<WebElement> elements){
        List<String> list= new ArrayList<>();
        for (WebElement boxNameElement : elements) {
            list.add(boxNameElement.getText());
        }
        return list;
    }

    public static List<String> createListForPassengerInfo(List<WebElement> elements){
        List<String> list= new ArrayList<>();
        for (WebElement boxNameElement : elements) {
            list.add(boxNameElement.getAttribute("data-maska-value"));
        }
        return list;
    }

    public static List<String> collectInfoResultPage(WebElement... element) {
        List<String> data = new ArrayList<>();
        for (WebElement webElement : element) {
            data.add(webElement.getText());
        }
        return data;
    }

    public static ArrayList<String> createArrayList(List<WebElement> elements){
        ArrayList<String> list= new ArrayList<>();
        for (WebElement boxNameElement : elements) {
            list.add(boxNameElement.getText());
        }
        return list;
    }

    public static LocalDateTime rollingUpHalfHour(){
        LocalDateTime time = LocalDateTime.now();

        LocalDateTime lastHalfUp = time.truncatedTo(ChronoUnit.HOURS)
                .plusMinutes(30+(30 * (time.getMinute() / 30)));
//        LocalDateTime lastHalf = time.truncatedTo(ChronoUnit.HOURS)//Aşağı yuvarlar
//                .plusMinutes(30 * (time.getMinute() / 30));
        return lastHalfUp;
    }

    public static void switchWindowTab (int tabNumber){
        ArrayList<String> tabs = new ArrayList<String> (Driver.get().getWindowHandles());
        BrowserUtils.waitFor(2);
        Driver.get().switchTo().window(tabs.get(tabNumber-1));
        BrowserUtils.waitFor(2);
    }

    public static void switchWindowTab (){
        String originalWindow = Driver.get().getWindowHandle();
        ArrayList<String> tabs = new ArrayList<String> (Driver.get().getWindowHandles());
        BrowserUtils.waitFor(2);
        for (String windowHandle : Driver.get().getWindowHandles()) {
            if(!originalWindow.contentEquals(windowHandle)) {
                Driver.get().switchTo().window(windowHandle);
                break;
            }
        }
    }



    public static String getNumberFromMonthName(String monthName, Locale locale){
        DecimalFormat formatter = new DecimalFormat("00");
        DateTimeFormatter dtFormatter = DateTimeFormatter.ofPattern("MMM")
                .withLocale(locale);
        TemporalAccessor temporalAccessor = dtFormatter.parse(monthName.substring(0,3));

        return formatter.format(temporalAccessor.get(ChronoField.MONTH_OF_YEAR));
    }

    public static String convertTwoDecimal(String number){
        if(number.length()==1){
            DecimalFormat formatter = new DecimalFormat("00");
            return formatter.format(number);
        }else{
            return number;
        }

    }

    public static void clearWithJS(WebElement element){
        JavascriptExecutor js = (JavascriptExecutor)Driver.get();
        js.executeScript("arguments[0].value = '';", element);
    }

    public static void refresh(){
        Driver.get().navigate().refresh();
        BrowserUtils.waitFor(1);
    }

    public static void clearAndSendKeys(WebElement element, String data){
        JavascriptExecutor js = (JavascriptExecutor)Driver.get();
        js.executeScript("arguments[0].value = '';", element);
        element.sendKeys(data);
    }

    public static double roundingFullUpDouble(Double d){
        //BigDecimal bd = new BigDecimal(d);
        BigDecimal bd = new BigDecimal(Double.toString(d));

        bd = bd.setScale(2, RoundingMode.CEILING); //437.044 ->437.05 yapması için CEILING
        System.out.println(bd.doubleValue());
        return bd.doubleValue();
    }

    public static double roundingUpDouble(Double d){
        //BigDecimal bd = new BigDecimal(d);
        BigDecimal bd = new BigDecimal(Double.toString(d));

        bd = bd.setScale(2, RoundingMode.HALF_UP); //437.044 ->437.04 yapması için CEILING
        System.out.println(bd.doubleValue());
        return bd.doubleValue();
    }

    /**
     * FEB -> 02
     */
    public static int mountConvertToNumber(String month){
        DateTimeFormatter parser = DateTimeFormatter.ofPattern("MMM")
                .withLocale(Locale.ENGLISH);
        TemporalAccessor accessor = parser.parse(month);
        System.out.println(accessor.get(ChronoField.MONTH_OF_YEAR));
        return accessor.get(ChronoField.MONTH_OF_YEAR);
    }

    public static void isSeenElement(WebElement element) {
        BrowserUtils.scrollToElement(element);
        BrowserUtils.waitFor(1);
        Assert.assertTrue(element.isDisplayed());
    }

    public static void isSeenListElement(List<WebElement> Locator) {
        for (int i = 0; i < Locator.size(); i++) {
            Assert.assertTrue(Locator.get(i).isDisplayed());
        }
    }

    public static boolean containsDigit(String text) {
        for (char c : text.toCharArray()) {
            if (Character.isDigit(c)) {
                return true;
            }
        }
        return false;
    }

    public static void clickButtonWithText(String text) {
        BrowserUtils.waitHalfSecond();
        WebElement element=Driver.get().findElement(By.xpath("//*[text()='"+text+"']"));
        BrowserUtils.clickWithJS(element);
    }

    public static void clickButtonWithCmsKey(String text) {

        BrowserUtils.waitHalfSecond();
        WebElement element=Driver.get().findElement(By.xpath("//*[@cms-key='"+text+"']"));
        BrowserUtils.clickWithJS(element);
        BrowserUtils.waitHalfSecond();
    }

    public static String setStringCurrency(String str){
        Locale locale = new Locale("tr", "TR"); // Türkçe format için "tr_TR" kullanıldı
        NumberFormat nf = NumberFormat.getInstance(locale);
        Number number = null;

        try {
            number = nf.parse(str);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        System.out.println("number = " + number);
        return number.toString().replace(".","");
    }

    public static double setDoubleCurrency(String str){
        if(str.contains(".")){
            Locale locale = new Locale("tr", "TR"); // Türkçe format için "tr_TR" kullanıldı
            NumberFormat nf = NumberFormat.getInstance(locale);
            //   DecimalFormat toTheFormat = new DecimalFormat("#.##");
            Number number = 0;

            if(str.equals("0")){
                return  0;
            }else{
                try {
                    number = nf.parse(str);
                    return (double) number;
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
            }
        }else{
            str=str.replace(",",".");
            System.out.println("str = " + str);
            return Double.parseDouble(str);
        }


    }

    public static double convertToDoubleWithCurrencyIcon(String inputString) {// with currency icon
        if(inputString.length()==1) {

            return 0.0;
        }else{
        inputString=inputString.substring(1);
        Locale turkishLocale = new Locale("tr", "TR");

        NumberFormat numberFormat = NumberFormat.getNumberInstance(turkishLocale);

        try {
            Number number = numberFormat.parse(inputString);
            return number.doubleValue();
        } catch (ParseException e) {
            System.out.println("String dönüştürülemedi: " + e.getMessage());
            return 0.0; // veya başka bir varsayılan değer döndürebilirsiniz
        }
        }

    }

    public static LocalDate formatDate(String date){//sıralama yapmak için date format. Büyük küçük , bugünden sonra önde gibi

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        LocalDate localDate = LocalDate.parse(date, formatter);
        return localDate;
    }

    public static void assertText(String s) {
        Assert.assertTrue(Driver.get().findElement(By.xpath("//p[text()='"+s+"']")).isDisplayed());
    }

    public static void assertTextAbsent(String s) {
        Assert.assertEquals(Driver.get().findElements(By.xpath("//p[text()='"+s+"']")).size(),0);
    }

    public static String replaceTextPrice(String text){
        System.out.println("text1 = " + text);
        if(text.contains("."))text= text.replace(".","");
        if(text.contains(","))text= text.replace(",","");
        System.out.println("text2 = " + text);
        return text;
    }

    public static Map <String, Integer> convertMonthNumber(){
        Map<String, Integer> ayMap = new HashMap<>();
        ayMap.put("OCAK", 1);
        ayMap.put("ŞUBAT", 2);
        ayMap.put("MART", 3);
        ayMap.put("NISAN", 4);
        ayMap.put("MAYIS", 5);
        ayMap.put("HAZIRAN", 6);
        ayMap.put("TEMMUZ", 7);
        ayMap.put("AĞUSTOS", 8);
        ayMap.put("EYLÜL", 9);
        ayMap.put("EKIM", 10);
        ayMap.put("KASIM", 11);
        ayMap.put("ARALIK", 12);

        return ayMap;
    }

    public static String getMonthNow() {

        LocalDate localDate = LocalDate.now();
        Month month = localDate.getMonth();
        String monthName = month.toString();
        // Türkçe month isimleri için çeviri yapabiliriz
        switch (month) {
            case JANUARY:
                monthName = "Ocak";
                break;
            case FEBRUARY:
                monthName = "Şubat";
                break;
            case MARCH:
                monthName = "Mart";
                break;
            case APRIL:
                monthName = "Nisan";
                break;
            case MAY:
                monthName = "Mayıs";
                break;
            case JUNE:
                monthName = "Haziran";
                break;
            case JULY:
                monthName = "Temmuz";
                break;
            case AUGUST:
                monthName = "Ağustos";
                break;
            case SEPTEMBER:
                monthName = "Eylül";
                break;
            case OCTOBER:
                monthName = "Ekim";
                break;
            case NOVEMBER:
                monthName = "Kasım";
                break;
            case DECEMBER:
                monthName = "Aralık";
                break;
        }
        return monthName;
    }

    public static Map<DayOfWeek, String> weekOfDay(){

        Map<DayOfWeek, String> turkceGunIsimleri = new HashMap<>();
        turkceGunIsimleri.put(DayOfWeek.MONDAY, "Pzt");
        turkceGunIsimleri.put(DayOfWeek.TUESDAY, "Sal");
        turkceGunIsimleri.put(DayOfWeek.WEDNESDAY, "Çar");
        turkceGunIsimleri.put(DayOfWeek.THURSDAY, "Per");
        turkceGunIsimleri.put(DayOfWeek.FRIDAY, "Cum");
        turkceGunIsimleri.put(DayOfWeek.SATURDAY, "Cmt");
        turkceGunIsimleri.put(DayOfWeek.SUNDAY, "Paz");

        return turkceGunIsimleri;
    }

    public static String extractNumbersAndText(String input) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (Character.isDigit(c) || c == '.' || c== ',') {

                result.append(c);
            }
        }
        return result.toString();
    }


    public static List<MonthDay> changeDateFormatExceptYear(List<String> flightDetailsDateInfo) {

        // Ay isimlerini tanımla
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d MMMM", new Locale("tr"));

        // MonthDay listesi oluştur
        List<MonthDay> monthDayList = new ArrayList<>();

        for (String date : flightDetailsDateInfo) {
            try {
                // Gün ve ay bilgisini parse et
                MonthDay monthDay = MonthDay.parse(date.trim(), formatter);
                // Listeye ekle
                monthDayList.add(monthDay);
            } catch (DateTimeParseException e) {
                System.err.println("Tarih parse edilemedi: " + date);
            }
        }

        // Sonuçları yazdır
        System.out.println("monthDayList = " + monthDayList);
        return  monthDayList;

    }

    public static String convertENCharacter(String metin) {
        metin = metin.replace('ç', 'c');
        metin = metin.replace('ğ', 'g');
        metin = metin.replace('ı', 'i');
        metin = metin.replace('ö', 'o');
        metin = metin.replace('ş', 's');
        metin = metin.replace('ü', 'u');
        metin = metin.replace('Ç', 'C');
        metin = metin.replace('Ğ', 'G');
        metin = metin.replace('İ', 'I');
        metin = metin.replace('Ö', 'O');
        metin = metin.replace('Ş', 'S');
        metin = metin.replace('Ü', 'U');
        return metin;
    }

    public static String capitalize(String inputString) {
         // Stringin ilk harfini büyültme

        char firstLetter = inputString.charAt(0);

        // convert it to an UpperCase letter
        char capitalFirstLetter = Character.toUpperCase(firstLetter);

        return inputString.replace(inputString.charAt(0), capitalFirstLetter);
    }
    public static String normalizeText(String text) {
        // Unicode normalization kullanarak aksanları ve özel karakterleri sadeleştir
        return Normalizer.normalize(text, Normalizer.Form.NFD).replaceAll("ı", "i");
    }
}