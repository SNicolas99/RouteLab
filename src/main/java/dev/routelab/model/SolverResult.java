package dev.routelab.model;

import java.util.List;

public record SolverResult(
        String algorithm,
        List<City> tour,
        double distance,
        double runtimeMs
) {
    public SolverResult {
        tour = List.copyOf(tour);
    }
}