import java.io.*;
import java.util.*;

public class ATMInterface {
  private ArrayList<HashMap<String, String>> registeredUsers = new ArrayList<>();
  private Scanner sc = new Scanner(System.in);
  private static final String USER_DATA_FILE = "userdata.txt";
  private static final String LAST_ACCOUNT_NUMBER_FILE = "lastaccountnumber.txt";
  private static final String ACCOUNT_DATA_FILE = "useraccountdetails.txt";
  private int lastAccountNumber;

  public ATMInterface() {
    loadUserData();
    loadLastAccountNumber();
  }

  private void loadUserData() {
    try (BufferedReader reader = new BufferedReader(new FileReader(USER_DATA_FILE))) {
      String line;
      while ((line = reader.readLine()) != null) {
        String[] userData = line.split(",");
        HashMap<String, String> user = new HashMap<>();
        user.put("Full Name", userData[0]);
        user.put("Email", userData[1]);
        user.put("Password", userData[2]);
        registeredUsers.add(user);
      }
    } catch (IOException e) {
      System.out.println("Error loading user data: " + e.getMessage());
    }
  }

  private void loadLastAccountNumber() {
    try (BufferedReader reader = new BufferedReader(new FileReader(LAST_ACCOUNT_NUMBER_FILE))) {
      String line;
      if ((line = reader.readLine()) != null) {
        lastAccountNumber = Integer.parseInt(line.trim());
      } else {
        lastAccountNumber = 100001001;
      }
    } catch (IOException e) {
      System.out.println("Error loading last account number: " + e.getMessage());
    }
  }

  private void saveLastAccountNumber() {
    try (PrintWriter writer = new PrintWriter(new FileWriter(LAST_ACCOUNT_NUMBER_FILE))) {
      writer.println(lastAccountNumber);
    } catch (IOException e) {
      System.out.println("Error saving last account number: " + e.getMessage());
    }
  }

  public void userInterface() {
    while (true) {
      System.out.println("*** WELCOME TO BANKING SYSTEM ***");
      System.out.println();
      System.out.println("1. Register");
      System.out.println("2. Login");
      System.out.println("3. Exit");
      System.out.print("Enter your option: ");
      int option = sc.nextInt();
      sc.nextLine();

      switch (option) {
        case 1:
          register();
          break;
        case 2:
          login();
          break;
        case 3:
          exit();
          break;
        default:
          System.out.println("Invalid option. Please enter a valid option.");
      }
    }
  }

  public void register() {
    HashMap<String, String> userDetails = new HashMap<>();

    System.out.print("Full Name: ");
    String fullName = sc.nextLine();
    userDetails.put("Full Name", fullName);

    System.out.print("Email: ");
    String email = sc.nextLine();

    for (HashMap<String, String> user : registeredUsers) {
      if (user.get("Email").equals(email)) {
        System.out.println("User already exists!");
        return;
      }
    }

    System.out.print("Password: ");
    String password = sc.nextLine();
    userDetails.put("Email", email);
    userDetails.put("Password", password);

    registeredUsers.add(userDetails);
    saveUserData();

    System.out.println("Registration Successful!");
  }

  private void saveUserData() {
    try (PrintWriter writer = new PrintWriter(new FileWriter(USER_DATA_FILE))) {
      for (HashMap<String, String> user : registeredUsers) {
        writer.println(user.get("Full Name") + "," + user.get("Email") + "," + user.get("Password"));
      }
    } catch (IOException e) {
      System.out.println("Error saving user data: " + e.getMessage());
    }
  }

  public void login() {
    System.out.print("Email: ");
    String email = sc.nextLine();

    System.out.print("Password: ");
    String password = sc.nextLine();

    boolean userFound = false;
    for (HashMap<String, String> user : registeredUsers) {
      if (user.get("Email").equals(email) && user.get("Password").equals(password)) {
        System.out.println("Login Successful!");
        userFound = true;

        Bank account = new Bank();
        account.newBankAccount();
        break;
      }
    }

    if (!userFound) {
      System.out.println("Invalid email or password. Please try again.");
    }
  }

  public void exit() {
    System.out.println("Exiting the banking system. Goodbye!");
    saveLastAccountNumber();
    System.exit(0);
  }

  public class Bank {
    private ArrayList<HashMap<String, Object>> userAccountDetails = new ArrayList<>();
    private Scanner sc;

    public Bank() {
      sc = new Scanner(System.in);
      loadUserAccounts();
    }

    public void newBankAccount() {
      System.out.print("Enter Full Name: ");
      String fullName = sc.nextLine();

      int accountNumber = ++lastAccountNumber;

      for (HashMap<String, Object> account : userAccountDetails) {
        if ((int) account.get("AccountNumber") == accountNumber) {
          System.out.println("Account with this account number already exists!");
          return;
        }
      }

      System.out.print("Enter deposit amount: ");
      int deposit = sc.nextInt();
      sc.nextLine();

      System.out.print("Enter Security Pin: ");
      int securityPin = sc.nextInt();
      sc.nextLine();

      HashMap<String, Object> accountDetails = new HashMap<>();
      accountDetails.put("AccountNumber", accountNumber);
      accountDetails.put("FullName", fullName);
      accountDetails.put("Deposit", deposit);
      accountDetails.put("SecurityPin", securityPin);

      userAccountDetails.add(accountDetails);

      saveUserAccounts();

      System.out.println("Account Created Successfully");
      System.out.println("Your Account number is: " + accountNumber);

      ATMInterface.this.lastAccountNumber = lastAccountNumber;
      ATMInterface.this.saveLastAccountNumber();
      userOptions();
    }

