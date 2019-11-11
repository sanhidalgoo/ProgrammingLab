import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Scanner;

/**
 * Unification algorithm implementation
 *
 * @Author Santiago Hidalgo Ocampo
 *
 */
public class Unify {
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
    }

    /**
     * Function in charge of reading the file with the set of restrictions (C)
     *
     * @return String with raw information
     * @throws FileNotFoundException
     */
    public static String readFile() throws FileNotFoundException {
        String informationLines = "";
        String ruta = "./src/com/company/cs1.txt";
        File file = new File(ruta);
        Scanner input = new Scanner(file);
        System.out.println(file.getAbsolutePath());
        while (input.hasNext()) {
            String line = input.nextLine();
            informationLines+=line;
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
    public static LinkedList<String> split(String rawInformation){
        LinkedList<String> equivalences = new LinkedList<>();
        String [] lines = rawInformation.split("<EOL>");
        for (String index: lines) {
            equivalences.add(index);
        }
        return equivalences;
    }

    /**
     * Unification algorithm implementation
     *
     * @param equivalences
     */
    public static void unify(LinkedList<String> equivalences){
        String currentLine = equivalences.get(0);
        currentLine = currentLine.replace(" ", "" );
        // First case: Empty constraint set
        if(currentLine.equals("<EOF>")){
            System.out.println("Program execution is complete");
        }else{
            //Separate Equivalence in S and T
            String [] arbitaryTypes = currentLine.split("=");
            String S = arbitaryTypes[0];
            String T = arbitaryTypes[1];

            //Second case:
            if(S.equals(T)){
                equivalences.remove(0);
                unify(equivalences);
            }
            // Determine if an expression is a variable according to the specified
            // regular expression
            boolean is_S_Variable = S.matches("[A-Za-z_]([A-Za-z | 0-9])*");
            boolean is_T_Variable = T.matches("[A-Za-z_]([A-Za-z | 0-9])*");
            // The free variables of each equivalence member are obtained
            LinkedList<String> freeVariablesOf_S = freeVariables(S);
            LinkedList<String> freeVariablesOf_T = freeVariables(T);

            if(is_S_Variable && !freeVariablesOf_T.contains(S)){
                //...
            }
            if(is_T_Variable && !freeVariablesOf_S.contains(T)){
                //...
            }


        }

    }

    /**
     * Function that returns the free variables of an expression
     *
     * @param line
     * @return The free variables of an expression
     */
    public static LinkedList<String> freeVariables(String line){
        LinkedList<String> freeVariables = new LinkedList<>();
        line = line.replace("Nat","");
        line = line.replace("Bool","");
        line = line.replace("->", ",");
        line = line.replace("(","");
        line = line.replace(")","");
        String [] fv = line.split(",");
        for (String i: fv) {
            freeVariables.add(i);
        }
        return freeVariables;
    }
}
