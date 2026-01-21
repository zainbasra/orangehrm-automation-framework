package com.orangehrm.base;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import java.time.Duration;


/**
 * BaseTest - Foundation class for all test classes
 * Handles WebDriver lifecycle and common configurations
 *
 * @author Zain Ul Rehman
 * @project OrangeHRM Automation Framework
 * @date January 2026
 */
public class BaseTest {

    protected WebDriver driver;
    protected WebDriverWait wait;

    // Application URLs
    protected static final String BASE_URL = "https://opensource-demo.orangehrmlive.com/";

    // Test Credentials
    protected static final String USERNAME = "Admin";
    protected static final String PASSWORD = "admin123";

    // Timeouts
    protected static final int IMPLICIT_WAIT = 10;
    protected static final int EXPLICIT_WAIT = 15;
    protected static final int PAGE_LOAD_TIMEOUT = 30;

    /**
     * Setup method - Runs before each test
     * Initializes WebDriver and navigates to application
     */

    @BeforeMethod
    public void Setup(){
        System.out.println("========================================");
        System.out.println("🚀 INITIALIZING ORANGEHRM TEST FRAMEWORK");
        System.out.println("========================================");

        // Setup ChromeDriver automatically
        WebDriverManager.chromedriver().setup();

        // Chrome options for stable execution
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        options.addArguments("--disabled-notification");
        options.addArguments("disable-popup-blocking");

        driver = new ChromeDriver(options);

        // Configure timeouts
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(IMPLICIT_WAIT));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(PAGE_LOAD_TIMEOUT));
        wait = new WebDriverWait(driver, Duration.ofSeconds(EXPLICIT_WAIT));

        // Navigate to application
        driver.get(BASE_URL);
        System.out.println("✅ Navigated to: " + BASE_URL);
    }

    /**
     * Teardown method - Runs after each test
     * Closes browser and cleans up resources
     */
    @AfterMethod
    public void teardown(){
        if(driver != null){
            driver.quit();
            System.out.println("❌ Browser closed");
            System.out.println("========================================\n");
        }
    }
}
