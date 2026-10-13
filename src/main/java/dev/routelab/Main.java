package dev.routelab;

import dev.routelab.algorithm.BruteForce;
import dev.routelab.algorithm.SimulatedAnnealing;
import dev.routelab.algorithm.NearestNeighbor;
import dev.routelab.algorithm.TwoOpt;
import dev.routelab.model.City;
import dev.routelab.model.Tour;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        // Initialize cities
        List<City> cities = List.of(
                new City("A", 0, 0),
                new City("D", 4, 6),
                new City("E", 0, 5),
                new City("B", 4, 0),
                new City("F", 2, 2),
                new City("C", 7, 3)
        );

        // Calculate routes
        List<City> bruteForce = BruteForce.solve(cities);
        List<City> annealing = SimulatedAnnealing.solve(
                cities, 100.0, 0.003
        );
        List<City> nearestNeighbor = NearestNeighbor.findTour(cities);
        List<City> twoOpt = TwoOpt.solve(cities);

        // Compare results
        System.out.println("=== RouteLab ===");

        printResult("Initial", cities);
        printResult("Brute Force", bruteForce);
        printResult("Simulated Annealing", annealing);
        printResult("Nearest Neighbor", nearestNeighbor);
        printResult("2-opt", twoOpt);

        // Also test reverse Segment
        List<City> cities2 = new ArrayList<>(List.of(
                new City("A", 0, 0),
                new City("B", 1, 0),
                new City("C", 2, 0),
                new City("D", 3, 0),
                new City("E", 4, 0),
                new City("F", 5, 0)
        ));

        TwoOpt.reverseSegment(cities2, 1, 4);

        System.out.println(cities2.stream().map(City::name).toList());
    }

    private static void printResult(String name, List<City> tour) {

        double distance = Tour.calculateDistance(tour);

        System.out.printf("%-20s | Distance: %8.4f%n", name, distance);

        System.out.println(
                "  Route: " +
                        tour.stream().map(City::name).toList()
        );
    }
}
