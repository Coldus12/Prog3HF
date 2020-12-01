package swingingAround.Math;

import java.util.ArrayList;

public class MathHelper {

    public static double executeExpression(String expression) throws Exception {
        if (expression.startsWith("-"))
            expression = "0" + expression;

        expression = expression.replace("(-","(0-");

        ArrayList<String> workable = breakUpExpression(expression);
        workable = changeSpecialCharsToValue(workable);
        workable = executeParentheses(workable,0,workable.size());

        if (workable.size() == 1) {
            return Double.parseDouble(workable.get(0));
        } else {
            System.out.println("------------------------------------------------------------------------------------------------------------------------");
            printEx(workable);
            System.out.println("------------------------------------------------------------------------------------------------------------------------");
            throw new Exception("Something went wrong it seems, better fix it ya dumass");
        }
    }

    //Breaking up the expression into an arraylist of smaller strings
    //------------------------------------------------------------------------------------------------------------------
    public static ArrayList<String> breakUpExpression(String expression) {

        ArrayList<String> brokenUpExpression = new ArrayList<String>();
        String currentNumber = "";
        String currentStringOfChars = "";

        for (int i = 0; i < expression.length(); i++) {
            char c = expression.charAt(i);

            if (!isSpace(c)) {
                if (isNumber(c)) {
                    currentNumber = currentNumber.concat(String.valueOf(c));
                }

                if (isOperation(c) || isParenthesis(c)) {
                    if (!currentNumber.equals(""))
                        brokenUpExpression.add(currentNumber);

                    if (!currentStringOfChars.equals(""))
                        brokenUpExpression.add(currentStringOfChars);

                    currentStringOfChars = "";
                    currentNumber = "";

                    brokenUpExpression.add(String.valueOf(c));
                }

                if (currentStringOfChars.equals("cos") || currentStringOfChars.equals("sin") ||
                        currentStringOfChars.equals("Cos") || currentStringOfChars.equals("Sin") ||
                        currentStringOfChars.equals("COS") || currentStringOfChars.equals("SIN")) {
                    brokenUpExpression.add(currentStringOfChars);
                    currentStringOfChars = "";
                }

                if (isCharFromABC(c)) {
                    currentStringOfChars = currentStringOfChars.concat(String.valueOf(c));
                }
            }
        }

        if (!currentNumber.equals(""))
            brokenUpExpression.add(currentNumber);

        if (!currentStringOfChars.equals(""))
            brokenUpExpression.add(currentStringOfChars);


        return brokenUpExpression;
    }

    private static boolean isNumber(char c) {
        switch (c) {
            case '0':
            case '1':
            case '2':
            case '3':
            case '4':
            case '5':
            case '6':
            case '7':
            case '8':
            case '9':
            case '.':
                return true;
            default:
                return false;
        }
    }

    private static boolean isOperation(char c) {
        switch (c) {
            case '-':
            case '+':
            case '^':
            case '*':
            case '/':
                return true;
            default:
                return false;
        }
    }

    private static boolean isParenthesis(char c) {
        switch (c) {
            case '(':
            case ')':
                return true;
            default:
                return false;
        }
    }

    private static boolean isSpace(char c) {
        return c == ' ';
    }

    private static boolean isCharFromABC(char c) {
        if (((c >= 65) && (c <= 90)) || ((c >= 97) && (c <= 122)))
            return true;
        else
            return false;
    }

    //Change values in the arraylist
    //------------------------------------------------------------------------------------------------------------------
    private static ArrayList<String> changeSpecialCharsToValue(ArrayList<String> expression) {

        for (int i = 0; i < expression.size(); i++) {
            if (expression.get(i).equals("E") || expression.get(i).equals("e"))
                expression.set(i, "" + Math.E);

            if (expression.get(i).equals("PI") || expression.get(i).equals("Pi") ||expression.get(i).equals("pi"))
                expression.set(i, "" + Math.PI);
        }

        return expression;
    }

    public static ArrayList<String> changeStringToVal(ArrayList<String> expression, String str, double val) {
        for (int i = 0; i < expression.size(); i++) {
            if (expression.get(i).equals(str))
                expression.set(i, "" + val);
        }

        return expression;
    }

    public static String changeSubstringToVal(String expression, String str, double val) {

        if (Math.abs(val) > 0.0001) {
            if (val>0)
                expression = expression.replace(str,""+val);
            else {
                expression = expression.replace(str,"(0"+val+")");
            }
        } else
            expression = expression.replace(str,""+0);

        return expression;
    }

    //Parentheses
    //------------------------------------------------------------------------------------------------------------------
    private static ArrayList<String> executeParentheses(ArrayList<String> expression, int from, int till) throws Exception {

        ArrayList<Integer> parenthesesList = new ArrayList<Integer>();
        for (int i = from; i < till; i++) {
            if (expression.get(i).equals("(")) {
                parenthesesList.add(i);
            }

            if (expression.get(i).equals(")")) {
                int index = parenthesesList.get(parenthesesList.size()-1);
                expression.remove(index);
                expression.remove(i-1);

                expression = executeExpressionReal(expression, parenthesesList.get((parenthesesList.size()-1)),i-1);
                parenthesesList.remove(parenthesesList.size()-1);
                i = from;
            }

            till = Math.min(expression.size(),till);
        }

        expression = executeExpressionReal(expression, 0, expression.size());


        return expression;
    }

