package mainpackage;

import jdk.nashorn.internal.runtime.ECMAException;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Créé par l'équipe qu'elle est forte en BioInfo
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

    public void addListBorne(int inf, int sup){
        Borne b = new Borne();
        b.setBorninf(inf);
        b.setBornsup(sup);
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

    /*
    * Les bornes inf et sup existent dans la séquence
    * Les bornes inf et sup sont des nombres
    * La borne inf est inf à la borne sup
    * les bornes inf et sup sont séparées par ..
    */
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
        line = line.trim();
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
    public static List<Borne> join (String global_string) throws Exception {
        List<Borne> listTmp = new ArrayList<>();
        Pattern p = Pattern.compile("join\\((.*)\\)");
        Matcher m = p.matcher(global_string);
        if(m.find()){
            String tmp = m.group(1); // ex: 20..30,50..60,90..500
            Pattern p2 = Pattern.compile(",");
            Matcher m2 = p2.matcher(tmp);
            if(m2.find()){
                // cas ou il y a des virgules
                String[] items = p2.split(tmp);
                for ( String it : items){
                    Borne b = stringToBorne(it);
                    System.out.println(b);
                    listTmp.add(b);
                }
            }else{
                // cas ou il n'y en pas
                Borne b = stringToBorne(tmp);
                System.out.println(b);
                listTmp.add(b);
            }

        }else{
            throw new Exception();
        }
        return listTmp;
    }

    /*
    * Fonction qui doit tester et extraire si une chaine est un intervalle de la forme : a..b
    *    - a<b
    *    - a est un int
    *    - b est un int
    *    renvoi Borne a b
    *    Rien d'autres
    *    TODO vérifier les expections génré sont-elles les bonnes ?
    */
    public static Borne stringToBorne(String it) throws Exception {
    // Prend un string de type int..int et renvoie une Borne en vérifiant si tout est bon
        Pattern p = Pattern.compile("([0-9]+)\\.\\.([0-9]+)");
        Matcher m = p.matcher(it);
        if(m.find()){
            if(m.groupCount() == 2){
                Borne b = new Borne();
                int inftmp = Integer.parseInt(m.group(1));
                int suptmp = Integer.parseInt(m.group(2));
                // TODO vérifier que toute les vérifications sont faites !!
                if(inftmp < suptmp){
                    b.setBorninf(inftmp);
                    b.setBornsup(suptmp);
                    return b;
                }else{
                    throw new Exception();
                }
            }else{
                throw new Exception();
            }
        }else{
            throw new Exception();
        }

    }

    public static List<Borne> cdsToBornes(String cdsLine) throws Exception {
        // TODO à finir stopé car multiligne cds detecté provoque bug et donc revnir à la source du pb avant !
        Pattern p = Pattern.compile(" *CDS +(.*)");
        Matcher m = p.matcher(cdsLine);
        if(m.find()){
            String strToExtract = m.group(1);
            if(strToExtract.length() > 2){// car il y a au minimun ..
                // le cas ou y a complement (join -> appeler join
                // le cas join -> join
                // le cas ou y a juste complement ( appeler stringtoborne
                // le cas simple

            }
        }else{
            throw new Exception();
        }
        return null;
    }


    private static class Borne {
        public void setBorninf(Integer borninf) {
            this.borninf = borninf;
        }

        public void setBornsup(Integer bornsup) {
            this.bornsup = bornsup;
        }

        private Integer borninf;
        private Integer bornsup;

        public boolean isInBorne(int a){
            return borninf <= a && a <= bornsup;
        }



        @Override
        public String toString() {
            return borninf+ " i .. s "+bornsup;
        }
    }

}
