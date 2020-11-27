package swingingAround;

import org.omg.Messaging.SYNC_WITH_TRANSPORT;
import swingingAround.Math.MathHelper;

import java.awt.*;
import java.util.ArrayList;

public class FunctionEntity extends Entity {

    private static int number;

    private String formula;
    private String name;
    private Color color;
    private Mathfunction mf;
    private boolean exists;

    public FunctionEntity(String[] function) {
        number++;
        name = "function" + number;
        exists = true;

        color = Color.GREEN;

        mf = new Mathfunction() {
            @Override
            public float exec3DFunction(float x, float y, float z) {
                String[] strTmp = new String[function.length - 1];
                System.arraycopy(function,1,strTmp,0,function.length-1);

                String tmp = MathHelper.putTogether(strTmp);
                //System.out.println(tmp+" THIS THE TEMP");

                tmp = MathHelper.changeSubstringToVal(tmp,"x",x);
                tmp = MathHelper.changeSubstringToVal(tmp,"y",y);
                tmp = MathHelper.changeSubstringToVal(tmp,"z",z);

                tmp = MathHelper.changeSubstringToVal(tmp,"X",x);
                tmp = MathHelper.changeSubstringToVal(tmp,"Y",y);
                tmp = MathHelper.changeSubstringToVal(tmp,"Z",z);

                //System.out.println(tmp+" THIS THE CHANGED");

                //System.out.println("--------------------------------------------------------------------------------------");
                //MathHelper.printEx(tmp);

                float retVal = 0;
                try {
                    retVal = (float) MathHelper.executeExpression(tmp);
                } catch (Exception ex) {ex.printStackTrace();}

                return retVal;
            }
        };

        //System.exit(0);
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

        formula = "";
        StringBuilder stringBuilder = new StringBuilder(formula);
        stringBuilder.append(function[0]);
        stringBuilder.append("=");
        for (int i = 1; i < function.length-1; i++) {
            stringBuilder.append(function[i]);
            stringBuilder.append(" ");
        }
        stringBuilder.append(function[function.length-1]);

        formula = stringBuilder.toString();
    }

    @Override
    public Mathfunction[] getMathfunctions() {
        Mathfunction[] mfs = new Mathfunction[1];
        mfs[0] = mf;
        return mfs;
    }

    @Override
    public String getFormula() {
        return formula;
    }

    @Override
    public void setFormula(String string) {
        formula = string;
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

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean stillExists() {
        return exists;
    }

    @Override
    public void delete() {
        exists = false;
    }
}
