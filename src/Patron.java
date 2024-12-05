import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Patron extends Menu{
  Scanner s;
  Application app;

  public Patron(Scanner s, Application a) {
    this.s = s;
    this.app = a;
  }

  public void thisMenu() {
    System.out.println("\nPatron Menu | Input 1, 2, 3, 4, 5, or 6");
    System.out.println("1: View the patrons of the library");
    System.out.println("2: Add or remove a patron from the library");
    System.out.println("3: Register patrons for an event");
    System.out.println("4: Check out media for a patron");
    System.out.println("5: Return media for a patron");
    System.out.println("6: Update a patron");

    String userInput = s.nextLine();

    switch (userInput.toLowerCase()) {
      case "1":
        this.viewLibraryPatrons();
        break;
      case "2":
        this.addOrRemovePatron();
        break;
      case "3":
        this.registerPatronForEvent();
        break;
      case "4":
        this.checkOutBooks();
        break;
      case "5":
        this.returnBooks();
        break;
      case "6":
        this.update();
        break;
      case "close":
        app.closeApp();
        break;
      case "restart":
      case "back":
        app.startMenu();
        break;
      default:
        System.out.println("invalid input");
        this.thisMenu();
    }
  }

  public void viewLibraryPatrons() {
    try {
      String call = "CALL GetPatrons()";
      CallableStatement addEvent = conn.prepareCall(call);
      ResultSet r = addEvent.executeQuery();
      while (r.next()) {
        System.out.println("phone_number: " + r.getString("phone_number") + ", ");
        System.out.println("card_number: " + r.getString("card_number") + ", ");
        System.out.println("first_name: " + r.getString("first_name") + ", ");
        System.out.println("last_name: " + r.getString("last_name") + ", ");
        System.out.println("dob: " + r.getString("dob"));
        System.out.println("address_street_num: " + r.getString("address_street_num"));
        System.out.println("address_street_name: " + r.getString("address_street_name"));
        System.out.println("address_city: " + r.getString("address_city"));
        System.out.println("address_state: " + r.getString("address_state"));
        System.out.println("address_zip_code: " + r.getString("address_zip_code"));
        System.out.println("------------------------------------------------");
      }
    } catch (SQLException e) {
      System.out.println("Invalid event id inputted" + e.getMessage());
      this.thisMenu();
    }
    this.thisMenu();
  }

  public void addOrRemovePatron() {
    System.out.println("\n1: Add patron \n2: Remove patron");

    String userInput = s.nextLine();
    switch (userInput.toLowerCase()) {
      case "1":
        try {
          String call = "CALL RegisterPatron(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
          CallableStatement addEvent = conn.prepareCall(call);
          System.out.println("Phone number: ");
          addEvent.setString(1, s.nextLine()); // Use parameterized query to prevent SQL injection
          System.out.println("Card number: ");
          addEvent.setString(2, s.nextLine()); // Use parameterized query to prevent SQL injection
          System.out.println("First name:");
          addEvent.setString(3, s.nextLine()); // Use parameterized query to prevent SQL injection
          System.out.println("Last name:");
          addEvent.setString(4, s.nextLine()); // Use parameterized query to prevent SQL injection
          System.out.println("Date of birth:");
          addEvent.setString(5, s.nextLine()); // Use parameterized query to prevent SQL injection
          System.out.println("Street number: ");
          addEvent.setString(6, s.nextLine()); // Use parameterized query to prevent SQL injection
          System.out.println("Street name: ");
          addEvent.setString(7, s.nextLine()); // Use parameterized query to prevent SQL injection
          System.out.println("City:");
          addEvent.setString(8, s.nextLine()); // Use parameterized query to prevent SQL injection
          System.out.println("State:");
          addEvent.setString(9, s.nextLine()); // Use parameterized query to prevent SQL injection
          System.out.println("Zip Code:");
          addEvent.setString(10, s.nextLine()); // Use parameterized query to prevent SQL injection
          ResultSet r = addEvent.executeQuery();
          this.seeChanges(s);
        } catch (SQLException e) {
          System.out.println("Invalid input");
          this.thisMenu();
        }
        break;
      case "2":
        try {
          String call = "CALL RemovePatron(?)";
          CallableStatement addEvent = conn.prepareCall(call);
          System.out.println("Phone number:");
          addEvent.setString(1, s.nextLine()); // Use parameterized query to prevent SQL injection
          ResultSet r = addEvent.executeQuery();
          this.seeChanges(s);
        } catch (SQLException e) {
          System.out.println("Invalid phone number");
          this.thisMenu();
        }
        break;
      case "close":
        app.closeApp();
        break;
      case "back":
        this.thisMenu();
        break;
      case "restart":
        app.startMenu();
        break;
      default:
        System.out.println("invalid input");
        this.addOrRemovePatron();
    }
    this.thisMenu();
  }

  public void update() {
    System.out.println("\nInput 1, 2, 3, 4, or 5");
    System.out.println("1: Update patron name");
    System.out.println("2: Update patron phone");
    System.out.println("3: Update patron address");
    System.out.println("4: Update patron date of birth");
    System.out.println("5: Update patron card number");

    String userInput = s.nextLine();

    switch (userInput.toLowerCase()) {
      case "1":
        this.updateName();
        break;
      case "2":
        this.updatePhone();
        break;
      case "3":
        this.updateAddress();
        break;
      case "4":
        this.updateDOB();
        break;
      case "5":
        this.updateCardNumber();
        break;
      case "close":
        app.closeApp();
        break;
      case "restart":
        app.startMenu();
        break;
      default:
        System.out.println("invalid input");
        this.thisMenu();
    }
    this.thisMenu();
  }

  public void registerPatronForEvent() {
    try {
      String call = "CALL RegisterForEvent(?, ?)";
      CallableStatement addEvent = this.conn.prepareCall(call);
      System.out.println("phone number: ");
      addEvent.setString(1, s.nextLine()); // Use parameterized query to prevent SQL injection
      System.out.println("event id: ");
      addEvent.setString(2, s.nextLine()); // Use parameterized query to prevent SQL injection
      ResultSet r = addEvent.executeQuery();
    } catch (SQLException e) {
      System.out.println("Invalid event id inputted");
      this.thisMenu();
    }
    System.out.println("Registered!");
    thisMenu();
  }

  public void checkOutBooks() {
    try {
      String checkOut = "CALL CheckOutMedia(?, ?, ?)";
      CallableStatement statement = conn.prepareCall(checkOut);
      System.out.println("Enter patron phone number: ");
      statement.setString(1, s.nextLine());
      System.out.println("Enter a Media ID: ");
      statement.setString(2, s.nextLine());
      System.out.println("Enter a Librarian ID: ");
      statement.setString(3, s.nextLine());
      statement.execute();
    } catch (SQLException e) {
      System.out.println("Encountered an error! " + e.getMessage());
      thisMenu();
    }
    this.thisMenu();
  }

  public void returnBooks() {
    try {
      String returnBooks = "CALL ReturnMedia(?)";
      CallableStatement statement = conn.prepareCall(returnBooks);
      System.out.println("Enter media ID: ");
      statement.setString(1, s.nextLine());
      statement.execute();
    } catch (SQLException e) {
      System.out.println("Encountered an error! " + e.getMessage());
      thisMenu();
    }
    this.thisMenu();
  }

  public void displayChanges() throws SQLException {
    this.viewLibraryPatrons();
  }

  public void updateName() {
    try {
      String call = "CALL UpdatePatronName(?, ?, ?)";
      CallableStatement addEvent = this.conn.prepareCall(call);
      System.out.println("phone number: ");
      addEvent.setString(1, s.nextLine()); // Use parameterized query to prevent SQL injection
      System.out.println("first name: ");
      addEvent.setString(2, s.nextLine()); // Use parameterized query to prevent SQL injection
      System.out.println("last name: ");
      addEvent.setString(3, s.nextLine()); // Use parameterized query to prevent SQL injection
      addEvent.execute();
    } catch (SQLException e) {
      System.out.println("Invalid input " + e.getMessage());
      this.thisMenu();
    }
    this.seeChanges(s);
  }

  public void updatePhone() {
    try {
      String call = "CALL UpdatePatronPhone(?, ?)";
      CallableStatement addEvent = this.conn.prepareCall(call);
      System.out.println("old phone number: ");
      addEvent.setString(1, s.nextLine()); // Use parameterized query to prevent SQL injection
      System.out.println("new phone number: ");
      addEvent.setString(2, s.nextLine()); // Use parameterized query to prevent SQL injection
      ResultSet r = addEvent.executeQuery();
    } catch (SQLException e) {
      System.out.println("Invalid event id inputted");
      this.thisMenu();
    }
    this.seeChanges(s);
  }

  public void updateAddress() {
    try {
      String call = "CALL UpdatePatronAddress(?, ?, ?, ?, ?, ?)";
      CallableStatement addEvent = this.conn.prepareCall(call);
      System.out.println("phone number: ");
      addEvent.setString(1, s.nextLine()); // Use parameterized query to prevent SQL injection
      System.out.println("street number: ");
      addEvent.setString(2, s.nextLine()); // Use parameterized query to prevent SQL injection
      System.out.println("street name: ");
      addEvent.setString(3, s.nextLine()); // Use parameterized query to prevent SQL injection
      System.out.println("city: ");
      addEvent.setString(4, s.nextLine()); // Use parameterized query to prevent SQL injection
      System.out.println("state: ");
      addEvent.setString(5, s.nextLine()); // Use parameterized query to prevent SQL injection
      System.out.println("zip code: ");
      addEvent.setString(6, s.nextLine()); // Use parameterized query to prevent SQL injection

      ResultSet r = addEvent.executeQuery();
    } catch (SQLException e) {
      System.out.println("Invalid event id inputted");
      this.thisMenu();
    }
    this.seeChanges(s);
  }

  public void updateDOB() {
    try {
      String call = "CALL UpdatePatronDateOfBirth(?, ?)";
      CallableStatement addEvent = this.conn.prepareCall(call);
      System.out.println("phone number: ");
      addEvent.setString(1, s.nextLine()); // Use parameterized query to prevent SQL injection
      System.out.println("date of birth: ");
      addEvent.setString(2, s.nextLine()); // Use parameterized query to prevent SQL injection
      ResultSet r = addEvent.executeQuery();
    } catch (SQLException e) {
      System.out.println("Invalid event id inputted");
      this.thisMenu();
    }
    this.seeChanges(s);
  }

  public void updateCardNumber() {
    try {
      String call = "CALL UpdatePatronCardNumber(?, ?)";
      CallableStatement addEvent = this.conn.prepareCall(call);
      System.out.println("phone number: ");
      addEvent.setString(1, s.nextLine()); // Use parameterized query to prevent SQL injection
      System.out.println("new number: ");
      addEvent.setString(2, s.nextLine()); // Use parameterized query to prevent SQL injection
      ResultSet r = addEvent.executeQuery();
    } catch (SQLException e) {
      System.out.println("Invalid event id inputted");
      this.thisMenu();
    }
    this.seeChanges(s);
  }
}
