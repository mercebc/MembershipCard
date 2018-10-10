package View;

import Model.Card;
import Model.Employee;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import java.util.ArrayList;

public class GsonView implements View {

  Gson gson;

  public GsonView(){
    gson = new Gson();
  }

  @Override
  public String generateJsonFromCard(Card card) {
    return gson.toJson(card);
  }

  @Override
  public Card generateCardFromJson(String json) {
    return gson.fromJson(json, Card.class);
  }

  @Override
  public String generateJsonFromEmployee(Employee employee) {
    return gson.toJson(employee);
  }

  @Override
  public String generateJsonFromEmployees(ArrayList<Employee> employees){
    return gson.toJson(employees);
  }

  @Override
  public Employee generateEmployeeFromJson(String json) {
    return gson.fromJson(json, Employee.class);
  }

  @Override
  public String generateMessage(Message message){
    return gson.toJson(message);
  }



}
