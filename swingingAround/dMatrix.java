package swingingAround;

public class dMatrix {

    private int nrOfColumns;
    private int nrOfRows;

    private double matrix[];

    public dMatrix(int columns, int rows) {
        nrOfColumns = columns;
        nrOfRows = rows;

        matrix = new double[nrOfColumns * nrOfRows];

        for (int i = 0; i < nrOfColumns; i++) {
            for (int j = 0; j < nrOfRows; j++) {
                matrix[i * nrOfRows + j] = 0.0;
            }
        }
    }

    public double getValueAt(int x, int y) {
        return matrix[x * nrOfRows + y];
    }

    public void setValueAt(int x, int y, double newValue) {
        matrix[x * nrOfRows + y] = newValue;
    }

    public int getWidth() {
        return nrOfColumns;
    }

    public int getHeight() {
        return nrOfRows;
    }

    public dMatrix multiplyByMatrix(dMatrix mat) {
        if (mat.getHeight() != this.getWidth())
            return null;

        dMatrix ret = new dMatrix(this.getWidth(),mat.getHeight());

        for (int i = 0; i < ret.getWidth(); i++) {
            for (int j = 0; j < ret.getHeight(); j++) {
                double dRet = 0;

                for (int n = 0; n < this.getWidth(); n++) {
                    dRet += this.getValueAt(n,j) * mat.getValueAt(i,n);
                }

                ret.setValueAt(i,j,dRet);
            }
        }

        return ret;
    }

    public dMatrix multiplyByNumber(double nr) {
        dMatrix ret = new dMatrix(this.getWidth(),this.getHeight());

        for (int i = 0; i < ret.getWidth(); i++) {
            for (int j = 0; j < ret.getHeight(); j++) {
                ret.setValueAt(i,j, getValueAt(i,j) * nr);
            }
        }

        return ret;
    }

    private double determinantThreeByThree() {
        double det = 0;

        det += getValueAt(0,0) * (getValueAt(1,1) * getValueAt(2,2) - getValueAt(1,2) * getValueAt(2,1));
        det -= getValueAt(1,0) * (getValueAt(0,1) * getValueAt(2,2) - getValueAt(2,1) * getValueAt(0,2));
        det += getValueAt(2,0) * (getValueAt(0,1) * getValueAt(1,2) - getValueAt(1,1) * getValueAt(0,2));

        return det;
    }

    //Making an inverse matrix for a 3x3 matrix
    //This function is using the adjoint matrix method
    //to calculate the inverse of this matrix.
    public dMatrix getInverse() {
        dMatrix ret = null;

        if (getWidth() != getHeight()) {
            System.out.println("This matrix isn't a square matrix therefore its inverse does not exist.");
            return null;
        } else if (getWidth() != 3) {
            System.out.println("This function is designed to only work with 3x3 matrices.");
            return null;
        }

        double det = determinantThreeByThree();
        if (det == 0) {
            System.out.println("Determinant is 0, the inverse of this matrix does not exist.");
        } else {
            //Calc adjoint
            double val[] = new double[9];
            val[0] = (getValueAt(1,1) * getValueAt(2,2) - getValueAt(1,2) * getValueAt(2,1)) * (1/det);
            val[1] = (getValueAt(0,1) * getValueAt(2,2) - getValueAt(0,2) * getValueAt(2,1)) * (1/det) * (-1);
            val[2] = (getValueAt(0,1) * getValueAt(1,2) - getValueAt(1,1) * getValueAt(0,2)) * (1/det);

            val[3] = (getValueAt(1,0) * getValueAt(2,2) - getValueAt(2,0) * getValueAt(1,2)) * (1/det) * (-1);
            val[4] = (getValueAt(0,0) * getValueAt(2,2) - getValueAt(2,0) * getValueAt(0,2)) * (1/det);
            val[5] = (getValueAt(0,0) * getValueAt(1,2) - getValueAt(1,0) * getValueAt(0,2)) * (1/det) * (-1);

            val[6] = (getValueAt(1,0) * getValueAt(2,1) - getValueAt(2,0) * getValueAt(1,1)) * (1/det);
            val[7] = (getValueAt(0,0) * getValueAt(2,1) - getValueAt(2,0) * getValueAt(0,1)) * (1/det) * (-1);
            val[8] = (getValueAt(0,0) * getValueAt(1,1) - getValueAt(1,0) * getValueAt(0,1)) * (1/det);

            ret = new dMatrix(3,3);

            //Transponalt
            ret.setValueAt(0,0,val[0]);
            ret.setValueAt(1,0,val[1]);
            ret.setValueAt(2,0,val[2]);

            ret.setValueAt(0,1,val[3]);
            ret.setValueAt(1,1,val[4]);
            ret.setValueAt(2,1,val[5]);

            ret.setValueAt(0,2,val[6]);
            ret.setValueAt(1,2,val[7]);
            ret.setValueAt(2,2,val[8]);
        }

        return ret;
    }

    public void printMatrix() {
        System.out.println("printing out the matrix");
        for (int i = 0; i < getWidth(); i++) {
            for (int j = 0; j < getHeight(); j++) {
                System.out.print(getValueAt(i,j) + " ");
            }
            System.out.println();
        }
    }
}
