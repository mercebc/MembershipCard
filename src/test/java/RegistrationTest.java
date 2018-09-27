import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.junit.Test;
import com.google.gson.*;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class RegistrationTest {

  OkHttpClient client = new OkHttpClient();
  Gson gson = new Gson();
  MediaType JSON = MediaType.parse("application/json; charset=utf-8");

  @Test
  public void RegisterSuccess() throws IOException {

    RequestBody body = RequestBody.create(JSON,"");

    Request request = new Request.Builder()
        .url("http://localhost:7000/register")
        .post(body)
        .build();
    Response response = client.newCall(request).execute();

    assertEquals(201, response.code());

  }

  @Test
  public void RegisterEmployerID() throws IOException {

    String json = "{'employerID':1}";
    RequestBody body = RequestBody.create(JSON,json);

    Request request = new Request.Builder()
        .url("http://localhost:7000/register")
        .post(body)
        .build();

    Response response = client.newCall(request).execute();

    Gson gson = new Gson();
    Card card = gson.fromJson(json, Card.class);

    assertEquals(1, card.getEmployerID());
    assertEquals(201, response.code());

  }

  @Test
  public void RegisterNewCard() throws IOException {

    String json = "{'employerID':2, 'name':'Anna', 'email':'anna@test.com', 'mobileNum':'+44756352637'}";
    RequestBody body = RequestBody.create(JSON,json);

    Request request = new Request.Builder()
        .url("http://localhost:7000/register")
        .post(body)
        .build();

    Response response = client.newCall(request).execute();

    Card card = gson.fromJson(json, Card.class);

    assertEquals("Anna", card.getName());
    assertEquals("anna@test.com", card.getEmail());
    assertEquals("+44756352637", card.getMobileNum());
    assertEquals(2, card.getEmployerID());
    assertEquals(201, response.code());

  }



}
