package dev.routelab.algorithm;

import dev.routelab.model.City;

import java.util.List;

public class SimulatedAnnealingSolver implements RouteSolver {

    private final double initialTemperature;
    private final double coolingRate;

    public SimulatedAnnealingSolver(
            double initialTemperature,
            double coolingRate
    ) {
        this.initialTemperature = initialTemperature;
        this.coolingRate = coolingRate;
    }

    @Override
    public String getName() {
        return "Simulated Annealing";
    }

    @Override
    public List<City> solve(List<City> cities) {
        return SimulatedAnnealing.solve(
                cities,
                initialTemperature,
                coolingRate
        );
    }
}
