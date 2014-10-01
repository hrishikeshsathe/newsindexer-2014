/**
 * 
 */
package edu.buffalo.cse.irf14.analysis;

import edu.buffalo.cse.irf14.analysis.utility.AnalysisUtility;

/**
 * @author Hrishikesh
 *
 */
public class AccentFilter extends TokenFilter {

	public AccentFilter(TokenStream stream) {
		super(stream);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see edu.buffalo.cse.irf14.analysis.Analyzer#increment()
	 */
	@Override
	public boolean increment() throws TokenizerException {
			char[] tempArr = null;
			String tempStr = null;
			if(ts.hasNext()){
			tempArr = ts.next().getTermBuffer();
			tempStr = "";
			for(int i=0;i<tempArr.length;i++){
				if(AnalysisUtility.accentWords.containsKey(tempArr[i])){
					tempStr += AnalysisUtility.accentWords.get(tempArr[i]);
				}
				else
					tempStr += String.valueOf(tempArr[i]);
			}
			ts.getCurrent().setTermText(tempStr);
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
