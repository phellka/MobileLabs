package com.example.mobilelabs.storage;

public class product {
    public int id;
    public String name;
    public product(String name){
        this.name = name;
    }
    public product(){
        name = "";
    }
    @Override
    public String toString(){
        return "id = " + Integer.toString(id) + "name = " + name;
    }
}
