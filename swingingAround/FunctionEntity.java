package swingingAround;

import swingingAround.Math.MathHelper;

import java.awt.*;

public class FunctionEntity extends Entity {

    private static int number;

    private Mathfunction mf;
    private Color color;

    public FunctionEntity(String[] function) {
        number++;
        setName("function" + number);
        setId("function"+number);

        color = Color.GREEN;

        mf = new Mathfunction() {
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
