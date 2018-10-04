import Model.Card;
import com.google.gson.Gson;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;


public class TopUpTest {

  OkHttpClient client = new OkHttpClient();
  Gson gson = new Gson();
  MediaType JSON = MediaType.parse("application/json; charset=utf-8");


  @Test
  public void TopUpSuccess() throws IOException {

    RequestBody body = RequestBody.create(JSON,"");

    Request request = new Request.Builder()
        .url("http://localhost:7000/topup")
        .put(body)
        .build();
    Response response = client.newCall(request).execute();

    assertEquals(202, response.code());

  }

  @Test
  public void TopUpAmount() throws IOException {

    String json = "{'amountTopUp':9}";
    RequestBody body = RequestBody.create(JSON,json);

    Request request = new Request.Builder()
        .url("http://localhost:7000/topup")
        .put(body)
        .build();
    Response response = client.newCall(request).execute();

    Card card = gson.fromJson(json, Card.class);

    assertEquals(202, response.code());

  }


}
