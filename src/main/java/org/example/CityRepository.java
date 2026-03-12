package org.example;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;

public class CityRepository {
    private Set<String> cities;

    public CityRepository() {
        cities = new HashSet<>();
        loadCities();
    }

    private void loadCities() {
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream("cities.txt")) {
            if (inputStream == null) {
                System.err.println("Файл cities.txt не знайдено!");
                loadDefaultCities();
                return;
            }
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    cities.add(line.trim());
                }
            }
        } catch (IOException e) {
            System.err.println("Помилка завантаження міст: " + e.getMessage());
            loadDefaultCities();
        }
    }

    //на всякий випадок
    private void loadDefaultCities() {
        cities.add("Київ");
        cities.add("Львів");
        cities.add("Тернопіль");
        cities.add("Одеса");
        cities.add("Харків");
        cities.add("Дніпро");
        cities.add("Запоріжжя");
        cities.add("Маріуполь");
        cities.add("Чернігів");
        cities.add("Житомир");
        cities.add("Херсон");
        cities.add("Івано-Франківськ");
        cities.add("Полтава");
        cities.add("Черкаси");
        cities.add("Суми");
        cities.add("Вінниця");
        cities.add("Ужгород");
        cities.add("Луцьк");
        cities.add("Кам'янець-Подільський");
    }

    public boolean cityExists(String city) {
        return cities.contains(city);
    }

    public Set<String> getAllCities() {
        return new HashSet<>(cities);
    }
}