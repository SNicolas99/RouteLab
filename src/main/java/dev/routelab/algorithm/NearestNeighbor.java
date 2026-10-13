package dev.routelab.algorithm;

import dev.routelab.model.City;

import java.util.ArrayList;
import java.util.List;


public class NearestNeighbor {

    public static List<City> findTour(List<City> cities) {

        // Handle empty list case
        if (cities.isEmpty()) {
            return new ArrayList<>();
        }

        List<City> tour = new ArrayList<>();
        List<City> unvisited = new ArrayList<>(cities);

        // Select the first city as the starting point
        City start = unvisited.get(0);
        tour.add(start);
        unvisited.remove(start);

        City current = start;

        // Repeat the NN process until all cities are visited
        while (!unvisited.isEmpty()) {

            // Find the nearest unvisited city to the current city
            City nearest = unvisited.get(0);

            for (City city : unvisited) {
                if (city.distanceTo(current) < nearest.distanceTo(current)) {
                    nearest = city;
                }
            }

            // Add the nearest city to the tour and remove it from the unvisited list
            tour.add(nearest);
            unvisited.remove(nearest);

            // Update current position
            current = nearest;
        }

        return tour;
    }

}
