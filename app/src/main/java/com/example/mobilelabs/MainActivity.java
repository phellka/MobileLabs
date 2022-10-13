package com.example.mobilelabs;

import androidx.appcompat.app.AppCompatActivity;


import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.mobilelabs.EditProductFragment;
import com.example.mobilelabs.storage.DBProductStorage;
import com.example.mobilelabs.storage.FileProductStorage;
import com.example.mobilelabs.storage.ProductsStorage;
import com.example.mobilelabs.R;
import com.example.mobilelabs.Settings;
import com.example.mobilelabs.ShowSimilarProductsActivity;
import com.example.mobilelabs.storage.product;
import com.example.mobilelabs.storage.storageInterface;
import java.util.ArrayList;
import java.util.Objects;

import com.example.mobilelabs.storage.product;

public class MainActivity extends AppCompatActivity implements DialogInterface.OnDismissListener {

    static storageInterface productsStorage;
    ArrayAdapter<product> adapter;
    ListView listViewProducts;
    EditProductFragment productEditFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

        listViewProducts = findViewById(R.id.ListViewProducts);
        TextView textViewProductName = findViewById(R.id.editTextTextViewProduct);
        Button buttonAdd = findViewById(R.id.buttonAddProduct);
        Button buttonFindSimilar = findViewById(R.id.buttonFindSimilar);
        Button buttonEditSelected = findViewById(R.id.buttonEditSelected);
        Button buttonDeleteSelected = findViewById(R.id.buttonDeleteSelected);


        buttonAdd.setOnClickListener(v -> {
            String addedText = textViewProductName.getText().toString();
            productsStorage.add(new product(addedText));
            adapter.notifyDataSetChanged();
        });

        buttonDeleteSelected.setOnClickListener(v -> {
            SparseBooleanArray sparseBooleanArray  = listViewProducts.getCheckedItemPositions();
            int len = listViewProducts.getCount();
            for(int i = 0; i < len; i++)
            {
                if(sparseBooleanArray.get(i) == true)
                {
                    productsStorage.delete(adapter.getItem(i));
                }
            }
            listViewProducts.clearChoices();
            adapter.notifyDataSetChanged();
        });

        buttonFindSimilar.setOnClickListener(v -> {
            String similar = textViewProductName.getText().toString();
            Intent intent = new Intent(this, ShowSimilarProductsActivity.class);
            intent.putExtra("productName", similar);
            startActivity(intent);
        });

        buttonEditSelected.setOnClickListener(v -> {
            productEditFragment = new EditProductFragment();
            Bundle bundle = new Bundle();
            String product = new String();
            SparseBooleanArray sparseBooleanArray  = listViewProducts.getCheckedItemPositions();
            for(int i = 0; i < listViewProducts.getCount(); i++)
            {
                if(sparseBooleanArray.get(i) == true)
                {
                    product = adapter.getItem(i).name;
                }
            }
            bundle.putString("productName", product );
            productEditFragment.setArguments(bundle);
            productEditFragment.show(getFragmentManager(), "editProduct");
        });

        Button buttonSettings = findViewById(R.id.buttonSettings);
        buttonSettings.setOnClickListener(v -> {
            Intent intent = new Intent(this, Settings.class);
            startActivity(intent);
        });

        adapter = new ArrayAdapter<product>(this, R.layout.forlistview, productsStorage.getList());
        listViewProducts.setAdapter(adapter);
        listViewProducts.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
    }
    @Override
    public void onDismiss(final DialogInterface dialog) {
        String product = productEditFragment.resultProductName;
        if (product != null){
            SparseBooleanArray sparseBooleanArray  = listViewProducts.getCheckedItemPositions();
            for(int i = 0; i < listViewProducts.getCount(); i++)
            {
                if(sparseBooleanArray.get(i) == true)
                {
                    product pr = new product(product);
                    pr.id = adapter.getItem(i).id;
                    productsStorage.update(pr);
                }
            }
            adapter.notifyDataSetChanged();
        }
    }
}





        /*
        buttonOutputSelected.setOnClickListener(v -> {
            SparseBooleanArray sparseBooleanArray  = listViewProducts.getCheckedItemPositions();
            String checked = "";
            for(int i = 0; i < listViewProducts.getCount(); i++)
            {
                if(sparseBooleanArray.get(i) == true)
                {
                    checked += listViewProducts.getItemAtPosition(i).toString() + "\n";
                }
            }
            Toast.makeText(this, checked, Toast.LENGTH_LONG).show();
        });

        buttonChoseLast.setOnClickListener(v -> {
            if (listViewProducts.getCount() > 0) {
                listViewProducts.setItemChecked(listViewProducts.getCount() - 1, true);
            }
        });

        buttonResetSelection.setOnClickListener(v -> {
            listViewProducts.clearChoices();
            adapter.notifyDataSetChanged();
        });
        */
