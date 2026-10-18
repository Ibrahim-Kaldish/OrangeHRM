package pages.addUser;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import pages.BasePage;
import java.util.List;

public class AddUserPage extends BasePage {
    private static final Logger log = LogManager.getLogger(AddUserPage.class);

    // Locators
    private static final String fieldGroup = "//div[contains(@class,'oxd-input-group')][.//label[starts-with(normalize-space(.),'%s')]]";
    private static final By addUserHeading = By.xpath("//h6[normalize-space(.)='Add User']");
    private static final By userRoleDropdown = By.xpath(String.format(fieldGroup, "User Role") + "//div[contains(@class,'oxd-select-text')]");
    private static final By statusDropdown = By.xpath(String.format(fieldGroup, "Status") + "//div[contains(@class,'oxd-select-text')]");
    private static final By employeeNameInput = By.xpath("//input[@placeholder='Type for hints...']");
    private static final By employeeSuggestions = By.xpath("//div[contains(@class,'oxd-autocomplete-dropdown')]//div[@role='option']");
    private static final By usernameInput = By.xpath(String.format(fieldGroup, "Username") + "//input");
    private static final By passwordInput = By.xpath(String.format(fieldGroup, "Password") + "//input");
    private static final By confirmPasswordInput = By.xpath(String.format(fieldGroup, "Confirm Password") + "//input");
    private static final By dropdownOptions = By.xpath("//div[@role='listbox']//div[@role='option']");
    private static final By saveButton = By.xpath("//button[@type='submit' and normalize-space(.)='Save']");

    public AddUserPage(WebDriver driver) {
        super(driver);
    }

    // Getters
    public WebElement getAddUserHeading() {
        return findElement(addUserHeading);
    }
    public List<WebElement> getDropdownOptions() {
        return findElements(dropdownOptions);
    }
    public WebElement getSaveButton() {
        return findElement(saveButton);
    }
    public boolean isPageDisplayed() {
        return getAddUserHeading().isDisplayed();
    }

    // Actions
    public void enterUsername(String username) {
        typeInto(usernameInput, username);
    }
    public void enterPassword(String password) {
        typeInto(passwordInput, password);
    }
    public void enterConfirmPassword(String password) {
        typeInto(confirmPasswordInput, password);
    }
    public void selectUserRole(String role) {
        selectFromDropdown(userRoleDropdown, role);
    }
    public void selectStatus(String status) {
        selectFromDropdown(statusDropdown, status);
    }
    public void selectEmployee(String employeeName) {
        WebElement input = findElement(employeeNameInput);
        input.clear();
        input.sendKeys(employeeName);

        waitForScreen(2);

        findElement(employeeSuggestions).click();
        log.info("👤 Selected employee {}", employeeName);
    }
    public void clickSave() {
        getSaveButton().click();
        log.info("💾 Clicked Save");
    }
    private void selectFromDropdown(By dropdownLocator, String optionText) {
        findElement(dropdownLocator).click();
        for (WebElement option : getDropdownOptions()) {
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