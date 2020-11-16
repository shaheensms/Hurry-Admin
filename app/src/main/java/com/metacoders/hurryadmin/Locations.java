package com.metacoders.hurryadmin;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class Locations extends AppCompatActivity {

    EditText cityName , townName ;
    Button addCity , addTown ;
    ArrayList<modelForSpinner> cityListTo = new ArrayList<>();
    ArrayList<String> CityNameListto = new ArrayList<>();
    Spinner citySpinnerTo ;
    String cityto ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_locations);
        cityName = findViewById(R.id.cityName);
        addCity = findViewById(R.id.addCity);
        citySpinnerTo = findViewById(R.id.citySpinnerTo);
        addTown =findViewById(R.id.addTown) ;
        townName =findViewById(R.id.townName);
        loadCity();
        addCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cityName.getText().toString().length() != 0) {
                    addCity(cityName.getText().toString());
                }

            }
        });

        addTown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (townName.getText().toString().length() != 0) {
                    addTown(townName.getText().toString());
                }
            }
        });
    }

    private void addTown(final String townNAME) {

        DatabaseReference mreference = FirebaseDatabase.getInstance().getReference("town").child(cityto)
                .child(System.currentTimeMillis()+"");
        HashMap<String, Object> map = new HashMap<>();
        map.put("name", townNAME);
        mreference.setValue(map).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                townName.setText("");
                Toast.makeText(getApplicationContext(), "Town Added", Toast.LENGTH_SHORT).show();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), "Error : " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void addCity(String city) {
        DatabaseReference mreference = FirebaseDatabase.getInstance().getReference("city").child(city.toLowerCase());
        HashMap<String, Object> map = new HashMap<>();
        map.put("name", city);
        mreference.setValue(map).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                cityName.setText("");
                Toast.makeText(getApplicationContext(), "City Added", Toast.LENGTH_SHORT).show();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), "Error : " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void loadCity() {
        DatabaseReference deptReference = FirebaseDatabase.getInstance().getReference("city");
        deptReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                cityListTo.clear();
                CityNameListto.clear();

                cityListTo.add(new modelForSpinner("Choose a City To Add Town")) ;

                //iterating through all the nodes
                for (DataSnapshot deptSnapshot : dataSnapshot.getChildren()) {
                    //getting departments
                    modelForSpinner departments = deptSnapshot.getValue(modelForSpinner.class);
                    //adding department to the list
                    cityListTo.add(departments);
                }

                if (cityListTo.size() > 0) {
                    for (int i = 0; i < cityListTo.size(); i++) {
                        CityNameListto.add(cityListTo.get(i).getName());
                    }
                }

                //creating adapter
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(Locations.this, android.R.layout.simple_list_item_activated_1, CityNameListto);
                citySpinnerTo.setAdapter(arrayAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        citySpinnerTo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                cityto = cityListTo.get(position).getName();
                Toast.makeText(getApplicationContext() , "City "+ cityto , Toast.LENGTH_LONG).show();


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}