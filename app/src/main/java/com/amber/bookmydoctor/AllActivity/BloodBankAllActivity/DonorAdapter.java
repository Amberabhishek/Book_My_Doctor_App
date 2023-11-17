package com.amber.bookmydoctor.AllActivity.BloodBankAllActivity;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.amber.bookmydoctor.R;

import java.util.List;

public class DonorAdapter extends RecyclerView.Adapter<DonorAdapter.DonorViewHolder> {

    private List<UserDetails> userDetailsList;

    public DonorAdapter(List<UserDetails> userDetailsList) {
        this.userDetailsList = userDetailsList;
    }

    @NonNull
    @Override
    public DonorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the item layout
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.blood_donors_card, parent, false);

        // Return a new instance of the ViewHolder
        return new DonorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DonorViewHolder holder, int position) {
        UserDetails userDetails = userDetailsList.get(position);

        if (userDetails != null) {
            holder.bloodGroupTextView.setText("Blood Group: " + userDetails.getBloodType());
            holder.nameTextView.setText("Name: " + userDetails.getName());
            holder.ageTextView.setText("Age: " + userDetails.getAge());
            holder.phoneTextView.setText("Phone: " + userDetails.getContact());
            holder.genderTextView.setText("Gender: " + userDetails.getGender());
        } else {
            // Handle null values if needed
            Log.d("FirebaseData", "UserDetails at position " + position + " is null");
        }
    }


    @Override
    public int getItemCount() {
        return userDetailsList.size();
    }

    static class DonorViewHolder extends RecyclerView.ViewHolder {
        TextView bloodGroupTextView, nameTextView, ageTextView, phoneTextView, genderTextView;

        public DonorViewHolder(@NonNull View itemView) {
            super(itemView);

            bloodGroupTextView = itemView.findViewById(R.id.bloodGroupTextView);
            nameTextView = itemView.findViewById(R.id.nameTextView);
            ageTextView = itemView.findViewById(R.id.ageTextView);
            phoneTextView = itemView.findViewById(R.id.phoneTextView);
            genderTextView = itemView.findViewById(R.id.genderTextView);
        }
    }
}
