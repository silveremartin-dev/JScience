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
- [x] Real (Exact & Approximate)
- [x] Boolean (Logic algebra)
- [x] Complex (Field over Real)
- [x] Fix circular dependencies in Natural/Real
- [x] Verify exact vs approximate behavior

## Phase 2: Linear Algebra & Core Packages (Level 1)
- [/] Vectors and Matrices
    - [x] Vector Interface
    - [x] Matrix Interface
    - [x] DenseVector Implementation
    - [x] DenseVector Implementation
    - [x] DenseMatrix Implementation
    - [/] GPU Acceleration Architecture
        - [x] ComputeContext & ComputeMode
        - [x] LinearAlgebraProvider Interface
        - [x] JavaLinearAlgebraProvider (CPU)
        - [x] Refactor DenseVector/Matrix to use Provider
        - [x] CudaLinearAlgebraProvider (GPU)
    - [x] Central Configuration (JScience.java)
        - [x] Global ComputeMode setting
        - [x] Global MathContext setting
        - [x] Logging & Introspection (Query capabilities, active mode)
    - [x] Operational Tooling
        - [x] Launch Scripts (Launcher.java - Portable)
        - [x] Documentation (Setup & Usage)
    - [/] Advanced Linear Algebra (Feature Parity with ND4J/Apache Math)
        - [x] Submatrix Operations
        - [x] Decompositions (LU, QR, Cholesky)
        - [x] Sparse Matrix Support (SparseMatrix, SparseLinearAlgebraProvider)
        - [ ] Hypermatrix Support (Tensors, ND4J integration?)
- [ ] `org.jscience.measure` (Units and Measures)
- [ ] `org.jscience.util`
- [ ] `org.jscience.io`

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
