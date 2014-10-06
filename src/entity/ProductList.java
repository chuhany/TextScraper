/**
 * A list of products in one page
 * 
 * @author Chuhan Yang
 */

package entity;

import java.util.ArrayList;
import java.util.List;

public class ProductList {
    private List<Product> list;
    
    public ProductList() {
        list = new ArrayList<Product>();
    }
    
    /**
     * Add a product into the list
     * 
     * @param product   the product to be added
     */
    public void add(Product product) {
        list.add(product);
    }
    
    /**
     * Print all information of each product in the list
     */
    public void printInfo() {
        for (int i = 1; i <= list.size(); i++) {
            System.out.println(i + ": ");
            System.out.println(list.get(i - 1).getInfo());
            System.out.println();
        }
    }
}
