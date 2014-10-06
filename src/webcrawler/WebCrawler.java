/**
 * A simple text scraper that supports two types of queries for shopping.com
 * 
 * @author Chuhan Yang
 */

package webcrawler;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import entity.Product;
import entity.ProductList;

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
            System.err.println("Invalid keyword. Please try again.");
            return null;
        }
        
        /* Preprocess the user input */
        keyword = keyword.trim();
        
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
     * Query 2: Given a keyword (e.g. "digital cameras") and page number (e.g. "1"), 
     *          return the results in that page.
     *          For each result, the information includes:
     *          1. Title/Product Name (e.g. "Samsung TL100 Digital Camera")
     *          2. Price of the product
     *          3. Shipping Price (e.g. "Free Shipping", "$3.50")
     *          4. Vendor (e.g. "Amazon", "5 stores")
     * 
     * @param   keyword     keyword entered by the user
     * @param   pageNum     page number entered by the user
     * @return  a list of results if successful. Each result is encapsulated in a Product object
     *          null if an error happens (invalid input, etc)
     */
    public ProductList getResultObjects(String keyword, int pageNum) {
        if (keyword == null) {
            System.err.println("Invalid keyword. Please try again.");
            return null;
        } else if (pageNum < 1) {
            System.err.println("Invalid page number (at least 1). Please try again.");
            return null;
        }
        
        /* Preprocess the user input */
        keyword = keyword.trim();
        
        /* Concatenate the keyword to form the url */
        String url = catURL(keyword);
        
        ProductList productList = new ProductList();
       
        try {
            /* Connect to the url and search for the keyword */
            Document doc = Jsoup.connect(url).data("CLT", "SCH").data("KW", keyword).get();
            
            /* Get the total number of pages */
            Element element = doc.select("a[name~=PL\\d]").last();
            if (element == null) {
                throw new IOException("Error in finding page information.");
            }
            int numOfPages = Integer.parseInt(element.html());
            if (pageNum > numOfPages) {
                System.err.println("Invalid page number. Total number of pages is " + numOfPages + ". Please try again.");
                return null;
            }
            
            /* Get the url of the selected page */
            String pageURL = getPageURL(url, pageNum);
            /* Connect to the selected page */
            Document page = Jsoup.connect(pageURL).data("KW", keyword).get();
            
            /* Get the info of products */
            Elements products = page.getElementsByClass("gridItemBtm");
            
            for (int i = 1; i <= products.size(); i++) {
                Element product = products.get(i - 1);
                
                /* Get the info of a product */
                String name = product.getElementById("nameQA" + i).attr("title");
                
                String price = product.getElementsByClass("productPrice").html();
                if (price.charAt(0) != '$') {
                    price = product.getElementById("DCTmerchNameLnk" + (i - 1)).html();
                }
                
                String shippingPrice = "No Shipping Price Info";
                Elements freeShip = product.getElementsByClass("freeShip");
                Elements calc = product.getElementsByClass("calc");
                if (freeShip.size() == 1) {
                    shippingPrice = freeShip.html();
                } else if (calc.size() == 1) {
                    shippingPrice = calc.html().replace("+", "").replace("shipping", "").trim();
                }
                
                String vendor = null;
                Element numOfStores = product.getElementById("numStoresQA" + i);
                if (numOfStores == null) {
                    vendor = product.getElementsByClass("newMerchantName").html();
                } else {
                    vendor = numOfStores.html();
                }
                
                /* Store the product info */
                Product item = new Product(name, price, shippingPrice, vendor);
                productList.add(item);
            }
        } catch (IOException e) {
            System.err.println("Error in loading web pages. Please try again.");
            return null;
        }
        
        return productList;
    }
        
    /**
     * Helper method to concatenate keyword to form a url to connect
     * 
     * @param   keyword   key word entered by the user
     * @return  concatenated url
     */
    private String catURL(String keyword) {
        return TARGET_URL + keyword.replace(' ', '-') + "/products";
    }
    
    /**
     * Helper method to get a url based on the page number
     * 
     * @param   url       concatenated url
     * @param   pageNum   page number entered by the user
     * @return  url of that page         
     */
    private String getPageURL(String url, int pageNum) {
        return url + "~PG-" + pageNum;
    }
}
