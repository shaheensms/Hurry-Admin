package com.metacoders.hurryadmin.Drivers;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.metacoders.hurryadmin.R;

public class DriverProfile extends AppCompatActivity {

    TextView ac_type, build_company, car_licence, car_model, car_type,car_year,seat_count,driver_id_activity,driver_join_date,name,phn_num,email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_profile);

        ac_type = findViewById(R.id.ac_type);
        build_company = findViewById(R.id.build_company);
        car_licence = findViewById(R.id.car_licence);
        car_model = findViewById(R.id.car_model);
        car_type = findViewById(R.id.car_type);
        car_year = findViewById(R.id.car_year);
        seat_count = findViewById(R.id.seat_count);
        driver_id_activity = findViewById(R.id.driver_id_activity);
        driver_join_date = findViewById(R.id.driver_join_date);
        name = findViewById(R.id.name);
        phn_num = findViewById(R.id.phn_num);
        email = findViewById(R.id.email);
    }
}