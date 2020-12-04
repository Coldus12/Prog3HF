package swingingAround.Cmd;

import swingingAround.Entities.Entity;
import swingingAround.Entities.LineEntity;
import swingingAround.Entities.Mathfunction;
import swingingAround.Entities.PointEntity;
import swingingAround.ThreeD.Vec3;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Konfigurációs osztály; minden fontosabb, változó információ itt van eltárolva.
 * <p>
 *     Ebben az osztályban kerül tárolásra minden a program futását befolyásoló,
 *     változó adat.
 *
 *     Az ebben az osztályban tárolt információ kerül(het) mentésre, vagy esetleg
 *     betöltésre fájlból. Ennek az osztálynak az attribútumain változtatnak a
 *     parancsok is.
 * </p>
 */
public class Config {
    /**
     * Azt határozza meg, hogy az entitásokat kirajzolja-e a program.
     */
    private boolean isGraphingEnabled = true;
    /**
     * Ha igaz, akkor a program teli háromszögeket rajzol ki, egyébként csak a háromszög oldalai kerülnek megjelenítésre.
     */
    private boolean graphingTrianglesFilled = false;

    /**
     * Hány háromszöget jelenít meg a program az X tengely mentén.
     */
    private int numberOfTrianglesX = 50;
    /**
     * Hány háromszöget jelenít meg a program a Z tengely mentén.
     */
    private int numberOfTrianglesZ = 50;
    /**
     * Hány háromszöget jelenít meg a program az Y tengely mentén.
     */
    private int numberOfTrianglesY = 50;

    /**
     * A kamera pozíciója a térben.
     */
    private Vec3 camPos = new Vec3(0,0,0);

    /**
     * Ez határozza meg, hogy a négyzetek oldalai milyen hoszzúak.
     * (azok a négyzetek, melyek a síkokat/felületeket rajzolnak ki, és melyek háromszögekre vannak bontva.)
     */
    private double stepSize = 1;
    /**
     * Fájlból nem régen lett-e betöltve a konfiguráció.
     */
    private boolean freshlyLoadedFromFile = false;

    /**
     * Az entitások maximum száma. Elméletben lehetne több, de az a program sebességére rossz hatással lenne.
     */
    private final int maxNumberOfEntities = 10;

    /**
     * Entitásokból álló heterogén kollekció.
     */
    private ArrayList<Entity> entities;
    /**
     * Vonalakból álló lista.
     * <p>
     *     Azért szükséges egy külön lista a vonalak számára, mert a sima
     *     entitásokat a függvényeik alapján kerülnek megjelenítésre. Ezzel
     *     szemben az egyeneseknek a térben nincs függvényük,
     *     egyenletük/egyenletrendszerük van. És hogy ne legyen szükség típus
     *     vizsgálatra, ezért a vonalak külön listában is szerepelnek.
     * </p>
     */
    private ArrayList<LineEntity> lines;
    /**
     * Pontokból álló lista.
     * <p>
     *     Azért szükséges egy külön lista a pontok számára, mert a sima
     *     entitásokat a függvényeik alapján kerülnek megjelenítésre. Ezzel
     *     szemben az pontoknak a térben nincs függvényük, csak koordinátájuk van.
     *     És hogy ne legyen szükség típus vizsgálatra, ezért a pontok
     *     külön listában is szerepelnek.
     * </p>
     */
    private ArrayList<PointEntity> points;

    /**
     * Azt mutatja meg, hogy változott-e a konfigurációban bárminek az értéke.
     */
    private boolean hasConfigChanged = true;

    public Config() {
        entities = new ArrayList<Entity>();
        lines = new ArrayList<LineEntity>();
        points = new ArrayList<PointEntity>();
    }

    public Config(Config conf) {
        this.isGraphingEnabled = conf.isGraphingEnabled();
        this.graphingTrianglesFilled = conf.graphingTrianglesFilled;
        this.numberOfTrianglesX = conf.numberOfTrianglesX;
        this.numberOfTrianglesZ = conf.numberOfTrianglesZ;
        this.numberOfTrianglesY = conf.numberOfTrianglesY;

        this.entities = conf.getEntities();
        this.lines = conf.getLines();
        this.points = conf.getPoints();
        this.stepSize = conf.getStepSize();
        this.camPos = conf.getCamPos();
        this.freshlyLoadedFromFile = conf.freshlyLoadedFromFile;
    }

    //Getters
    //------------------------------------------------------------------------------------------------------------------
    public boolean isGraphingEnabled() {
        return isGraphingEnabled;
    }

    public boolean getGraphingTrianglesFilled() {
        return graphingTrianglesFilled;
    }

    public int getNumberOfTrianglesX() {
        return numberOfTrianglesX;
    }

    public int getNumberOfTrianglesZ() {
        return numberOfTrianglesZ;
    }

