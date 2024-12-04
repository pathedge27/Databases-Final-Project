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
    String userInput = s.next();

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
    //call the function that changes the date of the given event
    this.thisMenu();
    this.seeChanges(s);
  }

  public void viewEventInfo(){
    //call the function that outputs the info for a particular event
    this.thisMenu();
  }

  public void addOrRemoveEvent(){
    System.out.println("\n1: Add event \n2: Remove event");

    String userInput = s.next();
    switch (userInput.toLowerCase()) {
      case "1":
        //call the function that adds event
        break;
      case "2":
        //call the function that removes event
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
    this.seeChanges(s);
  }

  public void displayChanges() {
    //SELECT * FROM Event
  }

}
