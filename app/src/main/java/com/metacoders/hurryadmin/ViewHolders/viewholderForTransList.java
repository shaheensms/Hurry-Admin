package com.metacoders.hurryadmin.ViewHolders;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.metacoders.hurryadmin.Models.TransactionsModel;
import com.metacoders.hurryadmin.R;


public class viewholderForTransList extends RecyclerView.ViewHolder {
    private static viewholderForTransList.Clicklistener mClicklistener;
    public Button acceptButton;
    View mview;
    TextView date, amount, transId, statusTv;


    public viewholderForTransList(@NonNull View itemView) {
        super(itemView);

        mview = itemView;


        //item click
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                mClicklistener.onItemClick(v, getAdapterPosition());

            }
        });


    }

    public static void setOnClickListener(viewholderForTransList.Clicklistener clickListener) {

        mClicklistener = clickListener;


    }

    public void setBidData(Context context, TransactionsModel model) {
        date = mview.findViewById(R.id.date);
        amount = mview.findViewById(R.id.pricTv);
        transId = mview.findViewById(R.id.transID);
        statusTv = mview.findViewById(R.id.statusTv);


        //setting the data
        date.setText(model.getTime());
        amount.setText("Amount : " + model.getAmountPaid() + " BDT");
        transId.setText("Trans ID : " + model.getTrxID());

        if (model.getStatus().contains("pending")) {
            statusTv.setText("Status : PENDING");
            statusTv.setTextColor(Color.RED);
        } else {
            statusTv.setText("Status : ACCEPTED");
            statusTv.setTextColor(Color.GREEN);
        }


        // loadthe image to the view

    }

    // interface
    public interface Clicklistener {

        void onItemClick(View view, int position);


    }


}
