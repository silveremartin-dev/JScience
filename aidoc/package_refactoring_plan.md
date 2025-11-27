# Package Refactoring Plan

The goal is to declutter `org.jscience.mathematics.algebra` by moving concrete implementations into meaningful subpackages, leaving mostly interfaces in the root package.

## Naming Convention
Java package names are typically singular (e.g., `java.util`). However, for packages that contain "collections of X", plural is sometimes used (e.g., `java.nio.channels`).
The user suggested `algebra.algebras`.
We will use the following structure:

*   `org.jscience.mathematics.algebra` (Interfaces: `Ring`, `Field`, `VectorSpace`, `Group`, `Monoid`)
*   `org.jscience.mathematics.algebra.group` (Implementations: `CyclicGroup`, `SymmetricGroup`, `FreeGroup`, `FiniteGroup`, `OrderedGroup`, `LieGroup`)
*   `org.jscience.mathematics.algebra.ring` (Implementations: `PolynomialRing`)
*   `org.jscience.mathematics.algebra.field` (Implementations: `FieldExtensions` - future)
*   `org.jscience.mathematics.algebra.space` (Implementations: `VectorSpace2D`, `VectorSpace3D`, `HilbertSpace`)
*   `org.jscience.mathematics.algebra.structure` (General structures: `CliffordAlgebra`, `KleeneAlgebra`, `Loop`, `Magma`, `Semigroup`, `Quasigroup`)
    *   *Alternative*: `org.jscience.mathematics.algebra.algebras` for `CliffordAlgebra`, `KleeneAlgebra`.

## Proposed Moves

### To `org.jscience.mathematics.algebra.group`
*   `SymmetricGroup.java`
*   `FreeGroup.java`
*   `CyclicGroup.java`
*   `FiniteGroup.java`
*   `OrderedGroup.java`
*   `U1Group.java`
*   `SU2Group.java`
*   `SU3Group.java`
*   `SO3_1Group.java`

### To `org.jscience.mathematics.algebra.ring`
*   `PolynomialRing.java`

### To `org.jscience.mathematics.algebra.structure` (or `algebras`)
*   `CliffordAlgebra.java`
*   `KleeneAlgebra.java`
*   `Loop.java`
*   `Magma.java`
*   `Semigroup.java`
*   `Quasigroup.java`

### To `org.jscience.mathematics.algebra.space` (or keep in `vector`?)
*   `VectorSpace2D.java` -> Already in `vector`? No, currently in `algebra` or `vector`?
    *   Check: `VectorSpace2D` was created in `vector` package in previous steps.
    *   `HilbertSpace.java` is in `algebra`. Move to `space` or `vector`? `Hilb` category is in `algebra.category`.

## Action Plan
1.  Create new directories.
2.  Move files.
3.  Update package declarations in files.
4.  Update imports in other files.
