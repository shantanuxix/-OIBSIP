import java.util.*;

class BankAccount {
    protected String name;
    private int balance;
    private String accountNumber;
    private ArrayList<String> history;
    private static Map<String, BankAccount> accounts = new HashMap<>();

    public BankAccount(String name, String accountNumber) {
        this.name = name;
        this.accountNumber = accountNumber;
        this.balance = 0;
        this.history = new ArrayList<>();
    }

    // Getter method for name field
    public String getName() {
        return name;
    }

    public void withdraw(int amount) {
        if (amount <= 0) {
            System.out.println("Invalid Withdraw: Negative or zero amount.");
            return;
        }

        if (amount > balance) {
            System.out.println("Failed Withdraw: Insufficient balance.");
            return;
        }

        balance -= amount;
        history.add("Withdraw: " + amount);
    }

    public void deposit(int amount) {
        if (amount <= 0) {
            System.out.println("Invalid Deposit: Negative or zero amount.");
            return;
        }

        balance += amount;
        history.add("Deposit: " + amount);
    }

    public void transfer(int amount, BankAccount receiver) {
        if (amount <= 0) {
            System.out.println("Invalid Transfer: Negative or zero amount.");
            return;
        }

        if (amount > balance) {
            System.out.println("Failed Transfer: Insufficient balance.");
            return;
        }

        balance -= amount;
        receiver.balance += amount;

        history.add("Transfer: " + amount + " to " + receiver.name + " (Account: " + receiver.accountNumber + ")");
        receiver.history.add("Received Transfer: " + amount + " from " + name + " (Account: " + accountNumber + ")");
    }

    public int getBalance() {
        return balance;
    }

    public ArrayList<String> getTransactionHistory() {
        return history;
    }

    public static BankAccount register(String name, String accountNumber) {
        BankAccount account = new BankAccount(name, accountNumber);
        accounts.put(accountNumber, account);
        return account;
    }

    public static BankAccount login(String accountNumber) {
        return accounts.get(accountNumber);
    }
}

public class ATMApp {
    private static Scanner sc = new Scanner(System.in);
    private static BankAccount currentAccount;

    public static void main(String[] args) {
        homepage();
    }

    public static void homepage() {
        System.out.println("WELCOME TO ATM INTERFACE");
        System.out.println("--------------------------");
        System.out.println("select option :");
        System.out.println("1. Register");
        System.out.println("2. Exit");
        System.out.print("Enter choice: ");
        int choice = sc.nextInt();
        sc.nextLine(); // Consume the newline character

        if (choice == 1) {
            register();
        } else if (choice == 2) {
            System.exit(0);
        } else {
            System.out.println("Invalid choice. Please select a value only from the given options.");
            homepage();
        }
    }

    public static void register() {
        System.out.println("---------------------------");
        System.out.print("Enter your name: ");
        String name = sc.nextLine();
        System.out.print("Enter username (Account number): ");
        String accountNumber = sc.nextLine();

        currentAccount = BankAccount.register(name, accountNumber);

        System.out.println("REGISTRATION SUCCESSFULLY!");
        System.out.println("---------------------------");

        prompt();
    }

    public static void prompt() {
        System.out.println("WELCOME " + currentAccount.name + "! TO ATM SYSTEM");
        System.out.println("---------------------");
        System.out.println("Select option : ");
        System.out.println("1. Withdraw");
        System.out.println("2. Deposit");
        System.out.println("3. Transfer");
        System.out.println("4. Check balance");
        System.out.println("5. Transaction History");
        System.out.println("6. Exit");
        System.out.print("Enter your choice: ");
        int choice = sc.nextInt();
        sc.nextLine(); // Consume the newline character

        switch (choice) {
            case 1:
                withdraw();
                break;
            case 2:
                deposit();
                break;
            case 3:
                transfer();
                break;
            case 4:
                checkBalance();
                break;
            case 5:
                transactionHistory();
                break;
            case 6:
                System.exit(0);
                break;
            default:
                System.out.println("Invalid choice. Please select a valid option.");
                prompt();
        }
    }

    public static void withdraw() {
        System.out.println("----------------");
        System.out.print("Enter amount to withdraw: ");
        int wcash = sc.nextInt();
        currentAccount.withdraw(wcash);
        System.out.println("---------------------------");
        prompt();
    }

    public static void deposit() {
        System.out.println("----------------");
        System.out.print("Enter amount to deposit: ");
        int dcash = sc.nextInt();
        currentAccount.deposit(dcash);
        System.out.println("---------------------------");
        prompt();
    }

    public static void transfer() {
        System.out.println("----------------");
        System.out.print("Enter the receiving body (Account number): ");
        String receiverAccountNumber = sc.nextLine();
        BankAccount receiver = BankAccount.login(receiverAccountNumber);
        if (receiver == null) {
            System.out.println("Receiver account not found.");
        } else {
            System.out.print("Enter the amount to be transferred: ");
            int tcash = sc.nextInt();
            currentAccount.transfer(tcash, receiver);
            System.out.println("---------------------------");
        }
        prompt();
    }

    public static void checkBalance() {
        System.out.println("------------------");
        System.out.println("The available balance in the bank account: " + currentAccount.getBalance());
        System.out.println("---------------------------");
        prompt();
    }

    public static void transactionHistory() {
        System.out.println("---------------------");
        System.out.println("Transaction History:");
        ArrayList<String> history = currentAccount.getTransactionHistory();
        if (history.isEmpty()) {
            System.out.println("No transaction history available.");
        } else {
            for (String transaction : history) {
                System.out.println(transaction);
            }
        }
        System.out.println("---------------------");
        prompt();
    }
}
