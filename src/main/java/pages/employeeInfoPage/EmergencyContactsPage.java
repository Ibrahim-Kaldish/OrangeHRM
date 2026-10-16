package pages.employeeInfoPage;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.BasePage;

import java.time.Duration;

public class EmergencyContactsPage extends BasePage {
    private static final Logger log = LogManager.getLogger(EmergencyContactsPage.class);

    private static final By emergencyContactsTab = By.xpath("//a[contains(@class,'orangehrm-tabs-item') and contains(@href,'/pim/viewEmergencyContacts/') and normalize-space(.)='Emergency Contacts']");
    private static final By assignedEmergencyContactsAddButton = By.xpath("//div[contains(@class,'orangehrm-action-header')][.//h6[normalize-space(.)='Assigned Emergency Contacts']]//button[contains(@class,'oxd-button--text')]");
    private static final String emergencyCard = "//h6[normalize-space(.)='Save Emergency Contact' or normalize-space(.)='Edit Emergency Contact']/ancestor::div[.//input][1]";
    private static final String emergencyFieldGroup = "//div[contains(concat(' ',normalize-space(@class),' '),' oxd-input-group ')][.//label[starts-with(normalize-space(.),'%s')]]";
    private static final By emergencyNameInput = By.xpath(emergencyCard + String.format(emergencyFieldGroup, "Name") + "//input");
    private static final By emergencyRelationshipInput = By.xpath(emergencyCard + String.format(emergencyFieldGroup, "Relationship") + "//input");
    private static final By emergencyHomeTelephoneInput = By.xpath(emergencyCard + String.format(emergencyFieldGroup, "Home Telephone") + "//input");
    private static final By emergencyMobileInput = By.xpath(emergencyCard + String.format(emergencyFieldGroup, "Mobile") + "//input");
    private static final By emergencyWorkTelephoneInput = By.xpath(emergencyCard + String.format(emergencyFieldGroup, "Work Telephone") + "//input");
    private static final By emergencyContactSaveButton = By.xpath(emergencyCard + "//button[@type='submit']");
    private static final By savedContactCards = By.xpath("//div[contains(@class,'oxd-table-card')]");
    private static final By savedContactDeleteButton = By.xpath(".//div[contains(@class,'oxd-table-cell-actions')]//i[contains(@class,'bi-trash')]/ancestor::button");
    private static final By savedContactEditButton = By.xpath(".//div[contains(@class,'oxd-table-cell-actions')]//button[contains(@class,'oxd-icon-button')][2]");
    private static final By confirmDeleteButton = By.xpath("//div[contains(@class,'oxd-dialog-sheet')]//button[normalize-space(.)='Yes, Delete']");

    public EmergencyContactsPage(WebDriver driver) {
        super(driver);
    }

    public WebElement getEmergencyContactsTab() {
        return findElement(emergencyContactsTab);
    }

    public WebElement getAssignedEmergencyContactsAddButton() {
        return findElement(assignedEmergencyContactsAddButton);
    }

    public WebElement getEmergencyContactSaveButton() {
        return findElement(emergencyContactSaveButton);
    }

    public WebElement getSavedContactEditButton() {
        return getDisplayedSavedContactCard().findElement(savedContactEditButton);
    }

    public WebElement getSavedContactDeleteButton() {
        return getDisplayedSavedContactCard().findElement(savedContactDeleteButton);
    }

    public int getSavedContactCount() {
        return (int) findElements(savedContactCards).stream()
                .filter(WebElement::isDisplayed)
                .count();
    }

    public WebElement getDisplayedSavedContactCard() {
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(d -> d.findElements(savedContactCards).stream().anyMatch(WebElement::isDisplayed));
        return findElements(savedContactCards).stream()
                .filter(WebElement::isDisplayed)
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("No visible saved contact card"));
    }

    public String getSavedContactCardText() {
        return getDisplayedSavedContactCard().getText();
    }

    public boolean isSavedContactShown(String name) {
        return findElements(savedContactCards).stream()
                .filter(WebElement::isDisplayed)
                .anyMatch(card -> card.getText().contains(name));
    }

    public void clickEmergencyContactsTab() {
        getEmergencyContactsTab().click();
        log.info("📑 Clicked Emergency Contacts tab");
    }

    public void clickAssignedEmergencyContactsAdd() {
        getAssignedEmergencyContactsAddButton().click();
        log.info("➕ Clicked Add in Assigned Emergency Contacts");
    }

    public void enterEmergencyName(String name) {
        typeInto(emergencyNameInput, name);
    }

    public void enterEmergencyRelationship(String relationship) {
        typeInto(emergencyRelationshipInput, relationship);
    }

    public void enterEmergencyHomeTelephone(String homeTelephone) {
        typeInto(emergencyHomeTelephoneInput, homeTelephone);
    }

    public void enterEmergencyMobile(String mobile) {
        typeInto(emergencyMobileInput, mobile);
    }

    public void enterEmergencyWorkTelephone(String workTelephone) {
        typeInto(emergencyWorkTelephoneInput, workTelephone);
    }

    public void clickEmergencyContactSave() {
        getEmergencyContactSaveButton().click();
        log.info("💾 Clicked Save Emergency Contact");
    }

    public void clickEdit() {
        getSavedContactEditButton().click();
        log.info("✏️ Clicked edit on saved contact");
    }

    public void clickDelete() {
        getSavedContactDeleteButton().click();
        log.info("🗑️ Clicked delete on saved contact");
    }

    public void clickConfirmDelete() {
        findElement(confirmDeleteButton).click();
        log.info("✅ Confirmed delete");
    }
}