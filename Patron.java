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
    System.out.println("4: Check out books for a patron");
    System.out.println("5: Return books for a patron");
    System.out.println("6: Update a patron");

    String userInput = s.next();

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
    //call the function to view the library's patrons
    this.thisMenu();
  }
  public void addOrRemovePatron() {
    System.out.println("\n1: Add patron \n2: Remove patron");

    String userInput = s.next();
    switch (userInput.toLowerCase()) {
      case "1":
        //call the function that adds media
        break;
      case "2":
        //call the function that removes media
        break;
      case "close":
        app.closeApp();
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

    String userInput = s.next();

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
    //call the function to register a patron for an event
    this.thisMenu();
  }

  public void checkOutBooks() {
    //call the function to check a book out
    this.thisMenu();
  }

  public void returnBooks() {
    //call the function to return a book to the library
    this.thisMenu();
  }

  public void displayChanges() {
    //SELECT * FROM Patrons
  }

  public void updateName() {

    this.thisMenu();
  }

  public void updatePhone() {

    this.thisMenu();
  }

  public void updateAddress() {

    this.thisMenu();
  }

  public void updateDOB() {

    this.thisMenu();
  }

  public void updateCardNumber() {

    this.thisMenu();
  }

}
