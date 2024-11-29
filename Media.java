import java.util.Scanner;

public class Media extends Menu{
  Scanner s;
  Application app;

  public Media(Scanner s, Application a) {
    this.s = s;
    this.app = a;
  }

  public void thisMenu() {
    System.out.println("\nInput 1, 2, or 3");
    System.out.println("\nClose to close the application, back to go to the original menu");
    System.out.println("\n1: Add or remove media from the library");
    System.out.println("\n2: View the library's checked out media");
    System.out.println("\n1: Edit location of the library's media");
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
      case "back":
        app.startMenu();
        break;
      default:
        System.out.println("invalid input");
        this.thisMenu();
    }
  }


  public void addOrRemoveMedia(){
    System.out.println("\n1: Add media \n2: Remove Media");

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
        this.addOrRemoveMedia();
    }
    app.startMenu();
  }

  public void viewCheckedOutMedia(){
    //call the function that shows checked out media
    app.startMenu();
  }

  public void changedMediaLocation(){
    //call the function that allows the user to change the location of media
    app.startMenu();
  }
}
