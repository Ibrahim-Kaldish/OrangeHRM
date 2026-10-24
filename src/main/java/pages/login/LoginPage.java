package pages.login;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pages.BasePage;

public class LoginPage extends BasePage {
    private static final Logger log = LogManager.getLogger(LoginPage.class);

    // Locators
    private final By USERNAME_INPUT = By.name("username");
    private final By PASSWORD_INPUT = By.name("password");
    private final By LOGIN_BUTTON = By.xpath("//button[@type='submit']");
    private final By INVALID_CREDENTIALS_MESSAGE = By.cssSelector("p.oxd-alert-content-text");

    public LoginPage(WebDriver driver){
        super(driver);
    }

    // Getters
    public WebElement getUsernameField() {
        return findElement(USERNAME_INPUT);
    }
    public WebElement getPasswordField() {
        return findElement(PASSWORD_INPUT);
    }
    public WebElement getLoginButton() {
        return findElement(LOGIN_BUTTON);
    }
    public WebElement errorMessage() {
        return findElement(INVALID_CREDENTIALS_MESSAGE);
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
        getLoginButton().click();
    }
}
