package Exceptions;

public class CardAlreadyRegistered extends RuntimeException{

  public CardAlreadyRegistered(String message){
    super(message);
  }
}