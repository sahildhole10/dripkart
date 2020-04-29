package com.example.dripkart;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity
{
    //SAhil Dhole
    EditText eemail,epassword;
    Button button,button1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = findViewById(R.id.register);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RegistrationActivity.class);
                startActivity(intent);
            }
        });


        button1 = findViewById(R.id.signin);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                login();
            }
        });

    }
    public void login () {
        eemail = (EditText) findViewById(R.id.email);
        epassword = (EditText) findViewById(R.id.password);
        String s1 = eemail.getText().toString();
        String s2 = epassword.getText().toString();

    }

}


