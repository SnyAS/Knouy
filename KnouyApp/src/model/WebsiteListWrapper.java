package model;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "websites")
public class WebsiteListWrapper {

	    private List<Website> websites;

	    @XmlElement(name = "website")
	    public List<Website> getWebsite() {
	        return websites;
	    }

	    public void setWebsites(List<Website> websites) {
	        this.websites = websites;
	    }

}