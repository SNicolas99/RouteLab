package dev.routelab;

import dev.routelab.algorithm.BruteForce;
import dev.routelab.algorithm.SimulatedAnnealing;
import dev.routelab.model.City;
import dev.routelab.model.Tour;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        // ==========================================
        // 1. Initialize cities
        // ==========================================

        List<City> cities = new ArrayList<>(List.of(
                new City("A", 0, 0),
                new City("D", 4, 6),
                new City("E", 0, 5),
                new City("B", 4, 0),
                new City("F", 2, 2),
                new City("C", 7, 3)
        ));

        Collections.shuffle(cities);

        double initialDistance = Tour.calculateDistance(cities);

        // ==========================================
        // 2. Calculate optimal solution (Brute Force)
        // ==========================================

        long bruteForceStart = System.nanoTime();

        List<City> optimalTour = BruteForce.solve(cities);
        double optimalDistance = Tour.calculateDistance(optimalTour);

        double bruteForceMs =
                (System.nanoTime() - bruteForceStart) / 1_000_000.0;

        // ==========================================
        // 3. Simulated Annealing Benchmark
        // ==========================================

        int runs = 100;

        double initialTemperature = 100.0;
        double coolingRate = 0.003;

        double totalDistance = 0.0;
        double bestDistance = Double.POSITIVE_INFINITY;
        double worstDistance = Double.NEGATIVE_INFINITY;

        int optimalRuns = 0;

        long startTime = System.nanoTime();

        for (int i = 0; i < runs; i++) {

            List<City> result = SimulatedAnnealing.solve(
                    cities,
                    initialTemperature,
                    coolingRate
            );

            double distance = Tour.calculateDistance(result);

            totalDistance += distance;

            // Update best and worst distances
            bestDistance = Math.min(bestDistance, distance);
            worstDistance = Math.max(worstDistance, distance);

            // Check whether the optimal solution was found
            if (Math.abs(distance - optimalDistance) < 1e-9) {
                optimalRuns++;
            }
        }

        long elapsedTime = System.nanoTime() - startTime;

        // ==========================================
        // 4. Calculate benchmark statistics
        // ==========================================

        double averageDistance = totalDistance / runs;
        double elapsedMs = elapsedTime / 1_000_000.0;
        double averageRuntimeMs = elapsedMs / runs;

        double successRate = optimalRuns * 100.0 / runs;

        // Handle zero-length tours
        double optimalityGap = optimalDistance == 0.0
                ? 0.0
                : (averageDistance - optimalDistance)
                / optimalDistance * 100.0;

        double improvement = initialDistance == 0.0
                ? 0.0
                : (initialDistance - averageDistance)
                / initialDistance * 100.0;

        // ==========================================
        // 5. Print results
        // ==========================================

        System.out.println();
        System.out.println("==================================");
        System.out.println("       RouteLab Benchmark");
        System.out.println("==================================");

        System.out.printf("Cities:             %d%n", cities.size());
        System.out.printf("Runs:               %d%n", runs);

        System.out.println();
        System.out.println("--- Initial Solution ---");
        System.out.printf("Initial distance:   %.4f%n", initialDistance);

        System.out.println();
        System.out.println("--- Brute Force ---");
        System.out.printf("Optimal distance:   %.4f%n", optimalDistance);
        System.out.printf("Runtime:            %.4f ms%n", bruteForceMs);
        System.out.println("Optimal tour:       " + optimalTour);

        System.out.println();
        System.out.println("--- Simulated Annealing ---");
        System.out.printf("Temperature:        %.2f%n", initialTemperature);
        System.out.printf("Cooling rate:       %.4f%n", coolingRate);
        System.out.printf("Best distance:      %.4f%n", bestDistance);
        System.out.printf("Worst distance:     %.4f%n", worstDistance);
        System.out.printf("Average distance:   %.4f%n", averageDistance);

        System.out.println();
        System.out.println("--- Performance ---");
        System.out.printf("Optimal runs:       %d / %d%n", optimalRuns, runs);
        System.out.printf("Success rate:       %.2f%%%n", successRate);
        System.out.printf("Optimality gap:     %.2f%%%n", optimalityGap);
        System.out.printf("Improvement:        %.2f%%%n", improvement);
        System.out.printf("Total runtime:      %.2f ms%n", elapsedMs);
        System.out.printf("Average runtime:    %.4f ms%n", averageRuntimeMs);

        System.out.println("==================================");
    }
}
