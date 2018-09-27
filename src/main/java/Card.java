public class Card {
  int employerID;
  String name;
  String email;
  String mobileNum;
  double amountToppingUp;
  double credit;
  int PIN;


  public Card(int employerID, String name){
    this.employerID = employerID;
    this.name = name;
  }

  public int getEmployerID() {
    return employerID;
  }

  public String getName() {
    return name;
  }

  public String getEmail() {
    return email;
  }

  public String getMobileNum() {
    return mobileNum;
  }

  public double getAmountToppingUp() {
    return amountToppingUp;
  }

  public double getCredit() {
    return credit;
  }

  public int getPIN() {
    return PIN;
  }
}
