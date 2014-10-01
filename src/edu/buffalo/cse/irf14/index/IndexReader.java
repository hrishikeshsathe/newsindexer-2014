/**
 * 
 */
package edu.buffalo.cse.irf14.index;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * @author nikhillo
 * Class that emulates reading data back from a written index
 */
public class IndexReader {
	public static HashMap<String, HashMap<Integer, Integer>> index = new HashMap<String, HashMap<Integer, Integer>>();
	public static TreeMap<Integer, String> docDictionary = new TreeMap<Integer, String>();
	public static int valueSize;
	/**
	 * Default constructor
	 * @param indexDir : The root directory from which the index is to be read.
	 * This will be exactly the same directory as passed on IndexWriter. In case 
	 * you make subdirectories etc., you will have to handle it accordingly.
	 * @param type The {@link IndexType} to read from
	 */
	public IndexReader(String indexDir, IndexType type) {
		//TODO
		try{
			FileReader fr = new FileReader(indexDir+File.separator+"docDict");
			BufferedReader br = new BufferedReader(fr);
			String temp = "";
			String[] tempArr;
			while((temp=br.readLine())!=null){
				tempArr = temp.split(" ");
				docDictionary.put(Integer.valueOf(tempArr[0]), tempArr[1]);
			}
			br.close();
			/*ArrayList<Integer> postings;
			String key = "";
			br = new BufferedReader(new FileReader(indexDir+File.separator+type.toString().toLowerCase()));
			while((temp = br.readLine())!=null){
				postings = new ArrayList<Integer>();
				tempArr = temp.split("->->");
				key = tempArr[0];
				tempArr = tempArr[1].split(" ");
				for(int i = 0;i<tempArr.length;i++){
					postings.add(Integer.valueOf(tempArr[i]));
				}
				valueSize+=postings.size();
				index.put(key, postings);*/
			 FileInputStream fis = new FileInputStream(indexDir+File.separator+type.toString().toLowerCase()+".ser");
	         ObjectInputStream ois = new ObjectInputStream(fis);
	         index =  (HashMap<String, HashMap<Integer, Integer>>) ois.readObject();
	         ois.close();
	         fis.close();
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * Get total number of terms from the "key" dictionary associated with this 
	 * index. A postings list is always created against the "key" dictionary
	 * @return The total number of terms
	 */
	public int getTotalKeyTerms() {
		//TODO : YOU MUST IMPLEMENT THIS
		return index.size();
	}
	
	/**
	 * Get total number of terms from the "value" dictionary associated with this 
	 * index. A postings list is always created with the "value" dictionary
	 * @return The total number of terms
	 */
	public int getTotalValueTerms() {
		//TODO: YOU MUST IMPLEMENT THIS
		return docDictionary.size();
	}
	
	/**
	 * Method to get the postings for a given term. You can assume that
	 * the raw string that is used to query would be passed through the same
	 * Analyzer as the original field would have been.
	 * @param term : The "analyzed" term to get postings for
	 * @return A Map containing the corresponding fileid as the key and the 
	 * number of occurrences as values if the given term was found, null otherwise.
	 */
	public Map<String, Integer> getPostings(String term) {
		//TODO:YOU MUST IMPLEMENT THIS
		Map<String, Integer> temp = new HashMap<String, Integer>();
		HashMap<Integer,Integer> postings = new HashMap<Integer, Integer>();
		if(index.containsKey(term)){
		postings = index.get(term);
		Iterator it=postings.entrySet().iterator();
		while(it.hasNext())
		{
			Map.Entry entry=(Map.Entry)it.next();
			Integer key=(Integer) entry.getKey();
			Integer value = (Integer) entry.getValue();
			String key1 = docDictionary.get(key);
			temp.put(key1, value);

		}
		return temp;
		}
		else
			return null;
	}
	
	/**
	 * Method to get the top k terms from the index in terms of the total number
	 * of occurrences.
	 * @param k : The number of terms to fetch
	 * @return : An ordered list of results. Must be <=k fr valid k values
	 * null for invalid k values
	 */
	public List<String> getTopK(int k) {
		//TODO YOU MUST IMPLEMENT THIS
		if(k<=0)
			return null;
		
		HashMap<Integer,Integer> postings = new HashMap<Integer, Integer>();
		HashMap<String,Integer> occurences = new HashMap<String,Integer>();
        ValueComparator bvc =  new ValueComparator(occurences);
        TreeMap<String,Integer> sorted_map = new TreeMap<String,Integer>(bvc);
		Iterator it=index.entrySet().iterator();
		while(it.hasNext())
		{
			Integer count = 0;
			Map.Entry entry=(Map.Entry)it.next();
			String key=entry.getKey().toString();
			postings = (HashMap<Integer, Integer>) entry.getValue();
			Iterator it2 = postings.entrySet().iterator();
			Map.Entry entry2;
			while(it2.hasNext()){
				entry2 = (Map.Entry)it2.next();
				count = count + (Integer)entry2.getValue();
			}
			occurences.put(key,count);
		}
		sorted_map.putAll(occurences);
		List<String> x = new ArrayList<String>();
		x.addAll(sorted_map.keySet());
		if(k>x.size()){
			k=x.size();
		}
		return x.subList(0, k);
	}
	
	/**
	 * Method to implement a simple boolean AND query on the given index
	 * @param terms The ordered set of terms to AND, similar to getPostings()
	 * the terms would be passed through the necessary Analyzer.
	 * @return A Map (if all terms are found) containing FileId as the key 
	 * and number of occurrences as the value, the number of occurrences 
	 * would be the sum of occurrences for each participating term. return null
	 * if the given term list returns no results
	 * BONUS ONLY
	 */
	public Map<String, Integer> query(String...terms) {
		//TODO : BONUS ONLY
		/*Set<Integer> answer = new HashSet<Integer>();
		answer.addAll(index.get(terms[0]).keySet());
		Map<String,Integer> queryAnswer = new HashMap<String, Integer>();
		for(int i = 1;i<terms.length;i++){
			HashMap<Integer,Integer> postings = new HashMap<Integer, Integer>();
			HashMap<String,Integer> occurences = new HashMap<String,Integer>();
			Iterator it=index.entrySet().iterator();
			answer.retainAll(postings.keySet());
			while(it.hasNext())
			{
				Integer count = 0;
				Map<String, Integer> temp = new HashMap<String, Integer>();
				Map.Entry entry=(Map.Entry)it.next();
				String key=entry.getKey().toString();
				postings = (HashMap<Integer, Integer>) entry.getValue();
				Iterator it2 = answer.iterator();
				while(it2.hasNext()){
					count = count + (Integer)postings.get(it2.next().toString());
				}
				occurences.put(key,count);
				
			}
		}
		Iterator setIterator = answer.iterator();
		while(setIterator.hasNext()){
			
		}*/
		Set<Integer> answer = new HashSet<Integer>();
		answer.addAll(index.get(terms[0]).keySet());
		Map<String,Integer> queryAnswer = new HashMap<String, Integer>();
		for(int i=0;i<terms.length;i++){
			HashMap<Integer,Integer> postings = new HashMap<Integer,Integer>();
			postings = index.get(terms[i]);
			answer.retainAll(postings.keySet());
		}
		if(answer.isEmpty())
			return null;
		Iterator it = answer.iterator();
		while(it.hasNext()){
			Integer temp = (Integer) it.next();
			int occurences = 0;
			for(int i = 0; i<terms.length;i++){
				HashMap<Integer,Integer> postings = new HashMap<Integer,Integer>();
				postings = index.get(terms[i]);
				occurences += postings.get(temp);
			}	
		queryAnswer.put(docDictionary.get(temp),occurences);
		}
		
		return queryAnswer;
	}
	
}

class ValueComparator implements Comparator<String> {

    Map<String, Integer> base;
    public ValueComparator(Map<String, Integer> base) {
        this.base = base;
    }

    // Note: this comparator imposes orderings that are inconsistent with equals.    
    public int compare(String a, String b) {
        if (base.get(a) < base.get(b)) {
            return 1;
        }
        else if(base.get(a) == base.get(b)){
        	return a.compareToIgnoreCase(b);
        }
       else {
            return -1;
        } // returning 0 would merge keys
    }
}
