import io.restassured.response.ValidatableResponse;
import io.qameta.allure.Step;
import static io.restassured.RestAssured.given;

public class OrderApi extends Client {

    private static final String PATH = "/api/v1/orders";


    @Step("Создание заказа {order}")
    public ValidatableResponse create(Order order) {

        return given()
                .spec(getSpec())
                .body(order)
                .when()
                .post(PATH)
                .then();
    }
    @Step("Получить список заказов")
    public ValidatableResponse return_list() {

        return given()
                .spec(getSpec())
                .when()
                .get(PATH)
                .then();
    }


}
