package dev.routelab.algorithm;

import dev.routelab.model.City;
import java.util.List;

public class NearestNeighborSolver implements RouteSolver {

    @Override
    public String getName() {
        return "Nearest Neighbor";
    }

    @Override
    public List<City> solve(List<City> cities) {
        return NearestNeighbor.findTour(cities);
    }
}
