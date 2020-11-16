package com.metacoders.hurryadmin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

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

        //throw new RuntimeException("Test Crash"); // Force a crash
    }
}