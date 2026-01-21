package com.orangehrm.tests;

import com.orangehrm.base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;


/**
 * LoginTests - Validates OrangeHRM login functionality
 *
 * Test Coverages:
 * 1. Valid login credentials
 * 2. Invalid password
 * 3. Invalid username
 * 4. Empty credentials
 * 5. Logout functionality
 *
 * Real-World Impact:
 * - Login is the gateway to all HR operation
 * - Security-critical: Wrong implementation = data breach
 * - Failed login = employees can't access payroll, time-off
 * - Average company: 500+ login attempts daily
 *
 * @author Zain Ul Rehman
 */

public class LoginTests extends BaseTest {

    /**
     * Test Case 1: Successful login with valid credentials
     * <p>
     * Business Context:
     * - Most common scenario (99% of daily logins)
     * - Broken login = complete system downtime = revenue loss
     * - HR can't process payroll = employee complaints
     * <p>
     * Steps
     * 1. Enter valid username
     * 2. Enter valid password
     * 3. Click login button
     * 4. Verify dashboard loads
     * 5. Verify user profile name displayed
     * 6. Session Security: Access After Logout
     * 7. Password case sensitivity
     */
    @Test(priority = 1, description = "Valid Login Test - happy Path")
    public void testValidLogin() {
        System.out.println("\n🧪 TEST 1: Valid Login");
        System.out.println("    Scenario: HR Manager logging in for morning shift");

        try {
            // Step 1: Wait for login page to load
            WebElement usernameField = wait.until(
                    ExpectedConditions.presenceOfElementLocated(
                            By.name("username")
                    )
            );
            System.out.println("   ➤ Login page loaded successfully");


            // Step 2: Enter username
            usernameField.clear();
            usernameField.sendKeys(USERNAME);
            System.out.println("   ➤ Entered username: " + USERNAME);

            // Step 3: Enter password
            WebElement passwordField = driver.findElement(By.name("password"));
            passwordField.clear();
            passwordField.sendKeys(PASSWORD);
            System.out.println("   ➤ Entered password");

            // Step 4: Click login button
            WebElement loginButton = driver.findElement(
                    By.cssSelector("button[type='submit']")
            );
            loginButton.click();
            System.out.println("   ➤ Clicked login button");

            // Step 5: Verify dashboard loads (URL changes)
            wait.until(ExpectedConditions.urlContains("dashboard"));
            String currentUrl = driver.getCurrentUrl();
            Assert.assertTrue(currentUrl.contains("dashboard"),
                    "Login failed! Dashboard did not load. Current URL: " + currentUrl);
            System.out.println("   ✅ Dashboard loaded: " + currentUrl);

            // Step 6: Verify dashboard Header
            WebElement dashboardHeader = wait.until(
                    ExpectedConditions.presenceOfElementLocated(
                            By.cssSelector("h6.oxd-text--h6")
                    )
            );
            Assert.assertTrue(dashboardHeader.isDisplayed(),
                    "Dashboard header not displayed!");
            System.out.println("   ✅ Dashboard header verified");

            System.out.println("   ✅ TEST PASSED: Login successful");
            System.out.println("   💡 Real impact: User can now access HR functions");

        } catch (Exception e) {
            System.out.println("   ❌ TEST FAILED: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Test Case 2: Login with invalid password
     *
     * Business Context:
     * - Security Validation (prevent authorized access)
     * - Users forget passwords = help desk tickets
     * - Error message must be clear for UX
     *
     * Expected: Error message "Invalid credentials" displayed
     */
    @Test(priority = 2, description = "Invalid Password Test")
    public void testInvalidPassword() {
        System.out.println("\n🧪 TEST 2: Invalid Password");
        System.out.println("    Scenario: User enters wrong password");

        try {
            // Enter valid username
            WebElement usernameField = wait.until(
                    ExpectedConditions.presenceOfElementLocated(By.name("username"))
            );
            usernameField.sendKeys(USERNAME);
            System.out.println("   ➤ Entered username: " + USERNAME);

            // Enter Wrong password
            WebElement passwordField = driver.findElement(By.name("password"));
            passwordField.sendKeys("WrongPassword123");
            System.out.println("   ➤ Entered wrong password");

            // Click login
            driver.findElement(By.cssSelector("button[type='submit']")).click();
            System.out.println("   ➤ Clicked login button");

            // Verify error message appears
            WebElement errorMessage = wait.until(
                    ExpectedConditions.presenceOfElementLocated(
                            By.cssSelector("p.oxd-alert-content-text")
                    )
            );

            String errorText = errorMessage.getText();
            Assert.assertTrue(errorText.contains("Invalid credentials"),
                    "Expected error message not displayed! Got: " + errorText);

            System.out.println("   ✅ Error message displayed: " + errorText);
            System.out.println("   ✅ TEST PASSED: Invalid login prevented");
            System.out.println("   💡 Security working: Unauthorized access blocked");


        } catch (Exception e) {
            System.out.println("   ❌ TEST FAILED: " + e.getMessage());
            throw e;
        }
    }

    /**
     *  Test Case 3: Login With Empty Credentials
     *
     *  Business Context
     *  - Client-side validation
     *  - Prevents unnecessary server calls
     *  - Better UX (instant feedback)
     *
     *  Expected: "Required" message under both fields
     */
    @Test(priority = 3, description = "Empty Credentials Test")
    public void testEmptyCredentials() {
        System.out.println("\n🧪 TEST 3: Empty Credentials");
        System.out.println("    Scenario: User clicks login without entering data");

        try {
            // Click login button directly (no credentials entered)
            WebElement loginButton = wait.until(
                    ExpectedConditions.elementToBeClickable(
                            By.cssSelector("button[type='submit']")
                    )
            );
            loginButton.click();
            System.out.println("   ➤ Clicked login with empty fields");

            // Verify "Required" message appears
            WebElement requiredMessage = wait.until(
                    ExpectedConditions.presenceOfElementLocated(
                            By.cssSelector("span.oxd-input-field-error-message")
                    )
            );

            String messageText = requiredMessage.getText();
            Assert.assertTrue(messageText.contains("Required"),
                    "Required validation message not displayed! Got " + messageText);

            System.out.println("   ✅ Validation message: " + messageText);
            System.out.println("   ✅ TEST PASSED: Empty field validation working");
            System.out.println("   💡 Client-side validation prevents wasted server calls");

        } catch (Exception e) {
            System.out.println("   ❌ TEST FAILED: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Test Case 4: Login With invalid username
     *
     * Business Context:
     * - Username enumeration prevention
     * - Security best practice: Don't reveal if username exists
     *
     * Expected: Same "Invalid credentials" message (no hint)
     */
    @Test(priority = 4, description = "Invalid Username Test")
    public void testInvalidUsername() {
        System.out.println("\n🧪 TEST 4: Invalid Username");
        System.out.println("   Scenario: User enters non-existent username");

        try {
            WebElement usernameField = wait.until(
                    ExpectedConditions.presenceOfElementLocated(
                            By.name("username")
                    )
            );
            usernameField.sendKeys("InvalidUser123");
            System.out.println("   ➤ Entered invalid username");

            // Enter valid password
            driver.findElement(By.name("password")).sendKeys(PASSWORD);
            System.out.println("   ➤ Entered password");

            // Click login
            driver.findElement(By.cssSelector("button[type='submit']")).click();

            // Verify generic error (security best practice)
            WebElement errorMessage = wait.until(
                    ExpectedConditions.presenceOfElementLocated(
                            By.cssSelector("p.oxd-alert-content-text")
                    )
            );

            String errorText = errorMessage.getText();
            Assert.assertTrue(errorText.contains("Invalid credentials"),
                    "Unexpected error message! Got: " + errorText);

            System.out.println("   ✅ Generic error displayed (good security)");
            System.out.println("   ✅ TEST PASSED: No username enumeration vulnerability");
            System.out.println("   💡 Attackers can't determine if username exists");

        } catch (Exception e) {
            System.out.println("   ❌ TEST FAILED: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Test Case 5: Logout Functionality
     *
     * Business Coverage:
     * - Session Management
     * - Compliance requirement (shared computers)
     * - Security: logout must work reliably
     *
     * 1. Login successfully
     * 2. Click user profile dropdown
     * 3. Click logout
     * 4. Verify redirected to login page
     * 5. Verify cannot access dashboard after logout
     *
     */
    @Test(priority = 5, description = "Logout Functionality Test")
    public void testLogOut() {
        System.out.println("\n🧪 TEST 5: Logout Functionality");
        System.out.println("   Scenario: User finishing shift, needs to logout securely");

        try{
            performLogin();
            System.out.println("   ➤ Logged-in successfully");

            wait.until(ExpectedConditions.urlContains("dashboard"));
            System.out.println("   ➤ Dashboard loaded");

            WebElement dropDown = wait.until(
                    ExpectedConditions.elementToBeClickable(
                        By.cssSelector("span.oxd-userdropdown-tab")
            ));
            dropDown.click();
            System.out.println("   ➤ Clicked user dropdown");

            WebElement logoutLink = wait.until(
                    ExpectedConditions.elementToBeClickable(
                            By.linkText("Logout")
                    )
            );
            logoutLink.click();
            System.out.println("   ➤ Clicked logout");

            wait.until(ExpectedConditions.urlContains("auth/login"));
            String currentUrl = driver.getCurrentUrl();
            Assert.assertTrue(currentUrl.contains("auth/login"),
                    "Logout failed! Not redirected to login page. URL: " + currentUrl);

            WebElement loginForm = wait.until(
                    ExpectedConditions.presenceOfElementLocated(
                          By.name("username")
                    )
            );

            Assert.assertTrue(loginForm.isDisplayed(),
                    "Login form not displayed after logout!");

            System.out.println("   ✅ Redirected to login page: " + currentUrl);
            System.out.println("   ✅ TEST PASSED: Logout successful");
            System.out.println("   💡 Session terminated - secure logout confirmed");
            System.out.println("   💡 Compliance: Shared computer security maintained");


        } catch (Exception e) {
            System.out.println("   ❌ TEST FAILED: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Test Case 6: Session validation after logout
     *
     * Business Context:
     * - Security critical: logged-out users can't access dashboard
     * - Prevents session hijacking
     * - OWASP Top 10: Broken Authentication prevention
     *
     * Expected: Attempting to access dashboard redirects to login
     */
    @Test(priority = 6, description = "Session Secuirty: Access After Logout")
    public void testAccessDashboardAfterLogout () {
        System.out.println("\n🧪 TEST 6: Access Dashboard After Logout");
        System.out.println("   Scenario: Security test - verify session invalidation");

        try{
            performLogin();
            wait.until(ExpectedConditions.urlContains("dashboard"));
            String dashboardUrl = driver.getCurrentUrl();
            System.out.println("   ➤ Captured dashboard URL: " + dashboardUrl);

            driver.findElement(By.cssSelector("span.oxd-userdropdown-tab")).click();
            wait.until(ExpectedConditions.elementToBeClickable(
                    By.linkText("Logout")
            )).click();
            wait.until(ExpectedConditions.urlContains("auth/login"));
            System.out.println("   ➤ Logged out successfully");

            driver.get(dashboardUrl);
            System.out.println("   ➤ Attempted to access dashboard after logout");

            wait.until(ExpectedConditions.urlContains("auth/login"));
            String finalUrl = driver.getCurrentUrl();

            Assert.assertTrue(finalUrl.contains("auth/login"),
                    "Security vulnerability! Dashboard accessible after logout");

            System.out.println("   ✅ Redirected to login: " + finalUrl);
            System.out.println("   ✅ TEST PASSED: Session properly invalidated");
            System.out.println("   💡 Security confirmed: No unauthorized access post-logout");
            System.out.println("   💡 OWASP compliance: Broken Authentication prevented");


        } catch (Exception e) {
            System.out.println("   ❌ TEST FAILED: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Test Case 7: Validates passwords ARE case-sensitive
     *
     * Expected: Validation error
     */
    @Test(priority = 7, description = "Verify Case-Sensitive for Password field")
    public void testCaseSensitive(){
        System.out.println("\n🧪 TEST 7: Verify Case-Sensitive for Password Field");
        System.out.println("   Scenario: Password Field Case-Sensitive ");

        try{
            WebElement usernameField = wait.until(
                    ExpectedConditions.presenceOfElementLocated(By.name("username"))
            );
            usernameField.clear();
            usernameField.sendKeys(USERNAME);

            WebElement passwordField = driver.findElement(By.name("password"));
            passwordField.clear();
            passwordField.sendKeys("ADMIN123");

            driver.findElement(By.cssSelector("button[type='submit']")).click();

            WebElement errorMessage = wait.until(ExpectedConditions.presenceOfElementLocated(
                    By.cssSelector("p.oxd-alert-content-text")
            ));
            String errorText = errorMessage.getText();
            Assert.assertTrue(errorText.contains("Invalid credentials"),
                    "Expected error message not displayed! Got: " + errorText);

        } catch (Exception e) {
            System.out.println("   ❌ TEST FAILED: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Helper method: Perform login (reusable across tests)
     * Encapsulates login logic to avoid code duplication
     */
    private void performLogin() {
        WebElement usernameField = wait.until(
                ExpectedConditions.presenceOfElementLocated(By.name("username"))
        );
        usernameField.clear();
        usernameField.sendKeys(USERNAME);

        WebElement passwordField = driver.findElement(By.name("password"));
        passwordField.clear();
        passwordField.sendKeys(PASSWORD);

        driver.findElement(By.cssSelector("button[type='submit']")).click();
    }
}


