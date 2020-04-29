package com.example.dripkart;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Random;

public class RegistrationActivity extends AppCompatActivity {

    Button button,button3;
    int count;
    EditText ename,eemail,enumber,epassword;
    FirebaseAuth mAuth;

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
          ename = (EditText) findViewById(R.id.ename);
        enumber = (EditText) findViewById(R.id.enumber);
        eemail = (EditText) findViewById(R.id.eemail);
        epassword = (EditText) findViewById(R.id.epassword);
        count=1;


        String s1 = ename.getText().toString();

        String s4 = eemail.getText().toString();
        String s5 = epassword.getText().toString();


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
        if (!Patterns.EMAIL_ADDRESS.matcher(s4).matches()) {
            eemail.setError("Please enter a valid email");
        //    editTextEmail.requestFocus();
            count=0;
            return;
        }

        DatabaseReference mDatabase;
        mDatabase = FirebaseDatabase.getInstance().getReference();

          data d=new data(s1,s4,s5);
         if(count==1) {
             mDatabase.push().setValue(d);
             Intent intent = new Intent(RegistrationActivity.this, MainActivity.class);
             startActivity(intent);


             mAuth.signInWithEmailAndPassword(s4, s5).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                 @Override
                 public void onComplete(@NonNull Task<AuthResult> task) {
                     //        progressBar.setVisibility(View.GONE);
                     if (task.isSuccessful()) {
                         finish();
                         Intent intent = new Intent(RegistrationActivity.this, MainActivity.class);
                         intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                         startActivity(intent);
                     } else {
                         Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                     }
                 }
             });
         }
    }
    private boolean isEmpty(EditText c) {
        CharSequence str = c.getText().toString();
        return TextUtils.isEmpty(str);
    }

}
