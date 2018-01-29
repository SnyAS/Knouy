package util;

import java.util.HashMap;
import java.util.Map;

public class WebsiteConfig {
    private Map<String,String> theRegex = new HashMap<>();

    public String getTheRegex(String url) {
        //
        /*
            * www.trustpilot.com - <div class="review-body"> ... </div>
            * www.flyertalk.com - <div id="post_message_13260597">�</div>
            * www.tripadvisor.com - <div class="postBody"> <p> ...</p> </div>
        */
        //TODO: extract from the whole url the website title
    	
    	if(theRegex.containsKey(url)) {
        return theRegex.get(url);
    	} 
    	return "Sorry Website not accesible! Please try again!";
    	
    }

    public WebsiteConfig() {
        theRegex.put("www.trustpilot.com", "div.review-body");
        theRegex.put("www.flyertalk.com", "div[id~=(post_message_)[0-9]+]");
        theRegex.put("www.tripadvisor.com", "div.postBody");
    }
}
