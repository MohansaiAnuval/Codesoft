import java.util.*;

public class CourseRegistraction {
  Scanner sc = new Scanner(System.in);
  Map<String, Registration> registrations = new HashMap<>();

  public CourseRegistraction() {
    while (true) {
      System.out.println("\nChoose an option:");
      System.out.println("1. New Registration");
      System.out.println("2. Login");
      System.out.println("3. Exit");

      int option = sc.nextInt();
      sc.nextLine(); // Consume newline

      switch (option) {
        case 1:
          Student_Details();
          break;
        case 2:
          login();
          break;
        case 3:
          System.out.println("Exiting...");
          return;
        default:
          System.out.println("Invalid option. Please choose again.");
      }
    }
  }

  public void login() {
    System.out.println("Enter your student id: ");
    String studentId = sc.nextLine();

    System.out.println("Enter your course id: ");
    String courseId = sc.nextLine();

    String key = studentId + "-" + courseId;
    Registration registration = registrations.get(key);
    if (registration != null) {
      System.out.println("\nStudent Details:");
      System.out.println("Fullname: " + registration.getFullname());
      System.out.println("Student ID: " + registration.getId());
      System.out.println("Phone No: " + registration.getPhoneNo());
      System.out.println("Email: " + registration.getEmail());
      System.out.println("Highest Qualification: " + registration.getEducation());
      System.out.println("Year of Passing: " + registration.getYear_of_passing());
      System.out.println("Selected Course ID: " + registration.getSelectedCourse().getCourseId());
      System.out.println("Selected Course Name: " + registration.getSelectedCourse().getCourseName());
    } else {
      System.out.println("No registration found for Student ID: " + studentId + " and Course ID: " + courseId);
    }
  }

  public static class Course {
    private String courseId;
    private String courseName;

    public Course(String courseId, String courseName) {
      this.courseId = courseId;
      this.courseName = courseName;
    }

    public String getCourseId() {
      return courseId;
    }

    public void setCourseId(String courseId) {
      this.courseId = courseId;
    }

    public String getCourseName() {
      return courseName;
    }

    public void setCourseName(String courseName) {
      this.courseName = courseName;
    }
  }

  public void Student_Details() {
    System.out.println("Enter Fullname: ");
    String fullname = sc.nextLine();

    System.out.println("Enter Student Id: ");
    String Id = sc.nextLine();

    System.out.println("Enter Phone No: ");
    String PhoneNo = sc.nextLine();

    System.out.println("Enter Email: ");
    String email = sc.nextLine();

    System.out.println("Enter Highest Qualification: ");
    String Education = sc.nextLine();

    System.out.println("Enter Year of Passing: ");
    String Year_of_passing = sc.nextLine();

    System.out.println("Select Your Course");
    displayCourseNames(); // Display course names only
    System.out.print("Please enter the number of the course from the above available courses: ");
    int userOption = sc.nextInt();
    Course selectedCourse = selectCourse(userOption);

    // Store registration in map
    String key = Id + "-" + selectedCourse.getCourseId();
    registrations.put(key, new Registration(fullname, Id, PhoneNo, email, Education, Year_of_passing, selectedCourse));

    System.out.println("Registered successfully!");

    System.out.println("Press 1 To Register");
    int option = sc.nextInt();
    if (option == 1) {
      Register(fullname, Id, selectedCourse, PhoneNo, email, Education, Year_of_passing);
    }
  }

  public static Map<String, Course> listOfCourses() {
    Map<String, Course> courses = new HashMap<>();
    courses.put("CS101", new Course("CS101", "1. Java Programming"));
    courses.put("CS102", new Course("CS102", "2. Python Programming"));
    courses.put("CS103", new Course("CS103", "3. C/C++ Programming"));
    courses.put("WD101", new Course("WD101", "4. Web Development (HTML/CSS/JavaScript)"));
    courses.put("FS101", new Course("FS101", "5. Full-Stack Development"));
    courses.put("MA101", new Course("MA101", "6. Mobile App Development (iOS/Android)"));
    courses.put("DSA101", new Course("DSA101", "7. Data Structures and Algorithms"));
    courses.put("DB101", new Course("DB101", "8. Database Management and SQL"));
    courses.put("SE101", new Course("SE101", "9. Software Engineering"));
    courses.put("CC101", new Course("CC101", "10. Cloud Computing (AWS, Azure, Google Cloud)"));
    return courses;
  }

  public static void displayCourseNames() {
    Map<String, Course> courses = listOfCourses();
    for (Course course : courses.values()) {
      System.out.println(course.getCourseName());
    }
  }

  public static Course selectCourse(int option) {
    switch (option) {
      case 1:
        return new Course("CS101", "Java Programming");
      case 2:
        return new Course("CS102", "Python Programming");
      case 3:
        return new Course("CS103", "C/C++ Programming");
      case 4:
        return new Course("WD101", "Web Development (HTML/CSS/JavaScript)");
      case 5:
        return new Course("FS101", "Full-Stack Development");
      case 6:
        return new Course("MA101", "Mobile App Development (iOS/Android)");
      case 7:
        return new Course("DSA101", "Data Structures and Algorithms");
      case 8:
        return new Course("DB101", "Database Management and SQL");
      case 9:
        return new Course("SE101", "Software Engineering");
      case 10:
        return new Course("CC101", "Cloud Computing (AWS, Azure, Google Cloud)");
      default:
        return new Course("", "No Course Selected");
    }
  }

  public void Register(String fullname, String Id, Course selectedCourse, String PhoneNo, String email,
      String Education, String Year_of_passing) {
    // Generate random ID for registration
    String registrationId = UUID.randomUUID().toString();

    // Display registration details
    System.out.println("\nRegistration Details:");
    System.out.println("Registration ID: " + registrationId);
    System.out.println("Student Name: " + fullname);
    System.out.println("Student ID: " + Id);
    System.out.println("Selected Course ID: " + selectedCourse.getCourseId());
    System.out.println("Selected Course Name: " + selectedCourse.getCourseName());
    System.out.println("Phone Number: " + PhoneNo);
    System.out.println("Email: " + email);
    System.out.println("Education: " + Education);
    System.out.println("Year of Passing: " + Year_of_passing);
    System.out.println("Registered Status: Yes");

    // Prompt for course removal
    System.out.println("\nDo you want to remove the course? (1. Yes / 2. No)");
    int choice = sc.nextInt();
    sc.nextLine(); // Consume newline

    if (choice == 1) {
      // Remove course
      String key = Id + "-" + selectedCourse.getCourseId();
      registrations.remove(key);
      System.out.println("Course removed. Registered Status: No Course Selected");
    }
  }

  public static void main(String[] args) {
    new CourseRegistraction();
  }

  public static class Registration {
    private String fullname;
    private String id;
    private String phoneNo;
    private String email;
    private String education;
    private String year_of_passing;
    private Course selectedCourse;

    public Registration(String fullname, String id, String phoneNo, String email, String education,
        String year_of_passing, Course selectedCourse) {
      this.fullname = fullname;
      this.id = id;
      this.phoneNo = phoneNo;
      this.email = email;
      this.education = education;
      this.year_of_passing = year_of_passing;
      this.selectedCourse = selectedCourse;
    }

    public String getFullname() {
      return fullname;
    }

    public String getId() {
      return id;
    }

    public String getPhoneNo() {
      return phoneNo;
    }

    public String getEmail() {
      return email;
    }

    public String getEducation() {
      return education;
    }

    public String getYear_of_passing() {
      return year_of_passing;
    }

    public Course getSelectedCourse() {
      return selectedCourse;
    }
  }
}
