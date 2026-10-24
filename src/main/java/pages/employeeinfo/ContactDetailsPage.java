package pages.employeeinfo;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import pages.BasePage;

public class ContactDetailsPage extends BasePage {
    private static final Logger log = LogManager.getLogger(ContactDetailsPage.class);

    // Locators
    private static final String contactForm = "//form[.//label[normalize-space(.)='Street 1']]";
    private static final String contactFieldGroup = "//div[contains(concat(' ',normalize-space(@class),' '),' oxd-input-group ')][.//label[normalize-space(.)='%s']]";
    private static final By contactDetailsTab = By.xpath("//a[contains(@class,'orangehrm-tabs-item') and normalize-space(.)='Contact Details']");
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

    public ContactDetailsPage(WebDriver driver) {
        super(driver);
    }

    // Getters
    public WebElement getContactStreet1Input() { return findElement(contactStreet1Input); }
    public WebElement getContactStreet2Input() { return findElement(contactStreet2Input); }
    public WebElement getContactCityInput() { return findElement(contactCityInput); }
    public WebElement getContactStateProvinceInput() { return findElement(contactStateProvinceInput); }
    public WebElement getContactZipPostalCodeInput() { return findElement(contactZipPostalCodeInput); }
    public WebElement getContactCountryDropdown() { return findElement(contactCountryDropdown); }
    public WebElement getContactHomeTelephoneInput() { return findElement(contactHomeTelephoneInput); }
    public WebElement getContactMobileInput() { return findElement(contactMobileInput); }
    public WebElement getContactWorkTelephoneInput() { return findElement(contactWorkTelephoneInput); }
    public WebElement getContactWorkEmailInput() { return findElement(contactWorkEmailInput); }
    public WebElement getContactOtherEmailInput() { return findElement(contactOtherEmailInput); }
    public WebElement getContactSaveButton() { return findElement(contactSaveButton); }
    public String getContactStreet1Value() { return getContactStreet1Input().getAttribute("value").trim(); }
    public String getContactStreet2Value() { return getContactStreet2Input().getAttribute("value").trim(); }
    public String getContactCityValue() { return getContactCityInput().getAttribute("value").trim(); }
    public String getContactStateProvinceValue() { return getContactStateProvinceInput().getAttribute("value").trim(); }
    public String getContactZipPostalCodeValue() { return getContactZipPostalCodeInput().getAttribute("value").trim(); }
    public String getContactCountryText() { return getContactCountryDropdown().getText().trim(); }
    public String getContactHomeTelephoneValue() { return getContactHomeTelephoneInput().getAttribute("value").trim(); }
    public String getContactMobileValue() { return getContactMobileInput().getAttribute("value").trim(); }
    public String getContactWorkTelephoneValue() { return getContactWorkTelephoneInput().getAttribute("value").trim(); }
    public String getContactWorkEmailValue() { return getContactWorkEmailInput().getAttribute("value").trim(); }
    public String getContactOtherEmailValue() { return getContactOtherEmailInput().getAttribute("value").trim(); }

    // Actions
    public void clickContactDetailsTab() {
        findElement(contactDetailsTab).click();
        log.info("📑 Clicked Contact Details tab");
    }
    public void enterContactStreet1(String value) { typeInto(contactStreet1Input, value); }
    public void enterContactStreet2(String value) { typeInto(contactStreet2Input, value); }
    public void enterContactCity(String value) { typeInto(contactCityInput, value); }
    public void enterContactStateProvince(String value) { typeInto(contactStateProvinceInput, value); }
    public void enterContactZipPostalCode(String value) { typeInto(contactZipPostalCodeInput, value); }
    public void selectContactCountry(String country) { selectFromDropdown(getContactCountryDropdown(), country); }
    public void enterContactHomeTelephone(String value) { typeInto(contactHomeTelephoneInput, value); }
    public void enterContactMobile(String value) { typeInto(contactMobileInput, value); }
    public void enterContactWorkTelephone(String value) { typeInto(contactWorkTelephoneInput, value); }
    public void enterContactWorkEmail(String value) { typeInto(contactWorkEmailInput, value); }
    public void enterContactOtherEmail(String value) { typeInto(contactOtherEmailInput, value); }
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
}