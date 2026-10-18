package pages.generateReport;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.BasePage;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class ReportGenerationPage extends BasePage {
    private static final Logger log = LogManager.getLogger(ReportGenerationPage.class);

    // Locators
    private static final By reportNameInput = By.xpath("//label[normalize-space(.)='Report Name']/following::input[1]");
    private static final String reportOptionFormat = "//div[@role='listbox']//div[@role='option'][contains(normalize-space(.),'%s')]";
    private static final By searchButton = By.xpath("//div[contains(@class,'oxd-form-actions')]//button[@type='submit' and normalize-space(.)='Search']");
    private static final By editReportButton = By.xpath("//button[.//i[contains(@class,'bi-pencil-fill')]]");
    private static final By employeeReportsTitle = By.xpath("//h5[contains(@class,'oxd-table-filter-title')]");
    private static final By selectionCriteriaDropdown = By.xpath("//div[contains(@class,'oxd-input-group')][.//label[normalize-space(.)='Selection Criteria']]//div[contains(@class,'oxd-select-text')]");
    private static final By selectionCriteriaAddButton = By.xpath("(//div[contains(@class,'orangehrm-report-criteria')][.//div[contains(@class,'oxd-select-wrapper')]]//button[contains(@class,'orangehrm-report-icon')])[1]");
    private static final By reportCard = By.xpath("//div[contains(@class,'oxd-card-table-body')]//*[contains(normalize-space(.),'Employee Contact info report')]");
    private static final By jobTitleRowDropdown = By.xpath("(//div[contains(@class,'oxd-input-group')][.//div[contains(@class,'oxd-select-text')]])[3]//div[contains(@class,'oxd-select-text')]");
    private static final By subUnitDropdown = By.xpath("(//div[contains(@class,'oxd-input-group')][.//div[contains(@class,'oxd-select-text')]])[4]//div[contains(@class,'oxd-select-text')]");
    private static final By employmentStatusDropdown = By.xpath("(//div[contains(@class,'oxd-input-group')][.//div[contains(@class,'oxd-select-text')]])[5]//div[contains(@class,'oxd-select-text')]");
    private static final By displayFieldGroupDropdown = By.xpath("//div[contains(@class,'oxd-input-group')][.//label[normalize-space(.)='Select Display Field Group']]//div[contains(@class,'oxd-select-text')]");
    private static final By displayFieldAddButton = By.xpath("(//div[contains(@class,'oxd-select-text')][ancestor::div[contains(@class,'oxd-input-group')][.//label[normalize-space(.)='Select Display Field']]]/following::button[contains(@class,'orangehrm-report-icon')])[1]");    private static final By saveButton = By.xpath("//div[contains(@class,'oxd-form-actions')]//button[@type='submit' and normalize-space(.)='Save']");
    private static final By expandTableIcon = By.xpath("//i[contains(@class,'bi-arrows-fullscreen')]");

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
    public WebElement getExpandTableIcon() { return findElement(expandTableIcon); }
    public boolean isPdfSaved(String fileName) {
        Path file = Paths.get("target", "downloads", fileName);
        if (!Files.exists(file)) {
            return false;
        }
        try (InputStream in = Files.newInputStream(file)) {
            byte[] header = in.readNBytes(4);
            return header.length == 4 && new String(header, StandardCharsets.US_ASCII).equals("%PDF");
        } catch (IOException e) {
            log.error("❌ Could not read PDF '{}'", fileName, e);
            return false;
        }
    }

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

    public void clickExpandTable() {
        getExpandTableIcon().click();
        log.info("⛶ Clicked expand table");
    }

    public void saveReportAsPdf(String fileName) {
        try {
            ChromeDriver chrome = (ChromeDriver) driver;

            Map<String, Object> params = new HashMap<>();
            params.put("printBackground", true);
            params.put("landscape", true);
            params.put("scale", 0.5);

            Map<String, Object> result = chrome.executeCdpCommand("Page.printToPDF", params);
            byte[] pdf = Base64.getDecoder().decode((String) result.get("data"));

            Path folder = Paths.get("target", "downloads");
            Files.createDirectories(folder);
            Path target = folder.resolve(fileName);
            Files.write(target, pdf);

            log.info("📄 Saved report as PDF: {}", target);
        } catch (IOException e) {
            log.error("❌ Could not save report PDF '{}'", fileName, e);
            throw new UncheckedIOException("Could not save report PDF: " + fileName, e);
        }
    }

}