    private void loadUserAccounts() {
      try (BufferedReader reader = new BufferedReader(new FileReader(ACCOUNT_DATA_FILE))) {
        String line;
        while ((line = reader.readLine()) != null) {
          String[] accountData = line.split(",");
          HashMap<String, Object> account = new HashMap<>();
          account.put("AccountNumber", Integer.parseInt(accountData[0]));
          account.put("FullName", accountData[1]);
          account.put("Deposit", Integer.parseInt(accountData[2]));
          account.put("SecurityPin", Integer.parseInt(accountData[3]));
          userAccountDetails.add(account);
        }
      } catch (IOException e) {
        System.out.println("Error loading user accounts: " + e.getMessage());
      }
    }

    private void userOptions() {
      while (true) {
        System.out.println("1. Deposit Money");
        System.out.println("2. Withdraw Money");
        System.out.println("3. Check Balance");
        System.out.println("4. Log out");

        System.out.print("Select Option: ");
        int option = sc.nextInt();
        sc.nextLine();

        switch (option) {
          case 1:
            depositMoney();
            break;
          case 2:
            withdrawMoney();
            break;
          case 3:
            checkBalance();
            break;
          case 4:
            return;
          default:
            System.out.println("Invalid option. Please select again.");
        }
      }
    }

    private void depositMoney() {
      System.out.print("Enter Account Number: ");
      int accountNumber = sc.nextInt();
      sc.nextLine();

      if (!validateSecurityPin(accountNumber)) {
        System.out.println("Invalid Security Pin!");
        return;
      }

      HashMap<String, Object> account = findAccount(accountNumber);
      if (account == null) {
        System.out.println("Account not found!");
        return;
      }

      System.out.print("Enter amount to deposit: ");
      int depositAmount = sc.nextInt();
      sc.nextLine();

      int currentDeposit = (int) account.get("Deposit");
      account.put("Deposit", currentDeposit + depositAmount);

      saveUserAccounts();
      System.out.println("Deposit Successful!");
    }

    private void withdrawMoney() {
      System.out.print("Enter Account Number: ");
      int accountNumber = sc.nextInt();
      sc.nextLine();

      if (!validateSecurityPin(accountNumber)) {
        System.out.println("Invalid Security Pin!");
        return;
      }

      HashMap<String, Object> account = findAccount(accountNumber);
      if (account == null) {
        System.out.println("Account not found!");
        return;
      }

      System.out.print("Enter amount to withdraw: ");
      int withdrawAmount = sc.nextInt();
      sc.nextLine();

      int currentDeposit = (int) account.get("Deposit");
      if (currentDeposit < withdrawAmount) {
        System.out.println("Insufficient balance!");
        return;
      }

      account.put("Deposit", currentDeposit - withdrawAmount);

      saveUserAccounts();
      System.out.println("Withdrawal Successful!");
    }

    private void checkBalance() {
      System.out.print("Enter Account Number: ");
      int accountNumber = sc.nextInt();
      sc.nextLine();

      if (!validateSecurityPin(accountNumber)) {
        System.out.println("Invalid Security Pin!");
        return;
      }

      HashMap<String, Object> account = findAccount(accountNumber);
      if (account == null) {
        System.out.println("Account not found!");
        return;
      }

      int currentDeposit = (int) account.get("Deposit");
      System.out.println("Current Balance: " + currentDeposit);
    }

    private boolean validateSecurityPin(int accountNumber) {
      System.out.print("Enter Security Pin: ");
      int securityPin = sc.nextInt();
      sc.nextLine();

      HashMap<String, Object> account = findAccount(accountNumber);
      if (account != null) {
        return (int) account.get("SecurityPin") == securityPin;
      }
      return false;
    }

    private HashMap<String, Object> findAccount(int accountNumber) {
      for (HashMap<String, Object> account : userAccountDetails) {
        if ((int) account.get("AccountNumber") == accountNumber) {
          return account;
        }
      }
      return null;
    }

    private void saveUserAccounts() {
      try (PrintWriter writer = new PrintWriter(new FileWriter(ACCOUNT_DATA_FILE))) {
        for (HashMap<String, Object> account : userAccountDetails) {
          writer.println(account.get("AccountNumber") + "," + account.get("FullName") + "," + account.get("Deposit")
              + "," + account.get("SecurityPin"));
        }
      } catch (IOException e) {
        System.out.println("Error saving user accounts: " + e.getMessage());
      }
    }
  }

  public static void main(String[] args) {
    ATMInterface atmInterface = new ATMInterface();
    atmInterface.userInterface();
  }
}
