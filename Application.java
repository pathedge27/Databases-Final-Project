import java.sql.*;
import java.util.Scanner;

/**
 * Application code for the HarryPotterDB database.
 */
public class Application {
  Scanner s;
  String username, password, url, name;
  Connection conn;
  Event eventObject;
  Media mediaObject;
  Patron patronObject;
  int startCount;

  /**
   * Main method for this application. Run this method to use the application.
   * @param args user input.
   * @throws ClassNotFoundException possible exception.
   */
  public static void main(String[] args) throws ClassNotFoundException {
    Application app = new Application();
  }

  /**
   * A no-argument constructor for the Application Class. Initializes the scanner, database url,
   * driver, and prompts for the user's username and password.
   * @throws ClassNotFoundException thrown if the driver is incorrectly initialized.
   */
  public Application() throws ClassNotFoundException {
    this.startCount = 0;
    s = new Scanner(System.in);
    url = "jdbc:mysql://localhost:3306/harry_potter_book_v2";
    this.eventObject = new Event(s, this); //pass in whatever's necessary for the event to call functions/triggers
    this.mediaObject = new Media(s, this); //pass in whatever's necessary for the media to call functions/triggers
    this.patronObject = new Patron(s, this); //pass in whatever's necessary for the patron to call functions/triggers
    this.uNameAndPassword();
    Class driver = Class.forName("com.mysql.cj.jdbc.Driver");
  }

  /**
   * Prompts the user for their username and password, and verifies that these are valid.
   */
  private void uNameAndPassword(){
    System.out.println("Enter your username:");
    if(s.hasNext()){
      username = s.next();
    }
    System.out.println("Enter your password:");
    if(s.hasNext()) {
      password = s.next();
    }
    System.out.println("Enter your name:");
    if(s.hasNext()) {
      name = s.next();
    }
//    try {
//      conn = DriverManager.getConnection(url, username, password);
//      System.out.println("Hello Librarian: " + name);
//    } catch (SQLException e) {
//      System.out.println("Invalid username or password.");
//      this.uNameAndPassword();
//    }
    this.startMenu();
  }

  /**
   * Displays the initial menu to the user, with three options
   * The media, event, and patron menus. Choosing one of these menus will
   * delegate the creation of that menu to a certain menu object.
   * Inputting the command restart should always return you to this menu
   * @throws SQLException exception thrown
   */
  public void startMenu() {
    System.out.print("\nStart Menu | ");
    if(startCount == 0) {
      System.out.print("Once you've chosen a menu, inputting 'restart' will direct you to ");
      System.out.print("\nthis starting menu, inputting 'back' will take you to the previously open menu,");
      System.out.println("\nand inputting 'close' will close the application");
      startCount++;
    }
    System.out.print("Input 1, 2, or 3");
    System.out.print("\n1: Media Menu");
    System.out.print("\n2: Event Menu");
    System.out.println("\n3: Patron Menu");

    String userInput = s.next();
    switch (userInput) {
      case "1":
        mediaObject.thisMenu();
        break;
      case "2":
        eventObject.thisMenu();
        break;
      case "3":
        patronObject.thisMenu();
      case "close":
        this.closeApp();
      default:
        System.out.println("invalid input");
        this.startMenu();
    }
  }

  /**
   * Closes the connection and disconnects from the dB.
   * @throws SQLException SQL exception if closing the connection causes an error.
   */
  public void closeApp() {
    //disconnect from dB and close application
    try {
      if (!conn.isClosed()) {
        try {
          conn.close();
        } catch (SQLException e) {
          throw new RuntimeException(e);
        }
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

}
