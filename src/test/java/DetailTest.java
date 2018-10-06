import Model.Card;
import Model.Model;
import Model.JdbcModel;
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

public class DetailTest {

  OkHttpClient client = new OkHttpClient();

  View myView = new GsonView();
  Model myModel = new JdbcModel();

  public DetailTest() {

    myModel.setConnectionString("jdbc:mysql://127.0.0.1:3306/membership_cards", "root", "");
  }

  @Test
  public void EndPointGetAllCards() throws IOException {

    Request request = new Request.Builder()
        .url("http://localhost:7000/cards")
        .get()
        .build();

    Response response = client.newCall(request).execute();

    assertEquals(200, response.code());
  }

  @Test
  public void GetCardWithCardID() throws IOException {

    String json = "{'employeeID':2357, 'firstName':'Martha', 'surname':'Smith', 'email':'victoria.test@test.com', 'mobileNumber':'+44756352607'}";
    MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    RequestBody body = RequestBody.create(JSON,json);

    Request newCardRequest = new Request.Builder()
        .url("http://localhost:7000/cards")
        .post(body)
        .build();
    Response newCardResponse =client.newCall(newCardRequest).execute();

    Card newCard = myView.generateCardFromJson(newCardResponse.body().string());

    long cardID = newCard.getCardID();

    Request request = new Request.Builder()
        .url("http://localhost:7000/cards/" + cardID)
        .get()
        .build();

    Response response = client.newCall(request).execute();
    Card readCard = myView.generateCardFromJson(response.body().string());

    assertEquals(200, response.code());
    assertEquals("Martha", readCard.getFirstName());
    assertEquals("Smith", readCard.getSurname());
  }

}
