package tests;

import io.qameta.allure.*;
import io.restassured.response.Response;
import lib.ApiCoreRequests;
import lib.Assertions;
import lib.BaseTestCase;
import lib.DataGenerator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.HashMap;
import java.util.Map;

@Epic("Registration cases")
@Feature("Registration")
class UserRegisterTest extends BaseTestCase {
    private final ApiCoreRequests apiCoreRequests = new ApiCoreRequests();

    @Test
    @DisplayName("Test user register with invalid email")
    @Description("Test user register is failed with invalid email")
    @Severity(SeverityLevel.CRITICAL)
    @TmsLink(value = "EX-15")
    void testCreateUserWithIncorrectEmail() {
        String email = "example.test.com";
        Map<String, String> userData = new HashMap<>();
        userData.put("email", email);
        userData = DataGenerator.getRegistrationData(userData);

        Response responseCreateAuth = apiCoreRequests.makePostRequest(
                "https://playground.learnqa.ru/api/user",
                userData);

        Assertions.assertResponseCodeEquals(responseCreateAuth, 400);
        Assertions.assertResponseTextEquals(responseCreateAuth, "Invalid email format");
    }


    @ParameterizedTest
    @ValueSource(strings = {"username", "email", "password", "firstName", "lastName"})
    @DisplayName("Test user register without fields")
    @Description("Test user register is failed without any of required fields")
    @Severity(SeverityLevel.CRITICAL)
    @TmsLink(value = "EX-15")
    void testCreateUserWithoutOneOfParameters(String condition) {
        Map<String, String> userData = new HashMap<>();
        userData = DataGenerator.getRegistrationData(userData);

        if (condition.equals("username") || condition.equals("email") ||
                condition.equals("password") || condition.equals("firstName") ||
                condition.equals("lastName")) {
            userData.remove(condition);
        } else {
            throw new IllegalArgumentException("Condition value is unknown: " + condition);
        }

        Response responseCreateAuth = apiCoreRequests.makePostRequest(
                "https://playground.learnqa.ru/api/user",
                userData);

        Assertions.assertResponseCodeEquals(responseCreateAuth, 400);
        Assertions.assertResponseTextEquals(responseCreateAuth, "The following required params are missed: " + condition);
    }

    @ParameterizedTest
    @DisplayName("Test register with invalid name")
    @Description("Test register is failed with invalid name")
    @CsvSource({
            "1, The value of \'username\' field is too short",
            "256, The value of \'username\' field is too long",
    })
    @Severity(SeverityLevel.MINOR)
    @TmsLink(value = "EX-15")
    public void testRegisterUserWithInvalidName(int length, String expectedAnswer) {
        String invalidUsername = DataGenerator.GetRandomName(length);

        Map<String, String> userData = new HashMap<>();
        userData.put("username", invalidUsername);
        userData = DataGenerator.getRegistrationData(userData);

        Response responseCreateUser = apiCoreRequests.makePostRequest(
                "https://playground.learnqa.ru/api/user/",
                userData
        );

        Assertions.assertResponseCodeEquals(responseCreateUser, 400);
        Assertions.assertResponseTextEquals(responseCreateUser, expectedAnswer);
    }

}
