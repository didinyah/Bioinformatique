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


    public static String complement (String string1){
        String res = new String();
        for (int i = 0; i < string1.length(); i++) {
            switch (string1.charAt(i)) {
                case 'a' :
                    res += 't';
                    break;
                case 't' :
                    res += 'a';
                    break;
                case 'c' :
                    res += 'g';
                    break;
                default :
                    res += 'c';
            }
        }
        StringBuffer buffer = new StringBuffer(res);
        return (buffer.reverse().toString());
    }


    public static String extractContentLine(String contentLine) throws Exceptions.ExceptionPatternLine {
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
                throw new Exceptions.ExceptionPatternLine();
            }

        }else{
            throw new Exceptions.ExceptionPatternLine();
        }
    }

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


    public static boolean checkCds (String line) {
        Pattern p = Pattern.compile(" *CDS +.*");
        return p.matcher(line).find();
    }



    public static boolean checkInit (String line) {
        Pattern p = Pattern.compile("ORIGIN");
        return p.matcher(line).find();
    }

    public static boolean checkEnd (String line) {
        Pattern p = Pattern.compile("//");
        return p.matcher(line).find();
    }

    // Voir si il faut bien checker les codons init et stop
    public static void countTrinFromString(String content, Trinucleotide current,Trinucleotide cdsCurrent, int phase) throws Exceptions.ExceptionPatternLine, Exceptions.ExceptionCodonNotFound {
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

            // On a compté en trop le codon stop
            if(phase == 0){ // la condition permet de soustraire qu'une fois car cette fonction marche pour les 3 phases TODO verif !
                tmpTri.addTriN(last,-1,0);
            }


            if((checkCodonInit(first) && checkCodonStop(last) && phase == 0) || (phase == 1 || phase == 2) ){ // on ne vérife pas pour les phases 1 et 2
                if(countMatches*3==content.length()){
                    current.fusion(tmpTri,phase);
                    cdsCurrent.fusion(tmpTri,phase);
                }else{
                    throw new Exceptions.ExceptionPatternLine("Group size is not multiple of 3 (found error in symbol)");
                }
            }else{
                throw new Exceptions.ExceptionCodonNotFound("Codont init or codont stop is not present");
            }

    	}else{
    		throw new Exceptions.ExceptionPatternLine("Length not multiple of 3");
    	}
    }

    public static void countDinFromString(String content, Dinucleotide current,Dinucleotide cdsCurrent, int phase) throws Exceptions.ExceptionPatternLine {
        if(content.length()%2==0){
            Pattern p = Pattern.compile("([a|t|c|g][a|t|c|g])"); //permet de savoir si il y a autre chose que a c g t
            Matcher m = p.matcher(content);
            int countMatches = 0;
            Dinucleotide tmpDi = new Dinucleotide();
            String last = "";
            while (m.find()) {

                //On affecte le dernier
                last = m.group();

                countMatches++;
                tmpDi.addDiN(m.group(),1,phase);
            }

            // On a compté en trop le codon stop
            if(phase==0){// la condition permet de soustraire qu'une fois car cette fonction marche pour les 3 phases TODO verif !
                tmpDi.addDiN(last,-1,0);
            }

            if(countMatches*2==content.length()){
                current.fusion(tmpDi,phase);
                cdsCurrent.fusion(tmpDi,phase);
            }else{
                throw new Exceptions.ExceptionPatternLine("Group size is not multiple of 2 (found error in symbol)");
            }


        }else{
            throw new Exceptions.ExceptionPatternLine("Length not multiple of 2");
        }
    }

    // Fonction importante permertant de calculer les 3 phases d'un trinucléotide
    public static void countTrinIn3PhasesFromString(String str,Trinucleotide general,Trinucleotide cdsCurrent) throws Exceptions.ExceptionPatternLine, Exceptions.ExceptionCodonNotFound {
        // Ne verifie rien car la fonction qu'on utilisera throw des exceptions

        Pattern p1 = Pattern.compile("^.(.*)[a|t|c|g][a|t|c|g]$");
        Matcher m1 = p1.matcher(str);
        Pattern p2 = Pattern.compile("^..(.*)[a|t|c|g]$");
        Matcher m2 = p2.matcher(str);
        if(m1.find() && m2.find()){
            countTrinFromString(str,general,cdsCurrent,0);
            countTrinFromString(m1.group(1),general,cdsCurrent,1);
            countTrinFromString(m2.group(1),general,cdsCurrent,2);
        }else{
            throw new Exceptions.ExceptionPatternLine();
        }
    }

    public static void countDinIn2PhasesFromString(String str,Dinucleotide general,Dinucleotide cdsCurrent) throws Exceptions.ExceptionPatternLine, Exceptions.ExceptionCodonNotFound {


        Pattern p1 = Pattern.compile("^.(.*)[a|t|c|g]$"); // TODO est-ce bon ? pour les DI il y a que 2 phases ? (jai po le sujet lol)
        Matcher m1 = p1.matcher(str);
        if(m1.find() ){
            countDinFromString(str,general,cdsCurrent,0);
            countDinFromString(m1.group(1),general,cdsCurrent,1);
            //countDinFromString(m2.group(1),general,cdsCurrent,2);
        }else{
            throw new Exceptions.ExceptionPatternLine();
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
    public static List<Bornes.Borne> join (String global_string) throws Exceptions.ExceptionCds, Exceptions.ExceptionBorne {
        List<Bornes.Borne> lstTmp = new ArrayList<Bornes.Borne>();
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
                    Bornes.Borne b = stringToBorne(it);
                    lstTmp.add(b);
                }
            }else{
                // cas ou il n'y en pas (todo cas inutile ???)
                Bornes.Borne b = stringToBorne(tmp);
                lstTmp.add(b);
            }

        }else{
            throw new Exceptions.ExceptionCds();
        }
        return lstTmp;
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
    public static Bornes.Borne stringToBorne(String it) throws Exceptions.ExceptionCds {
    // Prend un string de type int..int et renvoie une Borne en vérifiant si tout est bon
        Pattern p = Pattern.compile("^([0-9]+)\\.\\.([0-9]+)$");
        Matcher m = p.matcher(it);
        if(m.find()){
            if(m.groupCount() == 2){
                Bornes.Borne b = new Bornes.Borne();
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

    public static Bornes cdsToBornes(String cdsLine) throws Exceptions.ExceptionCds, Exceptions.ExceptionBorne {
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
                        List<Bornes.Borne> bornes =  join(strToExtract);
                        Bornes newBornes = new Bornes();

                        if(Bornes.isCorrectListBorne(bornes)){
                            // On ajoute des informations sur les bornes
                            Bornes.Borne lastB = null;
                            for(Bornes.Borne b : bornes)
                            {
                                b.setComplement(true);
                                b.setMultipleBorne(true);
                                b.setLinkId(uniqueID);
                                newBornes.addBorne(b);
                                lastB = b;
                            }
                            lastB.setLastMultipleBorne(true);
                            return newBornes;
                        }else{
                            throw new Exceptions.ExceptionCds();
                        }
                    }else{
                        Bornes.Borne b = stringToBorne(strToExtract);
                        b.setComplement(true);
                        Bornes singleElement = new Bornes();
                        singleElement.addBorne(b);
                        return singleElement;
                    }
                }else{
                    if(isJoin(strToExtract)){
                        // on crée l'unique id identifiant la même jointure
                        String uniqueID = UUID.randomUUID().toString();
                        List<Bornes.Borne> bornes =  join(strToExtract);
                        Bornes newBornes = new Bornes();

                        if(Bornes.isCorrectListBorne(bornes)){
                        // On ajoute des informations sur les bornes
                            Bornes.Borne lastB = null;
                            for(Bornes.Borne b :bornes)
                            {
                                b.setMultipleBorne(true);
                                b.setLinkId(uniqueID);
                                newBornes.addBorne(b);
                                lastB = b;
                            }
                            lastB.setLastMultipleBorne(true);
                            return newBornes;
                        }else{
                            throw new Exceptions.ExceptionCds();
                        }
                    }else{
                        Bornes.Borne b = stringToBorne(strToExtract);
                        Bornes singleElement = new Bornes();
                        singleElement.addBorne(b);
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

    public static boolean isCdsMultiLine(String line1) {
        // Il faut qu'il y a CDS dans la ligne et que la dernier caractère soit une virgule ,
        Pattern p = Pattern.compile(" *CDS +.*,$");
        return p.matcher(line1).find();
    }

    public static boolean isEndCdsMultiLine(String reconstructLine) {
        Pattern p = Pattern.compile("\\)$");
        return p.matcher(reconstructLine).find();
    }

    public static String cdsMultiLineToString(List<String> multiLine) throws Exceptions.ExceptionPatternLine {
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
                    throw new Exceptions.ExceptionPatternLine();
                }
            }
        }
        return fullLine;
    }

}
