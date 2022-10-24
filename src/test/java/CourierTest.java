import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.core.Is.is;

public class CourierTest {
    @Before
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru/";
    }

    @Test
    public void createCourier(){
        Courier courier = new Courier("Marewa","123123","Nawia");
        Response response = given()
                .header("Content-type","application/json")
                .and()
                .body(courier)
                .when()
                .post("/api/v1/courier");
        response.then().assertThat()
                .statusCode(201)
                .and()
                .body("ok", is(true));


    }

    @Test
    public void cantCreateTwoSameCourier(){
        Courier courier2 = new Courier("Mirus","123123","Nawisa");
        Response response2 = given()
                .header("Content-type","application/json")
                .and()
                .body(courier2)
                .when()
                .post("/api/v1/courier");
        response2.then().assertThat()
                .statusCode(201)
                .and()
                .body("ok", is(true));

        Courier courier3 = new Courier("Mirus","123123","Nawisa");
        Response response3 = given()
                .header("Content-type","application/json")
                .and()
                .body(courier3)
                .when()
                .post("/api/v1/courier");
        response3.then().assertThat()
                .statusCode(409)
                .and()
                .body("message", is("Этот логин уже используется")); //этот тест падает,т.к. в документации текст ошибки другой
    }

    @Test
    public void createCourierWithoutPassword(){
        String json =  "{\"login\": \"Deepinsd\" , \"firstName\":\"Boris\"}";
        Response response5 = given()
                .header("Content-type","application/json")
                .and()
                .body(json)
                .when()
                .post("/api/v1/courier");
        response5.then().assertThat()
                .statusCode(400)
                .and()
                .body("message", is("Недостаточно данных для создания учетной записи"));
    }

    @Test
    public void createCourierWithoutLogin(){
        String json2 =  "{\"password\": \"123123\" , \"firstName\":\"Boris\"}";
        Response response6 = given()
                .header("Content-type","application/json")
                .and()
                .body(json2)
                .when()
                .post("/api/v1/courier");
        response6.then().assertThat()
                .statusCode(400)
                .and()
                .body("message", is("Недостаточно данных для создания учетной записи"));
    }



}
