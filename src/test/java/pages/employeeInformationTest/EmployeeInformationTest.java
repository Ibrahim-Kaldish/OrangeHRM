package pages.employeeInformationTest;

import dataProviders.DataProviderTest;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.Test;
import pages.PIMPage.PIMPage;
import pages.baseTest.BaseTest;
import pages.dashboardPage.DashboardPage;
import pages.employeeInfoPage.ContactDetailsPage;
import pages.employeeInfoPage.DependentsPage;
import pages.employeeInfoPage.EmergencyContactsPage;
import pages.employeeInfoPage.PersonalDetailsPage;
import pages.loginPage.LoginPage;

import java.util.Map;

@Epic("Employee Management")
@Feature("Employee Information")
public class EmployeeInformationTest extends BaseTest {
    private static final Logger log = LogManager.getLogger(EmployeeInformationTest.class);

    String username;
    String password;
    String nationality;
    String maritalStatus;
    String emergencyName;
    String emergencyRelationship;
    String emergencyHomeTelephone;
    String emergencyMobile;
    String emergencyWorkTelephone;
    String street1;
    String street2;
    String city;
    String stateProvince;
    String zipPostalCode;
    String country;
    String contactHomeTelephone;
    String contactMobile;
    String contactWorkTelephone;
    String workEmail;
    String otherEmail;
    String dependentName = "Lina Smith";
    String dependentRelationship = "Child";
    String dependentDateOfBirth = "2015-10-03";
    LoginPage loginPage;
    DashboardPage dashboardPage;
    PersonalDetailsPage personalDetailsPage;
    EmergencyContactsPage emergencyContactsPage;
    ContactDetailsPage contactDetailsPage;
    DependentsPage dependentsPage;
    PIMPage pimPage;

    public void personalDetailsAssert(){
        log.info("🔍 Verifying Personal Details after reload");
        softAssert.assertEquals(personalDetailsPage.getSelectedNationality(), nationality, "Nationality after reload");
        softAssert.assertEquals(personalDetailsPage.getSelectedMaritalStatus(), maritalStatus, "Marital status after reload");
    }

    public void contactDetailsAssert(){
        log.info("🔍 Verifying Contact Details after reload");
        softAssert.assertEquals(contactDetailsPage.getContactStreet1Value(), street1, "Street 1 after reload");
        softAssert.assertEquals(contactDetailsPage.getContactStreet2Value(), street2, "Street 2 after reload");
        softAssert.assertEquals(contactDetailsPage.getContactCityValue(), city, "City after reload");
        softAssert.assertEquals(contactDetailsPage.getContactStateProvinceValue(), stateProvince, "State/Province after reload");
        softAssert.assertEquals(contactDetailsPage.getContactZipPostalCodeValue(), zipPostalCode, "Zip/Postal Code after reload");
        softAssert.assertEquals(contactDetailsPage.getContactCountryText(), country, "Country after reload");
        softAssert.assertEquals(contactDetailsPage.getContactHomeTelephoneValue(), contactHomeTelephone, "Home telephone after reload");
        softAssert.assertEquals(contactDetailsPage.getContactMobileValue(), contactMobile, "Mobile after reload");
        softAssert.assertEquals(contactDetailsPage.getContactWorkTelephoneValue(), contactWorkTelephone, "Work telephone after reload");
        softAssert.assertEquals(contactDetailsPage.getContactWorkEmailValue(), workEmail, "Work email after reload");
        softAssert.assertEquals(contactDetailsPage.getContactOtherEmailValue(), otherEmail, "Other email after reload");
    }

