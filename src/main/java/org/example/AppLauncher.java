package org.example;
import javax.swing.*;
import java.awt.*;
import java.util.*;

public class AppLauncher extends JFrame {
    private JFrame welcomeFrame;
    private JFrame gameFrame;
    private JTextField cityInput;
    private JLabel computerAnswerLabel;
    private JButton moveButton;
    private JLabel pointsLabel;

    private Set<String> cities;
    private Set<String> usedCities;
    private int playerScore = 0;
    private int computerScore = 0;
    private String lastChar = "";

    public AppLauncher() {
        // колекція з містами
        cities = new HashSet<>(Arrays.asList(
                "Київ", "Львів", "Тернопіль", "Одеса", "Харків", "Дніпро", "Запоріжжя",
                "Маріуполь", "Чернігів", "Житомир", "Херсон", "Івано-Франківськ", "Полтава",
                "Черкаси", "Суми", "Вінниця", "Ужгород", "Луцьк", "Кам'янець-Подільський"
        ));
        usedCities = new HashSet<>();
        showWelcomeFrame();
    }

    // інтро
    private void showWelcomeFrame() {
        welcomeFrame = new JFrame("Міста");
        welcomeFrame.setSize(400, 100);
        welcomeFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        welcomeFrame.setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout());
        JLabel label = new JLabel("Вітаємо вас у грі дитинства і всіх розумників!", JLabel.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 14));

        JButton startBtn = new JButton("Ок");
        startBtn.setFont(new Font("Arial", Font.BOLD, 14));
        startBtn.addActionListener(e -> {
            welcomeFrame.dispose();
            showGameFrame();
        });

        panel.add(label, BorderLayout.CENTER);
        panel.add(startBtn, BorderLayout.SOUTH);
        welcomeFrame.add(panel);
        welcomeFrame.setVisible(true);
    }

    // основне вікно
    private void showGameFrame() {
        gameFrame = new JFrame("Міста");
        gameFrame.setSize(400, 500);
        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameFrame.setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // перший рядок (поле введення та напис)
        JPanel row1 = new JPanel(new BorderLayout(10, 0));
        cityInput = new JTextField(15);
        cityInput.setFont(new Font("Arial", Font.PLAIN, 14));
        cityInput.setPreferredSize(new Dimension(200, 25));
        JLabel inputLabel = new JLabel("Введіть назву міста:");
        inputLabel.setFont(new Font("Arial", Font.PLAIN, 12));

        JPanel inputPanel = new JPanel();
        inputPanel.add(cityInput);
        JPanel labelPanel = new JPanel();
        labelPanel.add(inputLabel);

        row1.add(inputPanel, BorderLayout.CENTER);
        row1.add(labelPanel, BorderLayout.EAST);

        // другий рядок (кнопка та відповідь)
        JPanel row2 = new JPanel(new BorderLayout(10, 0));
        moveButton = new JButton("Зробити хід");
        moveButton.setFont(new Font("Arial", Font.BOLD, 12));
        moveButton.setPreferredSize(new Dimension(120, 25));
        moveButton.addActionListener(e -> playerMove());
        computerAnswerLabel = new JLabel("Комп'ютер: ...");
        computerAnswerLabel.setFont(new Font("Arial", Font.ITALIC, 12));

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(moveButton);
        JPanel computerPanel = new JPanel();
        computerPanel.add(computerAnswerLabel);

        row2.add(buttonPanel, BorderLayout.WEST);
        row2.add(computerPanel, BorderLayout.CENTER);

        // статистика
        pointsLabel = new JLabel("Ви: 0 | Комп'ютер: 0");
        pointsLabel.setFont(new Font("Arial", Font.BOLD, 14));
        pointsLabel.setHorizontalAlignment(JLabel.CENTER);

        JPanel pointsPanel = new JPanel();
        pointsPanel.add(pointsLabel);

        mainPanel.add(pointsPanel);
        mainPanel.add(Box.createVerticalStrut(20));
        mainPanel.add(row1);
        mainPanel.add(Box.createVerticalStrut(10));
        mainPanel.add(row2);
        mainPanel.add(Box.createVerticalGlue());

        gameFrame.add(mainPanel);
        gameFrame.setVisible(true);
    }

    // хід гравця
    private void playerMove() {
        String input = cityInput.getText().trim();
        cityInput.setText("");

        if (input.equalsIgnoreCase("здаюсь")) {
            endGame(false);
            return;
        }

        String city = input.substring(0, 1).toUpperCase() + input.substring(1).toLowerCase();

        if (!cities.contains(city)) {
            JOptionPane.showMessageDialog(gameFrame, "Такого міста немає!", "Помилка", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (usedCities.contains(city)) {
            JOptionPane.showMessageDialog(gameFrame, "Це місто вже було!", "Помилка", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!lastChar.isEmpty() && !city.toLowerCase().startsWith(lastChar)) {
            JOptionPane.showMessageDialog(gameFrame, "Місто має починатися на: " + lastChar.toUpperCase(), "Помилка", JOptionPane.ERROR_MESSAGE);
            return;
        }

        usedCities.add(city);
        playerScore++;
        lastChar = String.valueOf(city.charAt(city.length() - 1)).toLowerCase();

        String computerAnswer = getComputerCity();
        if (computerAnswer == null) {
            endGame(true);
        } else {
            computerAnswerLabel.setText("Комп'ютер: " + computerAnswer);
            computerScore++;
        }

        pointsLabel.setText("Ви: " + playerScore + " | Комп'ютер: " + computerScore);
    }

    // хід комп'ютера
    private String getComputerCity() {
        for (String city : cities) {
            if (!usedCities.contains(city) && city.toLowerCase().startsWith(lastChar)) {
                usedCities.add(city);
                lastChar = String.valueOf(city.charAt(city.length() - 1)).toLowerCase();
                return city;
            }
        }
        return null;
    }

    // результат
    private void endGame(boolean playerWon) {
        String message = playerWon ?
                "Вітаємо! Ви виграли!\n\nВаш рахунок: " + playerScore + "\nКомп'ютер: " + computerScore :
                "Комп'ютер переміг!\n\nВаш рахунок: " + playerScore + "\nКомп'ютер: " + computerScore;

        JOptionPane.showMessageDialog(gameFrame, message, "Гра закінчена", JOptionPane.INFORMATION_MESSAGE);
        gameFrame.dispose();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AppLauncher());
    }
}