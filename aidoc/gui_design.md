# Modern Matrix Viewer Design (JavaFX)

## Goal
Create a modern, efficient, and interactive Matrix Viewer component for JScience, replacing the Swing-based `matrices.gui` from V1.

## Features
1.  **Visual Representation**:
    *   Grid-based view of matrix elements.
    *   Support for large matrices (virtualization).
    *   Heatmap visualization (color coding based on value magnitude).
    *   Sparse matrix visualization (highlight non-zero elements).

2.  **Interactivity**:
    *   Editable cells (if matrix is mutable).
    *   Row/Column highlighting.
    *   Tooltip with precise values.

3.  **Architecture**:
    *   `MatrixViewer`: Main JavaFX control (extends `Control` or `Region`).
    *   `MatrixModel`: Adapter between `Matrix<E>` and the UI.
    *   `CellRenderer`: Customizable rendering for different element types (`Real`, `Complex`, `Rational`).

## Implementation Plan

### 1. `org.jscience.ui` Module
Create a new package `org.jscience.ui` and `org.jscience.ui.matrix`.

### 2. `MatrixViewer` Control
*   Use `TableView` or `GridPane`?
    *   `TableView`: Good for rows/cols, but column virtualization is tricky for many columns.
    *   `ControlsFX` SpreadsheetView: Heavy dependency.
    *   **Custom Virtualized Grid**: Best for performance with huge matrices.
    *   **Decision**: Start with `TableView` for simplicity (List of Lists), but if performance is bad, switch to custom `Canvas` or `VirtualFlow`.
    *   *Actually*, for a "Matrix", `TableView` is okay if columns are reasonable (< 100). For huge matrices, we need a specialized viewer.
    *   Let's build a `TableView` based prototype first, as it's standard JavaFX.

### 3. `MatrixViewModel`
*   Wraps `Matrix<E>`.
*   Provides `ObservableValue` for cells.

### 4. `CellFactory`
*   `RealCell`: Formats doubles, adds color gradient background.
*   `ComplexCell`: Formats "a + bi".

## Code Structure

```java
package org.jscience.ui.matrix;

import javafx.scene.control.TableView;
import org.jscience.mathematics.vector.Matrix;

public class MatrixViewer<E> extends BorderPane {
    private final TableView<Vector<E>> table;
    private Matrix<E> matrix;
    
    // ...
}
```