    public void emergencyContactAssert(){
        log.info("🔍 Verifying saved emergency contact");
        String savedCard = emergencyContactsPage.getSavedContactCardText();
        softAssert.assertTrue(savedCard.contains(emergencyName), "Saved name missing: " + emergencyName);
        softAssert.assertTrue(savedCard.contains(emergencyRelationship), "Saved relationship missing: " + emergencyRelationship);
        softAssert.assertTrue(savedCard.contains(emergencyHomeTelephone), "Saved home telephone missing: " + emergencyHomeTelephone);
        softAssert.assertTrue(savedCard.contains(emergencyMobile), "Saved mobile missing: " + emergencyMobile);
        softAssert.assertTrue(savedCard.contains(emergencyWorkTelephone), "Saved work telephone missing: " + emergencyWorkTelephone);
    }

    public void dependentAssert(){
        log.info("🔍 Verifying saved dependent");
        String dependentCards = dependentsPage.getDependentCardsText();
        softAssert.assertTrue(dependentCards.contains(dependentName), "Saved dependent name missing: " + dependentName);
        softAssert.assertTrue(dependentCards.contains(dependentRelationship), "Saved dependent relationship missing: " + dependentRelationship);
    }

    public void successMessage(String msg1, String msg2){
        log.info("🔔 Checking toast: {}", msg1);
        softAssert.assertEquals(personalDetailsPage.getSuccessToastText(), msg1, msg2);
    }

    public void setAttributes(String ... employeeData){
        log.info("📋 Loading employee data for username {}", employeeData[4]);
        username = employeeData[4];
        password = employeeData[5];
        nationality = employeeData[6];
        maritalStatus = employeeData[7];
        emergencyName = employeeData[8];
        emergencyRelationship = employeeData[9];
        emergencyHomeTelephone = employeeData[10];
        emergencyMobile = employeeData[11];
        emergencyWorkTelephone = employeeData[12];
        street1 = employeeData[13];
        street2 = employeeData[14];
        city = employeeData[15];
        stateProvince = employeeData[16];
        zipPostalCode = employeeData[17];
        country = employeeData[18];
        contactHomeTelephone = employeeData[19];
        contactMobile = employeeData[20];
        contactWorkTelephone = employeeData[21];
        workEmail = employeeData[22];
        otherEmail = employeeData[23];
        loginPage = new LoginPage(driver);
        dashboardPage = new DashboardPage(driver);
        personalDetailsPage = new PersonalDetailsPage(driver);
        emergencyContactsPage = new EmergencyContactsPage(driver);
        contactDetailsPage = new ContactDetailsPage(driver);
        dependentsPage = new DependentsPage(driver);
        pimPage = new PIMPage(driver);
    }

    public void loginAsUser(){
        log.info("🔐 Logging in as employee {}", username);
        loginPage.enterUsername(username);
        loginPage.enterPassword(password);
        loginPage.clickLogin();
        softAssert.assertTrue(driver.getCurrentUrl().equals(jsonFileManagerUrls.getValueByKey("dashboard")));
        softAssert.assertTrue(dashboardPage.getDashboardTitle().isDisplayed());
        log.info("✅ Employee logged in");
    }

    public void loginAsAdmin(){
        log.info("🔐 Logging in as admin");
        Map<String, String> admin = (Map<String, String>) jsonFileManagerUsers.getValueByKey("admin");

        loginPage.enterUsername(admin.get("username"));
        loginPage.enterPassword(admin.get("password"));
        loginPage.clickLogin();

        softAssert.assertTrue(driver.getCurrentUrl().equals(jsonFileManagerUrls.getValueByKey("dashboard")));
        softAssert.assertTrue(dashboardPage.getDashboardTitle().isDisplayed());
        log.info("✅ Admin logged in");
    }

    public void navigateToMyInfo(){
        log.info("📂 Opening My Info");
        personalDetailsPage.clickMyInfoMenuItem();
    }

    public void navigateToEmergencyContacts(){
        log.info("📂 Opening Emergency Contacts tab");
        emergencyContactsPage.clickEmergencyContactsTab();
    }

    public void navigateToContactDetails(){
        log.info("📂 Opening Contact Details tab");
        contactDetailsPage.clickContactDetailsTab();
    }

