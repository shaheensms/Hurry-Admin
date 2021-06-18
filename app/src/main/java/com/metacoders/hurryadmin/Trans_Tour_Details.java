package com.metacoders.hurryadmin;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.card.MaterialCardView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.metacoders.hurryadmin.Models.DriverWalletModel;
import com.metacoders.hurryadmin.Models.TransactionsModel;
import com.metacoders.hurryadmin.Models.driverProfileModel;
import com.metacoders.hurryadmin.Models.modelForCarRequest;
import com.metacoders.hurryadmin.Models.userModel;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class Trans_Tour_Details extends AppCompatActivity {

    TransactionsModel transactionsModel;

    String driverName, carModel, fromLoc, toLoc, fare, time, postID, driverID, driverNottificationID,
            tripDetails, status, triptype, driverFine, driverTotalTrip = "0", driverIncome = "0", driverTripCountThisMOn = "0",
            driverLifeTimeIncome = "0", transID;
    String userTotalTrip = "0", userFined, userSpent = "0";

    Button submit, accept;
    MaterialCardView payInfoCard, driverInfoCard;
    String uid = "TEST", driverUid = "";
    TextView DriverNAME, CARMODEL, drivername, FROMLOC, TOLOC, FARE, TIME, POSTID, DRIVERID, DRIVERNOTTIFICATIONID, descTv, typeTv, fareTv;
    LinearLayout ratingCOntainer;
    TextView trans_id, trans_date, type, transAmount, userName, userPhone, phoneNumINTripDetails,
            driverIDInTripDetails, carModelInTripDetais, statusTV;
    CircleImageView userImage, driverImage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trans__tour__details);
        transactionsModel = (TransactionsModel) getIntent().getSerializableExtra("TRANSMODEL");

        phoneNumINTripDetails = findViewById(R.id.phoneNumINTripDetails);
        driverIDInTripDetails = findViewById(R.id.driverIDInTripDetails);
        carModelInTripDetais = findViewById(R.id.carModelInTripDetais);
        trans_id = findViewById(R.id.trxId);
        trans_date = findViewById(R.id.transtype);
        type = findViewById(R.id.timeTv);
        drivername = findViewById(R.id.driverNameinTripDetails);
        transAmount = findViewById(R.id.advanceViewInTripDetails);
        userImage = findViewById(R.id.userPic);
        driverImage = findViewById(R.id.driverPic);
        //   DriverNAME =findViewById(R.id.driverNameTripDeatils)  ;
        //  CARMODEL=findViewById(R.id.carModelTripDetails)  ;
        FROMLOC = findViewById(R.id.locationFromTripDetails);
        TOLOC = findViewById(R.id.locationToTripDetalis);
        FARE = findViewById(R.id.priceViewInTripDetails);
        TIME = findViewById(R.id.dateOfTripDetails);
//        description = findViewById(R.id.feedBackDetails);
        submit = findViewById(R.id.sumbutBTnTripdetails);
//        stepView = findViewById(R.id.step_view);
        accept = findViewById(R.id.acceptBTnTripdetails);
        payInfoCard = findViewById(R.id.advacePayInfoCard);
        driverInfoCard = findViewById(R.id.driverInfoCard);
//        mRateBar = findViewById(R.id.ratingBarBidTripDetails);
        fareTv = findViewById(R.id.fareTv);
        typeTv = findViewById(R.id.typeTv);
//        ratingCOntainer = findViewById(R.id.ratingCOntainer) ;
        descTv = findViewById(R.id.descTv);
        userName = findViewById(R.id.userNameinTripDetails);
        userPhone = findViewById(R.id.userphoneNumINTripDetails);
        statusTV = findViewById(R.id.status);


