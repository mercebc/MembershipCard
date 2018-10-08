package Controller;

import Model.Model;
import Model.Exceptions.CardNotRegistered;
import Model.Exceptions.EmployeeNotRegistered;
import Model.Card;
import Model.Employee;
import View.View;
import io.javalin.Context;

import java.util.ArrayList;

public class Detail {

  View myView;
  Model myModel;

  public Detail(View myView, Model myModel) {

    this.myView = myView;
    this.myModel = myModel;

  }

  public void handleCardDetail(Context context) {

    String cardIDString = context.pathParam("id");
    long cardID = Long.parseLong(cardIDString);

    try {
      Card card = getCard(cardID);
      context.status(200);
      context.result(myView.generateJsonFromCard(card));
    } catch (CardNotRegistered e) {
      context.status(404);
      context.result("Please register card before continue");
    }

  }


  public Card getCard(long cardID) {

    Card card = myModel.getCardById(cardID);

    if (card.getCardID() == 0) {
      throw new CardNotRegistered("Card is not registered");
    } else {
      return card;
    }

  }

  public void handleEmployeeDetail(Context context) {

    String employeeIDString = context.pathParam("id");
    long employeeID = Long.parseLong(employeeIDString);

    try {
      Employee employee = getEmployee(employeeID);
      context.status(200);
      context.result(myView.generateJsonFromEmployee(employee));
    } catch (EmployeeNotRegistered e) {
      context.status(404);
      context.result("Please register employee before continue");
    }

  }

  public Employee getEmployee(long employeeID) {

    Employee employee = myModel.getEmployeeById(employeeID);

    if (employee.getEmployeeID() == 0) {
      throw new EmployeeNotRegistered("Employee is not registered");
    } else {
      return employee;
    }
  }

  public void handleEmployeesList(Context context) {

    ArrayList<Employee> employees = myModel.getAllEmployees();
    context.status(200);

    context.result(myView.generateJsonFromEmployees(employees));

  }


}
