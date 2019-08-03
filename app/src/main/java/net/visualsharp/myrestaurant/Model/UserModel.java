package net.visualsharp.myrestaurant.Model;

import java.util.List;

public class UserModel {
    private  boolean success;
    private  String message;
    private List<User> result;

    public boolean isSuccess(){
        return success;
    }

    public void setSuccess(boolean success){
        this.success = success;
    }

    public  String getMessage(){
        return message;
    }

    public List<User> getResult() {
        return result;
    }

    public void setMessage(String message){
        this.message = message;
    }

}
