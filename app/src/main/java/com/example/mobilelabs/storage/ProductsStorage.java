package com.example.mobilelabs.storage;

import java.util.ArrayList;
import java.util.List;

public class ProductsStorage implements storageInterface {
    private static ArrayList<product> products;

    public ProductsStorage(){
        products = new ArrayList<product>();
    }

    @Override
    public List<product> getList() {
        return products;
    }

    @Override
    public void add(product product) {
        int id = 0;
        for (int i = 0; i < products.size(); ++i){
            if (id <= products.get(i).id){
                id = products.get(i).id + 1;
            }
        }
        product.id = id;
        products.add(product);
    }

    @Override
    public void update(product product) {
        for (int i = 0; i < products.size(); ++i){
            if (products.get(i).id == product.id){
                products.get(i).name = product.name;
            }
        }
    }

    @Override
    public void delete(product product) {
        int index = -1;
        for (int i = 0; i < products.size(); ++i){
            if (products.get(i).id == product.id){
                index = i;
            }
        }
        if (index != -1){
            products.remove(index);
        }
    }

    @Override
    public ArrayList<product> findSimilar(product product) {
        ArrayList<product> similarProducts = new ArrayList<product>();
        for (int i = 0; i < products.size(); ++i){
            if (products.get(i).name.contains(product.name)){
                similarProducts.add(products.get(i));
            }
        }
        return similarProducts;
    }
}
