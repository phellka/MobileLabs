package com.example.mobilelabs;

import java.util.ArrayList;

public class ProductsStorage {
    public static ArrayList<String> products;

    public ProductsStorage(){
        products = new ArrayList<String>();
    }
    public ArrayList<String> findSimilar(String similar){
        ArrayList<String> similarProducts = new ArrayList<String>();
        for (int i = 0; i < products.size(); ++i){
            if (products.get(i).contains(similar)){
                similarProducts.add(products.get(i));
            }
        }
        return similarProducts;
    }
}
