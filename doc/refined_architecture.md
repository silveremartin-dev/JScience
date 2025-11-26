# Mathematical Type System - Refined Architecture

## User Concerns Addressed

### 1. Precision Preference System ✓

**Problem**: User may want float instead of double, or always BigDecimal for safety.

**Solution**: Computation Context + Hints

```java
// Global default (thread-safe)
public class MathContext {
    private static ThreadLocal<MathContext> current = 
        ThreadLocal.withInitial(MathContext::getDefault);
    
    public enum RealPrecision {
        FAST,        // float
        NORMAL,      // double (default)
        EXACT        // BigDecimal
    }
    
    private final RealPrecision realPrecision;
    
    public static MathContext fast() {
        return new MathContext(RealPrecision.FAST);
    }
    
    public static MathContext exact() {
        return new MathContext(RealPrecision.EXACT);
    }
}

// Usage 1: Thread-local context
MathContext.setCurrent(MathContext.fast());
Real r = Real.of(3.14);  // Uses float internally

// Usage 2: Explicit hint  
Real r = Real.of(3.14, MathContext.fast());

// Usage 3: Computation block
MathContext.fast().run(() -> {
    Real a = Real.of(1.0);  // float
    Real b = Real.of(2.0);  // float
    return a.add(b);         // float
});
```

**Build-time Configuration**:
```properties
# jscience.properties
math.real.default=NORMAL    # double (default)
math.real.default=FAST      # float (GPU/embedded)
math.real.default=EXACT     # BigDecimal (finance)

math.overflow.checking=SAFE    # Check all ops (default)
math.overflow.checking=UNSAFE  # No checks (dangerous!)
math.overflow.checking=LAZY    # Check periodically
```

### 2. Overflow Checking Performance ✓

**Problem**: Checking overflow at every operation is expensive.

**Analysis**:
```java
// Naive (SLOW):
public Natural add(Natural other) {
    try {
        int result = Math.addExact(this.value, other.value);
        return Natural.of(result);
    } catch (ArithmeticException e) {
        return NaturalLong.of((long)this.value + other.value);
    }
}
// Cost: exception + stack unwinding on overflow = 100-1000x slower!
```

**Solutions**:

#### Option A: Pre-check (FAST)
```java
public Natural add(Natural other) {
    // Check if result would overflow BEFORE computing
    if (willAddOverflow(this.value, other.value)) {
        // Promote to long
        return NaturalLong.of((long)this.value + (long)other.value);
    }
    // Fast path - no exception
    return NaturalInt.of(this.value + other.value);
}

private static boolean willAddOverflow(int a, int b) {
    // Fast bit-level check
    return ((a ^ b) >= 0) && ((a ^ (a + b)) < 0);
}
```
**Cost**: 1-2 CPU cycles (vs 1000+ for exception)

#### Option B: Unsafe Mode (FASTEST)
```java
public Natural add(Natural other) {
    if (MathContext.getCurrent().isOverflowCheckingEnabled()) {
        // Safe path
        if (willAddOverflow(...)) { promote... }
    }
    // Unsafe fast path - may wrap around!
    return NaturalInt.of(this.value + other.value);
}
```
**Cost**: Configurable safety vs speed

#### Option C: Lazy Checking (BALANCED)
```java
class NaturalInt extends Natural {
    private int value;
    private transient boolean mayHaveOverflowed = false;
    
    public Natural add(Natural other) {
        int result = this.value + other.value;
        NaturalInt n = new NaturalInt(result);
        
        // Mark if suspicious (no actual check yet)
        n.mayHaveOverflowed = (this.value > 0 && other.value > 0 && result < 0);
        
        return n;
    }
    
    // Check only when converting to string or comparing
    public String toString() {
        if (mayHaveOverflowed) {
            // Now we check and fix
            return promoteIfNeeded().toString();
        }
        return String.valueOf(value);
    }
}
```
**Cost**: Pay only when needed

**Recommendation**: **Option A (pre-check)** 
- Fast (1-2 cycles overhead)
- Always safe
- No exceptions in hot path

### 3. Interface Implementation ✓

**Problem**: All number types must implement appropriate algebraic interfaces.

**Complete Interface Mapping**:

