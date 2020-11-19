package swingingAround.Math;

public class TestingMathHelper {

    public static void main(String[] args) {
        //String expression = "1 + ( 3 * 4 * 2 * 10 + 2 - 10 ) * 3";
        //String expression = "( 3 * ( 4 * 2 - 1 ) * 10 + ( 2 - 10 ) ) * 10 - 12";
        //String expression = "( 3 * ( 4 * 2 - 1 ) * 10 + ( 2 - 10 ) ) * 10 - 12 * ( ( PI * E ) - Pi * 10 )";
        //String expression = "( 3 * ( 4 * 2 - 1 ) * 10 + ( 2 - 10 ) ) * 10 - 12 * ( ( 1 * 2 ) - 3 * 10 )";
        //String expression = "10 * ( ( 2 - 10 ) + 2 ) - 10";
        //String expression = "3 * ( 4 * 2 ) * 10 + ( 2 - 10 )";
        //String expression = "1 + 3 * 2 + 2 * ( 3 + 10 ) - 10 ";
        //String expression = "sin ( 12 + 2 * 3 ) * cos ( 3 * 4 ) + sin ( 3 ) + 3 ^ 2";
        //String expression = "3 ^ 2";
        //String expression = "( sin ( PI / 16.0 ) ) ^ 2 + ( cos ( PI / 16.0 ) ) ^ 2";
        //String expression = "cos x * sin x";
        String expression = "3 * e ^ ( 0 - ( ( ( x - 2 ) ^ ( 2 ) ) / ( 2 * 3 ^ ( 2 ) ) + ( ( y - 2 ) ^ ( 2 ) ) / ( 2 * 3 ^ ( 2 ) ) ) )";
        //String expression = "z = x + y + z";
        //Config conf = new Config();
        //Graph graph = new Graph();

        String[] e = expression.split(" ");
        e = MathHelper.changeStringToVal(e,"x",1);
        e = MathHelper.changeStringToVal(e, "e", 2.71);
        e = MathHelper.changeStringToVal(e, "y", 1);

        //conf = graph.execute(e,conf);
        //e = MathHelper.removeFromArray(e,2);
        //e = MathHelper.removeFromArray(e,2);

        try {
            System.out.println(expression);
            //String[] a = MathHelper.executeExpression(e);
            //String[] a = MathHelper.executeExpressionReal(e,3, 9);
            System.out.println("The value of this expression equals to: ");
            System.out.println(MathHelper.executeExpression(e));

            //System.out.println(conf.execMathFunction(1,1,1));
        } catch (Exception ex) {ex.printStackTrace();}
    }

}
