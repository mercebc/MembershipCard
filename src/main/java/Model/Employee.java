package Model;

public class Employee {
  private long employeeID;
  private String firstName;
  private String surname;
  private String email;
  private String mobileNumber;
  private long cardID;

  public String getSurname() {
    return surname;
  }

  public String getEmail() {
    return email;
  }

  public long getEmployeeID() {
    return employeeID;
  }

  public String getFirstName() {
    return firstName;
  }

  public String getMobileNumber() {
    return mobileNumber;
  }

  public long getCardID() {
    return cardID;
  }

  public void setSurname(String surname) {
    this.surname = surname;
  }

  public void setMobileNumber(String mobileNumber) {
    this.mobileNumber = mobileNumber;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public void setEmployeeID(long employeeID) {
    this.employeeID = employeeID;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public void setCardID(long cardID) {
    this.cardID = cardID;
  }
}
