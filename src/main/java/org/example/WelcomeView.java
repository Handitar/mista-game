package org.example;
import javax.swing.*;
import java.awt.*;

public class WelcomeView extends JFrame {
    private Runnable onStartGame;

    public WelcomeView(Runnable onStartGame) {
        this.onStartGame = onStartGame;
        setupUI();
    }

    private void setupUI() {
        setTitle("Міста");
        setSize(400, 100);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout());
        JLabel label = new JLabel("Вітаємо вас у грі дитинства і всіх розумників!", JLabel.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 14));

        JButton startBtn = new JButton("Ок");
        startBtn.setFont(new Font("Arial", Font.BOLD, 14));
        startBtn.addActionListener(e -> {
            dispose();
            onStartGame.run();
        });

        panel.add(label, BorderLayout.CENTER);
        panel.add(startBtn, BorderLayout.SOUTH);
        add(panel);
        setVisible(true);
    }
}