import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Event extends Menu{
  Scanner s;
  Application app;

  public Event(Scanner s, Application a) {
    this.s = s;
    this.app = a;
  }

  public void thisMenu() {
    System.out.println("\nEvent Menu | Input 1, 2, or 3");
    System.out.println("1: Add or remove events from the library");
    System.out.println("2: View the library's event information");
    System.out.println("3: Edit the date of a certain event");
    String userInput = s.nextLine();

    switch (userInput.toLowerCase()) {
      case "1":
        this.addOrRemoveEvent();
        break;
      case "2":
        this.viewEventInfo();
        break;
      case "3":
        this.changeEventDate();
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

  public void changeEventDate(){
    try {
      String call = "CALL UpdateEventDate(?, ?)";
      CallableStatement addEvent = conn.prepareCall(call);
      System.out.println("Event ID:");
      addEvent.setString(1, s.nextLine());
      System.out.println("Event Date:");
      addEvent.setString(2, s.nextLine()); // Use parameterized query to prevent SQL injection
      ResultSet r = addEvent.executeQuery();
    } catch (SQLException e) {
      System.out.println("Invalid event id or date inputted");
      this.thisMenu();
    }
    this.seeChanges(s);
  }

  public void viewEventInfo() {
    try {
      String call = "CALL GetEventInfo(?)";
      CallableStatement addEvent = this.conn.prepareCall(call);
      System.out.println("Event ID:");
      addEvent.setInt(1, Integer.parseInt(s.nextLine()));
      ResultSet r = addEvent.executeQuery();
      while (r.next()) {
        System.out.print("event_name: " + r.getString("event_name") + ", ");
        System.out.print("date: " + r.getString("date") + ", ");
        System.out.print("capacity: " + r.getString("capacity") + ", ");
        System.out.print("current attendees: " + r.getString("current_attendees") + ", ");
        System.out.print("attendees: " + r.getString("attendees"));
      }
    } catch (SQLException | NumberFormatException e) {
      System.out.println("Invalid event id inputted" + e.getMessage());
      this.thisMenu();
    }
    this.thisMenu();
  }

  public void addOrRemoveEvent(){
    System.out.println("\n1: Add event \n2: Remove event");

    String userInput = s.nextLine();
    switch (userInput.toLowerCase()) {
      case "1":
        try {
          String call = "CALL AddEvent(?, ?, ?, ?, ?)";
          CallableStatement addEvent = conn.prepareCall(call);
          System.out.println("Event name: ");
          addEvent.setString(1, s.nextLine()); // Use parameterized query to prevent SQL injection
          System.out.println("Event date: ");
          addEvent.setString(2, s.nextLine()); // Use parameterized query to prevent SQL injection
          System.out.println("Event capacity:");
          addEvent.setString(3, s.nextLine()); // Use parameterized query to prevent SQL injection
          System.out.println("Event children:");
          addEvent.setString(4, s.nextLine()); // Use parameterized query to prevent SQL injection
          System.out.println("Event library ID:");
          addEvent.setString(5, s.nextLine()); // Use parameterized query to prevent SQL injection
          ResultSet r = addEvent.executeQuery();
          this.seeChanges(s);
        } catch (SQLException e) {
          System.out.println("Invalid input");
          this.thisMenu();
        }
        break;
      case "2":
        try {
          String call = "CALL RemoveEvent(?)";
          CallableStatement addEvent = conn.prepareCall(call);
          System.out.println("Event ID:");
          addEvent.setString(1, s.nextLine()); // Use parameterized query to prevent SQL injection
          ResultSet r = addEvent.executeQuery();
          this.seeChanges(s);
        } catch (SQLException e) {
          System.out.println("Invalid event id");
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
        this.addOrRemoveEvent();
    }
  }

  public void displayChanges() {
    try {
      String call = "SELECT * FROM Event";
      CallableStatement addEvent = conn.prepareCall(call);
      ResultSet r = addEvent.executeQuery();
      while (r.next()) {
        System.out.print("\nevent_id: " + r.getString("event_id") + ", ");
        System.out.print("event_name: " + r.getString("event_name") + ", ");
        System.out.print("date: " + r.getString("date") + ", ");
        System.out.print("capacity: " + r.getString("capacity") + ", ");
        System.out.print("children: " + r.getString("children") + ", ");
        System.out.print("library_id: " + r.getString("library_id"));
      }
      System.out.print("\n");
      this.thisMenu();
    } catch (SQLException e) {
      System.out.println("Invalid display");
      this.thisMenu();
    }
  }

}
