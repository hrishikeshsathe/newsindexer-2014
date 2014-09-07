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

import edu.buffalo.cse.irf14.Runner;

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

		temp = filename.split("[\\"+File.separator+"]");
		d.setField(FieldNames.FILEID, temp[temp.length-1]); //FileID
		d.setField(FieldNames.CATEGORY, temp[temp.length-2]); //Category

		try{
			br = new BufferedReader(new FileReader(filename));	
		}
		catch(Exception e){

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
	 */
	public static void getTitleInformation(BufferedReader br, Document d){

		String line = null;
		Pattern titlePattern = Pattern.compile("[a-z]+");
		Matcher titleMatcher;

		try{
			while((line = br.readLine()) != null){
				line = line.trim();
				if(line.equals("") || line.equals("\n") || line.equals(" ")){
					continue;
				}

				titleMatcher = titlePattern.matcher(line);
				if(titleMatcher.find() != true){
					d.setField(FieldNames.TITLE, line);
					break;
				}
				else{
					break;
				}
			}
		}
		catch(Exception e){

		}
	}
	/**
	 * @param br : BufferedReader for file handling
	 * @param d : Document d for storing parsed information
	 */
	public static void getAuthorInformation(BufferedReader br, Document d){
		String line = null;

		Pattern authorPattern = Pattern.compile("(?i)<AUTHOR>[\\s]+by(.+?)</AUTHOR>");
		Pattern authorOrgPattern = Pattern.compile("[\\s](.+?),[\\s](\\w+)");

		Matcher authorMatcher;
		Matcher authorOrgMatcher;

		try
		{
			while((line = br.readLine()) != null){
				line = line.trim();
				if(line.equals("") || line.equals("\n") || line.equals(" ")){
					continue;
				}

				authorMatcher = authorPattern.matcher(line);
				if(authorMatcher.find()){ //if1
					authorOrgMatcher = authorOrgPattern.matcher(authorMatcher.group(1));
					if(authorOrgMatcher.find()){ //if2
						Runner.count2++;
						d.setField(FieldNames.AUTHOR, authorOrgMatcher.group(1)); //Author
						d.setField(FieldNames.AUTHORORG, authorOrgMatcher.group(2)); //Author Organisation
					}//end of if2
					else{
						Runner.count1++;
						d.setField(FieldNames.AUTHOR, authorMatcher.group(1)); //Author
						d.setField(FieldNames.AUTHORORG, ""); //Author Organization
					}//end of else
					getPlaceInformation(br,d,null);
					break;
				}//end of if1
				else
				{
					getPlaceInformation(br,d,line);
					break;
				}
			}//end of while
		}//end of try
		catch(Exception e){

		}//end of catch
	}

	/**
	 * @param br : BufferedReader for file handling
	 * @param d : Document d for storing parsed information
	 * @param line : Line from which place info is to be extracted
	 */
	public static void getPlaceInformation(BufferedReader br, Document d, String line)
	{
		try{
			if(line==null){	//if1
				while((line = br.readLine()) != null){
					line = line.trim();
					if(line.equals("") || line.equals("\n") || line.equals(" ")){	//if2
						continue;
					}//end of if2
					if(line.contains("-")){
						getPlaceAndDate(line,d);
					}
					break;
				}
			}
			else
			{
				if(line.contains("-")){
					getPlaceAndDate(line,d);
				}
				Runner.count++;}
		}
		catch(Exception e){

		}

	}

	/**
	 * @param line : Line from which place info is to be extracted
	 * @param d : Document d for storing parsed information
	 */

	public static void getPlaceAndDate(String line, Document d){

		String subStr = line.substring(0,line.indexOf('-'));
		String subArr[] = subStr.split(",");
		String date = subArr[subArr.length-1].toString().trim();
		String place = "";

		for (int i=0;i<(subArr.length-1);i++)
		{
			place=place+subArr[i];
		}
		d.setField(FieldNames.NEWSDATE, date);
		d.setField(FieldNames.PLACE, place);
	}
}
