/**
 * 
 */
package controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import application.Main;
import util.CSVreader;
import util.ParseWebsite;

/**
 * @author sandreicha
 *
 */
public class Engine {

	private final ParseWebsite website;

	// The maps where the compared +/- words are to be stored
	private Map<String, Integer> websitePositiveWord = new HashMap<>();
	private Map<String, Integer> websiteNegativeWord = new HashMap<>();

	// The maps where the +/- words from the csv are stored
	private Map<String, Integer> positiveWord = new HashMap<>();
	private Map<String, Integer> negativeWord = new HashMap<>();

	//The map storing the words not found in the csv
	private Map<String, Integer> wordsNotOnTheList = new HashMap<>();

	//The location of the csv files
	private String positiveCSV = "/positiveWords.csv";
	private String negativeCSV = "/negativeWords.csv";
	
	//Constructor initializing the attributes
	public Engine(ParseWebsite website) {
		super();
		this.website = website;
		try {
			//initializing the positiveWord Map with the read positive words from the csv file
			this.positiveWord = CSVreader.csvReader(positiveCSV);
			//initializing the negativeWord Map with the read negative words from the csv file
			this.negativeWord = CSVreader.csvReader(negativeCSV);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void start() {
		//A map populated with the found on the website
		Map<String, Integer> websiteWords = website.getWebsiteWords();

		//Iterating over the words (keys) in the websiteWords map
		for (String key : websiteWords.keySet()) {
			//if the positiveWord Map contains the key/word form the websiteWords
			//it gets the value(nr of repetitions)
			if (positiveWord.get(key)!=null) {
				websitePositiveWord.put(key, websiteWords.get(key));
			} else if (negativeWord.get(key)!=null) {
				//else if the negativeWord Map contains the key/word form the websiteWords
				//it gets the value(nr of repetitions)
				websiteNegativeWord.put(key, websiteWords.get(key));
			} else {
				//else if neither of the two Maps contains the key/word form the websiteWords
				//they will be placed in a Map which stores the words which are not on the list
				wordsNotOnTheList.put(key, websiteWords.get(key));
			}
		}

		//Printing the Top 10 positive words encountered on the website
		System.out.println("Total positive words are: "+ websitePositiveWord.size());
			websitePositiveWord.entrySet().stream().sorted((k1, k2) -> -k1.getValue().compareTo(k2.getValue())).limit(10).forEach(k -> System.out.println(k.getKey() + ": " + k.getValue()));

		//Printing the Top 10 negative words encountered on the website
		System.out.println("Total negative words are: "+ websiteNegativeWord.size());
		websiteNegativeWord.entrySet().stream().sorted((k1, k2) -> -k1.getValue().compareTo(k2.getValue())).limit(10).forEach(k -> System.out.println(k.getKey() + ": " + k.getValue()));





		//Stream.of(websitePositiveWord).forEach(System.out::println);
		// websiteWords.get(key.valueOf(key)));
		//websitePositiveWord.keySet().forEach(key -> System.out.println(key + " + " + websitePositiveWord.get(key)));
//		websiteNegativeWord.keySet().forEach(key -> System.out.println(key + " - " + websiteNegativeWord.get(key)));
//		System.out.println("\nTotal words Not On The List are: "+ wordsNotOnTheList.size());
//		wordsNotOnTheList.entrySet().stream()
//				.sorted((k1, k2) -> -k1.getValue().compareTo(k2.getValue())).limit(10)
//				.forEach(k -> System.out.println(k.getKey() + ": " + k.getValue()));
//		wordsNotOnTheList.keySet().forEach(key -> System.out.println(key + "," + wordsNotOnTheList.get(key)));

	}


	public int getWebsitePositiveWord() {
		return websitePositiveWord.size();

	}

	public int getWebsiteNegativeWord() {
		return websiteNegativeWord.size();
	}
}
