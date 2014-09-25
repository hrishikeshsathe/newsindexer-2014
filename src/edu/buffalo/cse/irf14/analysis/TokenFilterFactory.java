/**
 * 
 */
package edu.buffalo.cse.irf14.analysis;


/**
 * Factory class for instantiating a given TokenFilter
 * @author nikhillo
 *
 */
public class TokenFilterFactory {
	private TokenFilterFactory(){
		
	}
	/**
	 * Static method to return an instance of the factory class.
	 * Usually factory classes are defined as singletons, i.e. 
	 * only one instance of the class exists at any instance.
	 * This is usually achieved by defining a private static instance
	 * that is initialized by the "private" constructor.
	 * On the method being called, you return the static instance.
	 * This allows you to reuse expensive objects that you may create
	 * during instantiation
	 * @return An instance of the factory
	 */
	public static TokenFilterFactory getInstance() {
		//TODO : YOU MUST IMPLEMENT THIS METHOD
		return new TokenFilterFactory();
	}
	
	/**
	 * Returns a fully constructed {@link TokenFilter} instance
	 * for a given {@link TokenFilterType} type
	 * @param type: The {@link TokenFilterType} for which the {@link TokenFilter}
	 * is requested
	 * @param stream: The TokenStream instance to be wrapped
	 * @return The built {@link TokenFilter} instance
	 * @throws TokenizerException 
	 */
	public TokenFilter getFilterByType(TokenFilterType type, TokenStream stream) throws TokenizerException {
		//TODO : YOU MUST IMPLEMENT THIS METHOD
		Accent ac = null;
		Stopword sw = null;
		SpecialChar sc = null;
		Symbol sm = null;
		NumberFilter nf = null;
		CapitalizationFilter cf = null;
		Stemmer st=null;
		DateFilter df = null;
		
		if(type==TokenFilterType.ACCENT)
		{
			ac = new Accent(stream);
			return ac;
		}
		else if(type==TokenFilterType.CAPITALIZATION)
		{
			cf = new CapitalizationFilter(stream);
			return cf;
		}
		else if(type==TokenFilterType.DATE)
		{
			df = new DateFilter(stream);
			return df;
		}
		else if(type==TokenFilterType.NUMERIC)
		{
			nf = new NumberFilter(stream);
			return nf;
		}
		else if(type==TokenFilterType.SPECIALCHARS)
		{
			sc = new SpecialChar(stream);
			return sc;
		}
		else if(type==TokenFilterType.STEMMER)
		{
			st = new Stemmer(stream);
			return st;
		}
		else if(type==TokenFilterType.STOPWORD){
			sw = new Stopword(stream);
			return sw;
		}
		else if(type==TokenFilterType.SYMBOL)
		{
			sm = new Symbol(stream);
			return sm;
		}
		else
			return null;
		
	}
}
