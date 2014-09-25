package edu.buffalo.cse.irf14.analysis.utility;

import java.util.HashMap;
import java.util.HashSet;

public class AnalysisUtility {



	public static HashSet<String> stopWords;
	public static HashMap<Character,String> accentWords;
	public static HashMap<String,String> contractions;
	public static HashMap<String,String> monthNumber;
	public static final String SPECIALCHAR = "[^\\.\\!\\?A-Za-z0-9\\-]*";
	public static final String HYPHEN = "([A-Za-z]+)\\-([A-Za-z]+)";
	public static final String DATEPATTERN = "(?i)(Jan(uary)?|Feb(ruary)?|Mar(c(h)?)?|Apr(i(l)?)?|May|Jun(e)?|Jul(y)?|Aug(ust)?|"
			+ "Sep(t(ember)?)?|Oct(ober)?|Nov(ember)?|Dec(ember)?)";
	public static final String TIMEPATTERN = "(?:(?:([01]?\\d|2[0-3]):)?([0-5]?\\d):)?([0-5]?\\d)([am|pm|AM|PM]*)\\.*";
	
	static
	{
		getStopWords();
		getAccentWords();
		getContractions();
		getMonthNumber();
	}

	private static void getStopWords(){
		String[] word_list = "a,able,about,across,after,all,almost,also,am,among,an,and,any,are,as,at,be,because,been,but,by,can,cannot,could,dear,did,do,does,either,else,ever,every,for,from,get,got,had,has,have,he,her,hers,him,his,how,however,i,if,in,into,is,it,its,just,least,let,like,likely,may,me,might,most,must,my,neither,no,nor,not,of,off,often,on,only,or,other,our,own,rather,said,say,says,she,should,since,so,some,than,that,the,their,them,then,there,these,they,this,tis,to,too,twas,us,wants,was,we,were,what,when,where,which,while,who,whom,why,will,with,would,yet,you,your".split(","); 
		stopWords = new HashSet<String>();
		for(int i=0;i<word_list.length;i++){
			stopWords.add(word_list[i]);
		}
	}

	private static void getMonthNumber() {
		// TODO Auto-generated method stub
		monthNumber = new HashMap<String,String>();
		monthNumber.put("jan", "01");
		monthNumber.put("feb", "02");
		monthNumber.put("mar", "03");
		monthNumber.put("apr", "04");
		monthNumber.put("may", "05");
		monthNumber.put("jun", "06");
		monthNumber.put("jul", "07");
		monthNumber.put("aug", "08");
		monthNumber.put("sep", "09");
		monthNumber.put("oct", "10");
		monthNumber.put("nov", "11");
		monthNumber.put("dec", "12");
	}

	private static void getContractions() {
		// TODO Auto-generated method stub
		contractions = new HashMap<String,String>();
		contractions.put("won't", "will not");
		contractions.put("shan't", "shall not");
		contractions.put("let's", "let us");
		contractions.put("that's", "that is");
		contractions.put("it's", "it is");
		contractions.put("'em", "them");
	}

	private static void getAccentWords(){
		accentWords = new HashMap<Character,String>(); 
		accentWords.put('À',"A");
		accentWords.put('Á',"A");
		accentWords.put('Â',"A");
		accentWords.put('Ã',"A");
		accentWords.put('Ä',"A");
		accentWords.put('Å',"A");
		accentWords.put('Æ',"AE");
		accentWords.put('Ç',"C");
		accentWords.put('È',"E");
		accentWords.put('É',"E");
		accentWords.put('Ê',"E");
		accentWords.put('Ë',"E");
		accentWords.put('Ì',"I"); 
		accentWords.put('Í',"I");
		accentWords.put('Î',"I");
		accentWords.put('Ï',"I");
		accentWords.put('Ĳ',"IJ");
		accentWords.put('Ð',"D");
		accentWords.put('Ñ',"N");
		accentWords.put('Ò',"O");
		accentWords.put('Ó',"O");
		accentWords.put('Ô',"O");
		accentWords.put('Õ',"O");
		accentWords.put('Ö',"O");
		accentWords.put('Ø',"O");
		accentWords.put('Œ',"OE");
		accentWords.put('Þ',"TH");
		accentWords.put('Ù',"U");
		accentWords.put('Ú',"U");
		accentWords.put('Û',"U");
		accentWords.put('Ü',"U");
		accentWords.put('Ý',"Y");
		accentWords.put('Ÿ',"Y");
		accentWords.put('à',"a");
		accentWords.put('á',"a");
		accentWords.put('â',"a");
		accentWords.put('ã',"a");
		accentWords.put('ä',"a");
		accentWords.put('å',"a");
		accentWords.put('æ',"ae");
		accentWords.put('ç',"c");
		accentWords.put('è',"e");
		accentWords.put('é',"e");
		accentWords.put('ê',"e");
		accentWords.put('ë',"e");
		accentWords.put('ì',"i");
		accentWords.put('í',"i");
		accentWords.put('î',"i");
		accentWords.put('ï',"i");
		accentWords.put('ĳ',"ij");
		accentWords.put('ð',"d");
		accentWords.put('ñ',"n");
		accentWords.put('ò',"o");
		accentWords.put('ó',"o");
		accentWords.put('ô',"o");
		accentWords.put('õ',"o");
		accentWords.put('ö',"o");
		accentWords.put('ø',"o");
		accentWords.put('œ',"oe");
		accentWords.put('ß',"ss");
		accentWords.put('þ',"th");
		accentWords.put('ù',"u");
		accentWords.put('ú',"u");
		accentWords.put('û',"u");
		accentWords.put('ü',"u");
		accentWords.put('ý',"y");
		accentWords.put('ÿ',"y");
		accentWords.put('ﬀ',"ff");
		accentWords.put('ﬁ',"fi");
		accentWords.put('ﬂ',"fl");

	}
}
