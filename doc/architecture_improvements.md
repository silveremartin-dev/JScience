# Architecture Improvements

## 1. Logic System - Internal Representation

### Problem
Current importers/exporters just parse files without internal representation. No translation capability.

### Solution
Create internal proof system:
```
org.jscience.mathematics.logic.formal/
  - Axiom.java (name, formula, metadata)
  - Theorem.java (name, statement, proof)
  - Proof.java (steps, rules)
  - InferenceRule.java (name, premises → conclusion)
  - FormalSystem.java (axioms + rules)
```

Importers translate Coq/Metamath → Internal AST
Exporters translate Internal AST → Coq/Metamath
**Translation**: Coq → Internal → Metamath

---

## 2. Modular Operations - Plugin Architecture

### Problem
`ModularInteger` as separate type requires casting. Adding operations to `Real`/`Integer` bloats classes.

### Solution: Wrapper Pattern (Chosen)
```java
class ModularRing<T extends Ring<T>> implements Ring<ModularRing<T>> {
    private final T value;
    private final T modulus;
    // Wraps any Ring with modular operations
}
```

**Benefits**: Clean separation, works with any Ring, no bloat

---

## 3. Prime Algorithms - Industrial Strength

### Current
- Miller-Rabin (probabilistic)
- Trial division

### Needed
- **Baillie-PSW** (deterministic up to 2^64, no known counterexamples)
- **AKS** (deterministic polynomial time)
- **Segmented Sieve** (efficient for ranges)
- **Wheel factorization** (optimization)

---

## 4. Geometry - Missing Basics

### Needed
```
org.jscience.mathematics.geometry/
  - Point2D, Point3D
  - Line2D, Circle, Sphere
  - Polygon, Polyhedron
  - ConvexHull algorithms
```

---

## 5. Fractal Families - Generalization

### Current
- MandelbrotSet
- JuliaSet

### Generalize
```java
interface EscapeTimeFractal extends Set<Complex> {
    int escapeTime(Complex z);
}

// Polynomial: z → z^n + c
// Newton basins
// Burning Ship
// Lyapunov
```
