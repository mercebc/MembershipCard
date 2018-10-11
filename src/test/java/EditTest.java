import Model.Card;
import Model.Employee;
import View.GsonView;
import View.View;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class EditTest extends TestSetUp{

  private static final double DELTA = 1e-15;

  OkHttpClient client = new OkHttpClient();
  MediaType JSON = MediaType.parse("application/json; charset=utf-8");

  View myView = new GsonView();


  @Test
  public void TopUp9credits() throws IOException {

    String json = "{'employeeID':00404, 'firstName':'Martha', 'surname':'Smith', 'email':'victoria.test@test.com', 'mobileNumber':'+44756352607'}";
    RequestBody body = RequestBody.create(JSON,json);

    Request newCardRequest = new Request.Builder()
        .url("http://localhost:7000/cards/123")
        .post(body)
        .build();
    Response newCardResponse =client.newCall(newCardRequest).execute();

    Card newCard = myView.generateCardFromJson(newCardResponse.body().string());

    long cardID = newCard.getCardID();

    json = "{'amountTopUp':9}";
    body = RequestBody.create(JSON,json);

    Request request = new Request.Builder()
        .url("http://localhost:7000/cards/" + cardID)
        .put(body)
        .build();

    Response response = client.newCall(request).execute();

    Card readCard = myView.generateCardFromJson(response.body().string());


    assertEquals(200, response.code());
    assertEquals(9.0, readCard.getCredit(), DELTA);

  }


  @Test
  public void ChangeDetailsEmployee() throws IOException {

    String json = "{'employeeID':00405, 'firstName':'Martha', 'surname':'Smith', 'email':'victoria.test@test.com', 'mobileNumber':'+44756352607'}";
    RequestBody body = RequestBody.create(JSON,json);

    Request newCardRequest = new Request.Builder()
        .url("http://localhost:7000/cards/123")
        .post(body)
        .build();
    Response newCardResponse =client.newCall(newCardRequest).execute();

    Card newCard = myView.generateCardFromJson(newCardResponse.body().string());

    long employeeID = newCard.getEmployeeID();

    String newJson = "{'firstName':'Victoria', 'surname':'Smith', 'email':'victoria.test@test.com', 'mobileNumber':'+44756352607'}";
    RequestBody newBody = RequestBody.create(JSON,newJson);

    Request request = new Request.Builder()
        .url("http://localhost:7000/employees/" + employeeID)
        .put(newBody)
        .build();

    Response response = client.newCall(request).execute();

    Employee readEmployee = myView.generateEmployeeFromJson(response.body().string());

    assertEquals(200, response.code());
    assertEquals("Victoria", readEmployee.getFirstName());

  }


  @Test
  public void CardIsNotRegistered() throws IOException {

    long cardID = 100;

    String json = "{'employeeID':00406, 'firstName':'Victoria', 'surname':'Smith', 'email':'victoria.test@test.com', 'mobileNumber':'+44756352607'}";
    RequestBody body = RequestBody.create(JSON,json);

    Request request = new Request.Builder()
        .url("http://localhost:7000/cards/" + cardID)
        .put(body)
        .build();

    Response response = client.newCall(request).execute();

    assertEquals(404, response.code());

  }

  @Test
  public void NotAllParamsInRequest() throws IOException {

    String json = "{'employeeID':00403, 'firstName':'Martha', 'surname':'Smith', 'email':'victoria.test@test.com', 'mobileNumber':'+44756352607'}";
    RequestBody body = RequestBody.create(JSON,json);

    Request newCardRequest = new Request.Builder()
        .url("http://localhost:7000/cards/123")
        .post(body)
        .build();
    Response newCardResponse =client.newCall(newCardRequest).execute();

    Card newCard = myView.generateCardFromJson(newCardResponse.body().string());

    long employeeID = newCard.getEmployeeID();

    json = "{'email':'victoria.test@test.com'}";
    body = RequestBody.create(JSON,json);

    Request request = new Request.Builder()
        .url("http://localhost:7000/employees/" + employeeID)
        .put(body)
        .build();

    Response response = client.newCall(request).execute();

    assertEquals(400, response.code());
  }

}
