# Mathematical Expansion Plan

## Vision
Create a **Unified Scientific Computing Framework** that combines:
1.  **Rigorous Algebraic Foundation** (Groups, Rings, Fields) - *JScience Unique Strength*
2.  **Physical Consistency** (Units of Measurement) - *JScience Unique Strength*
3.  **Comprehensive Mathematical Tools** (Analysis, Stats, etc.) - *Matching Commons Math/Hipparchus*

## 1. Analysis (Calculus & Functions)
**Goal**: Rigorous handling of continuous change, functions, and limits.
*   **`org.jscience.mathematics.analysis`**
    *   `Function<D, R>`: Generalized function interface.
    *   `RealFunction`: Specialized R -> R (V1: `MathFunction`).
    *   `Differentiation`: Numerical (Finite Differences) and Symbolic (Automatic Differentiation).
    *   `Integration`:
        *   Numerical: Simpson, Trapezoidal, Gauss-Legendre (V1 had some support).
        *   Symbolic: Basic antiderivatives.
    *   `Polynomials`: Roots, evaluation, interpolation (V1: `Polynomial`).
    *   `FastFourierTransform`: FFT implementation (V1 had this).

## 2. Statistics & Probability
**Goal**: Modeling uncertainty and data analysis.
*   **`org.jscience.mathematics.statistics`**
    *   `Distribution`: Normal, Poisson, Chi-Squared, etc. (V1: `Distribution`).
    *   `RandomVariable`: Abstraction over random sources.
    *   `Descriptive`: Mean, variance, skewness, kurtosis (Streaming support).
    *   `Regression`: Linear, Least Squares (V1 had this).
    *   `HypothesisTesting`: T-test, Chi-square test.

## 3. Linear Algebra (Advanced)
**Goal**: High-performance matrix/vector operations.
*   **`org.jscience.mathematics.vector`** (Existing)
    *   `Decompositions`: LU, QR, Cholesky, SVD, Eigenvalue (V1: `Matrix` methods).
    *   `SparseMatrix`: Efficient storage for large sparse systems.
    *   `Tensors`: Multi-dimensional arrays (ND4J integration potential).

## 4. Geometry & Topology
**Goal**: Shape, space, and connectivity.
*   **`org.jscience.mathematics.geometry`**
    *   `Euclidean`: Points, lines, planes in R^n.
    *   `Projective`: Homogeneous coordinates.
    *   `Manifold`: Coordinate systems, charts.
    *   **Constructive Solid Geometry (CSG)**:
        *   `RegionBSPTree`: Binary Space Partitioning for efficient boolean operations (Union, Intersection, Difference).
        *   **Use Cases**: Physics simulations (collision detection), Ray tracing, Finite Element Analysis (mesh generation), Nuclear engineering (reactor modeling).
        *   **Programmatic API**: Focus on code-driven geometry creation rather than interactive editing.
*   **`org.jscience.mathematics.topology`**
    *   `TopologicalSpace`: Open sets, neighborhoods.
    *   `SimplicialComplex`: Triangulations (Already ported).

## 5. Discrete Mathematics
**Goal**: Structures for computer science.
*   **`org.jscience.mathematics.discrete`**
    *   `Graph`: Nodes, edges, traversal, shortest path.
    *   `Combinatorics`: Permutations, combinations.
    *   `Automata`: Finite state machines.

## 6. Number Theory
**Goal**: Properties of integers.
*   **`org.jscience.mathematics.numbertheory`**
    *   `Primes`: Generation, testing.
    *   `ModularArithmetic`: Congruences, CRT.
    *   `FieldExtensions`: Algebraic number fields.

## 7. Logic
**Goal**: Formal systems.
*   **`org.jscience.mathematics.logic`**
    *   `Propositional`: Boolean logic.
    *   `Predicate`: First-order logic.

## Comparison with Competitors
| Feature | JScience V2 | Apache Commons Math | Hipparchus |
| :--- | :--- | :--- | :--- |
| **Algebraic Structure** | ✅ Strong (Groups/Rings) | ❌ Weak | ❌ Weak |
| **Units of Measure** | ✅ Native Integration | ❌ None | ❌ None |
| **Linear Algebra** | ✅ Generic (Field<E>) | ⚠️ Double only | ⚠️ Double only |
| **Analysis/Calc** | 🚧 Planned | ✅ Mature | ✅ Mature |
| **Statistics** | 🚧 Planned | ✅ Mature | ✅ Mature |
| **Performance** | 🚀 GPU/Parallel | ⚠️ CPU | ⚠️ CPU |

## Strategy
1.  **Prioritize Analysis & Statistics**: These are the most requested features for general science.
2.  **Leverage Structure**: Build Analysis/Stats *on top of* the algebraic foundation (e.g., Polynomials over Rings).
3.  **Modernize**: Use Java Streams, Lambdas, and GPU acceleration where possible.
