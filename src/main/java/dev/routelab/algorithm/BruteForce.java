package dev.routelab.algorithm;

import dev.routelab.model.City;
import dev.routelab.model.Tour;

import java.util.ArrayList;
import java.util.List;

public class BruteForce {

    public static void permute(
            List<City> cities,
            int start
    ) {

        // Recursive end condition
        if (start == cities.size()) {
            System.out.println(cities);
            return;
        }

        // Generate all permutations recursively
        for (int i = start; i < cities.size(); i++) {
            Tour.swapCities(cities, start, i);
            permute(cities, start + 1);

            // Undo the swap
            Tour.swapCities(cities, start, i);
        }
    }

    private static class BestResult {
        private List<City> tour;
        private double distance = Double.POSITIVE_INFINITY;
    }

    public static List<City> solve(List<City> cities) {

        if (cities.size() <= 1) {
            return new ArrayList<>(cities);
        }

        List<City> workingTour = new ArrayList<>(cities);
        BestResult bestResult = new BestResult();

        // Fix the first city --> reduce the search space
        permuteAndEvaluate(workingTour, 1, bestResult);

        return bestResult.tour;
    }

    private static void permuteAndEvaluate(
            List<City> cities,
            int start,
            BestResult best
    ) {
        if (start == cities.size()) {

            double distance = Tour.calculateDistance(cities);

            if (distance < best.distance) {
                // Update the best result
                best.tour = new ArrayList<>(cities);
                best.distance = distance;
            }

            return;
        }

        for (int i = start; i < cities.size(); i++) {
            // Swap cities at indices start and i
            Tour.swapCities(cities, start, i);

            // Recursively call permuteAndEvaluate with the modified list
            permuteAndEvaluate(cities, start + 1, best);

            // Undo the swap
            Tour.swapCities(cities, start, i);
        }
    }
}
