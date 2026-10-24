package driverfactory;

import org.openqa.selenium.WebDriver;

public interface DriverFactory {

    static WebDriver getDriver() {
        return null;
    }
    static void quitDriver(){}
}
