package swingingAround;

/**
 * A program Main osztálya.
 */
public class Main {

    /**
     * A main függvény.
     * @param args a program nem törődik ezekkel az argumentumokkal.
     */
    public static void main(String[] args) {
        System.setProperty("sun.java2d.opengl", "true");
        System.setProperty("sun.java2d.d3d", "true");

        GeoFrame gFrame = new GeoFrame();
    }
}
