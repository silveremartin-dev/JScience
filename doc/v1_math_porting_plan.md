# V1 Mathematics Porting Plan - Critical Classes Only

**Date**: 2025-11-24  
**Goal**: Port minimum v1 mathematics classes needed to fix v2 compilation (~100 errors)

## Problem Statement

V2 has ~100 compilation errors due to missing v1 classes:
- `ExactComplex` - Exact complex number arithmetic
- `ExactReal` - Exact real number arithmetic
- `Field` - Algebraic field interface
- `Ring` - Algebraic ring interface (Field extends Ring)

## Scope - Mathematics Only

Per user: **Mathematics should be standalone** (except possibly measure).

Focus on self-contained mathematics packages from v1:
- `org.jscience.mathematics.algebraic.numbers`
- `org.jscience.mathematics.algebraic.fields`

## Missing Classes Analysis

### Priority 1: Algebraic Foundations

**Field Hierarchy** (in `v1/algebraic/fields/`):
- ✅ Found: `Field.java` - Division ring interface
- Need: `Ring.java` - Ring interface (Field extends Ring)
- May need: Basic field implementations

**Number Types** (in `v1/algebraic/numbers/`):
- ✅ Found: `ExactComplex.java` - Exact complex numbers
- ✅ Found: `ExactReal.java` - Exact real numbers  
- May need: `ExactRational.java` - Exact rationals (likely dependency)
- May need: `ExactQuaternion.java` (if referenced)
- May need: `Complex.java` - Regular complex numbers
- May need: Base number interfaces/classes

### Priority 2: Related Classes

Check dependencies of ExactComplex/ExactReal:
- Likely need rational arithmetic
- May need big integer/decimal support
- Check for utility classes

## Port Strategy

### Phase 1: Analyze Dependencies
- [ ] View ExactComplex.java full implementation
- [ ] View ExactReal.java full implementation
- [ ] Identify all imports and dependencies
- [ ] Create dependency tree

### Phase 2: Port Foundation Classes
- [ ] Port Ring interface
- [ ] Port Field interface  
- [ ] Port any required base number classes
- [ ] Apply MIT license + Java 21 modernization

### Phase 3: Port Number Types
- [ ] Port ExactRational (if needed)
- [ ] Port ExactReal
- [ ] Port ExactComplex
- [ ] Modernize to Java 21 (records where appropriate)

### Phase 4: Verify Compilation
- [ ] Run `mvn compile`
- [ ] Fix any remaining errors
- [ ] Ensure v2 mathematics compiles cleanly

### Phase 5: Add Tests
- [ ] Unit tests for ExactReal
- [ ] Unit tests for ExactComplex
- [ ] Integration tests with v2 polynomials
- [ ] Run `mvn test`

## Modernization Guidelines

### Java 21 Features to Apply
- Consider `record` for immutable number types
- Pattern matching for type checks
- Modern exception handling
- Improved Javadoc with @return, @param
-sealed classes for type hierarchies

### Code Quality
- MIT license headers
- Complete Javadoc
- Remove "DOCUMENT ME!" placeholders
- Use meaningful variable names
- Add null checks where appropriate

## File Locations

### Source (V1)
```
v1/src/org/jscience/mathematics/
├── algebraic/
│   ├── fields/
│   │   ├── Field.java
│   │   ├── Ring.java (need to find)
│   │   └── ExactRealField.java
│   └── numbers/
│       ├── ExactReal.java
│       ├── ExactComplex.java
│       ├── ExactRational.java
│       └── Complex.java
```

### Target (V2)
```
v2/src/main/java/org/jscience/mathematics/
├── algebraic/
│   ├── fields/
│   │   ├── Field.java (port & modernize)
│   │   └── Ring.java (port & modernize)
│   └── numbers/
│       ├── ExactReal.java (port & modernize)
│       ├── ExactComplex.java (port & modernize)
│       └── ExactRational.java (port if needed)
```

## Success Criteria

1. ✅ All v1 algebra dependencies ported
2. ✅ `mvn compile` succeeds with 0 errors
3. ✅ All ported classes have MIT headers
4. ✅ All ported classes have complete Javadoc
5. ✅ Unit tests for ported classes pass
6. ✅ v2 polynomial classes compile successfully
7. ✅ No regression in existing v2 classes

## Estimated Effort

- **Analysis**: 5-10 files to review
- **Porting**: 3-8 classes to port
- **Modernization**: ~500-1500 lines of code
- **Testing**: 5-10 test classes
- **Total**: 2-4 hours of focused work

## Next Steps

1. View full ExactComplex and ExactReal implementations
2. Map all dependencies
3. Start porting foundation classes (Ring, Field)
4. Port number classes
5. Verify compilation
6. Add tests

---

**Note**: Keep scope minimal - only port what's needed to compile v2. Other v1 packages (physics, chemistry, etc.) come later.
