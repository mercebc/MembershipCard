import Model.Card;
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

public class RegistrationTest extends TestSetUp{

  OkHttpClient client = new OkHttpClient();
  MediaType JSON = MediaType.parse("application/json; charset=utf-8");

  View myView = new GsonView();

  @Test
  public void EndPointToRegisterCard(){

    RequestBody body = RequestBody.create(JSON,"{}");
    Response response = null;

    Request request = new Request.Builder()
        .url("http://localhost:7000/cards")
        .post(body)
        .build();

    try {
      response = client.newCall(request).execute();
    } catch (IOException e) {
      e.printStackTrace();
    }

    assertEquals(201, response.code());
  }

  @Test
  public void RegisterCardAndHeaderLocation() {

    String json = "{'employeeID':2357, 'firstName':'Victoria', 'surname':'Smith', 'email':'victoria.test@test.com', 'mobileNumber':'+44756352607'}";

    Card readCard = null;
    Response response = null;

    RequestBody body = RequestBody.create(JSON,json);

    Request request = new Request.Builder()
        .url("http://localhost:7000/cards")
        .post(body)
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

    String url = request.url() + "/" + readCard.getCardID();

    assertEquals(url, response.header("Location"));
    assertEquals(2357, readCard.getEmployeeID());
  }

}
