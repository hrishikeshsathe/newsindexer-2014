/**
 * 
 */
package edu.buffalo.cse.irf14;

import java.io.File;

import edu.buffalo.cse.irf14.document.Document;
import edu.buffalo.cse.irf14.document.Parser;
import edu.buffalo.cse.irf14.document.ParserException;
import edu.buffalo.cse.irf14.index.IndexWriter;
import edu.buffalo.cse.irf14.index.IndexerException;

/**
 * @author nikhillo
 *
 */
public class Runner {
	public static int count;
	public static int count1;
	public static int count2;

	/**
	 * 
	 */
	public Runner() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//String ipDir = args[0];
		String ipDir = "E:/IR/training";
		//String indexDir = args[1];
		//more? idk!
		
		File ipDirectory = new File(ipDir);
		String[] catDirectories = ipDirectory.list();
		
		String[] files;
		File dir;
		
		Document d = null;
		//IndexWriter writer = new IndexWriter(indexDir);
		
		try {
			for (String cat : catDirectories) {
				dir = new File(ipDir+ File.separator+ cat);
				files = dir.list();
				
				if (files == null)
					continue;
				
				for (String f : files) {
					try {
						d = Parser.parse(dir.getAbsolutePath() + File.separator +f);
						
						//writer.addDocument(d);
					} catch (ParserException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} 
					
				}
				
				
			}
			System.out.println(count+" "+count1+" "+count2);
			//writer.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
