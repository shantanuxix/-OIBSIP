import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class NumberGuessingGame extends JFrame {
    private static final int MAX_ATTEMPTS = 7;

    private int secretNumber;
    private int attemptsLeft;
    private boolean gameOver;

    private JLabel messageLabel;
    private JTextField guessField;
    private JButton submitButton;
    private JLabel attemptsLeftLabel;

    public NumberGuessingGame() {
        // Set up the JFrame
        setTitle("Number Guessing Game");
        setSize(300, 200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridLayout(4, 1));

        // Initialize GUI components with values
        Font labelFont = new Font("Arial", Font.PLAIN, 18);
        Font buttonFont = new Font("Arial", Font.BOLD, 16);

        messageLabel = new JLabel("Guess a number between 1 and 100:");
        messageLabel.setFont(labelFont);
        messageLabel.setHorizontalAlignment(JLabel.CENTER);
        guessField = new JTextField(10);
        guessField.setFont(labelFont);
        submitButton = new JButton("Submit");
        submitButton.setFont(buttonFont);
        attemptsLeftLabel = new JLabel("Attempts left: " + attemptsLeft);
        attemptsLeftLabel.setFont(labelFont);

           // Initialize game variables and UI components
          resetGame();

        // Add padding and border to the components
        messageLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        guessField.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        attemptsLeftLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Add components to the JFrame
        add(messageLabel);
        add(guessField);
        add(submitButton);
        add(attemptsLeftLabel);

        // Set background and foreground colors for components
        Color backgroundColor = Color.WHITE;
        Color foregroundColor = Color.BLACK;
        Color buttonColor = new Color(0, 128, 0); // Dark Green

        messageLabel.setBackground(backgroundColor);
        messageLabel.setForeground(foregroundColor);
        guessField.setBackground(backgroundColor);
        guessField.setForeground(foregroundColor);
        attemptsLeftLabel.setBackground(backgroundColor);
        attemptsLeftLabel.setForeground(foregroundColor);
        submitButton.setBackground(buttonColor);
        submitButton.setForeground(Color.WHITE);

        // Add ActionListener to the submitButton
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!gameOver) {
                    checkGuess();
                } else {
                    int option = JOptionPane.showConfirmDialog(NumberGuessingGame.this,
                            "Do you want to play again?", "Play Again", JOptionPane.YES_NO_OPTION);
                    if (option == JOptionPane.YES_OPTION) {
                        resetGame();
                    } else {
                        System.exit(0);
                    }
                }
            }
        });

        // Set background color for the JFrame
        getContentPane().setBackground(backgroundColor);
        getRootPane().setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        setVisible(true);
    }

    private void resetGame() {
        secretNumber = generateRandomNumber(1, 100);
        attemptsLeft = MAX_ATTEMPTS;
        gameOver = false;
        messageLabel.setText("Guess a number between 1 and 100:");
        attemptsLeftLabel.setText("Attempts left: " + attemptsLeft);
        guessField.setText("");
        guessField.setEditable(true);
        submitButton.setText("Submit");
    }

    private int generateRandomNumber(int min, int max) {
        return new Random().nextInt(max - min + 1) + min;
    }

    private void checkGuess() {
        try {
            int playerGuess = Integer.parseInt(guessField.getText());

            if (playerGuess == secretNumber) {
                displayMessage("Congratulations! You guessed the number in " + (MAX_ATTEMPTS - attemptsLeft + 1) + " attempts.");
                gameOver = true;
                guessField.setEditable(false);
                submitButton.setText("Play Again");
            } else if (playerGuess < secretNumber) {
                displayMessage("Try again with a higher number.");
            } else {
                displayMessage("Try again with a lower number.");
            }

            attemptsLeft--;
            attemptsLeftLabel.setText("Attempts left: " + attemptsLeft);

            if (attemptsLeft == 0 && !gameOver) {
                displayMessage("Game Over! The secret number was: " + secretNumber);
                guessField.setEditable(false);
                submitButton.setText("Play Again");
                gameOver = true;
            }
        } catch (NumberFormatException ex) {
            displayMessage("Invalid input. Please enter a valid number.");
        }
    }

    private void displayMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new NumberGuessingGame();
            }
        });
    }
}
