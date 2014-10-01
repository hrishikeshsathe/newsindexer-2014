package edu.buffalo.cse.irf14.analysis.analyzer;

import edu.buffalo.cse.irf14.analysis.Analyzer;
import edu.buffalo.cse.irf14.analysis.CapitalizationFilter;
import edu.buffalo.cse.irf14.analysis.SpecialCharFilter;
import edu.buffalo.cse.irf14.analysis.SymbolFilter;
import edu.buffalo.cse.irf14.analysis.TokenStream;
import edu.buffalo.cse.irf14.analysis.TokenizerException;

public class PlaceAnalyzer implements Analyzer {

	TokenStream ts;
	public PlaceAnalyzer(TokenStream stream){
		ts = stream;
	}
	@Override
	public boolean increment() throws TokenizerException {
		// TODO Auto-generated method stub
		CapitalizationFilter cf = new CapitalizationFilter(ts);
		while(cf.increment()){}
		cf.getStream().reset();
		SymbolFilter sf = new SymbolFilter(cf.getStream());
		while(sf.increment()){}
		sf.getStream().reset();
		SpecialCharFilter sc = new SpecialCharFilter(sf.getStream());
		while(sc.increment()){}
		sc.getStream().reset();
		ts = sc.getStream();
		return false;
	}

	@Override
	public TokenStream getStream() {
		// TODO Auto-generated method stub
		return ts;
	}

}
