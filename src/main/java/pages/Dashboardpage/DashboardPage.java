package pages.Dashboardpage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import pages.BasePage;

public class DashboardPage extends BasePage {

    private final By dashboardTitleLocator = By.className("oxd-topbar-header-breadcrumb");
    private final By PIMLocator = By.linkText("PIM");
    private final By userDropdownLocator = By.xpath("//span[contains(@class,'oxd-userdropdown-tab')]");
    private final By logoutLocator = By.xpath("//a[normalize-space()='Logout']");
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

    // Actions
    public void clickPIMBtn(){
        getPIMBtn().click();
    }

    public void clickUserDropdown() {
        getUserDropdown().click();
    }

    public void clickLogout() {
        clickUserDropdown();
        getLogout().click();
    }
}
