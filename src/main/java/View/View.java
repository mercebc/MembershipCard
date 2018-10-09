package View;

import Model.Card;
import Model.Employee;

import java.util.ArrayList;

public interface View {

  String generateJsonFromCard(Card card);

  Card generateCardFromJson(String json);

  String generateJsonFromEmployee(Employee employee);

  String generateJsonFromEmployees(ArrayList<Employee> employees);

  Employee generateEmployeeFromJson(String json);
}
