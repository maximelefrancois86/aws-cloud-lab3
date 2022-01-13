package emse;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is used to regroup all transactions that are present in a CSV file and store some useful informations
 */
public class TransactionList {
    private List<Transaction> transactions;
    private List<String> countries;
    private List<String> products;

    public TransactionList (){
        transactions = new ArrayList<Transaction>();
        countries = new ArrayList<String>();
        products = new ArrayList<String>();
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public List<String> getCountries() {
        return countries;
    }

    public List<String> getProducts() {
        return products;
    }

    public void addTransaction (Transaction transaction){
        String product=transaction.getProduct();
        String country=transaction.getCountry();

        transactions.add(transaction);

        if (!products.contains(product)){
            products.add(product);
        }
        if (!countries.contains(country)){
            countries.add(country);
        }
    }

    public int nBSalesByCountryByProduct(String country,String product){
        int nbOfSales=0;
        for (Transaction transaction : transactions){
            if (transaction.getCountry().equals(country) && transaction.getProduct().equals(product))nbOfSales++;
        }
        return nbOfSales;
    }

    public int salesByCountryByProduct(String country,String product){
        int sales=0;
        for (Transaction transaction : transactions){
            if (transaction.getCountry().equals(country) && transaction.getProduct().equals(product))sales+=transaction.getPrice();
        }
        return sales;
    }

    public int averageSoldPerCountryPerProduct( String country){
        int totalSold=0;
        for (Transaction transaction : transactions){
            if (transaction.getCountry().equals(country) ) {
                totalSold=totalSold+transaction.getPrice();
            }
        }
        return totalSold/products.size();
    }


}
