package com.example.dripkart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ImagesActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private ImageAdapter mAdapter;
    private ProgressBar mProgressCircle;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    private DatabaseReference mDatabaseRef;
    private List<Uploadurl> mUploads;
  Button cartbutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_images);

        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mProgressCircle = findViewById(R.id.progress_circle);

        mUploads = new ArrayList<>();

        cartbutton =(Button) findViewById(R.id.cart);

        cartbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(ImagesActivity.this,CartActivity.class);
                startActivity(intent);
            }
        });

                db.collection("downloadurl")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                //  Log.d(TAG, document.getId() + " => " + document.getData());
                                String url = (String) document.get("url");
                                String name = (String) document.get("name");
                                Integer price = ((Long) document.get("price")).intValue();
                                Integer product_id=((Long) document.get("producr_id")).intValue();
                           //     Log.e("L", "url:" + url);
                                Uploadurl upload = new Uploadurl(url, name, price,product_id);
                                mUploads.add(upload);
                            }
                            mAdapter = new ImageAdapter(ImagesActivity.this, mUploads);
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




