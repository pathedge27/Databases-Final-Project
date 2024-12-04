import java.util.Scanner;

public class Media extends Menu{
  Scanner s;
  Application app;

  public Media(Scanner s, Application a) {
    this.s = s;
    this.app = a;
  }

  public void thisMenu() {
    System.out.println("\nMedia Menu | Input 1, 2, or 3");
    System.out.println("1: Add or remove media from the library");
    System.out.println("2: View the library's checked out media");
    System.out.println("3: Edit location of the library's media");
    String userInput = s.next();

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

  public void addOrRemoveMedia(){
    System.out.println("\n1: Add media \n2: Remove media");

    String userInput = s.next();
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

  public void viewCheckedOutMedia() {
    //call the function that shows checked out media
    this.thisMenu();
  }

  public void changedMediaLocation() {
    //call the function that allows the user to change the location of media
    this.thisMenu();
    this.seeChanges(s);
  }

  public void addMedia() {
    System.out.println("\n1: Book \n2: CD \n3: Record\n4: Magazine");

    String userInput = s.next();
    switch (userInput.toLowerCase()) {
      case "1":
        //function to add book
        break;
      case "2":
        //function to add CD
        break;
      case "3":
        //function to add Record
        break;
      case "4":
        //function to add Magazine
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

  public void removeMedia() {
    //call the function removing media
    this.seeChanges(s);
    this.thisMenu();
  }

  public void displayChanges() {
    //SELECT * FROM Media
  }

}
