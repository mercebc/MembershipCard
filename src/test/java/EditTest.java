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
  public void EndPointPutCredit(){
    Response response = null;

    RequestBody body = RequestBody.create(JSON,"{}");

    Request request = new Request.Builder()
        .url("http://localhost:7000/cards/credit/4")
        .put(body)
        .build();

    try {
      response = client.newCall(request).execute();
    } catch (IOException e) {
      e.printStackTrace();
    }

    assertEquals(200, response.code());
  }


  @Test
  public void TopUp9credits(){

    Card readCard = null;
    Response response = null;

    String json = "{'amountTopUp':9, 'firstName':'Merce'}";
    RequestBody body = RequestBody.create(JSON,json);

    Request request = new Request.Builder()
        .url("http://localhost:7000/cards/credit/11")
        .put(body)
        .build();

    try {
      response = client.newCall(request).execute();
    } catch (IOException e) {
      e.printStackTrace();
    }

    try {
      readCard = myView.generateCardFromJson(response.body().string());
    } catch (IOException e) {
      e.printStackTrace();
    }

    assertEquals(200, response.code());
    assertEquals(9.0, readCard.getCredit(), DELTA);

  }


  @Test
  public void ChangeDetailsEmployee(){

    Card readCard = null;
    Response response = null;

    String json = "{'employeeID':2357, 'firstName':'Victoria', 'surname':'Smith', 'email':'victoria.test@test.com', 'mobileNumber':'+44756352607'}";
    RequestBody body = RequestBody.create(JSON,json);

    Request request = new Request.Builder()
        .url("http://localhost:7000/cards/11")
        .put(body)
        .build();

    try {
      response = client.newCall(request).execute();
    } catch (IOException e) {
      e.printStackTrace();
    }

    try {
      readCard = myView.generateCardFromJson(response.body().string());
    } catch (IOException e) {
      e.printStackTrace();
    }

    assertEquals(200, response.code());
    assertEquals(9.0, readCard.getCredit(), DELTA); //check that the credit hasn't changed
    assertEquals("Victoria", readCard.getFirstName());

  }


}
