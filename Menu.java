import java.sql.SQLException;
import java.util.Scanner;

public abstract class Menu {

  public void thisMenu() throws SQLException {
  }

  public void seeChanges(Scanner s) {
    System.out.println("Would you like to see your changes?");
    String userInput = s.next();
    switch (userInput.toLowerCase()) {
      case "yes":
        this.displayChanges();
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

  public void displayChanges() {}
}
