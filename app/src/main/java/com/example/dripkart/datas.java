package com.example.dripkart;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

public  class datas {

    public String email,password;
    public int temp;

    public datas()
    {

    }

    public datas(String y, String z, int temp) {

        this.email=y;
        this.password=z;
        this.temp=temp;
        Log.e("X", "data: "+this.temp);
    }

    public int getTemp() {
        return temp;
    }

    public void setTemp(int temp) {
        this.temp = temp;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}