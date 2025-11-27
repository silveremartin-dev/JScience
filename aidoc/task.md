# JScience Reimagined - Task List

## Project Configuration & Identity
- [ ] Rename Maven artifact to `jscience`
- [ ] Update `pom.xml` with new description, authors, and license
- [ ] Configure Maven Assembly Plugin for 4 distributions:
    - [ ] Level 1: Core/Embedded (Math, IO, Util, etc.)
    - [ ] Level 2: Natural Sciences (Physics, Biology, Chemistry, etc.)
    - [ ] Level 3: Human Sciences (Economics, Sociology, etc.)
    - [ ] Data: Resource files only

## Phase 1: Mathematical Foundation (Level 1)
### Core Algebraic Structures
- [x] Set interface (fundamental container)
- [x] Semiring, Ring, Field interfaces
- [x] VectorSpace, Module interfaces

### Scalar Types Implementation
- [x] Natural (Exact & Approximate)
- [x] Integer (Exact & Approximate)
        - [x] Global MathContext setting
        - [x] Logging & Introspection (Query capabilities, active mode)
    - [/] Operational Tooling
# JScience Reimagined - Task List

## Project Configuration & Identity
- [ ] Rename Maven artifact to `jscience`
- [ ] Update `pom.xml` with new description, authors, and license
- [ ] Configure Maven Assembly Plugin for 4 distributions:
    - [ ] Level 1: Core/Embedded (Math, IO, Util, etc.)
    - [ ] Level 2: Natural Sciences (Physics, Biology, Chemistry, etc.)
    - [ ] Level 3: Human Sciences (Economics, Sociology, etc.)
    - [ ] Data: Resource files only

## Phase 1: Mathematical Foundation (Level 1)
### Core Algebraic Structures
- [x] Set interface (fundamental container)
- [x] Semiring, Ring, Field interfaces
- [x] VectorSpace, Module interfaces

