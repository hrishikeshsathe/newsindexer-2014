/**
 * 
 */
package edu.buffalo.cse.irf14.analysis;

import edu.buffalo.cse.irf14.analysis.utility.AnalysisUtility;

/**
 * @author Hrishikesh
 *
 */
public class SpecialChar extends TokenFilter {

	public SpecialChar(TokenStream stream) {
		super(stream);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see edu.buffalo.cse.irf14.analysis.Analyzer#increment()
	 */
	@Override
	public boolean increment() throws TokenizerException {
		// TODO Auto-generated method stub
		String temp = null;
		if(ts.hasNext()){
			temp = ts.next().getTermText();
			temp = temp.replaceAll(AnalysisUtility.SPECIALCHAR, "");
			temp = temp.replaceAll(AnalysisUtility.HYPHEN,"$1$2");
			ts.getCurrent().setTermText(temp);
			return true;
		}
		else
			return false;
	}

	/* (non-Javadoc)
	 * @see edu.buffalo.cse.irf14.analysis.Analyzer#getStream()
	 */
	@Override
	public TokenStream getStream() {
		// TODO Auto-generated method stub
		return ts;
	}

}
