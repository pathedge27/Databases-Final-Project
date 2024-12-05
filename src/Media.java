import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

/**
 * A class that holds various functions for media.
 */
public class Media extends Menu{
  Scanner s;
  Application app;

  /**
   * A constructor that takes in needed fields.
   * @param s the scanner that this menu needs to register input.
   * @param a the application itself, passed as a param
   */
  public Media(Scanner s, Application a) {
    this.s = s;
    this.app = a;
  }

  /**
   * The default media menu. Allows a librarian to navigate to any function needed.
   */
  public void thisMenu() {
    System.out.println("\nMedia Menu | Input 1, 2, or 3");
    System.out.println("1: Add or remove media from the library");
    System.out.println("2: View the library's checked out media");
    System.out.println("3: Edit location of the library's media");
    String userInput = s.nextLine();

    switch (userInput.toLowerCase()) {
      case "1":
        this.addOrRemoveMedia();
        break;
      case "2":
        this.viewCheckedOutMedia();
        break;
      case "3":
        this.changedMediaLocation();
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

  /**
   * Allows a user to add or remove media based on media ID.
   */
  public void addOrRemoveMedia(){
    System.out.println("\n1: Add media \n2: Remove media");

    String userInput = s.nextLine();
    switch (userInput.toLowerCase()) {
      case "1":
        this.addMedia();
        break;
      case "2":
        this.removeMedia();
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
        this.addOrRemoveMedia();
    }
  }

  /**
   * Returns all checked out media.
   */
  public void viewCheckedOutMedia() {
    try {
      String getChecked = "CALL GetCheckedOutMedia()";
      CallableStatement statement = conn.prepareCall(getChecked);
      ResultSet r = statement.executeQuery();
      while (r.next()) {
        System.out.print("\nid: " + r.getString("mid") + ", ");
        System.out.print("title: " + r.getString("title") + ", ");
        System.out.print("publisher: " + r.getString("publisher") + ", ");
        System.out.print("reference number: " + r.getString("reference_number") + ", ");
        System.out.print("location: " + r.getString("location") + ", ");
        System.out.print("publication_date: " + r.getString("publication_date") + ", ");
        System.out.print("checked out?: " + (r.getString("is_checked_out").equals("1")));
        System.out.println("-----------------------------------------");
      }
    } catch (SQLException e) {
      System.out.println("Encountered an error! " + e.getMessage());
    }
    this.thisMenu();
  }

  /**
   * Changes the media location to the specified inputs.
   */
  public void changedMediaLocation() {
    try {
      displayChanges();
      System.out.println("Choose a media id from the above list:");
      String mid = s.nextLine();
      System.out.println("Media location:");
      String location = s.nextLine();

      String getChecked = "CALL UpdateMediaLocation(?, ?)";
      CallableStatement statement = conn.prepareCall(getChecked);
      statement.setString(1, mid);
      statement.setString(2, location);
      statement.execute();
    } catch (SQLException e) {
      System.out.println("Encountered an error! " + e.getMessage());
    }
    this.thisMenu();
    this.seeChanges(s);
  }

  /**
   * Adds user-specified media to the library database.
   */
  public void addMedia() {
    System.out.println("\n1: Book \n2: CD \n3: Record\n4: Magazine");

    String userInput = s.nextLine();
    switch (userInput.toLowerCase()) {
      case "1":
        System.out.println("Please enter book details: \n" +
                "Book title:");
        String title = s.nextLine();
        System.out.println("Publisher:");
        String publisher = s.nextLine();
        System.out.println("Reference number:");
        String reference = s.nextLine();
        System.out.println("Location:");
        String location = s.nextLine();
        System.out.println("Publication date:");
        String publicationDate = s.nextLine();
        System.out.println("ISBN:");
        String isbn = s.nextLine();
        System.out.println("Author:");
        String author = s.nextLine();
        System.out.println("Hardcover (true or false):");
        String hardcover = s.nextLine();
        System.out.println("Number of pages:");
        String numberOfPages = s.nextLine();
        addBook(title, publisher, reference, location, publicationDate, isbn, author, hardcover, numberOfPages);
        break;
      case "2":
        addCD();
        break;
      case "3":
        addRecord();
        break;
      case "4":
        addMagazine();
        break;
      case "close":
        app.closeApp();
        break;
      case "back":
        this.addOrRemoveMedia();
        break;
      case "restart":
        app.startMenu();
        break;
      default:
        System.out.println("invalid input");
        this.addMedia();
    }
    this.seeChanges(s);
  }

  private void addBook(String title, String publisher, String reference, String location,
                       String publicationDate, String isbn, String author,
                       String hardcover, String numberOfPages) {
    String addB = "CALL AddBook(?, ?, ?, ?, ?, ?, ?, ?, ?)";
    try {
      CallableStatement statement = conn.prepareCall(addB);
      statement.setString(1, title);
      statement.setString(2, publisher);
      statement.setString(3, reference);
      statement.setString(4, location);
      statement.setString(5, publicationDate);
      statement.setString(6, isbn);
      statement.setString(7, author);
      if (hardcover.equalsIgnoreCase("true") || hardcover.equalsIgnoreCase("false")) {
        statement.setBoolean(8, hardcover.equalsIgnoreCase("true"));
      } else {
        System.out.println("invalid input for hardcover! please input either true or false.");
      }
      statement.setString(9, numberOfPages);
      statement.execute();
    } catch (SQLException e) {
      System.out.println("Invalid input! " + e.getMessage());
      thisMenu();
    }
    this.seeChanges(s);
  }

  private void addCD() {
    try {
      String addCD = "CALL AddCD(?, ?, ?, ?, ?, ?, ?, ?)";
      CallableStatement statement = conn.prepareCall(addCD);
      System.out.println("Please enter a title: ");
      statement.setString(1, s.nextLine());
      System.out.println("Please enter a publisher: ");
      statement.setString(2, s.nextLine());
      System.out.println("Please enter a reference number: ");
      statement.setString(3, s.nextLine());
      System.out.println("Please enter a location: ");
      statement.setString(4, s.nextLine());
      System.out.println("Please enter a publication date: ");
      statement.setString(5, s.nextLine());
      System.out.println("Please enter an artist: ");
      statement.setString(6, s.nextLine());
      System.out.println("Please enter the number of CDs in this set: ");
      statement.setInt(7, Integer.parseInt(s.nextLine()));
      System.out.println("Please enter the type of this set (either 'audiobook' or 'music'):");
      String type = s.nextLine().trim().toLowerCase();
      if (!type.equals("audiobook") && !type.equals("music")) {
        System.out.println("Invalid type! Must be 'audiobook' or 'music'.");
        return;
      }
      statement.setString(8, type);
      statement.execute();
      this.seeChanges(s);
    } catch (SQLException | NumberFormatException e) {
      System.out.println("Encountered an error! " + e.getMessage());
      thisMenu();
    }
    this.seeChanges(s);
  }

  private void addRecord() {
    try {
      String addRecord = "CALL AddRecord(?, ?, ?, ?, ?, ?, ?, ?)";
      CallableStatement statement = conn.prepareCall(addRecord);
      System.out.println("Please enter a title: ");
      statement.setString(1, s.nextLine());
      System.out.println("Please enter a publisher: ");
      statement.setString(2, s.nextLine());
      System.out.println("Please enter a reference number: ");
      statement.setString(3, s.nextLine());
      System.out.println("Please enter a location: ");
      statement.setString(4, s.nextLine());
      System.out.println("Please enter a publication date: ");
      statement.setString(5, s.nextLine());
      System.out.println("Please enter an artist: ");
      statement.setString(6, s.nextLine());
      System.out.println("Please enter a record label: ");
      statement.setString(7, s.nextLine());
      System.out.println("Please enter the number of LPs in this set: ");
      statement.setInt(8, Integer.parseInt(s.nextLine()));
      statement.execute();
      this.seeChanges(s);
    } catch (SQLException | NumberFormatException e) {
      System.out.println("Encountered an error! " + e.getMessage());
      thisMenu();
    }
  }

  private void addMagazine() {
    try {
      String addMagazine = "CALL AddMagazine(?, ?, ?, ?, ?, ?, ?)";
      CallableStatement statement = conn.prepareCall(addMagazine);
      System.out.println("Please enter a title: ");
      statement.setString(1, s.nextLine());
      System.out.println("Please enter a publisher: ");
      statement.setString(2, s.nextLine());
      System.out.println("Please enter a reference number: ");
      statement.setString(3, s.nextLine());
      System.out.println("Please enter a location: ");
      statement.setString(4, s.nextLine());
      System.out.println("Please enter a publication date: ");
      statement.setString(5, s.nextLine());
      System.out.println("Please enter an issue number: ");
      statement.setString(6, s.nextLine());
      System.out.println("Please enter the number of pages: ");
      statement.setInt(7, Integer.parseInt(s.nextLine()));
      statement.execute();
      this.seeChanges(s);
    } catch (SQLException | NumberFormatException e) {
      System.out.println("Encountered an error! " + e.getMessage());
      thisMenu();
    }
    seeChanges(s);
  }

  /**
   * Removes a media from the database off of its media id.
   */
  public void removeMedia() {
    String removeMedia = "CALL RemoveMedia(?)";
    try {
      CallableStatement statement = conn.prepareCall(removeMedia);
      displayChanges();
      System.out.println("Please enter a media ID from the list above:");
      statement.setString(1, s.nextLine());
      statement.execute();
    } catch (SQLException e) {
      System.out.println("Invalid input! " + e.getMessage());
    }
    this.seeChanges(s);
  }

  /**
   * Displays a list of all media in the database with their respective fields.
   */
  public void displayChanges() {
    try {
      CallableStatement statement = conn.prepareCall("SELECT * FROM Media");
      ResultSet result = statement.executeQuery();
      while (result.next()) {
        System.out.println("Media ID: " + result.getString("mid"));
        System.out.println("Title: " + result.getString("title"));
        System.out.println("Publisher: " + result.getString("publisher"));
        System.out.println("Reference Number: " + result.getString("reference_number"));
        System.out.println("Location: " + result.getString("location"));
        System.out.println("Publication Date: " + result.getString("publication_date"));
        System.out.println("Is Checked Out?: " +
                (result.getString("is_checked_out").equals("1")));
        System.out.println("-----------------------------------------\n");
      }
    } catch (SQLException e) {
      System.out.println("Encountered an error! " + e.getMessage());
      thisMenu();
    }
  }
}
