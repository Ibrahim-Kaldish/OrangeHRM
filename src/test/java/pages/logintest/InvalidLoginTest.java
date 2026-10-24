package pages.logintest;

import dataproviders.DataProviderTest;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.basetest.BaseTest;
import pages.login.LoginPage;

@Epic("Authentication")
@Feature("Login")
public class InvalidLoginTest extends BaseTest {
    private static final Logger log = LogManager.getLogger(InvalidLoginTest.class);

    @Test(dataProvider = "invalidCredentials", dataProviderClass = DataProviderTest.class)
    @Story("User is rejected when credentials are invalid")
    @Description("As a user, I want to see an error when I sign in with invalid credentials so that I know the login failed.")
    @Severity(SeverityLevel.CRITICAL)
    public void login(String username, String password) {
        LoginPage loginPage = new LoginPage(driver);

        log.info("🔐 Attempting login with invalid credentials for {}", username);
        loginPage.enterUsername(username);
        loginPage.enterPassword(password);
        loginPage.clickLogin();

        log.info("🔍 Checking for the invalid credentials message");
        Assert.assertTrue(loginPage.errorMessage().isDisplayed());
        Assert.assertEquals(loginPage.errorMessage().getText(), "Invalid credentials");
        log.info("✅ Invalid credentials message shown for {}", username);
    }
}