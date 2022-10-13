package com.example.mobilelabs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.mobilelabs.storage.DBProductStorage;
import com.example.mobilelabs.storage.FileProductStorage;
import com.example.mobilelabs.storage.ProductsStorage;
import com.example.mobilelabs.storage.product;
import com.example.mobilelabs.storage.storageInterface;

import java.util.ArrayList;
import java.util.Objects;

public class ShowSimilarProductsActivity extends AppCompatActivity {

    ArrayList<product> products;
    ArrayAdapter<product> adapter;
    storageInterface productsStorage  = new ProductsStorage();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_similar_products);

        Intent ownIntent = getIntent();
        String productName = ownIntent.getStringExtra("productName");
        SharedPreferences sPref = getSharedPreferences("settings", MODE_PRIVATE);
        if (sPref.contains("saveMode")){
            String saveMode = sPref.getString("saveMode", "");
            if (Objects.equals(saveMode, "DB")){
                productsStorage = new DBProductStorage(this);
            }
            if (Objects.equals(saveMode, "Files")){
                productsStorage = new FileProductStorage(this);
            }
        }
        else{
            productsStorage = new FileProductStorage(this);
        }
        products = (ArrayList<product>) productsStorage.findSimilar(new product(productName));

        ListView listViewProducts = findViewById(R.id.ListViewProducts);
        adapter = new ArrayAdapter<product>(this, R.layout.forlistview, products);
        listViewProducts.setAdapter(adapter);
        listViewProducts.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
    }
}