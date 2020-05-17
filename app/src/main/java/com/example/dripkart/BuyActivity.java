package com.example.dripkart;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class BuyActivity extends AppCompatActivity {

    public Spinner mySpinner;
    public String itemtype;
    Button search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy);

        mySpinner = (Spinner) findViewById(R.id.spinner1);

        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(BuyActivity.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.names));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mySpinner.setAdapter(myAdapter);

          Button cartbutton =(Button) findViewById(R.id.cart);

        cartbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(BuyActivity.this,CartActivity.class);
                startActivity(intent);
            }
        });


        search=(Button) findViewById(R.id.searchbutton);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(BuyActivity.this, ImagesActivity.class);
                intent.putExtra("itemtype", itemtype);
                startActivity(intent);
            }
        });

        mySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view,
                                       int position, long id) {
                Object item = adapterView.getItemAtPosition(position);
                if (item != null) {
                    itemtype = (String) adapterView.getItemAtPosition(position);
                    Log.d("x", ":" + itemtype);


                }

            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });


    }
}
