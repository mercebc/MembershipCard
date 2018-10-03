import com.google.gson.Gson;
import io.javalin.Context;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class ToppingUp {

  static Connection connection;
  static double newCredit;

  public ToppingUp() throws SQLException {

    connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/membership_cards", "root", "");

  }

  public static void handleTopUp(Context context) throws SQLException {

    String cardIDString = context.pathParam("cardID");
    int cardID = Integer.parseInt(cardIDString);

    Gson gson = new Gson();
    Card readCard = gson.fromJson(context.body(), Card.class);

    updateCredit(cardID,readCard.getAmountTopUp());

    System.out.println("Your new credit is " + newCredit);

    context.status(202);

  }

  public static void updateCredit(int cardID, Double amountTopUp) throws SQLException {

    PreparedStatement select = connection.prepareStatement("SELECT * FROM cards WHERE cardID LIKE ?");
    select.setInt(1,  cardID);
    ResultSet resultSet = select.executeQuery();

    newCredit = resultSet.getDouble("credit") + amountTopUp;

    PreparedStatement update = connection.prepareStatement("UPDATE cards SET credit = ? WHERE cardID LIKE ?");
    select.setDouble(1, newCredit);
    select.setInt(2,  cardID);

    update.executeUpdate();

  }

}
