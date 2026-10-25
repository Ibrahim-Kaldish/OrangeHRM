package pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;

public class BasePage {
    private static final Logger log = LogManager.getLogger(BasePage.class);

    protected final WebDriver driver;
    private WebDriverWait wait;
    private static final By successToast = By.xpath("//div[contains(@class,'oxd-toast')]//p[contains(@class,'oxd-text--toast-message')]");
    private static final By loadingSpinner = By.cssSelector(".oxd-loading-spinner");
    private static final Duration SPINNER_GONE_TIMEOUT = Duration.ofSeconds(10);

    public BasePage(WebDriver driver) {
        this.driver = driver;
    }

    // Getters
    public WebElement findElement(By locator) {
        return findElement(locator, Duration.ofSeconds(10));
    }
    public WebElement findElement(By locator, Duration duration) {
        wait = new WebDriverWait(driver, duration);
        log.debug("🔎 Waiting for element: {}", locator);
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        } catch (TimeoutException e) {
            log.error("❌ Element not visible within {} seconds: {}", duration.getSeconds(), locator);
            throw e;
        }
    }
    public List<WebElement> findElements(By locator) {
        return findElements(locator, Duration.ofSeconds(10));
    }
    public List<WebElement> findElements(By locator, Duration duration) {
        wait = new WebDriverWait(driver, duration);
        log.debug("🔎 Waiting for elements: {}", locator);
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        } catch (TimeoutException e) {
            log.error("❌ Elements not visible within {} seconds: {}", duration.getSeconds(), locator);
            throw e;
        }
        List<WebElement> elements = driver.findElements(locator);
        log.debug("📦 Found {} elements for {}", elements.size(), locator);
        return elements;
    }
    public String getSuccessToastText() {
        return findElement(successToast).getText().trim();
    }

    // Actions
    public void typeInto(By locator, String text) {
        WebElement element = findElement(locator);
        element.click();
        element.sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.DELETE);

        String remaining = element.getAttribute("value");
        if (remaining != null && !remaining.isEmpty()) {
            log.error("❌ Field {} still contains '{}' after clearing", locator, remaining);
            throw new IllegalStateException("Field did not clear: " + locator);
        }

        element.sendKeys(text);
        log.info("⌨️ Cleared and entered text into {}", locator);
    }
    public void selectFromDropdown(WebElement dropdown, String optionText) {
        dropdown.click();
        final By dropdownOptions = By.xpath("//div[@role='listbox']//div[@role='option']");
        List<WebElement> options = findElements(dropdownOptions);
        for (WebElement option : options) {
            if (option.getText().trim().equals(optionText)) {
                option.click();
                log.info("🔽 Selected '{}'", optionText);
                return;
            }
        }
        log.error("❌ Option '{}' not found. Found {} options:", optionText, options.size());
        for (WebElement option : options) {
            log.error("   - '{}'", option.getText().trim());
        }
        throw new IllegalArgumentException("Dropdown option not found: " + optionText);
    }

    public void waitForScreen(int appearSeconds) {
        boolean appeared = true;
        try {
            new WebDriverWait(driver, Duration.ofSeconds(appearSeconds))
                    .until(ExpectedConditions.visibilityOfElementLocated(loadingSpinner));
            log.debug("⏳ Loading spinner appeared");
        } catch (TimeoutException e) {
            appeared = false;
            log.debug("No loading spinner appeared within {}s, assuming page already loaded", appearSeconds);
        }

        try {
            new WebDriverWait(driver, SPINNER_GONE_TIMEOUT)
                    .until(ExpectedConditions.invisibilityOfElementLocated(loadingSpinner));
        } catch (TimeoutException e) {
            String msg = String.format(
                    "Loading spinner still visible after %ds (spinner seen: %s) at URL: %s",
                    SPINNER_GONE_TIMEOUT.getSeconds(), appeared, driver.getCurrentUrl());
            log.error("❌ {}", msg);
            throw new TimeoutException(msg, e);
        }
    }

    public void waitForScreen() {
        waitForScreen(1);
    }
}