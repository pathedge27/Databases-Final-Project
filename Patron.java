import java.util.Scanner;

public class Patron extends Menu{
  Scanner s;
  Application app;

  public Patron(Scanner s, Application a) {
    this.s = s;
    this.app = a;
  }

  public void thisMenu() {
    System.out.println("\nInput 1, 2, 3, 4, or 5");
    System.out.println("\nClose to close the application, back to go to the original menu");
    System.out.println("\n1: View the patrons of the library");
    System.out.println("\n4: Add or remove a patron from the library");
    System.out.println("\n2: Register patrons for an event");
    System.out.println("\n3: Check out books for a patron");
    System.out.println("\n4: Return books for a patron");

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
      case "close":
        app.closeApp();
        break;
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
    app.startMenu();
  }
  public void addOrRemovePatron() {
    System.out.println("\n1: Add patron \n2: Remove patron");

    String userInput = s.next();
    switch (userInput) {
      case "1":
        //call the function that adds media
        break;
      case "2":
        //call the function that removes media
        break;
      default:
        System.out.println("invalid input");
        this.addOrRemovePatron();
    }
    app.startMenu();
  }
  public void registerPatronForEvent(){
    //call the function to register a patron for an event
    app.startMenu();
  }
  public void checkOutBooks(){
    //call the function to check a book out
    app.startMenu();
  }
  public void returnBooks(){
    //call the function to return a book to the library
    app.startMenu();
  }
}
