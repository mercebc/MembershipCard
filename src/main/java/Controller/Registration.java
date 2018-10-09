package Controller;

import Exceptions.ParametersMissing;
import Model.Model;
import Exceptions.EmployeeAlreadyRegistered;
import Model.Employee;
import Model.Validator;
import Model.Card;
import View.View;
import io.javalin.Context;


public class Registration {

  View myView;
  Model myModel;
  Validator myValidator;

  public Registration(View myView, Model myModel, Validator myValidator) {

    this.myView = myView;
    this.myModel = myModel;
    this.myValidator = myValidator;

  }

  public void handleCardRegistration(Context context) {

    Employee readEmployee = myView.generateEmployeeFromJson(context.body());

    try {
      myValidator.EmployeeRegistered(readEmployee);

      myModel.registerEmployee(readEmployee);
      Card newCard = myModel.registerCard(readEmployee);

      context.status(201);
      context.result(myView.generateJsonFromCard(newCard));

      String url = context.url() + "/" + newCard.getCardID();
      context.header("location", url);

    } catch (EmployeeAlreadyRegistered e) {
      context.result("Welcome back " + readEmployee.getFirstName() + " " + readEmployee.getSurname());
    } catch (ParametersMissing e) {
      context.status(400);
      context.result("Parameters missing");
    }

  }
}

