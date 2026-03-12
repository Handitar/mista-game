package org.example;
import javax.swing.*;
import java.awt.*;

public class GameView extends JFrame {
    private JTextField cityInput;
    private JLabel computerAnswerLabel;
    private JButton moveButton;
    private JLabel pointsLabel;
    private GameEngine gameEngine;
    private Runnable onGameEnd;

    public GameView(GameEngine gameEngine, Runnable onGameEnd) {
        this.gameEngine = gameEngine;
        this.onGameEnd = onGameEnd;
        setupUI();
    }

    private void setupUI() {
        setTitle("Міста - Гра");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Рядок статистики
        pointsLabel = new JLabel("Ви: 0 | Комп'ютер: 0");
        pointsLabel.setFont(new Font("Arial", Font.BOLD, 14));
        pointsLabel.setHorizontalAlignment(JLabel.CENTER);
        JPanel pointsPanel = new JPanel();
        pointsPanel.add(pointsLabel);

        // Рядок 1: Поле введення + Напис
        JPanel row1 = new JPanel(new BorderLayout(10, 0));
        cityInput = new JTextField(15);
        cityInput.setFont(new Font("Arial", Font.PLAIN, 14));
        cityInput.setPreferredSize(new Dimension(200, 25));
        cityInput.addActionListener(e -> playerMove());
        JLabel inputLabel = new JLabel("Введіть назву міста:");
        inputLabel.setFont(new Font("Arial", Font.PLAIN, 12));

        JPanel inputPanel = new JPanel();
        inputPanel.add(cityInput);
        JPanel labelPanel = new JPanel();
        labelPanel.add(inputLabel);

        row1.add(inputPanel, BorderLayout.CENTER);
        row1.add(labelPanel, BorderLayout.EAST);

        // Рядок 2: Кнопка + Відповідь комп'ютера
        JPanel row2 = new JPanel(new BorderLayout(10, 0));
        moveButton = new JButton("Зробити хід");
        moveButton.setFont(new Font("Arial", Font.BOLD, 12));
        moveButton.setPreferredSize(new Dimension(100, 25));
        moveButton.addActionListener(e -> playerMove());
        computerAnswerLabel = new JLabel("Комп'ютер: ...");
        computerAnswerLabel.setFont(new Font("Arial", Font.ITALIC, 12));

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(moveButton);
        JPanel computerPanel = new JPanel();
        computerPanel.add(computerAnswerLabel);

        row2.add(buttonPanel, BorderLayout.WEST);
        row2.add(computerPanel, BorderLayout.CENTER);

        mainPanel.add(pointsPanel);
        mainPanel.add(Box.createVerticalStrut(20));
        mainPanel.add(row1);
        mainPanel.add(Box.createVerticalStrut(10));
        mainPanel.add(row2);
        mainPanel.add(Box.createVerticalGlue());

        add(mainPanel);
        setVisible(true);
        cityInput.requestFocus();
    }

    private void playerMove() {
        String input = cityInput.getText().trim();
        cityInput.setText("");

        if (input.equalsIgnoreCase("здаюсь")) {
            endGame("Комп'ютер переміг!");
            return;
        }

        String result = gameEngine.validateAndPlayCity(input);

        if (result.startsWith("error:")) {
            JOptionPane.showMessageDialog(this, result.substring(6), "Помилка", JOptionPane.ERROR_MESSAGE);
        } else if (result.startsWith("win:")) {
            endGame(result.substring(4));
        } else if (result.startsWith("ok:")) {
            computerAnswerLabel.setText("Комп'ютер: " + result.substring(3));
            pointsLabel.setText(gameEngine.getScore());
        }

        cityInput.requestFocus();
    }

    private void endGame(String message) {
        String finalMessage = message + "\n\n" + gameEngine.getScore();
        int option = JOptionPane.showConfirmDialog(this, finalMessage + "\n\nЗіграти ще?", "Гра закінчена", JOptionPane.YES_NO_OPTION);

        if (option == JOptionPane.YES_OPTION) {
            gameEngine.resetGame();
            computerAnswerLabel.setText("Комп'ютер: ...");
            pointsLabel.setText(gameEngine.getScore());
            cityInput.setText("");
            cityInput.requestFocus();
        } else {
            dispose();
            onGameEnd.run();
        }
    }
}
