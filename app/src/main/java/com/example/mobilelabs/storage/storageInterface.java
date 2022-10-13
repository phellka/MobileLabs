package com.example.mobilelabs.storage;

import java.util.List;

public interface storageInterface {
    public List<product> getList();
    public void add(product product);
    public void update(product product);
    public void delete(product product);
    public List<product> findSimilar(product product);
}
