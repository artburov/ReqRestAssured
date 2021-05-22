import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.CoreMatchers.is;

public class ReqRestApiTests {

    @BeforeAll
    static void setup() {
        RestAssured.baseURI = "https://reqres.in";
    }

    @Test
    void verifyPerPage() {
        given()
                .when()
                .get("/api/users")
                .then()
                .statusCode(200)
                .body("per_page", is(6));
    }

    @Test
    void verifyTotalPages() {
        given()
                .when()
                .get("/api/users")
                .then()
                .statusCode(200)
                .body("total_pages", is(2));
    }

    @Test
    void verifyCertainEmail() {
        given()
                .when()
                .get("/api/users")
                .then()
                .statusCode(200)
                .body("data[4].email", is("charles.morris@reqres.in"));
    }

    @Test
    void verifyUserData() {
        given()
                .contentType(JSON)
                .when()
                .get("/api/users/3")
                .then()
                .statusCode(200)
                .body("data.email", is("emma.wong@reqres.in"))
                .body("data.first_name", is("Emma"))
                .body("data.last_name", is("Wong"))
                .body("data.avatar", is("https://reqres.in/img/faces/3-image.jpg"))
                .body("support.text", is("To keep ReqRes free, " +
                        "contributions towards server costs are appreciated!"));
    }

    @Test
    void registerLogin() {
        given()
                .contentType(JSON)
                .body("{ \"email\": \"eve.holt@reqres.in\", " +
                        "\"password\": \"pistol\" }")

                .when()
                .post("api/register")
                .then()
                .statusCode(200)
                .body("id", is(4))
                .body("token", is("QpwL5tke4Pnpja7X4"));
    }
}