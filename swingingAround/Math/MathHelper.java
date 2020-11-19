package swingingAround.Math;

import java.util.ArrayList;

public class MathHelper {
    private static final char startSymbol = '(';
    private static final char endSymbol = ')';

    private static String[] executeMultiplicationsAndDivisions(String[] expression, int from, int till) throws Exception {
        String[] temp;

        for (int i = from; i < till; i++) {
            if (expression[i].equals("*")) {
                if (i-1 < 0)
                    throw new Exception("An expression cannot start with an 'action'!");
                else if (i+1 > till)
                    throw new Exception("An expression cannot end with an 'action'!");

                float val = Float.parseFloat(expression[i-1]) * Float.parseFloat(expression[i+1]);

                temp = expression.clone();
                expression = new String[temp.length - 2];

                System.arraycopy(temp,0, expression, 0, i - 1);
                expression[i-1] = "" + val;
                System.arraycopy(temp,i+2,expression,i,temp.length-i-2);
                till -= 2;

                if (i < till)
                    if (expression[i].equals("*") || expression[i].equals("/")) i--;

            } else if (expression[i].equals("/")) {
                if (i-1 < 0)
                    throw new Exception("An expression cannot start with an 'action'!");
                else if (i+1 > till)
                    throw new Exception("An expression cannot end with an 'action'!");

                float val = Float.parseFloat(expression[i-1]) / Float.parseFloat(expression[i+1]);

                temp = expression.clone();
                expression = new String[temp.length - 2];

                System.arraycopy(temp,0, expression, 0, i - 1);
                expression[i-1] = "" + val;
                System.arraycopy(temp,i+2,expression,i,temp.length-i-2);
                till -= 2;

                if (i < till)
                    if (expression[i].equals("*") || expression[i].equals("/")) i--;
            }
        }

        return expression;
    }

    private static String[] executeAdditionsAndSubstractions(String[] expression, int from, int till) throws Exception {
        String[] temp;

        for (int i = from; i < till; i++) {
            if (expression[i].equals("+")) {
                if (i-1 < 0)
                    throw new Exception("An expression cannot start with an 'action'!");
                else if (i+1 > till)
                    throw new Exception("An expression cannot end with an 'action'!");

                float val = Float.parseFloat(expression[i-1]) + Float.parseFloat(expression[i+1]);

                temp = expression.clone();
                expression = new String[temp.length - 2];

                System.arraycopy(temp,0, expression, 0, i - 1);
                expression[i-1] = "" + val;
                System.arraycopy(temp,i+2,expression,i,temp.length-i-2);
                till -= 2;

                if (i < till)
                    if (expression[i].equals("+") || expression[i].equals("-")) i--;

            } else if (expression[i].equals("-")) {
                if (i-1 < 0)
                    throw new Exception("An expression cannot start with an 'action'!");
                else if (i+1 > till)
                    throw new Exception("An expression cannot end with an 'action'!");

                float val = Float.parseFloat(expression[i-1]) - Float.parseFloat(expression[i+1]);

                temp = expression.clone();
                expression = new String[temp.length - 2];

                System.arraycopy(temp,0, expression, 0, i - 1);
                expression[i-1] = "" + val;
                System.arraycopy(temp,i+2,expression,i,temp.length-i-2);
                till -= 2;

                if (i < till)
                    if (expression[i].equals("+") || expression[i].equals("-")) i--;
            }
        }
        return expression;
    }

    private static String[] removeFromArray(String[] expression, int i) {
        String[] tmp = expression.clone();
        expression = new String[tmp.length-1];

        System.arraycopy(tmp,0,expression,0,i);
        System.arraycopy(tmp,i+1,expression,i, tmp.length - (i+1));

        return expression;
    }

