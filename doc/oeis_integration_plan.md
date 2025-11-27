# Series & OEIS Integration Architecture

## OEIS (Online Encyclopedia of Integer Sequences)

### What is OEIS?
- **URL**: https://oeis.org
- **Database**: 370,000+ integer sequences
- **Standard format** for mathematical sequences
- **Examples**: 
  - A000045 = Fibonacci (0,1,1,2,3,5,8,13,...)
  - A000040 = Primes (2,3,5,7,11,13,17,...)
  - A000290 = Squares (0,1,4,9,16,25,36,...)

### OEIS Format
```
%I A000045                                    # ID
%S A000045 0,1,1,2,3,5,8,13,21,34,55,89      # Sequence data
%T A000045 144,233,377,610,987,1597          # More data
%N A000045 Fibonacci numbers: F(n) = F(n-1) + F(n-2)  # Name
%C A000045 Also number of binary strings...  # Comments
%F A000045 F(n) = ((1+sqrt(5))^n-(1-sqrt(5))^n)/(2^n*sqrt(5))  # Formula
%e A000045 F(5) = 5 because...               # Examples
%p A000045 a:= n-> (Matrix([[1,1],[1,0]])^n)[1,2]  # Program
%Y A000045 Cf. A000032, A001519.              # Cross-references
%K A000045 nonn,core,nice                     # Keywords
%O A000045 0,3                                # Offset, first index > 1
%A A000045 N. J. A. Sloane                    # Author
```

---

## Proposed Architecture

### 1. Sequence Interface
```java
package org.jscience.mathematics.sequences;

interface Sequence<T> {
    T get(int index);           // a_n
    String getId();             // OEIS ID (e.g., "A000045")
    String getName();           // Human-readable name
    String getFormula();        // Mathematical formula (optional)
}

interface IntegerSequence extends Sequence<java.math.BigInteger> {
    // Most OEIS sequences are integer sequences
}
```

### 2. OEIS Importer/Exporter
```java
package org.jscience.mathematics.sequences.oeis;

class OEISImporter {
    IntegerSequence fromFile(Path oeisFile);
    IntegerSequence fromId(String oeisId);  // Fetch from oeis.org API
    IntegerSequence fromText(String oeisFormat);
}

class OEISExporter {
    String toOEISFormat(Sequence<?> sequence);
    void exportToFile(Sequence<?> sequence, Path output);
}
```

### 3. Standard Sequences (from OEIS)
```java
// A000045 - Fibonacci
class FibonacciSequence implements IntegerSequence {
    @Override public String getId() { return "A000045"; }
    @Override public BigInteger get(int n) { /* F(n) */ }
}

// A000040 - Primes
class PrimeSequence implements IntegerSequence {
    @Override public String getId() { return "A000040"; }
    @Override public BigInteger get(int n) { /* nth prime */ }
}

// A000290 - Squares  
class SquareSequence implements IntegerSequence {
    @Override public String getId() { return "A000290"; }
    @Override public BigInteger get(int n) { return BigInteger.valueOf(n * n); }
}

// A000720 - Pi(n) = number of primes <= n
class PrimePiSequence implements IntegerSequence {
    @Override public String getId() { return "A000720"; }
    @Override public BigInteger get(int n) { /* count primes */ }
}
```

### 4. Sequence Operations
```java
interface SequenceOperations {
    // Transform
    <T, R> Sequence<R> map(Sequence<T> seq, Function<T, R> f);
    
    // Combine
    Sequence<Integer> add(IntegerSequence a, IntegerSequence b);
    
    // Generate
    Sequence<Integer> differences(IntegerSequence seq);  // a(n+1) - a(n)
    Sequence<Integer> partialSums(IntegerSequence seq);  // Σ a(k), k=0..n
}
```

---

## Implementation Plan

### Phase 1: Core Sequences
```
org.jscience.mathematics.sequences/
  - Sequence.java
  - IntegerSequence.java
  - ConvergentSequence.java
  - RecursiveSequence.java (for Fibonacci-like)
```

### Phase 2: Standard OEIS Sequences
```
org.jscience.mathematics.sequences.standard/
  - FibonacciSequence.java      (A000045)
  - PrimeSequence.java           (A000040)
  - SquareSequence.java          (A000290)
  - TriangularSequence.java      (A000217)
  - CatalanSequence.java         (A000108)
  - BellSequence.java            (A000110)
  - FactorialSequence.java       (A000142)
  - PrimePiSequence.java         (A000720)
```

### Phase 3: OEIS Integration
```
org.jscience.mathematics.sequences.oeis/
  - OEISImporter.java
  - OEISExporter.java
  - OEISFormat.java (parser)
  - OEISClient.java (HTTP client for oeis.org API)
```

### Phase 4: Series (Infinite Sums)
```java
interface InfiniteSeries<T> {
    T partialSum(int n);        // S_n = Σ a(k), k=0..n
    boolean isConvergent();
    T limit();                  // If convergent
}

class GeometricSeries implements InfiniteSeries<Real> {
    // Σ ar^n, converges if |r| < 1
}

class HarmonicSeries implements InfiniteSeries<Real> {
    // Σ 1/n, diverges but useful
}

class PowerSeries implements InfiniteSeries<Real> {
    // For Taylor/Maclaurin expansions
}
```

---

## Benefits

✅ **Interoperability**: Direct OEIS import/export
✅ **Standards compliance**: OEIS is the de facto standard
✅ **Rich catalog**: 370,000+ sequences available
✅ **Research-friendly**: Scientists can share sequences
✅ **Validation**: Compare against OEIS reference data
✅ **Discovery**: Import new sequences from OEIS

---

## Example Usage

```java
// Import from OEIS
OEISImporter importer = new OEISImporter();
IntegerSequence fib = importer.fromId("A000045");
System.out.println(fib.get(10));  // 55

// Create custom sequence
IntegerSequence mySeq = new IntegerSequence() {
    @Override public String getId() { return "CUSTOM001"; }
    @Override public String getName() { return "My sequence"; }
    @Override public BigInteger get(int n) { return BigInteger.valueOf(n * n + 1); }
};

// Export to OEIS format
OEISExporter exporter = new OEISExporter();
String oeisFormat = exporter.toOEISFormat(mySeq);
System.out.println(oeisFormat);
// %I CUSTOM001
// %S CUSTOM001 1,2,5,10,17,26,37,50,65,82,101
// %N CUSTOM001 My sequence
```

---

## OEIS API Integration

OEIS provides HTTP API:
```
GET https://oeis.org/search?q=id:A000045&fmt=json
```

Response:
```json
{
  "results": [{
    "number": 45,
    "data": "0,1,1,2,3,5,8,13,21,34,55,89,144",
    "name": "Fibonacci numbers",
    "formula": ["F(n) = F(n-1) + F(n-2)"],
    "program": ["(PARI) a(n)=fibonacci(n)"]
  }]
}
```

We can fetch sequences programmatically!

---

## Priority

**HIGH** - This makes JScience a serious research tool.
OEIS integration positions us as a comprehensive mathematical library.
