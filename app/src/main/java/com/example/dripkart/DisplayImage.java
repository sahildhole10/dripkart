
package com.example.dripkart;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class DisplayImage extends AppCompatActivity {

    int count;
    ImageView myImage;
    String url ,name,email;
    int price,product_id;
    Button cart_button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);

        Intent intent=getIntent();
        Bundle b = intent.getExtras();

        url = intent.getStringExtra("image_url");
        name = intent.getStringExtra("name");
        price = intent.getIntExtra("price",1);
        product_id = intent.getIntExtra("product_id",1);

        myImage = findViewById(R.id.myImage);
        Picasso.get()
                .load(url)
                .placeholder(R.mipmap.ic_launcher)
                .fit()
                //    .centerCrop()
                .into(myImage);

        final HashMap<String,Object> cartMap=new HashMap<>();


        cart_button= (Button) findViewById(R.id.button);
        cart_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cartMap.put("product_id", product_id);
                cartMap.put("name", name);
                cartMap.put("price", price);

                //   FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
                //   String userid=user.getUid();
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user != null) {
                    email = user.getEmail();
                    Log.d("X", "user email:" + email);
                }

//See whether user's cart exist or not
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                DocumentReference docIdRef = db.collection("/cartdata/").document(email);
                docIdRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                Log.d("X", "Document exists!");
                                count = 1;
                                check(product_id,email);

                            } else {
                                Log.d("X", "Document does not exist!");
                                count = 0;
                                check(product_id,email);

                            }
                        } else {
                            Log.d("X", "Failed with: ", task.getException());
                        }

                    }
                });

            }

            private void check(int product_id,String email) {

                Log.d("X", "count:"+count);
                FirebaseFirestore db = FirebaseFirestore.getInstance();

                //If does not exist create new one
                if (count == 0) {
                    db = FirebaseFirestore.getInstance();
                    db.collection("/cartdata/").document(email).collection("/cartdata/"+email).document(String.valueOf(product_id))
                            .set(cartMap)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d("N", "DocumentSnapshot successfully written of cartmap!");
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d("N", "Error writing document of cartmap");
                                }
                            });
                }
                //If does exist update user
                else if(count==1){

                    db = FirebaseFirestore.getInstance();

                    db.collection("/cartdata/").document(email).collection("/cartdata/"+email).document(String.valueOf(product_id))
                            .update(cartMap)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d("X", "DocumentSnapshot successfully updated!");
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d("X", "Error updating document");
                                }
                            });
                }


            }
        });
    }


}

