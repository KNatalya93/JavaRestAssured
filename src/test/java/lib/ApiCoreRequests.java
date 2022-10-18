package lib;

import io.qameta.allure.Step;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.http.Header;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class ApiCoreRequests {
    @Step("Make a GET-request with token and auth cookie")
    public Response makeGetRequest(String url, String token, String cookie){
        return given()
                .filter(new AllureRestAssured())
                .header(new Header("x-csrf-token", token))
                .cookie("auth_sid", cookie)
                .get(url)
                .andReturn();
    }

    @Step("Make a GET-request with auth cookie only")
    public Response makeGetRequestWithCookie(String url, String cookie){
        return given()
                .filter(new AllureRestAssured())
                .cookie("auth_sid", cookie)
                .get(url)
                .andReturn();
    }

    @Step("Make a GET-request with token only")
    public Response makeGetRequestWithToken(String url, String token){
        return given()
                .filter(new AllureRestAssured())
                .header(new Header("x-csrf-token", token))
                .get(url)
                .andReturn();
    }

    @Step("Make a POST-request with token only")
    public Response makePostRequest(String url, Map<String, String> authData){
        return given()
                .filter(new AllureRestAssured())
                .body(authData)
                .post(url)
                .andReturn();
    }

    @Step("Make user data request")
    public static Response getUserDataRequest(String header, String cookie, String id) {
        return  given()
                .header("x-csrf-token", header)
                .cookie("auth_sid", cookie)
                .get("https://playground.learnqa.ru/api/user/" + id)
                .andReturn();
    }

    @Step("Make a put-request with token")
    public static Response putEditUserRequest(String header, String cookie,Map<String, String> editData, String userId) {
        return
                given()
                .header("x-csrf-token", header)
                .cookie("auth_sid", cookie)
                .body(editData)
                .put("https://playground.learnqa.ru/api/user/" + userId)
                .andReturn();
    }

    @Step("Make a delete-request with token")
    public static Response deleteUserRequest(String header, String cookie, String userId) {
        return  given()
                .header("x-csrf-token", header)
                .cookie("auth_sid", cookie)
                .delete("https://playground.learnqa.ru/api/user/" + userId)
                .andReturn();
    }

    @Step("Make new user")
    public static JsonPath generateUserRequest(Map<String, String> userData) {
        return  given()
                .body(userData)
                .post("https://playground.learnqa.ru/api/user/")
                .jsonPath();
    }


    @Step("AuthRequest")
    public static Response authRequest(String email, String password) {
        Map<String, String> authDate = new HashMap<>();
        authDate.put("email", email);
        authDate.put("password", password);

        return  given()
                .body(authDate)
                .post("https://playground.learnqa.ru/api/user/login")
                .andReturn();
    }

}
