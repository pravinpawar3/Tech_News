package com.pravin.newsfeeds;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class AddNews extends AppCompatActivity {
    private Button add;
    private ImageView addimage_;
    private EditText title_, description_;
    private Uri imageUri = null;
    private static int GALLERY_REQUEST = 1;
    private StorageReference storeimage;
    private DatabaseReference database;
    private ProgressDialog loading;
    private Spinner tech_list_;
    private Uri dowmloadUrl;
    private StorageReference imagepath;
    private DatabaseReference upload;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_news);

        //Get FireBase DataBase Storage Instance
        storeimage = FirebaseStorage.getInstance().getReference();
        database = FirebaseDatabase.getInstance().getReference().child("Tech News");

        //Link Id with its Object
        add = (Button) findViewById(R.id.addcard);
        addimage_ = (ImageView) findViewById(R.id.addimage);
        title_ = (EditText) findViewById(R.id.title);
        description_ = (EditText) findViewById(R.id.description);
        tech_list_ = (Spinner) findViewById(R.id.tech_list);
        loading = new ProgressDialog(this);

        addimage_.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Switch to Camera To Get Image
                Intent getimage = new Intent(Intent.ACTION_GET_CONTENT);
                getimage.setType("image/*");
                startActivityForResult(getimage, GALLERY_REQUEST);
            }
        });


        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Get The Title,Description and List in the respective TextBox
                final String title = title_.getText().toString().trim();
                final String description = description_.getText().toString().trim();
                final String list = tech_list_.getSelectedItem().toString();

                if (!TextUtils.isEmpty(title) && !TextUtils.isEmpty(description) && imageUri != null) {
                    loading.setMessage("Loading Please Wait....");
                    loading.show();
                    imagepath = storeimage.child("Newsfeed Images").child(imageUri.getLastPathSegment());
                    imagepath.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {

                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            //Upload New To DataBase
                            dowmloadUrl = taskSnapshot.getDownloadUrl();
                            upload = database.push();
                            upload.child("Title").setValue(title);
                            upload.child("Topic").setValue(list);
                            upload.child("Description").setValue(description);
                            upload.child("Image").setValue(dowmloadUrl.toString());
                            loading.dismiss();

                            //After Uploading Data Move To MainActivity
                            Intent gohome = new Intent(AddNews.this, MainActivity.class);
                            startActivity(gohome);

                            AddNews.this.finish();

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                            //On Failure Of Add Option
                            loading.dismiss();
                            Toast.makeText(AddNews.this, "Please Try Again Later Something Went Wrong", Toast.LENGTH_LONG).show();
                        }
                    });
                } else {

                    //If SomeField is Left Empty
                    Toast.makeText(AddNews.this, "Please Fill All the field", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GALLERY_REQUEST && resultCode == RESULT_OK) {

            //Get Data From Internet
            imageUri = data.getData();
            addimage_.setImageURI(imageUri);
        }
    }
}
