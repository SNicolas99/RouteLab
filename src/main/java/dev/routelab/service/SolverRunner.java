package dev.routelab.service;

import dev.routelab.algorithm.RouteSolver;
import dev.routelab.model.City;
import dev.routelab.model.SolverResult;
import dev.routelab.model.Tour;

import java.util.List;

public class SolverRunner {

    public static SolverResult run(
            RouteSolver solver,
            List<City> cities
    ) {
        long startTime = System.nanoTime();

        List<City> tour = solver.solve(cities);

        double runtimeMs = (System.nanoTime() - startTime) / 1_000_000.0;

        double distance = Tour.calculateDistance(tour);

        return new SolverResult(
                solver.getName(),
                tour,
                distance,
                runtimeMs
        );
    }
}
