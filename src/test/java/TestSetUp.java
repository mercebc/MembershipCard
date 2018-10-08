import org.junit.Before;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TestSetUp {

  @Before
  public void setUp() {

    try(Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/MembershipSystem", "root", "")){

     PreparedStatement statement = connection.prepareStatement("TRUNCATE TABLE MembershipSystem.cards");
     statement.execute();

     statement = connection.prepareStatement("TRUNCATE TABLE MembershipSystem.employees");
     statement.execute();

    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
}
