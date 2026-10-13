package dev.routelab.algorithm;

import dev.routelab.model.City;

import java.util.List;

public interface RouteSolver {

    String getName();

    List<City> solve(List<City> cities);
}
