package swingingAround.Math;

import java.util.ArrayList;

/**
 * String-ben kapott matematikai kifejezéseket próbál kiértékelni.
 * <p>
 *     A matematikai kifejezés alatt jelen esetben egy
 *     olyan karakter sorozat értendő, amiben csak E,PI,
 *     számok, műveletek (+,-,*,/,^), zárójelek, függvények
 *     (sin, cos) szerepelnek. Ha ezen kívűl más karakter-sorozat
 *     (pl. x,y,z, vagy "valtozo") szerepel, akkor azt ennek az
 *     osztálynak egy függvényével ki lehet cserélni konkrét
 *     értékekre, más esetben a kifejezést az osztály nem képes
 *     kiértékelni.
 * </p>
 */
public class MathHelper {

    /**
     * Az osztály főfüggvénye. Ez az ami egy adott String-ben szereplő kifejezést megpróbál kiszámolni.
     * @param expression String-ben érkező kifejezés
     * @return a kifejezés kiszámolt értéke
     * @throws Exception ha valamilyen okból a kifejezést nem tudja kiértékelni Exception-t dob.
     */
    public static double executeExpression(String expression) throws Exception {
        String original = expression;
        if (expression.startsWith("-"))
            expression = "0" + expression;

        expression = expression.replace("(-","(0-");

        ArrayList<String> workable = breakUpExpression(expression);
        workable = changeSpecialCharsToValue(workable);
        workable = executeParentheses(workable,0,workable.size());

        if (workable.size() == 1) {
            return Double.parseDouble(workable.get(0)) ;
        } else {
            System.err.println("For some reason the evaluation of the provided expression failed.");
            System.err.println("------------------------------------------------------------------------------------------------------------------------");
            System.err.println("Original expression:");
            System.err.println(original);
            System.err.println("What remains:");
            printEx(workable);
            System.err.println("------------------------------------------------------------------------------------------------------------------------");
            System.err.println("Returning the value 0");
            throw new Exception("Evaluation of expression failed.");
        }
    }

    //Breaking up the expression into an arraylist of smaller strings
    //------------------------------------------------------------------------------------------------------------------
    /**
     * Feldarabolja a kifejezést számokra, "szavakra", és műveletekre (+,-,*,/,^), és zárójelekre.
     * @param expression a kifejezés.
     * @return a feldarabolt kifejezés.
     */
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

    /**
     * Meghatározza, hogy egy adott karakter szám-e.
     * @param c a karakter, amelyről el kell dönteni, hogy szám-e.
     * @return valóban szám-e ez a karakter.
     */
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

    /**
     * Meghatározza, hogy egy adott karakter művelet-e.
     * @param c a karakter, amelyről el kell dönteni, hogy művelet-e.
     * @return valóban művelet-e ez a karakter.
     */
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

    /**
     * Meghatározza, hogy egy adott karakter zárójel-e.
     * @param c a karakter, amelyről el kell dönteni, hogy zárójel-e.
     * @return valóban zárójel-e ez a karakter.
     */
    private static boolean isParenthesis(char c) {
        switch (c) {
            case '(':
            case ')':
                return true;
            default:
                return false;
        }
    }

    /**
     * Eldönti egy karakterröl hogy space vagy sem.
     * @param c a karakter.
     * @return space-e a karakter.
     */
    private static boolean isSpace(char c) {
        return c == ' ';
    }

    /**
     * Eldönti egy char-ról, hogy az abc egyik betűje, vagy sem.
     * @param c char amiről dönteni kell
     * @return a char az abc egyik betűje, vagy sem
     */
    private static boolean isCharFromABC(char c) {
        if (((c >= 65) && (c <= 90)) || ((c >= 97) && (c <= 122)))
            return true;
        else
            return false;
    }

    //Change values in the arraylist
    //------------------------------------------------------------------------------------------------------------------
    /**
     * A feldarabolt kifejezésben kicseréli az ismert speciális karaktereket (E, Pi) számokra.
     * @param expression feldarabolt kifejezés
     * @return feldarabolt kifejezés melyben már nem szerepel E vagy Pi, csak az értékük.
     */
    private static ArrayList<String> changeSpecialCharsToValue(ArrayList<String> expression) {

        for (int i = 0; i < expression.size(); i++) {
            if (expression.get(i).equals("E") || expression.get(i).equals("e"))
                expression.set(i, "" + Math.E);

            if (expression.get(i).equals("PI") || expression.get(i).equals("Pi") ||expression.get(i).equals("pi"))
                expression.set(i, "" + Math.PI);
        }

        return expression;
    }

    /**
     * Feldarabolt kifejezésben kicserél egy adott String-et egy adott értékre.
     * @param expression feldarabolt kifejezés
     * @param str a kicserélendő karaktersorozat
     * @param val az érték amire a karaktersorozatot cseréljük
     * @return feldarabolt kifejezés amiben megtörtént már a csere
     */
    public static ArrayList<String> changeStringToVal(ArrayList<String> expression, String str, double val) {
        for (int i = 0; i < expression.size(); i++) {
            if (expression.get(i).equals(str))
                expression.set(i, "" + val);
        }

        return expression;
    }

