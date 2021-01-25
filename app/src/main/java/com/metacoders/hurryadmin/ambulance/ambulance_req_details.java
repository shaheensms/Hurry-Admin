package com.metacoders.hurryadmin.ambulance;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.metacoders.hurryadmin.Models.ambulanceModel;
import com.metacoders.hurryadmin.R;

public class ambulance_req_details extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ambulance_req_details);
        ambulanceModel model =(ambulanceModel) getIntent().getSerializableExtra("MODEL") ;


        findViewById(R.id.submitBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + model.getUser_ph()));
                startActivity(intent);
            }
        });
    }
}