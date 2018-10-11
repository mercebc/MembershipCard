package Controller;

import Model.DAO;
import Model.Employee;
import Model.Validator;
import Exceptions.CardNotRegistered;
import Exceptions.ParametersMissing;
import Exceptions.EmployeeNotRegistered;
import Model.Card;
import View.View;
import View.Message;
import io.javalin.Context;

public class Edit {

  View myView;
  DAO myDAO;
  Validator myValidator;

  public Edit(View myView, DAO myDAO, Validator myValidator) {

    this.myView = myView;
    this.myDAO = myDAO;
    this.myValidator = myValidator;

  }

  public void handleEdit(Context context) {

    String employeeIDString = context.pathParam("id");
    long employeeID = Long.parseLong(employeeIDString);

    Employee readEmployee = myView.generateEmployeeFromJson(context.body());

    try {
        myValidator.employeeNotRegistered(employeeID);
        myDAO.updateEmployeeInfo(employeeID, readEmployee);

        Employee retrievedEmployee = myDAO.getEmployeeById(employeeID);

        context.status(200);
        context.result(myView.generateJsonFromEmployee(retrievedEmployee));
    } catch (EmployeeNotRegistered e) {
      context.status(404);
      Message message = new Message("Please register the employee before editing");
      context.result(myView.generateMessage(message));
    } catch (ParametersMissing e) {
      context.status(400);
      Message message = new Message("Parameters missing");
      context.result(myView.generateMessage(message));
    }
  }

  public void handleTopUp(Context context) {

    String cardIDString = context.pathParam("id");
    long cardID = Long.parseLong(cardIDString);

    Card readCard = myView.generateCardFromJson(context.body());

    try {
      myValidator.cardNotRegistered(cardID);
      myValidator.cardHasAmountTopUp(readCard);

      Card retrievedCard = updateCredit(cardID, readCard);

      context.status(200);
      context.result(myView.generateJsonFromCard(retrievedCard));
    } catch (CardNotRegistered e) {
      context.status(404);
      Message message = new Message("Please register the card before topping up");
      context.result(myView.generateMessage(message));
    } catch (ParametersMissing e) {
      context.status(400);
      Message message = new Message("Parameters missing");
      context.result(myView.generateMessage(message));
    }

  }

  public Card updateCredit(long cardID, Card readCard) {

    Card card = myDAO.getCardById(cardID);
    double newCredit = card.getCredit() + readCard.getAmountTopUp();

    myDAO.topUpCreditOnCard(cardID, newCredit);

    return myDAO.getCardById(cardID);

  }

}
