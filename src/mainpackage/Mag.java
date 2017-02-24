package mainpackage;

public class Mag {
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
}
