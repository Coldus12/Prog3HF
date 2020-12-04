package swingingAround.Entities;

import swingingAround.Math.MathHelper;

import java.awt.*;

/**
 * Egyszerű térbeli függvényekhez tartozó entitás.
 */
public class FunctionEntity extends Entity {

    /**
     * Hány darab függvény entitás volt amióta a program elindult
     * <p>
     *     Az egyetlen szerepe ennek a változónak az,
     *     hogy biztosítsa azt, hogy minden függvény
     *     entitás különböző, egyedi id-val rendelkezik.
     * </p>
     */
    private static int number;

    /**
     * Maga a függvény.
     */
    private Mathfunction mf;
    /**
     * A szín amivel kirajzolódik majd a függvény.
     */
    private Color color;

    /**
     * Az osztály konstruktora, mely egy String tömb alapján
     * létrehozza a megfelelő Mathfunction-t.
     * <p>
     *     A konstruktor beállítja az entitás id-ját, nevét,
     *     színét, majd létrehozza a hozzá tartozó Mathfunction-t.
     *
     *     A Mathfunction megfelelő működéséhez a MathHelper függvényeit
     *     használja. Jelen esetben a(z) exec3DFunction először is majd
     *     összerakja egy Stringbe mindazt, ami az egyenlőség után állt.
     *     Ezek után ebben a Stringben kicserél minden x,y,z-t a megfelelő
     *     értékre, majd az így kapott Stringet átadja a MathHelpernek,
     *     ami megpróbálja végrehajtani, és ha sikerül, akkor egy double-el
     *     tér vissza.
     * </p>
     * @param function a függvény hozzárendelési szabálya földarabolva.
     */
    public FunctionEntity(String[] function) {
        number++;
        setName("function" + number);
        setId("function"+number);

        color = Color.GREEN;

        mf = new Mathfunction(Mathfunction.Axis.notDefined) {
            @Override
            public float exec3DFunction(float x, float y, float z) {
                String[] strTmp = new String[function.length - 1];
                System.arraycopy(function,1,strTmp,0,function.length-1);

                String tmp = MathHelper.putTogether(strTmp);

                tmp = MathHelper.changeSubstringToVal(tmp,"x",x);
                tmp = MathHelper.changeSubstringToVal(tmp,"y",y);
                tmp = MathHelper.changeSubstringToVal(tmp,"z",z);

                tmp = MathHelper.changeSubstringToVal(tmp,"X",x);
                tmp = MathHelper.changeSubstringToVal(tmp,"Y",y);
                tmp = MathHelper.changeSubstringToVal(tmp,"Z",z);

                float retVal = 0;
                try {
                    retVal = (float) MathHelper.executeExpression(tmp);
                } catch (Exception ex) {
                    System.out.println(tmp);
                    ex.printStackTrace();
                    System.exit(0);
                }

                return retVal;
            }
        };

        mf.setColor(color);

        switch (function[0]) {
            case "y":
            case "Y":
                mf.setCurrentAxis(Mathfunction.Axis.y);
                break;
            case "x":
            case "X":
                mf.setCurrentAxis(Mathfunction.Axis.x);
                break;
            case "z":
            case "Z":
                mf.setCurrentAxis(Mathfunction.Axis.z);
                break;
            default:
                mf.setCurrentAxis(Mathfunction.Axis.notDefined);
                break;
        }

        String formula = "";
        StringBuilder stringBuilder = new StringBuilder(formula);
        stringBuilder.append(function[0]);
        stringBuilder.append("=");
        for (int i = 1; i < function.length-1; i++) {
            stringBuilder.append(function[i]);
            stringBuilder.append(" ");
        }
        stringBuilder.append(function[function.length-1]);

        formula = stringBuilder.toString();
        setFormula(formula);
    }

    /**
     * Visszadja az entitáshoz tartozó Mathfunction-t.
     * @return a függvény Mathfunction-je.
     */
    @Override
    public Mathfunction[] getMathfunctions() {
        Mathfunction[] mfs = new Mathfunction[1];
        mfs[0] = mf;
        return mfs;
    }

    @Override
    public Color getColor() {
        return color;
    }

    @Override
    public void setColor(Color newColor) {
        color = newColor;
        mf.setColor(newColor);
    }

}