    public void navigateToPIMPage(){
        log.info("📂 Opening PIM from the dashboard");
        dashboardPage.clickPIMBtn();
    }

    public void navigateToDependents(){
        log.info("📂 Opening Dependents tab");
        dependentsPage.clickDependentsTab();
    }

    public void searchForEmployee(String id){
        log.info("🔎 Searching PIM for employee Id {}", id);
        pimPage.enterEmployeeId(id);
        pimPage.clickSearch();

        pimPage.waitForSearchResults();
        softAssert.assertTrue(pimPage.getResultCount() > 0, "Search returned no results");
        pimPage.clickFirstResult();
        log.info("✅ Opened employee record for Id {}", id);
    }

    public void loginAsAdminAndSearch(String id){
        loginAsAdmin();
        navigateToPIMPage();
        searchForEmployee(id);
    }

    public void clickAddEmergency(){
        log.info("✏️ Adding emergency contact {}", emergencyName);
        emergencyContactsPage.clickAssignedEmergencyContactsAdd();
    }

    public void clickAddDependent(){
        log.info("➕ Adding dependent {}", dependentName);
        dependentsPage.clickAssignedDependentsAdd();
    }

    public void personalDetailsActions(){
        log.info("✏️ Updating Personal Details");
        personalDetailsPage.selectNationality(nationality);
        personalDetailsPage.selectMaritalStatus(maritalStatus);
        personalDetailsPage.clickPersonalDetailsSave();
    }

    public void emergencyContactActions(){
        emergencyContactsPage.enterEmergencyName(emergencyName);
        emergencyContactsPage.enterEmergencyRelationship(emergencyRelationship);
        emergencyContactsPage.enterEmergencyHomeTelephone(emergencyHomeTelephone);
        emergencyContactsPage.enterEmergencyMobile(emergencyMobile);
        emergencyContactsPage.enterEmergencyWorkTelephone(emergencyWorkTelephone);
        emergencyContactsPage.clickEmergencyContactSave();
    }

    public void contactDetailsActions(){
        log.info("✏️ Updating Contact Details");
        contactDetailsPage.enterContactDetails(street1, street2, city, stateProvince, zipPostalCode,
                country, contactHomeTelephone, contactMobile, contactWorkTelephone, workEmail, otherEmail);
        contactDetailsPage.clickContactSave();
    }

    public void dependentActions(){
        dependentsPage.enterDependentName(dependentName);
        dependentsPage.selectDependentRelationship(dependentRelationship);
        dependentsPage.enterDependentDateOfBirth(dependentDateOfBirth);
        dependentsPage.clickDependentSave();
    }

    @Test(priority = 0, dataProvider = "validEmployeesData", dataProviderClass = DataProviderTest.class)
    @Story("Employee updates personal, contact, and emergency details")
    @Description("As an employee, I want to update my personal, contact, and emergency details so that HR has current records.")
    @Severity(SeverityLevel.CRITICAL)
    public void updateEmployeeInformation(String... employeeData) throws InterruptedException {
        log.info("🚀 Starting employee information update");

        setAttributes(employeeData);

        loginAsUser();

        navigateToMyInfo();
        Thread.sleep(3000);

        personalDetailsActions();
        successMessage("Successfully Updated", "Personal Details save message");
        personalDetailsAssert();
        Thread.sleep(3000);

        navigateToEmergencyContacts();
        clickAddEmergency();
        emergencyContactActions();
        successMessage("Successfully Saved", "Emergency Contact save message");
        emergencyContactAssert();
        Thread.sleep(3000);

        navigateToContactDetails();
        contactDetailsActions();
        successMessage("Successfully Updated", "Contact Details save message");
        contactDetailsAssert();
        Thread.sleep(3000);

        log.info("🚪 Logging out employee");
        dashboardPage.clickLogout();
        softAssert.assertTrue(driver.getCurrentUrl().equals(jsonFileManagerUrls.getValueByKey("url")));

        loginAsAdminAndSearch(employeeData[3]);

        Thread.sleep(3000);
        personalDetailsAssert();

        emergencyContactsPage.clickEmergencyContactsTab();
        Thread.sleep(3000);
        emergencyContactAssert();

        contactDetailsPage.clickContactDetailsTab();
        Thread.sleep(3000);
        contactDetailsAssert();

        log.info("🏁 Employee information update finished, checking results");
        softAssert.assertAll();
    }

