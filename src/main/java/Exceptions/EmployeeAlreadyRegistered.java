package Exceptions;

public class EmployeeAlreadyRegistered extends RuntimeException{

  public EmployeeAlreadyRegistered(String message){
    super(message);
  }
}
