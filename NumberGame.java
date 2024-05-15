import java.util.Scanner;

class NumberGame {
  static int attempts = 3;
  static int score = 0;
  static Scanner sc = new Scanner(System.in);
  static boolean exitLoop = false;

  public static void main(String[] args) {
    System.out.println("***** Welcome to guess the number game *****");
    System.out.println("Press 1: For menu\nPress 2: To start the Game\nType exit: To Exit\nPress 3:To reset the score");
    part1();
  }

  public static void part1() {
    outer: while (!exitLoop) {
      System.out.print("Enter the Option: ");
      String input = sc.nextLine().trim();
      switch (input) {
        case "1":
          menu();
          break;
        case "2":
          Game();
          break;
        case "3":
          score = 0;
          break;
        case "exit":
          exitLoop = true;
          break outer;
        default:
          System.out.println("Please enter a valid option to continue..");
          break;
      }
    }
  }

  public static void menu() {
    System.out.println("****** High Score = " + score + "******");
    System.out.println(
        "****** About ******\nThis Game is all about Guessing a number which is Randomly generated between a range of numbers ");
  }

  public static void Game() {
    while (true) {
      System.out.println();
      System.out.println("********* Start the Game *********");
      int randomNumber = (int) Math.floor(Math.random() * 100) + 1;
      System.out.println("Attempts: " + attempts + "                          Score: " + score);
      System.out.println("Guess the number between 1 to 100");
      System.out.println();

      while (attempts > 0) {
        System.out.print("User: ");
        try {
          int userGuess = Integer.parseInt(sc.nextLine());
          if (userGuess >= 1 && userGuess <= 100) {
            if (randomNumber == userGuess) {
              System.out.println();
              System.out.println("Congratulations! Your guess was right");
              score++;
              System.out.println("Your Score is " + score);
              break;
            } else {
              System.out.println("***** Your guess is wrong. Try again.. *****");
              attempts--;
              System.out.println("Attempts left " + attempts);
              if (attempts == 0) {
                System.out.println();
                System.out.println("The correct answer is " + randomNumber);
                System.out.println("Press 5: To restart the Game\nPress 6: Return to main menu");
                int option = Integer.parseInt(sc.nextLine());
                if (option == 5) {
                  attempts = 3;
                } else if (option == 6) {
                  return;
                } else {
                  System.out.println("Please enter a valid option to continue");
                }
              }
            }
          } else {
            System.out.println("Please enter a number between 1 and 100.");
          }
        } catch (NumberFormatException e) {
          System.out.println("Please enter a valid integer.");
        }
      }
    }
  }
}