package Controller;

import Exceptions.CardNotRegistered;
import Exceptions.EmployeeNotRegistered;
import Model.Card;
import Model.Employee;
import Model.DAO;
import Model.Validator;
import View.View;
import View.Message;
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
      myValidator.cardNotRegistered(cardID);
      Card card = myDAO.getCardById(cardID);

      context.status(200);
      context.result(myView.generateJsonFromCard(card));
    } catch (CardNotRegistered e) {
      context.status(404);
      Message message = new Message("Please register the card before continue");
      context.result(myView.generateMessage(message));
    }

  }

  public void handleEmployeeDetail(Context context) {

    String employeeIDString = context.pathParam("id");
    long employeeID = Long.parseLong(employeeIDString);

    try {
      myValidator.employeeNotRegistered(employeeID);

      Employee employee = myDAO.getEmployeeById(employeeID);

      context.status(200);
      context.result(myView.generateJsonFromEmployee(employee));
    } catch (EmployeeNotRegistered e) {
      context.status(404);
      Message message = new Message("Please register the employee before continue");
      context.result(myView.generateMessage(message));
    }

  }

  public void handleEmployeesList(Context context) {

    ArrayList<Employee> employees = myDAO.getAllEmployees();
    context.status(200);

    context.result(myView.generateJsonFromEmployees(employees));

  }


}
