package edu.buffalo.cse.irf14.analysis.analyzer;

import edu.buffalo.cse.irf14.analysis.AccentFilter;
import edu.buffalo.cse.irf14.analysis.Analyzer;
import edu.buffalo.cse.irf14.analysis.CapitalizationFilter;
import edu.buffalo.cse.irf14.analysis.DateFilter;
import edu.buffalo.cse.irf14.analysis.NumberFilter;
import edu.buffalo.cse.irf14.analysis.SpecialCharFilter;
import edu.buffalo.cse.irf14.analysis.StemmerFilter;
import edu.buffalo.cse.irf14.analysis.StopwordFilter;
import edu.buffalo.cse.irf14.analysis.SymbolFilter;
import edu.buffalo.cse.irf14.analysis.TokenStream;
import edu.buffalo.cse.irf14.analysis.TokenizerException;

public class TermAnalyzer implements Analyzer {
	TokenStream ts;
	
	
	public TermAnalyzer(TokenStream stream){
		ts = stream;
	}
	
	@Override
	public boolean increment() throws TokenizerException {
		// TODO Auto-generated method stub
		CapitalizationFilter capTF = new CapitalizationFilter(ts);
		while(capTF.increment()){}
		capTF.getStream().reset();
		AccentFilter accTF = new AccentFilter(capTF.getStream());
		while(accTF.increment()){}
		accTF.getStream().reset();
		SymbolFilter symbolTF = new SymbolFilter(accTF.getStream());
		while(symbolTF.increment()){}
		symbolTF.getStream().reset();
		DateFilter dateTF = new DateFilter(symbolTF.getStream());
		while(dateTF.increment()){}
		dateTF.getStream().reset();
		NumberFilter numberTF = new NumberFilter(dateTF.getStream());
		while(numberTF.increment()){}
		numberTF.getStream().reset();
		SpecialCharFilter specialcharTF = new SpecialCharFilter(numberTF.getStream());
		while(specialcharTF.increment()){}
		specialcharTF.getStream().reset();
		StemmerFilter stemmerTF = new StemmerFilter(specialcharTF.getStream());
		while(stemmerTF.increment()){}
		stemmerTF.getStream().reset();
		StopwordFilter stopwordTF = new StopwordFilter(stemmerTF.getStream());
		while(stopwordTF.increment()){}
		stopwordTF.getStream().reset();
		ts = stemmerTF.getStream();
		return false;
	}

	@Override
	public TokenStream getStream() {
		// TODO Auto-generated method stub
		return ts;
	}

}
