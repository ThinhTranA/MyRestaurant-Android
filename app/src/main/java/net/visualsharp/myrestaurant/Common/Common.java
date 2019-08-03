package net.visualsharp.myrestaurant.Common;

import net.visualsharp.myrestaurant.Model.User;

public class Common {

    public static final String API_RESTAURANT_ENDPOINT = "http://10.0.2.2:3000/";
    public static final String API_KEY = "abcd";//"1234"; // TODO: secure this key with Firebase Remote config
    public static User currentUser;
}
