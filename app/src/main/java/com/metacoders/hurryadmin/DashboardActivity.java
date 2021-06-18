package com.metacoders.hurryadmin;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.metacoders.hurryadmin.AllTripList.allTripPage;
import com.metacoders.hurryadmin.Drivers.DriverList;
import com.metacoders.hurryadmin.Models.ambulanceModel;
import com.metacoders.hurryadmin.Users.userList;
import com.metacoders.hurryadmin.ambulance.allAmbulancePage;

public class DashboardActivity extends AppCompatActivity {

    LinearLayout transactionView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        transactionView = findViewById(R.id.transID1);

        transactionView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent p = new Intent(getApplicationContext(), TransactionList.class);
                startActivity(p);
            }
        });

        findViewById(R.id.newsFeedList).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent p = new Intent(getApplicationContext(), AddNewsFeed.class);
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
                Intent p = new Intent(getApplicationContext(), allTripPage.class);
                startActivity(p);

            }
        });

        findViewById(R.id.ambulanceReq).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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

        findViewById(R.id.ambulanceReq).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                AlertDialog dialog = new AlertDialog.Builder(DashboardActivity.this).create();
//
//                dialog.setTitle("Error ");
//                dialog.setCancelable(true);
//                dialog.setMessage("Firebase Limit Exceeded ..");
//                dialog.show();
                Intent p = new Intent(getApplicationContext(), allAmbulancePage.class);
                startActivity(p);

            }
        });


        //throw new RuntimeException("Test Crash"); // Force a crash
    }
}