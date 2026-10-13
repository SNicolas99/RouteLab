package dev.routelab.model;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TourTest {

    @Test
    void shouldCalculateCorrectTourDistance() {

        List<City> cities = List.of(
                new City("A", 0, 0),
                new City("B", 4, 0),
                new City("C", 4, 3),
                new City("D", 0, 3)
        );

        double distance = Tour.calculateDistance(cities);

        assertEquals(14.0, distance, 1e-9);
    }

    @Test
    void shouldReturnZeroForEmptyTour() {
        List<City> cities = List.of();
        double distance = Tour.calculateDistance(cities);

        assertEquals(0.0, distance, 1e-9);
    }

    @Test
    void shouldReturnZeroForSingleCityTour() {
        List<City> cities = List.of(new City("A", 0, 0));
        double distance = Tour.calculateDistance(cities);

        assertEquals(0.0, distance, 1e-9);
    }

    @Test
    void shouldInlcudeReturnTrip() {
        List<City> cities = List.of(
                new City("A", 0, 0),
                new City("B", 3, 4)
        );
        double distance = Tour.calculateDistance(cities);

        assertEquals(10.0, distance, 1e-9);
    }

    @Test
    void shouldSwapCities() {
        List<City> cities = new ArrayList<>(List.of(
                new City("A", 0, 0),
                new City("B", 1, 1),
                new City("C", 2, 2)
        ));

        Tour.swapCities(cities, 0, 2);

        assertEquals("C", cities.get(0).name());
        assertEquals("B", cities.get(1).name());
        assertEquals("A", cities.get(2).name());
    }

    @Test
    void shouldGenerateNeighborWithoutChangingOriginal() {
        List<City> cities = List.of(
                new City("A", 0, 0),
                new City("B", 1, 1),
                new City("C", 2, 2),
                new City("D", 3, 3)
        );

        List<City> original = new ArrayList<>(cities);
        List<City> neighbor = Tour.generateNeighbor(cities);

        assertEquals(original, cities);
        assertNotSame(cities, neighbor);
        assertNotEquals(cities, neighbor);
        assertEquals(cities.size(), neighbor.size());
        assertTrue(neighbor.containsAll(cities));
    }
}
