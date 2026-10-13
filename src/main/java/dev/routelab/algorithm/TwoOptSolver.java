package dev.routelab.algorithm;

import dev.routelab.model.City;
import java.util.List;

public class TwoOptSolver implements RouteSolver {

    @Override
    public String getName() {
        return "2-opt";
    }

    @Override
    public List<City> solve(List<City> cities) {
        return TwoOpt.solve(cities);
    }
}
