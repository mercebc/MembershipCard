package Model;

import java.util.ArrayList;

public interface Model {


  void setConnectionString(String url, String user, String password);

  Card registerCard(Employee readEmployee);

  void registerEmployee(Employee readEmployee);

  Employee getEmployeeById(long employeeID);

  Card getCardById(long cardId);

  void topUpCreditOnCard(long cardId, double newCredit);

  void updateEmployeeInfo(long employeeID, Employee readEmployee);

  ArrayList<Employee> getAllEmployees();

  void deleteCard(int cardId);
}
