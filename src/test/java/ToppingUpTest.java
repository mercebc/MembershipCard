import com.google.gson.Gson;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class ToppingUpTest {

  OkHttpClient client = new OkHttpClient();
  Gson gson = new Gson();
  MediaType JSON = MediaType.parse("application/json; charset=utf-8");

  @Test
  public void TopUpSuccess() throws IOException {

    RequestBody body = RequestBody.create(JSON,"");

    Request request = new Request.Builder()
        .url("http://localhost:7000/topup")
        .patch(body)
        .build();
    Response response = client.newCall(request).execute();

    assertEquals(202, response.code());

  }

  @Test
  public void TopUpAmount() throws IOException {

    Card card = new Card(2, "Anna");

    String json = "{'amountToppingUp':9}";
    RequestBody body = RequestBody.create(JSON,"");

    Request request = new Request.Builder()
        .url("http://localhost:7000/topup")
        .patch(body)
        .build();
    Response response = client.newCall(request).execute();

    card = gson.fromJson(json, Card.class);



    System.out.println(card.getAmountToppingUp());
    System.out.println(card.getName());
    System.out.println(card.getEmail());
    System.out.println(card.getEmployerID());

    assertEquals(201, response.code());

  }
}
