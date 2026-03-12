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

        String city = input.substring(0, 1).toUpperCase() + input.substring(1).toLowerCase();

        if (!repository.cityExists(city)) {
            return "error:Такого міста немає!";
        }

        if (usedCities.contains(city)) {
            return "error:Це місто вже було!";
        }

        if (!lastChar.isEmpty() && !city.toLowerCase().startsWith(lastChar)) {
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
        String w = word.toLowerCase();
        for (int i = w.length() - 1; i >= 0; i--) {
            char c = w.charAt(i);
            if ("ьй'-і ".indexOf(c) == -1) {
                return String.valueOf(c);
            }
        }
        return "а";
    }

    private String getComputerCity() {
        for (String city : repository.getAllCities()) {
            if (!usedCities.contains(city) && city.toLowerCase().startsWith(lastChar)) {
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
