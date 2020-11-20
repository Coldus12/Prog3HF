package swingingAround;

import org.omg.Messaging.SYNC_WITH_TRANSPORT;
import swingingAround.Math.MathHelper;

import java.awt.*;

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
                String[] tmp = new String[function.length - 2];
                System.arraycopy(function,2,tmp,0,function.length-2);


                tmp = MathHelper.changeStringToVal(tmp,"x",x);
                tmp = MathHelper.changeStringToVal(tmp,"y",y);
                tmp = MathHelper.changeStringToVal(tmp,"z",z);

                tmp = MathHelper.changeStringToVal(tmp,"X",x);
                tmp = MathHelper.changeStringToVal(tmp,"Y",y);
                tmp = MathHelper.changeStringToVal(tmp,"Z",z);

                //System.out.println("--------------------------------------------------------------------------------------");
                //MathHelper.printEx(tmp);

                float retVal = 0;
                try {
                    retVal = (float) MathHelper.executeExpression(tmp);
                } catch (Exception ex) {ex.printStackTrace();}

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

        formula = "";
        StringBuilder stringBuilder = new StringBuilder(formula);
        for (int i = 0; i < function.length-1; i++) {
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
