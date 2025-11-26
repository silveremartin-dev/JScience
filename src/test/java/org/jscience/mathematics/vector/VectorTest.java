package org.jscience.mathematics.vector;

import org.jscience.mathematics.number.Real;
import org.jscience.mathematics.number.set.Reals;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;

class VectorTest {

    @Test
    void testDenseVectorCreation() {
        List<Real> elements = Arrays.asList(Real.of(1.0), Real.of(2.0), Real.of(3.0));
        Vector<Real> v = new DenseVector<>(elements, Reals.getInstance());

        assertEquals(3, v.dimension());
        assertEquals(Real.of(1.0), v.get(0));
        assertEquals(Real.of(2.0), v.get(1));
        assertEquals(Real.of(3.0), v.get(2));
    }

    @Test
    void testAdd() {
        Vector<Real> v1 = new DenseVector<>(Arrays.asList(Real.of(1.0), Real.of(2.0)), Reals.getInstance());
        Vector<Real> v2 = new DenseVector<>(Arrays.asList(Real.of(3.0), Real.of(4.0)), Reals.getInstance());

        Vector<Real> sum = v1.add(v2);

        assertEquals(Real.of(4.0), sum.get(0));
        assertEquals(Real.of(6.0), sum.get(1));
    }

    @Test
    void testSubtract() {
        Vector<Real> v1 = new DenseVector<>(Arrays.asList(Real.of(1.0), Real.of(2.0)), Reals.getInstance());
        Vector<Real> v2 = new DenseVector<>(Arrays.asList(Real.of(3.0), Real.of(4.0)), Reals.getInstance());

        Vector<Real> diff = v1.subtract(v2);

        assertEquals(Real.of(-2.0), diff.get(0));
        assertEquals(Real.of(-2.0), diff.get(1));
    }

    @Test
    void testMultiplyScalar() {
        Vector<Real> v = new DenseVector<>(Arrays.asList(Real.of(1.0), Real.of(2.0)), Reals.getInstance());

        Vector<Real> scaled = v.multiply(Real.of(2.0));

        assertEquals(Real.of(2.0), scaled.get(0));
        assertEquals(Real.of(4.0), scaled.get(1));
    }

    @Test
    void testDotProduct() {
        Vector<Real> v1 = new DenseVector<>(Arrays.asList(Real.of(1.0), Real.of(2.0)), Reals.getInstance());
        Vector<Real> v2 = new DenseVector<>(Arrays.asList(Real.of(3.0), Real.of(4.0)), Reals.getInstance());

        Real dot = v1.dot(v2);

        // 1*3 + 2*4 = 3 + 8 = 11
        assertEquals(Real.of(11.0), dot);
    }

    @Test
    void testNegate() {
        Vector<Real> v = new DenseVector<>(Arrays.asList(Real.of(1.0), Real.of(-2.0)), Reals.getInstance());
        Vector<Real> neg = v.negate();

        assertEquals(Real.of(-1.0), neg.get(0));
        assertEquals(Real.of(2.0), neg.get(1));
    }
}
