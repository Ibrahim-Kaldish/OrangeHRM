package pages.loginPage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pages.BasePage;

public class LoginPage extends BasePage {
    private static final Logger log = LogManager.getLogger(LoginPage.class);

    // Locators
    private final By usernameLocator = By.name("username");
    private final By passwordLocator = By.name("password");
    private final By loginBtnLocator = By.xpath("//button[@type='submit']");
    private final By invalidCredentialsMsg = By.cssSelector("p.oxd-alert-content-text");

    public LoginPage(WebDriver driver){
        super(driver);
    }

    // Getters
    public WebElement getUsernameField() {
        return findElement(usernameLocator);
    }
    public WebElement getPasswordField() {
        return findElement(passwordLocator);
    }
    public WebElement getLoginBtn() {
        return findElement(loginBtnLocator);
    }
    public WebElement errorMessage() {
        return findElement(invalidCredentialsMsg);
    }

    // Actions
    public void enterUsername(String username){
        log.info("⌨️ Entering username");
        getUsernameField().sendKeys(username);
    }
    public void enterPassword(String password){
        log.info("🔑 Entering password (hidden)");
        getPasswordField().sendKeys(password);

    }
    public void clickLogin(){
        log.info("🔐 Clicking Login");
        getLoginBtn().click();
    }
}
