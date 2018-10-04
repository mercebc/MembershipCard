import Model.Card;
import Model.Employee;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.junit.Test;
import com.google.gson.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.Assert.assertEquals;

public class RegistrationTest {

  OkHttpClient client = new OkHttpClient();
  Gson gson = new Gson();
  MediaType JSON = MediaType.parse("application/json; charset=utf-8");

  Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/membership_cards_test", "root", "");

  public RegistrationTest() throws SQLException {
  }


  @Test
  public void RegisterSuccess() throws IOException {

    RequestBody body = RequestBody.create(JSON,"");

    Request request = new Request.Builder()
        .url("http://localhost:7000/register")
        .post(body)
        .build();
    Response response = client.newCall(request).execute();

    assertEquals(202, response.code());

  }

  @Test
  public void RegisterEmployeeName() throws IOException {

    String json = "{'firstName':'Anna'}";
    RequestBody body = RequestBody.create(JSON,json);

    Request request = new Request.Builder()
        .url("http://localhost:7000/register")
        .post(body)
        .build();

    Response response = client.newCall(request).execute();

    Gson gson = new Gson();
    Employee employee = gson.fromJson(json, Employee.class);

    assertEquals("Anna", employee.getFirstName());
    assertEquals(202, response.code());

  }


  @Test
  public void InsertNewUserInDB() throws IOException, SQLException {

    int cardID = 234234; //passed as param

    String json = "{'employeeID':'2358', 'firstName':'Victoria', 'surname':'Smith', 'email':'victoria.test@test.com', 'mobileNumber':'+44756352637'}";
    RequestBody body = RequestBody.create(JSON,json);

    Request request = new Request.Builder()
        .url("http://localhost:7000/register")
        .post(body)
        .build();

    client.newCall(request).execute();

    Employee readEmployee = gson.fromJson(json, Employee.class);

    PreparedStatement insert = connection.prepareStatement("INSERT INTO employees (employeeID, firstName, surname, email, mobileNumber, cardID) VALUES (?, ?, ?, ?, ?, ?)");
    insert.setInt(1, readEmployee.getEmployeeID());
    insert.setString(2, readEmployee.getFirstName());
    insert.setString(3, readEmployee.getSurname());
    insert.setString(4, readEmployee.getEmail());
    insert.setString(5, readEmployee.getMobileNumber());
    insert.setInt(6, cardID);

    insert.executeUpdate();

    //check if has been registered
    PreparedStatement select = connection.prepareStatement("SELECT * FROM employees WHERE employeeID LIKE ?");
    select.setInt(1, readEmployee.getEmployeeID());
    ResultSet resultSet = select.executeQuery();

    assertEquals(true, resultSet.next());
  }


  @Test
  public void InsertNewCardInDB() throws IOException, SQLException {

    int cardID = 234234; //passed as param

    String json = "{'employeeID':'2358', 'firstName':'Victoria', 'surname':'Smith', 'email':'victoria.test@test.com', 'mobileNumber':'+44756352637'}";
    RequestBody body = RequestBody.create(JSON,json);

    Request request = new Request.Builder()
        .url("http://localhost:7000/register")
        .post(body)
        .build();

    client.newCall(request).execute();

    Card readCard = gson.fromJson(json, Card.class);

    //insert new card
    PreparedStatement insert = connection.prepareStatement("INSERT INTO cards (cardID, employeeID) VALUES (?, ?)");
    insert.setInt(1, cardID);
    insert.setInt(2, readCard.getEmployeeID());

    insert.executeUpdate();

    //check if has been registered
    PreparedStatement selectCard = connection.prepareStatement("SELECT * FROM cards WHERE cardID LIKE ?");
    selectCard.setInt(1, cardID);
    ResultSet results = selectCard.executeQuery();

    assertEquals(true, results.next());
  }

  @Test
  public void registeredEmployee() throws SQLException {

    int employerID = 2358;

    PreparedStatement select = connection.prepareStatement("SELECT * FROM employees WHERE employeeID LIKE ?");
    select.setInt(1, employerID);
    ResultSet resultSet = select.executeQuery();

    assertEquals(true, resultSet.next());

  }

  @Test
  public void NotRegisteredEmployer() throws SQLException {

    int employerID = 0;

    PreparedStatement select = connection.prepareStatement("SELECT * FROM employees WHERE employeeID LIKE ?");
    select.setInt(1, employerID);
    ResultSet resultSet = select.executeQuery();

    assertEquals(false, resultSet.next());

  }

  @Test
  public void registeredCard() throws SQLException {

    int cardID = 234234;

    PreparedStatement select = connection.prepareStatement("SELECT * FROM cards WHERE cardID LIKE ?");
    select.setInt(1, cardID);
    ResultSet resultSet = select.executeQuery();

    assertEquals(true, resultSet.next());

  }

  @Test
  public void NotRegisteredCard() throws SQLException {

    int cardID = 0;

    PreparedStatement select = connection.prepareStatement("SELECT * FROM cards WHERE cardID LIKE ?");
    select.setInt(1, cardID);
    ResultSet resultSet = select.executeQuery();

    assertEquals(false, resultSet.next());

  }

}
