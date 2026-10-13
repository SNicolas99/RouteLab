package dev.routelab;

import dev.routelab.algorithm.SimulatedAnnealing;
import dev.routelab.model.City;
import dev.routelab.model.Tour;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        List<City> cities = new ArrayList<>(List.of(
                new City("A", 0, 0),
                new City("B", 4, 0),
                new City("C", 7, 3),
                new City("D", 4, 6),
                new City("E", 0, 5),
                new City("F", 2, 2)
        ));

        Collections.shuffle(cities);


        int runs = 100;

        double totalDistance = 0.0;
        double bestDistance = Double.POSITIVE_INFINITY;
        double worstDistance = Double.NEGATIVE_INFINITY;

        long startTime = System.nanoTime();

        for (int i=0; i<runs; i++) {

            List<City> result = SimulatedAnnealing.solve(
                    cities, 100.0, 0.003
            );

            double distance = Tour.calculateDistance(result);

            totalDistance += distance;

            if (distance < bestDistance) {
                bestDistance = distance;
            }

            if (distance > worstDistance) {
                worstDistance = distance;
            }

        }

        long endTime = System.nanoTime();
        long elapsedTime = endTime - startTime;

        double averageDistance = totalDistance / runs;
        double elapsedMs = elapsedTime / 1_000_000.0;

        System.out.println("=== RouteLab Benchmark ===");
        System.out.printf("Runs:             %d%n", runs);
        System.out.printf("Best distance:    %.4f%n", bestDistance);
        System.out.printf("Worst distance:   %.4f%n", worstDistance);
        System.out.printf("Average distance: %.4f%n", averageDistance);
        System.out.printf("Total runtime:    %.2f ms%n", elapsedMs);
        System.out.printf("Average runtime:  %.2f ms%n", elapsedMs / runs);

    }

}