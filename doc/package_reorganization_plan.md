# Package Reorganization Plan

## Current Issues

1. **Mixing Structures and Elements**: `Natural` is both an element and implements `Semiring`
2. **Flat Package**: Everything in `algebra` and `scalar` - no domain organization
3. **No Separation**: Rings, Fields, Lattices all mixed together

## Proposed Package Structure

```
org.jscience.mathematics
├── structure/              # Abstract algebraic structures (interfaces only)
│   ├── Set, FiniteSet, InfiniteSet
│   ├── Magma, Monoid, Semigroup
│   ├── Group, AbelianGroup, AbelianMonoid
│   ├── Semiring, Ring, Field
│   ├── Module, VectorSpace, Algebra
│   └── Lattice, JoinSemilattice, MeetSemilattice
│
├── number/                 # Number elements (individual values)
│   ├── Natural             # A single natural number (like 5)
│   ├── Integer             # A single integer (like -3)
│   ├── Rational            # A single rational (like 2/3)
│   ├── Real                # A single real number
│   ├── Complex<T>          # A single complex number
│   └── Quaternion<T>       # A single quaternion
│
├── number.set/             # Number set structures (ℕ, ℤ, ℚ, ℝ, ℂ)
│   ├── Naturals            # implements Semiring<Natural>, InfiniteSet<Natural>
│   ├── Integers            # implements Ring<Integer>, InfiniteSet<Integer>
│   ├── Rationals           # implements Field<Rational>, InfiniteSet<Rational>
│   ├── Reals               # implements Field<Real>, InfiniteSet<Real>
│   └── Complexes<T>        # implements Field<Complex<T>>, InfiniteSet<Complex<T>>
│
├── lattice/                # Lattice implementations
│   ├── BooleanAlgebra      # implements Lattice<Boolean>, Semiring<Boolean>
│   ├── PowerSet<E>         # P(S) with ∪ and ∩
│   └── (future: OrderedSet, etc.)
│
├── ring/                   # Ring implementations (beyond numbers)
│   ├── Polynomial<T>       # R[x]
│   ├── MatrixRing<T>       # Mₙ(R)
│   └── ModularIntegers     # ℤ/nℤ
│
├── field/                  # Field implementations (beyond numbers)
│   ├── RationalFunctions<T>
│   ├── FiniteField         # 𝔽ₚ
│   └── (future: AlgebraicExtensions)
│
├── linear/                 # Linear algebra
│   ├── Vector<T>
│   ├── Matrix<T>
│   ├── Tensor<T>
│   └── LinearMap<T>
│
└── scalar/                 # Computational strategy (KEEP AS IS)
    ├── ScalarType<T>       # Strategy interface
    ├── DoubleScalar        # Fast primitive strategy
    ├── FloatScalar         # GPU strategy
    ├── IntScalar           # Int strategy
    ├── ExactScalar         # BigDecimal strategy
    └── ComplexScalar<T>    # Complex adapter
```

## Key Design Principles

### 1. Structure vs Element Separation

**Before**:
```java
Natural n = Natural.of(5);  // Natural is both element AND structure
n.add(Natural.of(3));       // Confusing!
```

**After**:
```java
Natural n = Natural.of(5);            // Element (just a value)
Naturals structure = Naturals.getInstance();  // Structure (operations)
Natural sum = structure.add(n, Natural.of(3)); // Clear!
```

### 2. Domain Organization

- **`structure/`**: Pure interfaces (no implementations)
- **`number/`**: Number elements only
- **`number.set/`**: Number structures (Naturals, Integers, etc.)
- **`lattice/`, `ring/`, `field/`**: Domain-specific implementations
- **`linear/`**: Linear algebra (separate concern)
- **`scalar/`**: Computational strategies (unchanged)

### 3. Benefits

✓ **Clear separation**: Elements vs Structures  
✓ **Mathematical correctness**: `Naturals` is a structure, `Natural` is an element  
✓ **Extensibility**: Easy to add new rings, fields, lattices  
✓ **Discoverability**: `import o.j.m.number.*` gets all number types  
✓ **Maintainability**: Related code together

## Migration Path

### Phase 1: Create New Package Structure (No Breaking Changes)
1. Create new packages
2. Create `Naturals`, `Integers`, etc. structures
3. Keep old `Natural` implementing both (deprecated)

### Phase 2: Refactor Elements
1. Refactor `Natural` to be pure element
2. Move structural methods to `Naturals`
3. Update `ScalarType` adapter pattern

### Phase 3: Move Existing Code
1. Move interfaces to `structure/`
2. Move lattice code to `lattice/`
3. Update imports

### Phase 4: Cleanup
1. Remove deprecated code
2. Update all tests
3. Update documentation

## Example: Natural Numbers

### Current (Conflated)
```java
public final class Natural implements ScalarType<Natural>, Semiring<Natural> {
    public Natural add(Natural other) { ... }  // Element method
    public Natural add(Natural a, Natural b) { ... }  // Structure method
}
```

### Proposed (Separated)

**Element** (`org.jscience.mathematics.number.Natural`):
```java
public final class Natural implements Comparable<Natural> {
    private final BigInteger value;
    
    public BigInteger getValue() { ... }
    public int compareTo(Natural other) { ... }
    // NO algebraic operations here
}
```

**Structure** (`org.jscience.mathematics.number.set.Naturals`):
```java
public final class Naturals implements Semiring<Natural>, InfiniteSet<Natural> {
    private static final Naturals INSTANCE = new Naturals();
    
    @Override
    public Natural add(Natural a, Natural b) {
        return Natural.of(a.getValue().add(b.getValue()));
    }
    
    @Override
    public Natural zero() { return Natural.ZERO; }
    
    @Override
    public boolean contains(Natural n) { return true; }
    
    @Override
    public boolean isCountable() { return true; }
}
```

**Adapter** (`org.jscience.mathematics.scalar.NaturalScalar`):
```java
public final class NaturalScalar implements ScalarType<Natural> {
    private final Naturals structure = Naturals.getInstance();
    
    @Override
    public Natural add(Natural a, Natural b) {
        return structure.add(a, b);
    }
    // Delegates to Naturals structure
}
```

## Recommendation

**Start with Phase 1**: Create new packages and structures without breaking existing code.

Next session:
1. Create `org.jscience.mathematics.number.set.Naturals`
2. Create `org.jscience.mathematics.number.set.Integers`
3. Keep current `Natural`/`LongScalar` working as adapters
4. Gradually migrate

This allows incremental, safe refactoring.
