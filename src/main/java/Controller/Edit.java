package Controller;

import Model.Model;
import Model.Card;
import View.View;
import io.javalin.Context;

public class Edit {

  View myView;
  Model myModel;

  public Edit(View myView, Model myModel){

    this.myView = myView;
    this.myModel = myModel;

  }

  public void handleEdit(Context context) {

    String cardIDString = context.pathParam("id");
    long cardID = Long.parseLong(cardIDString);

    Card readCard = myView.generateCardFromJson(context.body());

    Card retrievedCard = updateEmployee(cardID,readCard);

    context.status(200);
    context.result(myView.generateJsonFromCard(retrievedCard));

  }

  private Card updateEmployee(long cardID, Card readCard) {

    myModel.updateEmployeeInfoOnCard(cardID,readCard);

    return myModel.getCardById(cardID);

  }

  public void handleTopUp(Context context) {

    String cardIDString = context.pathParam("id");
    long cardID = Long.parseLong(cardIDString);

    Card readCard = myView.generateCardFromJson(context.body());

    Card retrievedCard = updateCredit(cardID,readCard);

    context.status(200);
    context.result(myView.generateJsonFromCard(retrievedCard));

  }

  public Card updateCredit(long cardID, Card readCard){

    Card card = myModel.getCardById(cardID);

    double newCredit = card.getCredit() + readCard.getAmountTopUp();

    myModel.updateCreditOnCard(cardID, newCredit);

    return myModel.getCardById(cardID);

  }

}
