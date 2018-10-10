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

public class PresentCardTest extends TestSetUp{

  MediaType JSON = MediaType.parse("application/json; charset=utf-8");


  OkHttpClient client = new OkHttpClient();
  View myView = new GsonView();

  @Test
  public void returnWelcomeMessageWhenCardIsFound() throws IOException {

    String json = "{'employeeID':00405, 'firstName':'Martha', 'surname':'Smith', 'email':'victoria.test@test.com', 'mobileNumber':'+44756352607'}";
    RequestBody body = RequestBody.create(JSON,json);

    Request newCardRequest = new Request.Builder()
        .url("http://localhost:7000/cards")
        .post(body)
        .build();
    Response newCardResponse =client.newCall(newCardRequest).execute();

    Card newCard = myView.generateCardFromJson(newCardResponse.body().string());

    Request request = new Request.Builder()
        .url("http://localhost:7000/present/" + newCard.getCardID())
        .get()
        .build();

    Response response = client.newCall(request).execute();

    assertEquals(200, response.code());
    assertEquals("{\"message\":\"Welcome Martha Smith\"}", response.body().string());

  }

  


}
