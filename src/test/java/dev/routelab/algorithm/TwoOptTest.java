package dev.routelab.algorithm;

import dev.routelab.model.City;
import dev.routelab.model.Tour;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class TwoOptTest {

    Random random = new Random(42);

    @Test
    void shouldReverseSegment() {
        List<City> cities = new ArrayList<>(List.of(
                new City("A", 0, 0),
                new City("B", 1, 0),
                new City("C", 2, 0),
                new City("D", 3, 0),
                new City("E", 4, 0),
                new City("F", 5, 0)
        ));

        TwoOpt.reverseSegment(cities, 1, 4);

        assertEquals(
                List.of("A", "E", "D", "C", "B", "F"),
                cities.stream().map(City::name).toList()
        );
    }

    @Test
    void shouldImproveCrossedTour() {
        List<City> cities = new ArrayList<>(List.of(
                new City("A", 0, 0),
                new City("C", 4, 3),
                new City("B", 4, 0),
                new City("D", 0, 3)
        ));

        double initialDistance = Tour.calculateDistance(cities);

        List<City> result = TwoOpt.solve(cities);
        double optimizedDistance = Tour.calculateDistance(result);

        assertTrue(optimizedDistance < initialDistance);
        assertEquals(List.of("A", "B", "C", "D"), result.stream().map(City::name).toList());
        assertEquals(14.0, optimizedDistance, 1e-9);
    }

    @Test
    void shouldNeverMakeTourWorse() {
        List<City> cities = new ArrayList<>(
                List.of(
                        new City("A", random.nextInt(0, 100), random.nextInt(0, 100)),
                        new City("B", random.nextInt(0, 100), random.nextInt(0, 100)),
                        new City("C", random.nextInt(0, 100), random.nextInt(0, 100)),
                        new City("D", random.nextInt(0, 100), random.nextInt(0, 100)),
                        new City("E", random.nextInt(0, 100), random.nextInt(0, 100)),
                        new City("F", random.nextInt(0, 100), random.nextInt(0, 100))
                )
        );

        List<City> result = TwoOpt.solve(cities);

        double initialDistance = Tour.calculateDistance(cities);
        double optimizedDistance = Tour.calculateDistance(result);

        assertTrue(optimizedDistance <= initialDistance);
    }

    @Test
    void shouldPreserveAllCities() {
        List<City> cities = new ArrayList<>(List.of(
                new City("A", 0, 0),
                new City("C", 4, 3),
                new City("B", 4, 0),
                new City("D", 0, 3)
        ));

        List<City> result = TwoOpt.solve(cities);

        assertEquals(cities.size(), result.size());
        assertTrue(result.containsAll(cities));
        assertTrue(cities.containsAll(result));
        assertEquals(new HashSet<>(result), new HashSet<>(cities));
    }

    @Test
    void shouldNotModifyInput() {
        List<City> cities = new ArrayList<>(List.of(
                new City("A", 0, 0),
                new City("C", 4, 3),
                new City("B", 4, 0),
                new City("D", 0, 3)
        ));

        List<City> original = new ArrayList<>(cities);
        List<City> result = TwoOpt.solve(cities);

        assertEquals(original, cities);
        assertEquals("A", cities.get(0).name());
        assertEquals("C", cities.get(1).name());
        assertEquals("B", cities.get(2).name());
        assertEquals("D", cities.get(3).name());
    }

    @Test
    void shouldHandleSmallTours() {
        // TODO: Test empty, single-city,
        //  two-city and three-city tours.
        // 1) empty tour
        List<City> cities = List.of();
        List<City> result = TwoOpt.solve(cities);
        assertEquals(0, result.size());

        // 2) single-city tour
        cities = List.of(new City("A", 0, 0));
        result = TwoOpt.solve(cities);
        assertEquals(1, result.size());
        assertEquals("A", result.get(0).name());
        assertEquals(
                0.0,
                Tour.calculateDistance(result),
                1e-9
        );

        // 3) two-city tour
        cities = List.of(
                new City("A", 0, 0),
                new City("B", 1, 1)
        );
        result = TwoOpt.solve(cities);
        assertEquals(2, result.size());
        assertEquals("A", result.get(0).name());
        assertEquals("B", result.get(1).name());
        assertEquals(
                2 * Math.sqrt(2),
                Tour.calculateDistance(result),
                1e-9
        );

        // 4) three-city tour
        cities = List.of(
                new City("A", 0, 0),
                new City("B", 3, 3),
                new City("C", 2, 0)
        );
        result = TwoOpt.solve(cities);
        assertEquals(3, result.size());
        assertEquals("A", result.get(0).name());
        assertEquals("B", result.get(1).name());
        assertEquals("C", result.get(2).name());
        assertEquals(
                Math.sqrt(18) + Math.sqrt(10) + 2,
                Tour.calculateDistance(result),
                1e-9
        );
    }


    @Test
    void shouldReachLocalOptimum() {
        List<City> cities = List.of(
                new City("A", 0, 0),
                new City("D", 4, 8),
                new City("B", 5, 1),
                new City("E", -1, 6),
                new City("C", 8, 5),
                new City("F", 2, 3)
        );

        List<City> optimized = TwoOpt.solve(cities);
        List<City> optimizedAgain = TwoOpt.solve(optimized);

        assertEquals(
                Tour.calculateDistance(optimized),
                Tour.calculateDistance(optimizedAgain),
                1e-9
        );
    }


}
