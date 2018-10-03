public class Card {
  private int cardID;
  private double amountTopUp;
  private double credit;
  private int PIN;
  private int employeeID;

  public int getCardID() {
    return cardID;
  }

  public double getAmountTopUp() {
    return amountTopUp;
  }

  public int getEmployeeID() {
    return employeeID;
  }

  public void setAmountTopUp(double amountTopUp) {
    this.amountTopUp = amountTopUp;
  }

  public double getCredit() {
    return credit;
  }

  public int getPIN() {
    return PIN;
  }


}
