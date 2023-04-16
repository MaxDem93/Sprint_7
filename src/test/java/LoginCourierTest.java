import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;
import static org.apache.http.HttpStatus.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


public class LoginCourierTest {


    Courier courier = new Courier("qaja", "1234", "qa_java");
    CourierApi courierApi;
    @Before
    public void setUp() {
        courierApi = new CourierApi();
    }
    @Test
    @DisplayName("Проверка успешной авторизации курьера")
    @Description("Проверка того, в резултате успешной авторизации возвращается не пустой id")
    public void authCourierSuccessTest() {
        ValidatableResponse response = courierApi.login(courier);
        int statusCode = response.extract().statusCode();
        int valId = response.extract().path("id");
        assertEquals(SC_OK, statusCode);
        assertNotNull(valId);
    }
    @Test
    @DisplayName("Проверка авторизации курьера без логина")
    @Description("Проверка авторизации курьера без указания логина")
    public void authCourierWithOutLoginTest() {
        Courier courierWithOutLogin = new Courier("", "1234", "");
        ValidatableResponse response = courierApi.login(courierWithOutLogin);
        int statusCode = response.extract().statusCode();
        String valMessage = response.extract().path("message");
        assertEquals(SC_BAD_REQUEST, statusCode);
        assertEquals("Недостаточно данных для входа", valMessage);
    }
    @Test
    @DisplayName("Проверка авторизации курьера без пароля")
    @Description("Проверка авторизации курьера без указания пароля")
    public void authCourierWithOutPasswordTest() {
        Courier courierWithOutPassword = new Courier("TestQA", "", "");
        ValidatableResponse response = courierApi.login(courierWithOutPassword);

        int statusCode = response.extract().statusCode();
        assertEquals(SC_BAD_REQUEST, statusCode);
    }
    @Test
    @DisplayName("Проверка авторизации курьера с неправильным паролем")
    @Description("Проверка авторизации курьера с неправильным паролем")
    public void authCourierIncorrectPasswordTest() {
        Courier courierIncorrectPassword = new Courier("qaja", "12345", "qa_java");
        ValidatableResponse response = courierApi.login(courierIncorrectPassword);

        int statusCode = response.extract().statusCode();
        String valMessage = response.extract().path("message");
        assertEquals(SC_NOT_FOUND, statusCode);
        assertEquals("Учетная запись не найдена", valMessage);
    }
    @Test
    @DisplayName("Проверка авторизации курьера с неправильным или не существующим логином")
    @Description("Проверка авторизации курьера с неправильным или не существующим логином")
    public void authCourierIncorrectLoginTest() {
        Courier courierIncorrectLogin = new Courier("qaja_bad_login", "1234", "qa_java");

        ValidatableResponse response = courierApi.login(courierIncorrectLogin);

        int statusCode = response.extract().statusCode();
        String valMessage = response.extract().path("message");
        assertEquals(SC_NOT_FOUND, statusCode);
        assertEquals("Учетная запись не найдена", valMessage);
    }
}
