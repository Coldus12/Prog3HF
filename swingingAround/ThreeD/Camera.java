package swingingAround.ThreeD;

import java.awt.*;

/**
 * A tér Screen-en való megjelenítéséhez használt osztály.
 */
public class Camera {
    /**
     * A kamera poziciója a térben.
     */
    private Vec3 pos;
    /**
     * A képernyő amelyre a kamera kirajzolja azt, amit "lát".
     * <p>
     *     A kamera képernyője tekinthető úgy, mint egy sík, amire
     *     rávetítjük az összes dolgot amit a kamera lát, és utána
     *     ezt a síkot rajzoljuk ki a képernyőre.
     * </p>
     */
    public Screen screen;
    /**
     * A kamera és a képernyő síkjának távolsága, ezt valójában megadja a fov.
     */
    private double distanceToScreen;
    /**
     * Field of View, megadja milyen szögben látjuk a síkot.
     */
    private double fov;

    /**
     * A kamera bázisa.
     */
    private dMatrix base;
    /**
     * A kamera bázisának inverze
     */
    private dMatrix inverseBase;
    /**
     * Azt mondja meg, hogy változott-e a kamera bázisa.
     * <p>
     *     Azért van erre szükség, mert amíg a kamera
     *     bázisa nem változik, addig fölösleges a bázis
     *     inverzét folyamatosan újra számolni, de ha
     *     a bázis változik, akkor az inverzet is újra
     *     kell számolni.
     * </p>
     */
    private boolean hasBaseChanged = true;

    /**
     * Létrehoz egy kamerát egy adott pozicíóban egy adott felbontású képernyővel, és egy adott fov-val.
     * @param width a képernyő szélessége
     * @param height a képernyő magassága
     * @param pos a kamera pozíciója a térben.
     * @param FOV a kamera Field of View-ja, azaz látószöge.
     */
    public Camera(int width, int height, Vec3 pos, double FOV) {
        this.pos = pos;

        screen = new Screen(width,height);
        distanceToScreen = width/(2.0 * Math.tan((FOV/2)));
        fov = FOV;
        base = new dMatrix(3,3);

        base.setValueAt(0,0,1);
        base.setValueAt(0,1,0);
        base.setValueAt(0,2,0);

        base.setValueAt(1,0,0);
        base.setValueAt(1,1,1);
        base.setValueAt(1,2,0);

        base.setValueAt(2,0,0);
        base.setValueAt(2,1,0);
        base.setValueAt(2,2,1);
    }

    //Setters
    //------------------------------------------------------------------------------------------------------------------
    public void setFOV(double FOV) {
        distanceToScreen = screen.getWidth()/(2.0 * Math.tan((FOV/2)));
        fov = FOV;
    }

    public void setPos(Vec3 pos) {
        this.pos = pos;
    }

    public void setBase(dMatrix newBase) {
        if ((newBase.getWidth() == 3) && (newBase.getHeight() == 3)) {
            base = newBase;
            hasBaseChanged = true;
        }
    }

    //Getters
    //------------------------------------------------------------------------------------------------------------------
    public Vec3 getPos() {
        return pos;
    }

    public double getDistanceToScreen() {
        return distanceToScreen;
    }

    public double getFOV() {
        return fov;
    }

    public dMatrix getBase() {
        return base;
    }

    //Get the camera view
    //------------------------------------------------------------------------------------------------------------------
    public Screen getScreen() {
        return screen;
    }

    //Main functions
    //------------------------------------------------------------------------------------------------------------------

    /**
     * Kirajzol a képernyőre egy pontot zöld színnel.
     * @param vec a kirajzolandó pont
     */
    public void render3DPoint(Vec3 vec) {
        render3DPoint(vec,Color.GREEN);
    }

    /**
     * Kiszámolja/Újraszámolja a bázishoz tartozó inverz mátrixot.
     */
    private void calcInverse() {
        if (hasBaseChanged) {

            inverseBase = base;
            inverseBase = inverseBase.multiplyByNumber(distanceToScreen);
            inverseBase = inverseBase.getInverse();

            hasBaseChanged = false;
        }
    }

