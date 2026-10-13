package dev.routelab;

import dev.routelab.algorithm.*;
import dev.routelab.model.City;
import dev.routelab.model.Tour;

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

        // Initialize all solvers
        List<RouteSolver> solvers = List.of(
                new BruteForceSolver(),
                new NearestNeighborSolver(),
                new TwoOptSolver(),
                new SimulatedAnnealingSolver(100.0, 0.003)
        );

        System.out.println("=== RouteLab ===");
        System.out.println("Cities: " + cities.size());
        System.out.println();

        // Execute and compare solvers
        for (RouteSolver solver : solvers) {

            long startTime = System.nanoTime();

            List<City> result = solver.solve(cities);

            double runtimeMs =
                    (System.nanoTime() - startTime) / 1_000_000.0;

            double distance = Tour.calculateDistance(result);

            System.out.printf(
                    "%-22s | Distance: %8.4f | Time: %8.3f ms%n",
                    solver.getName(),
                    distance,
                    runtimeMs
            );

            System.out.println(
                    "  Route: " +
                            result.stream()
                                    .map(City::name)
                                    .toList()
            );
        }
    }
}
