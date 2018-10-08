package Controller;

import Model.Model;
import Model.Exceptions.EmployeeAlreadyRegistered;
import Model.Employee;
import View.View;
import io.javalin.Context;


public class Registration{

  View myView;
  Model myModel;

  public Registration(View myView, Model myModel){

    this.myView = myView;
    this.myModel = myModel;

  }

  public void handleCardRegistration(Context context) {

    Employee readEmployee = myView.generateEmployeeFromJson(context.body());
    Employee foundEmployee = myModel.getEmployeeById(readEmployee.getEmployeeID());

    try{
      Employee newEmployee = registerEmployeeAndCard(readEmployee, foundEmployee);
      context.status(201);
      context.result(myView.generateJsonFromEmployee(newEmployee));

      String url = context.url() + "/" + newEmployee.getCardID();
      context.header("location", url);

    }catch (EmployeeAlreadyRegistered e) {
      context.result("Welcome back " + foundEmployee.getFirstName() + " " + foundEmployee.getSurname());
    }

  }

  public Employee registerEmployeeAndCard(Employee readEmployee, Employee foundEmployee){

    if(foundEmployee.getEmployeeID() == 0){

      readEmployee = myModel.registerCard(readEmployee);

      myModel.registerEmployee(readEmployee);

      return myModel.getEmployeeById(readEmployee.getEmployeeID());
    }else{
      throw new EmployeeAlreadyRegistered("Employee is already registered");
    }
  }

}
