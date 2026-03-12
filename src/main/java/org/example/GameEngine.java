package org.example;
import java.util.HashSet;
import java.util.Set;

public class GameEngine {
    private CityRepository repository;
    private Set<String> usedCities;
    private int playerScore;
    private int computerScore;
    private String lastChar;

    public GameEngine(CityRepository repository) {
        this.repository = repository;
        this.usedCities = new HashSet<>();
        this.playerScore = 0;
        this.computerScore = 0;
        this.lastChar = "";
    }

    public String validateAndPlayCity(String input) {
        if (input.isBlank()) {
            return "error:Введіть місто!";
        }

        String city = input.trim().toLowerCase();

        if (!repository.cityExists(city)) {
            return "error:Такого міста немає!";
        }

        if (usedCities.contains(city)) {
            return "error:Це місто вже було!";
        }

        if (!lastChar.isEmpty() && !city.startsWith(lastChar)) {
            return "error:Місто має починатися на: " + lastChar.toUpperCase();
        }

        usedCities.add(city);
        playerScore++;
        lastChar = getLastValidChar(city);

        String computerAnswer = getComputerCity();
        if (computerAnswer == null) {
            return "win:Вітаємо! Ви виграли!";
        }

        computerScore++;
        return "ok:" + computerAnswer;
    }

    private String getLastValidChar(String word) {
        for (int i = word.length() - 1; i >= 0; i--) {
            char c = word.charAt(i);
            if ("ьй'-і ".indexOf(c) == -1) {
                return String.valueOf(c);
            }
        }
        return "а";
    }

    private String getComputerCity() {
        for (String city : repository.getAllCities()) {
            if (!usedCities.contains(city) && city.startsWith(lastChar)) {
                usedCities.add(city);
                lastChar = getLastValidChar(city);
                return city;
            }
        }
        return null;
    }

    public String getScore() {
        return "Ви: " + playerScore + " | Комп'ютер: " + computerScore;
    }

    public void resetGame() {
        usedCities.clear();
        playerScore = 0;
        computerScore = 0;
        lastChar = "";
    }
}