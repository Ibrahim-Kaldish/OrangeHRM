package pages.employeeinfo;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import pages.BasePage;

public class PersonalDetailsPage extends BasePage {
    private static final Logger log = LogManager.getLogger(PersonalDetailsPage.class);

    // Locators
    private static final String fieldGroup = "//div[contains(@class,'oxd-input-group')][.//label[starts-with(normalize-space(.),'%s')]]";
    private static final By myInfoMenuItem = By.xpath("//a[contains(@class,'oxd-main-menu-item') and @href='/web/index.php/pim/viewMyDetails']");
    private static final By nationalityDropdown = By.xpath(String.format(fieldGroup, "Nationality") + "//div[contains(@class,'oxd-select-text')]");
    private static final By maritalStatusDropdown = By.xpath(String.format(fieldGroup, "Marital Status") + "//div[contains(@class,'oxd-select-text')]");
    private static final By nationalitySelectedText = By.xpath(String.format(fieldGroup, "Nationality") + "//div[contains(@class,'oxd-select-text-input')]");
    private static final By maritalStatusSelectedText = By.xpath(String.format(fieldGroup, "Marital Status") + "//div[contains(@class,'oxd-select-text-input')]");
    private static final By personalDetailsSaveButton = By.xpath("//div[contains(@class,'orangehrm-card-container')][.//h6[normalize-space(.)='Personal Details']]//button[@type='submit' and contains(@class,'oxd-button--secondary') and contains(@class,'orangehrm-left-space')]");

    public PersonalDetailsPage(WebDriver driver) {
        super(driver);
    }

    // Getters
    public WebElement getNationalityDropdown() {
        return findElement(nationalityDropdown);
    }
    public WebElement getMaritalStatusDropdown() {
        return findElement(maritalStatusDropdown);
    }
    public WebElement getPersonalDetailsSaveButton() {
        return findElement(personalDetailsSaveButton);
    }
    public String getSelectedNationality() {
        return findElement(nationalitySelectedText).getText().trim();
    }
    public String getSelectedMaritalStatus() {
        return findElement(maritalStatusSelectedText).getText().trim();
    }

    // Actions
    public void clickMyInfoMenuItem() {
        findElement(myInfoMenuItem).click();
        log.info("👤 Clicked My Info menu");
    }
    public void selectNationality(String nationality) {
        selectFromDropdown(getNationalityDropdown(), nationality);
    }
    public void selectMaritalStatus(String maritalStatus) {
        selectFromDropdown(getMaritalStatusDropdown(), maritalStatus);
    }
    public void clickPersonalDetailsSave() {
        getPersonalDetailsSaveButton().click();
        log.info("💾 Clicked Personal Details Save");
    }
}
