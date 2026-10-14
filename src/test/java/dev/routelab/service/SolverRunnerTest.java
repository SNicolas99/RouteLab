package dev.routelab.service;

import dev.routelab.algorithm.NearestNeighborSolver;
import dev.routelab.model.City;
import dev.routelab.model.SolverResult;
import dev.routelab.model.Tour;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class SolverRunnerTest {

    @Test
    void shouldReturnCorrectResult() {
        List<City> cities = List.of(
                new City("A", 0, 0),
                new City("B", 3, 0),
                new City("C", 3, 4)
        );

        SolverResult result = SolverRunner.run(
                new NearestNeighborSolver(),
                cities
        );

        assertEquals("Nearest Neighbor", result.algorithm());
        assertEquals(cities.size(), result.tour().size());
        assertEquals(
                Tour.calculateDistance(result.tour()),
                result.distance(),
                1e-9
        );
        assertTrue(result.runtimeMs() >= 0.0);
    }
}
