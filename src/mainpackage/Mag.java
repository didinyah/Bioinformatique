package mainpackage;

public class Mag {

	public static boolean checkcds (String line) {
		String cds = "CDS";
		for (int i = 0; i < cds.length(); i++) {
			if (cds.charAt(i) != line.charAt(i)) {
				return false;
			}
		}
		return true;
	}
	
	public static boolean checkinit (String line) {
		String origin = "ORIGIN";
		for (int i = 0; i < origin.length(); i++) {
			if (origin.charAt(i) != line.charAt(i)) {
				return false;
			}
		}
		return true;
	}
	
	public static boolean checkend (String line) {
		String end = "//";
		for (int i = 0; i < end.length(); i++) {
			if (end.charAt(i) != line.charAt(i)) {
				return false;
			}
		}
		return true;
	}
	
}
