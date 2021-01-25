package com.metacoders.hurryadmin.Users;

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
import com.metacoders.hurryadmin.Adapter.UserListAdapter;
import com.metacoders.hurryadmin.Constants.Const;
import com.metacoders.hurryadmin.Models.userModel;
import com.metacoders.hurryadmin.R;

import java.util.ArrayList;
import java.util.List;

public class userList extends AppCompatActivity implements UserListAdapter.ItemClickListener {

    EditText searchEdit;
    MaterialButton searchButton;
    RecyclerView listRcv;
    String searchText;
    DatabaseReference mref;
    List<userModel> userProfileModelList = new ArrayList<>();
    UserListAdapter adapter;
    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_list);
        mref = FirebaseDatabase.getInstance().getReference(Const.UserProfileDirectory);
        // view
        swipeRefreshLayout = findViewById(R.id.swipeRefresh);

        listRcv = findViewById(R.id.list);
        searchButton = findViewById(R.id.search_button);
        searchEdit = findViewById(R.id.search_edit);

        listRcv.setLayoutManager(new LinearLayoutManager(this));
        userProfileModelList.clear();
        adapter = new UserListAdapter(getApplicationContext(), userProfileModelList, this);
        listRcv.setAdapter(adapter);
        loadTheList();

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchText = searchEdit.getText().toString();
                searchText = "+88" + searchText;

                if (!searchText.isEmpty()) {
                    SearchForData(userProfileModelList, searchText);
                } else {
                    loadTheList();
                }
            }
        });


        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(true);
                userProfileModelList.clear();

                loadTheList();
            }
        });

    }

    private void SearchForData(List<userModel> driverProfileModelList, String searchText) {
        List<userModel> fillerTedList = new ArrayList<>();
        fillerTedList.clear();

        for (userModel item : driverProfileModelList) {
            if (item.getPhone().contains(searchText)) {
                fillerTedList.add(item);

            }
        }

        listRcv.setAdapter(new UserListAdapter(getApplicationContext(), fillerTedList, userList.this));
    }

    private void loadTheList() {
        userProfileModelList.clear();
        mref.keepSynced(true);
        mref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                Log.d("TAG ", "" + snapshot.getChildrenCount());
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    userModel profileModel = postSnapshot.getValue(userModel.class);
                    userProfileModelList.add(profileModel);
                }
                try {
                    swipeRefreshLayout.setRefreshing(false);
                } catch (Exception e) {

                }
                setupRecylerView(userProfileModelList);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void setupRecylerView(List<userModel> userProfileModelList) {

        listRcv.setAdapter(new UserListAdapter(getApplicationContext(), userProfileModelList, userList.this));


    }


    @Override
    public void onItemClick(userModel model) {
        Intent p = new Intent(getApplicationContext(), UserProfile.class);
        p.putExtra("MODEL", model);
        startActivity(p);
    }
}