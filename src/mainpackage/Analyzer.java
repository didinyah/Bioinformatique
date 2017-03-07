package mainpackage;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Créé par l'équipe qu'elle est forte en BioInfo
 */
public class Analyzer {

    public static String extractContentLine(String contentLine) throws Exception {
        // Extrait d'une ligne de content les infos utiles
        Pattern p1 = Pattern.compile("^ *([0-9]+) (.*)$");
        Matcher m1 = p1.matcher(contentLine);
        if(m1.find()){
            int position = Integer.parseInt(m1.group(1));
            String tmpgene = m1.group(2);
            Pattern p2 = Pattern.compile(" ");
            String[] items = p2.split(tmpgene);
            if(items.length >0){
                String res = ""; // le cas générique
                for ( String item : items){
                    res += item;
                }
                return res;
            }else if(items.length ==0 && tmpgene.length() > 0 ){
                return tmpgene; // le cas ou il y a juste un morceau de moin de 10 char de gene
            }else{
                throw new Exception();
            }

        }else{
            throw new Exception();
        }
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

    public static boolean checkCodonInit (String line){
        return Utils.getListOfCodonInit().contains(line);
    }

    public static boolean checkCodonStop (String line){
        return Utils.getListOfCodonStop().contains(line);
    }

    public static boolean checkCorrectChar (char c){
        if (c=='a' || c=='c' || c=='t' || c=='g'){
            return true;
        }
        return false;
    }


    public static boolean checkCds (String line) {
        Pattern p = Pattern.compile(" *CDS +.*");
        return p.matcher(line).find();
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
    // Voir si il faut bien checker les codons init et stop
    public static void countTrinFromString(String content, Trinucleotide current, int phase) throws Exception{
    	if(content.length()%3==0){
	    	Pattern p = Pattern.compile("([a|t|c|g][a|t|c|g][a|t|c|g])"); //permet de savoir si il y a autre chose que a c g t
	        Matcher m = p.matcher(content);
            int countMatches = 0;
            Trinucleotide tmpTri = new Trinucleotide();
            String first = "";
            String last = "";
            while (m.find()) {
                // On affecte le premier
                if(countMatches==0){
                    first = m.group();
                }
                //On affecte le dernier
                last = m.group();

                countMatches++;
                tmpTri.addTriN(m.group(),1,phase);
            }

            if((checkCodonInit(first) && checkCodonStop(last) && phase == 0) || (phase == 1 || phase == 2) ){ // on ne vérife pas pour les phases 1 et 2
                if(countMatches*3==content.length()){
                    current.fusion(tmpTri,phase);
                }else{
                    throw new Exception("Group size is not multiple of 3 (found error in symbol)");
                }
            }else{
                throw new Exception("Codont init or codont stop is not present");
            }

    	}else{
    		throw new Exception("Length not multiple of 3");
    	}
    }
    // Fonction importante permertant de calculer les 3 phases d'un trinucléotide
    public static void countTrinIn3PhasesFromString(String str,Trinucleotide current) throws Exception {
        // Todo vérifier comment on phases un str
        // Ne verifie rien car la fonction qu'on utilisera throw des exceptions

        Pattern p1 = Pattern.compile("^(.)(.*)$");
        Matcher m1 = p1.matcher(str);
        Pattern p2 = Pattern.compile("^(..)(.*)$");
        Matcher m2 = p2.matcher(str);
        if(m1.find() && m2.find()){
            countTrinFromString(str,current,0);
            countTrinFromString(m1.group(2)+m1.group(1),current,1);
            countTrinFromString(m2.group(2)+m2.group(1),current,2);
        }else{
            throw new Exception();
        }
    }

     /*
     * Calcule le nombre de caractères à récupérer pour utiliser la méthode substring
     */
    public static int nbCaract (int inf, int sup){
    	return (sup-inf);
    }

    /*
     * Fonction qui permet de join plusieurs chaines de caractères
     * join([string1,string2,string3,...], string_global)
     * chaque string doit être de la forme d'un intervalle : int..int
     * Il faut récupérer l'intervalle de chaque string et aller chercher dans la chaine globale chaque chaine correspondant aux intervalles précédente
     * On retourne la contraction de tous les chaînes
     *
     * utiliser substring
     *
     */
    public static List<Borne> join (String global_string) throws Exceptions.ExceptionCds {
        List<Borne> listTmp = new ArrayList<Borne>();
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
                    listTmp.add(b);
                }
            }else{
                // cas ou il n'y en pas (todo cas inutile ???)
                Borne b = stringToBorne(tmp);
                listTmp.add(b);
            }

        }else{
            throw new Exceptions.ExceptionCds();
        }
        return listTmp;
    }

    public static boolean isJoin(String line){
        Pattern p = Pattern.compile("join\\(.*\\)");
        return p.matcher(line).find();
    }
    /*
    * Fonction qui doit tester et extraire si une chaine est un intervalle de la forme : a..b
    *    - a<b
    *    - a est un int
    *    - b est un int
    *    renvoi Borne a b
    *    Rien d'autres
    *    TODO vérifier les expections génèré sont-elles les bonnes ?
    */
    public static Borne stringToBorne(String it) throws Exceptions.ExceptionCds {
    // Prend un string de type int..int et renvoie une Borne en vérifiant si tout est bon
        Pattern p = Pattern.compile("^([0-9]+)\\.\\.([0-9]+)$");
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
                    throw new Exceptions.ExceptionCds();
                }
            }else{
                throw new Exceptions.ExceptionCds();
            }
        }else{
            throw new Exceptions.ExceptionCds();
        }

    }

    public static List<Borne> cdsToBornes(String cdsLine) throws Exceptions.ExceptionCds {
        Pattern p = Pattern.compile(" *CDS +(.*)");
        Matcher m = p.matcher(cdsLine);
        if(m.find()){
            String strToExtract = m.group(1);
            if(strToExtract.length() > 2){// car il y a au minimun ..
                Pattern pcomplement = Pattern.compile("complement\\((.*)\\)");
                Matcher mcomplement = pcomplement.matcher(strToExtract);
                if(mcomplement.find()){
                    strToExtract = mcomplement.group(1); // on enlève le texte complement( )
                    if(isJoin(strToExtract)){
                        // on crée l'unique id identifiant la même jointure
                        String uniqueID = UUID.randomUUID().toString();
                        List<Borne> joinList =  join(strToExtract);

                        if(isCorrectListBorne(joinList)){
                            // On ajoute des informations sur les bornes
                            for(Borne b :joinList)
                            {
                                b.setComplement(true);
                                b.setMultipleBorne(true);
                                b.setLinkId(uniqueID);
                            }
                            return joinList;
                        }else{
                            throw new Exceptions.ExceptionCds();
                        }
                    }else{
                        Borne b = stringToBorne(strToExtract);
                        b.setComplement(true);
                        List<Borne> singleElement = new ArrayList<Borne>();
                        singleElement.add(b);
                        return singleElement;
                    }
                }else{
                    if(isJoin(strToExtract)){
                        // on crée l'unique id identifiant la même jointure
                        String uniqueID = UUID.randomUUID().toString();
                        List<Borne> joinList =  join(strToExtract);

                        if(isCorrectListBorne(joinList)){
                        // On ajoute des informations sur les bornes
                            for(Borne b :joinList)
                            {
                                b.setMultipleBorne(true);
                                b.setLinkId(uniqueID);
                            }
                            return joinList;
                        }else{
                            throw new Exceptions.ExceptionCds();
                        }
                    }else{
                        Borne b = stringToBorne(strToExtract);
                        List<Borne> singleElement = new ArrayList<Borne>();
                        singleElement.add(b);
                        return singleElement;
                    }
                }
            }else{
                throw new Exceptions.ExceptionCds();
            }
        }else{
            throw new Exceptions.ExceptionCds();
        }
    }

    private static boolean isCorrectListBorne(List<Borne> joinList) {
        if(!joinList.isEmpty()){
            Borne lastBorne = null;
            for(Borne b: joinList){
                if(!b.isCorrectBorne()){
                    return false;
                }
                if(lastBorne == null){
                    lastBorne =b;
                }else{
                    if(!b.isGreaterThan(lastBorne)){
                        return false;
                    }
                    lastBorne = b;
                }

            }
        }else{
            return false;
        }

        return  true;
    }


    public static boolean isCdsMultiLine(String line1) {
        // Il faut qu'il y a CDS dans la ligne et que la dernier caractère soit une virgule ,
        Pattern p = Pattern.compile(" *CDS +.*,$");
        return p.matcher(line1).find();
    }

    public static boolean isEndCdsMultiLine(String reconstructLine) {
        Pattern p = Pattern.compile("\\)$");
        return p.matcher(reconstructLine).find();
    }

    public static String cdsMultiLineToString(List<String> multiLine) throws Exception {
        String fullLine = "";
        boolean firstLine = true;
        for ( String line : multiLine){
            if(firstLine){
                fullLine += line;
                firstLine = false;
            }else{
                Pattern p = Pattern.compile(" *(.*)$");
                Matcher m = p.matcher(line);
                if(m.find()){
                    fullLine += m.group(1);
                }else{
                    throw new Exception();
                }
            }
        }
        return fullLine;
    }

    public static class Borne {

        private Integer borninf;
        private Integer bornsup;
        private boolean complement; // savoir si la borne est complement
        private boolean multipleBorne; // savoir si c'est une borne d'une jointure
        private String  linkId; // l'id de lien avec les autres borne de la même jointure (todo créer un id plus complexe uniqid)

        public Borne(){
            // Variable par défaut
            complement = false;
            multipleBorne = false;
            linkId = "error"; // -1 signifie qu'il n'y a pas de lien
        }
        public boolean isInBorne(int a){
            return borninf <= a && a <= bornsup;
        }


        public void setBorninf(Integer borninf) {
            this.borninf = borninf;
        }

        public void setBornsup(Integer bornsup) {
            this.bornsup = bornsup;
        }


        @Override
        // i = inf s =sup C = complement M = multiline
        public String toString() {
            String complementLetter = complement ? "C": "";
            String multiLine = multipleBorne ? "M": "";
            String addInfo = complement || multipleBorne ? " (I): " + complementLetter +" "+ multiLine : "";
            return borninf+ " i .. s "+bornsup + addInfo ;
        }

        public void setComplement(boolean complement) {
            this.complement = complement;
        }


        public void setMultipleBorne(boolean multipleBorne) {
            this.multipleBorne = multipleBorne;
        }

        public boolean isComplement() {
            return complement;
        }

        public boolean isMultipleBorne() {
            return multipleBorne;
        }

        public String getLinkId() {
            return linkId;
        }

        public void setLinkId(String linkId) {
            this.linkId = linkId;
        }

        public boolean isCorrectBorne(){
            if(borninf < bornsup){
                // test sur complement ?
                if(multipleBorne){
                    // on test que l'unique ID a bien été défini
                    return !linkId.equals("error");
                }else{
                    return true;
                }

            }else{
                return false;
            }
        }

        // b1.isGreaterThan(b2) => b1.borninf >= b2.bornsup (todo vérifier >= ou > stric ? autre à verifier ?)
        public boolean isGreaterThan(Borne b){
            return borninf >= b.bornsup;
        }
    }

}
