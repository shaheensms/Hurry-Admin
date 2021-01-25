package com.metacoders.hurryadmin.Users;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.metacoders.hurryadmin.GalleryView;
import com.metacoders.hurryadmin.Models.driverProfileModel;
import com.metacoders.hurryadmin.Models.userModel;
import com.metacoders.hurryadmin.R;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserProfile extends AppCompatActivity {
    TextView name,phn_num,email;
    userModel model;
    CircleImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        name = findViewById(R.id.name);
        phn_num = findViewById(R.id.phn_num);
        email = findViewById(R.id.fff);
        imageView  = findViewById(R.id.img) ;

        model = (userModel) getIntent().getSerializableExtra("MODEL") ;
        if(model!= null){
            setView(model);
        }
        email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<String> imageList = new ArrayList<>();
                imageList.add(model.getNid_front()) ;
                imageList.add(model.getNid_back()) ;
                imageList.add(model.getUserProPic());

                Intent p = new Intent(getApplicationContext() , GalleryView.class);
                p.putExtra( "LIST", imageList) ;
                startActivity(p);


            }
        });
    }

    private void setView(userModel model) {

        name.setText(model.getUserName());
        phn_num.setText(model.getPhone());

        Glide.with(getApplicationContext()).load(model.getUserProPic())
                .into(imageView) ;




    }
}