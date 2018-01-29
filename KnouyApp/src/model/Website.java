package model;

import java.io.IOException;
import java.net.URL;
import java.util.Set;

import util.BadConnectionException;
import util.TitleExtractor;

public class Website {
	private Website site;

	private Set<Website> pages;
	private URL url;
	private TitleExtractor title;

	public Website(String url) throws IOException, BadConnectionException {
		this.url = new URL(url);
		this.title = new TitleExtractor();
	}

	public URL getUrl() {
		return url;
	}

	public void setUrl(String url) {
        site.setUrl(url);
        }


	public String getTitle() {
		return title.toString();
	}

	public void getSite() {
		if (pages.contains(site))
			site.getSite();
	}
	
	public boolean addPage(Website page) {
        return pages.add(page);
    }

}
