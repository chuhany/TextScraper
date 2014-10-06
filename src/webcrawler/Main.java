/**
 * Driver class
 * 
 * @author Chuhan Yang
 */
package webcrawler;

import entity.ProductList;

public class Main {
    /**
     * Print usage info on screen
     */
    private static void usage() {
        System.out.println("Invalid argument(s)");
        System.out.println("Usage: ");
        System.out.println("Query 1: java -jar Assignment.jar <keyword> (e.g. java -jar Assignment.jar \"baby strollers\")");
        System.out.println("Query 2: java -jar Assignment.jar <keyword> <page number> (e.g. java -jar Assignment.jar \"baby strollers\" 2)");
    }

    public static void main(String[] args) {
        if (args == null || args.length < 1 || args.length > 2) {
            usage();
        }
        
        WebCrawler crawler = new WebCrawler();
        
        if (args.length == 1) {
            String totalNumOfResults = crawler.getTotalNumOfResults(args[0]);
            if (totalNumOfResults != null) {
                System.out.println("Total number of results: " + totalNumOfResults);
            }
        } else {
            int pageNum = Integer.parseInt(args[1]);
            ProductList list = crawler.getResultObjects(args[0], pageNum);
            if (list  != null) {
                list.printInfo();
            }
        }
    }

}
