package pages.generateReport;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.BasePage;

import java.time.Duration;

public class ReportGenerationPage extends BasePage {
    private static final Logger log = LogManager.getLogger(ReportGenerationPage.class);

    // Locators
    private static final By reportNameInput = By.xpath("//label[normalize-space(.)='Report Name']/following::input[1]");
    private static final String reportOptionFormat = "//div[@role='listbox']//div[@role='option'][contains(normalize-space(.),'%s')]";
    private static final By searchButton = By.xpath("//div[contains(@class,'oxd-form-actions')]//button[@type='submit' and normalize-space(.)='Search']");
    private static final By editReportButton = By.xpath("//button[.//i[contains(@class,'bi-pencil-fill')]]");
    private static final By employeeReportsTitle = By.xpath("//h5[contains(@class,'oxd-table-filter-title')]");
    private static final By selectionCriteriaDropdown = By.xpath("//div[contains(@class,'oxd-input-group')][.//label[normalize-space(.)='Selection Criteria']]//div[contains(@class,'oxd-select-text')]");
    private static final By selectionCriteriaAddButton = By.xpath("(//div[contains(@class,'orangehrm-report-criteria')][.//div[contains(@class,'oxd-select-wrapper')]]//button[contains(@class,'orangehrm-report-icon')])[1]");    private static final By reportCard = By.xpath("//div[contains(@class,'oxd-card-table-body')]//*[contains(normalize-space(.),'Employee Contact info report')]");
    private static final By jobTitleRowDropdown = By.xpath("(//div[contains(@class,'oxd-input-group')][.//div[contains(@class,'oxd-select-text')]])[3]//div[contains(@class,'oxd-select-text')]");
    private static final By subUnitDropdown = By.xpath("(//div[contains(@class,'oxd-input-group')][.//div[contains(@class,'oxd-select-text')]])[4]//div[contains(@class,'oxd-select-text')]");
    private static final By employmentStatusDropdown = By.xpath("(//div[contains(@class,'oxd-input-group')][.//div[contains(@class,'oxd-select-text')]])[5]//div[contains(@class,'oxd-select-text')]");
    private static final By displayFieldGroupDropdown = By.xpath("//div[contains(@class,'oxd-input-group')][.//label[normalize-space(.)='Select Display Field Group']]//div[contains(@class,'oxd-select-text')]");
    private static final By displayFieldAddButton = By.xpath("(//div[contains(@class,'oxd-select-text')][ancestor::div[contains(@class,'oxd-input-group')][.//label[normalize-space(.)='Select Display Field']]]/following::button[contains(@class,'orangehrm-report-icon')])[1]");    private static final By saveButton = By.xpath("//div[contains(@class,'oxd-form-actions')]//button[@type='submit' and normalize-space(.)='Save']");

    public ReportGenerationPage(WebDriver driver){
        super(driver);
    }

    // Getters
    public WebElement getSearchButton() { return findElement(searchButton); }
    public WebElement getEditReportButton() { return findElement(editReportButton); }
    public WebElement getEmployeeReportsTitle() { return findElement(employeeReportsTitle); }
    public WebElement getSelectionCriteriaDropdown() { return findElement(selectionCriteriaDropdown); }
    public WebElement getSelectionCriteriaAddButton() { return findElement(selectionCriteriaAddButton); }
    public WebElement getJobTitleRowDropdown() { return findElement(jobTitleRowDropdown); }
    public WebElement getSubUnitDropdown() { return findElement(subUnitDropdown); }
    public WebElement getEmploymentStatusDropdown() { return findElement(employmentStatusDropdown); }
    public WebElement getDisplayFieldDropdown() { return findElement(displayFieldGroupDropdown); }
    public WebElement getDisplayFieldAddButton() { return findElement(displayFieldAddButton); }
    public WebElement getSaveButton() { return findElement(saveButton); }

    // Actions
    public boolean isEmployeeReportsTitleDisplayed() {
        return getEmployeeReportsTitle().isDisplayed();
    }

    public void enterReportName(String name) {
        typeInto(reportNameInput, name);
    }

    public void selectReport(String name) {
        By option = By.xpath(String.format(reportOptionFormat, name));
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.elementToBeClickable(option))
                .click();
        log.info("📄 Selected report '{}'", name);
    }

    public void clickSearch() {
        getSearchButton().click();
        log.info("🔎 Clicked Search");
    }

    public void clickEditReport() {
        getEditReportButton().click();
        log.info("✏️ Clicked edit report");
    }

    public void selectSelectionCriteria(String criteria) {
        selectFromDropdown(getSelectionCriteriaDropdown(), criteria);
        log.info("🎯 Selected selection criteria '{}'", criteria);
    }

    public void clickSelectionCriteriaAdd() {
        getSelectionCriteriaAddButton().click();
        log.info("➕ Clicked + next to Selection Criteria");
    }

    public void selectJobTitleCriteria(String jobTitle) {
        selectFromDropdown(getJobTitleRowDropdown(), jobTitle);
    }

    public void selectSubUnitCriteria(String subUnit) {
        selectFromDropdown(getSubUnitDropdown(), subUnit);
    }

    public void selectEmploymentStatusCriteria(String status) {
        selectFromDropdown(getEmploymentStatusDropdown(), status);
    }
    public void selectDisplayField(String field) {
        selectFromDropdown(getDisplayFieldDropdown(), field);
        log.info("🧾 Selected display field '{}'", field);
    }

    public void clickDisplayFieldAdd() {
        getDisplayFieldAddButton().click();
        log.info("➕ Clicked + beside Select Display Field");
    }

    public void clickSave() {
        getSaveButton().click();
        log.info("💾 Clicked Save on Edit Report");
    }

}