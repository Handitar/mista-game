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
                    cities.add(line.trim().toLowerCase());
                }
            }
        } catch (IOException e) {
            System.err.println("Помилка завантаження міст: " + e.getMessage());
            loadDefaultCities();
        }
    }

    private void loadDefaultCities() {
        cities.add("київ");
        cities.add("львів");
        cities.add("тернопіль");
        cities.add("одеса");
        cities.add("харків");
        cities.add("дніпро");
        cities.add("запоріжжя");
        cities.add("маріуполь");
        cities.add("чернігів");
        cities.add("житомир");
        cities.add("херсон");
        cities.add("івано-франківськ");
        cities.add("полтава");
        cities.add("черкаси");
        cities.add("суми");
        cities.add("вінниця");
        cities.add("ужгород");
        cities.add("луцьк");
        cities.add("кам'янець-подільський");
    }

    public boolean cityExists(String city) {
        return cities.contains(city.toLowerCase());
    }

    public Set<String> getAllCities() {
        return new HashSet<>(cities);
    }
}