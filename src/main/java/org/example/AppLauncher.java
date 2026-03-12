package org.example;
import javax.swing.*;

public class AppLauncher {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            CityRepository repository = new CityRepository();
            GameEngine gameEngine = new GameEngine(repository);

            showWelcome(repository, gameEngine);
        });
    }

    private static void showWelcome(CityRepository repository, GameEngine gameEngine) {
        new WelcomeView(() -> {
            gameEngine.resetGame();
            new GameView(gameEngine, () -> showWelcome(repository, gameEngine));
        });
    }
}