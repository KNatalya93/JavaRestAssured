package lib;

import io.qameta.allure.Step;
import io.restassured.response.Response;

import static org.hamcrest.Matchers.hasKey;
import static org.hamcrest.core.IsNot.not;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class Assertions {
    @Step("Check that json contain field {name} with value: {expectedValue}")
    public static void assertJsonByName(Response Response, String name, String expectedValue) {
        Response.then().assertThat().body("$", hasKey(name));

        String value = Response.jsonPath().getString(name);
        assertEquals(expectedValue, value, "JSON value in not equal to expected value");
    }

    @Step("Check that actual response is equal for expected: {expectedAnswer}")
    public static void assertResponseTextEquals(Response response, String expectedAnswer) {
        assertEquals(expectedAnswer,
                response.asString(),
                "Response text is not as expected");
    }
    @Step("Check that actual status code is equal for expected: {expectedStatusCode}")
    public static void assertResponseCodeEquals(Response response, int expectedStatusCode) {
        assertEquals(expectedStatusCode,
                response.statusCode(),
                "Response status code is not as expected");
    }


    @Step("Check that response contain field {name}: {expectedFieldName}")
    public static void assertJsonHasField(Response response, String expectedFieldName) {
        response.then().assertThat().body("$", hasKey(expectedFieldName));
    }

    @Step("\"Check that response contain fields: {expectedFieldNames}")
    public static void assertJsonHasFields(Response response, String[] expectedFieldNames) {
        for (String expectedFieldName : expectedFieldNames) {
            Assertions.assertJsonHasField(response, expectedFieldName);
        }
    }

    @Step("Check that response doesn't contain field: {unexpectedFieldName}")
    public static void assertJsonHasNotField(Response response, String unexpectedFieldName) {
        response.then().assertThat().body("$", not(hasKey(unexpectedFieldName)));
    }

    @Step("Check that response doesn't contain fields: {unexpectedFieldNames}")
    public static void assertJsonHasNotFields(Response response, String[] unexpectedFieldNames) {
        for (String unexpectedFieldName : unexpectedFieldNames) {
            Assertions.assertJsonHasNotField(response, unexpectedFieldName);
        }
    }




}
