/**
 * 
 */
package edu.buffalo.cse.irf14.analysis;

import edu.buffalo.cse.irf14.analysis.utility.AnalysisUtility;

/**
 * @author Hrishikesh
 *
 */
public class StopwordFilter extends TokenFilter {

	
	public StopwordFilter(TokenStream stream) {
		super(stream);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see edu.buffalo.cse.irf14.analysis.Analyzer#increment()
	 */
	@Override
	public boolean increment() throws TokenizerException {
		// TODO Auto-generated method stub
		String[] tempArr = null;
		String tempStr = "";
		if(ts.hasNext()){
			tempArr = ts.next().getTermText().split(" ");
			for(int i=0;i<tempArr.length;i++){
				if(!AnalysisUtility.stopWords.contains(tempArr[i].toLowerCase())){
					tempStr+=tempArr[i]+" ";
				}	
			}
			tempStr = tempStr.trim();
			if(tempStr.length()==0)
				ts.remove();
			else
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
