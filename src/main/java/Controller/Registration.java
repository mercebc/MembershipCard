package Controller;

import Exceptions.CardAlreadyRegistered;
import Exceptions.ParametersMissing;
import Model.DAO;
import Exceptions.EmployeeAlreadyRegistered;
import Model.Employee;
import Model.Validator;
import Model.Card;
import View.View;
import View.Message;
import io.javalin.Context;


public class Registration {

  View myView;
  DAO myDAO;
  Validator myValidator;

  public Registration(View myView, DAO myDAO, Validator myValidator) {

    this.myView = myView;
    this.myDAO = myDAO;
    this.myValidator = myValidator;

  }

  public void handleCardRegistration(Context context) {

    String cardIDString = context.pathParam("id");
    long cardID = Long.parseLong(cardIDString);

    Employee readEmployee = myView.generateEmployeeFromJson(context.body());

    try {
      myValidator.EmployeeRegistered(readEmployee); //throws exception when employee found
      myValidator.CardRegistered(cardID); //throws exception when card found

      myDAO.registerEmployee(readEmployee);
      Card newCard = myDAO.registerCard(cardID, readEmployee);

      context.status(401);
      context.result(myView.generateJsonFromCard(newCard));

      String url = context.url() + "/" + newCard.getCardID();
      context.header("location", url);

    } catch (EmployeeAlreadyRegistered e) {
      context.status(400);
      Message message = new Message(e.getMessage());
      context.result(myView.generateMessage(message));
    } catch (CardAlreadyRegistered e) {
      context.status(400);
      Message message = new Message(e.getMessage());
      context.result(myView.generateMessage(message));
    } catch (ParametersMissing e) {
      context.status(400);
      Message message = new Message(e.getMessage());
      context.result(myView.generateMessage(message));
    }

  }
}

