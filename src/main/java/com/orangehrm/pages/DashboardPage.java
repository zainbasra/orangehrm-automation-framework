package com.orangehrm.pages;

import com.orangehrm.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class DashboardPage extends BasePage {

    // ==================== LOCATORS ====================

    private final By dashboardHeader = By.cssSelector("h6.oxd-text--h6");
    private final By userDropdown = By.cssSelector("span.oxd-userdropdown-tab");
    private final By logoutLink = By.linkText("Logout");

    private final By adminMenu = By.xpath("//span[text()='Admin']");
    private final By pimMenu = By.xpath("//span[text()='PIM']");
    private final By leaveMenu = By.xpath("//span[text()='Leave']");
    private final By timeMenu = By.xpath("//span[text()='Time']");
    private final By recruitmentMenu = By.xpath("//span[text()='Recruitment']");

    // ==================== CONSTRUCTOR ====================

    public DashboardPage(WebDriver driver) {
        super(driver);
    }

    // ==================== VALIDATION METHODS ====================

    /**
     * Verify dashboard Loaded successfully
     * checks if dashboard header is displayed
     *
     * @return true if dashboard loaded, false otherwise
     * Real Scenario: Confirm successful login
     */

    public boolean isDashboardDisplayed() {
        waitForUrl("dashboard");
        return isDisplayed(dashboardHeader);
    }

    /**
     * Get dashboard header text
     *
     * @return Dashboard Header text
     */

    public String getDashboardHeader() {
        return getText(dashboardHeader);
    }

    /**
     * Click Admin Menu
     *
     * @return AdminPage object
     * Real Scenario: Navigate to user management
     */

    public void clickAdminMenu() {
        click(adminMenu);
        System.out.println("   ➤ Clicked Admin menu");
    }

    /**
     * Click PIM (Employee) menu
     *
     * Real scenario: Navigate to employee management
     */

    public void clickPIMMenu(){
        click(pimMenu);
        System.out.println("   ➤ Clicked PIM menu");
    }


    /**
     * Click Leave menu
     *
     * Real scenario: Navigate to leave management
     */

    public void clickLeaveMenu() {
        click(leaveMenu);
        System.out.println("   ➤ Clicked Leave menu");
    }

    // ==================== LOGOUT FUNCTIONALITY ====================

    /**
     * Perform logout
     * Clicks user dropdown and selects logout
     *
     * @return LoginPage object (after logout, user is on login page)
     * Real scenario: End of work day, secure logout
     *
     * Design Pattern: Page Object chaining
     */

    public LoginPage logout(){
        System.out.println("   ➤ Performing logout...");

        // Click user dropdown
        click(userDropdown);

        // Click logout
        click(logoutLink);

        System.out.println("   ➤ Clicked logout link");

        // After logout, user is on login page
        return new LoginPage(driver);
    }
}