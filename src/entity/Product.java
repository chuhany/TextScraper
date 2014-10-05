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
     * Get the title of the product
     * 
     * @return  title of the product 
     */
    public String getTitle() {
        return title;
    }
    
    /**
     * Get the price of the product
     * 
     * @return  price of the product 
     */
    public String getPrice() {
        return price;
    }
    
    /**
     * Get the shipping price of the product
     * 
     * @return  shipping price of the product 
     */
    public String getShippingPrice() {
        return shippingPrice;
    }
    
    /**
     * Get the vendor of the product
     * 
     * @return  vendor of the product 
     */
    public String getVendor() {
        return vendor;
    }
}
