package pages.employeeInfoPage;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.BasePage;

import java.time.Duration;

public class ReportToPage extends BasePage {
    private static final Logger log = LogManager.getLogger(ReportToPage.class);

    private static final By reportToTab = By.xpath("//a[contains(@class,'orangehrm-tabs-item') and contains(@href,'/pim/viewReportToDetails/') and normalize-space(.)='Report-to']");
    private static final By addSupervisorButton = By.xpath("//div[contains(@class,'orangehrm-action-header')][.//h6[normalize-space(.)='Assigned Supervisors']]//button[contains(@class,'oxd-button--text')]");
    private static final String reportFieldGroup = "//div[contains(concat(' ',normalize-space(@class),' '),' oxd-input-group ')][.//label[starts-with(normalize-space(.),'%s')]]";
    private static final By supervisorNameInput = By.xpath(String.format(reportFieldGroup, "Name") + "//input");
    private static final By reportingMethodDropdown = By.xpath(String.format(reportFieldGroup, "Reporting Method") + "//div[contains(@class,'oxd-select-text')]");
    private static final String supervisorOptionFormat = "//div[@role='option'][contains(normalize-space(.),'%s')]";
    private static final By saveButton = By.xpath("//form[.//label[starts-with(normalize-space(.),'Reporting Method')]]//div[contains(@class,'oxd-form-actions')]//button[@type='submit']");
    private static final By firstSupervisorOption = By.xpath("//div[@role='listbox']//div[@role='option'][not(contains(normalize-space(.),'Searching'))][1]");

    public ReportToPage(WebDriver driver) {
        super(driver);
    }

    public WebElement getReportToTab() { return findElement(reportToTab); }
    public WebElement getAddSupervisorButton() { return findElement(addSupervisorButton); }
    public WebElement getSupervisorNameInput() { return findElement(supervisorNameInput); }
    public WebElement getReportingMethodDropdown() { return findElement(reportingMethodDropdown); }
    public WebElement getSaveButton() { return findElement(saveButton); }

    public void clickReportToTab() {
        getReportToTab().click();
        log.info("👥 Clicked Report-to tab");
    }

    public void clickAddSupervisor() {
        getAddSupervisorButton().click();
        log.info("➕ Clicked Add in Assigned Supervisors");
    }

    public void enterSupervisorName(String name) {
        typeInto(supervisorNameInput, name);
    }

    public void selectFirstSupervisor() {
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.elementToBeClickable(firstSupervisorOption))
                .click();
        log.info("👤 Selected first supervisor result");
    }

    public void selectReportingMethod(String method) {
        selectFromDropdown(getReportingMethodDropdown(), method);
    }

    public void clickSave() {
        getSaveButton().click();
        log.info("💾 Clicked Save in Report-to");
    }
}