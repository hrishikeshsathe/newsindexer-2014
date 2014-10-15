/**
 * 
 */
package edu.buffalo.cse.irf14.index;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import edu.buffalo.cse.irf14.analysis.Analyzer;
import edu.buffalo.cse.irf14.analysis.AnalyzerFactory;
import edu.buffalo.cse.irf14.analysis.TokenStream;
import edu.buffalo.cse.irf14.analysis.Tokenizer;
import edu.buffalo.cse.irf14.analysis.TokenizerException;
import edu.buffalo.cse.irf14.document.Document;
import edu.buffalo.cse.irf14.document.FieldNames;

/**
 * @author nikhillo
 * Class responsible for writing indexes to disk
 */
public class IndexWriter {
	public static HashMap<String, HashMap<Integer, ArrayList<Integer>>> termIndex = new HashMap<String, HashMap<Integer, ArrayList<Integer>>>();
	public static HashMap<String, HashMap<Integer, ArrayList<Integer>>> authIndex = new HashMap<String,HashMap<Integer, ArrayList<Integer>>>();
	public static HashMap<Integer, String> docDictionary = new HashMap<Integer, String>();
	public static HashMap<String, HashMap<Integer, ArrayList<Integer>>> placeIndex = new HashMap<String,HashMap<Integer, ArrayList<Integer>>>();
	public static HashMap<String, HashMap<Integer, ArrayList<Integer>>> categoryIndex = new HashMap<String, HashMap<Integer, ArrayList<Integer>>>();

	static String indexDirectory;
	public static Integer docID = 0;
	static AnalyzerFactory af = AnalyzerFactory.getInstance();
	/**
	 * Default constructor
	 * @param indexDir : The root directory to be sued for indexing
	 */
	public IndexWriter(String indexDir) {

		indexDirectory = indexDir;
	}

	/**
	 * Method to add the given Document to the index
	 * This method should take care of reading the filed values, passing
	 * them through corresponding analyzers and then indexing the results
	 * for each indexable field within the document. 
	 * @param d : The Document to be added
	 * @throws IndexerException : In case any error occurs
	 * @throws TokenizerException 
	 */
	public void addDocument(Document d) throws IndexerException {
		if(docDictionary.containsValue(d.getField(FieldNames.FILEID)[0])){
			indexAll(FieldNames.CATEGORY,d,categoryIndex);
			return;
		}
		docID++;
		indexAll(FieldNames.TITLE,d,termIndex);
		indexAll(FieldNames.CONTENT,d,termIndex);
		indexAll(FieldNames.NEWSDATE,d,termIndex);
		indexAll(FieldNames.AUTHORORG, d, authIndex);
		indexAll(FieldNames.AUTHOR,d,authIndex);
		indexAll(FieldNames.CATEGORY,d,categoryIndex);
		indexAll(FieldNames.PLACE, d, placeIndex);
			
	}

	
	

	public static void indexAll(FieldNames fn, Document d, HashMap<String, HashMap<Integer, ArrayList<Integer>>> indexer)
	{
		Tokenizer tz=new Tokenizer();
		TokenStream ts=null;

		try{
			if(null!=d.getField(fn))
			{
				ts=tz.consume(d.getField(fn)[0]);
				Analyzer ta=af.getAnalyzerForField(fn, ts);
				while(ta.increment()){}
				ts=ta.getStream();
				ts.reset();
				int posCounter=0;
				while(ts.hasNext())
				{
					//TODO
					String filename="";
					

					if(fn==FieldNames.TITLE || fn==FieldNames.CONTENT)
					{
						//if(null!=d.getField(FieldNames.CATEGORY))
						//{
						//	filename=d.getField(FieldNames.CATEGORY)[0]+File.separator+d.getField(FieldNames.FILEID)[0];
						//}
						//else
						{
							filename=d.getField(FieldNames.FILEID)[0];
						}
						docDictionary.put(docID, filename);
					}

				//	HashMap<Integer,Integer> postings=new HashMap<Integer, Integer>();
					HashMap<Integer,ArrayList<Integer>> postings=new HashMap<Integer, ArrayList<Integer>>();
					String temp = "";
					if(fn.equals(FieldNames.AUTHOR) || fn.equals(FieldNames.AUTHORORG))
					{	
						temp = ts.next().toString().toLowerCase();
						posCounter++;
					}
					
					else
					{	temp = ts.next().toString();
						posCounter++;
					}
					if(indexer.containsKey(temp)){
						postings = indexer.get(temp);
						if(postings.containsKey(docID)){
							ArrayList<Integer> posArray=postings.get(docID);
							//i=i+1;
							if(fn==FieldNames.CONTENT)
							{
								posArray.add(posCounter);
								postings.put(docID,posArray);
							}
							
							indexer.put(temp, postings);
						}
						else
						{
							ArrayList<Integer> posArray=new ArrayList<Integer>();
							if(fn==FieldNames.CONTENT)
								posArray.add(posCounter);
							postings.put(docID, posArray);
							indexer.put(temp, postings);
						}
					}
					else
					{
						ArrayList<Integer> posArray=new ArrayList<Integer>();
						if(fn==FieldNames.CONTENT)
						posArray.add(posCounter);
						postings.put(docID,posArray);
						indexer.put(temp, postings);
					}

				}
			}
		}
		catch(TokenizerException e){

		}
	}
	
	/**
	 * Method that indicates that all open resources must be closed
	 * and cleaned and that the entire indexing operation has been completed.
	 * @throws IndexerException : In case any error occurs
	 */
	public void close() throws IndexerException {
		//TODO
		writeIndexToFile(IndexType.TERM.toString().toLowerCase(),termIndex);
		writeIndexToFile(IndexType.AUTHOR.toString().toLowerCase(),authIndex);
		writeIndexToFile(IndexType.PLACE.toString().toLowerCase(),placeIndex);
		writeIndexToFile(IndexType.CATEGORY.toString().toLowerCase(), categoryIndex);
		try{
			FileWriter w = new FileWriter(indexDirectory+File.separator+"docDict");
			Iterator it = docDictionary.entrySet().iterator();
			while(it.hasNext()){
				Map.Entry entry = (Map.Entry)it.next();
				String key = entry.getKey().toString();
				String value = entry.getValue().toString();
				w.write(key+" "+value+"\n");
			}
			w.close();
		}
		catch(Exception e){

		}
	}

	public static void writeIndexToFile(String type, HashMap<String, HashMap<Integer, ArrayList<Integer>>> indexToWrite){
		try{
			/*FileWriter writers = new FileWriter(indexDirectory+File.separator+type);
			BufferedWriter bw = new BufferedWriter(writers,8);
			Iterator it=termIndex2.entrySet().iterator();
			while(it.hasNext())
			{
				Map.Entry entry=(Map.Entry)it.next();
				String key=entry.getKey().toString();
				HashMap hm=(HashMap) entry.getValue();
				String v = "";
				System.out.println(hm.toString());
				//for(int i =0;i<arrlst.size();i++){
				//	v += arrlst.get(i)+" ";
				//}
				//bw.write(key+"->->"+v+"\n");

			}
			//writers.close();*/
			File toCheck = new File(indexDirectory);
			if(!toCheck.exists()){
				toCheck.mkdirs();
			}
			FileOutputStream fos =
					new FileOutputStream(indexDirectory+File.separator+type+".ser");
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(indexToWrite);
			oos.close();
			fos.close();
		}
		catch(Exception e){
		}
	}
}
