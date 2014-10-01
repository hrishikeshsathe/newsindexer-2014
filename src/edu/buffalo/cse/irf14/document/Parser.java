/**
 * 
 */
package edu.buffalo.cse.irf14.document;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import edu.buffalo.cse.irf14.pattern.ParserPatterns;

/**
 * @author nikhillo
 * Class that parses a given file into a Document
 */
public class Parser {
	/**
	 * Static method to parse the given file into the Document object
	 * @param filename : The fully qualified filename to be parsed
	 * @return The parsed and fully loaded Document object
	 * @throws ParserException In case any error occurs during parsing
	 * @throws FileNotFoundException 
	 */
	public static Document parse(String filename) throws ParserException {
		// TODO YOU MUST IMPLEMENT THIS
		Document d = new Document();
		String[] temp = null;
		BufferedReader br = null;

		try{

			if(null!=filename)
			{

				File f=new File(filename);
				if(!f.exists() || f.isDirectory())
				{
					throw new ParserException(); 
				}
				temp = filename.split("[\\"+File.separator+"]");

				d.setField(FieldNames.FILEID, temp[temp.length-1]); //FileID
				d.setField(FieldNames.CATEGORY, temp[temp.length-2]); //Category

				br = new BufferedReader(new FileReader(filename));	
			}
			else
				throw new ParserException();
		}

		catch(Exception e){
			throw new ParserException();
		}

		/* Get other information */
		getTitleInformation(br,d);
		getAuthorInformation(br,d);

		return d;
	}

	/**
	 * 
	 * @param br : BufferedReader for file handling
	 * @param d : Document d for storing parsed information
	 * @throws ParserException 
	 */
	public static void getTitleInformation(BufferedReader br, Document d) throws ParserException{
		String line = null;
		try{
			//			Skip whitespace
			while((line = br.readLine()) != null){
				line = line.trim();
				if(line.equals("") || line.equals("\n") || line.equals(" ")){
					continue;
				}

				d.setField(FieldNames.TITLE, line);
				break;
			}
		}
		catch(Exception e){
			throw new ParserException();
		}
	}
	/**
	 * @param br : BufferedReader for file handling
	 * @param d : Document d for storing parsed information
	 * @throws ParserException 
	 */
	public static void getAuthorInformation(BufferedReader br, Document d) throws ParserException{
		String line = null;

		Pattern authorPattern = Pattern.compile(ParserPatterns.AUTHOR_PATTERN);
		Pattern authorOrgPattern = Pattern.compile(ParserPatterns.AUTHOR_ORG_PATTERN);

		Matcher authorMatcher;
		Matcher authorOrgMatcher;

		try
		{
			//			Skip whitespace
			while((line = br.readLine()) != null){
				line = line.trim();
				if(line.equals("") || line.equals("\n") || line.equals(" ")){ 
					continue;
				}//end of if

				authorMatcher = authorPattern.matcher(line);
				if(authorMatcher.find()){ //if1
					authorOrgMatcher = authorOrgPattern.matcher(authorMatcher.group(1));
					if(authorOrgMatcher.find()){ //if2
						d.setField(FieldNames.AUTHOR, authorOrgMatcher.group(1)); //Author
						d.setField(FieldNames.AUTHORORG, authorOrgMatcher.group(2)); //Author Organisation
					}//end of if2
					else{
						d.setField(FieldNames.AUTHOR, authorMatcher.group(1)); //Author
						d.setField(FieldNames.AUTHORORG, ""); //Author Organization

					}//end of else
					//					call getPlaceInformation where author is not found
					getDateInformation(br,d,null);
					break;
				}//end of if1
				else
				{
					getDateInformation(br,d,line);		
					break;
				}
			}//end of while
		}//end of try
		catch(Exception e){
			throw new ParserException();
		}//end of catch
	}//end of function

	/**
	 * @param br : BufferedReader for file handling
	 * @param d : Document d for storing parsed information
	 * @param line : Line from which place info is to be extracted
	 * @throws ParserException 
	 */
	public static void getDateInformation(BufferedReader br, Document d, String line) throws ParserException
	{
		Pattern datePattern = Pattern.compile(ParserPatterns.DATE_PATTERN);	
		Matcher dateMatcher = null;
		String date = null;
		try{
			if(line==null){	//if1
				while((line = br.readLine()) != null){
					line = line.trim();
					if(line.equals("") || line.equals("\n") || line.equals(" ")){	//if2
						continue;
					}//end of if2

					dateMatcher = datePattern.matcher(line);

					if(dateMatcher.find()){ //if3
						date = dateMatcher.group(0);
						d.setField(FieldNames.NEWSDATE, date);
						getPlace(line,d,dateMatcher.group(0));
					}	//end of if3
					break;
				}//end of while
			}//end of if1
			else
			{
				dateMatcher = datePattern.matcher(line);
				if(dateMatcher.find()){
					date = dateMatcher.group(0);
					d.setField(FieldNames.NEWSDATE, date);
					getPlace(line,d,dateMatcher.group(0));
				}//end of if
			}//end of else
		}//end of try
		catch(Exception e){
			throw new ParserException();
		}//end of catch
		getContent(line,br,d,date);

	}//end of fucnction

	/**
	 * @param line : Line from which place info is to be extracted
	 * @param d : Document d for storing parsed information
	 * @param date : date found in the line
	 */

	public static void getPlace(String line, Document d, String date){
		char[] subArr = line.toCharArray();
		int index = line.indexOf(date);
		String place = "";
		for (int i=0;i<(index-2);i++)
		{
			place=place+subArr[i];
		}
		d.setField(FieldNames.PLACE, place);

	}//end of function

	/**
	 * 
	 * @param line : Line from which place info is to be extracted
	 * @param br : BufferedReader for file handling
	 * @param d : Document d for storing parsed information
	 * @param date : date found in the line
	 * @throws ParserException 
	 */
	public static void getContent(String line,BufferedReader br, Document d, String date) throws ParserException{

		char[] subArr = line.toCharArray();
		int index = 0;
		String content = "";

		if(date!=null){
			index = line.indexOf(date)+date.length();
		}

		for (int i=index;i<line.length();i++)
		{
			content=content+subArr[i];
		}
		content+=" ";
		try{

			while((line=br.readLine())!=null){
				content+=line+" ";
			}
			d.setField(FieldNames.CONTENT, content);
		}
		catch(Exception e){
			throw new ParserException();
		}
	}//end of function
}//end of class
