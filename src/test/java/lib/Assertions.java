package lib;

import io.restassured.response.Response;

import static org.hamcrest.Matchers.hasKey;
import static org.hamcrest.core.IsNot.not;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class Assertions {
    public static void assertJsonByName(Response Response, String name, String expectedValue) {
        Response.then().assertThat().body("$", hasKey(name));

        String value = Response.jsonPath().getString(name);
        assertEquals(expectedValue, value, "JSON value in not equal to expected value");
    }

    public static void assertResponseTextEquals(Response response, String expectedAnswer) {
        assertEquals(expectedAnswer,
                response.asString(),
                "Response text is not as expected");
    }

    public static void assertResponseCodeEquals(Response response, int expectedStatusCode) {
        assertEquals(expectedStatusCode,
                response.statusCode(),
                "Response status code is not as expected");
    }

    public static void assertJsonHasField(Response response, String expectedFieldName) {
        response.then().assertThat().body("$", hasKey(expectedFieldName));
    }

    public static void assertJsonHasFields(Response response, String[] expectedFieldNames) {
        for (String expectedFieldName : expectedFieldNames) {
            Assertions.assertJsonHasField(response, expectedFieldName);
        }
    }

    public static void assertJsonHasNotField(Response response, String unexpectedFieldName) {
        response.then().assertThat().body("$", not(hasKey(unexpectedFieldName)));
    }

    public static void assertJsonHasNotFields(Response response, String[] unexpectedFieldNames) {
        for (String unexpectedFieldName : unexpectedFieldNames) {
            Assertions.assertJsonHasNotField(response, unexpectedFieldName);
        }
    }




}
