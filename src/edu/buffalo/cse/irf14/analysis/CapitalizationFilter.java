/**
 * 
 */
package edu.buffalo.cse.irf14.analysis;


/**
 * @author Hrishikesh
 *
 */
public class CapitalizationFilter extends TokenFilter {

	public CapitalizationFilter(TokenStream stream) {
		super(stream);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see edu.buffalo.cse.irf14.analysis.Analyzer#increment()
	 */
	@Override
	public boolean increment() throws TokenizerException {
		// TODO Auto-generated method stub
		boolean isAllCaps=false;
		boolean isCamelCased=false;
		boolean isLastWord=false;
		String temp = null;

		if(isAllCapsSentence())
		{
			toLowerCaseSentence();
			return false;
		}
		ts.reset();
		temp = ts.next().getTermText();
		if(isCamelCased(temp))
		{
			ts.getCurrent().setTermText(temp.toLowerCase());
		}
		while(ts.hasNext())
		{
			isLastWord=temp.endsWith(".");
			while(isLastWord && ts.hasNext())
			{
				String temp2=ts.next().getTermText();
				isAllCaps=isAllCaps(temp2);
				isCamelCased=isCamelCased(temp2);
				if(isCamelCased && !isAllCaps)
				{
					temp2=temp2.toLowerCase();
					ts.getCurrent().setTermText(temp2);
				}
				isLastWord=false;
			}
			ts.reset();
			String temp3=null;

			while(ts.hasNext())
			{
				temp3=ts.next().getTermText();

				if(isCamelCased(temp3))
				{

					int index=ts.position;
					if(ts.hasNext())
					{
					String str=ts.next().getTermText();

					if(isCamelCased(str))
					{
						temp3=temp3+" "+str;

						ts.remove();
					}
					}
					ts.tokenStream.get(index).setTermText(temp3);
				}
			}
		}
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

	public static boolean isAllCaps(String str)
	{
		if(str.matches("[A-Z]{2,}"))
		{
			return true;
		}
		else
			return false;
	}
	public static boolean isCamelCased(String str)
	{
		if(str.matches("[A-Z]+[a-z]+[A-Z]*"))
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public boolean isAllCapsSentence()
	{
		TokenStream tStream=ts;
		while(tStream.hasNext())
		{
			String str=tStream.next().getTermText();
			
		for (int i=0;i<str.length();i++)
		{
			if(Character.isLetter(str.charAt(i)) && Character.isLowerCase(str.charAt(i)))
			{
				return false;
			}
		}
		}
		return true;
		
	}
	public void toLowerCaseSentence()
	{

		ts.reset();
		while(ts.hasNext())
		{
			String str=ts.next().getTermText();
			char[] strArr=str.toCharArray();
		for (int i=0;i<strArr.length;i++)
		{
			if(Character.isLetter(strArr[i]) && Character.isUpperCase(strArr[i]))
			{
				strArr[i]=Character.toLowerCase(strArr[i]);
				
				
			}
		}
		
		ts.getCurrent().setTermBuffer(strArr);
		}
		ts.reset();
		
	
	}
}
