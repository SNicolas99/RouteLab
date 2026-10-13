package dev.routelab.algorithm;

import dev.routelab.model.City;
import dev.routelab.model.Tour;

import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SimulatedAnnealingTest {

    @Test
    void shouldNeverReturnWorseThanInitialTour() {
        List<City> cities = List.of(
                new City("A", 0, 0),
                new City("B", 4, 0),
                new City("C", 7, 3),
                new City("D", 4, 6),
                new City("E", 0, 5)
        );

        double initialDistance = Tour.calculateDistance(cities);

        List<City> result = SimulatedAnnealing.solve(
                cities, 100.0, 0.003
        );

        double resultDistance = Tour.calculateDistance(result);

        assertTrue(resultDistance <= initialDistance + 1e-9);
    }


    @Test
    void shouldRejectInvalidParameters() {
        List<City> cities = List.of(
                new City("A", 0, 0),
                new City("B", 1, 1)
        );

        // Invalid initial temperature (0.0)
        assertThrows(
                IllegalArgumentException.class,
                () -> SimulatedAnnealing.solve(cities, 0.0, 0.003)
        );

        // Invalid cooling rate (0.0 and 1.0)
        assertThrows(
                IllegalArgumentException.class,
                () -> SimulatedAnnealing.solve(cities, 100.0, 0.0)
        );

        assertThrows(
                IllegalArgumentException.class,
                () -> SimulatedAnnealing.solve(cities, 100.0, 1.0)
        );

        // Non-finite parameters
        assertThrows(
                IllegalArgumentException.class,
                () -> SimulatedAnnealing.solve(cities, Double.NaN, 0.003)
        );
    }


    @Test
    void shouldPreserveAllCities() {
        List<City> cities = List.of(
                new City("A", 0, 0),
                new City("B", 4, 0),
                new City("C", 7, 3),
                new City("D", 4, 6),
                new City("E", 0, 5)
        );

        List<City> result = SimulatedAnnealing.solve(
                cities, 100.0, 0.003
        );

        // Verify that both lists have the same size
        assertEquals(cities.size(), result.size());

        // Verify that result contains all original cities
        assertTrue(result.containsAll(cities));

        // Verify that every city appears exactly once
        assertEquals(cities.size(), new HashSet<>(result).size());
    }

    @Test
    void shouldAlwaysAcceptBetterOrEqualSolutions() {
        assertTrue(
                SimulatedAnnealing.shouldAccept(20.0, 15.0, 100.0)
        );

        assertTrue(
                SimulatedAnnealing.shouldAccept(20.0, 20.0, 100.0)
        );
    }


    @Test
    void shouldNeverAcceptWorseSolutions() {
        assertFalse(
                SimulatedAnnealing.shouldAccept(20.0, 25.0, 0.0)
        );
    }

}