```java
// Natural implements Semiring (no additive inverse)
public abstract class Natural implements Semiring<Natural>, 
                                          InfiniteSet<Natural>,
                                          Comparable<Natural> {
    // Semiring operations
    public abstract Natural add(Natural other);
    public abstract Natural multiply(Natural other);
    public abstract Natural zero();
    public abstract Natural one();
    
    // Partial operations (may throw)
    public abstract Natural subtract(Natural other); // throws if negative
}

// Integer implements Ring (has additive inverse)
public abstract class Integer implements Ring<Integer>,
                                          InfiniteSet<Integer>,
                                         Comparable<Integer> {
    public abstract Integer add(Integer other);
    public abstract Integer subtract(Integer other);
    public abstract Integer multiply(Integer other);
    public abstract Integer negate();
    public abstract Integer zero();
    public abstract Integer one();
}

// Rational implements Field (has multiplicative inverse)
public abstract class Rational implements Field<Rational>,
                                           InfiniteSet<Rational>,
                                           Comparable<Rational> {
    public abstract Rational add(Rational other);
    public abstract Rational subtract(Rational other);
    public abstract Rational multiply(Rational other);
    public abstract Rational divide(Rational other);
    public abstract Rational negate();
    public abstract Rational inverse();  // 1/x
}

// Real implements Field (approximate)
public abstract class Real implements Field<Real>,
                                       InfiniteSet<Real>,
                                       Comparable<Real> {
    // All field operations
    // PLUS transcendental functions
    public abstract Real sqrt();
    public abstract Real exp();
    public abstract Real log();
    public abstract Real sin();
    // ...
}

// Complex implements Field
public abstract class Complex<T extends Real> implements Field<Complex<T>>,
                                                          InfiniteSet<Complex<T>> {
    // Field operations
    // PLUS complex-specific
    public abstract T real();
    public abstract T imaginary();
    public abstract Complex<T> conjugate();
    public abstract T abs();
    // Note: NOT Comparable (no total ordering in ℂ)
}
```

**Internal implementations ALL inherit these**:
```java
final class NaturalInt extends Natural { ... }    // Semiring
final class NaturalLong extends Natural { ... }   // Semiring
final class NaturalBig extends Natural { ... }    // Semiring

final class IntegerInt extends Integer { ... }    // Ring
final class IntegerLong extends Integer { ... }   // Ring
final class IntegerBig extends Integer { ... }    // Ring

// etc.
```

**Benefit**: Polymorphism works!
```java
public <E> E compute(E a, E b, Semiring<E> structure) {
    return structure.multiply(structure.add(a, b), structure.one());
}

Natural n = compute(Natural.of(5), Natural.of(3), Naturals.getInstance());
Integer i = compute(Integer.of(-5), Integer.of(3), Integers.getInstance());
// Same algorithm, different types!
```

### 4. Boolean as Algebraic Type ✓

**Problem**: Enum is nice but doesn't implement interfaces.

**Solution**: Proper class implementing all relevant interfaces

```java
/**
 * Boolean algebra element (𝔹 = {false, true}).
 * Implements Semiring, Lattice, and FiniteSet for mathematical completeness.
 */
public final class Boolean implements Semiring<Boolean>,
                                       Lattice<Boolean>,
                                       FiniteSet<Boolean>,
                                       Comparable<Boolean> {
    
    // Constants (like enum)
    public static final Boolean FALSE = new Boolean(false);
    public static final Boolean TRUE = new Boolean(true);
    
    private final boolean value;
    
    // Private constructor (singleton pattern)
    private Boolean(boolean value) {
        this.value = value;
    }
    
    // Factory
    public static Boolean of(boolean value) {
        return value ? TRUE : FALSE;
    }
    
    // --- Semiring Implementation ---
    // (Boolean algebra is a semiring with + = OR, * = AND)
    
    @Override
    public Boolean add(Boolean other) {
        return of(this.value || other.value);  // OR
    }
    
    @Override
    public Boolean multiply(Boolean other) {
        return of(this.value && other.value);  // AND
    }
    
    @Override
    public Boolean zero() {
        return FALSE;  // OR identity
    }
    
    @Override
    public Boolean one() {
        return TRUE;   // AND identity
    }
    
    @Override
    public boolean isMultiplicationCommutative() {
        return true;
    }
    
    // --- Lattice Implementation ---
    
    @Override
    public Boolean join(Boolean other) {
        return add(other);  // OR = join (supremum)
    }
    
    @Override
    public Boolean meet(Boolean other) {
        return multiply(other);  // AND = meet (infimum)
    }
    
    // --- FiniteSet Implementation ---
    
    @Override
    public long size() {
        return 2;
    }
    
    @Override
    public boolean contains(Boolean element) {
        return element == TRUE || element == FALSE;
    }
    
    @Override
    public Iterator<Boolean> iterator() {
        return List.of(FALSE, TRUE).iterator();
    }
    
    @Override
    public Stream<Boolean> stream() {
        return Stream.of(FALSE, TRUE);
    }
    
    // --- Boolean-specific operations ---
    
    public Boolean not() {
        return of(!this.value);
    }
    
    public Boolean xor(Boolean other) {
        return of(this.value ^ other.value);
    }
    
    public Boolean implies(Boolean other) {
        return of(!this.value || other.value);  // a → b ≡ ¬a ∨ b
    }
    
    // --- Comparable ---
    
    @Override
    public int compareTo(Boolean other) {
        return java.lang.Boolean.compare(this.value, other.value);
    }
    
    // --- Standard methods ---
    
    public boolean booleanValue() {
        return value;
    }
    
    @Override
    public boolean equals(Object obj) {
        return this == obj;  // Singleton comparison
    }
    
    @Override
    public int hashCode() {
        return value ? 1 : 0;
    }
    
    @Override
    public String toString() {
        return value ? "true" : "false";
    }
}
```

