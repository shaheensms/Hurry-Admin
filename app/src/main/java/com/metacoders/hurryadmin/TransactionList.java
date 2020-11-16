package com.metacoders.hurryadmin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.metacoders.hurryadmin.Models.TransactionsModel;
import com.metacoders.hurryadmin.ViewHolders.viewholderForTransList;

public class TransactionList extends AppCompatActivity {

    RecyclerView recyclerView ;
    LinearLayoutManager llm  ;
    DatabaseReference mref ;
    FirebaseRecyclerAdapter<TransactionsModel, viewholderForTransList> firebaseRecyclerAdapter;
    FirebaseRecyclerOptions<TransactionsModel> options;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_list);
        mref = FirebaseDatabase.getInstance().getReference("transaction_List");
        llm = new LinearLayoutManager(this);
        llm.setReverseLayout(true);
        llm.setStackFromEnd(true);
        recyclerView = findViewById(R.id.list) ;
        recyclerView.setLayoutManager(llm);

        loadList()  ;

    }

    private void loadList() {
        options = new FirebaseRecyclerOptions.Builder<TransactionsModel>().setQuery(mref, TransactionsModel.class).build();
        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<TransactionsModel, viewholderForTransList>(options) {
            @Override
            protected void onBindViewHolder(@NonNull viewholderForTransList holder, final int position, @NonNull TransactionsModel model) {
                holder.setBidData(getApplicationContext()  , getItem(position));
            }

            @NonNull
            @Override
            public viewholderForTransList onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View mview = LayoutInflater.from(parent.getContext()).inflate(R.layout.transaction, parent, false);
                final viewholderForTransList viewholder = new viewholderForTransList(mview);

                viewholderForTransList.setOnClickListener(new viewholderForTransList.Clicklistener() {
                    @Override
                    public void onItemClick(View view,final int position) {
                        TransactionsModel model = getItem(position) ;
                        Intent p = new Intent(getApplicationContext(), Trans_Tour_Details.class);
                        p.putExtra("TRANSMODEL" , model);
                        startActivity(p);
                    }
                });

                return  viewholder ;

            }
        };
        recyclerView.setLayoutManager(llm);
        firebaseRecyclerAdapter.startListening();
        recyclerView.setAdapter(firebaseRecyclerAdapter);
    }


}