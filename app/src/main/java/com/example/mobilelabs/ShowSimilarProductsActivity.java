package com.example.mobilelabs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class ShowSimilarProductsActivity extends AppCompatActivity {

    ArrayList<String> products;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_similar_products);

        Intent ownIntent = getIntent();
        products = ownIntent.getStringArrayListExtra("productsList");

        ListView listViewProducts = findViewById(R.id.ListViewProducts);
        adapter = new ArrayAdapter<String>(this, R.layout.forlistview, products);
        listViewProducts.setAdapter(adapter);
        listViewProducts.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
    }
}