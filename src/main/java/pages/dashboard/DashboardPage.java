package pages.dashboard;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import pages.BasePage;

public class DashboardPage extends BasePage {

    private static final Logger log = LogManager.getLogger(DashboardPage.class);

    private static final By DASHBOARD_TITLE = By.className("oxd-topbar-header-breadcrumb");
    private static final By PIM_MENU_LINK = By.linkText("PIM");
    private static final By USER_DROPDOWN = By.xpath("//span[contains(@class,'oxd-userdropdown-tab')]");
    private static final By LOGOUT_LINK = By.xpath("//a[normalize-space()='Logout']");
    private static final By ADMIN_MENU_ITEM = By.cssSelector("a.oxd-main-menu-item[href='/web/index.php/admin/viewAdminModule']");
    private static final By HEADER_SECONDARY_BUTTON = By.xpath("//div[contains(@class,'orangehrm-header-container')]//button[contains(@class,'oxd-button--secondary')]");
    private static final By TIME_MENU_LINK = By.cssSelector("a[href='/web/index.php/time/viewTimeModule']");
    private static final By TIMESHEETS_SUB_MENU = By.xpath("//span[contains(@class,'oxd-topbar-body-nav-tab-item') and normalize-space(.)='Timesheets']");
    private static final By REPORTS_TAB = By.xpath("//nav[@aria-label='Topbar Menu']//a[contains(@class,'oxd-topbar-body-nav-tab-item') and normalize-space(.)='Reports']");

    public DashboardPage(WebDriver driver) {
        super(driver);
    }

    public WebElement getDashboardTitle() {
        return findElement(DASHBOARD_TITLE);
    }

    public WebElement getPimMenuLink() {
        return findElement(PIM_MENU_LINK);
    }

    public WebElement getUserDropdown() {
        return findElement(USER_DROPDOWN);
    }

    public WebElement getLogoutLink() {
        return findElement(LOGOUT_LINK);
    }

    public WebElement getAdminMenuItem() {
        return findElement(ADMIN_MENU_ITEM);
    }

    public WebElement getHeaderSecondaryButton() {
        return findElement(HEADER_SECONDARY_BUTTON);
    }

    public WebElement getTimeMenuLink() {
        return findElement(TIME_MENU_LINK);
    }

    public WebElement getReportsTab() {
        return findElement(REPORTS_TAB);
    }

    public void clickPimMenuLink() {
        getPimMenuLink().click();
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
        getTimeMenuLink().click();
        findElement(TIMESHEETS_SUB_MENU).click();
        log.info("⏱️ Opened Timesheets");
    }

    public void clickReportsTab() {
        getReportsTab().click();
        log.info("📊 Clicked Reports tab");
    }

    public void clickLogout() {
        clickUserDropdown();
        getLogoutLink().click();
    }
}