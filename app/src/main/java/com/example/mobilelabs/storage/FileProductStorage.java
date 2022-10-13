package com.example.mobilelabs.storage;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.Gson;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import android.content.Context;

public class FileProductStorage implements storageInterface{
    private final String fileName = "productsFile";
    private static ArrayList<product> products;
    private Context context;

    public FileProductStorage(Context context){
        this.context = context;
        importFromJSON();
    }

    @Override
    public ArrayList<product> getList() {
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
        exportToJSON();
    }

    @Override
    public void update(product product) {
        for (int i = 0; i < products.size(); ++i){
            if (products.get(i).id == product.id){
                products.get(i).name = product.name;
            }
        }
        exportToJSON();
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
        exportToJSON();
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
    private void exportToJSON() {
        Gson gson = new Gson();
        DataItems dataItems = new DataItems();
        dataItems.setProducts(products);
        String jsonString = gson.toJson(dataItems);

        try(FileOutputStream fileOutputStream =
                    context.openFileOutput(fileName, Context.MODE_PRIVATE)) {
            fileOutputStream.write(jsonString.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void importFromJSON() {
        try(FileInputStream fileInputStream = context.openFileInput(fileName);
            InputStreamReader streamReader = new InputStreamReader(fileInputStream)){
            Gson gson = new Gson();
            DataItems dataItems = gson.fromJson(streamReader, DataItems.class);
            products = (ArrayList<product>) dataItems.getProducts();
        }
        catch (IOException ex){
            products = new ArrayList<product>();
            ex.printStackTrace();
        }
    }
    private static class DataItems {
        private List<product> products;

        List<product> getProducts() {
            return products;
        }
        void setProducts(List<product> products) {
            this.products = products;
        }
    }
}
