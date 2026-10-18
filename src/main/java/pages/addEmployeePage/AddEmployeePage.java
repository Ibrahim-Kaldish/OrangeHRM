package pages.addEmployeePage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.BasePage;
import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.time.Duration;

public class AddEmployeePage extends BasePage {

    // Locators
    private static final By firstNameLocator = By.name("firstName");
    private static final By middleNameLocator = By.name("middleName");
    private static final By lastNameLocator = By.name("lastName");
    private static final By employeeIdLocator = By.xpath("//label[normalize-space()='Employee Id']/parent::div/following-sibling::div//input");
    private static final By createLoginDetailsLocator = By.xpath("//div[contains(@class,'user-form-header')]//span[contains(@class,'oxd-switch-input')]");
    private static final By usernameLocator = By.xpath("//label[normalize-space()='Username']/parent::div/following-sibling::div//input");
    private static final By passwordLocator = By.xpath("//label[normalize-space()='Password']/parent::div/following-sibling::div//input");
    private static final By confirmPasswordLocator = By.xpath("//label[normalize-space()='Confirm Password']/parent::div/following-sibling::div//input");
    private static final By enabledRadioLocator = By.xpath("//label[normalize-space()='Enabled']");
    private static final By saveButtonLocator = By.xpath("//button[normalize-space()='Save']");
    private static final By addPhotoButton = By.xpath("//button[contains(@class,'employee-image-action')]");
    private static final By photoPreview = By.xpath("//div[contains(@class,'employee-image-wrapper')]//img");

    public AddEmployeePage(WebDriver driver) {
        super(driver);
    }

    // Getters
    public WebElement getCreateLoginToggle() { return findElement(createLoginDetailsLocator); }
    public WebElement getUsername() { return findElement(usernameLocator); }
    public WebElement getPassword() { return findElement(passwordLocator); }
    public WebElement getEnabledRadio() { return findElement(enabledRadioLocator); }
    public WebElement getSaveButton() { return findElement(saveButtonLocator); }
    public WebElement getAddPhotoButton() { return findElement(addPhotoButton); }
    public String getPhotoPreviewSrc() {
        return new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(photoPreview))
                .getAttribute("src");
    }

    // Actions
    public void enterFirstName(String firstName) {
        typeInto(firstNameLocator, firstName);
    }
    public void enterMiddleName(String middleName) {
        typeInto(middleNameLocator, middleName);
    }
    public void enterLastName(String lastName) {
        typeInto(lastNameLocator, lastName);
    }
    public void enterEmployeeId(String id) {
        typeInto(employeeIdLocator, id);
    }
    public void clickAddPhoto() {
        getAddPhotoButton().click();
    }
    public void clickAddPhotoAndSelect(String absolutePath) {
        try {
            clickAddPhoto();
            Thread.sleep(1500);

            StringSelection selection = new StringSelection(absolutePath);
            Toolkit.getDefaultToolkit().getSystemClipboard().setContents(selection, null);

            Robot robot = new Robot();
            robot.keyPress(KeyEvent.VK_CONTROL);
            robot.keyPress(KeyEvent.VK_V);
            robot.keyRelease(KeyEvent.VK_V);
            robot.keyRelease(KeyEvent.VK_CONTROL);
            robot.delay(500);

            robot.keyPress(KeyEvent.VK_ENTER);
            robot.keyRelease(KeyEvent.VK_ENTER);
        } catch (AWTException e) {
            throw new IllegalStateException("Could not select photo: " + absolutePath, e);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IllegalStateException("Interrupted while selecting photo: " + absolutePath, e);
        }
    }
    public void clickCreateLoginDetails() {
        getCreateLoginToggle().click();
    }
    public void enterUsername(String username) {
        typeInto(usernameLocator, username);
    }
    public void enterPassword(String password) {
        typeInto(passwordLocator, password);
    }
    public void enterConfirmPassword(String password) {
        typeInto(confirmPasswordLocator, password);
    }
    public void selectEnabled() {
        getEnabledRadio().click();
    }
    public void clickSave() {
        getSaveButton().click();
    }

}