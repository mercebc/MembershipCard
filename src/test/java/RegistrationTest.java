import io.javalin.Javalin;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.junit.Before;
import org.junit.Test;
import com.google.gson.*;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class RegistrationTest {

  OkHttpClient client = new OkHttpClient();

  @Test
  public void RegisterSuccess() throws IOException {

    RequestBody body = RequestBody.create(MediaType.parse(""),"");

    Request request = new Request.Builder()
        .url("http://localhost:7000/register")
        .post(body)
        .build();
    Response response = client.newCall(request).execute();

    assertEquals(201, response.code());

  }

  @Test
  public void RegisterEmployerID() throws IOException {

    MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    String json = "{'employerID':25}";
    RequestBody body = RequestBody.create(JSON,json);

    Request request = new Request.Builder()
        .url("http://localhost:7000/register")
        .post(body)
        .build();

    Response response = client.newCall(request).execute();

    Gson gson = new Gson();
    Employer employer = gson.fromJson(json, Employer.class);

    assertEquals(25, employer.getEmployerID());
    assertEquals(201, response.code());

  }

}
