package net.visualsharp.myrestaurant.UserModel;

import java.util.List;

public class UserModel {
    private  boolean success;
    private  String message;
    private List<User> result;

    public void isSuccess(boolean success){
        this.success = success;
    }

    public  String getMessage(){
        return message;
    }

    public void setMessage(String message){
        this.message = message;
    }

}
