package com.gittigidiyor;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

public class MainPageTest extends MainPage {

    @Test
    public void TestCase() {

        WebDriverWait wait = new WebDriverWait(driver, 10);
        String url = driver.getCurrentUrl();
        assertEquals("You are not in Home Page", url, "https://www.gittigidiyor.com/");
        //Giriş yap navbarda görünene kadar bekler ve tıklar
        WebElement navBarLogin = wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.xpath("//*[@id='main-header']"))));
        navBarLogin.click();
        //driver yönlendirme
        driver.navigate().to("https://www.gittigidiyor.com/uye-girisi");

        //Username textbox'ı için veri gönderir
        driver.findElement(By.name("kullanici")).sendKeys("markher5235@outlook.com");

        //Password textbox'ı için veri gönderir
        WebElement password = driver.findElement(By.xpath("//*[@id='L-PasswordField']"));
        password.sendKeys("markher5252");

        //Giriş Yap Butonuna Tıklar
        WebElement loginClick = wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.xpath("//*[@id='gg-login-enter']"))));
        loginClick.click();

        String url1 = driver.getCurrentUrl();
        assertEquals("You logged in unsuccessfully", url1, "https://www.gittigidiyor.com/");

        //Bilgisayar kelimesi yazılır ve aratılır.
        driver.findElement(By.name("k")).sendKeys("bilgisayar");
        WebElement searchBarClick = wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.xpath("//button[@class='qjixn8-0 sc-1bydi5r-0 hKfdXF']"))));
        searchBarClick.click();

        WebElement pageClick = wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.xpath("//*[@id=\"best-match-right\"]/div[5]/ul/li[2]/a"))));
        //scroll için sayfayı aşağı indirmek için
        driver.findElement(By.cssSelector("body")).sendKeys(Keys.CONTROL, Keys.END);
        pageClick.click();
        //2.sayfanın açıldığı kontrol ediliyor.
        String url2 = driver.getCurrentUrl();
        assertEquals("You opened 2nd page unsuccessfully", url2, "https://www.gittigidiyor.com/arama/?k=bilgisayar&sf=2");

        //Random sonuç döndürmesi için
        Random r = new Random(); //random sınıfı
        int randomProduct = r.nextInt(48);
        System.out.println("2nd Page Product index=" + randomProduct);

        WebElement productClick = wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.xpath("/html/body/div[5]/div[2]/div/div[2]/div[3]/div[2]/ul/li[" + randomProduct + "]/a"))));
        productClick.click();

        //fiyatları karşılaştırmak için
        String getHighPrice = driver.findElement(By.xpath("//*[@id='sp-price-highPrice']")).getText();
        String getLowPrice = driver.findElement(By.xpath("//*[@id='sp-price-lowPrice']")).getText();
        System.out.println("high price= " + getHighPrice);
        System.out.println("low price= " + getLowPrice);

        //ürün sepete ekleme
        WebElement addProductClick = wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.xpath("//*[@id='add-to-basket']"))));
        addProductClick.click();
        WebElement basketClick = wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.xpath("//*[contains(text(),'Sepete Git')]"))));
        basketClick.click();

        String getBasketPrice = driver.findElement(By.xpath("/html/body/div[1]/div[2]/div/div[1]/form/div/div[2]/div[3]/div/div[1]/div/div[5]/div[2]/div[3]/p")).getText();
        System.out.println("basket price= " + getBasketPrice);

        //indirimli ve indirimsiz ürünler için ayrı kontrol
        //indirimli ürün kontrolü
        if (!getHighPrice.equals("") && !getLowPrice.equals("")) {
            Assert.assertEquals(getLowPrice, getBasketPrice);
            System.out.println("Fiyatlar eşittir.");
        }//indirimsiz ürün kontrolü
        else {
            Assert.assertEquals(getHighPrice, getBasketPrice);
            System.out.println("Fiyatlar eşittir.");
        }

        //ürün adedi arttırma (select)
        Select dropdown = new Select(driver.findElement(By.xpath("html/body/div[1]/div[2]/div/div[1]/form/div/div[2]/div[2]/div[1]/div[2]/div[6]/div[2]/div[2]/div[4]/div/div[2]/select")));
        dropdown.selectByIndex(1);

        //sepetteki ürünün 2 olduğu kontrol edilir.
        String option = dropdown.getFirstSelectedOption().getText();
        Assert.assertEquals("2", option);

        driver.manage().timeouts().implicitlyWait(45, TimeUnit.SECONDS);
        //sepeti boşaltmak için
        WebElement deleteProducts = wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.xpath("/html/body/div[1]/div[2]/div/div[1]/form/div/div[2]/div[2]/div[1]/div[2]/div[6]/div[2]/div[2]/div[3]/div/div[2]/div/a[1]/i"))));
        deleteProducts.click();

        //sepetin boş olduğu kontrol edilir.
        driver.manage().timeouts().implicitlyWait(45, TimeUnit.SECONDS);
        String actualText = wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.xpath("//*[@id='empty-cart-container']/div[1]/div[1]/div/div[2]/h2")))).getText();
        Assert.assertEquals("Sepetinizde ürün bulunmamaktadır.", actualText);

    }
}







