package Model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class JdbcModel implements Model {

  Connection connection;

  public JdbcModel(){

  setConnectionString("jdbc:mysql://127.0.0.1:3306/membership_cards", "root", "");

  }

  private final String SQL_CREATE_CARD = "INSERT INTO cards (employeeID, firstName, surname, email, mobileNumber, PIN) VALUES (?,?,?,?,?,?)";

  private final String SQL_GET_CARD_BY_ID = "SELECT cardID, employeeID, firstName, surname, email, mobileNumber, credit FROM cards WHERE cardID = ?";
  private final String SQL_GET_ALL_CARDS = "SELECT cardID, employeeID, firstName, surname, email, mobileNumber, credit FROM cards";

  private final String SQL_UPDATE_CREDIT_ON_CARD = "UPDATE cards SET credit = ? WHERE cardID LIKE ?";
  private final String SQL_UPDATE_EMPLOYEE_INFO_ON_CARD = "UPDATE cards SET employeeID = ?,  firstName = ?, surname = ?, email = ?, mobileNumber = ?, PIN = ? WHERE cardID LIKE ?";
  private final String SQL_DELETE_CARD = "DELETE FROM cards WHERE cardID = ?";

  @Override
  public void setConnectionString(String url, String user, String password){
    try {
      this.connection = DriverManager.getConnection(url, user, password);
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void registerCard(Card readCard){

    try (PreparedStatement statement = connection.prepareStatement(SQL_CREATE_CARD, Statement.RETURN_GENERATED_KEYS)){
      statement.setLong(1, readCard.getEmployeeID());
      statement.setString(2, readCard.getFirstName());
      statement.setString(3, readCard.getSurname());
      statement.setString(4, readCard.getEmail());
      statement.setString(5, readCard.getMobileNumber());
      statement.setInt(6, readCard.getPIN());
      statement.executeUpdate();
      try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
        if (generatedKeys.next()) {
          readCard.setCardID(generatedKeys.getLong(1));
        }
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  @Override
  public Card getCardById(long cardId) {

    Card card = new Card();

    try (PreparedStatement statement = connection.prepareStatement(SQL_GET_CARD_BY_ID)){
      statement.setLong(1, cardId);

      try (ResultSet result = statement.executeQuery()) {
        while (result.next()) {
          card.setCardID(result.getLong(1));
          card.setEmployeeID(result.getLong(2));
          card.setFirstName(result.getString(3));
          card.setSurname(result.getString(4));
          card.setEmail(result.getString(5));
          card.setMobileNumber(result.getString(6));
          card.setCredit(result.getDouble(7));
          }
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return card;
  }

  @Override
  public void updateCreditOnCard(long cardId, double newCredit) {

    try (PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_CREDIT_ON_CARD)){
      statement.setDouble(1, newCredit);
      statement.setLong(2, cardId);
      statement.executeUpdate();

    } catch (SQLException e) {
      e.printStackTrace();
    }

  }

  @Override
  public void updateEmployeeInfoOnCard(long cardId, Card readCard) {

    try (PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_EMPLOYEE_INFO_ON_CARD)){
      statement.setLong(1, readCard.getEmployeeID());
      statement.setString(2, readCard.getFirstName());
      statement.setString(3, readCard.getSurname());
      statement.setString(4, readCard.getEmail());
      statement.setString(5, readCard.getMobileNumber());
      statement.setInt(6, readCard.getPIN());
      statement.setLong(7, cardId);
      statement.executeUpdate();

    } catch (SQLException e) {
      e.printStackTrace();
    }

  }

  @Override
  public ArrayList<Card> getAllCards() {
    ArrayList<Card> allCards = new ArrayList();

    try (PreparedStatement statement = connection.prepareStatement(SQL_GET_ALL_CARDS);
        ResultSet result = statement.executeQuery()) {
          while (result.next()) {
            Card card = new Card();
            card.setCardID(result.getLong(1));
            card.setEmployeeID(result.getLong(2));
            card.setFirstName(result.getString(3));
            card.setSurname(result.getString(4));
            card.setEmail(result.getString(5));
            card.setMobileNumber(result.getString(6));
            card.setCredit(result.getDouble(7));
            allCards.add(card);
          }
    } catch (SQLException e) {
      e.printStackTrace();
    }

    return allCards;
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
