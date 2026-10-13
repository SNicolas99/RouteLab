package dev.routelab.algorithm;

import dev.routelab.model.City;
import dev.routelab.model.Tour;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SimulatedAnnealing {

    private static final Random RANDOM = new Random();

    public static boolean shouldAccept(
            double currentDistance,
            double newDistance,
            double temperature
    ) {
        // If new distance is better or equal to current distance, accept it
        if (newDistance <= currentDistance) {
            return true;
        }

        // Make sure temperature is positive
        if (temperature <= 0) {
            return false;
        }

        double delta = newDistance - currentDistance;
        double probability = Math.exp(-delta / temperature);

        // get random number between 0 and 1 to decide whether to accept or not
        return RANDOM.nextDouble() < probability;
    }


    public static List<City> solve(
            List<City> cities,
            double initialTemperature,
            double coolingRate
    ) {
        // Validate parameters
        if (!Double.isFinite(initialTemperature) || initialTemperature <= 0.01) {
            throw new IllegalArgumentException(
                    "Initial temperature must be finite and > 0.01"
            );
        }

        if (!Double.isFinite(coolingRate) || coolingRate <= 0 || coolingRate >= 1) {
            throw new IllegalArgumentException(
                    "Cooling rate must be between 0 and 1 (exclusive)"
            );
        }


        List<City> currentTour = new ArrayList<>(cities);
        List<City> bestTour = new ArrayList<>(currentTour);

        double currentDistance = Tour.calculateDistance(currentTour);
        double bestDistance = currentDistance;

        double temperature = initialTemperature;

        while (temperature > 0.01) {
            // Generate a new tour
            List<City> newTour = Tour.generateNeighbor(currentTour);
            double newDistance = Tour.calculateDistance(newTour);

            // Decide whether to accept the new tour
            if (shouldAccept(currentDistance, newDistance, temperature)) {
                // Save a copy of the new tour
                currentTour = newTour;
                currentDistance = newDistance;
            }

            // Update best tour if the new tour is better
            if (currentDistance < bestDistance) {
                bestTour = new ArrayList<>(currentTour);
                bestDistance = currentDistance;
            }

            // Decrease temperature
            temperature *= (1.0 - coolingRate);
        }

        // Return the best tour found
        return bestTour;
    }
}
