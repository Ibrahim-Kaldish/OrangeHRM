package pages.loginPage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import pages.BasePage;

public class LoginPage extends BasePage {
    private final By usernameLocator = By.name("username");
    private final By passwordLocator = By.name("password");
    private final By loginBtnLocator = By.xpath("//button[@type='submit']");
    private final By invalidCredentialsMsg = By.cssSelector("p.oxd-alert-content-text");
    public LoginPage(WebDriver driver){
        super(driver);
    }

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

    public void enterUsername(String username){
        getUsernameField().sendKeys(username);
    }
    public void enterPassword(String password){
        getPasswordField().sendKeys(password);

    }
    public void clickLogin(){
        getLoginBtn().click();
    }

}
