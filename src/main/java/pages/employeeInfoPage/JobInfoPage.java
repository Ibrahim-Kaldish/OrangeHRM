package pages.employeeInfoPage;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import pages.BasePage;

public class JobInfoPage extends BasePage {
    private static final Logger log = LogManager.getLogger(JobInfoPage.class);

    // Locators
    private static final By jobTab = By.xpath("//a[contains(@class,'orangehrm-tabs-item') and contains(@href,'/pim/viewJobDetails/') and normalize-space(.)='Job']");
    private static final String jobFieldGroup = "//div[contains(concat(' ',normalize-space(@class),' '),' oxd-input-group ')][.//label[starts-with(normalize-space(.),'%s')]]";
    private static final By jobTitleDropdown = By.xpath(String.format(jobFieldGroup, "Job Title") + "//div[contains(@class,'oxd-select-text')]");
    private static final By subUnitDropdown = By.xpath(String.format(jobFieldGroup, "Sub Unit") + "//div[contains(@class,'oxd-select-text')]");
    private static final By employmentStatusDropdown = By.xpath(String.format(jobFieldGroup, "Employment Status") + "//div[contains(@class,'oxd-select-text')]");
    private static final By jobSaveButton = By.xpath("//form[.//label[starts-with(normalize-space(.),'Job Title')]]//div[contains(@class,'oxd-form-actions')]//button[@type='submit']");

    public JobInfoPage(WebDriver driver) {
        super(driver);
    }

    // Getters
    public WebElement getJobTab() { return findElement(jobTab); }
    public WebElement getJobTitleDropdown() { return findElement(jobTitleDropdown); }
    public WebElement getSubUnitDropdown() { return findElement(subUnitDropdown); }
    public WebElement getEmploymentStatusDropdown() { return findElement(employmentStatusDropdown); }
    public WebElement getJobSaveButton() { return findElement(jobSaveButton); }

    // Actions
    public void clickJobTab() {
        getJobTab().click();
        log.info("💼 Clicked Job tab");
    }
    public void selectJobTitle(String title) {
        selectFromDropdown(getJobTitleDropdown(), title);
    }
    public void selectSubUnit(String subUnit) {
        selectFromDropdown(getSubUnitDropdown(), subUnit);
    }
    public void selectEmploymentStatus(String status) {
        selectFromDropdown(getEmploymentStatusDropdown(), status);
    }
    public void clickJobSave() {
        getJobSaveButton().click();
        log.info("💾 Clicked Save in Job Details");
    }
}