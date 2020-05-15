package com.example.dripkart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class MainActivity extends AppCompatActivity
{
    FirebaseAuth mAuth;
    EditText eemail,epassword;
    Button button,button1;
    int temp;
    String s1,s2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        button1 = findViewById(R.id.signin);
        button = findViewById(R.id.register);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RegistrationActivity.class);
                startActivity(intent);
            }
        });

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login(v);
            }

        });
    }

    public void login (View v)
    {
        eemail = (EditText) findViewById(R.id.email);
        epassword = (EditText) findViewById(R.id.password);
        s1 = eemail.getText().toString();
        s2 = epassword.getText().toString();

        mAuth.signInWithEmailAndPassword(s1, s2).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.e("d", "signInWithEmail:success");
                    FirebaseUser user = mAuth.getCurrentUser();

                } else {
                    // If sign in fails, display a message to the user.
                    Log.e("d", "signInWithEmail:failure", task.getException());
                    Toast.makeText(MainActivity.this, "Authentication failed.",
                            Toast.LENGTH_SHORT).show();
                    //updateUI(null);
                }

            }
        });
        verify(v);

    }

    public void verify (View v) {
         Log.e("N","aao");
        FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
        String userid=user.getUid();
        Log.e("X","user_id:"+userid);
        final View z=v;

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

        reference.child(userid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

              datas d=dataSnapshot.getValue(datas.class);
                Log.e("X","main callback from storage:"+d.getPassword());
                temp=d.getTemp();
                Log.e("X","Seeing temp value"+temp);

                //Buyer
                if(temp==1)
                {
                    Intent mintent = new Intent(MainActivity.this, ImagesActivity.class);
                    startActivity(mintent);
                }

                //Seller
                else {
                    Intent mintent = new Intent(MainActivity.this, HomeActivity.class);
                    startActivity(mintent);
                }

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        }
}


