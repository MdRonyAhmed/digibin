package com.example.digibin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class UsersRVAdapter extends RecyclerView.Adapter<UsersRVAdapter.ViewHolder> {
    // creating variables for our ArrayList and context
    private ArrayList<user> usersList;
    private Context context;

    // creating constructor for our adapter class
    public UsersRVAdapter(ArrayList<user> userArrayList, Context context) {
        this.usersList = userArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // passing our layout file for displaying our card item
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.activity_ulist, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull UsersRVAdapter.ViewHolder holder, int position) {
        // setting data to our text views from our modal class.
        user user = usersList.get(position);
        holder.name.setText(user.getName());
        holder.location.setText(user.getLocation());
        holder.userPhone.setText(user.getPhoneNumber());
//        holder.status.setText(user.getStatus());
    }

    @Override
    public int getItemCount() {
        // returning the size of our array list.
        return usersList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        // creating variables for our text views.
        private final TextView name;
        private final TextView location;
//        private final ToggleButton status;
        private final TextView userPhone;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // initializing our text views.
            name = itemView.findViewById(R.id.userName);
            location = itemView.findViewById(R.id.userLocation);
//            status = (ToggleButton) itemView.findViewById(R.id.status);
            userPhone = itemView.findViewById(R.id.userPhone);

        }
    }
}