### Scalar Types Implementation
- [x] Natural (Exact & Approximate)
- [x] Integer (Exact & Approximate)
        - [x] Global MathContext setting
        - [x] Logging & Introspection (Query capabilities, active mode)
    - [/] Operational Tooling
        - [x] Launch Scripts (Launcher.java - Portable)
        - [ ] Documentation (Setup & Usage)
    - [/] Advanced Linear Algebra (Feature Parity with ND4J/Apache Math)
        - [x] Submatrix Operations
        - [x] Decompositions (LU, QR, Cholesky)
        - [x] Sparse Matrix Support (SparseMatrix, SparseLinearAlgebraProvider)
        - [ ] Hypermatrix Support (Tensors, ND4J integration?)
    - [x] Missing V1 Features
        - [x] `Lattice` interface
        - [x] `Rational` and `Complex` scalar types
        - [x] `Rationals` and `Complexes` sets
        - [x] Deprecate `DoubleNaturals`
        - [x] Port `org.jscience.mathematics.algebraic.categories` (Category, Functor, etc.)
        - [x] Port `org.jscience.mathematics.algebraic.groups` (LieGroup, CyclicGroup, etc.)
        - [x] Port `org.jscience.mathematics.algebraic.algebras` (LieAlgebra, HilbertSpace, etc.)
        - [ ] **Advanced Algebraic Structures**
            - [x] `SymmetricGroup` (Permutations)
            - [x] `FreeGroup` (Words)
            - [x] `U1Group` (Unitary Group / Circle Group)
            - [x] `KleeneAlgebra` (Regular Expressions logic)
            - [x] `Monad` (Category Theory)
            - [x] `Loop` (Quasigroup with identity)
            - [x] `Simplicial` (Simplicial Complex / Topology)
            - [x] `SO3_1Group` (Lorentz Group)
            - [x] `PolynomialRing` (Algebra)
            - [x] `CliffordAlgebra` (Geometric Algebra)
            - [x] `OrderedGroup` (Order Theory)
            - [x] `FiniteGroup` (Group Theory)
            - [x] `VectorSpace2D` (2D Real Vector Space)
            - [x] `VectorSpace3D` (3D Real Vector Space)
            - [x] `SU2Group` (Special Unitary Group 2)
            - [x] `SU3Group` (Special Unitary Group 3)
            - [x] `Bifunctor` (Category Theory)
            - [x] `FinSet` (Category of Finite Sets)
            - [x] `Hilb` (Category of Hilbert Spaces)
            - [x] `HomFunctor` (Category Theory)
            - [x] `Preorder` (Category Theory)
            - [x] `Magma` (Algebraic Structure)
            - [x] `Semigroup` (Algebraic Structure)
            - [x] `Quasigroup` (Algebraic Structure)
    - [x] **Modern UI (JavaFX)**
        - [x] Create `org.jscience.ui` module/package
        - [x] Implement Matrix/Vector viewer (modernizing V1's `matrices.gui`)
        - [x] Add JavaFX dependencies to `pom.xml`
- [ ] `org.jscience.measure` (Units and Measures)
- [ ] `org.jscience.util`
- [ ] `org.jscience.io`

## Phase 2: Mathematical Expansion (Level 1+)
### 1. Analysis (Calculus & Functions)
- [x] `Function<D, R>` Interface
- [x] `RealFunction` (R -> R)
- [x] `Polynomials` (Roots, Evaluation)
- [x] `Differentiation` (Numerical/Symbolic)
- [x] `Integration` (Numerical/Symbolic)

### 3. Linear Algebra (Advanced)
- [ ] `Decompositions` (LU, QR, Cholesky, SVD, Eigenvalue)
- [ ] `SparseMatrix` Support
- [ ] `Tensors` (Multi-dimensional arrays)
# JScience Reimagined - Task List

## Project Configuration & Identity
- [ ] Rename Maven artifact to `jscience`
- [ ] Update `pom.xml` with new description, authors, and license
- [ ] Configure Maven Assembly Plugin for 4 distributions:
    - [ ] Level 1: Core/Embedded (Math, IO, Util, etc.)
    - [ ] Level 2: Natural Sciences (Physics, Biology, Chemistry, etc.)
    - [ ] Level 3: Human Sciences (Economics, Sociology, etc.)
    - [ ] Data: Resource files only

## Phase 1: Mathematical Foundation (Level 1)
### Core Algebraic Structures
- [x] Set interface (fundamental container)
- [x] Semiring, Ring, Field interfaces
- [x] VectorSpace, Module interfaces

### Scalar Types Implementation
- [x] Natural (Exact & Approximate)
- [x] Integer (Exact & Approximate)
        - [x] Global MathContext setting
        - [x] Logging & Introspection (Query capabilities, active mode)
    - [/] Operational Tooling
# JScience Reimagined - Task List

## Project Configuration & Identity
- [ ] Rename Maven artifact to `jscience`
- [ ] Update `pom.xml` with new description, authors, and license
- [ ] Configure Maven Assembly Plugin for 4 distributions:
    - [ ] Level 1: Core/Embedded (Math, IO, Util, etc.)
    - [ ] Level 2: Natural Sciences (Physics, Biology, Chemistry, etc.)
    - [ ] Level 3: Human Sciences (Economics, Sociology, etc.)
    - [ ] Data: Resource files only

## Phase 1: Mathematical Foundation (Level 1)
### Core Algebraic Structures
- [x] Set interface (fundamental container)
- [x] Semiring, Ring, Field interfaces
- [x] VectorSpace, Module interfaces

### Scalar Types Implementation
- [x] Natural (Exact & Approximate)
- [x] Integer (Exact & Approximate)
        - [x] Global MathContext setting
        - [x] Logging & Introspection (Query capabilities, active mode)
    - [/] Operational Tooling
        - [x] Launch Scripts (Launcher.java - Portable)
        - [ ] Documentation (Setup & Usage)
    - [/] Advanced Linear Algebra (Feature Parity with ND4J/Apache Math)
        - [x] Submatrix Operations
        - [x] Decompositions (LU, QR, Cholesky)
        - [x] Sparse Matrix Support (SparseMatrix, SparseLinearAlgebraProvider)
        - [ ] Hypermatrix Support (Tensors, ND4J integration?)
    - [x] Missing V1 Features
        - [x] `Lattice` interface
        - [x] `Rational` and `Complex` scalar types
        - [x] `Rationals` and `Complexes` sets
        - [x] Deprecate `DoubleNaturals`
        - [x] Port `org.jscience.mathematics.algebraic.categories` (Category, Functor, etc.)
        - [x] Port `org.jscience.mathematics.algebraic.groups` (LieGroup, CyclicGroup, etc.)
        - [x] Port `org.jscience.mathematics.algebraic.algebras` (LieAlgebra, HilbertSpace, etc.)
        - [ ] **Advanced Algebraic Structures**
            - [x] `SymmetricGroup` (Permutations)
            - [x] `FreeGroup` (Words)
            - [x] `U1Group` (Unitary Group / Circle Group)
            - [x] `KleeneAlgebra` (Regular Expressions logic)
            - [x] `Monad` (Category Theory)
            - [x] `Loop` (Quasigroup with identity)
            - [x] `Simplicial` (Simplicial Complex / Topology)
            - [x] `SO3_1Group` (Lorentz Group)
            - [x] `PolynomialRing` (Algebra)
            - [x] `CliffordAlgebra` (Geometric Algebra)
            - [x] `OrderedGroup` (Order Theory)
            - [x] `FiniteGroup` (Group Theory)
            - [x] `VectorSpace2D` (2D Real Vector Space)
            - [x] `VectorSpace3D` (3D Real Vector Space)
            - [x] `SU2Group` (Special Unitary Group 2)
            - [x] `SU3Group` (Special Unitary Group 3)
            - [x] `Bifunctor` (Category Theory)
            - [x] `FinSet` (Category of Finite Sets)
            - [x] `Hilb` (Category of Hilbert Spaces)
            - [x] `HomFunctor` (Category Theory)
            - [x] `Preorder` (Category Theory)
            - [x] `Magma` (Algebraic Structure)
            - [x] `Semigroup` (Algebraic Structure)
            - [x] `Quasigroup` (Algebraic Structure)
    - [x] **Modern UI (JavaFX)**
        - [x] Create `org.jscience.ui` module/package
        - [x] Implement Matrix/Vector viewer (modernizing V1's `matrices.gui`)
        - [x] Add JavaFX dependencies to `pom.xml`
- [ ] `org.jscience.measure` (Units and Measures)
- [ ] `org.jscience.util`
- [ ] `org.jscience.io`

## Phase 2: Mathematical Expansion (Level 1+)
### 1. Analysis (Calculus & Functions)
- [x] `Function<D, R>` Interface
- [x] `RealFunction` (R -> R)
- [x] `Polynomials` (Roots, Evaluation)
- [x] `Differentiation` (Numerical/Symbolic)
- [x] `Integration` (Numerical/Symbolic)

### 3. Linear Algebra (Advanced)
- [ ] `Decompositions` (LU, QR, Cholesky, SVD, Eigenvalue)
- [ ] `SparseMatrix` Support
- [ ] `Tensors` (Multi-dimensional arrays)

### 4. Geometry & Topology
### 12. Applied Mathematics
- [ ] `GameTheory` (Nash Equilibrium, Minimax)

## Phase 3: Natural Sciences (Level 2)
- [ ] `org.jscience.physics`
- [ ] `org.jscience.chemistry`
- [ ] `org.jscience.biology`
- [ ] `org.jscience.astronomy`
- [ ] `org.jscience.earth` (Geology, etc.)

## Phase 4: Human Sciences (Level 3)
- [ ] `org.jscience.economics`
- [ ] `org.jscience.sociology`
- [ ] `org.jscience.psychology`
- [ ] `org.jscience.geography`

## Data Management
- [x] Extract data files from V1/V2 legacy
- [ ] Create resource loaders
- [ ] Organize into `src/main/resources`

## Continuous
- [x] Cleanup obsolete directories (unified-science-*)
- [x] Move demos to test source tree
- [ ] Keep all documentation up to date
- [ ] Maintain test coverage >90%
- [ ] Update i18n for all new features
- [ ] **Final Optimization Review** (Task: Full check of optimizations at project end)
