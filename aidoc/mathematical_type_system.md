# Mathematical Type System Architecture

## User's Vision (Approved!)

> "When doing physics, I don't want to get concerned about whether this will be a long or an int, but I want to manipulate numbers from ℕ or ℝ or ℂ. This is all that is needed to know."

**Perfect!** This is the right abstraction level.

## Mathematical Types (User-Facing)

| Type | Symbol | Description | Backing Implementations |
|------|--------|-------------|-------------------------|
| **Natural** | ℕ | {0, 1, 2, ...} | BigInteger, long, int |
| **Integer** | ℤ | {..., -1, 0, 1, ...} | BigInteger, long, int |
| **Rational** | ℚ | p/q (fractions) | BigInteger/BigInteger, long/long, int/int |
| **Real** | ℝ | All real numbers | BigDecimal, double, float |
| **Complex** | ℂ | a + bi | Real/Real backing |
| **Boolean** | 𝔹 | {true, false} | boolean |
| **Byte** | — | {-128..127} | byte |

## Smart Delegation Strategy

### Principle: Users Never Choose Implementation

```java
// User writes (mathematical concept):
Natural n = Natural.of(1000);      // Smart factory chooses backing
Real r = Real.of(3.14159);          // Smart factory chooses backing
Complex c = Complex.of(1.0, 2.0);   // Smart factory chooses backing

// NOT this (implementation details):
Natural n = new NaturalBigInt(1000);  // ❌ Too explicit
Natural n = new NaturalLong(1000);     // ❌ Exposes internals
```

### How It Works

**Natural** (Smart Factory Pattern):
```java
public abstract class Natural {
    // Factory method - chooses implementation
    public static Natural of(long value) {
        if (value < 0) throw new IllegalArgumentException();
        
        // Smart delegation
        if (value <= Integer.MAX_VALUE) {
            return new NaturalInt((int)value);  // Fast!
        } else if (value <= Long.MAX_VALUE) {
            return new NaturalLong(value);      // Medium
        } else {
            return new NaturalBig(BigInteger.valueOf(value));  // Slow but exact
        }
    }
    
    // Abstract operations
    public abstract Natural add(Natural other);
    public abstract Natural multiply(Natural other);
    // ...
}
```

**Concrete Implementations** (Internal):
```java
// Package-private: users never see these
class NaturalInt extends Natural {
    private final int value;
    
    public Natural add(Natural other) {
        // Upgrade if overflow
        try {
            int result = Math.addExact(this.value, ((NaturalInt)other).value);
            return Natural.of(result);
        } catch (ArithmeticException e) {
            // Upgrade to long
            return new NaturalLong((long)this.value + ((NaturalInt)other).value);
        }
    }
}

class NaturalLong extends Natural {
    private final long value;
    
    public Natural add(Natural other) {
        // Similar with upgrade to BigInteger on overflow
        // ...
    }
}

class NaturalBig extends Natural {
    private final BigInteger value;
    
    public Natural add(Natural other) {
        // Always exact, never overflows
        return Natural.of(this.value.add(((NaturalBig)other).value));
    }
}
```

## Complete Type Mapping

### Natural (ℕ)
```
Natural
├── NaturalInt    (int: 0 to 2^31-1)        [FAST]
├── NaturalLong   (long: 2^31 to 2^63-1)    [MEDIUM]
└── NaturalBig    (BigInteger: 2^63+)       [SLOW, EXACT]
```

**Smart Promotion**:
- Start with `int` for small values
- Promote to `long` on overflow
- Promote to `BigInteger` for huge values
- **Transparent to user!**

### Integer (ℤ)
```
Integer
├── IntegerInt    (int: -2^31 to 2^31-1)
├── IntegerLong   (long: -2^63 to 2^63-1)
└── IntegerBig    (BigInteger: unlimited)
```

### Rational (ℚ)
```
Rational
├── RationalIntInt       (int/int: small fractions)
├── RationalLongLong     (long/long: medium fractions)
└── RationalBigBig       (BigInteger/BigInteger: exact)
```

**Example**:
```java
Rational half = Rational.of(1, 2);           // Uses int/int internally
Rational huge = Rational.of(Long.MAX_VALUE, 3); // Uses long/long
// Operations automatically promote as needed
```

### Real (ℝ)
```
Real
├── RealFloat       (float: 7 digits, fast)
├── RealDouble      (double: 15 digits, default)
└── RealBigDecimal  (BigDecimal: arbitrary precision, exact)
```

**User Control** (when precision matters):
```java
Real r1 = Real.of(3.14159);              // Uses double (default)
Real r2 = Real.exact("3.14159265358979"); // Uses BigDecimal (exact)
Real r3 = Real.fast(3.14f);               // Uses float (fast, GPU)
```

### Decimal (for Money/Finance)

User asked: "Decimal backed up by ???"

**Answer**: Decimal = exact Real (always BigDecimal)

```java
public final class Decimal extends RealBigDecimal {
    // Always exact, never approximate
    private final BigDecimal value;
    private final MathContext precision;
    
    public static Decimal of(String value) {
        return new Decimal(new BigDecimal(value), MathContext.DECIMAL128);
    }
}
```