    //Execute expression real
    //------------------------------------------------------------------------------------------------------------------
    private static ArrayList<String> executeExpressionReal(ArrayList<String> expression, int from, int till) throws Exception {
        if ((from >= expression.size()) || (till > expression.size()) || (from > till))
            throw new ArrayIndexOutOfBoundsException("Something must've gone terribly wrong for this to happen.");

        int powernr = 0;
        int multdivnr = 0;
        int funcnr = 0;
        for (int j = from; j < till; j++) {
            if (expression.get(j).equals("*") || expression.get(j).equals("/"))
                multdivnr++;

            if (expression.get(j).equals("sin") || expression.get(j).equals("cos"))
                funcnr++;

            if (expression.get(j).equals("^"))
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

    //Exponentiation
    //------------------------------------------------------------------------------------------------------------------
    private static ArrayList<String> executeExponentiation(ArrayList<String> expression, int from, int till) {

        for (int i = from; i < till; i++) {
            if (expression.get(i).equals("^")) {
                double val = Math.pow(Double.parseDouble(expression.get(i-1)),Double.parseDouble(expression.get(i+1)));

                expression.set(i, "" + val);
                expression.remove(i-1);
                expression.remove(i);

                till -= 2;
            }

            if (i < till)
                if (expression.get(i).equals("^")) i--;
        }

        return expression;
    }

    //Functions
    //------------------------------------------------------------------------------------------------------------------
    private static ArrayList<String> executeFunctions(ArrayList<String> expression, int from, int till) {

        for (int i = from; i < till; i++) {
            if (expression.get(i).equals("sin") || expression.get(i).equals("Sin") || expression.get(i).equals("SIN")) {

                expression.set(i, "" + Math.sin(Double.parseDouble(expression.get(i+1))));
                expression.remove(i+1);
                till -= 1;

            } else if (expression.get(i).equals("cos") || expression.get(i).equals("Cos") || expression.get(i).equals("COS")) {

                expression.set(i, "" + Math.cos(Double.parseDouble(expression.get(i+1))));
                expression.remove(i+1);
                till -= 1;

            }
        }

        return expression;
    }

    //Multiplications
    //------------------------------------------------------------------------------------------------------------------
    private static ArrayList<String> executeMultiplicationsAndDivisions(ArrayList<String> expression, int from, int till) throws Exception {

        for (int i = from; i < till; i++) {
            if (expression.get(i).equals("*")) {
                if (i-1 < 0)
                    throw new Exception("An expression cannot start with an 'action'!");
                else if (i+1 > till)
                    throw new Exception("An expression cannot end with an 'action'!");

                float val = Float.parseFloat(expression.get(i-1)) * Float.parseFloat(expression.get(i+1));
                expression.set(i-1, "" + val);
                expression.remove(i);
                expression.remove(i);

                till -= 2;

                if (i < till)
                    if (expression.get(i).equals("*") || expression.get(i).equals("/")) i--;

            } else if (expression.get(i).equals("/")) {
                if (i-1 < 0)
                    throw new Exception("An expression cannot start with an 'action'!");
                else if (i+1 > till)
                    throw new Exception("An expression cannot end with an 'action'!");

                float val = Float.parseFloat(expression.get(i-1)) / Float.parseFloat(expression.get(i+1));
                expression.set(i-1, "" + val);
                expression.remove(i);
                expression.remove(i);

                till -= 2;

                if (i < till)
                    if (expression.get(i).equals("*") || expression.get(i).equals("/")) i--;
            }
        }

        return expression;
    }

    //Addition and substraction
    //------------------------------------------------------------------------------------------------------------------
    private static ArrayList<String> executeAdditionsAndSubstractions(ArrayList<String> expression, int from, int till) throws Exception {

        for (int i = from; i < till; i++) {
            if (expression.get(i).equals("+")) {
                if (i-1 < 0)
                    throw new Exception("An expression cannot start with an 'action'!");
                else if (i+1 > till)
                    throw new Exception("An expression cannot end with an 'action'!");

                float val = Float.parseFloat(expression.get(i-1)) + Float.parseFloat(expression.get(i+1));

                expression.set(i-1, "" + val);
                expression.remove(i);
                expression.remove(i);

                till -= 2;

                if (i < till)
                    if (expression.get(i).equals("+") || expression.get(i).equals("-")) i--;

            } else if (expression.get(i).equals("-")) {
                if (i-1 < 0)
                    throw new Exception("An expression cannot start with an 'action'!");
                else if (i+1 > till)
                    throw new Exception("An expression cannot end with an 'action'!");

                float val = Float.parseFloat(expression.get(i-1)) - Float.parseFloat(expression.get(i+1));
                expression.set(i-1,"" + val);
                expression.remove(i);
                expression.remove(i);
                till -= 2;

                if (i < till)
                    if (expression.get(i).equals("+") || expression.get(i).equals("-")) i--;
            }
        }
        return expression;
    }

    //A function for printing out the broken up expression
    //------------------------------------------------------------------------------------------------------------------
    public static void printEx(ArrayList<String> expression) {
        for (String s: expression)
            System.out.print(s + " ");
        System.out.println();
    }

    //Put string together
    //------------------------------------------------------------------------------------------------------------------
    public static String putTogether(String[] cmd) {
        String retString = "";

        for (int i = 0; i < cmd.length; i++) {
            retString = retString.concat(cmd[i]);
        }

        return retString;
    }

}

