package dev.routelab.service;

import dev.routelab.algorithm.NearestNeighborSolver;
import dev.routelab.model.BenchmarkResult;
import dev.routelab.model.City;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class BenchmarkServiceTest {

    @Test
    void shouldAggregateMultipleRuns() {
        List<City> cities = List.of(
                new City("A", 0, 0),
                new City("B", 3, 0),
                new City("C", 3, 4)
        );

        List<BenchmarkResult> results = BenchmarkService.run(
                List.of(new NearestNeighborSolver()),
                cities,
                5,
                12.0
        );

        assertEquals(1, results.size());

        BenchmarkResult result = results.getFirst();

        assertEquals("Nearest Neighbor", result.algorithm());
        assertEquals(5, result.runs());
        assertEquals(12.0, result.averageDistance(), 1e-9);
        assertEquals(12.0, result.bestDistance(), 1e-9);
        assertEquals(12.0, result.worstDistance(), 1e-9);
        assertEquals(0.0, result.optimalityGapPercent(), 1e-9);
        assertTrue(result.averageRuntimeMs() >= 0.0);
    }

    @Test
    void shouldRejectInvalidParameters() {
        assertThrows(
                IllegalArgumentException.class,
                () -> BenchmarkService.run(
                        List.of(new NearestNeighborSolver()),
                        List.of(),
                        0,
                        0.0
                )
        );
    }


    @Test
    void shouldHandleUnknownOptimum() {
        List<BenchmarkResult> results = BenchmarkService.run(
                List.of(new NearestNeighborSolver()),
                List.of(
                        new City("A", 0, 0),
                        new City("B", 3, 4)
                ),
                3,
                null
        );

        assertNull(results.getFirst().optimalityGapPercent());
    }

}