**Usage**:
```java
// Mathematical context
Boolean a = Boolean.TRUE;
Boolean b = Boolean.FALSE;

// Semiring operations
Boolean result = a.add(b);     // OR
Boolean result2 = a.multiply(b); // AND

// Lattice operations
Boolean sup = a.join(b);       // Supremum
Boolean inf = a.meet(b);       // Infimum

// Boolean-specific
Boolean notA = a.not();
Boolean implies = a.implies(b);

// Works with generic algorithms!
public <E> E computeBoolean(E a, E b, Semiring<E> structure) {
    return structure.add(a, structure.multiply(b, b));
}

Boolean result = computeBoolean(Boolean.TRUE, Boolean.FALSE, Booleans.getInstance());
```

**Structure class**:
```java
public final class Booleans implements Semiring<Boolean>,
                                       Lattice<Boolean>,
                                       FiniteSet<Boolean> {
    private static final Booleans INSTANCE = new Booleans();
    
    public static Booleans getInstance() {
        return INSTANCE;
    }
    
    @Override
    public Boolean add(Boolean a, Boolean b) {
        return a.add(b);
    }
    
    // ... delegate all operations to Boolean
}
```

## Complete Type Hierarchy with Interfaces

```
Natural (abstract)
├── implements Semiring<Natural>
├── implements InfiniteSet<Natural>
├── implements Comparable<Natural>
└── Implementations:
    ├── NaturalInt    (int)
    ├── NaturalLong   (long)
    └── NaturalBig    (BigInteger)

Integer (abstract)
├── implements Ring<Integer>
├── implements InfiniteSet<Integer>
├── implements Comparable<Integer>
└── Implementations:
    ├── IntegerInt    (int)
    ├── IntegerLong   (long)
    └── IntegerBig    (BigInteger)

Rational (abstract)
├── implements Field<Rational>
├── implements InfiniteSet<Rational>
├── implements Comparable<Rational>
└── Implementations:
    ├── RationalIntInt      (int/int)
    ├── RationalLongLong    (long/long)
    └── RationalBigBig      (BigInteger/BigInteger)

Real (abstract)
├── implements Field<Real>
├── implements InfiniteSet<Real>
├── implements Comparable<Real>
└── Implementations:
    ├── RealFloat       (float)
    ├── RealDouble      (double)
    └── RealBigDecimal  (BigDecimal)

Complex (abstract)
├── implements Field<Complex<T>>
├── implements InfiniteSet<Complex<T>>
└── Implementations:
    ├── ComplexFloat    (Complex<RealFloat>)
    ├── ComplexDouble   (Complex<RealDouble>)
    └── ComplexBig      (Complex<RealBigDecimal>)

Boolean (final class)
├── implements Semiring<Boolean>
├── implements Lattice<Boolean>
├── implements FiniteSet<Boolean>
└── implements Comparable<Boolean>
```

## Performance Configuration Summary

| Mode | Overflow Checking | Speed | Safety |
|------|------------------|-------|--------|
| **SAFE** (default) | Pre-check before op | Fast (1-2 cycles overhead) | Always safe |
| **UNSAFE** | Disabled | Fastest (0 overhead) | May wrap/corrupt |
| **LAZY** | Check on output | Medium | Safe when accessed |

**Recommendation**: SAFE mode (negligible overhead, always correct)

## Summary

✅ **1. Precision Preferences**: MathContext (thread-local + build-time config)  
✅ **2. Overflow Performance**: Pre-check strategy (1-2 cycle overhead, no exceptions)  
✅ **3. Interface Compliance**: All types implement appropriate algebraic structures  
✅ **4. Boolean**: Proper class with Semiring + Lattice + FiniteSet  

**Next**: Implement this refined architecture!
