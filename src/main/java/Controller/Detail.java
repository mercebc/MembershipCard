package Controller;

import Exceptions.CardNotRegistered;
import Exceptions.EmployeeNotRegistered;
import Model.Card;
import Model.Employee;
import Model.Model;
import Model.Validator;
import View.View;
import io.javalin.Context;

import java.util.ArrayList;

public class Detail {

  View myView;
  Model myModel;
  Validator myValidator;

  public Detail(View myView, Model myModel, Validator myValidator) {

    this.myView = myView;
    this.myModel = myModel;
    this.myValidator = myValidator;

  }

  public void handleCardDetail(Context context) {

    String cardIDString = context.pathParam("id");
    long cardID = Long.parseLong(cardIDString);

    try {
      myValidator.CardNotRegistered(cardID);
      Card card = myModel.getCardById(cardID);

      context.status(200);
      context.result(myView.generateJsonFromCard(card));
    } catch (CardNotRegistered e) {
      context.status(404);
      context.result("Please register card before continue");
    }

  }

  public void handleEmployeeDetail(Context context) {

    String employeeIDString = context.pathParam("id");
    long employeeID = Long.parseLong(employeeIDString);

    try {
      myValidator.EmployeeNotRegistered(employeeID);

      Employee employee = myModel.getEmployeeById(employeeID);

      context.status(200);
      context.result(myView.generateJsonFromEmployee(employee));
    } catch (EmployeeNotRegistered e) {
      context.status(404);
      context.result("Please register employee before continue");
    }

  }

  public void handleEmployeesList(Context context) {

    ArrayList<Employee> employees = myModel.getAllEmployees();
    context.status(200);

    context.result(myView.generateJsonFromEmployees(employees));

  }


}
