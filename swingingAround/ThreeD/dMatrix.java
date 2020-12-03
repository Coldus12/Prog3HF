package swingingAround.ThreeD;

/**
 * Valós szám (double) mátrix.
 */
public class dMatrix {

    /**
     * Hány oszlopa van a mátrixnak
     */
    private int nrOfColumns;
    /**
     * Hány sora van a mátrixnak
     */
    private int nrOfRows;

    /**
     * Valós szám tömb, ami maga a mátrix valójában.
     */
    private double matrix[];

    /**
     * A mátrix konstruktora, mely létrehoz egy nxm-es mátrixot.
     * @param columns mátrix oszlopainak száma
     * @param rows mátrix sorainak száma
     */
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

    /**
     * Visszaad egy értéket a mátrix egy adott helyéről
     * @param x melyik oszlop
     * @param y melyik sor
     * @return az érték az adott sor és oszlop metszetében.
     */
    public double getValueAt(int x, int y) {
        if (x * nrOfRows + y < matrix.length)
            return matrix[x * nrOfRows + y];
        return 0;
    }

    /**
     * Beállít egy értéket a mátrix egy adott sorának és oszlopának metszetében.
     * @param x melyik oszlop
     * @param y melyik sor
     * @param newValue az új érték
     */
    public void setValueAt(int x, int y, double newValue) {
        matrix[x * nrOfRows + y] = newValue;
    }

    /**
     * Milyen széles a mátrix, azaz hány oszlopa van.
     * @return mátrix oszlopainak száma.
     */
    public int getWidth() {
        return nrOfColumns;
    }

    /**
     * Milyen magas a mátrix, azaz hány sora van.
     * @return mátrix sorainak száma.
     */
    public int getHeight() {
        return nrOfRows;
    }

    /**
     * Beszorozza ezt a mátrixot egy másik mátrixxal.
     * @param mat a másik mátrix, amivel ez be van szorozva
     * @return a szorzás eredeménye
     */
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

    /**
     * Létrehoz egy új mátrixot, melynek elemei ennek a mátrixnak az elemei beszorozva egy adott számmal.
     * @param nr a szám
     * @return az új mátrix
     */
    public dMatrix multiplyByNumber(double nr) {
        dMatrix ret = new dMatrix(this.getWidth(),this.getHeight());

        for (int i = 0; i < ret.getWidth(); i++) {
            for (int j = 0; j < ret.getHeight(); j++) {
                ret.setValueAt(i,j, getValueAt(i,j) * nr);
            }
        }

        return ret;
    }

    /**
     * Visszaadja a mátrix determinánsát, feltéve hogy a mátrix 3x3-as.
     * @return mátrix determinánsa
     */
    private double determinantThreeByThree() {
        double det = 0;

        if ((getHeight() == 3) && (getWidth() == 3)) {
            det += getValueAt(0, 0) * (getValueAt(1, 1) * getValueAt(2, 2) - getValueAt(1, 2) * getValueAt(2, 1));
            det -= getValueAt(1, 0) * (getValueAt(0, 1) * getValueAt(2, 2) - getValueAt(2, 1) * getValueAt(0, 2));
            det += getValueAt(2, 0) * (getValueAt(0, 1) * getValueAt(1, 2) - getValueAt(1, 1) * getValueAt(0, 2));
        }

        return det;
    }

    /**
     * Ha a mátrix 3x3-as, akkor elkészíti hozzá az inverzét.
     * @return a mátrix inverze.
     */
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

    /**
     * Kiírja a mátrixot.
     */
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
