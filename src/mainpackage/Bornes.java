package mainpackage;

import javafx.collections.transformation.SortedList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * Created by SandrosLaptop on 15/03/2017.
 */
// List de bornes en forme de classe pour avoir des conditions de test etc...
public class Bornes {



    private List<Borne> list = new ArrayList<Borne>();

    public void addBorne(Borne b) throws Exceptions.ExceptionBorne {
        // TODO Vérification si on peut ajouter la borne
        if(b.isCorrectBorne()){
            list.add(b);
            // On trie au cas ou
            Collections.sort(list);
        }else{
            throw new Exceptions.ExceptionBorne();
        }

    }

    public void fusion(Bornes bs) throws Exceptions.ExceptionBorne {

        for(Borne b : bs.getList()){
            addBorne(b); // est vérifier par addBorne
        }
    }


    public static boolean isCorrectListBorne(List<Borne> list) {
        if(!list.isEmpty()){
            Borne lastBorne = null;
            for(Borne b: list){
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


    public List<Borne> getList() {
        return list;
    }

    public void setList(List<Borne> list) {
        this.list = list;
    }

    public void clear() {
        this.list.clear();
    }

    public int size() {
        return this.list.size();
    }



    @Override
    public String toString() {
        return "Bornes:"+list.toString();
    }

    public List<Bornes.Borne> extractStartBornesFromLine(int i, int j)  {
        List<Bornes.Borne> tmp = new ArrayList<Borne>();

        for(Borne b : list){
            if(b.getBorninf() >= i && b.getBorninf() < j){
                tmp.add(b);
            }
        }
        return tmp;
    }

    public List<Bornes.Borne> extractEndBornesFromLine(int i, int j)  {
        List<Bornes.Borne> tmp = new ArrayList<Borne>();

        for(Borne b : list){
            if(b.getBornsup() >= i && b.getBornsup() < j){
                tmp.add(b);
            }
        }
        return tmp;
    }


    public HashMap<Borne,String> initMultipleCdsStr() {
        HashMap<Borne,String> tmp = new HashMap<Bornes.Borne,String>();
        for(Borne b : list){
            tmp.put(b,"");
        }
        return tmp;
    }

    public static class Borne  implements Comparable<Borne> {


        private Integer borninf;
        private Integer bornsup;
        private boolean complement; // savoir si la borne est complement
        private boolean multipleBorne; // savoir si c'est une borne d'une jointure
        private String  linkId; // l'id de lien avec les autres borne de la même jointure (todo créer un id plus complexe uniqid)

        // Des information temporaires permettant de savoir l'indice devant la ligne (utile pour le découpage pendant la lecture)
        private Integer temporyLineBornInf;
        private Integer temporyLineBornSup;

        //Information pour les multipleBorne si cette borne est la dernière
        private boolean lastMultipleBorne;

        public Borne() {
            // Variable par défaut
            complement = false;
            multipleBorne = false;
            linkId = "error"; // -1 signifie qu'il n'y a pas de lien
            lastMultipleBorne = false;
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

        public Integer getBornsup() {
            return bornsup;
        }
        public Integer getBorninf() {
            return borninf;
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
            return borninf > b.bornsup;
        }

        public boolean isLessThan(Borne b){
            return bornsup < b.borninf;
        }

        public boolean isEmptyIntersection(Borne b){
            return isLessThan(b) || isGreaterThan(b);
        }

        public int compareTo(Borne o) {
            return isGreaterThan(o) ? 1 : isLessThan(o) ? -1 : 0;
        }

        public Integer getTemporyLineBornInf() {
            return temporyLineBornInf;
        }

        public void setTemporyLineBornInf(Integer temporyLineBornInf) {
            this.temporyLineBornInf = temporyLineBornInf;
        }

        public Integer getTemporyLineBornSup() {
            return temporyLineBornSup;
        }

        public void setTemporyLineBornSup(Integer temporyLineBornSup) {
            this.temporyLineBornSup = temporyLineBornSup;
        }

        public boolean isLastMultipleBorne() {
            return lastMultipleBorne;
        }

        public void setLastMultipleBorne(boolean lastMultipleBorne) {
            this.lastMultipleBorne = lastMultipleBorne;
        }
    }
}
