import Model.Card;
import View.View;
import View.GsonView;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class EditTest {

  private static final double DELTA = 1e-15;

  OkHttpClient client = new OkHttpClient();
  MediaType JSON = MediaType.parse("application/json; charset=utf-8");

  View myView = new GsonView();


  @Test
  public void EndPointPutCredit() throws IOException {
    RequestBody body = RequestBody.create(JSON,"{}");

    Request request = new Request.Builder()
        .url("http://localhost:7000/cards/credit/4")
        .put(body)
        .build();

    Response response = client.newCall(request).execute();

    assertEquals(200, response.code());
  }


  @Test
  public void TopUp9credits() throws IOException {

    String json = "{'employeeID':2357, 'firstName':'Victoria', 'surname':'Smith', 'email':'victoria.test@test.com', 'mobileNumber':'+44756352607', 'amountTopUp':9}";
    RequestBody body = RequestBody.create(JSON,json);

    Request newCardRequest = new Request.Builder()
        .url("http://localhost:7000/cards")
        .post(body)
        .build();
    Response newCardResponse =client.newCall(newCardRequest).execute();

    Card newCard = myView.generateCardFromJson(newCardResponse.body().string());

    long cardID = newCard.getCardID();

    Request request = new Request.Builder()
        .url("http://localhost:7000/cards/credit/" + cardID)
        .put(body)
        .build();

    Response response = client.newCall(request).execute();

    Card readCard = myView.generateCardFromJson(response.body().string());


    assertEquals(200, response.code());
    assertEquals(9.0, readCard.getCredit(), DELTA);

  }


  @Test
  public void ChangeDetailsEmployee() throws IOException {

    String json = "{'employeeID':2357, 'firstName':'Victoria', 'surname':'Smith', 'email':'victoria.test@test.com', 'mobileNumber':'+44756352607'}";
    RequestBody body = RequestBody.create(JSON,json);

    Request request = new Request.Builder()
        .url("http://localhost:7000/cards/11")
        .put(body)
        .build();


    Response  response = client.newCall(request).execute();

    Card readCard = myView.generateCardFromJson(response.body().string());


    assertEquals(200, response.code());
    assertEquals(9.0, readCard.getCredit(), DELTA); //check that the credit hasn't changed
    assertEquals("Victoria", readCard.getFirstName());

  }


}
