package Controller;

import Model.Model;
import Model.Card;
import View.View;
import io.javalin.Context;


public class Registration{

  View myView;
  Model myModel;

  public Registration(View myView, Model myModel){

    this.myView = myView;
    this.myModel = myModel;

  }

  public void handleCardRegistration(Context context) {

    Card readCard = myView.generateCardFromJson(context.body());

    myModel.registerCard(readCard);
    context.status(201);

    readCard = myModel.getCardById(readCard.getCardID());
    context.result(myView.generateJsonFromCard(readCard));

    String url = context.url() + "/" + readCard.getCardID();
    context.header("location", url);
  }

}
