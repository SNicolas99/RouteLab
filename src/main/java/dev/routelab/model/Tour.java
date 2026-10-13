package dev.routelab.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Tour {

    private static final Random RANDOM = new Random();

    public static double calculateDistance(List<City> cities) {

        double distance = 0.0;

        for (int i = 0; i < cities.size(); i++) {
            City city1 = cities.get(i);
            City city2 = cities.get((i + 1) % cities.size());

            // Add distance between cities to total distance
            distance += city1.distanceTo(city2);
        }
        return distance;
    }

    public static void swapCities(List<City> cities, int i, int j) {
        // Temporarily store the value of the city at index i
        City temp = cities.get(i);

        cities.set(i, cities.get(j));
        cities.set(j, temp);

        // Alternatively: use Collections.swap(cities, i, j);
    }

    public static List<City> generateNeighbor(List<City> cities) {
        List<City> neighbor = new ArrayList<>(cities);

        // Make sure at least list has more than 1 city
        if (cities.size() <= 1) {
            return neighbor;
        }

        // Get two random indices for cities to be swapped
        int i = RANDOM.nextInt(cities.size());
        int j = RANDOM.nextInt(cities.size());

        while (i == j) {
            j = RANDOM.nextInt(cities.size());
        }

        // Swap cities at indices i and j
        swapCities(neighbor, i, j);

        return neighbor;
    }
}
