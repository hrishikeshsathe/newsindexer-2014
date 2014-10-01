package edu.buffalo.cse.irf14.analysis.analyzer;

import edu.buffalo.cse.irf14.analysis.Analyzer;
import edu.buffalo.cse.irf14.analysis.TokenStream;
import edu.buffalo.cse.irf14.analysis.TokenizerException;

public class CategoryAnalyzer implements Analyzer {

	TokenStream ts;
	
	public CategoryAnalyzer(TokenStream stream)
	{
		ts=stream;
	}
	@Override
	public boolean increment() throws TokenizerException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public TokenStream getStream() {
		// TODO Auto-generated method stub
		return ts;
	}

}
