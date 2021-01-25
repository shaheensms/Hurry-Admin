package com.metacoders.hurryadmin.ambulance;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.metacoders.hurryadmin.AllTripList.tripDetails;
import com.metacoders.hurryadmin.Models.TransactionsModel;
import com.metacoders.hurryadmin.Models.ambulanceModel;
import com.metacoders.hurryadmin.Models.modelForCarRequest;
import com.metacoders.hurryadmin.R;
import com.metacoders.hurryadmin.ViewHolders.viewholdersForCurrentTrip;

public class allAmbulancePage extends AppCompatActivity {
    RecyclerView mrecyclerview;
    LinearLayoutManager linearLayoutManager;
    DatabaseReference mref;
    FirebaseRecyclerOptions<ambulanceModel> options;
    FirebaseRecyclerAdapter<ambulanceModel, viewholdersForCurrentTrip> firebaseRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_trip_page);

        mref = FirebaseDatabase.getInstance().getReference("req_ambulanceDb"); // db link
        mrecyclerview = findViewById(R.id.currentList);
        linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setStackFromEnd(true);
        linearLayoutManager.setReverseLayout(true);

        mrecyclerview.setLayoutManager(linearLayoutManager);

        mref.keepSynced(true);
        loadDataToFireBase();

    }

    private void loadDataToFireBase() {


        options = new FirebaseRecyclerOptions.Builder<ambulanceModel>().setQuery(mref, ambulanceModel.class).build();
        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<ambulanceModel, viewholdersForCurrentTrip>(options) {
            @Override
            protected void onBindViewHolder(@NonNull viewholdersForCurrentTrip viewholdersForCurrentTrip, final int i, @NonNull ambulanceModel model) {

                viewholdersForCurrentTrip.setDataToView(getApplicationContext(),
                        "model.getPostId()", "model.getUserId()", "model.getUserNotificationID()"," model.getDriverId()", "model.getDriverNotificationID",
                        "Unknown", "Unknown", model.getReq_date(), "model.getCarModl()", "model.getDriverName()",
                        model.getStatus(), "Unknown", "n/a", "model.getCarType()",
                        model.getReq_date(), " ", " ");


            }

            @NonNull
            @Override
            public viewholdersForCurrentTrip onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                View iteamVIew = LayoutInflater.from(parent.getContext()).inflate(R.layout.all_trip_view_module, parent, false);
                final viewholdersForCurrentTrip viewholders = new viewholdersForCurrentTrip(iteamVIew);


                viewholdersForCurrentTrip.setOnClickListener(new viewholdersForCurrentTrip.Clicklistener() {
                    @Override
                    public void onItemClick(View view, final int postion) {

                        //go to the Trip details page to mark it done

                        Intent o = new Intent(getApplicationContext(), ambulance_req_details.class);
                        ambulanceModel model = getItem(postion) ;
                        //carry data to their
                        o.putExtra("MODEL" ,model );

                        startActivity(o);


                    }
                });


                return viewholders;
            }
        };
        mrecyclerview.setLayoutManager(linearLayoutManager);
        firebaseRecyclerAdapter.startListening();
        mrecyclerview.setAdapter(firebaseRecyclerAdapter);


    }

    @Override
    public void onStart() {

        loadDataToFireBase();
        super.onStart();

    }


}