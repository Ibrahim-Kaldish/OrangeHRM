package dataProviders;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.DataProvider;
import utils.JsonFileManager;
import utils.OpenCSVFileManager;

public class DataProviderTest {
    private static final Logger log = LogManager.getLogger(DataProviderTest.class);
    public static JsonFileManager jsonFileManagerUsers = new JsonFileManager("src/main/resources/users.json");
    public static JsonFileManager jsonFileManagerEmployees = new JsonFileManager("src/main/resources/employees.json");
    public static OpenCSVFileManager openCSVFileManager = new OpenCSVFileManager("src/main/resources/usernames.csv");

    @DataProvider (name = "validCredentials")
    public Object[][] validCredentials() {
        log.info("📋 Supplying validCredentials data");
        Object[][] data = jsonFileManagerUsers.getUsersByKey("valid");
        if (data.length == 0) {
            log.warn("⚠️ validCredentials returned no rows");
        }
        return data;
    }

    @DataProvider (name = "invalidCredentials")
    public Object[][] invalidCredentials() {
        log.info("📋 Supplying invalidCredentials data");
        Object[][] data = jsonFileManagerUsers.getUsersByKey("invalid");;
        if (data.length == 0) {
            log.warn("⚠️ invalidCredentials returned no rows");
        }
        return data;
    }

    @DataProvider (name = "validEmployeesData")
    public Object[][] validEmployeesData() {
        log.info("📋 Supplying valid Employees data");
        Object[][] data = jsonFileManagerEmployees.getEmployeesByKey("valid");;
        if (data.length == 0) {
            log.warn("⚠️ valid Employees returned no rows");
        }
        return data;
    }

    @DataProvider (name = "invalidEmployeesData")
    public Object[][] invalidEmployeesData() {
        log.info("📋 Supplying invalid Employees data");
        Object[][] data = jsonFileManagerEmployees.getEmployeesByKey("invalid");;
        if (data.length == 0) {
            log.warn("⚠️ Invalid Employees returned no rows");
        }
        return data;
    }

    @DataProvider (name = "validUsernames")
    public Object[][] validUsernames() {
        log.info("📋 Supplying valid Usernames data");
        Object[][] data = openCSVFileManager.getRowsAsArray();
        if (data.length == 0) {
            log.warn("⚠️ Valid Usernames returned no rows");
        }
        return data;
    }

}