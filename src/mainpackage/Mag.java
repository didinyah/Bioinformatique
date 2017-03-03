package mainpackage;

public class Mag extends Analyzer {
	public static String Complement (String string1){
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
	
	public static boolean checkComplement (String line) {
        String compl = "complement(";
        for (int i = 0; i < compl.length(); i++) {
            if (compl.charAt(i) != line.charAt(i)) {
                return false;
            }
        }
        if (line.charAt(line.length()-1)!=')') {
        	return false;
        }
        return true;
    }
	
	public static boolean checkNb (String[] liste) {
		if (liste.length!=2) {
			//trop de nombres
			return false;
		}
		if (Integer.valueOf(liste[0]) >= Integer.valueOf(liste[1])) {
			//pas le bon ordre
			return false;
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
	    	line=line.trim();
	    	String[] split1 = line.split("             ");
	    	if (checkCds(split1[0])!=true) {
	    		return false;
	    	}
	    	if (checkComplement(split1[1])!=true) {
	    		return false;
	    	}
	    	split1[1]=split1[1].replace("complement(", "");
	    	split1[1]=split1[1].replace(")", "");
	    	String[] split2 = split1[1].split("\\..");
	    	if (checkNb(split2)!=true){
	    		return false;
	    	}
	        return true;
	    }
	
}



