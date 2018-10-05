import Model.Card;
import Model.Model;
import Model.JdbcModel;
import View.View;
import View.GsonView;

import okhttp3.OkHttpClient;
import okhttp3.Request;
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
  public void EndPointGetAllCards(){
    Response response = null;

    Request request = new Request.Builder()
        .url("http://localhost:7000/cards")
        .get()
        .build();

    try {
      response = client.newCall(request).execute();
    } catch (IOException e) {
      e.printStackTrace();
    }

    assertEquals(200, response.code());
  }

  @Test
  public void EndPointGetCard(){
    Response response = null;

    Request request = new Request.Builder()
        .url("http://localhost:7000/cards/1")
        .get()
        .build();

    try {
      response = client.newCall(request).execute();
    } catch (IOException e) {
      e.printStackTrace();
    }

    assertEquals(200, response.code());
  }

  @Test
  public void EndPointGetCardWithID1(){
    Response response = null;
    Card readCard = null;

    Request request = new Request.Builder()
        .url("http://localhost:7000/cards/1")
        .get()
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
    assertEquals("Merce", readCard.getFirstName());
    assertEquals("Bauza", readCard.getSurname());
  }


}
