package edu.buffalo.cse.irf14.analysis.analyzer;

import edu.buffalo.cse.irf14.analysis.Analyzer;
import edu.buffalo.cse.irf14.analysis.DateFilter;
import edu.buffalo.cse.irf14.analysis.TokenStream;
import edu.buffalo.cse.irf14.analysis.TokenizerException;

public class NewsDateAnalyzer implements Analyzer {

	TokenStream ts;
	public NewsDateAnalyzer(TokenStream stream) {
		// TODO Auto-generated constructor stub
		ts = stream;
	}

	@Override
	public boolean increment() throws TokenizerException {
		// TODO Auto-generated method stub
		DateFilter df = new DateFilter(ts);
		while(df.increment()){}
		df.getStream().reset();
		ts = df.getStream();
		return false;
	}

	@Override
	public TokenStream getStream() {
		// TODO Auto-generated method stub
		return ts;
	}

}
