package Controller;

import Model.Model;
import Model.Employee;
import Model.Validator;
import Exceptions.CardNotRegistered;
import Exceptions.ParametersMissing;
import Exceptions.EmployeeNotRegistered;
import Model.Card;
import View.View;
import io.javalin.Context;

public class Edit {

  View myView;
  Model myModel;
  Validator myValidator;

  public Edit(View myView, Model myModel, Validator myValidator) {

    this.myView = myView;
    this.myModel = myModel;
    this.myValidator = myValidator;

  }

  public void handleEdit(Context context) {

    String employeeIDString = context.pathParam("id");
    long employeeID = Long.parseLong(employeeIDString);

    Employee readEmployee = myView.generateEmployeeFromJson(context.body());

    try {
        myValidator.EmployeeNotRegistered(employeeID);
        myModel.updateEmployeeInfo(employeeID, readEmployee);

        Employee retrievedEmployee = myModel.getEmployeeById(employeeID);

        context.status(200);
        context.result(myView.generateJsonFromEmployee(retrievedEmployee));
    } catch (EmployeeNotRegistered e) {
      context.status(404);
      context.result("Please register employee before editing");
    } catch (ParametersMissing e) {
      context.status(400);
      context.result("Parameters missing");
    }
  }

  public void handleTopUp(Context context) {

    String cardIDString = context.pathParam("id");
    long cardID = Long.parseLong(cardIDString);

    Card readCard = myView.generateCardFromJson(context.body());

    try {
      myValidator.CardNotRegistered(cardID);
      myValidator.CardHasAmountTopUp(readCard);

      Card retrievedCard = updateCredit(cardID, readCard);

      context.status(200);
      context.result(myView.generateJsonFromCard(retrievedCard));
    } catch (CardNotRegistered e) {
      context.status(404);
      context.result("Please register the card before topping up");
    } catch (ParametersMissing e) {
      context.status(400);
      context.result("Parameters missing");
    }

  }

  public Card updateCredit(long cardID, Card readCard) {

    Card card = myModel.getCardById(cardID);
    double newCredit = card.getCredit() + readCard.getAmountTopUp();

    myModel.topUpCreditOnCard(cardID, newCredit);

    return myModel.getCardById(cardID);

  }

}
