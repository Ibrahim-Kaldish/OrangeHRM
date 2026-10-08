package pages.addEmployeeTest;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;
import pages.Dashboardpage.DashboardPage;
import pages.PIMPage.PIMPage;
import pages.addEmployeePage.AddEmployeePage;
import pages.baseTest.BaseTest;
import pages.loginPage.LoginPage;

import java.util.Map;


public class AddEmployeeTest extends BaseTest {
    @Test
    public void addEmployee() throws InterruptedException{

        LoginPage loginPage = new LoginPage(driver);
        DashboardPage dashboardPage = new DashboardPage(driver);
        PIMPage pimPage = new PIMPage(driver);
        AddEmployeePage addEmployeePage = new AddEmployeePage(driver);

        Map<String, String> admin = (Map<String, String>) jsonFileManagerUsers.getValueByKey("admin");

        loginPage.enterUsername(admin.get("username"));
        loginPage.enterPassword(admin.get("password"));
        loginPage.clickLogin();

        softAssert.assertTrue(driver.getCurrentUrl().equals(jsonFileManagerUrls.getValueByKey("dashboard")));
        softAssert.assertTrue(dashboardPage.getDashboardTitle().isDisplayed());
        softAssert.assertAll();

        dashboardPage.clickPIMBtn();
        softAssert.assertTrue(driver.getCurrentUrl().equals(jsonFileManagerUrls.getValueByKey("PIM")));
        softAssert.assertTrue(pimPage.getPIMTitle().isDisplayed());

        pimPage.clickAddEmployeeLink();
        softAssert.assertTrue(driver.getCurrentUrl().equals(jsonFileManagerUrls.getValueByKey("addEmployee")));

        Map<String, String> employee = (Map<String, String>) jsonFileManagerUsers.getValueByKey("employee");

        addEmployeePage.enterFirstName(employee.get("firstName"));
        addEmployeePage.enterMiddleName(employee.get("middleName"));
        addEmployeePage.enterLastName(employee.get("lastName"));
        addEmployeePage.enterEmployeeId(employee.get("employeeId"));

        addEmployeePage.clickCreateLoginDetails();
        addEmployeePage.enterUsername(employee.get("username"));
        addEmployeePage.selectEnabled();
        WebElement enabled = driver.findElement(By.xpath("//label[normalize-space()='Enabled']//input[@type='radio']"));

        softAssert.assertTrue(enabled.isSelected());

        addEmployeePage.enterPassword(employee.get("password"));
        addEmployeePage.enterConfirmPassword(employee.get("password"));
        addEmployeePage.clickSave();
        Thread.sleep(3000);

        dashboardPage.clickLogout();
        softAssert.assertTrue(driver.getCurrentUrl().equals(jsonFileManagerUrls.getValueByKey("url")));

        Map<String, String> user = (Map<String, String>) jsonFileManagerUsers.getValueByKey("user");

        loginPage.enterUsername(user.get("username"));
        loginPage.enterPassword(user.get("password"));
        loginPage.clickLogin();


        softAssert.assertTrue(driver.getCurrentUrl().equals(jsonFileManagerUrls.getValueByKey("dashboard")));
        softAssert.assertTrue(dashboardPage.getDashboardTitle().isDisplayed());
        softAssert.assertAll();
    }
}
