import java.util.Scanner;

class Students_Marks_Calculator {
  static int Total_marks = 0;
  static int total_subjects;
  static double[] Marks;
  static String[] Subjects;
  static String[] Grades;
  static double Percentage;
  static String Grade;

  public static void StudentsAndMarks() {

    Scanner sc = new Scanner(System.in);

    System.out.print("Total number of Subjects: ");
    total_subjects = sc.nextInt();
    Marks = new double[total_subjects];
    Subjects = new String[total_subjects];
    Grades = new String[total_subjects];

    System.out.println("********** Enter subject and Subject Marks below **********");

    for (int i = 0; i < total_subjects; i++) {
      System.out.println("***** Enter Subject Name and marks *****");
      Subjects[i] = sc.next();
      Marks[i] = sc.nextDouble();
      Total_marks += Marks[i];
    }

    // System.out.println("Totals marks: " + Total_marks);
    Percentage = (Total_marks / total_subjects);
    System.out.println();
    System.out.println("********** Number of Marks Obtained by student **********");
    System.out.println("----------------------------");
    System.out.println("|SUBJECT | MARKS | GRADE |");

    for (int i = 0; i < total_subjects; i++) {
      Grades[i] = calculateGrade(Marks[i]);
      System.out.println("---------------------");
      System.out
          .println(Subjects[i] + " | " + Marks[i] + " | " + Grades[i] + " | ");
    }
    System.out.println("---------------------");
    Grade = calculateGrade(Percentage);
  }

  public static String calculateGrade(double marks) {
    if (marks >= 90) {
      return "A+";
    } else if (marks >= 85) {
      return "A";
    } else if (marks >= 75) {
      return "B+";
    } else if (marks >= 65) {
      return "B";
    } else if (marks >= 55) {
      return "C+";
    } else if (marks >= 45) {
      return "C";
    } else if (marks >= 35) {
      return "D";
    } else if (marks >= 25) {
      return "E";
    } else {
      return "F";
    }
  }

  public static void Display() {
    System.out.println("********** Overall Performance **********");
    System.out.println(
        " Marks: " + Total_marks + "     " + "Average percentage: " + Percentage + "     " + " Grade: " + Grade);
  }

  public static void main(String[] args) {
    StudentsAndMarks();
    Display();

  }
}
