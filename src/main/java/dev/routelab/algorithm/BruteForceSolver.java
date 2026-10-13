package dev.routelab.algorithm;

import dev.routelab.model.City;
import java.util.List;

public class BruteForceSolver implements RouteSolver {

    @Override
    public String getName() {
        return "Brute Force";
    }

    @Override
    public List<City> solve(List<City> cities) {
        return BruteForce.solve(cities);
    }
}
