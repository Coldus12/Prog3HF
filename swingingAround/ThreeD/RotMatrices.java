package swingingAround.ThreeD;

/**
 * Térbeli forgás mátrixok
 */
public class RotMatrices {

    private static double degree = Math.PI/180;

    /**
     * Visszaadja azt a mátrixot amivel az x tengely mentén lehet forgatni "degrees" fokkal.
     * @param degrees hány fokkal legyen elforgatva az x tengely mentén
     * @return az a forgató mátrix, ami "degrees" fokkal forgat az x tengely körül a vele való szorzás folytamán
     */
    public static dMatrix rotateXByDegree(double degrees) {
        dMatrix rotX = new dMatrix(3,3);

        rotX.setValueAt(0,0,1);
        rotX.setValueAt(0,1,0);
        rotX.setValueAt(0,2,0);

        rotX.setValueAt(1,0,0);
        rotX.setValueAt(1,1, Math.cos(degrees * degree));
        rotX.setValueAt(1,2,-Math.sin(degrees * degree));

        rotX.setValueAt(2,0,0);
        rotX.setValueAt(2,1,Math.sin(degrees * degree));
        rotX.setValueAt(2,2,Math.cos(degrees * degree));

        return rotX;
    }

    /**
     * Visszaadja azt a mátrixot amivel az y tengely mentén lehet forgatni "degrees" fokkal.
     * @param degrees hány fokkal legyen elforgatva az y tengely mentén
     * @return az a forgató mátrix, ami "degrees" fokkal forgat az y tengely körül a vele való szorzás folytamán
     */
    public static dMatrix rotateYByDegree(double degrees) {
        dMatrix rotY = new dMatrix(3,3);

        rotY.setValueAt(0,0,Math.cos(degrees * degree));
        rotY.setValueAt(0,1,0);
        rotY.setValueAt(0,2,Math.sin(degrees * degree));

        rotY.setValueAt(1,0,0);
        rotY.setValueAt(1,1,1);
        rotY.setValueAt(1,2,0);

        rotY.setValueAt(2,0,-Math.sin(degrees * degree));
        rotY.setValueAt(2,1,0);
        rotY.setValueAt(2,2,Math.cos(degrees * degree));

        return rotY;
    }

    /**
     * Visszaadja azt a mátrixot amivel az z tengely mentén lehet forgatni "degrees" fokkal.
     * @param degrees hány fokkal legyen elforgatva az z tengely mentén
     * @return az a forgató mátrix, ami "degrees" fokkal forgat az z tengely körül a vele való szorzás folytamán
     */
    public static dMatrix rotateZByDegree(double degrees) {
        dMatrix rotZ = new dMatrix(3,3);

        rotZ.setValueAt(0,0,Math.cos(degrees * degree));
        rotZ.setValueAt(0,1,-Math.sin(degrees * degree));
        rotZ.setValueAt(0,2,0);

        rotZ.setValueAt(1,0,Math.sin(degrees * degree));
        rotZ.setValueAt(1,1,Math.cos(degrees * degree));
        rotZ.setValueAt(1,2,0);

        rotZ.setValueAt(2,0,0);
        rotZ.setValueAt(2,1,0);
        rotZ.setValueAt(2,2,1);

        return rotZ;
    }

}
