package Controller;

import Model.Model;
import Model.Exceptions.CardNotRegistered;
import Model.ParametersMissing;
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
      Card card = myModel.getCardById(cardID);
      context.status(200);
      System.out.println(card.getCardID());
      context.result(myView.generateJsonFromCard(card));
    } catch (CardNotRegistered e) {
      context.status(404);
      context.result("Please register card before continue");
    } catch (ParametersMissing e) {
      context.status(400);
    }

  }

  public void handleEmployeeDetail(Context context) {

    String employeeIDString = context.pathParam("id");
    long employeeID = Long.parseLong(employeeIDString);

    try {
      Employee employee = myModel.getEmployeeById(employeeID);
      context.status(200);
      context.result(myView.generateJsonFromEmployee(employee));
    } catch (EmployeeNotRegistered e) {
      context.status(404);
      context.result("Please register employee before continue");
    } catch (ParametersMissing e) {
      context.status(400);
    }

  }

  public void handleEmployeesList(Context context) {

    ArrayList<Employee> employees = myModel.getAllEmployees();
    context.status(200);

    context.result(myView.generateJsonFromEmployees(employees));

  }


}
