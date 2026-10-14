# RouteLab

A Java-based Traveling Salesman Problem (TSP) playground for implementing, testing, and benchmarking optimization algorithms.

## Algorithms implemented

- **Brute Force:** Finds the optimal solution by evaluating all possible permutations. Works well for small problems.
- **Nearest Neighbor:** Greedy heuristic.
- **2-opt:** Local search for improving existing tours.
- **Simulated Annealing:** Probabilistic optimization.

## Getting Started

Build the project by running:

```bash
mvn clean package
```

Run a benchmark:

```bash
java -jar target/routelab-1.0-SNAPSHOT.jar --cities 30 --runs 20 --seed 42
```

### CLI Options

| Parameter | Description | Default |
|---|---|---|
| `--cities` | Number of randomly generated cities | 100 |
| `--runs` | Benchmark iterations per solver | 10 |
| `--seed` | Random seed for city generation | 42 |

For up to 9 cities, Brute Force is also executed to calculate the optimality gap.

## Testing

```bash
mvn test
```

## License

Not specified yet.