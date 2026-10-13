package dev.routelab.algorithm;

import dev.routelab.model.City;
import dev.routelab.model.Tour;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class NearestNeighborTest {

    @Test
    void shouldHandleEmptyList() {
        List<City> cities = new ArrayList<>();
        List<City> tour = NearestNeighbor.findTour(cities);

        assertEquals(0, tour.size());
    }

    @Test
    void shouldHandleSingleCity() {
        List<City> cities = List.of(new City("A", 0, 0));
        List<City> tour = NearestNeighbor.findTour(cities);

        assertEquals(1, tour.size());
        assertEquals("A", tour.get(0).name());
    }

    @Test
    void shouldSelectNearestCityFirst() {
        List<City> cities = List.of(
                new City("A", 0, 0),
                new City("B", 10, 0),
                new City("C", 4, 0),
                new City("D", 2, 0)
        );

        // A --> D --> C --> B

        List<City> result = NearestNeighbor.findTour(cities);

        assertEquals("A", result.get(0).name());
        assertEquals("D", result.get(1).name());
        assertEquals("C", result.get(2).name());
        assertEquals("B", result.get(3).name());

        assertEquals(20.0, Tour.calculateDistance(result), 1e-9);
    }

    @Test
    void shouldPreserveAllCities() {
        List<City> cities = List.of(
                new City("A", 0, 0),
                new City("B", 2, 3),
                new City("C", 5, 1),
                new City("D", 7, 6)
        );

        List<City> result = NearestNeighbor.findTour(cities);

        assertEquals(cities.size(), result.size());
        assertTrue(result.containsAll(cities));
    }

    @Test
    void shouldNotModifyInput() {
        List<City> cities = new ArrayList<>(List.of(
                new City("A", 0, 0),
                new City("B", 5, 1),
                new City("C", 2, 4)
        ));

        List<City> original = new ArrayList<>(cities);

        NearestNeighbor.findTour(cities);

        assertEquals(original, cities);
    }
}
