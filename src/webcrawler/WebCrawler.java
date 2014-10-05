package webcrawler;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class WebCrawler {
    private static final String TARGET_URL = "http://www.shopping.com/";
    
    /**
     * Query 1: Given a keyword, return the total number of results found
     * 
     * @param   keyword   key word entered by the user
     * @return  total number of results (647, 1500+, etc) if successful
     *          null if an error happens (invalid input, etc)        
     */
    public String getTotalNumOfResults(String keyword) {
        if (keyword == null) {
            System.err.println("Invalid input. Please try again.");
            return null;
        }
        
        /* Preprocess the user input */
        keyword = keyword.trim();
        if (keyword.length() == 0) {
            System.err.println("Invalid input. Please try again.");
            return null;
        }
        
        /* Concatenate the keyword to form the url */
        String url = catURL(keyword);
        String totalNum = null;
        
        try {
            /* Connect to the url and search for the keyword */
            Document doc = Jsoup.connect(url).data("CLT", "SCH").data("KW", keyword).get();
            
            /* Get the total number of results */
            String numTotalResults = doc.getElementsByClass("numTotalResults").html();
            int index = numTotalResults.lastIndexOf(' ');
            totalNum = numTotalResults.substring(index + 1);
        } catch (IOException e) {
            System.err.println("Error in loading web pages. Please try again.");
            return null;
        }
        
        System.out.println(totalNum);
        
        return totalNum;
    }
    
    /**
     * Helper method to concatenate keyword to form a url to connect
     * 
     * @param   keyword   key word entered by the user
     * @return  concatenated url
     */
    private static String catURL(String keyword) {
        return TARGET_URL + keyword.replace(' ', '-') + "/products";
    }
    
    public 
    
    public static void main(String[] args) {
        
    }
}
