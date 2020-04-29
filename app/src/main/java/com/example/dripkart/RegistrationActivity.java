package com.example.dripkart;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Random;

public class RegistrationActivity extends AppCompatActivity {
    // public String name,email;
    //public String password;
    Button button,button3;
    int count;
    EditText ename,eemail,enumber,epassword;



    //public  email;
    //private String name;
    // public int number;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        // Log.e("X","yolo");
        count=0;

        button3=findViewById(R.id.button3);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                back();
            }
        });

        button = findViewById(R.id.button2);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });
    }

    private void back() {
        Intent intent = new Intent(RegistrationActivity.this, MainActivity.class);
        startActivity(intent);
    }

    public void register() {
        //private String  password,name,email;
        // int number;
        //  String x="sahil";
        ename = (EditText) findViewById(R.id.ename);
        enumber = (EditText) findViewById(R.id.enumber);
        eemail = (EditText) findViewById(R.id.eemail);
        epassword = (EditText) findViewById(R.id.epassword);
        //Log.e("X","Xolo"+x);
//            FirebaseDatabase database = FirebaseDatabase.getInstance();
//            DatabaseReference myRef = database.getReference("message");
//
//            myRef.setValue("Hello, World!");


//       boolean isEmpty(EditText naya) {
//           CharSequence str = naya.getText().toString();
//           return TextUtils.isEmpty(str);
//       }
        count=1;

        if (isEmpty(ename)) {
            //  Toast t = Toast.makeText(this, "You must enter first name to register!", Toast.LENGTH_SHORT);
            // t.show();
            //  ename.requestFocus();
            ename.setError("Enter name");

            count=0;
        }


        if (isEmpty(enumber)) {
            //  Toast t = Toast.makeText(this, "You must enter first number to register!", Toast.LENGTH_SHORT);
            //  t.show();
            //enumber.requestFocus();
            enumber.setError("Enter number");

            count=0;
        }


        if (isEmpty(eemail)) {
            //  Toast t = Toast.makeText(this, "You must enter first email to register!", Toast.LENGTH_SHORT);
            //   t.show();
            eemail.setError("Enter email");
            count=0;
        }


        if (isEmpty(epassword)) {
            //  Toast t = Toast.makeText(this, "You must enter first password to register!", Toast.LENGTH_SHORT);
            // t.show();
            epassword.requestFocus();
            count=0;
            epassword.setError("Enter password");

        }

        int length = epassword.getText().length();

        if (length < 8) {
            // Toast t = Toast.makeText(this, "Password must be of atleast 8 letters!", Toast.LENGTH_SHORT);
            //  t.show();
            epassword.requestFocus();
            count=0;
            epassword.setError("Enter password of atleast 8 length");

        }


        DatabaseReference mDatabase;
        mDatabase = FirebaseDatabase.getInstance().getReference();

        String s1 = ename.getText().toString();
        //  String s2 = enumber.getText().toString();
        // int s3=Integer.parseInt(s2);

        String s4 = eemail.getText().toString();
        String s5 = epassword.getText().toString();
        //     Random random = new Random();



        // RegistrationActivity user = new RegistrationActivity();
        //user.Registrations(s1,s4,s5);
        data d=new data(s1,s4,s5);
        //  Log.e("X","Registrationsd: "+name);
        // Log.e("c", "register1: "+d.name );
        //mDatabase.setValue(d);
        //mDatabase.child(Integer.toString(random.nextInt(100))).setValue(d);
        if(count==1)
        {
            mDatabase.push().setValue(d);
            Intent intent = new Intent(RegistrationActivity.this, MainActivity.class);
            startActivity(intent);
        }
    }

    private boolean isEmpty(EditText c) {
        CharSequence str = c.getText().toString();
        return TextUtils.isEmpty(str);
    }

//
//    public  void Registrations(String s1,String s4, String s5) {
//
//        name = s1;
//        email = s4;
//        //  this.number=number;
//        password=s5;
//        //Log.e("X","Registrations: "+this.name);
//    }

}
