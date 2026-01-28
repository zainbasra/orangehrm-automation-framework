package com.orangehrm.base;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import  org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

/**
 * BasePage - Parent class for all page Objects
 * Contains common methods that every page will use
 *
 * Real-World Context:
 *  - Every page in your application needs to find elements, click, type
 *  - Instead of duplicating this code in 20 page classes
 *  - We write it ONE here, all page inherit it
 *
 *  Design Pattern: Inheritance + Encapsulation
 *
 * @author Zain Ul Rehman
 * @project ORANGEHRM Automation Framework
 */
public class BasePage {

    protected WebDriver driver;
    protected WebDriverWait wait;

    // Constructor - initialize when page object is created
    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }


    // ==================== PROTECTED METHODS (Internal API) ====================
    /**
     * Find element with explicit wait
     * Waits until element is present before returning it
     *
     * Why: Prevents NoSuchElementException
     * Real scenario: Page loading slowly, element appears after 2 seconds
     */
    protected WebElement findElement(By locator) {
        return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    /**
     * Click element with explicit wait
     * Waits until element is clickable before clicking
     *
     * Why: Prevents ElementNotInteractableException
     * Real scenario: Button exists but covered by loading spinner
     */

    protected void click(By locator) {
        wait.until(ExpectedConditions.presenceOfElementLocated(locator)).click();
    }

    /**
     * Type text into input field
     * Clears field first, then enters text
     *
     * Why clear first: Previous test data might remain
     * Real scenario: Username field has old data from failed test
     */

    protected void type(By locator, String text) {
        WebElement element = findElement(locator);
        element.clear();
        element.sendKeys(text);
    }

    /**
     * Get text from element
     * Waits for element to be present first
     */
    protected String getText(By locator) {
        return findElement(locator).getText();
    }

    /**
     * Check if element is displayed
     * Returns true if visible, false otherwise
     *
     * Real scenario: Verify error message appears after invalid login
     */
    protected  boolean isDisplayed(By locator) {
        try {
            return findElement(locator).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Wait for URL to contain specific text
     * Used for navigation verification
     *
     * Real scenario: After login, wait for dashboard URL
     */

    protected void waitForUrl(String urlFragment) {
        wait.until(ExpectedConditions.urlContains(urlFragment));
    }

    // ==================== PUBLIC METHODS (External API) ====================

    /**
     * Get current page URL
     * Generic method - works for any page
     *
     * @return Current page URL
     */
    public String getPageUrl() {
        return driver.getCurrentUrl();
    }

    /**
     * Get current page title
     * Generic method - works for any page
     *
     * @return Current page title
     */
    public String getPageTitle() {
        return driver.getTitle();
    }
}
