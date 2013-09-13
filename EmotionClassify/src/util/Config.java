package util;

public class Config {
	
	private static String encoding = "utf8";
	private static String dataDir = "data/";
	private static String resourceDir = "e:/stu-support/EmotionClassify/resource/";
	
	public static final String url = "http://[a-zA-Z/\\.0-9]*";
	public static final String halfurl = "www\\.[a-zA-Z0-9.]+";
	public static final String[] splitCharacters = {"~", ",", "¡£", "£¬", "£¡", "£¿", "?", "£º", "£»", "\n", "\n\n", "RETWEET", "!"};
	public static final String nousePunctuation = "([\\\\,\\.£¬¡££®¡¿¡¾£»~¡¢\\-¨D_\\*=+`£½£­¡«¡¥/¡¤>¡ï<X:|=¡ä\\(\\)£à]{2,})";
	public static final String usefulPunctuation = "([!£¡£¿?]{2,})";
	public static final String numbers = "[0-9]+";
	public static final String characters = "[a-zA-Z]+";
	
	public static final String hashtagRegex = "#.+?#";
	public static final String SINA_USER_NAME = "(.{2,20})"; 
	//"([A-Za-z0-9-_\u4e00-\u9fa5]{2,20})";
	public static final String retweet = "//( *?)@" + SINA_USER_NAME + "( *)[:£º\\-_)£©]";
	public static final String reply = "»Ø¸´( *?)@" + SINA_USER_NAME + "[:£º\\-_)£©]";
	public static final String atUser = "@" + SINA_USER_NAME + "[:£º \\-_)£©,£¬¡££»¡¢]";
	
	public static String getEncoding() {
		return encoding;
	}

	public static void setEncoding(String encoding) {
		Config.encoding = encoding;
	}
	
	public static void setDataDir(String dataDir) {
		Config.dataDir = dataDir;
	}

	public static String getDataDir() {
		return dataDir;
	}

	public static void setResourceDir(String resourceDir) {
		Config.resourceDir = resourceDir;
	}

	public static String getResourceDir() {
		return resourceDir;
	}
}
