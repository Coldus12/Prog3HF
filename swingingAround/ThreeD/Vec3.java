package swingingAround.ThreeD;

/**
 * Háromdimenziós vektor.
 */
public class Vec3 {

    /**
     * A vektor komponensei
     */
    public double x, y, z;

    /**
     * A vektor konstruktora
     * @param x x komponense a vektornak
     * @param y y komponense a vektornak
     * @param z z komponense a vektornak
     */
    public Vec3(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * Hozzáad egy vektort ehhez a vektorhoz komponensenként.
     * @param vec a vektor amit hozzáadunk ehhez a vektorhoz.
     */
    public void addVec3ToThisVec3(Vec3 vec) {
        x += vec.x;
        y += vec.y;
        z += vec.z;
    }

    /**
     * Beszorozza ezt a vektor egy mátrixxal.
     * <p>
     *     A szorzás csak akkor megy végbe, ha a mátrix
     *     egy 3x3-as mátrix. Bár elég lehetne az, ha
     *     a mátrixnak a "magassága" 3, akkor viszont szükség
     *     lenne egy n méretű vektor osztályra, amire
     *     ennél a programnál nincs semmi szükség.
     * </p>
     * @param mat a mátrix amivel beszorozzuk a vektort
     * @return a szorzás során keletkező Vec3
     */
    public Vec3 multiplyVec3ByMatrix(dMatrix mat) {
        if ((mat.getHeight() != 3) || (mat.getWidth() != 3)) {
            System.out.println("Either the number of rows in the matrix is incorrect for Vec3 multiplication, " +
                               "\nor the number of columns is incorrect, and then the result vector wouldn't be a Vec3.");


            return null;
        }

        double newX, newY, newZ;
        newX = x * mat.getValueAt(0,0) + y * mat.getValueAt(0,1) + z * mat.getValueAt(0,2);
        newY = x * mat.getValueAt(1,0) + y * mat.getValueAt(1,1) + z * mat.getValueAt(1,2);
        newZ = x * mat.getValueAt(2,0) + y * mat.getValueAt(2,1) + z * mat.getValueAt(2,2);

        return new Vec3(newX,newY,newZ);
    }

    /**
     * Két Vec3 skalár szorzatát adja vissza, ahol az egyik vektor ez a vektor.
     * @param v a másik vektor
     * @return skalárszorzat eredménye
     */
    public double dotProduct(Vec3 v) {
        double product = 0;
        product += v.x * x + v.y * y + v.z * z;
        return product;
    }

    /**
     * A vektor összes komponensét beszorozza egy számmal
     * @param nr a szám amivel minden komponens be lesz szorozva
     */
    public void multiplyByNumber(double nr) {
        x *= nr;
        y *= nr;
        z *= nr;
    }

    /**
     * Kiírja ennek a vektornak a komponenseit.
     */
    public void printVec() {
        System.out.println(x + " " + y + " " + z);
    }

    /**
     * Megvizsgálja, hogy a paraméterként kapott vektor
     * komponensei egyenlők-e ennek a vektornak a komponenseivel.
     * @param vec a vizsgálandó vektor
     * @return egyenlőek-e a komponensek
     */
    public boolean equals(Vec3 vec) {
        return ((this.x == vec.x) && (this.y == vec.y) && (this.z == vec.z));
    }

    /**
     * Fölülírt toString, hogy a vektor komponenseit írja ki.
     * @return a vektor komponensei String-ben szóközzel elválasztva.
     */
    public String toString() {
        return x + " " + y + " " + z;
    }

    /**
     * Összeadja a két paraméterként kapott vektort
     * @param v1 egyik vektor
     * @param v2 másik vektor
     * @return az összeadás eredményeként kapott vektor
     */
    public static Vec3 vecPlusVec(Vec3 v1, Vec3 v2) {
        return new Vec3(v1.x + v2.x,v1.y + v2.y, v1.z + v2.z);
    }

    /**
     * Kivonja a két paraméterként kapott vektor komponenseit egymásból
     * @param from a vektor aminek a komponenseiból kivonjuk a másik vektor komponenseit
     * @param what a másik vektor
     * @return a kivonás eredményeként kapott vektor
     */
    public static Vec3 vecMinusVec(Vec3 from, Vec3 what) {
        return new Vec3(from.x - what.x, from.y - what.y, from.z - what.z);
    }

    /**
     * A vektor x komponensét növeli delta mennyiséggel.
     * @param delta a mennyiség amivel az x komponens nő
     */
    public void moveXbyDelta(double delta) {
        x += delta;
    }

    /**
     * A vektor y komponensét növeli delta mennyiséggel.
     * @param delta a mennyiség amivel az y komponens nő
     */
    public void moveYByDelta(double delta) {
        y += delta;
    }

    /**
     * A vektor z komponensét növeli delta mennyiséggel.
     * @param delta a mennyiség amivel az z komponens nő
     */
    public void moveZByDelta(double delta) {
        z += delta;
    }
}
