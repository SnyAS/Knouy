package util;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;

import org.jsoup.nodes.Document;

public class ParseWebsite {
	//A string containing the regular expressions which will be replaced from the text
	public static final String REGEX = ", |'|\\?|!|\\.|\\(|\\)|: [0 1 2 3 4 5 6 7 8 9 -]]";
	// The maps where the words from the website will be stored
	private Map<String, Integer> websiteWords = new HashMap<>();

	//Method parsing the website url with the cssQuery
	public void parsing(String url, String cssQuery) throws IOException {
		//Connecting to website with Jsoup library
		Document document = Jsoup.connect(url).get();
		//A list the lines of text
		List<String> lines = document.select(cssQuery).eachText();
		//For each line, in the lines List, make them to upper case,
		// replace all characters that are similar to the REGEX with nothing
		//and then split them on the space
		for (String line : lines) {
			String[] words = line.toUpperCase().replaceAll(REGEX, "").split(" ");
			//Populate the websiteWords Map with the word as Key and repetition as Value
			for (String word : words) {
				Integer repeatedWordCount = websiteWords.get(word);
				if (repeatedWordCount != null) {
					//if word is existent update the Value to the repeatedWordCount
					repeatedWordCount++;
					websiteWords.put(word, repeatedWordCount);
				} else {
					//if word is not existent, add it to the websiteWords Map with the Value of 1
					websiteWords.put(word, 1);
				}
			}
		}
		//Printing the total words found on the website
		System.out.println("Total words are: "+ websiteWords.size()+"\n");
	}

	public Map<String, Integer> getWebsiteWords() {
		return websiteWords;
	}
}







//System.out.println("The word from website \t" + word + "\t" + repeatedWordCount);

				//	System.out.println("Adding new word from website \t" + word);
// List<String> lines =
// document.select("div[id~=(post_message_)[0-9]+]").eachText(); // for
// flyertalk
// Stream.of(words).forEach(System.out::println);
// System.out.println(line.toUpperCase().words(" "));
// public void websiteToParse(String page) {
//
// BufferedReader reader = null;
// try {
// URL url = new URL(page);
// URLConnection connection = url.openConnection();//
// page.getUrl().openConnection();
// reader = new BufferedReader(new
// InputStreamReader(connection.getInputStream()));
// StringBuilder content = new StringBuilder();
// String line;
// while ((line = reader.readLine()) != null) {
// content.append(line).append(" ");
// }
// String[] words =
// Jsoup.parse(content.toString()).body().text().replaceAll("[^\\p{L}- ]", "
// ").split(" ");
//
// int mood = 0;
//
// for (String word : words) {
// System.out.println(word);
// //if (websiteWords.containsKey(word.toUpperCase())) {
// // mood +=
// websiteWords.get(word.toUpperCase());
// //}
// }
// // page.updateMood(mood);
// } catch (IOException e) {
// e.printStackTrace();
// } finally {
//
// try {
// if (reader != null) {
// reader.close();
// }
// } catch (IOException e) {
// e.printStackTrace();
// }
// }
//
// }

// /**
// * @throws IOException
// */
// public void parsingComments(String url) throws IOException {
// // trustpilot
// Document document = Jsoup.connect(url).get();
// List<String> words = document.select("div.review-body").eachText();
// for (String word : words) {
// websiteWords.get(word.toUpperCase());
// System.out.println(word.toUpperCase());
// }
// }

// public void parsingTripadvisor(String url) throws IOException {
// // for tripadvisor
// Document document = Jsoup.connect(url).get();
// List<String> words = document.select("div.postBody").eachText();
// for (String word : words) {
// websiteWords.get(word.toUpperCase());
// System.out.println(word.toUpperCase());
// }
// }