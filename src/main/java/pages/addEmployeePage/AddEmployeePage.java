package pages.addEmployeePage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import pages.BasePage;

import java.util.List;

public class AddEmployeePage extends BasePage {
    private final By firstNameLocator = By.name("firstName");
    private final By middleNameLocator = By.name("middleName");
    private final By lastNameLocator = By.name("lastName");
    private final By employeeIdLocator = By.xpath("//label[normalize-space()='Employee Id']/parent::div/following-sibling::div//input");
    private final By photoUploadLocator = By.xpath("//input[@type='file']");
    private final By createLoginDetailsLocator = By.xpath("//div[contains(@class,'user-form-header')]//span[contains(@class,'oxd-switch-input')]");
    private final By createLoginCheckboxLocator = By.xpath("//div[contains(@class,'user-form-header')]//input[@type='checkbox']");
    private final By usernameLocator = By.xpath("//label[normalize-space()='Username']/parent::div/following-sibling::div//input");
    private final By passwordLocator = By.xpath("//label[normalize-space()='Password']/parent::div/following-sibling::div//input");
    private final By confirmPasswordLocator = By.xpath("//label[normalize-space()='Confirm Password']/parent::div/following-sibling::div//input");

    private final By enabledRadioLocator = By.xpath("//label[normalize-space()='Enabled']");
    private final By disabledRadioLocator = By.xpath("//label[normalize-space()='Disabled']");
    private final By enabledRadioInputLocator = By.xpath("//label[normalize-space()='Enabled']//input[@type='radio']");
    private final By disabledRadioInputLocator = By.xpath("//label[normalize-space()='Disabled']//input[@type='radio']");
    private final By saveButtonLocator = By.xpath("//button[normalize-space()='Save']");
    private final By cancelButtonLocator = By.xpath("//button[normalize-space()='Cancel']");
    private final By requiredHintLocator = By.xpath("//*[normalize-space()='* Required']");

    public AddEmployeePage(WebDriver driver) {
        super(driver);
    }

    // Getters
    public WebElement getFirstName() { return findElement(firstNameLocator); }
    public WebElement getMiddleName() { return findElement(middleNameLocator); }
    public WebElement getLastName() { return findElement(lastNameLocator); }
    public WebElement getEmployeeId() { return findElement(employeeIdLocator); }
    public WebElement getPhotoUpload() { return findElement(photoUploadLocator); }
    public WebElement getCreateLoginToggle() { return findElement(createLoginDetailsLocator); }
    public WebElement getCreateLoginCheckbox() { return findElement(createLoginCheckboxLocator); }
    public WebElement getUsername() { return findElement(usernameLocator); }
    public WebElement getPassword() { return findElement(passwordLocator); }
    public WebElement getConfirmPassword() { return findElement(confirmPasswordLocator); }
    public WebElement getEnabledRadio() { return findElement(enabledRadioLocator); }
    public WebElement getDisabledRadio() { return findElement(disabledRadioLocator); }
    public WebElement getEnabledRadioInput() { return findElement(enabledRadioInputLocator); }
    public WebElement getDisabledRadioInput() { return findElement(disabledRadioInputLocator); }
    public WebElement getSaveButton() { return findElement(saveButtonLocator); }
    public WebElement getCancelButton() { return findElement(cancelButtonLocator); }
    public WebElement getRequiredHint() { return findElement(requiredHintLocator); }

    // Actions
    public void enterFirstName(String firstName) {
        getFirstName().sendKeys(firstName);
    }
    public void enterMiddleName(String middleName) {
        getMiddleName().sendKeys(middleName);
    }
    public void enterLastName(String lastName) {
        getLastName().sendKeys(lastName);
    }
    public void enterEmployeeId(String id) {
        getEmployeeId().sendKeys(id);
    }
    public void uploadPhoto(String absolutePath) {
        getPhotoUpload().sendKeys(absolutePath);
    }
    public void clickCreateLoginDetails() {
        getCreateLoginToggle().click();
    }

    public void enterUsername(String username) {
        getUsername().sendKeys(username);
    }

    public void enterPassword(String password) {
        getPassword().sendKeys(password);
    }

    public void enterConfirmPassword(String password) {
        getConfirmPassword().sendKeys(password);
    }

    public void selectEnabled() {
        getEnabledRadio().click();
    }

    public void selectDisabled() {
        getDisabledRadio().click();
    }

    public void clickSave() {
        getSaveButton().click();
    }

    public void clickCancel() {
        getCancelButton().click();
    }
}

