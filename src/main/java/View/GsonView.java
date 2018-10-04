package View;

import Model.Card;
import com.google.gson.Gson;

import java.util.ArrayList;

public class GsonView implements View {

  Gson gson;

  public GsonView(){
    gson = new Gson();
  }

  @Override
  public String generateJsonFromCard(Card card) {
    return gson.toJson(card);

  }

  @Override
  public String generateJsonFromCards(ArrayList<Card> cards){
    return gson.toJson(cards);
  }

  @Override
  public Card generateCardFromJson(String json) {
    return gson.fromJson(json, Card.class);

  }

}
