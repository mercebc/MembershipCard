package Model;

public class Card {
  private long cardID;
  private long employeeID;
  private String firstName;
  private String surname;
  private String email;
  private String mobileNumber;
  private double amountTopUp;
  private double credit;
  private int PIN;

  public long getCardID() {
    return cardID;
  }

  public String getMobileNumber() {
    return mobileNumber;
  }

  public String getEmail() {
    return email;
  }

  public String getSurname() {
    return surname;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setCardID(long cardID) {
    this.cardID = cardID;
  }

  public double getAmountTopUp() {
    return amountTopUp;
  }

  public long getEmployeeID() {
    return employeeID;
  }

  public double getCredit() {
    return credit;
  }

  public int getPIN() {
    return PIN;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public void setEmployeeID(long employeeID) {
    this.employeeID = employeeID;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public void setMobileNumber(String mobileNumber) {
    this.mobileNumber = mobileNumber;
  }

  public void setPIN(int PIN) {
    this.PIN = PIN;
  }

  public void setSurname(String surname) {
    this.surname = surname;
  }

  public void setCredit(double credit) {
    this.credit = credit;
  }
}
