package Controller;

import Model.Model;
import Model.Card;
import View.View;
import io.javalin.Context;

import java.util.ArrayList;

public class Detail {

  View myView;
  Model myModel;

  public Detail(View myView, Model myModel){

    this.myView = myView;
    this.myModel = myModel;

  }

  public void handleCardDetail(Context context) {

    String cardIDString = context.pathParam("id");
    long cardID = Long.parseLong(cardIDString);

    Card card = myModel.getCardById(cardID);
    context.status(200);

    context.result(myView.generateJsonFromCard(card));

  }

  public void handleCardList(Context context) {

    ArrayList<Card> cards = myModel.getAllCards();
    context.status(200);

    context.result(myView.generateJsonFromCards(cards));

  }



}
