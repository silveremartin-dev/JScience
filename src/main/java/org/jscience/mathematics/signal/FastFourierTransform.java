package org.jscience.mathematics.signal;

import org.jscience.mathematics.number.Complex;
import org.jscience.mathematics.number.Real;
import org.jscience.mathematics.vector.DenseVector;
import org.jscience.mathematics.vector.Vector;
import org.jscience.mathematics.number.set.Complexes;

import java.util.ArrayList;
import java.util.List;

/**
 * Implements the Fast Fourier Transform (FFT).
 * <p>
 * Uses the Cooley-Tukey algorithm for efficient computation of the Discrete
 * Fourier Transform (DFT).
 * </p>
 * 
 * @author Silvere Martin-Michiellot
 * @author Gemini AI (Google DeepMind)
 * @since 1.0
 */
public final class FastFourierTransform {

    private FastFourierTransform() {
        // Utility class
    }

    /**
     * Computes the Forward FFT of a complex vector.
     * <p>
     * The input size must be a power of 2.
     * </p>
     * 
     * @param input the input vector of complex numbers
     * @return the transformed vector
     * @throws IllegalArgumentException if input size is not a power of 2
     */
    public static Vector<Complex> transform(Vector<Complex> input) {
        int n = input.dimension();
        if (n == 0)
            return input;
        if ((n & (n - 1)) != 0) {
            throw new IllegalArgumentException("Input size must be a power of 2");
        }

        // Base case
        if (n == 1) {
            return input;
        }

        // Recursive step
        // Split into even and odd
        List<Complex> evenList = new ArrayList<>(n / 2);
        List<Complex> oddList = new ArrayList<>(n / 2);
        for (int i = 0; i < n; i++) {
            if (i % 2 == 0) {
                evenList.add(input.get(i));
            } else {
                oddList.add(input.get(i));
            }
        }

        Vector<Complex> even = new DenseVector<>(evenList, Complexes.getInstance());
        Vector<Complex> odd = new DenseVector<>(oddList, Complexes.getInstance());

        Vector<Complex> evenFFT = transform(even);
        Vector<Complex> oddFFT = transform(odd);

        List<Complex> resultList = new ArrayList<>(n);
        // Initialize list with nulls
        for (int i = 0; i < n; i++)
            resultList.add(null);

        for (int k = 0; k < n / 2; k++) {
            double kth = -2 * Math.PI * k / n;
            Complex w = Complex.of(Math.cos(kth), Math.sin(kth));
            Complex oddPart = w.multiply(oddFFT.get(k));

            resultList.set(k, evenFFT.get(k).add(oddPart));
            resultList.set(k + n / 2, evenFFT.get(k).subtract(oddPart));
        }

        return new DenseVector<>(resultList, Complexes.getInstance());
    }

    /**
     * Computes the Inverse FFT of a complex vector.
     * 
     * @param input the input vector of complex numbers
     * @return the inverse transformed vector
     */
    public static Vector<Complex> inverseTransform(Vector<Complex> input) {
        int n = input.dimension();

        // Conjugate input
        List<Complex> conjugatedInput = new ArrayList<>(n);
        for (int i = 0; i < n; i++) {
            conjugatedInput.add(input.get(i).conjugate());
        }

        // Forward FFT
        Vector<Complex> forward = transform(new DenseVector<>(conjugatedInput, Complexes.getInstance()));

        // Conjugate again and scale by 1/n
        List<Complex> resultList = new ArrayList<>(n);
        Real nReal = Real.of(n);
        for (int i = 0; i < n; i++) {
            resultList.add(forward.get(i).conjugate().divide(Complex.of(nReal, Real.ZERO)));
        }

        return new DenseVector<>(resultList, Complexes.getInstance());
    }
}
