package pages.timeSheetPage;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.BasePage;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class TimeSheetPage extends BasePage {

    // Locators
    private final By dateInput = By.xpath("//div[contains(@class,'oxd-date-input')]//input");
    private final DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-dd-MM");
    private final By editButton = By.xpath("//button[contains(@class,'oxd-button--ghost') and normalize-space()='Edit']");
    private final By submitButton = By.xpath("//button[contains(@class,'oxd-button--secondary') and normalize-space()='Submit']");
    private final By timesheetTitle = By.xpath("//div[contains(@class,'orangehrm-timesheet-header--title')]");
    private final By projectInput = By.xpath("//div[contains(@class,'orangehrm-timesheet-body')]//input[@placeholder='Type for hints...']");    private final By cocaColaOption = By.xpath("//div[@role='option']//span[contains(., 'The Coca-Cola Company')]");
    private final By activityDropdown = By.xpath("//div[contains(@class,'orangehrm-timesheet-body')]//div[contains(concat(' ', normalize-space(@class), ' '), ' oxd-select-text ')]");
    private final By activityListbox = By.xpath("//div[@role='listbox']");
    private final By activityOption = By.xpath(".//div[@role='option']");
    private final By dayInputs = By.xpath("//td[contains(@class,'orangehrm-timesheet-table-body-cell') and contains(@class,'--duration-input')]//input");
    private final By saveButton = By.xpath("//button[@type='submit' and normalize-space()='Save']");
    private final By savedHourCells = By.xpath("//div[contains(@class,'orangehrm-timesheet-body')]//tr[1]/td[position()>=3 and position()<=9]");
    private static final By submittedStatusLabel = By.xpath("//*[normalize-space(.)='Status: Submitted']");
    private final By submittedCard = By.xpath("//div[contains(concat(' ', normalize-space(@class), ' '), ' oxd-table-card ')][.//*[normalize-space()='Submitted']]");
    private final By employeeNameInput = By.xpath("//div[contains(@class,'oxd-autocomplete-text-input')]//input[@placeholder='Type for hints...']");
    private final By viewEmployeeButton = By.xpath("//button[@type='submit' and normalize-space()='View']");
    private final By viewProjectCell = By.xpath("//div[contains(@class,'orangehrm-timesheet-body')]//tr[1]/td[1]");
    private final By viewActivityCell = By.xpath("//div[contains(@class,'orangehrm-timesheet-body')]//tr[1]/td[2]");
    private final By approveButton = By.xpath("//button[contains(@class,'oxd-button--success') and normalize-space()='Approve']");
    private final By approvedStatusLabel = By.xpath("//*[normalize-space(.)='Status: Approved']");

    public TimeSheetPage(WebDriver driver){
        super(driver);
    }

    // Getters
    public WebElement getDateInput() {
        return findElement(dateInput);
    }
    public WebElement getEditButton() {
        return findElement(editButton);
    }
    public WebElement getSubmitButton() {
        return findElement(submitButton);
    }
    public List<WebElement> getDayInputs() {
        List<WebElement> boxes = findElements(dayInputs);
        if (boxes.size() != 7) {
            throw new IllegalStateException("Expected 7 day boxes but found " + boxes.size());
        }
        return boxes;
    }
    public WebElement getSaveButton() {
        return findElement(saveButton);
    }
    public WebElement getSubmittedActionCard() {return findElement(submittedCard);}
    public WebElement getViewEmployeeButton() {return findElement(viewEmployeeButton);}
    public String getViewProjectName() {return findElement(viewProjectCell).getText().trim();}
    public String getViewActivityName() {return findElement(viewActivityCell).getText().trim();}
    public List<String> enterRandomHoursForEachDay() {
        Random rnd = new Random();
        List<String> entered = new ArrayList<>();
        getDayInputs();
        for (int i = 1; i <= 7; i++) {
            String time = String.format("%02d:%02d", rnd.nextInt(1, 24), rnd.nextInt(0, 60));
            typeInto(dayInput(i), time);
            entered.add(time);
        }
        return entered;
    }
    public List<String> getSavedHours() {
        return findElements(savedHourCells).stream()
                .map(e -> e.getText().trim())
                .collect(Collectors.toList());
    }
    public WebElement getApproveButton() {return findElement(approveButton);}
    private By dayInput(int index) {
        return By.xpath("(//td[contains(@class,'orangehrm-timesheet-table-body-cell') and contains(@class,'--duration-input')]//input)[" + index + "]");
    }
    public String selectRandomActivity() {
        findElement(activityDropdown).click();

        WebElement listbox = findElement(activityListbox);

        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(d -> listbox.findElements(activityOption).size() > 1);

        List<WebElement> choices = listbox.findElements(activityOption).stream()
                .filter(e -> !e.getText().trim().equals("-- Select --"))
                .collect(Collectors.toList());

        WebElement picked = choices.get(new Random().nextInt(choices.size()));
        String name = picked.getText().trim();
        picked.click();
        return name;
    }
    public String getApprovalStatus() {
        String text = findElement(approvedStatusLabel).getText().trim();   // "Status: Approved"
        return text.replace("Status:", "").trim();                          // "Approved"
    }

    // Actions
    public void enterTimesheetWeek(LocalDate date) {
        typeInto(dateInput, date.format(fmt));
        closeCalendar();
    }
    public void enterCurrentWeek() {
        enterTimesheetWeek(LocalDate.now());
    }
    public boolean isWeekShown(int year, int month, int day) {
        final DateTimeFormatter displayFmt = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate monday = LocalDate.of(year, month, day).with(DayOfWeek.MONDAY);
        LocalDate sunday = monday.plusDays(6);
        String value = getDateInput().getAttribute("value");

        String shown = monday.format(displayFmt) + " to " + sunday.format(displayFmt);
        String stored = monday.format(fmt) + " to " + sunday.format(fmt);
        return value.equals(shown) || value.equals(stored);
    }
    public void closeCalendar() {
        new Actions(driver)
                .moveToElement(findElement(timesheetTitle))
                .click()
                .perform();
    }
    public void clickEditAndWaitForNavigation() {
        String before = driver.getCurrentUrl();
        getEditButton().click();
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(d -> !d.getCurrentUrl().equals(before));
    }
    public void selectCocaColaProject() {
        typeInto(projectInput, "Coca");
        findElement(cocaColaOption).click();
    }
    public void clickSaveButton() {
        getSaveButton().click();
    }
    public void clickSubmitButton() {
        getSubmitButton().click();
    }
    public void waitForSubmittedStatus() {
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(submittedStatusLabel));
    }
    public void enterEmployeeName(String name) {
        typeInto(employeeNameInput, name);
        By option = By.xpath("//div[@role='option']//span[normalize-space()='" + name + "']");
        findElement(option).click();
    }
    public void clickViewEmployeeButton() {getViewEmployeeButton().click();}
    public void clickApproveButton() {getApproveButton().click();}
    public void waitForApproval() {
        try {
            new WebDriverWait(driver, Duration.ofSeconds(15))
                    .until(ExpectedConditions.visibilityOfElementLocated(
                            By.xpath("//*[normalize-space(.)='Status: Approved']")));
        } catch (TimeoutException e) {
            System.out.println("URL after approve: " + driver.getCurrentUrl());
            System.out.println("PAGE TEXT: " + driver.findElement(By.tagName("body")).getText());
            throw e;
        }
    }
}