//        trans_id.setText(transactionsModel.getTrxID());
//        trans_date.setText(transactionsModel.getTime());
//        type.setText(transactionsModel.getPayment_type());
//        transAmount.setText(transactionsModel.getAmountPaid());
        loadTripDetails(transactionsModel.getTripId());
        loadUserData(transactionsModel.getUserUid());

        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AcceptTrip(transactionsModel.getTripId(), transactionsModel.getTrxID());
            }
        });


    }

    private void loadTripDetails(String uid) {
        DatabaseReference mref = FirebaseDatabase.getInstance().getReference("reqCarDb").child(uid);
        mref.keepSynced(true);
        mref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    modelForCarRequest tripModel = snapshot.getValue(modelForCarRequest.class);
                    driverName = tripModel.getDriverName();
                    carModel = tripModel.getCarModl();
                    fromLoc = tripModel.getFromLoc();
                    status = tripModel.getStatus();
                    toLoc = tripModel.getToLoc();
                    fare = tripModel.getFare();
                    time = tripModel.getTimeDate();
                    postID = tripModel.getPostId();
                    driverID = tripModel.getDriverId();
                    driverNottificationID = tripModel.getDriverNotificationID();
                    tripDetails = tripModel.getTripDetails();
                    triptype = tripModel.getRideType();
                    transID = tripModel.getTransId();
                    // setting at here
                    drivername.setText(driverName);
                    // CARMODEL.setText(carModel);
                    FROMLOC.setText(fromLoc);
                    TOLOC.setText(toLoc);
                    TIME.setText(time);
                    FARE.setText(fare);
                    descTv.setText(tripDetails);
                    fareTv.setText(fare);
                    typeTv.setText(triptype);
                    loadDriverData(tripModel.getDriverId());

                    if (tripModel.getTransId().toLowerCase().equals("null")) {
                        statusTV.setText(status);
                        accept.setVisibility(View.VISIBLE);


                    } else {
                        accept.setVisibility(View.GONE);
                        statusTV.setText(status + " With Payment " + transactionsModel.getAmountPaid() + " BDT");
                    }

                } else {
                    Toast.makeText(getApplicationContext(), "There is no such Trip . ", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), "Error " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void loadUserData(String userUid) {
        DatabaseReference mref1 = FirebaseDatabase.getInstance().getReference("userProfile").child(userUid);
        mref1.keepSynced(true);
        mref1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    userModel userModel = snapshot.getValue(userModel.class);

                    userName.setText(userModel.getUserName());
                    userPhone.setText(userModel.getPhone());
                    Glide.with(getApplicationContext()).load(userModel.getUserProPic())
                            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                            .into(userImage);

                } else {

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    private void loadDriverData(String dUid) {
        DatabaseReference mref12 = FirebaseDatabase.getInstance().getReference("driverProfile").child(dUid);
        mref12.keepSynced(true);
        mref12.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    driverProfileModel dModel = snapshot.getValue(driverProfileModel.class);

                    Glide.with(getApplicationContext()).load(dModel.getProfile_picture())
                            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                            .into(driverImage);
                    phoneNumINTripDetails.setText(dModel.getPhone());
                    ;
                    driverIDInTripDetails.setText(dModel.getCarLic());
                    carModelInTripDetais.setText(dModel.getCarModel());

                } else {
                    Toast.makeText(getApplicationContext(), "There is no Driver . ", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    private void AcceptTrip(String postID, String trx) {
        /*
        here we add trx id  on the transaction
         */
        HashMap<String, Object> map = new HashMap<>();
        map.put("transId", trx);
        DatabaseReference mreff = FirebaseDatabase.getInstance().getReference("reqCarDb").child(postID);
        mreff.keepSynced(true);
        mreff.updateChildren(map).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {


                DatabaseReference mref = FirebaseDatabase.getInstance().getReference("").child(transactionsModel.getTripId()).child("status");
                mref.setValue("complete");


                CreateDriverWallet(
                        transactionsModel.getAmountPaid()
                );
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), "Error : Could Not Accept " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }


    private void CreateDriverWallet(String amount) {
        double totalAmount = Double.parseDouble(fareTv.getText().toString());
        double clientPaidInAdvance = Double.parseDouble(amount);
        // so , now  calculate -> they will  get 20 % Admin will get 10%
        double driverWillGet = totalAmount * 0.20;
        // now register it to the servers
        DatabaseReference mref = FirebaseDatabase.getInstance().getReference("wallet").child(driverID);
        String key = mref.push().getKey();

        DriverWalletModel model = new DriverWalletModel(System.currentTimeMillis() + "", transactionsModel.getTripId(), driverID, driverWillGet, true);
        //

        mref.child(key).setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                finish();
            }
        });

    }


}