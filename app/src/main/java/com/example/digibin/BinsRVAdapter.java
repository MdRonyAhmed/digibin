package com.example.digibin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class BinsRVAdapter extends RecyclerView.Adapter<BinsRVAdapter.ViewHolder> {
    // creating variables for our ArrayList and context
    private ArrayList<bin> binsList;
    private Context context;

    // creating constructor for our adapter class
    public BinsRVAdapter(ArrayList<bin> userArrayList, Context context) {
        this.binsList = userArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // passing our layout file for displaying our card item
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.activity_dlist, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull BinsRVAdapter.ViewHolder holder, int position) {
        // setting data to our text views from our modal class.
        bin bins = binsList.get(position);
        holder.binId.setText(bins.getBinId());
        holder.binLocation.setText(bins.getBinLocation());

    }

    @Override
    public int getItemCount() {
        // returning the size of our array list.
        return binsList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        // creating variables for our text views.
        private final TextView binId;
        private final TextView binLocation;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // initializing our text views.
            binId = itemView.findViewById(R.id.binId);
            binLocation = itemView.findViewById(R.id.binLocation);

        }
    }
}
