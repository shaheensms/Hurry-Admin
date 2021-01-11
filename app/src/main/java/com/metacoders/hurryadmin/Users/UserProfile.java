package com.metacoders.hurryadmin.Users;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.metacoders.hurryadmin.R;

public class UserProfile extends AppCompatActivity {
    TextView name,phn_num,email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        name = findViewById(R.id.name);
        phn_num = findViewById(R.id.phn_num);
        email = findViewById(R.id.email);
    }
}