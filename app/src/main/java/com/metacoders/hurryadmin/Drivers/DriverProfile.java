package com.metacoders.hurryadmin.Drivers;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.metacoders.hurryadmin.Constants.Const;
import com.metacoders.hurryadmin.GalleryView;
import com.metacoders.hurryadmin.Models.driverProfileModel;
import com.metacoders.hurryadmin.R;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class DriverProfile extends AppCompatActivity {

    TextView ac_type, build_company, car_licence, car_model, car_type,car_year,seat_count,driver_id_activity,driver_join_date,name,phn_num,email;
    driverProfileModel model ;
    CircleImageView circleImageView ;
    SwitchMaterial switchMaterial  ;
    DatabaseReference mref  ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_profile);

        // recive the model
        switchMaterial = findViewById(R.id.swtich) ;

        circleImageView = findViewById(R.id.img) ;
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

        model = (driverProfileModel) getIntent().getSerializableExtra("MODEL") ;
        mref = FirebaseDatabase.getInstance().getReference(Const.DriverProfileDirectory)
                .child(model.getUserID())
                .child("driverIdActivated");
        setTheView(model);


        switchMaterial.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                 mref.setValue("ACTIVATE").addOnSuccessListener(new OnSuccessListener<Void>() {
                     @Override
                     public void onSuccess(Void aVoid) {
                         Toast.makeText(getApplicationContext(), "Driver Profile Activated" , Toast.LENGTH_SHORT).show();
                     }
                 }).addOnFailureListener(new OnFailureListener() {
                     @Override
                     public void onFailure(@NonNull Exception e) {
                         Toast.makeText(getApplicationContext(), "Error : "+ e.getMessage() , Toast.LENGTH_SHORT).show();
                     }
                 }) ;

                }
                else {
                    mref.setValue("DEACTIVATED").addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(getApplicationContext(), "Driver Profile Activated" , Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getApplicationContext(), "Error : "+ e.getMessage() , Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            }
        });

       driver_id_activity.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               // making the list
               ArrayList<String> arrayList = new ArrayList<String>();
               arrayList.add(model.getProfile_picture())  ;
               arrayList.add(model.getDriver_license_image())  ;
               arrayList.add(model.getNid_card_image())  ;
               arrayList.add(model.getFitness_license_image())  ;
               arrayList.add(model.getTax_token_image())  ;
               arrayList.add(model.getVehicle_reg_image())  ;
               arrayList.add(model.getCarPics().getCar_back_image())  ;
               arrayList.add(model.getCarPics().getCar_front_image())  ;
               arrayList.add(model.getCarPics().getCar_inside_image())  ;
               arrayList.add(model.getCarPics().getCar_side_image())  ;

               Intent p = new Intent(getApplicationContext() , GalleryView.class);
               p.putExtra( "LIST", arrayList) ;
               startActivity(p);


           }
       });

    }

    private void setTheView(driverProfileModel models) {

        car_licence.setText(models.getCarLic());
        car_model.setText(models.getCarModel());
        car_type.setText(models.getCarType());
        car_year.setText(models.getCarYear());
        seat_count.setText(models.getSitCount());
        name.setText(models.getDriverName());
        phn_num.setText(models.getPhone());
        driver_join_date.setText(models.getDriverJoinedDate());
        build_company.setText(models.getBuildCompany()+"");
        email.setText(models.getEmail()+"");
        Glide.with(getApplicationContext())
                .load(model.getProfile_picture())
                .into(circleImageView);
        if(models.getDriverIdActivated().toLowerCase().equals("activate")){
            switchMaterial.setChecked(true);

        }else {
            switchMaterial.setChecked(false);
        }

    }
}