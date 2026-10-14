package dev.routelab.generator;

import dev.routelab.model.City;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CityGeneratorTest {

    @Test
    void shouldGenerateRequestedNumberOfCities() {
        List<City> cities = CityGenerator.generate(50, 42L);

        assertEquals(50, cities.size());
    }

    @Test
    void shouldBeReproducible() {
        List<City> cities1 = CityGenerator.generate(50, 42L);
        List<City> cities2 = CityGenerator.generate(50, 42L);

        assertEquals(cities1, cities2);
    }

    @Test
    void shouldRejectNegativeNumberOfCities() {
        assertThrows(
                IllegalArgumentException.class,
                () -> CityGenerator.generate(-1, 42L)
        );
    }


    @Test
    void shouldHandleZeroCities() {
        List<City> cities = CityGenerator.generate(0, 42L);

        assertTrue(cities.isEmpty());
    }

}
