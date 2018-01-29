package util;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class TitleExtractor {

	public static String getPageTitle(URL url)  throws MalformedURLException, IOException, BadConnectionException {
		Document doc = Jsoup.connect(url.toString()).get();
		String title = doc.title();

		System.out.println(title);
		return title;
	}
}
