package Model;

public class CardNotRegistered extends RuntimeException{

  public CardNotRegistered(String message){
    super(message);
  }
}
