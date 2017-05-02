package mainpackage;

import java.io.File;

public class Configuration {
	public static String DIR_SEPARATOR = File.separator;
	public static Boolean OPTION_DL_EUKARYOTES = false;
	public static Boolean OPTION_DL_VIRUSES = false;
	public static Boolean OPTION_DL_PROKARYOTES = false;
	public static Boolean OPTION_DL_KEEPFILES = false;
	public static Boolean OPTION_ARCHIVE_FILES = false;
	public static String RESULTS_FOLDER = System.getProperty("user.dir")+ Configuration.DIR_SEPARATOR +"Results";
}
