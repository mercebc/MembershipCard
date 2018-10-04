package Model;

import java.util.ArrayList;

public interface Model {

  void setConnectionString(String url, String user, String password);

  void registerCard(Card readCard);

  Card getCardById(long cardId);

  void updateCreditOnCard(long cardId, double newCredit);

  void updateEmployeeInfoOnCard(long cardId, Card readCard);

  ArrayList<Card> getAllCards();

  void deleteCard(int cardId);

}
