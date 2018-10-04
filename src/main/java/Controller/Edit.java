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

    updateEmployee(cardID,readCard);

    context.status(200);
    context.result(myView.generateJsonFromCard(readCard));

  }

  private void updateEmployee(long cardID, Card readCard) {

    myModel.updateEmployeeInfoOnCard(cardID,readCard);

  }

  public void handleTopUp(Context context) {

    String cardIDString = context.pathParam("id");
    long cardID = Long.parseLong(cardIDString);

    Card readCard = myView.generateCardFromJson(context.body());

    updateCredit(cardID,readCard);

    context.status(200);
    context.result(myView.generateJsonFromCard(readCard));

  }

  public void updateCredit(long cardID, Card readCard){

    Card card = myModel.getCardById(cardID);

    double newCredit = card.getCredit() + readCard.getAmountTopUp();

    myModel.updateCreditOnCard(cardID, newCredit);

  }

}
