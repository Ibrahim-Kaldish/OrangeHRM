package pages.dashboardPage;

import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import pages.BasePage;

public class DashboardPage extends BasePage {

    Logger log = org.apache.logging.log4j.LogManager.getLogger(DashboardPage.class);

    private static final By dashboardTitleLocator = By.className("oxd-topbar-header-breadcrumb");
    private static final By PIMLocator = By.linkText("PIM");
    private static final By userDropdownLocator = By.xpath("//span[contains(@class,'oxd-userdropdown-tab')]");
    private static final By logoutLocator = By.xpath("//a[normalize-space()='Logout']");
    private static final By adminMenuItem = By.cssSelector("a.oxd-main-menu-item[href='/web/index.php/admin/viewAdminModule']");
    private static final By headerSecondaryButton = By.xpath("//div[contains(@class,'orangehrm-header-container')]//button[contains(@class,'oxd-button--secondary')]");
    private static final By timeMenu = By.cssSelector("a[href='/web/index.php/time/viewTimeModule']");
    private static final By timesheetsSubMenu = By.xpath("//span[contains(@class,'oxd-topbar-body-nav-tab-item') and normalize-space(.)='Timesheets']");

    public DashboardPage(WebDriver driver) {super(driver);}


    // Getters
    public WebElement getDashboardTitle(){
        return findElement(dashboardTitleLocator);
    }

    public WebElement getPIMBtn(){
        return findElement(PIMLocator);
    }
    public WebElement getUserDropdown() { return findElement(userDropdownLocator); }

    public WebElement getLogout() { return findElement(logoutLocator); }

    public WebElement getAdminMenuItem() {
        return findElement(adminMenuItem);
    }
    public WebElement getHeaderSecondaryButton() {
        return findElement(headerSecondaryButton);
    }
    public WebElement getTimeMenu() {return findElement(timeMenu);}

    // Actions
    public void clickPIMBtn(){
        getPIMBtn().click();
    }

    public void clickUserDropdown() {
        getUserDropdown().click();
    }

    public void clickAdminMenuItem() {
        getAdminMenuItem().click();
        log.info("📂 Clicked Admin menu");
    }
    public void clickHeaderSecondaryButton() {
        getHeaderSecondaryButton().click();
        log.info("➕ Clicked header button");
    }

    public void clickTimeMenu() {
        getTimeMenu().click();
        findElement(timesheetsSubMenu).click();
        log.info("⏱️ Opened Timesheets");
    }

    public void clickLogout() {
        clickUserDropdown();
        getLogout().click();
    }

}