    /**
     * Kiszámolja, hogy egy háromszögnek mik a "képernyő koordinátái", és azokat adja vissza egy háromszögben.
     * <p>
     *     A képernyőt érdemes egy síkként elképzelni. Egy pont ezen a síkon akkor fog úgy megjelenni, mintha
     *      az a térben helyezkedne el, hogy ha felveszünk egy vektort ami a kamera pozíciójából indul, és
     *      a megjelenítendő pontba vezet, és megnézzük, hogy ez a vektor milyen koordinátákban metszi a síkot,
     *      amit mi képernyőnek nevezünk.
     *
     *     Amikor "képernyő koordinátákat" számolunk első lépés, hogy a normál {1,0,0; 0,1,0; 0,0,1}
     *     bázisról álljunk át a kamera bázisára, majd a kamera bázisában lévő koordinátákat átalakítsuk
     *     képernyő koordinátákra.
     *
     *     Egy pont kamera bázis-beli koordinátáit úgy kapjuk meg, hogy a "normál" bázisban lévő vektort
     *     beszorozzuk a kamera bázisának inverzével. Ezt a háromszög mind a három pontjával megtesszük,
     *     és emellett ezt megteszzük a kamera pozíciójával is. Erre azért van szükség, hogy a háromszög
     *     csúcsait "eltolhassuk" abba a pozícióba ahol a kamera pozíciója olya mint az origó. Ezek után
     *     a kamera pozíciója megfeleltehető a (0,0,0) pontnak a térben. Ismerjük a kamera és a képernyő
     *     síkunk közötti távolságot, ez a distanceToScreen, ami egyben a kamera bázisában a "z tengely"
     *     egységvektorának a hossza is. És (az első bekezdésre hivatkozva) minket az érdekel, hogy a
     *     kamerából induló a megjelenítendő pontba mutató vektort (nevezzük v-nek) milyen skalárral kell
     *     beszorzni ahhoz, hogy ez a vektor pontosan a képernyő síkunk egy pontjára mutasson. Mivel a
     *     háromszög minden csúcsa immáron a kamera bázisában van, ezért ehhez elég megnéznünk a kamera
     *     és a megjelenítendő pont z komponensei közti különbséget. Ez elvileg hosszab/nagyobb, mint a
     *     distanceToScreen, hiszen ha rövidebb lenne, akkor nem tudná metszeni a képernyő síkját. Majd
     *     ez alapján megnézni a kettő arányát (distanceToScreen/v.z), és ezzel beszorozni a v-t (ami
     *     az a vektor ami a kamerából mutat a megjelenítendő pontba).
     *
     *     Az így kapott vektor már a kamera síkunk egy pontjára mutat, de ennek a vektornak az x, és
     *     y komponensei még nem maguk a háromszög egyik csúcsának képernyő koordinátái. Ez azért van,
     *     mert a képernyő (0,0) pontja a bal fölső sarokban van, és az y lefele, az x pedig jobbra mutat.
     *     Így ezeket az x és y koordinátákat is el kell tolni úgy, mintha a képernyő közepe lenne a
     *     (0,0) pontja, és mintha az y fölfelé mutatna. Ezt úgy lehet, ha az x-hez hozzáadjuk a képernyő
     *     szélességének felét, és ha az y-ont egyenlőve teszzük azzal, hogy a képernyő magasságának a fele
     *     minusz az y. Ez az x, és y már a képernyő koordináták.
     * </p>
     * @param tri a háromszög aminek a képernyő koordinátái érdekelnek
     * @return egy háromszög aminek a csúcsai a paraméterként kapott háromszög képernyő koordinátái.
     */
    private Triangle calcProjectedTrianglesCoordinates(Triangle tri) {
        calcInverse();

        Vec3 v1b = tri.v1.multiplyVec3ByMatrix(inverseBase);
        Vec3 v2b = tri.v2.multiplyVec3ByMatrix(inverseBase);
        Vec3 v3b = tri.v3.multiplyVec3ByMatrix(inverseBase);

        Vec3 camPos = getPos().multiplyVec3ByMatrix(inverseBase);

        v1b = new Vec3(v1b.x - camPos.x, v1b.y - camPos.y, v1b.z - camPos.z);
        v2b = new Vec3(v2b.x - camPos.x, v2b.y - camPos.y, v2b.z - camPos.z);
        v3b = new Vec3(v3b.x - camPos.x, v3b.y - camPos.y, v3b.z - camPos.z);

        //Transformation to screen coordinates
        if ((v1b.z > 0) && (v2b.z  > 0) && (v3b.z > 0)) {
            v1b.multiplyByNumber((distanceToScreen)/(v1b.z));
            v2b.multiplyByNumber((distanceToScreen)/(v2b.z));
            v3b.multiplyByNumber((distanceToScreen)/(v3b.z));

            v1b.x += screen.getWidth()/2.0;
            v2b.x += screen.getWidth()/2.0;
            v3b.x += screen.getWidth()/2.0;

            v1b.y = screen.getHeight()/2.0 - v1b.y;
            v2b.y = screen.getHeight()/2.0 - v2b.y;
            v3b.y = screen.getHeight()/2.0 - v3b.y;

            return new Triangle(v1b,v2b,v3b,tri.c);
        } else {
            return null;
        }
    }

