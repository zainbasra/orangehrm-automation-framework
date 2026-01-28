package com.orangehrm.tests;

import com.orangehrm.base.BaseTest;
import com.orangehrm.pages.DashboardPage;
import com.orangehrm.pages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * DashboardTests - Validates dashboard functionality
 */

public class DashboardTests extends BaseTest {

    private LoginPage loginPage;
    private DashboardPage dashboardPage;

    @BeforeMethod
    public void loginBeforeEachTest() {
        // Login before each test (dashboard tests need authenticated user)
        loginPage = new LoginPage(driver);
        dashboardPage = loginPage.login(USERNAME, PASSWORD);
    }

    /**
     * Test 1: Verify dashboard header is displayed
     */
    @Test(priority = 1, description = "Verify DashBoard Header")
    public void testDashboardHeader() {
        System.out.println("\n🧪 TEST: Dashboard Header");

        String headerText = dashboardPage.getDashboardHeader();
        System.out.println("    Dashboard Header Text: " + headerText);

        Assert.assertTrue(headerText.contains("Dashboard"),
                "Dashboard deader not displayed!");

        System.out.println("   ✅ TEST PASSED: Dashboard header verified");
    }


    /**
     * Test 2: Verify Admin menu is clickable
     *
     * YOUR TASK:
     * 1. Click Admin menu using dashboardPage.clickAdminMenu()
     * 2. Verify URL changes (contains "viewSystemUsers")
     * 3. Use getCurrentUrl() and assertions
     */
    @Test(priority = 2, description = "Verify Admin Menu Navigation")
    public void testAdminMenuNavigation() {
        System.out.println("\n🧪 TEST: Admin Menu Navigation");

        dashboardPage.clickAdminMenu();

        String currentUrl = dashboardPage.getPageUrl();
        System.out.println("   Current URL: " + currentUrl);

        Assert.assertTrue(currentUrl.contains("viewSystemUsers"),
                "Admin page url is incorrect!");

        System.out.println("   ✅ TEST PASSED: Admin menu navigation works");
    }


    /**
     * Test 3: Verify PIM menu is clickable
     *
     * YOUR TASK: Similar to Test 2, but for PIM menu
     * URL should contain "viewEmployeeList"
     */

    @Test(priority = 3, description = "Verify PIM Menu Navigation")
    public void testPIMMenuNavigation() {
        System.out.println("\n🧪 TEST: PIM Menu Navigation");

        dashboardPage.clickPIMMenu();

        String currentUrl = dashboardPage.getPageUrl();
        System.out.println("   Current URL: " + currentUrl);

        Assert.assertTrue(currentUrl.contains("viewEmployeeList"),
                "PIM Menu page url is incorrect");

        System.out.println("   ✅ TEST PASSED: PIM menu navigation works");
    }
}
