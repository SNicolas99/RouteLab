package dev.routelab.service;

import dev.routelab.algorithm.RouteSolver;
import dev.routelab.model.BenchmarkResult;
import dev.routelab.model.City;
import dev.routelab.model.SolverResult;

import java.util.ArrayList;
import java.util.List;

public class BenchmarkService {

    public static List<BenchmarkResult> run(
            List<RouteSolver> solvers,
            List<City> cities,
            int runs,
            Double optimalDistance
    ) {
        if (runs <= 0) {
            throw new IllegalArgumentException("Number of runs must be positive");
        }

        List<BenchmarkResult> results = new ArrayList<>();

        for (RouteSolver solver : solvers) {

            double totalDistance = 0.0;
            double totalRuntime = 0.0;
            double bestDistance = Double.POSITIVE_INFINITY;
            double worstDistance = Double.NEGATIVE_INFINITY;

            for (int i = 0; i < runs; i++) {
                SolverResult result = SolverRunner.run(solver, cities);

                totalDistance += result.distance();
                totalRuntime += result.runtimeMs();
                bestDistance = Math.min(bestDistance, result.distance());
                worstDistance = Math.max(worstDistance, result.distance());
            }

            // Average results
            double averageDistance = totalDistance / runs;
            double averageRuntime = totalRuntime / runs;

            Double gap = null;

            if (optimalDistance != null) {
                gap = optimalDistance == 0.0
                        ? 0.0
                        : (averageDistance - optimalDistance) / optimalDistance * 100.0;
            }

            results.add(
                    new BenchmarkResult(
                            solver.getName(),
                            runs,
                            bestDistance,
                            worstDistance,
                            averageDistance,
                            averageRuntime,
                            gap
                    )
            );
        }
        return results;
    }
}
