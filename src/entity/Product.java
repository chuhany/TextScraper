/**
 * A entity class to encapsulate all information of a product
 * 
 * @author Chuhan Yang
 */

package entity;

public class Product {
    private String title;
    private String price;
    private String shippingPrice;
    private String vendor;
    
    public Product(String title, String price, String shippingPrice, String vendor) {
        this.title = title;
        this.price = price;
        this.shippingPrice = shippingPrice;
        this.vendor = vendor;
    }
    
    /**
     * Concatenate all information of a product into a string
     * 
     * @return  a string containing all information of this product
     */
    public String getInfo() {
        StringBuilder sb = new StringBuilder();
        
        sb.append("Name: " + title);
        sb.append(System.getProperty("line.separator"));
        sb.append("Price: " + price);
        sb.append(System.getProperty("line.separator"));
        sb.append("Shipping Price: " + shippingPrice);
        sb.append(System.getProperty("line.separator"));
        sb.append("Vendor: " + vendor);
        sb.append(System.getProperty("line.separator"));
        
        return sb.toString();
    }
}
