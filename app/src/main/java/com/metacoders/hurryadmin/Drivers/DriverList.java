package com.metacoders.hurryadmin.Drivers;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.button.MaterialButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.metacoders.hurryadmin.Adapter.DriverListAdapter;
import com.metacoders.hurryadmin.Constants.Const;
import com.metacoders.hurryadmin.Models.driverProfileModel;
import com.metacoders.hurryadmin.R;

import java.util.ArrayList;
import java.util.List;

public class DriverList extends AppCompatActivity implements DriverListAdapter.ItemClickListener {

    EditText searchEdit;
    MaterialButton searchButton;
    RecyclerView listRcv;
    String searchText;
    DatabaseReference mref;
    List<driverProfileModel> driverProfileModelList = new ArrayList<>();
    DriverListAdapter adapter;
    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_list);
        mref = FirebaseDatabase.getInstance().getReference(Const.DriverProfileDirectory);
        // view
        swipeRefreshLayout = findViewById(R.id.swipeRefresh);

        listRcv = findViewById(R.id.list);
        searchButton = findViewById(R.id.search_button);
        searchEdit = findViewById(R.id.search_edit);

        listRcv.setLayoutManager(new LinearLayoutManager(this));
        driverProfileModelList.clear();
        adapter = new DriverListAdapter(getApplicationContext(), driverProfileModelList, DriverList.this);
        listRcv.setAdapter(adapter);
       // loadTheList();

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchText = searchEdit.getText().toString();
                searchText = "+88" + searchText;

                if (!searchText.isEmpty()) {
                    SearchForData(driverProfileModelList, searchText);
                } else {
                    loadTheList();
                }
            }
        });


        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(true);
                driverProfileModelList.clear();

                loadTheList();
            }
        });

    }

    private void SearchForData(List<driverProfileModel> driverProfileModelList, String searchText) {
        List<driverProfileModel> fillerTedList = new ArrayList<>();
        fillerTedList.clear();

        for (driverProfileModel item : driverProfileModelList) {
            if (item.getPhone().contains(searchText)) {
                fillerTedList.add(item);

            }
        }
        Log.d("TAG", "SearchForData: " + fillerTedList.size());
        listRcv.setAdapter(new DriverListAdapter(getApplicationContext(), fillerTedList, DriverList.this));
    }

    private void loadTheList() {
        driverProfileModelList.clear();
        mref.keepSynced(true);
        mref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {


                Log.d("TAG ", "" + snapshot.getChildrenCount());
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    driverProfileModel profileModel = postSnapshot.getValue(driverProfileModel.class);
                    driverProfileModelList.add(profileModel);
                }
                try {
                    swipeRefreshLayout.setRefreshing(false);
                } catch (Exception e) {

                }
                setupRecylerVIew(driverProfileModelList);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public void setupRecylerVIew(List<driverProfileModel> driverProfileModelList) {

        listRcv.setAdapter(new DriverListAdapter(getApplicationContext(), driverProfileModelList, DriverList.this));


    }

    @Override
    public void onItemClick(driverProfileModel models) {

        Intent p = new Intent(getApplicationContext(), DriverProfile.class);
        p.putExtra("MODEL", models);
        startActivity(p);

    }

    @Override
    protected void onResume() {
        super.onResume();
        loadTheList();
    }
}