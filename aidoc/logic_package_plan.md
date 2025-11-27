# Logic Package Plan (`org.jscience.mathematics.logic`)

This package aims to provide a rigorous mathematical foundation for logic, supporting both classical and non-classical systems. It serves as the "axiomatic" core for reasoning within JScience.

## 1. Core Abstractions
We define logic in a generalized way, not limited to boolean `true`/`false`.

*   `Logic<T>`: Interface for a logical system where `T` is the type of truth value.
*   `TruthValue`: Interface for truth values (comparable, orderable).

### Implementations
*   `BooleanLogic`: Classical 2-valued logic (`Boolean`).
*   `FuzzyLogic`: Continuous logic (`Real` in [0, 1]).
*   `ThreeValuedLogic`: Kleene/Łukasiewicz logic (True, False, Unknown).

## 2. Propositional Logic
Focuses on propositions and logical connectives.

*   `Proposition`: A statement that has a truth value.
*   `Connective`:
    *   `And` (Conjunction)
    *   `Or` (Disjunction)
    *   `Not` (Negation)
    *   `Implies` (Implication)
    *   `Equivalent` (Biconditional)
*   `TruthTable`: Utility to generate truth tables for expressions.
*   `Tautology`: Checks if a formula is always true.
*   `Satisfiability`: Interface for SAT solvers (find variable assignments that make a formula true).

## 3. Predicate Logic (First-Order Logic)
Extends propositional logic with quantifiers and variables.

*   `Term`: Represents an object (Constant, Variable, Function).
*   `Predicate`: A relation over terms (e.g., `IsPrime(x)`).
*   `Quantifier`:
    *   `ForAll` (Universal: ∀x P(x))
    *   `Exists` (Existential: ∃x P(x))
*   `Unification`: Algorithm to find substitutions that make two terms identical (crucial for AI/Prolog).
*   `Resolution`: Inference rule for automated theorem proving.

## 4. Axiomatic Systems
Tools for defining formal systems and verifying proofs.

*   `Axiom`: A proposition assumed to be true.
*   `InferenceRule`: A rule to derive new theorems from axioms/theorems (e.g., Modus Ponens).
*   `Theorem`: A derived proposition.
*   `Proof`: A sequence of steps deriving a theorem from axioms.
*   `FormalSystem`: A collection of axioms and rules (e.g., Peano Arithmetic, Euclidean Geometry, ZFC Set Theory).

## 5. AI Integration
*   **Knowledge Base**: A store of axioms and facts.
*   **Inference Engine**: Applies rules to the KB to derive new facts (Forward/Backward Chaining).
*   **Fuzzy Inference**: Applies fuzzy rules (e.g., "If Temperature is High then Fan is Fast").

## Legacy Porting
We will evaluate and port relevant parts from `jscience-old-v1`:
*   `org.jscience.computing.ai.planning` (LogicalExpression)
*   `org.jscience.computing.ai.fuzzylogic` (FuzzyEngine)
