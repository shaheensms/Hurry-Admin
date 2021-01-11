package com.metacoders.hurryadmin;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.metacoders.hurryadmin.Drivers.DriverList;
import com.metacoders.hurryadmin.Users.userList;

public class DashboardActivity extends AppCompatActivity {

    LinearLayout transactionView ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        transactionView =  findViewById(R.id.transID1);

       transactionView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            Intent p = new Intent(getApplicationContext(), TransactionList.class);
            startActivity(p);
            }
        });

        findViewById(R.id.addLoc).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent p = new Intent(getApplicationContext(), Locations.class);
                startActivity(p);
            }
        });

        findViewById(R.id.allTrips).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog dialog = new AlertDialog.Builder(DashboardActivity.this).create();

                dialog.setTitle("Error ");
                dialog.setCancelable(true);
                dialog.setMessage("Firebase Limit Exceeded ..");
                dialog.show();

            }
        });
        findViewById(R.id.stats).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog dialog = new AlertDialog.Builder(DashboardActivity.this).create();

                dialog.setTitle("Error ");
                dialog.setCancelable(true);
                dialog.setMessage("Firebase Limit Exceeded ..");
                dialog.show();

            }
        });
        findViewById(R.id.carLatout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent p = new Intent(getApplicationContext(), userList.class);
                startActivity(p);

            }
        });
        findViewById(R.id.driverOption).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                AlertDialog dialog = new AlertDialog.Builder(DashboardActivity.this).create();
//
//                dialog.setTitle("Error ");
//                dialog.setCancelable(true);
//                dialog.setMessage("Firebase Limit Exceeded ..");
//                dialog.show();
                Intent p = new Intent(getApplicationContext(), DriverList.class);
                startActivity(p);

            }
        });


        //throw new RuntimeException("Test Crash"); // Force a crash
    }
}