package util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import com.opencsv.CSVReader;

/**
 * @author sandreicha
 *
 */
public class CSVreader {

	public static Map<String, Integer> csvReader(String theCSV) throws IOException {
		CSVReader csvreader = null;
		Map<String, Integer> theWords = new HashMap<>();
		
		try {
			InputStream res = CSVreader.class.getResourceAsStream(theCSV);//"/WordList.csv");

		    BufferedReader reader = new BufferedReader(new InputStreamReader(res));
		    
			csvreader = new CSVReader(reader);//((String) paths.getPath()), ',');//
			String[] line;
			while ((line = csvreader.readNext()) != null) {
				if (line.length == 2) {
					try {
						
						//split the csv into 2 parts the positive and negative

						theWords.put(line[0], Integer.parseInt(line[1]));
//						System.out.println(line[0]+"\t"+Integer.parseInt(line[1]));
					} catch (NumberFormatException ignore) {

					}
				}
			}
		} catch (Throwable e) {
			e.printStackTrace();
		}finally {
			try {
				if (csvreader != null) {
					csvreader.close();
				}
			} catch (IOException ignore) {
			}
		}
		return theWords;
	}
}
