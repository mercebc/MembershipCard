package Model;

import Exceptions.ParametersMissing;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class JdbcModel implements Model {

  Connection connection;

  private final String SQL_CREATE_EMPLOYEE = "INSERT INTO employees (employeeID, firstName, surname, email, mobileNumber) VALUES (?,?,?,?,?)";
  private final String SQL_CREATE_CARD = "INSERT INTO cards (credit, PIN, employeeID) values (DEFAULT, DEFAULT, ?)";

  private final String SQL_GET_EMPLOYEE_BY_ID = "SELECT * FROM employees WHERE employeeID = ?";
  private final String SQL_GET_ALL_EMPLOYEES = "SELECT * FROM employees";
  private final String SQL_GET_CARD_BY_ID = "SELECT * FROM cards WHERE cardID = ?";

  private final String SQL_UPDATE_CREDIT_ON_CARD = "UPDATE cards SET credit = ? WHERE cardID LIKE ?";
  private final String SQL_UPDATE_EMPLOYEE_INFO = "UPDATE employees SET firstName = ?, surname = ?, email = ?, mobileNumber = ? WHERE employeeID LIKE ?";

  private final String SQL_DELETE_CARD = "DELETE FROM cards WHERE cardID = ?"; //delete employee too

  public JdbcModel(String url, String user, String password){

    setConnectionString(url, user, password);

  }

  @Override
  public void setConnectionString(String url, String user, String password){
    try {
      this.connection = DriverManager.getConnection(url, user, password);
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  @Override
  public Card registerCard(Employee readEmployee){

    Card card = new Card(); //default values

    try (PreparedStatement statement = connection.prepareStatement(SQL_CREATE_CARD, Statement.RETURN_GENERATED_KEYS)){
      statement.setLong(1, readEmployee.getEmployeeID());
      statement.executeUpdate();
      try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
        if (generatedKeys.next()) {
          card.setCardID(generatedKeys.getLong(1));
          card.setEmployeeID(readEmployee.getEmployeeID());
        }
      }
    } catch (SQLException e) {
      e.printStackTrace();
      throw new ParametersMissing("Parameters are missing");
    }

    return card;
  }

  @Override
  public void registerEmployee(Employee readEmployee){

    try (PreparedStatement statement = connection.prepareStatement(SQL_CREATE_EMPLOYEE)){
      statement.setLong(1, readEmployee.getEmployeeID());
      statement.setString(2, readEmployee.getFirstName());
      statement.setString(3, readEmployee.getSurname());
      statement.setString(4, readEmployee.getEmail());
      statement.setString(5, readEmployee.getMobileNumber());
      statement.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
      throw new ParametersMissing("Parameters are missing");
    }

  }

  @Override
  public Employee getEmployeeById(long employeeID) {

    Employee employee = new Employee(); //default values

    try (PreparedStatement statement = connection.prepareStatement(SQL_GET_EMPLOYEE_BY_ID)){
      statement.setLong(1, employeeID);

      try (ResultSet result = statement.executeQuery()) {
        while (result.next()) {
          employee.setEmployeeID(result.getLong(1));
          employee.setFirstName(result.getString(2));
          employee.setSurname(result.getString(3));
          employee.setEmail(result.getString(4));
          employee.setMobileNumber(result.getString(5));
          }
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return employee;
  }

  @Override
  public Card getCardById(long cardId) {

    Card card = new Card(); //default values

    try (PreparedStatement statement = connection.prepareStatement(SQL_GET_CARD_BY_ID)){
      statement.setLong(1, cardId);

      try (ResultSet result = statement.executeQuery()) {
        while (result.next()) {
          card.setCardID(result.getLong(1));
          card.setCredit(result.getDouble(2));
          card.setPIN(result.getInt(3));
          card.setEmployeeID(result.getLong(4));
          }
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return card;
  }

  @Override
  public void topUpCreditOnCard(long cardId, double newCredit) {

    try (PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_CREDIT_ON_CARD)){
      statement.setDouble(1, newCredit);
      statement.setLong(2, cardId);
      statement.executeUpdate();

    } catch (SQLException e) {
      e.printStackTrace();
      throw new ParametersMissing("Parameters are missing");
    }

  }

  @Override
  public void updateEmployeeInfo(long employeeID, Employee readEmployee) {

    try (PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_EMPLOYEE_INFO)){
      statement.setString(1, readEmployee.getFirstName());
      statement.setString(2, readEmployee.getSurname());
      statement.setString(3, readEmployee.getEmail());
      statement.setString(4, readEmployee.getMobileNumber());
      statement.setLong(5, employeeID);
      statement.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
      throw new ParametersMissing("Parameters are missing");
    }

  }

  @Override
  public ArrayList<Employee> getAllEmployees() {
    ArrayList<Employee> allEmployees = new ArrayList();

    try (PreparedStatement statement = connection.prepareStatement(SQL_GET_ALL_EMPLOYEES);
         ResultSet result = statement.executeQuery()) {
      while (result.next()) {
        Employee employee = new Employee();
        employee.setEmployeeID(result.getLong(1));
        employee.setFirstName(result.getString(2));
        employee.setSurname(result.getString(3));
        employee.setEmail(result.getString(4));
        employee.setMobileNumber(result.getString(5));
        allEmployees.add(employee);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return allEmployees;
  }

  @Override
  public void deleteCard(int cardId) {

    try (PreparedStatement statement = connection.prepareStatement(SQL_DELETE_CARD)) {
      statement.setInt(1, cardId);
      statement.executeUpdate();

    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

}
