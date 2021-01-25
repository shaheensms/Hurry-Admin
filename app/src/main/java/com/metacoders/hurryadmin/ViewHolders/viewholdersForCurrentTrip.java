package com.metacoders.hurryadmin.ViewHolders;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.metacoders.hurryadmin.R;

public class viewholdersForCurrentTrip extends RecyclerView.ViewHolder {

    private static Clicklistener mclicklistener;
    View mview;

    public viewholdersForCurrentTrip(@NonNull View itemView) {
        super(itemView);

        mview = itemView;


        //item click
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                mclicklistener.onItemClick(v, getAdapterPosition());

            }
        });


    }

    public static void setOnClickListener(Clicklistener clickListener) {


        mclicklistener = clickListener;


    }

    public void setDataToView(Context context, String postId, String userId, String userNotificationID, String driverId, String driverNotificationID,
                              String toLoc, String fromLoc, String timeDate, String carModl, String driverName,
                              String status, String carLicNum, String fare, String carType,
                              String reqDate, String tripDetails, String returnTimee) {

        TextView dateView = mview.findViewById(R.id.dateOfRow);
        //  TextView fareView = mview.findViewById(R.id.fareRow);
        TextView locaTo = mview.findViewById(R.id.locationTo);
        TextView locaFrom = mview.findViewById(R.id.locationFrom);
        TextView statusTv = mview.findViewById(R.id.statusRow);


        if (status.contains("Bid Found")) {
            statusTv.
                    setBackgroundTintList(context.getResources().getColorStateList(R.color.blue));

        } else if (status.contains("Driver Found") || status.equals("Accepted")) {

            statusTv.
                    setBackgroundTintList(context.getResources().getColorStateList(R.color.green));
        } else {

            statusTv.
                    setBackgroundTintList(context.getResources().getColorStateList(R.color.colorPrimary));
        }


        dateView.setText(timeDate);
        //    fareView.setText(fare);
        locaTo.setText(toLoc);
        locaFrom.setText(fromLoc);
        statusTv.setText(status);

    }

    public interface Clicklistener {

        void onItemClick(View view, int postion);

    }


}
