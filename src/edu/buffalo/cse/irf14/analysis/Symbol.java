package edu.buffalo.cse.irf14.analysis;

import edu.buffalo.cse.irf14.analysis.utility.AnalysisUtility;

public class Symbol extends TokenFilter {

	public Symbol(TokenStream stream) {
		super(stream);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean increment() throws TokenizerException {
		// TODO Auto-generated method stub
		String temp = null;
		if(ts.hasNext()){
			temp = ts.next().getTermText();
			temp = removeContractions(temp);
			temp = temp.replaceAll("[\\.\\?\\!\\-]+$", "");
			temp = temp.replaceAll("^[\\-]+", "");
			temp = temp.replaceAll("[\\'\\,]", "");
			if(temp.matches("[\\.\\?\\!\\-]+"))
				ts.remove();
			else if(temp.matches("[a-zA-Z]+\\-[a-zA-Z]+[[\\-]*[a-zA-Z]+\\-[a-zA-Z]]*")){
				temp = temp.replaceAll("\\-"," ");
				ts.getCurrent().setTermText(temp);
			}
			else
				ts.getCurrent().setTermText(temp);
			return true;
		}
		else
			return false;
	}

	@Override
	public TokenStream getStream() {
		// TODO Auto-generated method stub
		return ts;
	}
	
	public static String removeContractions(String inputString) { 

		if(AnalysisUtility.contractions.containsKey(inputString)){
				
			return AnalysisUtility.contractions.get(inputString);
		}
		else{
	    inputString = inputString.replaceAll("n't", " not");
	    inputString = inputString.replaceAll("'re", " are");
	    inputString = inputString.replaceAll("'m", " am");
	    inputString = inputString.replaceAll("'ll", " will");
	    inputString = inputString.replaceAll("'ve", " have");
	    inputString = inputString.replaceAll("'d", " would");
	    inputString = inputString.replaceAll("'s", "");

	    return inputString;
	    }
	}

}
