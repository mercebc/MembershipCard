package Model;

public class Card {
  private long cardID;
  private double amountTopUp;
  private double credit;
  private int PIN;
  private long employeeID;

  public long getCardID() {
    return cardID;
  }

  public void setCardID(long cardID) {
    this.cardID = cardID;
  }

  public double getAmountTopUp() {
    return amountTopUp;
  }

  public double getCredit() {
    return credit;
  }

  public int getPIN() {
    return PIN;
  }

  public void setPIN(int PIN) {
    this.PIN = PIN;
  }

  public void setCredit(double credit) {
    this.credit = credit;
  }

  public long getEmployeeID() {
    return employeeID;
  }

  public void setEmployeeID(long employeeID) {
    this.employeeID = employeeID;
  }
}