    public int getNumberOfTrianglesY() { return  numberOfTrianglesY; }

    public ArrayList<Entity> getEntities() { return entities; }

    public ArrayList<LineEntity> getLines() { return lines; }

    public ArrayList<PointEntity> getPoints() {
        return points;
    }

    public Vec3 getCamPos() { return camPos; }

    public double getStepSize() { return stepSize; }

    public boolean isConfigFreshlyLoaded() { return freshlyLoadedFromFile; }

    public boolean hasConfigChanged() { return hasConfigChanged; }

    //Setters
    //------------------------------------------------------------------------------------------------------------------
    public void setGraphingEnabled(boolean graphingEnabled) {
        isGraphingEnabled = graphingEnabled;
        hasConfigChanged = true;
    }

    public void setGraphingTrianglesFilled(boolean graphingTrianglesFilled) {
        this.graphingTrianglesFilled = graphingTrianglesFilled;
        hasConfigChanged = true;
    }

    public void setNumberOfTrianglesX(int numberOfTrianglesX) {
        this.numberOfTrianglesX = numberOfTrianglesX;
        hasConfigChanged = true;
    }

    public void setNumberOfTrianglesZ(int numberOfTrianglesZ) {
        this.numberOfTrianglesZ = numberOfTrianglesZ;
        hasConfigChanged = true;
    }

    public void setNumberOfTrianglesY(int numberOfTrianglesY) {
        this.numberOfTrianglesY = numberOfTrianglesY;
        hasConfigChanged = true;
    }

    public void setCamPos(Vec3 pos) {
        camPos = pos;
    }

    public void setStepSize(double newStepSize) {
        stepSize = newStepSize;
        hasConfigChanged = true;
    }

    public void setFreshlyLoadedFromFile(boolean freshlyLoaded) {
        freshlyLoadedFromFile = freshlyLoaded;
        hasConfigChanged = true;
    }

    public void setConfigChangedToTrue() {
        hasConfigChanged = true;
    }

    //Others
    //------------------------------------------------------------------------------------------------------------------

    /**
     * Hozzáad egy új entitást az entitások ArrayList-jéhez.
     * @param entity az új entitás
     */
    public void addEntity(Entity entity) {
        if (entities.size() < maxNumberOfEntities)
            entities.add(entity);

        hasConfigChanged = true;
    }

    /**
     * Egy entitás Id-ja alapján kitörli az adott entitást
     * <p>
     *     A törlés azért Id, és nem név alapú, mert az entitás nevét lehet változtatni,
     *     és ezért név alapján az entitások lista tartalmát nem lehet frissíteni, hiszen ha
     *     a név frissül, akkor nem tudjuk melyik entitás neve frissült. Ezért az entitások
     *     rendelkeznek Id-val is, mely semmilyen körülmények között nem változhat meg.
     * </p>
     * @param entityId a törlendő entitás Id-ja
     */
    public void removeEntity(String entityId) {
        lines.removeIf(line -> (line.getId().equals(entityId)));
        points.removeIf(point -> (point.getId().equals(entityId)));
        entities.removeIf(n -> (n.getId().equals(entityId)));

        hasConfigChanged = true;
    }

    /**
     * Összegyűjti egy listába az összes tárolt entitásnak az össze függvényét
     * egy helyre, hogy azokat közel egyszerre lehessen kirajzolni.
     * @return az entitások függvényeiből álló lista
     */
    public ArrayList<Mathfunction> getMathfunctions() {
        ArrayList<Mathfunction> mfs = new ArrayList<Mathfunction>();
        for (Entity entity : entities) {
            Collections.addAll(mfs, entity.getMathfunctions());
        }

        hasConfigChanged = false;
        return mfs;
    }

    /**
     * Frissíti az entitásokat
     * <p>
     *     A frissítés azt jelenti jelen esetben, hogy törli a listákból
     *     azokat, melyek már "nem léteznek", az az azokat, amelyeknek
     *     az exists booleanja false.
     * </p>
     */
    public void updateEntity() {
        for (int i = 0; i < entities.size(); i++) {
            if (!entities.get(i).stillExists())
                removeEntity(entities.get(i).getId());
        }
    }

    /**
     * Hozzáad egy új LineEntity-t az entitások és a line-ok listáihoz.
     * @param line az új LineEntity
     */
    public void addLine(LineEntity line) {
        if (entities.size() < maxNumberOfEntities) {
            lines.add(line);
            entities.add(line);

            hasConfigChanged = true;
        }
    }

    /**
     * Hozzáad egy új PointEntity-t az entitások és a point-ok listáihoz.
     * @param point az új PointEntity
     */
    public void addPoint(PointEntity point) {
        if (entities.size() < maxNumberOfEntities) {
            points.add(point);
            entities.add(point);

            hasConfigChanged = true;
        }
    }

}
