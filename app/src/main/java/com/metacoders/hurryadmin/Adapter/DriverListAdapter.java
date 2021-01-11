package com.metacoders.hurryadmin.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.metacoders.hurryadmin.Models.driverProfileModel;
import com.metacoders.hurryadmin.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class DriverListAdapter extends RecyclerView.Adapter<DriverListAdapter.ViewHolder> {
    Context context;
    List<driverProfileModel> list;
    private ItemClickListener itemClickListener;
    public DriverListAdapter(Context context, List<driverProfileModel> list ,ItemClickListener itemClickListener) {
        this.context = context;
        this.list = list;
        this.itemClickListener = itemClickListener;

    }

    @NonNull
    @Override
    public DriverListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        view = inflater.inflate(R.layout.driver_row, parent, false);
        return new ViewHolder(view);
    }

    public interface ItemClickListener {
        void onItemClick(driverProfileModel model);
    }

    @Override
    public void onBindViewHolder(@NonNull DriverListAdapter.ViewHolder holder, final int position) {

        driverProfileModel model = list.get(position) ;

        try {
            Glide.with(context)
                    .load(model.getProfile_picture())
                    .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                    .into(holder.circleImageView) ;
            holder.nameTv.setText("Name: " +model.getDriverName());
            holder.statusTv.setText("Staus: " +model.getDriverIdActivated());
            holder.phoneTv.setText("Phone : " +model.getPhone());

        } catch (Exception r) {
            Log.d("TAG", "onBindViewHolder: " + r.getMessage());
        }

        holder.itemView.setOnClickListener(v -> {

            driverProfileModel models= list.get(position);
            itemClickListener.onItemClick(models);

        });

    }
//    @Override
//    public Filter getFilter() {
//
//        return new Filter() {
//            @Override
//            protected FilterResults performFiltering(CharSequence constraint) {
//
//                String Key = constraint.toString();
//                if (Key.isEmpty()) {
//
//                    mDataFiltered = list;
//
//                } else {
//                    List<driverProfileModel> lstFiltered = new ArrayList<>();
//                    for (driverProfileModel row : mData) {
//                        //Log.d("TAG", "Filtering : " + row.getProductTitle());
//                        if (row.getProductTitle().toLowerCase().contains(Key.toLowerCase())) {
//
//                            //  Log.d("TAG", "Fillered: " + row.getProductTitle());
//                            lstFiltered.add(row);
//                        }
//
//                    }
//                    // Log.d("TAG", "Size: " + lstFiltered.size());
//                    mDataFiltered = lstFiltered;
//                    // Log.d("TAG", "dataset Size: " + mDataFiltered.size());
//
//                }
//
//
//                FilterResults filterResults = new FilterResults();
//                filterResults.values = mDataFiltered;
//                return filterResults;
//
//            }
//
//            @Override
//            protected void publishResults(CharSequence constraint, FilterResults results) {
//
//
//                mDataFiltered = (List<ProductModel>) results.values;
//                notifyDataSetChanged();
//
//            }
//        };
//
//
//    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView nameTv, statusTv, phoneTv;
        CircleImageView circleImageView;


        public ViewHolder(@NonNull View mview) {
            super(mview);

            circleImageView = mview.findViewById(R.id.Circle);
            nameTv = mview.findViewById(R.id.nameTv);
            statusTv = mview.findViewById(R.id.statusTv);
            phoneTv = mview.findViewById(R.id.phoneTv);


        }


    }


}
