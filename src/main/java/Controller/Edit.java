package Controller;

import Model.Model;
import Model.Employee;
import Model.Exceptions.CardNotRegistered;
import Model.Exceptions.ParametersMissing;
import Model.Exceptions.EmployeeNotRegistered;
import Model.Card;
import View.View;
import io.javalin.Context;

public class Edit {

  View myView;
  Model myModel;

  public Edit(View myView, Model myModel) {

    this.myView = myView;
    this.myModel = myModel;

  }

  public void handleEdit(Context context) {

    String employeeIDString = context.pathParam("id");
    long employeeID = Long.parseLong(employeeIDString);

    Employee readEmployee = myView.generateEmployeeFromJson(context.body());

    try {
      Employee retrievedEmployee = updateEmployee(employeeID, readEmployee);
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

  private Employee updateEmployee(long employeeID, Employee readEmployee) {

    Employee employee = myModel.getEmployeeById(employeeID);

    if (employee.getEmployeeID() == 0) {
      throw new EmployeeNotRegistered("Employee is not registered");
    } else {
      myModel.updateEmployeeInfo(employeeID, readEmployee);
    }
    return myModel.getEmployeeById(employeeID);

  }

  public void handleTopUp(Context context) {

    String cardIDString = context.pathParam("id");
    long cardID = Long.parseLong(cardIDString);

    Card readCard = myView.generateCardFromJson(context.body());

    try {
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

    if (readCard.getAmountTopUp() == 0) {
      throw new ParametersMissing("Amount to top up is missing");

    } else {
      double newCredit = card.getCredit() + readCard.getAmountTopUp();

      if (card.getCardID() == 0) {
        throw new CardNotRegistered("Card is not registered");
      } else {
        myModel.topUpCreditOnCard(cardID, newCredit);
      }
    }

    return myModel.getCardById(cardID);

  }

}
