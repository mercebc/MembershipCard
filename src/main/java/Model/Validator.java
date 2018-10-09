package Model;

import Exceptions.CardNotRegistered;
import Exceptions.EmployeeAlreadyRegistered;
import Exceptions.EmployeeNotRegistered;
import Exceptions.ParametersMissing;


public class Validator {

  Model myModel;

  public Validator(Model myModel) {
    this.myModel = myModel;

  }

  public boolean EmployeeRegistered(Employee employee) {
    Employee foundEmployee = myModel.getEmployeeById(employee.getEmployeeID());

    if (foundEmployee.getEmployeeID() == 0) {
      return true;
    } else {
      throw new EmployeeAlreadyRegistered("Employee is already registered");
    }
  }

  public boolean EmployeeNotRegistered(long employeeID) {
    Employee foundEmployee = myModel.getEmployeeById(employeeID);

    if (foundEmployee.getEmployeeID() == 0) {
      throw new EmployeeNotRegistered("Employee is not registered");
    } else {
      return false;
    }
  }

  public boolean CardNotRegistered(long cardID) {
    Card card = myModel.getCardById(cardID);

    if (card.getCardID() == 0) {
      throw new CardNotRegistered("Card is not registered");
    } else {
      return false;
    }
  }

  public boolean CardHasAmountTopUp(Card readCard) {

    if (readCard.getAmountTopUp() == 0) {
      throw new ParametersMissing("Parameters missing");
    } else {
      return true;
    }
  }


}
