import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Scanner;

/**
 * Unification algorithm implementation
 *
 * @Author Santiago Hidalgo Ocampo
 */
public class Unify {
    public static LinkedList<String> substitutions = new LinkedList<>();
    public static boolean fail = true;

    /**
     * Main Method. Determine the program flow
     *
     * @param args
     * @throws FileNotFoundException
     */
    public static void main(String[] args) throws FileNotFoundException {
        String rawInformation = readFile();
        LinkedList<String> equivalences = split(rawInformation);
        unify(equivalences);
        if(fail){
            System.out.println("Substitutions set: ");
            for (String i: substitutions) {
                System.out.println(i);
            }
        }
    }

    /**
     * Function in charge of reading the file with the set of restrictions (C)
     *
     * @return String with raw information
     * @throws FileNotFoundException
     */
    public static String readFile() throws FileNotFoundException {
        String informationLines = "";
        String ruta = "./src/com/company/cs4.txt"; //TEMPORAL PATH!! CAREFUL
        File file = new File(ruta);
        Scanner input = new Scanner(file);
        while (input.hasNext()) {
            String line = input.nextLine();
            informationLines += line;
        }
        return informationLines;
    }

    /**
     * Function that divides raw information into a dynamic
     * list to facilitate the handling of equivalences
     *
     * @param rawInformation
     * @return Equivalences in a dynamic list
     */
    public static LinkedList<String> split(String rawInformation) {
        LinkedList<String> equivalences = new LinkedList<>();
        String[] lines = rawInformation.split("<EOL>");
        for (String index : lines) {
            equivalences.add(index);
        }
        return equivalences;
    }

    /**
     * Unification algorithm implementation
     *
     * @param equivalences
     */
    public static void unify(LinkedList<String> equivalences) {
        String currentLine = equivalences.get(0);
        currentLine = currentLine.replace(" ", "");
        // First case: Empty constraint set
        if (currentLine.equals("<EOF>")) {
            System.out.println("Program execution is complete");
        } else {
            //Separate Equivalence in S and T
            String[] arbitaryTypes = currentLine.split("=");
            String S = arbitaryTypes[0];
            String T = arbitaryTypes[1];
            // Determine if an expression is a variable according to the specified
            // regular expression  System.out.println(equivalences.toString());
            boolean is_S_Variable = S.matches("[A-Za-z]([A-Za-z | 0-9])*") && !S.equals("Nat") && !S.equals("Bool") && !S.contains("->");
            boolean is_T_Variable = T.matches("[A-Za-z]([A-Za-z | 0-9])*") && !T.equals("Nat") && !T.equals("Bool") && !T.contains("->");
            // The free variables of each equivalence member are obtained
            LinkedList<String> freeVariablesOf_S = freeVariables(S);
            LinkedList<String> freeVariablesOf_T = freeVariables(T);
            //Second case: Both members of the equivalence are the same
            if (S.equals(T)) {
                equivalences.remove(0);
                //System.out.println(equivalences.toString());
                unify(equivalences);
            }else if (is_S_Variable && !freeVariablesOf_T.contains(S)) {
                substitutions.add(S+"/->"+T);
                equivalences = changes(equivalences, S,T);
                // equivalences.remove(0);
                //System.out.println(equivalences.toString());
                unify(equivalences);
            }else if (is_T_Variable && !freeVariablesOf_S.contains(T)) {
                substitutions.add(T+"/->"+S);
                equivalences = changes(equivalences, T,S);
                //equivalences.remove(0);
                //System.out.println(equivalences.toString());
                unify(equivalences);
            }else if (S.contains("->") && T.contains("->")) {
                LinkedList<String> newConstraints = separateFunctions(S, T);
                for (String i: newConstraints){
                    equivalences.add(0,i);
                }
                //System.out.println(equivalences.toString());
                unify(equivalences);
            }else{
                fail = false;
                System.out.println("Fail");
            }
        }

    }

    /**
     * Function that returns the free variables of an expression
     *
     * @param line
     * @return The free variables of an expression
     */
    public static LinkedList<String> freeVariables(String line) {
        LinkedList<String> freeVariables = new LinkedList<>();
        line = line.replace("Nat", "");
        line = line.replace("Bool", "");
        line = line.replace("->", ",");
        line = line.replace("(", "");
        line = line.replace(")", "");
        String[] fv = line.split(",");
        for (String i : fv) {
            freeVariables.add(i);
        }
        return freeVariables;
    }

    /**
     * @param S
     * @param T
     * @return new constraints
     */
    public static LinkedList<String> separateFunctions(String S, String T) {
        System.out.println(S);
        System.out.println(T);
        LinkedList<String> newConstraints = new LinkedList<>();
        String[] s = S.split("->");
        String[] t = T.split("->");
        String [] arrayS = div(s);
        String [] arrayT = div(t);
        String Sprima = arrayS[0], Tprima = arrayT[0], Sprima2 = arrayS[1], Tprima2 = arrayT[1];
        newConstraints.add(Sprima+"="+Tprima);
        newConstraints.add(Sprima2+"="+Tprima2);
        return newConstraints;
    }

    /**
     * Auxiliary function to divide an equivalence member into two parts
     *
     * @param a
     * @return Member of equivalence divided
     */
    public static String[] div(String[] a) {
        String Aprima = "", Aprima2 = "";
        String[] array = new String[2];
        int aux = 0;
        int contS = 0;

        for (int i = 0; i < a.length; i++) {
            if (a[i].contains("(")) contS++;
            if ((a[i].contains(")"))) contS--;
            Aprima += a[i] + "->";
            if (i > 0 && contS == 0) {
                aux = i;
                break;
            }
            if(i==0 && !a[i].contains("(")){
                break;
            }
        }
        Aprima = Aprima.substring(0,Aprima.length()-2);

        for (int j = aux+1; j < a.length - 1; j++) {
            Aprima2 += a[j] + "->";
        }
        Aprima2 += a[a.length - 1];

        array[0] = Aprima;
        array[1] = Aprima2;
        return array;
    }
    
    /**
     *
     * @param equivalences
     * @param old
     * @param neww
     * @return
     */
    public static LinkedList<String> changes(LinkedList<String> equivalences , String old, String neww){
        LinkedList<String> newList = new LinkedList<>();
        old = old.replace(" ","");
        neww = neww.replace(" ", "");
        for (int i = 0; i< equivalences.size(); i++){
            newList.add(equivalences.get(i).replace(old,neww));
        }
        return newList;
    }
}
