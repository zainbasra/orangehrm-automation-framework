package com.orangehrm.tests;

import com.orangehrm.base.BaseTest;
import com.orangehrm.pages.LoginPage;
import com.orangehrm.pages.DashboardPage;
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
 * 6. Session Security: Access After Logout
 * 7. Password case sensitivity
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

    private LoginPage loginPage;
    private DashboardPage dashboardPage;

    /**
     * Test Case 1: Successful login with valid credentials
     * Business Context:
     * - Most common scenario (99% of daily logins)
     * - Broken login = complete system downtime = revenue loss
     * - HR can't process payroll = employee complaints
     */
    @Test(priority = 1, description = "Valid Login - Happy Flow")
    public void testValidLogin() {
        System.out.println("\n🧪 TEST 1: Valid Login");

        // Initialize page object
        loginPage = new LoginPage(driver);

        // Perform login using page object methods
        dashboardPage = loginPage.login(USERNAME,PASSWORD);

        // Verify Dashboard loaded
        Assert.assertTrue(dashboardPage.isDashboardDisplayed(),
                "Dashboard did not load after login!");

        System.out.println("   ✅ TEST PASSED: Login successful");
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

    @Test(priority = 2, description = "Invalid Password")
    public void testInvalidPassword() {
        System.out.println("\n🧪 TEST 2: Invalid Password");

        loginPage = new LoginPage(driver);

        // Step-by-step login (for scenarios needing verification at each step)
        loginPage.enterUsername(USERNAME);
        loginPage.enterPassword("Wrongpassword1234");
        loginPage.clickLoginButton();

        // Verify error message using page object method
        Assert.assertTrue(loginPage.isErrorMessageDisplayed(),
                "Error message not displayed!");

        String errorText = loginPage.getErrorMessage();
        Assert.assertTrue(errorText.contains("Invalid credentials"),
                "Unexpected error message: " + errorText);

        System.out.println("   ✅ TEST PASSED: Invalid login prevented");
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

        loginPage = new LoginPage(driver);

        // Click login without entering credentials
        loginPage.clickLoginButton();

        // Verify validation message
        String validationMessage = loginPage.getRequiredFieldError();
        Assert.assertTrue(validationMessage.contains("Required"),
                "Required Validation not displayed!");

        System.out.println("   ✅ TEST PASSED: Empty field validation working");
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

        loginPage = new LoginPage(driver);

        loginPage.enterUsername("Adim123");
        loginPage.enterPassword(PASSWORD);
        loginPage.clickLoginButton();

        String errorText = loginPage.getErrorMessage();
        Assert.assertTrue(errorText.contains("Invalid credentials"),
                "Unexpected error: " + errorText);

        System.out.println("   ✅ TEST PASSED: Generic error prevents username enumeration");
    }

    /**
     * Test Case 5: Logout Functionality
     *
     * Business Coverage:
     * - Session Management
     * - Compliance requirement (shared computers)
     * - Security: logout must work reliably
     */
    @Test(priority = 5, description = "Logout Functionality Test")
    public void testLogout() {
        System.out.println("\n🧪 TEST 5: Logout");

        loginPage = new LoginPage(driver);

        // Login and get dashboard page
        dashboardPage = loginPage.login(USERNAME, PASSWORD);
        Assert.assertTrue(dashboardPage.isDashboardDisplayed(),
                "Dashboard not loaded!");

        // Verify back on login page
        loginPage = dashboardPage.logout();

        Assert.assertTrue(loginPage.isLoginPageDisplayed(),
                "Not redirected to login page after logout!");

        System.out.println("   ✅ TEST PASSED: Logout successful");
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
    @Test(priority = 6, description = "Session Security: Access After Logout")
    public void testAccessDashboardAfterLogout() {
        System.out.println("\n🧪 TEST 6: Access Dashboard After Logout");

        loginPage = new LoginPage(driver);

        // Login
        dashboardPage = loginPage.login(USERNAME, PASSWORD);
        String dashboardUrl = dashboardPage.getPageUrl();
        System.out.println("   ➤ Dashboard URL: " + dashboardUrl);

        // Logout
        loginPage = dashboardPage.logout();

        // Try accessing dashboard directly
        driver.get(dashboardUrl);

        // Verify redirected to login (session invalid)
        Assert.assertTrue(loginPage.isLoginPageDisplayed(),
                "Security vulnerability! Dashboard accessible after logout");

        System.out.println("   ✅ TEST PASSED: Session properly invalidated");
    }

    /**
     * Test Case 7: Password case sensitivity
     *
     * Expected: Validation error
     */
    @Test(priority = 7, description = "Password case sensitivity")
    public void testCaseSensitive() {
        System.out.println("\n🧪 TEST 7: Password case sensitivity");

        loginPage = new LoginPage(driver);

        // Login with uppercase password (should fail)
        loginPage.enterUsername(USERNAME);
        loginPage.enterPassword("ADMIN123");
        loginPage.clickLoginButton();

        // Verify error
        Assert.assertTrue(loginPage.isErrorMessageDisplayed(),
                "Error not displayed for wrong case password!");

        String errorText = loginPage.getErrorMessage();
        Assert.assertTrue(errorText.contains("Invalid credentials"),
                "Unexpected error: " + errorText);

        System.out.println("   ✅ TEST PASSED: Password is case-sensitive");

    }
}
