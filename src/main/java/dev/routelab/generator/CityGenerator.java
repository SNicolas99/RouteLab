package dev.routelab.generator;

import dev.routelab.model.City;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CityGenerator {


    public static List<City> generate(
            int n,
            long seed
    ) {
        if (n < 0) {
            throw new IllegalArgumentException("Number of cities must be >= 0");
        }

        Random random = new Random(seed);
        List<City> cities = new ArrayList<>(n);

        for (int i = 0; i < n; i++) {
            double x = random.nextDouble(0.0, 100.0);
            double y = random.nextDouble(0.0, 100.0);

            cities.add(
                    new City("City-" + (i+1), x, y)
            );
        }

        return cities;
    }
}
