package com.example.dripkart;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

public  class datas {

    public String name,email,password;

    public datas(String y, String z) {
      //  this.name = x;
        this.email=y;
        this.password=z;
        Log.e("X", "data: "+this.name);
    }

//    @Override
//    protected void onCreate(Bundle savedInstanceState)
//    {
//        super.onCreate(savedInstanceState);
//    }

}