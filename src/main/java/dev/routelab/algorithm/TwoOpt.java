package dev.routelab.algorithm;

import dev.routelab.model.City;
import dev.routelab.model.Tour;

import java.util.ArrayList;
import java.util.List;

public class TwoOpt {

    public static void reverseSegment(
            List<City> cities,
            int start,
            int end
    ) {
        while (start < end) {
            // Swap cities[start] and cities[end]
            Tour.swapCities(cities, start, end);

            // Move start and end indices towards each other
            start++;
            end--;
        }
    }

    public static List<City> solve(List<City> cities) {

        // Mutable copy of the input list
        List<City> bestTour = new ArrayList<>(cities);

        // No meaningful solution for less than 4 cities
        if (bestTour.size() < 4) {
            return bestTour;
        }

        // Initialize the best distance
        double bestDistance = Tour.calculateDistance(bestTour);
        boolean improved = true;

        while (improved) {

            improved = false;

            // Fix first city
            for (int i = 1; i < bestTour.size() - 1; i++) {

                for (int j = i + 1; j < bestTour.size(); j++) {
                    List<City> candidate = new ArrayList<>(bestTour);

                    // Reverse the segment i...j and calculate the new distance
                    reverseSegment(candidate, i, j);
                    double candidateDistance = Tour.calculateDistance(candidate);

                    if (candidateDistance < bestDistance - 1e-9) {
                        // Update values of best tour and restart
                        bestTour = candidate;
                        bestDistance = candidateDistance;
                        improved = true;
                        break;
                    }
                }

                if (improved) {
                    break;
                }
            }
        }

        return bestTour;
    }
}
