package net.visualsharp.myrestaurant.Model;

import java.util.List;

public class MenuModel {
    private boolean success;
    private String message;
    private List<Category> result;

    public List<Category> getResult() {
        return result;
    }
}
