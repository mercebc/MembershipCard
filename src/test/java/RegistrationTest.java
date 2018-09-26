import io.javalin.Javalin;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class RegistrationTest {

  @Test
  public void RegisterSuccess() throws IOException {

    Javalin app = Javalin.create().start(7000);

    app.post("/register", ctx -> {
      ctx.status(201);
    });

    OkHttpClient client = new OkHttpClient();
    RequestBody body = RequestBody.create(MediaType.parse(""),"");
    Request request = new Request.Builder()
        .url("http://localhost:7000/register")
        .post(body)
        .build();
    Response response = client.newCall(request).execute();

    assertEquals(201, response.code());

  }

}
