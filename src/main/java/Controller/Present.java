package Controller;

import Exceptions.CardNotRegistered;
import Model.Card;
import Model.Employee;
import Model.DAO;
import Model.Validator;
import View.Message;
import View.View;
import io.javalin.Context;

public class Present {

  View myView;
  DAO myDAO;
  Validator myValidator;


  public Present(View myView, DAO myDAO, Validator myValidator) {

    this.myView = myView;
    this.myDAO = myDAO;
    this.myValidator = myValidator;

  }

  public void handlePresentation(Context context) {

    String cardIDString = context.pathParam("id");
    long cardID = Long.parseLong(cardIDString);

    try{
      myValidator.cardNotRegistered(cardID); //throws exception when card not found

      Card card = myDAO.getCardById(cardID);
      Employee employee = myDAO.getEmployeeById(card.getEmployeeID());

      Message message = new Message("Welcome " + employee.getFirstName() + " " + employee.getSurname());
      context.result(myView.generateMessage(message));
      context.status(200);
    }catch (CardNotRegistered e){
      context.status(404);
      Message message = new Message(e.getMessage());
      context.result(myView.generateMessage(message));
    }
  }

}
