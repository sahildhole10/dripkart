package com.example.dripkart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.FileUtils;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class HomeActivity extends AppCompatActivity {

    StorageReference storageReference, ref;
    private final int PICK_IMAGE_REQUEST = 22;
    FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();

    private static final int PICK_PHOTO_FOR_AVATAR = 0;
    private static final int RESULT_LOAD_IMG = 0;
    private StorageReference mStorageRef;
    Uri filePath;
    private static final int SELECT_PICTURE = 1;

  // private static int prod_id=0;
    private String selectedImagePath;
    Button select_image, upload_image;
    EditText name, price;
    public Uri downloadUrl;
    public String itemtype="";
    public Spinner mySpinner;
    private int product_id;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

//         Intent intent = getIntent();
//         int temp = intent.getIntExtra("temp", 0);


        select_image = findViewById(R.id.select_image);
        upload_image = findViewById(R.id.upload_image);
        name = (EditText) findViewById(R.id.yname);
        price = (EditText) findViewById(R.id.yprice);


        select_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelectImage();
            }
        });
        upload_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImage();
            }
        });


         mySpinner = (Spinner) findViewById(R.id.spinner1);

        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(HomeActivity.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.names));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mySpinner.setAdapter(myAdapter);

        mySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view,
                                       int position, long id) {
                Object item = adapterView.getItemAtPosition(position);
                if (item != null) {
                     itemtype= (String) adapterView.getItemAtPosition(position);
                    Log.d("x",":"+itemtype);
                  //  Toast.makeText(MainActivity.this, item.toString(),
                    //        Toast.LENGTH_SHORT).show();
                }
                //Toast.makeText(MainActivity.this, "Selected",
                 //       Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // TODO Auto-generated method stub

            }
        });

    }

    private void SelectImage() {

        // Defining Implicit Intent to mobile gallery
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(
                Intent.createChooser(
                        intent,
                        "Select Image from here..."),
                PICK_IMAGE_REQUEST);
    }

    // Override onActivityResult method
    @Override
    protected void onActivityResult(int requestCode,
                                    int resultCode,
                                    Intent data) {

        super.onActivityResult(requestCode,
                resultCode,
                data);

        // checking request code and result code
        // if request code is PICK_IMAGE_REQUEST and
        // resultCode is RESULT_OK
        // then set image in the image view
        if (requestCode == PICK_IMAGE_REQUEST
                && resultCode == RESULT_OK
                && data != null
                && data.getData() != null) {

            // Get the Uri of data
            filePath = data.getData();
            try {
                // Setting image on image view using Bitmap

                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                //JHOL imageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                // Log the exception
                e.printStackTrace();
            }
        }
    }

    // UploadImage method
    private void uploadImage() {
        final String username = name.getText().toString();
        final String userprice = price.getText().toString();
        final int price_int = Integer.parseInt(userprice);


            if (isEmpty(name)) {
                name.setError("Enter name");
                return;
            }

        if (isEmpty(price)) {
            price.setError("Enter price");
            return;
        }

        if(itemtype=="")
        {
            Toast.makeText(HomeActivity.this, "Select item type",Toast.LENGTH_LONG).show();

        }

        Log.e("X", "lfc:" + username);
        if (filePath != null) {

//            Log.e("X", "uploadImage: "+username );
            // Code for showing progressDialog while uploading
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();
            storageReference = firebaseStorage.getReference();
            // Defining the child of storageReference
            ref
                    = storageReference
                    .child(
                            "images/"+itemtype+"/"
                                    + UUID.randomUUID().toString());

            final FirebaseFirestore db;

            db = FirebaseFirestore.getInstance();

            db.collection("product_id")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    product_id=((Long) document.get("producr_id")).intValue();
                                    product_id++;
                                }

                            } else {
                                Log.e("N", "else");
                            }
                        }
                    });



            ref.putFile(filePath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            downloadUrl = uri;
                            Toast.makeText(HomeActivity.this, "Upload Done", Toast.LENGTH_LONG).show();

                            Map<String, Object> pid = new HashMap<>();
                            pid.put("producr_id",product_id);

                            db.collection("product_id").document("pid")
                                    .set(pid)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Log.d("N", "pid updated!");
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Log.e("N", "Error writing document");
                                        }
                                    });




                            //After upload Complete we have to store the Data to firestore.
                            Map<String, Object> file = new HashMap<>();
                            file.put("url", downloadUrl.toString());// We are using it as String because our data type in Firestore will be String
                            file.put("name", username);
                            file.put("price", price_int);
                            file.put("producr_id", product_id);

                            db.collection(""+itemtype).document(UUID.randomUUID().toString())
                                    .set(file)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Log.d("N", "DocumentSnapshot successfully written!");
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Log.e("N", "Error writing document");
                                        }
                                    });

                        }
                    });

                }
            });

        }
    }


    private boolean isEmpty(EditText c) {
        CharSequence str = c.getText().toString();
        return TextUtils.isEmpty(str);
    }

}