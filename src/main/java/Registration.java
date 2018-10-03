import com.google.gson.Gson;
import io.javalin.Context;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Registration{

  static Connection connection;
  static Gson gson;

  public Registration() throws SQLException {

    gson = new Gson();
    connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/membership_cards_tests", "root", "");

  }

  public static void handleRegistration(Context context) throws SQLException {

    String cardIDString = context.pathParam("cardID");
    int cardID = Integer.parseInt(cardIDString);

    Employee readEmployee = gson.fromJson(context.body(), Employee.class);
    Card readCard = gson.fromJson(context.body(), Card.class);

    ResultSet resultSet = checkRegistrationCard(cardID);

      if(!resultSet.next()){//Card and user not registered

        //Register the user
        if(registerEmployee(context, readEmployee)){
          System.out.println("New user registered for " + readEmployee.getFirstName());

          //Register the card once the user is registered
          if(registerCard(context, readCard)){
            System.out.println("New card registered for " + readCard.getEmployeeID());

          }else{
            System.out.println("Something went wrong and the card is not registered");
          }
        }else{
          System.out.println("Something went wrong and the user is not registered");
        }

      }else{//Welcome back Message if the card is registered
        System.out.println("Welcome back, " + readEmployee.getFirstName());
        context.status(200);
      }

  }

  private static boolean registerEmployee(Context context, Employee readEmployee) throws SQLException {
    PreparedStatement insert = connection.prepareStatement("INSERT INTO employees (employeeID, firstName, surname, email, mobileNumber, cardID) VALUES (?, ?, ?, ?, ?, ?)");
    insert.setInt(1, readEmployee.getEmployeeID());
    insert.setString(2, readEmployee.getFirstName());
    insert.setString(3, readEmployee.getSurname());
    insert.setString(4, readEmployee.getEmail());
    insert.setString(5, readEmployee.getMobileNumber());
    insert.setInt(6, readEmployee.getCardID());
    insert.executeUpdate();
    context.status(202);

    ResultSet resultSet = checkRegistrationEmployee(readEmployee.getEmployeeID());

    return resultSet.next();
  }

  private static boolean registerCard(Context context, Card readCard) throws SQLException {
    PreparedStatement insert = connection.prepareStatement("INSERT INTO cards (cardID, employeeID) VALUES (?, ?)");
    insert.setInt(1, readCard.getCardID());
    insert.setInt(2, readCard.getEmployeeID());
    insert.executeUpdate();
    context.status(202);

    ResultSet resultSet = checkRegistrationCard(readCard.getCardID());

    return resultSet.next();
  }



  public static ResultSet checkRegistrationCard(int cardID) throws SQLException {
      PreparedStatement select = connection.prepareStatement("SELECT * FROM cards WHERE cardID LIKE ?");
      select.setString(1, "%" + cardID + "%");
      ResultSet resultSet = select.executeQuery();

      return resultSet;
  }


  public static ResultSet checkRegistrationEmployee(int employeeID) throws SQLException {
    PreparedStatement select = connection.prepareStatement("SELECT * FROM employees WHERE employeeID LIKE ?");
    select.setString(1, "%" + employeeID + "%");
    ResultSet resultSet = select.executeQuery();

    return resultSet;
  }

}
