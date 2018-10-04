package View;

import Model.Card;
import Model.Employee;

import java.util.ArrayList;

public interface View {

  String generateJsonFromCard(Card card);

  Card generateCardFromJson(String json);

  String generateJsonFromCards(ArrayList<Card> cards);

}
