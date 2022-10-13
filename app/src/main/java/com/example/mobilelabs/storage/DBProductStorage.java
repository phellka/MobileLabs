package com.example.mobilelabs.storage;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.google.gson.Gson;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class DBProductStorage implements storageInterface{
    DBHelper dbHelper;
    private ArrayList<product> products = new ArrayList<product>();
    public DBProductStorage(Context context){
        dbHelper = new DBHelper(context);
        readAll();
    }
    @Override
    public List<product> getList() {
        return products;
    }

    @Override
    public void add(product product) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("name", product.name);
        long rowID = db.insert("products", null, cv);
        product.id = (int) rowID;
        products.add(product);
        dbHelper.close();
    }

    @Override
    public void update(product product) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("name", product.name);
        db.update("products", cv, "id = ?", new String[] {String.valueOf(product.id)});
        dbHelper.close();
        readAll();
    }

    @Override
    public void delete(product product) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("name", product.name);
        db.delete("products", "id = " + product.id, null);
        dbHelper.close();
        readAll();
    }

    @Override
    public List<product> findSimilar(product product) {
        ArrayList<product> products = new ArrayList<product>();
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor c = db.query("products", null, "name = ?", new String[] {product.name}, null, null, null);
        if (c.moveToFirst()) {
            int idColIndex = c.getColumnIndex("id");
            int nameColIndex = c.getColumnIndex("name");
            do {
                product pr = new product();
                pr.id = c.getInt(idColIndex);
                pr.name = c.getString(nameColIndex);
                products.add(pr);
            } while (c.moveToNext());
        }
        dbHelper.close();
        return products;
    }


    public void readAll(){
        products.clear();
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor c = db.query("products", null, null, null, null, null, null);
        if (c.moveToFirst()) {
            int idColIndex = c.getColumnIndex("id");
            int nameColIndex = c.getColumnIndex("name");
            do {
                product pr = new product();
                pr.id = c.getInt(idColIndex);
                pr.name = c.getString(nameColIndex);
                products.add(pr);
            } while (c.moveToNext());
        }
        dbHelper.close();
    }

    private void importFromJSON(Context context, String fileName) {
        try(FileInputStream fileInputStream = context.openFileInput(fileName);
            InputStreamReader streamReader = new InputStreamReader(fileInputStream)){
            Gson gson = new Gson();
            DataItems dataItems = gson.fromJson(streamReader, DataItems.class);
            products = (ArrayList<product>) dataItems.getProducts();
            for (int i = 0; i < products.size(); ++i){
                add(products.get(i));
            }
        }
        catch (IOException ex){
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

    class DBHelper extends SQLiteOpenHelper {

        public DBHelper(Context context) {
            super(context, "myDB", null, 1);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("create table products ("
                    + "id integer primary key autoincrement,"
                    + "name text" + ");");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }
}
