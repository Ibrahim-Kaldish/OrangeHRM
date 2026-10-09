package pages.employeeInfoPage;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.BasePage;

import java.time.Duration;
import java.util.List;

public class EmployeeInfoPage extends BasePage {
    private static final Logger log = LogManager.getLogger(EmployeeInfoPage.class);

    // Personal details locators
    private static final String fieldGroup = "//div[contains(@class,'oxd-input-group')][.//label[starts-with(normalize-space(.),'%s')]]";
    private static final By myInfoMenuItem = By.xpath("//a[contains(@class,'oxd-main-menu-item') and @href='/web/index.php/pim/viewMyDetails']");
    private static final By nationalityDropdown = By.xpath(String.format(fieldGroup, "Nationality") + "//div[contains(@class,'oxd-select-text')]");
    private static final By maritalStatusDropdown = By.xpath(String.format(fieldGroup, "Marital Status") + "//div[contains(@class,'oxd-select-text')]");
    private static final By nationalitySelectedText = By.xpath(String.format(fieldGroup, "Nationality") + "//div[contains(@class,'oxd-select-text-input')]");
    private static final By maritalStatusSelectedText = By.xpath(String.format(fieldGroup, "Marital Status") + "//div[contains(@class,'oxd-select-text-input')]");
    private static final By dropdownOptions = By.xpath("//div[@role='listbox']//div[@role='option']");
    private static final By personalDetailsSaveButton = By.xpath("//div[contains(@class,'orangehrm-card-container')][.//h6[normalize-space(.)='Personal Details']]//button[@type='submit' and contains(@class,'oxd-button--secondary') and contains(@class,'orangehrm-left-space')]");

    // Emergency contacts locators
    private static final By emergencyContactsTab = By.xpath("//a[contains(@class,'orangehrm-tabs-item') and contains(@href,'/pim/viewEmergencyContacts/') and normalize-space(.)='Emergency Contacts']");
    private static final By assignedEmergencyContactsAddButton = By.xpath("//div[contains(@class,'orangehrm-action-header')][.//h6[normalize-space(.)='Assigned Emergency Contacts']]//button[contains(@class,'oxd-button--text')]");
    private static final String emergencyCard = "//h6[normalize-space(.)='Save Emergency Contact']/ancestor::div[.//input][1]";
    private static final String emergencyFieldGroup = "//div[contains(@class,'oxd-input-group')][.//label[normalize-space(.)='%s']]";
    private static final By emergencyNameInput = By.xpath(emergencyCard + String.format(emergencyFieldGroup, "Name") + "//input");
    private static final By emergencyRelationshipInput = By.xpath(emergencyCard + String.format(emergencyFieldGroup, "Relationship") + "//input");
    private static final By emergencyHomeTelephoneInput = By.xpath(emergencyCard + String.format(emergencyFieldGroup, "Home Telephone") + "//input");
    private static final By emergencyMobileInput = By.xpath(emergencyCard + String.format(emergencyFieldGroup, "Mobile") + "//input");
    private static final By emergencyWorkTelephoneInput = By.xpath(emergencyCard + String.format(emergencyFieldGroup, "Work Telephone") + "//input");
    private static final By emergencyContactSaveButton = By.xpath(emergencyCard + "//button[@type='submit']");
    private static final By savedContactCards = By.xpath("//div[contains(@class,'oxd-table-card')]");

    // Contact details locators
    private static final By contactDetailsTab = By.xpath("//a[contains(@class,'orangehrm-tabs-item') and normalize-space(.)='Contact Details']");
    private static final String contactForm = "//form[.//label[normalize-space(.)='Street 1']]";
    private static final String contactFieldGroup = "//div[contains(concat(' ',normalize-space(@class),' '),' oxd-input-group ')][.//label[normalize-space(.)='%s']]";
    private static final By contactStreet1Input = By.xpath(contactForm + String.format(contactFieldGroup, "Street 1") + "//input");
    private static final By contactStreet2Input = By.xpath(contactForm + String.format(contactFieldGroup, "Street 2") + "//input");
    private static final By contactCityInput = By.xpath(contactForm + String.format(contactFieldGroup, "City") + "//input");
    private static final By contactStateProvinceInput = By.xpath(contactForm + String.format(contactFieldGroup, "State/Province") + "//input");
    private static final By contactZipPostalCodeInput = By.xpath(contactForm + String.format(contactFieldGroup, "Zip/Postal Code") + "//input");
    private static final By contactCountryDropdown = By.xpath(contactForm + String.format(contactFieldGroup, "Country") + "//div[contains(@class,'oxd-select-text')]");
    private static final By contactHomeTelephoneInput = By.xpath(contactForm + String.format(contactFieldGroup, "Home") + "//input");
    private static final By contactMobileInput = By.xpath(contactForm + String.format(contactFieldGroup, "Mobile") + "//input");
    private static final By contactWorkTelephoneInput = By.xpath(contactForm + String.format(contactFieldGroup, "Work") + "//input");
    private static final By contactWorkEmailInput = By.xpath(contactForm + String.format(contactFieldGroup, "Work Email") + "//input");
    private static final By contactOtherEmailInput = By.xpath(contactForm + String.format(contactFieldGroup, "Other Email") + "//input");
    private static final By contactSaveButton = By.xpath(contactForm + "//button[@type='submit']");

    // Toast
    private static final By successToast = By.xpath("//div[contains(@class,'oxd-toast')]//p[contains(@class,'oxd-text--toast-message')]");

