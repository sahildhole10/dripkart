package com.example.dripkart;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class RegistrationActivity extends AppCompatActivity {

    Button button,button3;
    int count,temp;
    EditText ename,eemail,epassword;
    FirebaseAuth mAuth;
    public RadioGroup rg1;
    //  RadioGroup radioGroup;
  @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
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
       //   radioGroup=findViewById(R.id.radioGroup);

  }


//
//    public void onRadioButtonClicked(View view) {
//        // Is the button now checked?
//        Log.e("X","Fucker");
//
//        boolean checked = ((RadioButton) view).isChecked();
//
//        // Check which radio button was clicked
//        switch(view.getId()) {
//            case R.id.Buyer:
//                if (checked)
//                    Log.e("X","Buyer");
//                    temp=1;
//                    break;
//            case R.id.Seller:
//                if (checked)
//                 Log.e("X","Seller");
//                    temp=0;
//                    break;
//        }
//    }


    private void back() {
        Intent intent = new Intent(RegistrationActivity.this, MainActivity.class);
        startActivity(intent);
    }

    public void register() {
       // ename = (EditText) findViewById(R.id.ename);

//        RadioGroup rg = (RadioGroup)findViewById(R.id.radioGroup );
//        String radiovalue = ((RadioButton)findViewById(rg.getCheckedRadioButtonId())).getText().toString();
//        if(radiovalue=="Buyer")
//            Log.e("X","Fucker");
//        else
//            Log.e("X","SUcker");

        eemail = (EditText) findViewById(R.id.eemail);
        epassword = (EditText) findViewById(R.id.epassword);
        count=1;
        mAuth = FirebaseAuth.getInstance();

       // String s1 = ename.getText().toString();

        final String s4 = eemail.getText().toString();
        String s5 = epassword.getText().toString();




        if (isEmpty(eemail)) {
            eemail.setError("Enter email");
            count=0;
        }


        if (isEmpty(epassword)) {
            epassword.requestFocus();
            count=0;
            epassword.setError("Enter password");

        }

        int length = epassword.getText().length();

        if (length < 8) {
             epassword.requestFocus();
            count=0;
            epassword.setError("Enter password of atleast 8 length");

        }
        if (!Patterns.EMAIL_ADDRESS.matcher(s4).matches()) {
            eemail.setError("Please enter a valid email");
        //    editTextEmail.requestFocus();
            count=0;
        }

      DatabaseReference mDatabase;
      mDatabase = FirebaseDatabase.getInstance().getReference();
          datas d=new datas(s4,s5);
         if(count==1) {
            mDatabase.push().setValue(d);
//             Intent intent = new Intent(RegistrationActivity.this, MainActivity.class);
//             startActivity(intent);
               //Log.e("X","ky"+s1);

             mAuth.createUserWithEmailAndPassword(s4, s5).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                 @Override
                 public void onComplete(@NonNull Task<AuthResult> task) {
                 //    progressBar.setVisibility(View.GONE);
                     if (task.isSuccessful()) {
                         finish();
                        // Log.e("X","bro"+s4);
                         startActivity(new Intent(RegistrationActivity.this, MainActivity.class));
                     } else {

                         if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                             Toast.makeText(getApplicationContext(), "You are already registered", Toast.LENGTH_SHORT).show();

                         } else {
                             Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                         }

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
