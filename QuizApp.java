import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class QuizApp {

  private static final int Time_Limit = 10;
  private static final Scanner scanner = new Scanner(System.in);
  private static int score = 0;
  private static List<String> results = new ArrayList<>();

  public static void main(String[] args) {

    Map<String, String> quiz = new HashMap<>();

    quiz.put("What is the capital of France?\nA) Paris\nB) London\nC) Rome\nD) Berlin", "A");
    quiz.put("Which planet is known as the Red Planet?\nA) Earth\nB) Mars\nC) Jupiter\nD) Saturn", "B");
    quiz.put("Who wrote 'To Kill a Mockingbird'?\nA) Harper Lee\nB) Mark Twain\nC) J.K. Rowling\nD) Ernest Hemingway",
        "A");
    quiz.put(
        "What is the largest ocean on Earth?\nA) Atlantic Ocean\nB) Indian Ocean\nC) Arctic Ocean\nD) Pacific Ocean",
        "D");

    for (Map.Entry<String, String> entry : quiz.entrySet()) {
      String question = entry.getKey();
      String correctAnswer = entry.getValue();

      System.out.println(question);

      long startTime = System.currentTimeMillis();

      final boolean[] answered = { false };

      Thread inputThread = new Thread(() -> {
        String userAnswer = scanner.nextLine();
        long elapsedTime = (System.currentTimeMillis() - startTime) / 1000;

        synchronized (answered) {
          if (!answered[0] && elapsedTime < Time_Limit) {
            if (userAnswer.equalsIgnoreCase(correctAnswer)) {
              System.out.println("Correct!");
              score++;
              results.add("Correct: " + question + "\nChosen: " + userAnswer);
            } else {
              System.out.println("Incorrect. The correct answer was: " + correctAnswer);
              results.add("Incorrect: " + question + "\nChosen: " + userAnswer);
            }
            answered[0] = true;
          }
        }
      });

      inputThread.start();

      try {

        inputThread.join(Time_Limit * 1000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }

      synchronized (answered) {
        if (!answered[0]) {

          inputThread.interrupt();
          System.out.println("Time's up! The correct answer was: " + correctAnswer);
          results.add("Time's up: " + question + "\nChosen: (no answer)");
          answered[0] = true;
        }
      }

      System.out.println();
    }

    System.out.println("Your final score is: " + score);

    System.out.println("Results:");
    for (String result : results) {
      System.out.println(result);
    }
  }
}