    public EmployeeInfoPage(WebDriver driver) {
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

    public WebElement getEmergencyContactsTab() {
        return findElement(emergencyContactsTab);
    }

    public WebElement getAssignedEmergencyContactsAddButton() {
        return findElement(assignedEmergencyContactsAddButton);
    }

    public WebElement getEmergencyContactSaveButton() {
        return findElement(emergencyContactSaveButton);
    }

    public WebElement getDisplayedSavedContactCard() {
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(d -> d.findElements(savedContactCards).stream().anyMatch(WebElement::isDisplayed));
        return driver.findElements(savedContactCards).stream()
                .filter(WebElement::isDisplayed)
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("No visible saved contact card"));
    }

    public String getSavedContactCardText() {
        return getDisplayedSavedContactCard().getText();
    }

    public String getSuccessToastText() {
        return findElement(successToast).getText().trim();
    }

    public WebElement getContactStreet1Input() {
        return findElement(contactStreet1Input);
    }

    public WebElement getContactStreet2Input() {
        return findElement(contactStreet2Input);
    }

    public WebElement getContactCityInput() {
        return findElement(contactCityInput);
    }

    public WebElement getContactStateProvinceInput() {
        return findElement(contactStateProvinceInput);
    }

    public WebElement getContactZipPostalCodeInput() {
        return findElement(contactZipPostalCodeInput);
    }

    public WebElement getContactCountryDropdown() {
        return findElement(contactCountryDropdown);
    }

    public WebElement getContactHomeTelephoneInput() {
        return findElement(contactHomeTelephoneInput);
    }

    public WebElement getContactMobileInput() {
        return findElement(contactMobileInput);
    }

    public WebElement getContactWorkTelephoneInput() {
        return findElement(contactWorkTelephoneInput);
    }

    public WebElement getContactWorkEmailInput() {
        return findElement(contactWorkEmailInput);
    }

    public WebElement getContactOtherEmailInput() {
        return findElement(contactOtherEmailInput);
    }

    public WebElement getContactSaveButton() {
        return findElement(contactSaveButton);
    }

    public String getContactStreet1Value() {
        return getContactStreet1Input().getAttribute("value").trim();
    }

    public String getContactStreet2Value() {
        return getContactStreet2Input().getAttribute("value").trim();
    }

    public String getContactCityValue() {
        return getContactCityInput().getAttribute("value").trim();
    }

    public String getContactStateProvinceValue() {
        return getContactStateProvinceInput().getAttribute("value").trim();
    }

    public String getContactZipPostalCodeValue() {
        return getContactZipPostalCodeInput().getAttribute("value").trim();
    }

    public String getContactCountryText() {
        return getContactCountryDropdown().getText().trim();
    }

    public String getContactHomeTelephoneValue() {
        return getContactHomeTelephoneInput().getAttribute("value").trim();
    }

    public String getContactMobileValue() {
        return getContactMobileInput().getAttribute("value").trim();
    }

    public String getContactWorkTelephoneValue() {
        return getContactWorkTelephoneInput().getAttribute("value").trim();
    }

    public String getContactWorkEmailValue() {
        return getContactWorkEmailInput().getAttribute("value").trim();
    }

    public String getContactOtherEmailValue() {
        return getContactOtherEmailInput().getAttribute("value").trim();
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

    public void reloadPage() {
        driver.navigate().refresh();
        log.info("🔄 Reloaded page");
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

    public void clickContactDetailsTab() {
        findElement(contactDetailsTab).click();
        log.info("📑 Clicked Contact Details tab");
    }

    public void enterContactStreet1(String value) {
        typeInto(contactStreet1Input, value);
    }

    public void enterContactStreet2(String value) {
        typeInto(contactStreet2Input, value);
    }

    public void enterContactCity(String value) {
        typeInto(contactCityInput, value);
    }

    public void enterContactStateProvince(String value) {
        typeInto(contactStateProvinceInput, value);
    }

    public void enterContactZipPostalCode(String value) {
        typeInto(contactZipPostalCodeInput, value);
    }

    public void selectContactCountry(String country) {
        selectFromDropdown(getContactCountryDropdown(), country);
    }

    public void enterContactHomeTelephone(String value) {
        typeInto(contactHomeTelephoneInput, value);
    }

    public void enterContactMobile(String value) {
        typeInto(contactMobileInput, value);
    }

    public void enterContactWorkTelephone(String value) {
        typeInto(contactWorkTelephoneInput, value);
    }

    public void enterContactWorkEmail(String value) {
        typeInto(contactWorkEmailInput, value);
    }

    public void enterContactOtherEmail(String value) {
        typeInto(contactOtherEmailInput, value);
    }

    public void enterContactDetails(String street1, String street2, String city, String stateProvince,
                                    String zipPostalCode, String country, String homeTelephone,
                                    String mobile, String workTelephone, String workEmail, String otherEmail) {
        enterContactStreet1(street1);
        enterContactStreet2(street2);
        enterContactCity(city);
        enterContactStateProvince(stateProvince);
        enterContactZipPostalCode(zipPostalCode);
        selectContactCountry(country);
        enterContactHomeTelephone(homeTelephone);
        enterContactMobile(mobile);
        enterContactWorkTelephone(workTelephone);
        enterContactWorkEmail(workEmail);
        enterContactOtherEmail(otherEmail);
        log.info("🏠 Entered contact details");
    }

    public void clickContactSave() {
        getContactSaveButton().click();
        log.info("💾 Clicked Contact Details Save");
    }

    // Helpers
    private void selectFromDropdown(WebElement dropdown, String optionText) {
        dropdown.click();
        List<WebElement> options = findElements(dropdownOptions);
        for (WebElement option : options) {
            if (option.getText().trim().equals(optionText)) {
                option.click();
                log.info("🔽 Selected '{}'", optionText);
                return;
            }
        }
        log.error("❌ Option '{}' not found in dropdown", optionText);
        throw new IllegalArgumentException("Dropdown option not found: " + optionText);
    }
}