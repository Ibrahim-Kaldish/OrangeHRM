package pages.employeeinfo;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.BasePage;
import java.time.Duration;
import java.util.stream.Collectors;

public class DependentsPage extends BasePage {
    private static final Logger log = LogManager.getLogger(DependentsPage.class);

    // Locators
    private static final By dependentsTab = By.xpath("//a[contains(@class,'orangehrm-tabs-item') and contains(@href,'/pim/viewDependents/') and normalize-space(.)='Dependents']");
    private static final By assignedDependentsAddButton = By.xpath("//div[contains(@class,'orangehrm-action-header')][.//h6[normalize-space(.)='Assigned Dependents']]//button[contains(@class,'oxd-button--text')]");
    private static final String dependentForm = "//form[.//label[starts-with(normalize-space(.),'Relationship')]]";
    private static final String dependentFieldGroup = "//div[contains(concat(' ',normalize-space(@class),' '),' oxd-input-group ')][.//label[starts-with(normalize-space(.),'%s')]]";
    private static final By dependentNameInput = By.xpath(dependentForm + String.format(dependentFieldGroup, "Name") + "//input");
    private static final By dependentRelationshipDropdown = By.xpath(dependentForm + String.format(dependentFieldGroup, "Relationship") + "//div[contains(@class,'oxd-select-text')]");
    private static final By dependentDateOfBirthInput = By.xpath(dependentForm + String.format(dependentFieldGroup, "Date of Birth") + "//input");
    private static final By dependentSaveButton = By.xpath(dependentForm + "//button[@type='submit']");
    private static final By dependentCards = By.xpath("//div[contains(@class,'oxd-table-card')]");

    public DependentsPage(WebDriver driver) {
        super(driver);
    }

    // Getters
    public WebElement getDependentsTab() { return findElement(dependentsTab); }
    public WebElement getAssignedDependentsAddButton() { return findElement(assignedDependentsAddButton); }
    public WebElement getDependentSaveButton() { return findElement(dependentSaveButton); }
    public String getDependentCardsText() {
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(d -> !d.findElements(dependentCards).isEmpty());
        return findElements(dependentCards).stream()
                .filter(WebElement::isDisplayed)
                .map(WebElement::getText)
                .collect(Collectors.joining("\n"));
    }

    // Actions
    public void clickDependentsTab() {
        getDependentsTab().click();
        log.info("📑 Clicked Dependents tab");
    }
    public void clickAssignedDependentsAdd() {
        getAssignedDependentsAddButton().click();
        log.info("➕ Clicked Add in Assigned Dependents");
    }
    public void enterDependentName(String name) { typeInto(dependentNameInput, name); }
    public void selectDependentRelationship(String relationship) {
        selectFromDropdown(findElement(dependentRelationshipDropdown), relationship);
    }
    public void enterDependentDateOfBirth(String dateOfBirth) {
        typeInto(dependentDateOfBirthInput, dateOfBirth);
    }
    public void clickDependentSave() {
        getDependentSaveButton().click();
        log.info("💾 Clicked Save Dependent");
    }
}