package Controller;

import Exceptions.CardNotRegistered;
import Exceptions.EmployeeNotRegistered;
import Model.Card;
import Model.Employee;
import Model.DAO;
import Model.Validator;
import View.View;
import io.javalin.Context;

import java.util.ArrayList;

public class Detail {

  View myView;
  DAO myDAO;
  Validator myValidator;

  public Detail(View myView, DAO myDAO, Validator myValidator) {

    this.myView = myView;
    this.myDAO = myDAO;
    this.myValidator = myValidator;

  }

  public void handleCardDetail(Context context) {

    String cardIDString = context.pathParam("id");
    long cardID = Long.parseLong(cardIDString);

    try {
      myValidator.CardNotRegistered(cardID);
      Card card = myDAO.getCardById(cardID);

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

      Employee employee = myDAO.getEmployeeById(employeeID);

      context.status(200);
      context.result(myView.generateJsonFromEmployee(employee));
    } catch (EmployeeNotRegistered e) {
      context.status(404);
      context.result("Please register employee before continue");
    }

  }

  public void handleEmployeesList(Context context) {

    ArrayList<Employee> employees = myDAO.getAllEmployees();
    context.status(200);

    context.result(myView.generateJsonFromEmployees(employees));

  }


}