    /**
     * Kirajzol a kéeprnyőre egy pontot adott színnel.
     * @param vec a kirajzolandó pont
     * @param c a kirajzolandó pont színe
     */
    public void render3DPoint(Vec3 vec, Color c) {
        calcInverse();

        Vec3 v1b = vec.multiplyVec3ByMatrix(inverseBase);

        Vec3 camPos = getPos().multiplyVec3ByMatrix(inverseBase);

        v1b = new Vec3(v1b.x - camPos.x, v1b.y - camPos.y, v1b.z - camPos.z);

        if (v1b.z > 0) {
            v1b.multiplyByNumber((distanceToScreen)/(v1b.z));
            v1b.x += screen.getWidth()/2.0;
            v1b.y = screen.getHeight()/2.0 - v1b.y;

            try {
                screen.drawPoint((int) v1b.x, (int) v1b.y,3, c);
            } catch (Exception ex) {ex.printStackTrace();}
        }
    }

    /**
     * Kirajzolja egy térbeli háromszög oldalait.
     * @param tri a kirajzolandó háromszög
     */
    public void render3DTriangle(Triangle tri) {
        tri = calcProjectedTrianglesCoordinates(tri);
        screen.drawTriangle(tri);
    }

    /**
     * Kirajzol egy teli térbeli háromszöget
     * @param tri a kirajzolandó háromszög
     */
    public void render3DFilledTriangle(Triangle tri) {
        tri = calcProjectedTrianglesCoordinates(tri);
        screen.fillTriangle(tri);
    }

    /**
     * Kirajzol egy 3D-s szakaszt két pont között adott színnel
     * @param p1 a pont amiből a szakasz indul
     * @param p2 a pont ameddig a szakasz megy
     * @param c a szakasz színe.
     */
    public void render3DLine(Vec3 p1, Vec3 p2, Color c) {
        calcInverse();

        Vec3 v1b = p1.multiplyVec3ByMatrix(inverseBase);
        Vec3 v2b = p2.multiplyVec3ByMatrix(inverseBase);

        Vec3 camPos = getPos().multiplyVec3ByMatrix(inverseBase);

        v1b = new Vec3(v1b.x - camPos.x, v1b.y - camPos.y, v1b.z - camPos.z);
        v2b = new Vec3(v2b.x - camPos.x, v2b.y - camPos.y, v2b.z - camPos.z);

        if ((v1b.z > 0) && (v2b.z  > 0)) {
            v1b.multiplyByNumber((distanceToScreen)/(v1b.z));
            v2b.multiplyByNumber((distanceToScreen)/(v2b.z));

            v1b.x += screen.getWidth()/2.0;
            v2b.x += screen.getWidth()/2.0;

            v1b.y = screen.getHeight()/2.0 - v1b.y;
            v2b.y = screen.getHeight()/2.0 - v2b.y;

            try {
                screen.drawLine((int) v1b.x, (int) v1b.y, (int) v2b.x, (int) v2b.y, c);
            } catch (Exception ex) {ex.printStackTrace();}
        }
    }
}
