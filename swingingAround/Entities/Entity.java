package swingingAround.Entities;

import java.awt.*;

/**
 * Absztrakt entitás osztály.
 * <p>
 *     Minden amit a program képes megjeleníteni az egy entitás.
 *     Ez az osztály tartalmazza az összes tulajdonságot, mellyel
 *     mindegyik entitás rendelkezik.
 * </p>
 */
public abstract class Entity {

    /**
     * Az adott entitás képlete/parancsa.
     */
    private String formula;
    /**
     * Az entitás színe.
     */
    private Color color;
    /**
     * Az entitás neve, ami alapján más entitások hivatkozni tudnak rá.
     */
    private String name;
    /**
     * Id, egyedi azonosító, melyet a felhasználó nem tud megváltoztatni,
     * és mely alapján az adott entitás biztos megtalálható.
     */
    private String id;
    /**
     * Létezik-e még az entitás, meghatározza ki kell-e venni a listá(k)ból, vagy meg kell-e még jeleníteni.
     */
    private boolean exists = true;

    /**
     * Visszaadja az(oka)t a függvény(eke)t, mely(ek)kel meglehet jeleníteni az adott entitást.
     * <p>
     *     Nem minden entitás olyan, amit fügvénnyel meg lehet jeleníteni,
     *     például egy egyenesnek nincs függvénye 3D-ben. Ebben az esetben
     *     ez a függvény null-lal tér vissza.
     * </p>
     * @return azok a térbeli függvények, melyekkel meglehet jeleníteni az adott entitást, már ha vannak ilyenek.
     */
    public abstract Mathfunction[] getMathfunctions();

    public String getFormula() {
        return formula;
    }

    public void setFormula(String string) {
        formula = string;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color newColor) {
        color = newColor;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(String newId) {
        id = newId;
    }

    public String getId() {
        return id;
    }

    public boolean stillExists() {
        return exists;
    }

    public void delete() {
        exists = false;
    }
}
