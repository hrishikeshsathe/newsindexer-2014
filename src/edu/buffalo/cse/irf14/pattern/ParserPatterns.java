package edu.buffalo.cse.irf14.pattern;

public class ParserPatterns {

	public static final String AUTHOR_PATTERN = "(?i)<AUTHOR>[\\s]+by(.+?)</AUTHOR>";
	public static final String AUTHOR_ORG_PATTERN = "[\\s](.+?),[\\s](\\w+)";
	public static final String DATE_PATTERN = "(?i)(Jan(uary)?|Feb(ruary)?|Mar(c(h)?)?|Apr(i(l)?)?|May|Jun(e)?|Jul(y)?|Aug(ust)?|"
			+ "Sep(t(ember)?)?|Oct(ober)?|Nov(ember)?|Dec(ember)?)\\s+[0-9]{1,2}";
	
	
}
