import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import java.util.ArrayList;
import static org.apache.http.HttpStatus.SC_CREATED;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


@RunWith(Parameterized.class)
public class createOrderTest { //Order

    static OrderApi orderApi;
    final private String firstNameParam;
    final private String lastNameParam;
    final private String addressParam;
    final private String metroStationParam;
    final private String phoneParam;
    final private int rentTimeParam;
    final private String deliveryDateParam;
    final private String commentParam;
    final private String colorParam;
    public createOrderTest(String firstNameParam, String lastNameParam, String addressParam, String metroStationParam, String phoneParam, int rentTimeParam, String deliveryDateParam, String commentParam, String colorParam) {
        this.firstNameParam = firstNameParam;
        this.lastNameParam = lastNameParam;
        this.addressParam = addressParam;
        this.metroStationParam = metroStationParam;
        this.phoneParam = phoneParam;
        this.rentTimeParam = rentTimeParam;
        this.deliveryDateParam = deliveryDateParam;
        this.commentParam = commentParam;
        this.colorParam = colorParam;
    }
    @Parameterized.Parameters(name = "Тестовые данные: {0} {1} {2} {3} {4} {5} {6} {7}")
    public static Object[][] getTestData() {
        return new Object[][]{
                {"Max", "Demidov", "str.Lenina", "Uralmash", "+79999999999", 5, "2023-04-16", "No comments", "Black"},
                {"Sasha", "Korolev", "str.Kalinina", "1905 goda", "+79999999999", 5, "2023-04-15", "comment 1", "Grey"},
                {"Gordei", "Ivanov", "str.Mira", "Geologicheskaya", "+79999999999", 5, "2023-04-14", "comment 2", "Blue"},
        };
    }
    @Before
    public void setUp() {
        orderApi = new OrderApi();
    }
    @Test
    @DisplayName("Проверка создания заказа")
    public void createOrderTest() {
        Order order = new Order(firstNameParam, lastNameParam, addressParam, metroStationParam, phoneParam, rentTimeParam, deliveryDateParam, commentParam, colorParam);
        ValidatableResponse response = orderApi.create(order);
        int statusCode = response.extract().statusCode();
        assertEquals(SC_CREATED, statusCode);
    }
    @Test
    @DisplayName("Проверка создания заказа c одним цветом")
    public void createOrderOneColor() {
        Order orderOneColor = new Order(firstNameParam, lastNameParam, addressParam, metroStationParam, phoneParam, rentTimeParam, deliveryDateParam, commentParam, colorParam);
        ValidatableResponse response = orderApi.create(orderOneColor);
        int statusCode = response.extract().statusCode();
        int valTrack = response.extract().path("track");
        assertEquals(SC_CREATED, statusCode);
        assertNotNull(valTrack);
    }
    @Test
    @DisplayName("Проверка создания заказа c несколькими цветами")
    public void createOrderManyColor() {
        ArrayList<String> manyColor = new ArrayList<>();
        manyColor.add("Black");
        manyColor.add("Grey");
        Order orderManyColor = new Order(firstNameParam, lastNameParam, addressParam, metroStationParam, phoneParam, rentTimeParam, deliveryDateParam, commentParam, manyColor);
        ValidatableResponse response = orderApi.create(orderManyColor);
        int statusCode = response.extract().statusCode();
        int valTrack = response.extract().path("track");
        assertEquals(SC_CREATED, statusCode);
        assertNotNull(valTrack);
    }
    @Test
    @DisplayName("Проверка создания заказа без цвета") 
    public void createOrderWithOutColor() {
        Order orderWithOutColor = new Order(firstNameParam, lastNameParam, addressParam, metroStationParam, phoneParam, rentTimeParam, deliveryDateParam, commentParam);
        ValidatableResponse response = orderApi.create(orderWithOutColor);
        int statusCode = response.extract().statusCode();
        int valTrack = response.extract().path("track");
        assertEquals(SC_CREATED, statusCode);
        assertNotNull(valTrack);
    }
}
