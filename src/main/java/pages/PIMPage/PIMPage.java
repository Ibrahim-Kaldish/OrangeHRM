package pages.PIMPage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import pages.BasePage;

import java.util.List;

public class PIMPage extends BasePage {
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
        getEmployeeName().sendKeys(name);
    }

    public void enterEmployeeId(String id) {
        getEmployeeId().sendKeys(id);
    }

    public void enterSupervisorName(String name) {
        getSupervisorName().sendKeys(name);
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
       findElement((option)).click();
    }

}

