package com.metacoders.hurryadmin;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.metacoders.hurryadmin.Models.NewsfeedModel;
import com.metacoders.hurryadmin.ViewHolders.viewholderForNewsfeed;

import java.io.File;

public class AddNewsFeed extends AppCompatActivity {

    RecyclerView recyclerView;
    EditText title, description;
    ImageView imageView;
    Uri imageUri;
    String imagePath, imageUrl;
    StorageReference imageRef;
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_news_feed);
        title = findViewById(R.id.title);
        description = findViewById(R.id.descTv);
        imageView = findViewById(R.id.image);
        btn = findViewById(R.id.updateBtn);
        recyclerView = findViewById(R.id.newsFeedList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        imageRef = FirebaseStorage.getInstance().getReference("news_feed_image");
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                    if (ContextCompat.checkSelfPermission(AddNewsFeed.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

                        ActivityCompat.requestPermissions(AddNewsFeed.this,
                                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);

                        BringImagePicker();


                    } else {

                        BringImagePicker();

                    }

                } else {

                    BringImagePicker();

                }
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String titlet, desc;
                titlet = title.getText().toString();
                desc = description.getText().toString();
                if (titlet.isEmpty() || desc.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Fill the form", Toast.LENGTH_SHORT).show();

                } else {
                    updateData(titlet, desc);
                }
            }
        });

        loadNewsFeed();
    }

    private void updateData(String titlet, String desc) {
        NewsfeedModel model = new NewsfeedModel();
        model.setTitle(titlet);
        model.setDesc(desc);
        model.setTimestamp(System.currentTimeMillis()+"");
        ;
        model.setImage(imageUrl);
        ProgressDialog dialog1 = new ProgressDialog(AddNewsFeed.this);
        dialog1.setMessage("Creating Profile...");
        dialog1.show();
        DatabaseReference m = FirebaseDatabase.getInstance().getReference().child("newsfeed");
        String key = m.push().getKey();
        model.setPost_id(key);
        m.child(key).setValue(model)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        dialog1.dismiss();
                        title.setText("");
                        description.setText("");
                        imageView.setImageDrawable(getResources().getDrawable(R.drawable.ic_baseline_add_24));
                        loadNewsFeed();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                dialog1.dismiss();
                Toast.makeText(getApplicationContext(), "Error : " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    void loadNewsFeed() {
        FirebaseRecyclerOptions<NewsfeedModel> options;
        FirebaseRecyclerAdapter<NewsfeedModel, viewholderForNewsfeed> firebaseRecyclerAdapter;
        DatabaseReference mref = FirebaseDatabase.getInstance().getReference("newsfeed");

        options = new FirebaseRecyclerOptions.Builder<NewsfeedModel>().setQuery(mref, NewsfeedModel.class).build();

        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<NewsfeedModel, viewholderForNewsfeed>(options) {
            @Override
            protected void onBindViewHolder(@NonNull viewholderForNewsfeed holder, int position, @NonNull NewsfeedModel model) {

                holder.setDataToView(getApplicationContext(), model);

            }

            @NonNull
            @Override
            public viewholderForNewsfeed onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View iteamVIew = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_newsfeed, parent, false);
                final viewholderForNewsfeed viewholders = new viewholderForNewsfeed(iteamVIew);


                return viewholders;
            }
        };

        firebaseRecyclerAdapter.startListening();
        recyclerView.setAdapter(firebaseRecyclerAdapter);
    }

    private void BringImagePicker() {
        ImagePicker.Companion.with(AddNewsFeed.this)
                .galleryOnly()
                //Crop image(Optional), Check Customization for more option
                .compress(1024)            //Final image size will be less than 1 MB(Optional)
                .start();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            imageUri = data.getData();
            imagePath = ImagePicker.Companion.getFilePath(data);
            uploadItToStorage(imageUri, imagePath);

        } else {
            Toast.makeText(getApplicationContext(), "Please Pick An Image !!!", Toast.LENGTH_SHORT).show();
        }
    }

    private void uploadItToStorage(Uri imageUri, String imagePath) {

        ProgressDialog dialog = new ProgressDialog(AddNewsFeed.this);
        dialog.setMessage("Uploading The Image...");

        if (imageUri != null) {
            imageView.setImageURI(imageUri);
        }
        try {
            File imageFile = new File(imagePath);
            Uri image = Uri.fromFile(imageFile);
            if (!imagePath.isEmpty()) {
                dialog.show();
                UploadTask fileUploadTask = imageRef.child(System.currentTimeMillis() + ".jpg").putFile(image);
                fileUploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        dialog.dismiss();
                        Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                        while (!uriTask.isSuccessful()) ;
                        Uri download_Uri = uriTask.getResult();
                        imageUrl = download_Uri.toString();

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        dialog.dismiss();
                        Toast.makeText(getApplicationContext(),
                                "Try again : " + e.getMessage(),
                                Toast.LENGTH_SHORT).show();
                    }
                });
            } else {
                Toast.makeText(getApplicationContext(), "Pick Image Again !!",
                        Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Error : " + e.getMessage(),
                    Toast.LENGTH_SHORT).show();
        }

    }
}