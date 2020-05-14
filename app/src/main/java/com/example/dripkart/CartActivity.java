package com.example.dripkart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class CartActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private CartAdapter mAdapter;
    private ProgressBar mProgressCircle;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    String id;
    private List<Cartdata> mCartdata;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mProgressCircle = findViewById(R.id.progress_circle);

        mCartdata = new ArrayList<>();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        id=user.getUid();


                db.collection("/cartdata/").document(id)
                .collection("pid") .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                 // Log.d(TAG, document.getId() + " => " + document.getData());
                               // Log.d("V","YO");
                                // String url = (String) document.get("url");
                                String name = (String) document.get("name");
                                Integer price = ((Long) document.get("price")).intValue();
                                //Log.d("c","price"+price);
                                Integer product_id=((Long) document.get("producr_id")).intValue();

                                Cartdata cartdata = new Cartdata( name, price,product_id);
                                mCartdata.add(cartdata);
                            }
                            mAdapter = new CartAdapter(CartActivity.this, mCartdata);
                            mRecyclerView.setAdapter(mAdapter);
                            //  mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
                            mProgressCircle.setVisibility(View.INVISIBLE);

                        } else {
                            Log.e("N", "else");
                        }
                    }
                });


    }
}



