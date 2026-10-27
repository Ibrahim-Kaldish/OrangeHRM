package utils;

import io.qameta.allure.Allure;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class ScreenshotUtil {

    private static final Logger log = LogManager.getLogger(ScreenshotUtil.class);

    /**
     * Takes a screenshot of the current page.
     *
     * @param driver the driver to capture from
     * @return the screenshot file, or null if the driver itself is null
     */
    public static File takeScreenshot(WebDriver driver) {
        if (driver == null) {
            log.warn("⚠️ Cannot take screenshot: driver is null");
            return null;
        }

        log.info("📸 Taking screenshot");
        try {
            File image = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            log.info("✅ Screenshot saved: {}", image.getAbsolutePath());
            return image;
        } catch (ClassCastException e) {
            log.error("❌ Driver {} does not support screenshots", driver.getClass().getSimpleName(), e);
            throw e;
        } catch (WebDriverException e) {
            log.error("❌ Failed to take screenshot", e);
            throw e;
        }
    }

    public static void saveFailureScreenshot(WebDriver driver, String testName) {
        File captured = ScreenshotUtil.takeScreenshot(driver);
        if (captured == null) {
            log.warn("⚠️ No screenshot captured for {}", testName);
            return;
        }

        Path target = Paths.get("target", "screenshots", testName + "_" + System.currentTimeMillis() + ".png");
        try {
            Files.createDirectories(target.getParent());
            Files.copy(captured.toPath(), target, StandardCopyOption.REPLACE_EXISTING);
            Allure.addAttachment(testName, "image/png", new ByteArrayInputStream(Files.readAllBytes(target)), "png");
            log.info("📸 Failure screenshot saved to {}", target.toAbsolutePath());
        } catch (IOException e) {
            throw new UncheckedIOException("Could not save failure screenshot for " + testName, e);
        }
    }

}