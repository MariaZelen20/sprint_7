import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import org.junit.runners.Parameterized;

import java.util.Collections;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.core.Is.is;

public class createOrderTest {

    @Before
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru/";
    }

    @Parameterized.Parameters
    public static String[][] getColor() {
        return (String[][]) new Object[][]{
                {new String[]{"BLACK"}},
                {new String[]{"GREY", "BLACK"}},
                {new String[]{"GREY"}},
                {new String[]{""}},

        };
    }

    @Test
    public void createOrder(){
        Order order = new Order("Владимир","Серпухов","Москва","Серпуховская","89321535821",4,"2022-10-30","Nothing", Collections.singletonList("GREY"));
        Response response = given()
                .header("Content-type","application/json")
                .and()
                .body(order)
                .when()
                .post("/api/v1/orders");
        response.then().assertThat()
                .statusCode(201)
                .and()
                .body("track", notNullValue());
    }

    @Test
    public void createOrderWithTwoColors(){
        Order order2 = new Order("Владимир","Серпухов","Москва","Серпуховская","89321535821",4,"2022-10-30","Nothing", Collections.singletonList("BLACK,GRAY"));
        Response response2 = given()
                .header("Content-type","application/json")
                .and()
                .body(order2)
                .when()
                .post("/api/v1/orders");
        response2.then().assertThat()
                .statusCode(201)
                .and()
                .body("track", notNullValue());
    }

    @Test
    public void createOrderWithoutColors(){
        Order order3 = new Order("Владимир","Серпухов","Москва","Серпуховская","89321535821",4,"2022-10-30","Nothing", Collections.singletonList(""));
        Response response3 = given()
                .header("Content-type","application/json")
                .and()
                .body(order3)
                .when()
                .post("/api/v1/orders");
        response3.then().assertThat()
                .statusCode(201)
                .and()
                .body("track", notNullValue());
    }
}
