package com.example.mobilelabs;

import androidx.appcompat.app.AppCompatActivity;


import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements DialogInterface.OnDismissListener {

    static ProductsStorage productsStorage = new ProductsStorage();
    ArrayAdapter<String> adapter;
    ListView listViewProducts;
    EditProductFragment productEditFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        listViewProducts = findViewById(R.id.ListViewProducts);
        TextView textViewProductName = findViewById(R.id.editTextTextViewProduct);
        Button buttonAdd = findViewById(R.id.buttonAddProduct);
        Button buttonFindSimilar = findViewById(R.id.buttonFindSimilar);
        Button buttonEditSelected = findViewById(R.id.buttonEditSelected);
        Button buttonDeleteSelected = findViewById(R.id.buttonDeleteSelected);


        buttonAdd.setOnClickListener(v -> {
            String addedText = textViewProductName.getText().toString();
            productsStorage.products.add(addedText);
            adapter.notifyDataSetChanged();
        });

        buttonDeleteSelected.setOnClickListener(v -> {
            SparseBooleanArray sparseBooleanArray  = listViewProducts.getCheckedItemPositions();
            int len = listViewProducts.getCount();
            for(int i = 0; i < len; i++)
            {
                if(sparseBooleanArray.get(i) == true)
                {
                    productsStorage.products.remove(i);
                }
            }
            listViewProducts.clearChoices();
            adapter.notifyDataSetChanged();
        });

        buttonFindSimilar.setOnClickListener(v -> {
            String similar = textViewProductName.getText().toString();
            ArrayList<String> similarProducts = productsStorage.findSimilar(similar);
            Intent intent = new Intent(this, ShowSimilarProductsActivity.class);
            intent.putStringArrayListExtra("productsList", similarProducts);
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
                    product = listViewProducts.getItemAtPosition(i).toString();
                }
            }
            bundle.putString("product", product );
            productEditFragment.setArguments(bundle);
            productEditFragment.show(getFragmentManager(), "editProduct");
        });

        adapter = new ArrayAdapter<String>(this, R.layout.forlistview, productsStorage.products);
        listViewProducts.setAdapter(adapter);
        listViewProducts.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
    }
    @Override
    public void onDismiss(final DialogInterface dialog) {
        String product = productEditFragment.resultProduct;
        if (product != null){
            SparseBooleanArray sparseBooleanArray  = listViewProducts.getCheckedItemPositions();
            for(int i = 0; i < listViewProducts.getCount(); i++)
            {
                if(sparseBooleanArray.get(i) == true)
                {
                    productsStorage.products.set(i, product);
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
