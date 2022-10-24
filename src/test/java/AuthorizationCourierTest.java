import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.core.Is.is;

public class AuthorizationCourierTest {
    @Before
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru/";
    }

    @Test
    public void canAuthorizationCourier(){
        AuthorizationCourier authorizationCourier = new AuthorizationCourier("Marewa","123123");
        Response response = given()
                .header("Content-type","application/json")
                .and()
                .body(authorizationCourier)
                .when()
                .post("/api/v1/courier/login");
        response.then().assertThat()
                .statusCode(200)
                .and()
                .body("id", notNullValue());

    }

    @Test
    public void authorizationCourierWithNotCorrectPassword(){
        AuthorizationCourier authorizationCourier2 = new AuthorizationCourier("Marewa","123124");
        Response response2 = given()
                .header("Content-type","application/json")
                .and()
                .body(authorizationCourier2)
                .when()
                .post("/api/v1/courier/login");
        response2.then().assertThat()
                .statusCode(404)
                .and()
                .body("message", is("Учетная запись не найдена"));
    }

    @Test
    public void authorizationCourierWithNotCorrectLogin(){
        AuthorizationCourier authorizationCourier3 = new AuthorizationCourier("Mare","123123");
        Response response3 = given()
                .header("Content-type","application/json")
                .and()
                .body(authorizationCourier3)
                .when()
                .post("/api/v1/courier/login");
        response3.then().assertThat()
                .statusCode(404)
                .and()
                .body("message", is("Учетная запись не найдена"));
    }

    //тест падает из-за  504 ошибки, по документации должна быть 400
    @Test
    public void authorizationCourierWithoutPassword(){
        String json =  "{\"login\": \"Marew\"}";
        Response response4 = given()
                .header("Content-type","application/json")
                .and()
                .body(json)
                .when()
                .post("/api/v1/courier/login");
        response4.then().assertThat()
                .statusCode(400)
                .and()
                .body("message", is("Недостаточно данных для входа"));
    }


    @Test
    public void authorizationCourierWithoutLogin(){
        String json2 =  "{\"password\": \"123123\"}";
        Response response5 = given()
                .header("Content-type","application/json")
                .and()
                .body(json2)
                .when()
                .post("/api/v1/courier/login");
        response5.then().assertThat()
                .statusCode(400)
                .and()
                .body("message", is("Недостаточно данных для входа"));
    }
}
