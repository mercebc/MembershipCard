package Exceptions;

public class EmployeeNotRegistered extends RuntimeException{

  public EmployeeNotRegistered(String message){
    super(message);
  }

}
