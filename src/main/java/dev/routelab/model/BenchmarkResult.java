package dev.routelab.model;

public record BenchmarkResult(
        String algorithm,
        int runs,
        double bestDistance,
        double worstDistance,
        double averageDistance,
        double averageRuntimeMs,
        Double optimalityGapPercent
) {
}
