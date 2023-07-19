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
