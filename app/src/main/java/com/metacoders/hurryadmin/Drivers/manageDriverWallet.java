package com.metacoders.hurryadmin.Drivers;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.metacoders.hurryadmin.Models.DriverWalletModel;
import com.metacoders.hurryadmin.Models.userModel;
import com.metacoders.hurryadmin.R;
import com.metacoders.hurryadmin.Utils.constants;

import java.util.ArrayList;
import java.util.List;

public class manageDriverWallet extends AppCompatActivity {
    TextView payableWallet, paidWallet;
    MaterialButton materialButton;
    EditText editText;
    String driverID, postID;
    double paidAmount  = 0  , payableAmount = 0 , totalEarnedAmount = 0  , totalGivenAmount = 0   ;

    List<DriverWalletModel>walletList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_driver_wallet);
        payableWallet = findViewById(R.id.payable_amount);
        paidWallet = findViewById(R.id.paid_amount);
        materialButton = findViewById(R.id.add_Payment);
        editText = findViewById(R.id.amountTv);

        driverID = getIntent().getStringExtra("DID") ;


        materialButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!editText.getText().toString().isEmpty()){
                    CreateDriverWallet();
                }

            }
        });

        LoadALlThePaymet();

    }

    private void CreateDriverWallet() {
        double totalAmount = Double.parseDouble(editText.getText().toString());

        DatabaseReference mref = FirebaseDatabase.getInstance().getReference("wallet").child(driverID);
        String key = mref.push().getKey();

        DriverWalletModel model = new DriverWalletModel(System.currentTimeMillis() + "", "Admin Paid", driverID, totalAmount, false);
        //

        mref.child(key).setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                finish();
            }
        });

    }

    private  void  LoadALlThePaymet(){
        walletList.clear();
        DatabaseReference mref = FirebaseDatabase.getInstance().getReference().child("wallet").child(driverID);

        mref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    DriverWalletModel walletModel = postSnapshot.getValue(DriverWalletModel.class);

                    if(walletModel.isEarned()){
                        // driver earned
                        totalEarnedAmount += walletModel.getAmount();

                    }
                    else {
                        totalGivenAmount += walletModel.getAmount();

                    }
                    Log.d("TAG", "onDataChange: "+ walletModel.getAmount());
                    walletList.add(walletModel);


                }
                Log.d("TAG", "EARNED: "+ totalEarnedAmount);
                payableAmount = totalEarnedAmount - totalGivenAmount ;
                Log.d("TAG", "PAYABLE: "+ payableAmount);
                Log.d("TAG", "PAYABLE: "+ payableAmount);
                payableWallet.setText(payableAmount+"");
                paidWallet.setText(totalGivenAmount+"");

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}