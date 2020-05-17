package com.example.dripkart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CartActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private CartAdapter mAdapter;
    private ProgressBar mProgressCircle;
    Map<String, Object> file = new HashMap<>();
    public int total;
    Button checkout;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    String id;
    private List<Uploadurl> mUploads;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mProgressCircle = findViewById(R.id.progress_circle);

        mUploads = new ArrayList<>();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        id=user.getUid();


        checkout=findViewById(R.id.checkout);

        checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                db.collection("Total")
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                         total = ((Long) document.get("total")).intValue();
                                      //  Log.e("xcv","yolo");

                                    }

                                  //  Context mcontext=this;
                                      View v = LayoutInflater.from(this).inflate(R.layout.activity_payment, null);
//
//                                    Button b = (Button) v.findViewById(R.id.checkout);
//                                    b.setOnClickListener(new View.OnClickListener() {
//                                        @Override
//                                        public void onClick(View v) {
//
//                                            TextView payment = (TextView) findViewById(R.id.payment);
//                                            payment.setText(String.valueOf(total));
//
//                                        }
//                                    });

                                        Log.e("XXX","Total price is:"+total);
                                    Intent intent=new Intent(CartActivity.this,PaymentActivity.class);
                                    intent.putExtra("total",total);
                                    startActivity(intent);

                                } else {
                                    Log.e("N", "else");
                                }



                            }
                        });




            }
        });

//        checkout=(Button)findViewById(R.id.checkout);
//        checkout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });

         db.collection("cartdata").document(id)
                .collection("pid").get()
        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        file= document.getData();
                     String name= (String) file.get("name");
                        String url= (String) file.get("url");
                        int price= ((Long) file.get("price")).intValue();
                     int product_id= ((Long) file.get("product_id")).intValue();
                      Uploadurl cartsdata =new Uploadurl(url,name,price,product_id);
                      mUploads.add(cartsdata);
                    }
                    mAdapter = new CartAdapter(CartActivity.this, mUploads);
                    mRecyclerView.setAdapter(mAdapter);
                    //  mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
                    mProgressCircle.setVisibility(View.INVISIBLE);
                } else {
                    Log.d("XZ", "Error getting documents: ", task.getException());
                }
            }
        });






    }
}