    private static String[] executeParentheses(String[] expression, int from, int till) throws Exception {

        ArrayList<Integer> parenthesesList = new ArrayList<Integer>();
        for (int i = from; i < till; i++) {
            if (expression[i].equals(String.valueOf(startSymbol))) {
                parenthesesList.add(i);
            }

            if (expression[i].equals(String.valueOf(endSymbol))) {
                expression = removeFromArray(expression, parenthesesList.get(parenthesesList.size()-1));
                expression = removeFromArray(expression, i-1);

                //printEx(expression);

                expression = executeExpressionReal(expression, parenthesesList.get((parenthesesList.size()-1)),i-1);
                //printEx(expression);
                parenthesesList.remove(parenthesesList.size()-1);
                i = from;
            }

            till = Math.min(expression.length,till);
        }

        //if (parenthesesList.size() == 0) {
        expression = executeExpressionReal(expression, 0, expression.length);
        //}

        return expression;
    }

    private static String[] changeCharsToValue(String[] expression) {

        for (int i = 0; i < expression.length; i++) {
            if (expression[i].equals("E") || expression[i].equals("e"))
                expression[i] = "" + Math.E;

            if (expression[i].equals("PI") || expression[i].equals("Pi") || expression[i].equals("pi"))
                expression[i] = "" + Math.PI;
        }

        return expression;
    }

    private static String[] executeFunctions(String[] expression, int from, int till) {

        for (int i = from; i < till; i++) {
            if (expression[i].equals("sin")) {

                expression[i] = "" + Math.sin(Double.parseDouble(expression[i+1]));
                expression = removeFromArray(expression, i+1);
                till -= 1;

            } else if (expression[i].equals("cos")) {

                expression[i] = "" + Math.cos(Double.parseDouble(expression[i+1]));
                expression = removeFromArray(expression, i+1);
                till -= 1;

            }
        }

        return expression;
    }

    private static String[] executeExponentiation(String[] expression, int from, int till) {

        String[] temp;

        for (int i = from; i < till; i++) {
            if (expression[i].equals("^")) {
                double val = Math.pow(Double.parseDouble(expression[i-1]),Double.parseDouble(expression[i+1]));

                temp = expression.clone();
                expression = new String[temp.length - 2];

                System.arraycopy(temp,0, expression, 0, i - 1);
                expression[i-1] = "" + val;
                System.arraycopy(temp,i+2,expression,i,temp.length-i-2);

                till -= 2;
            }

            if (i < till)
                if (expression[i].equals("^"))  i--;
        }

        return expression;
    }

    private static String[] executeExpressionReal(String[] expression, int from, int till) throws Exception {
        if ((from >= expression.length) || (till > expression.length) || (from > till))
            throw new ArrayIndexOutOfBoundsException("Something must've gone terribly wrong for this to happen.");

        int powernr = 0;
        int multdivnr = 0;
        int funcnr = 0;
        for (int j = from; j < till; j++) {
            if (expression[j].equals("*") || expression[j].equals("/"))
                multdivnr++;

            if (expression[j].equals("sin") || expression[j].equals("cos"))
                funcnr++;

            if (expression[j].equals("^"))
                powernr++;
        }

        //Do the exponentiation
        expression = executeExponentiation(expression, from, till);
        till -= 2*powernr;

        //Do the functions
        expression = executeFunctions(expression, from, till);
        till -= funcnr;

        //Does all the multiplying and dividing
        expression = executeMultiplicationsAndDivisions(expression, from, till);
        till -= 2*multdivnr;

        //Összeadások végrehajtása:
        expression = executeAdditionsAndSubstractions(expression, from, till);

        return expression;
    }

    public static void printEx(String[] exp) {
        for (String s : exp) System.out.print(s + " ");
        System.out.println();
    }

    public static String[] changeStringToVal(String[] expression, String str, double val) {
        for (int i = 0; i < expression.length; i++) {
            if (expression[i].equals(str))
                expression[i] = "" + val;
        }

        return expression;
    }

    public static double executeExpression(String[] expression) throws Exception {
        expression = changeCharsToValue(expression);
        expression = executeParentheses(expression, 0, expression.length);

        if (expression.length == 1) {
            return Double.parseDouble(expression[0]);
        } else {
            System.out.println("------------------------------------------------------------------------------------------------------------------------");
            printEx(expression);
            System.out.println("------------------------------------------------------------------------------------------------------------------------");
            throw new Exception("Something went wrong it seems, better fix it ya dumass");
        }
    }

}
