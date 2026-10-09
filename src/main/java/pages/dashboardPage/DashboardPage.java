package pages.Dashboardpage;

import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import pages.BasePage;

public class DashboardPage extends BasePage {

    Logger log = org.apache.logging.log4j.LogManager.getLogger(DashboardPage.class);

    private final By dashboardTitleLocator = By.className("oxd-topbar-header-breadcrumb");
    private final By PIMLocator = By.linkText("PIM");
    private final By userDropdownLocator = By.xpath("//span[contains(@class,'oxd-userdropdown-tab')]");
    private final By logoutLocator = By.xpath("//a[normalize-space()='Logout']");
    private static final By adminMenuItem = By.cssSelector("a.oxd-main-menu-item[href='/web/index.php/admin/viewAdminModule']");
    private static final By headerSecondaryButton = By.xpath("//div[contains(@class,'orangehrm-header-container')]//button[contains(@class,'oxd-button--secondary')]");



    public DashboardPage(WebDriver driver) {super(driver);
    }


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

    public void clickLogout() {
        clickUserDropdown();
        getLogout().click();
    }

}
