package com.gittigidiyor;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.HashMap;
import java.util.Map;

public class MainPage {
    WebDriver driver;
    private static Logger log  = Logger.getLogger(MainPage.class);
    @Before
    public void ac(){
        PropertyConfigurator.configure("path_to_log4j.properties");
        log.info("Test is starting");
        //bildirimleri ve çerezleri engellemek için
        Map<String, Object> prefs = new HashMap<String, Object>();
        prefs.put("profile.default_content_setting_values.notifications", 2);
        System.setProperty("webdriver.chrome.driver","src/test/resources/driver/chromedriver.exe");
        prefs.put("profile.default_content_settings.cookies", 2);
        ChromeOptions options = new ChromeOptions();
        options.setExperimentalOption("prefs", prefs);
        //chrome size büyütme
        options.addArguments("start-maximized");

        driver = new ChromeDriver(options);
        driver.get("https://www.gittigidiyor.com");
    }

    @After
    public void kapat(){
        driver.quit();
        log.info("Test is ending");
    }
}
