package com.orangehrm.pages;

import com.orangehrm.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * LoginPage - Page Object for OrangeHRM Login Page
 *
 * Responsibility: Encapsulate all login page elements and actions
 *
 * Real-World Context:
 * - Login Page is used in 90% of test scenarios
 * - By creating this class, we write login logic ONCE
 * - if login page changes, we update ONLY this file
 * - All tests automatically use the updated code
 *
 * Design Pattern: Page Object Model + Encapsulation
 *
 */

public class LoginPage extends BasePage {

    // ==================== LOCATORS ====================
    // All element locators in one place (Single Responsibility Principle)

    private final By usernameField = By.name("username");
    private final By passwordField = By.name("password");
    private final By loginButton = By.cssSelector("button[type='submit']");
    private final By errorMessage = By.cssSelector("p.oxd-alert-content-text");
    private final By requiredFieldMessage = By.cssSelector("span.oxd-input-field-error-message");

    // ==================== CONSTRUCTOR ====================
    public LoginPage (WebDriver driver) {
        super(driver); // call BasePage Constructor
    }

    // ==================== PAGE ACTIONS ====================

    /**
     * Enter username in username field
     *
     * @param username - Username to enter
     * Real scenario: HR manager entering their username
     */

    public void enterUsername(String username) {
         type(usernameField, username);
        System.out.println("   ➤ Entered username: " + username);
    }

    /**
     * Enter password in password field
     *
     * @param password - Password to enter
     * Real scenario: HR manager entering their password
     */

    public void enterPassword(String password) {
        type(passwordField, password);
        System.out.println("   ➤ Entered password");
    }

    /**
     * Click login button
     *
     * Real scenario: Submitting login credentials
     */

    public void clickLoginButton() {
        click(loginButton);
    }

    /**
     * Complete login action (high-level method)
     * Combines multiple actions into one convenient method
     *
     * @param username - Username
     * @param password - Password
     * @return DashboardPage object (we'll create this next)
     *
     * Real scenario: Quick login for tests that don't need to verify each step
     *
     * Design Pattern: Fluent Interface (method chaining)
     */

    public DashboardPage login(String username, String password) {
        System.out.println("   ➤ Performing login...");
        enterUsername(username);
        enterPassword(password);
        clickLoginButton();

        // After successful login, user is on Dashboard
        // So we return DashboardPage object (Page Object chaining)
        return new DashboardPage(driver);
    }

    // ==================== VALIDATION METHODS ====================
    /**
     * Get error message text (for invalid login scenarios)
     *
     * @return Error message text
     * Real scenario: Verify "Invalid credentials" appears
     */

    public String getErrorMessage() {
        String errorText = getText(errorMessage);
        System.out.println("   ➤ Error message: " + errorText);
        return errorText;
    }

    /**
     * Check if error message is displayed
     *
     * @return true if error visible, false otherwise
     */

    public boolean isErrorMessageDisplayed() {
        return isDisplayed(errorMessage);
    }

    /**
     * Get required field validation message
     *
     * @return Validation message text (e.g., "Required")
     * Real scenario: Verify empty field validation
     */

    public String getRequiredFieldError() {
        return getText(requiredFieldMessage);
    }

    /**
     * Verify login page is loaded
     * Checks if username field is displayed
     *
     * @return true if login page loaded, false otherwise
     * Real scenario: Verify redirect to login after logout
     */

    public boolean isLoginPageDisplayed() {
        return isDisplayed(usernameField);
    }























}
