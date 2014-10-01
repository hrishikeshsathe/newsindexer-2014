/**
 * 
 */
package edu.buffalo.cse.irf14.analysis;

import edu.buffalo.cse.irf14.analysis.analyzer.AuthorAnalyzer;
import edu.buffalo.cse.irf14.analysis.analyzer.AuthorOrgAnalyzer;
import edu.buffalo.cse.irf14.analysis.analyzer.CategoryAnalyzer;
import edu.buffalo.cse.irf14.analysis.analyzer.NewsDateAnalyzer;
import edu.buffalo.cse.irf14.analysis.analyzer.PlaceAnalyzer;
import edu.buffalo.cse.irf14.analysis.analyzer.TermAnalyzer;
import edu.buffalo.cse.irf14.document.FieldNames;

/**
 * @author nikhillo
 * This factory class is responsible for instantiating "chained" {@link Analyzer} instances
 */
public class AnalyzerFactory {
	
	private AnalyzerFactory(){
		
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
	public static AnalyzerFactory getInstance() {
		//TODO: YOU NEED TO IMPLEMENT THIS METHOD
		return new AnalyzerFactory();
	}
	
	/**
	 * Returns a fully constructed and chained {@link Analyzer} instance
	 * for a given {@link FieldNames} field
	 * Note again that the singleton factory instance allows you to reuse
	 * {@link TokenFilter} instances if need be
	 * @param name: The {@link FieldNames} for which the {@link Analyzer}
	 * is requested
	 * @param TokenStream : Stream for which the Analyzer is requested
	 * @return The built {@link Analyzer} instance for an indexable {@link FieldNames}
	 * null otherwise
	 */
	public Analyzer getAnalyzerForField(FieldNames name, TokenStream stream) {
		//TODO : YOU NEED TO IMPLEMENT THIS METHOD
		if(name.equals(FieldNames.TITLE) || name.equals(FieldNames.CONTENT)){
			TermAnalyzer ta = new TermAnalyzer(stream);
			return ta;
		}
		else if(name.equals(FieldNames.AUTHOR))
		{
			AuthorAnalyzer aa = new AuthorAnalyzer(stream);
			return aa;
		}
		else if(name.equals(FieldNames.AUTHORORG)){
			AuthorOrgAnalyzer aoa = new AuthorOrgAnalyzer(stream);
			return aoa;
		}
		else if(name.equals(FieldNames.PLACE)){
			PlaceAnalyzer pa = new PlaceAnalyzer(stream);
			return pa;
		}
		else if(name.equals(FieldNames.CATEGORY))
		{
			CategoryAnalyzer ca=new CategoryAnalyzer(stream);
			return ca;
		}
		else if(name.equals(FieldNames.NEWSDATE))
		{
			NewsDateAnalyzer na=new NewsDateAnalyzer(stream);
			return na;
		}
		else
			return null;
	}
}
