package Controller;

import Model.Card;
import Model.Employee;
import Model.Model;
import Model.Validator;
import View.Message;
import View.View;
import io.javalin.Context;

public class Present {

  View myView;
  Model myModel;
  Validator myValidator;


  public Present(View myView, Model myModel, Validator myValidator) {

    this.myView = myView;
    this.myModel = myModel;
    this.myValidator = myValidator;

  }

  public void handlePresentation(Context context) {

    String cardIDString = context.pathParam("id");
    long cardID = Long.parseLong(cardIDString);

    Card card = myModel.getCardById(cardID);

    Employee employee = myModel.getEmployeeById(card.getEmployeeID());

    Message message = new Message("Welcome " + employee.getFirstName() + " " + employee.getSurname());

    context.result(myView.generateMessage(message));
  }

}
