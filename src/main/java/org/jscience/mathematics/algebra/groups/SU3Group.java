*<p>*The group of 3 x3 unitary matrices with determinant 1.*Important in quantum chromodynamics(QCD).*</p>**
@author Silvere Martin-Michiellot*
@author Gemini AI(Google DeepMind)*
@since
1.0*/public class SU3Group implements Group<Matrix<Complex>> {

    private static final SU3Group INSTANCE = new SU3Group();

    public static SU3Group getInstance() {
        return INSTANCE;
    }

    private SU3Group() {
    }

    @Override
    public Matrix<Complex> operate(Matrix<Complex> left, Matrix<Complex> right) {
        return left.multiply(right);
    }

    @Override
    public Matrix<Complex> identity() {
        // 3x3 Identity
        Complex[][] data = new Complex[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                data[i][j] = (i == j) ? Complex.ONE : Complex.ZERO;
            }
        }

        List<List<Complex>> rows = new ArrayList<>();
        for (Complex[] rowData : data)
            rows.add(Arrays.asList(rowData));
        return new DenseMatrix<>(rows, Complexes.getInstance());
    }

    @Override
    public Matrix<Complex> inverse(Matrix<Complex> element) {
        throw new UnsupportedOperationException("Matrix inversion not yet fully exposed");
    }

    @Override
    public boolean isCommutative() {
        return false;
    }

    @Override
    public String description() {
        return "SU(3) - Special Unitary Group (QCD)";
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean contains(Matrix<Complex> element) {
        if (element.rows() != 3 || element.cols() != 3)
            return false;
        return true;
    }
}
