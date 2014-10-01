package edu.buffalo.cse.irf14.analysis.analyzer;

import edu.buffalo.cse.irf14.analysis.AccentFilter;
import edu.buffalo.cse.irf14.analysis.Analyzer;
import edu.buffalo.cse.irf14.analysis.StopwordFilter;
import edu.buffalo.cse.irf14.analysis.TokenStream;
import edu.buffalo.cse.irf14.analysis.TokenizerException;

public class AuthorAnalyzer implements Analyzer {

	TokenStream ts;
	
	public AuthorAnalyzer(TokenStream stream)
	{
		ts=stream;
	}
	@Override
	public boolean increment() throws TokenizerException {
		// TODO Auto-generated method stub
		AccentFilter accTF = new AccentFilter(ts);
		while(accTF.increment()){}
		accTF.getStream().reset();
		StopwordFilter stopwordTF = new StopwordFilter(accTF.getStream());
		while(stopwordTF.increment()){}
		stopwordTF.getStream().reset();
		return false;
	}

	@Override
	public TokenStream getStream() {
		// TODO Auto-generated method stub
		return ts;
	}

}
