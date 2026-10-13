package dev.routelab.algorithm;

import dev.routelab.model.City;
import dev.routelab.model.Tour;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class BruteForceTest {

    @Test
    void shouldFindOptimalTour() {
        List<City> cities = List.of(
                new City("A", 0, 0),
                new City("B", 4, 0),
                new City("C", 4, 3),
                new City("D", 0, 3)
        );

        List<City> result = BruteForce.solve(cities);

        assertEquals(14.0, Tour.calculateDistance(result), 1e-9);
    }

    @Test
    void shouldFindOptimalTourForUnorderedCities() {
        List<City> cities = List.of(
                new City("A", 0, 0),
                new City("D", 4, 8),
                new City("B", 5, 1),
                new City("E", -1, 6),
                new City("C", 8, 5),
                new City("F", 2, 3)
        );

        double initialDistance = Tour.calculateDistance(cities);

        List<City> result = BruteForce.solve(cities);
        double optimalDistance = Tour.calculateDistance(result);

        assertEquals(42.8110811317, initialDistance, 1e-9);
        assertEquals(28.3323762833, optimalDistance, 1e-9);
        assertTrue(optimalDistance < initialDistance);
    }

    @Test
    void shouldHandleEmptyAndSingleCity() {
        // Empty List Test
        List<City> cities = List.of();
        List<City> result = BruteForce.solve(cities);

        assertEquals(0.0, Tour.calculateDistance(result), 1e-9);

        // Single City Test
        cities = List.of(new City("A", 0, 0));
        result = BruteForce.solve(cities);

        assertEquals(0.0, Tour.calculateDistance(result), 1e-9);
    }

    @Test
    void shouldNotModifyInput() {
        List<City> cities = new ArrayList<>();
        cities.add(new City("A", 0, 0));
        cities.add(new City("B", 1, 1));
        cities.add(new City("C", 2, 2));
        cities.add(new City("D", 3, 3));

        BruteForce.solve(cities);

        // Verify the original list is unchanged.
        assertEquals(4, cities.size());
        assertEquals("A", cities.get(0).name());
        assertEquals("B", cities.get(1).name());
        assertEquals("C", cities.get(2).name());
        assertEquals("D", cities.get(3).name());
    }
}
