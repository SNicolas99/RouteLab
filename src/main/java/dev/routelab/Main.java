package dev.routelab;

import dev.routelab.algorithm.*;
import dev.routelab.generator.CityGenerator;
import dev.routelab.model.BenchmarkResult;
import dev.routelab.model.City;
import dev.routelab.model.Tour;
import dev.routelab.service.BenchmarkService;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        int cityCount = 100;
        int runs = 10;
        long seed = 42L;

        try {
            // Parse CLI arguments
            for (int i = 0; i < args.length; i++) {
                switch (args[i]) {
                    case "--cities" -> cityCount = Integer.parseInt(args[++i]);
                    case "--runs" -> runs = Integer.parseInt(args[++i]);
                    case "--seed" -> seed = Long.parseLong(args[++i]);
                    default -> throw new IllegalArgumentException("Invalid argument: " + args[i]);
                }
            }

            // Catch exceptions
            if (cityCount < 0) {
                throw new IllegalArgumentException("Number of cities must be >= 0");
            }

            if (runs <= 0) {
                throw new IllegalArgumentException("Number of runs must be positive");
            }

            // Generate cities
            List<City> cities = CityGenerator.generate(cityCount, seed);
            System.out.println("Generated " + cities.size() + " cities");

            // Add solvers for all algorithms
            List<RouteSolver> solvers = new ArrayList<>();
            solvers.add(new NearestNeighborSolver());
            solvers.add(new TwoOptSolver());
            solvers.add(
                    new SimulatedAnnealingSolver(
                            1000.0,
                            0.003
                    )
            );

            Double optimalDistance = null;

            // Exact solver only feasible for small number of cities (e.g.: 9 or smaller)
            if (cityCount <= 9) {
                BruteForceSolver bruteForceSolver = new BruteForceSolver();
                List<City> optimalTour = bruteForceSolver.solve(cities);
                optimalDistance = Tour.calculateDistance(optimalTour);
                solvers.addFirst(bruteForceSolver);
            }

            List<BenchmarkResult> results = BenchmarkService.run(
                    solvers,
                    cities,
                    runs,
                    optimalDistance
            );

            printResults(results, cityCount, runs, seed);


        } catch (RuntimeException e) {
            System.err.println("Error: " + e.getMessage());
            System.err.println("Usage: java -jar route-lab.jar [--cities <n>] [--runs <n>] [--seed <n>]");
            System.exit(1);
        }
    }

    private static void printResults(
            List<BenchmarkResult> results,
            int cityCount,
            int runs,
            long seed
    ) {
        System.out.println();
        System.out.println("=== RouteLab Benchmark ===");
        System.out.printf(
                "Cities: %d | Runs: %d | Seed: %d%n%n",
                cityCount, runs, seed
        );

        System.out.printf(
                "%-22s %12s %12s %12s %12s%n",
                "Algorithm", "Avg Dist",
                "Best Dist", "Avg ms", "Gap %"
        );

        for (BenchmarkResult result : results) {

            String gap = result.optimalityGapPercent() == null
                    ? "N/A"
                    : String.format(
                    "%.2f",
                    result.optimalityGapPercent()
            );

            System.out.printf(
                    "%-22s %12.4f %12.4f %12.3f %12s%n",
                    result.algorithm(),
                    result.averageDistance(),
                    result.bestDistance(),
                    result.averageRuntimeMs(),
                    gap
            );
        }
    }
}
