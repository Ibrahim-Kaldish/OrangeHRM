package pages.PIMPage;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.BasePage;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

public class PIMPage extends BasePage {
    Logger log = LogManager.getLogger(PIMPage.class);

    private final By PIMTitleLocator = By.className("oxd-topbar-header-breadcrumb");
    private final By addEmployeeLocator = By.linkText("Add Employee");

    private final By employmentStatusLocator = By.xpath("//label[normalize-space()='Employment Status']/parent::div/following-sibling::div//div[contains(@class,'oxd-select-text--active')]");
    private final By includeLocator = By.xpath("//label[normalize-space()='Include']/parent::div/following-sibling::div//div[contains(@class,'oxd-select-text--active')]");
    private final By jobTitleLocator = By.xpath("//label[normalize-space()='Job Title']/parent::div/following-sibling::div//div[contains(@class,'oxd-select-text--active')]");
    private final By subUnitLocator = By.xpath("//label[normalize-space()='Sub Unit']/parent::div/following-sibling::div//div[contains(@class,'oxd-select-text--active')]");
    private final By employeeNameLocator = By.xpath("//label[normalize-space()='Employee Name']/parent::div/following-sibling::div//input");
    private final By employeeIdLocator = By.xpath("//label[normalize-space()='Employee Id']/parent::div/following-sibling::div//input");
    private final By supervisorNameLocator = By.xpath("//label[normalize-space()='Supervisor Name']/parent::div/following-sibling::div//input");
    private final By searchBtnLocator = By.xpath("//button[normalize-space()='Search']");
    private final By resetBtnLocator  = By.xpath("//button[normalize-space()='Reset']");
    private final By addButtonLocator = By.xpath("//button[normalize-space()='Add']");
    private static final By resultRows = By.xpath("//div[contains(@class,'orangehrm-employee-list')]//div[contains(@class,'oxd-table-row--clickable')]");
    private static final By recordsFoundMessage = By.xpath("//span[contains(normalize-space(.),'Record Found')]");
    private static final By noRecordsMessage = By.xpath("//span[normalize-space(.)='No Records Found']");

    public PIMPage(WebDriver driver) {
        super(driver);
    }

    public WebElement getPIMTitle(){
        return findElement(PIMTitleLocator);
    }

    // Getters
    public WebElement getAddEmployeeLink(){
        return findElement(addEmployeeLocator);
    }

    public WebElement getEmployeeName() {
        return findElement(employeeNameLocator);
    }

    public WebElement getEmployeeId() {
        return findElement(employeeIdLocator);
    }

    public WebElement getSupervisorName() {
        return findElement(supervisorNameLocator);
    }

    public WebElement getEmploymentStatus() {
        return findElement(employmentStatusLocator);
    }

    public WebElement getInclude() {
        return findElement(includeLocator);
    }

    public WebElement getJobTitle() {
        return findElement(jobTitleLocator);
    }

    public WebElement getSubUnit() {
        return findElement(subUnitLocator);
    }

    public WebElement getSearchButton() {
        return findElement(searchBtnLocator);
    }

    public WebElement getResetButton() {
        return findElement(resetBtnLocator);
    }

    public WebElement getAddButton() {
        return findElement(addButtonLocator);
    }

    // Input fields
    public void enterEmployeeName(String name) {
        typeInto(employeeNameLocator, name);
    }

    public void enterEmployeeId(String id) {
        typeInto(employeeIdLocator, id);
    }

    public void enterSupervisorName(String name) {
        typeInto(supervisorNameLocator, name);
    }

    // Buttons
    public void clickAddEmployeeLink() {
        getAddEmployeeLink().click();
    }

    public void clickSearch() {
        getSearchButton().click();
    }

    public void clickReset() {
        getResetButton().click();
    }

    public void clickAdd() {
        getAddButton().click();
    }

    public void selectFromDropdown(String label, String value) {
        By dropdown = By.xpath("//label[normalize-space()='" + label + "']/parent::div/following-sibling::div//div[contains(@class,'oxd-select-text--active')]");
        By option = By.xpath("//div[@role='listbox']//span[normalize-space()='" + value + "']");

        findElement(dropdown).click();
        findElement(option).click();
    }


    public List<WebElement> getDisplayedResultRows() {
        return driver.findElements(resultRows).stream()
                .filter(WebElement::isDisplayed)
                .collect(Collectors.toList());
    }

    public int getResultCount() {
        return getDisplayedResultRows().size();
    }

    public void waitForSearchResults() {
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(d -> !getDisplayedResultRows().isEmpty() || !d.findElements(noRecordsMessage).isEmpty());
    }

    public void clickFirstResult() {
        waitForSearchResults();
        List<WebElement> rows = getDisplayedResultRows();
        if (rows.isEmpty()) {
            throw new IllegalStateException("No search results to click");
        }
        rows.get(0).click();
        log.info("🖱️ Clicked first search result");
    }

    public void clickResultByNameAndId(String firstAndMiddleName, String lastName, String employeeId) {
        waitForSearchResults();
        for (WebElement row : getDisplayedResultRows()) {
            String text = row.getText();
            boolean nameMatches = text.contains(firstAndMiddleName) && text.contains(lastName);
            boolean idMatches = text.matches("(?s).*\\bId " + java.util.regex.Pattern.quote(employeeId) + "\\b.*");
            if (nameMatches && idMatches) {
                row.click();
                log.info("🖱️ Clicked search result for {} {} (Id {})", firstAndMiddleName, lastName, employeeId);
                return;
            }
        }
        throw new IllegalStateException("No search result matches: " + firstAndMiddleName + " " + lastName + " (Id " + employeeId + ")");
    }
}