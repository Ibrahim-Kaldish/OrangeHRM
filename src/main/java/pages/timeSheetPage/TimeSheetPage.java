package pages.timeSheetPage;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
    private static final Logger log = LogManager.getLogger(TimeSheetPage.class);

    // Locators
    private static final DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-dd-MM");
    private static final By dateInput = By.xpath("//div[contains(@class,'oxd-date-input')]//input");
    private static final By editButton = By.xpath("//button[contains(@class,'oxd-button--ghost') and normalize-space()='Edit']");
    private static final By submitButton = By.xpath("//button[contains(@class,'oxd-button--secondary') and normalize-space()='Submit']");
    private static final By timesheetTitle = By.xpath("//div[contains(@class,'orangehrm-timesheet-header--title')]");
    private static final By projectInput = By.xpath("//div[contains(@class,'orangehrm-timesheet-body')]//input[@placeholder='Type for hints...']");
    private static final By cocaColaOption = By.xpath("//div[@role='option']//span[contains(., 'The Coca-Cola Company')]");
    private static final By activityDropdown = By.xpath("//div[contains(@class,'orangehrm-timesheet-body')]//div[contains(concat(' ', normalize-space(@class), ' '), ' oxd-select-text ')]");
    private static final By activityListbox = By.xpath("//div[@role='listbox']");
    private static final By activityOption = By.xpath(".//div[@role='option']");
    private static final By dayInputs = By.xpath("//td[contains(@class,'orangehrm-timesheet-table-body-cell') and contains(@class,'--duration-input')]//input");
    private static final By saveButton = By.xpath("//button[@type='submit' and normalize-space()='Save']");
    private static final By savedHourCells = By.xpath("//div[contains(@class,'orangehrm-timesheet-body')]//tr[1]/td[position()>=3 and position()<=9]");
    private static final By submittedStatusLabel = By.xpath("//*[normalize-space(.)='Status: Submitted']");
    private static final By submittedCard = By.xpath("//div[contains(concat(' ', normalize-space(@class), ' '), ' oxd-table-card ')][.//*[normalize-space()='Submitted']]");
    private static final By employeeNameInput = By.xpath("//div[contains(@class,'oxd-autocomplete-text-input')]//input[@placeholder='Type for hints...']");
    private static final By viewEmployeeButton = By.xpath("//button[@type='submit' and normalize-space()='View']");
    private static final By viewProjectCell = By.xpath("//div[contains(@class,'orangehrm-timesheet-body')]//tr[1]/td[1]");
    private static final By viewActivityCell = By.xpath("//div[contains(@class,'orangehrm-timesheet-body')]//tr[1]/td[2]");
    private static final By approveButton = By.xpath("//button[contains(@class,'oxd-button--success') and normalize-space()='Approve']");
    private static final By approvedStatusLabel = By.xpath("//*[normalize-space(.)='Status: Approved']");

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
    public WebElement getSaveButton() {
        return findElement(saveButton);
    }
    public WebElement getSubmittedActionCard() {return findElement(submittedCard);}
    public WebElement getViewEmployeeButton() {return findElement(viewEmployeeButton);}
    public WebElement getApproveButton() {return findElement(approveButton);}
    public List<WebElement> getDayInputs() {
        List<WebElement> boxes = findElements(dayInputs);
        if (boxes.size() != 7) {
            log.error("❌ Expected 7 day inputs but found {}", boxes.size());
            throw new IllegalStateException("Expected 7 day boxes but found " + boxes.size());
        }
        return boxes;
    }
    public String getViewProjectName() {return findElement(viewProjectCell).getText().trim();}
    public String getViewActivityName() {return findElement(viewActivityCell).getText().trim();}
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
        log.info("🎲 Selected random activity '{}'", name);
        picked.click();
        return name;
    }
    public String getApprovalStatus() {
        String text = findElement(approvedStatusLabel).getText().trim();   // "Status: Approved"
        return text.replace("Status:", "").trim();                          // "Approved"
    }
    public List<String> enterRandomHoursForEachDay() {
        log.info("⏱️ Entering random hours for 7 days");
        Random rnd = new Random();
        List<String> entered = new ArrayList<>();
        getDayInputs();
        for (int i = 1; i <= 7; i++) {
            String time = String.format("%02d:%02d", rnd.nextInt(1, 24), rnd.nextInt(0, 60));
            typeInto(dayInput(i), time);
            entered.add(time);
        }
        log.info("✅ Entered hours for 7 days");
        return entered;
    }
    public List<String> getSavedHours() {
        return findElements(savedHourCells).stream()
                .map(e -> e.getText().trim())
                .collect(Collectors.toList());
    }
    private By dayInput(int index) {
        return By.xpath("(//td[contains(@class,'orangehrm-timesheet-table-body-cell') and contains(@class,'--duration-input')]//input)[" + index + "]");
    }

    // Actions
    public void enterTimesheetWeek(LocalDate date) {
        log.info("📅 Entering timesheet week starting {}", date);
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
        log.info("✏️ Clicking Edit timesheet");
        getEditButton().click();
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(d -> !d.getCurrentUrl().equals(before));
    }
    public void selectCocaColaProject() {
        log.info("🏢 Selecting project The Coca-Cola Company");
        typeInto(projectInput, "Coca");
        findElement(cocaColaOption).click();
    }
    public void clickSaveButton() {
        log.info("💾 Clicking Save timesheet");
        getSaveButton().click();
    }
    public void clickSubmitButton() {
        log.info("📤 Clicking Submit timesheet");
        getSubmitButton().click();
    }
    public void waitForSubmittedStatus() {
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(submittedStatusLabel));
    }
    public void enterEmployeeName(String name) {
        log.info("👤 Searching timesheets for employee {}", name);
        typeInto(employeeNameInput, name);
        By option = By.xpath("//div[@role='option']//span[normalize-space()='" + name + "']");
        findElement(option).click();
    }
    public void clickViewEmployeeButton() {getViewEmployeeButton().click();}
    public void clickApproveButton() {
        log.info("✅ Clicking Approve timesheet");
        getApproveButton().click();
    }
}