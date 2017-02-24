package mainpackage;

import java.util.ArrayList;

/**
 * Créé par l'équipe qu'elle est forte en BioInfo
 */
public class Analyzer {

    public static boolean checkString (String string1, String string2){
        if (string1.length()!=string2.length()){
            return false;
        }
        else {
            for (int i=0; i<string1.length();i++){
                if (string1.charAt(i)!=string2.charAt(i)){
                    return false;
                }
            }
        }
        return true;
    }

    /*
    * Les bornes inf et sup existent dans la séquence
    * Les bornes inf et sup sont des nombres
    * La borne inf est inf à la borne sup
    * les bornes inf et sup sont séparées par ..
    */
    public static boolean checkIsCDS (String line){



        return true;
    }

    public static boolean checkCodonInit (String line){
        ArrayList<String> list = new ArrayList<String>();
        boolean b = true;
        for (int i=0; i<list.size();i++){
            for (int j=0; j<list.get(i).length();j++){
                if (line.charAt(j)!= list.get(i).charAt(j)){
                    b = false;
                }
                if (b==true){
                    return true;
                }
                else{
                    b = true;
                }
            }
        }
        return false;
    }

    public static boolean checkCodonStop (String line){
        ArrayList<String> list = new ArrayList<String>();
        boolean b = true;
        for (int i=0; i<list.size();i++){
            for (int j=0; j<list.get(i).length();j++){
                if (line.charAt(j)!= list.get(i).charAt(j)){
                    b = false;
                }
                if (b){
                    return true;
                }
                else{
                    b = true;
                }
            }
        }
        return false;
    }

    public static boolean checkCorrectChar (char c){
        if (c=='a' || c=='c' || c=='t' || c=='g'){
            return true;
        }
        return false;
    }


    public static boolean checkCds (String line) {
        String cds = "CDS";
        for (int i = 0; i < cds.length(); i++) {
            if (cds.charAt(i) != line.charAt(i)) {
                return false;
            }
        }
        return true;
    }

    public static boolean checkInit (String line) {
        String origin = "ORIGIN";
        for (int i = 0; i < origin.length(); i++) {
            if (origin.charAt(i) != line.charAt(i)) {
                return false;
            }
        }
        return true;
    }

    public static boolean checkEnd (String line) {
        String end = "//";
        for (int i = 0; i < end.length(); i++) {
            if (end.charAt(i) != line.charAt(i)) {
                return false;
            }
        }
        return true;
    }

    /*
    * Fonctions à faire pour le join
    */ 
    /* TODO
    * Fonction qui doit tester si une chaine est un intervalle de la forme : a..b
    *    - a<b
    *    - a est un int
    *    - b est un int
    */
    public static boolean isInterval (String intervalle){
        return true;
    }
    /*
     * Calcule le nombre de caractères à récupérer pour utiliser la méthode substring
     */
    public static int nbCaract (int inf, int sup){
    	return (sup-inf);
    }
    
    /* TODO
     * Fonction qui permet de join plusieurs chaines de caractères
     * join([string1,string2,string3,...], string_global)
     * chaque string doit être de la forme d'un intervalle : int..int
     * Il faut récupérer l'intervalle de chaque string et aller chercher dans la chaine globale chaque chaine correspondant aux intervalles précédente
     * On retourne la contraction de tous les chaînes
     * 
     * utiliser substring
     * 
     */
    public static String join (ArrayList<String> list_interval, String global_string){
        return "";
    }
    
    
    
}