    /**
     * Egy adott String-ben kicserél egy másik String-et egy adott értékre
     * <p>
     *     Ez függvény abban tér el a String.replace-től többek között,
     *     hogy használja azt, és ha az érték negatív lenne, akkor azt
     *     zárójelek közé teszi, így kitudja értékelni.
     *     Például azt nem tudná kiértékelni, hogy y=3-x, ahol x-et -4-re
     *     cserélnénk, mert az csak annyi lenne, hogy y=3--4, amit nem tudna
     *     értelmezni. Így, az y=3-x arra változik, hogy y=3-(0-4), amit már
     *     kitud értékelni.
     *
     *     Ezen kívűl problémát okozott az, hogy ha egy szám olyan közel
     *     kerűlt a nullához hogy a java már E-esen ábrázolta (pl.: 1E-8),
     *     ehelyett az ilyen kis számokat inkább 0-ra cseréli ez a függvény.
     *
     *     Ellenben problémát okozhat az, ha a szám túl nagy (pl.:1E16), mert
     *     azzal megint nem tud majd ez az osztály mit kezdeni, és így az
     *     eredeti kifejezést nem fogja tudni kiértékelni.
     * </p>
     * @param expression a kifejezés amiben egy substring értékre cserélődik
     * @param str a substring amit lecserélünk
     * @param val az érték amire lecseréljük
     * @return a megváltozott kifejezés.
     */
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
    /**
     * Végig fut a kifejezésen és végrehajtatja a zárójelek között álló kifejezéseket.
     * <p>
     *     Egy listában gyűjti a nyitózárójelek pozicíóját, és ha talál egy záró zárójelet,
     *     akkor a lista utolsó elemétől (amit eltávolít) egészen a záró zárójelig végre hajtatja
     *     a kifejezést. Így mindig először a zárójelek között álló dolgok kerülnek kiértékelésre.
     * </p>
     * @param expression feldarabolt kifejezés
     * @param from honnantól kell "végre hajtani" a zárójelezést
     * @param till meddig kell a zárójelezést csinálni
     * @return feldarabolt kifejezés melyben nem szerepel zárójel
     * @throws Exception tovább dobja az exceptiont amit kap, ha valami elromlik amikor végre hajtatja a zárójelek közötti részeket.
     */
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
    /**
     * Kiértékeli a kifejezést valamettől valameddig.
     * @param expression feldarabolt kifejezés
     * @param from honnan
     * @param till meddig
     * @return feldarabolt kifejezés, ahol a from-till-ig tartó rész helyén már csak egy szám szerepel
     * @throws Exception tovább dobja azt az exception-öket amikett a kifejezés kiértékelése közben kaphat.
     */
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
    /**
     * Végrehajtja az összes hatványozást valamettől valameddig
     * @param expression feldarabolt kifejezés
     * @param from honnan
     * @param till meddig
     * @return feldarabolt kifejezés, ahol már végre lett hajtva a hatványre emelés ott ahol kellett.
     */
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
    /**
     * Kiértékeli a sin/cos függvényeket valahonnan valameddig.
     * @param expression feldarabolt kifejezés
     * @param from mettől
     * @param till meddig
     * @return feldarabolt kifejezés, melyben a megfelelő helye(ke)n már kiértékelődtek a sin/cos függvények.
     */
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
    /**
     * Elvégzi a szorzást, osztást valamettől valameddig.
     * @param expression feldarabolt kifejezés
     * @param from mettől végezze el a szorzást/osztást
     * @param till meddig végezze el a szorzást/osztást
     * @return feldarabolt kifejezés, amiben el lett végezve a szorzás/osztás ott ahol kellett
     * @throws Exception kivételt dob, ha a művelet a kifejezés elején, vagy végén áll, mert akkor azt nem lehet végre hajtani.
     */
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
    /**
     * Elvégzi az összeadást, kivonást valamettől valameddig.
     * @param expression feldarabolt kifejezés
     * @param from mettől végezze el a összeadást/kivonást
     * @param till meddig végezze el a összeadást/kivonást
     * @return feldarabolt kifejezés, amiben el lett végezve a összeadás/kivonás ott ahol kellett
     * @throws Exception kivételt dob, ha a művelet a kifejezés elején, vagy végén áll, mert akkor azt nem lehet végre hajtani.
     */
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
    /**
     * Egy sorba szóközökkel elválasztva kiír egy adott feldarabolt kifejezést
     * @param expression a kiirandó feldarabolt kifejezés
     */
    public static void printEx(ArrayList<String> expression) {
        for (String s: expression)
            System.out.print(s + " ");
        System.out.println();
    }

    //Put string together
    //------------------------------------------------------------------------------------------------------------------
    /**
     * Egy String tömböt egyesít egy String-be.
     * @param cmd egyesítendő String tömb
     * @return egyesített String tömb
     */
    public static String putTogether(String[] cmd) {
        String retString = "";

        for (int i = 0; i < cmd.length; i++) {
            retString = retString.concat(cmd[i]);
        }

        return retString;
    }

}