    @Test(priority = 1, dataProvider = "validEmployeesData", dataProviderClass = DataProviderTest.class)
    @Story("Admin edits and removes an emergency contact, then adds a dependent")
    @Description("As an admin, I want to edit and remove an employee's emergency contact and add a dependent so that the employee's family and emergency records stay accurate.")
    @Severity(SeverityLevel.CRITICAL)
    public void editOrRemoveExistingContact(String... employeeData) throws InterruptedException {
        setAttributes(employeeData);

        loginAsAdminAndSearch(employeeData[3]);

        navigateToEmergencyContacts();
        clickAddEmergency();
        emergencyContactActions();
        successMessage("Successfully Saved", "Emergency Contact save message");
        Thread.sleep(3000);
        emergencyContactAssert();

        emergencyContactsPage.clickEdit();
        emergencyContactsPage.enterEmergencyName("Emily Johnson");
        emergencyContactsPage.enterEmergencyRelationship("Spouse");
        emergencyContactsPage.enterEmergencyHomeTelephone("0223456789");
        emergencyContactsPage.enterEmergencyMobile("0111234567");
        emergencyContactsPage.enterEmergencyWorkTelephone("0987654321");
        emergencyContactsPage.clickEmergencyContactSave();
        successMessage("Successfully Updated", "Emergency Contact save message");
        Thread.sleep(3000);
        String savedCard = emergencyContactsPage.getSavedContactCardText();
        log.info("🔍 Verifying saved emergency contact");
        softAssert.assertTrue(savedCard.contains("Emily Johnson"), "Saved name missing: Emily Johnson");
        softAssert.assertTrue(savedCard.contains("Spouse"), "Saved relationship missing: Spouse");
        softAssert.assertTrue(savedCard.contains("0223456789"), "Saved home telephone missing: 0223456789");
        softAssert.assertTrue(savedCard.contains("0111234567"), "Saved mobile missing: 0111234567");
        softAssert.assertTrue(savedCard.contains("0987654321"), "Saved work telephone missing: 0987654321");


        int countBefore = emergencyContactsPage.getSavedContactCount();
        emergencyContactsPage.clickDelete();
        emergencyContactsPage.clickConfirmDelete();

        softAssert.assertEquals(personalDetailsPage.getSuccessToastText(), "Successfully Deleted", "Delete message");

        Thread.sleep(3000);
        softAssert.assertEquals(emergencyContactsPage.getSavedContactCount(), countBefore - 2, "Saved contact count after delete");
//        softAssert.assertFalse(emergencyContactsPage.isSavedContactShown(emergencyName), "Deleted contact still shown: " + emergencyName);

        navigateToDependents();
        clickAddDependent();
        dependentActions();
        successMessage("Successfully Saved", "Dependent save message");
        dependentAssert();

        dashboardPage.clickLogout();
        softAssert.assertTrue(driver.getCurrentUrl().equals(jsonFileManagerUrls.getValueByKey("url")));

        loginAsAdminAndSearch(employeeData[3]);

        navigateToEmergencyContacts();
        softAssert.assertEquals(emergencyContactsPage.getSavedContactCount(), countBefore - 2, "Saved contact count after delete");
        emergencyContactAssert();

        navigateToDependents();
        dependentAssert();

        softAssert.assertAll();
    }
}