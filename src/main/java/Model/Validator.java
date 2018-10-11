package Model;

import Exceptions.CardNotRegistered;
import Exceptions.EmployeeAlreadyRegistered;
import Exceptions.EmployeeNotRegistered;
import Exceptions.ParametersMissing;


public class Validator {

  DAO myDAO;

  public Validator(DAO myDAO) {
    this.myDAO = myDAO;

  }

  public void cardNotRegistered(long cardID) {
    Card card = myDAO.getCardById(cardID);

    if (card.getCardID() == 0) {
      throw new CardNotRegistered("Card is not registered");
    }
  }

  public void cardRegistered(long cardID) {
    Card card = myDAO.getCardById(cardID);

    if (card.getCardID() != 0) {
      throw new CardNotRegistered("Card is already registered");
    }
  }

  public void employeeRegistered(Employee employee) {
    Employee foundEmployee = myDAO.getEmployeeById(employee.getEmployeeID());

    if (foundEmployee.getEmployeeID() != 0) {
      throw new EmployeeAlreadyRegistered("Employee is already registered");
    }
  }

  public void employeeNotRegistered(long employeeID) {
    Employee foundEmployee = myDAO.getEmployeeById(employeeID);

    if (foundEmployee.getEmployeeID() == 0) {
      throw new EmployeeNotRegistered("Employee is not registered");
    }
  }


  public void cardHasAmountTopUp(Card readCard) {

    if (readCard.getAmountTopUp() == 0) {
      throw new ParametersMissing("Parameters missing");
    }
  }


}
