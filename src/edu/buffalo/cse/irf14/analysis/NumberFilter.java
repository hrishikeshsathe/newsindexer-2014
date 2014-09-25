package edu.buffalo.cse.irf14.analysis;

public class NumberFilter extends TokenFilter {

	public NumberFilter(TokenStream stream) {
		super(stream);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean increment() throws TokenizerException {
		// TODO Auto-generated method stub
		String temp = null;
		if(ts.hasNext()){
			temp = ts.next().getTermText();
			temp = temp.replaceAll("[0-9]+\\,*\\.*[0-9]*", "");
			System.out.println(temp);
			if(temp.length()==0)
			{
				ts.remove();
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

}
