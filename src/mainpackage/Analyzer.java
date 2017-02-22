package mainpackage;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by SandrosLaptop on 21/02/2017.
 */
public class Analyzer {



    // On définit la liste de Borne (c'est un couple private classe définit en bas)
    private List<Borne> listBorne = new ArrayList<Borne>();

    public List<Borne> getListBorne() {
        return listBorne;
    }

    public void addListBorne(Borne b){
        listBorne.add(b);
    }

    // TODO (à voir) FONCTION VERIFIANT QUE LA LIST TRIE et QUI LA TRIE OU ADDLISTBORN DOIT AJOUTER DE MANIERE TRIE

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

    public static boolean checkIsCDS (String line){


        // TODO revient à lire listBorne est vérifier si on est compris dans une borne
        // deux problèmatique soit on considère que listBorn est trié en fonction de la premier borne par construction
        // soit pas (temps de calcul très long)

        //On ne doit pas vérifier ici si les bornes sont conformes par construction c'est bon

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

    private class Borne {
        private Integer borninf;
        private Integer bornsup;

        // Todo fonction d'appertenance de borne
        // Todo fonction de verification de borne


    }
}