**Use cases**:
- Money: `Decimal price = Decimal.of("19.99");`
- Accounting: Always exact
- Tax calculations: No rounding errors

### Complex (ℂ)
```
Complex<T extends Real>
├── ComplexFloat       (float + float)
├── ComplexDouble      (double + double)
└── ComplexBig         (BigDecimal + BigDecimal)
```

**Usage**:
```java
Complex c = Complex.of(1.0, 2.0);  // Uses double (default)
Complex exact = Complex.exact("1.5", "2.7"); // Uses BigDecimal
```

### Boolean (𝔹)
```java
public enum MathBoolean {
    FALSE(false),
    TRUE(true);
    
    private final boolean value;
    
    public MathBoolean and(MathBoolean other) { ... }
    public MathBoolean or(MathBoolean other) { ... }
    public MathBoolean not() { ... }
}
```

Backed by primitive `boolean`.

### Byte
```java
public final class MathByte {
    private final byte value;  // -128 to 127
    
    public MathByte add(MathByte other) {
        // Promote to Integer on overflow
        int result = (int)this.value + (int)other.value;
        if (result < Byte.MIN_VALUE || result > Byte.MAX_VALUE) {
            return Integer.of(result);  // Automatic promotion!
        }
        return new MathByte((byte)result);
    }
}
```

## Implementation Strategy

### Phase 1: Core Types
1. `Natural` (abstract) + 3 implementations (int, long, BigInteger)
2. `Integer` (abstract) + 3 implementations
3. `Real` (abstract) + 3 implementations (float, double, BigDecimal)

### Phase 2: Derived Types
1. `Rational` (abstract) + 3 implementations
2. `Complex<T>` (generic over Real)
3. `Decimal` (alias for exact Real)

### Phase 3: Special Types
1. `MathBoolean`
2. `MathByte`

## User Experience Examples

### Physics (User's Use Case)

```java
// User thinks in mathematical terms only!
Real mass = Real.of(9.109e-31);      // Electron mass (kg)
Real c = Real.of(299792458.0);        // Speed of light (m/s)
Real energy = mass.multiply(c.pow(2)); // E = mc²

// No concern about double vs BigDecimal!
// Library chooses appropriate backing
```

### Number Theory

```java
// Working with natural numbers
Natural fibonacci(int n) {
    Natural a = Natural.ZERO;
    Natural b = Natural.ONE;
    
    for (int i = 0; i < n; i++) {
        Natural temp = a.add(b);
        a = b;
        b = temp;
    }
    return b;
}

Natural fib100 = fibonacci(100);
// Automatically uses BigInteger internally (huge number)
// User doesn't need to know!
```

### Finance

```java
Decimal price = Decimal.of("19.99");
Decimal quantity = Decimal.of("100");
Decimal tax = Decimal.of("0.08");

Decimal subtotal = price.multiply(quantity);
Decimal total = subtotal.add(subtotal.multiply(tax));

System.out.println(total);  // "2158.92" - exact, no rounding errors!
```

##  Where DoubleScalar Strategy Goes

User asked: "Can't we put the strategy of DoubleScalar in the DoubleReal?"

**Answer**: YES! Exactly right.

**Before** (confusing):
```java
DoubleScalar strategy = new DoubleScalar();  // Strategy pattern
Double result = strategy.add(2.0, 3.0);       // Awkward
```

**After** (clean):
```java
Real a = Real.of(2.0);  // RealDouble internally
Real b = Real.of(3.0);  // RealDouble internally
Real c = a.add(b);      // Direct, ergonomic
```

The "strategy" is now **built into** `RealDouble` implementation. No separate `DoubleScalar` class needed!

## Summary Table

| Mathematical Type | User API | Internal Implementations | Smart Delegation |
|-------------------|----------|--------------------------|------------------|
| `Natural` | `Natural.of(...)` | NaturalInt, NaturalLong, NaturalBig | On overflow |
| `Integer` | `Integer.of(...)` | IntegerInt, IntegerLong, IntegerBig | On overflow |
| `Rational` | `Rational.of(p, q)` | RationalIntInt, RationalLongLong, RationalBigBig | On overflow |
| `Real` | `Real.of(...)` | RealFloat, RealDouble, RealBigDecimal | On precision need |
| `Decimal` | `Decimal.of(...)` | Always BigDecimal | N/A (always exact) |
| `Complex` | `Complex.of(re, im)` | Complex<RealFloat>, Complex<RealDouble>, Complex<RealBig> | Based on Real |
| `MathBoolean` | `MathBoolean.TRUE` | boolean | N/A |
| `MathByte` | `MathByte.of(...)` | byte | Promote to Integer |

## Key Principles

1. ✅ **User thinks mathematically**: ℕ, ℤ, ℚ, ℝ, ℂ
2. ✅ **Smart delegation**: Library chooses int/long/BigInteger
3. ✅ **Automatic promotion**: Overflow → upgrade backing
4. ✅ **Performance**: Start with fastest (int), upgrade as needed
5. ✅ **Correctness**: Always mathematically sound
6. ✅ **Simple API**: `Natural.of()`, `Real.of()`, `Complex.of()`

This is the architecture you described - perfect for scientific computing!
