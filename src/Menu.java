import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

public abstract class Menu {
  Connection conn;

  public void thisMenu() throws SQLException {
  }

  public void seeChanges(Scanner s) {
    System.out.println("Would you like to see your changes?");
    String userInput = s.nextLine();
    switch (userInput.toLowerCase()) {
      case "yes":
        try {
          this.displayChanges();
          this.thisMenu();
        } catch (SQLException e) {
          throw new RuntimeException(e);
        }
        break;
      case "no":
        try {
          this.thisMenu();
        } catch (SQLException e) {
          throw new RuntimeException(e);
        }
        break;
      default:
        System.out.println("invalid input");
        this.seeChanges(s);
    }
  }

  public void addConn(Connection conn) {
    this.conn = conn;
  }

  public abstract void displayChanges() throws SQLException;
}
