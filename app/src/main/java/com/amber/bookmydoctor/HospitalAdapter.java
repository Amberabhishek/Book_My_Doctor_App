package com.amber.bookmydoctor;

import static android.view.LayoutInflater.from;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class HospitalAdapter extends RecyclerView.Adapter<HospitalAdapter.ViewHolder> {

    private final Context context;
    private ArrayList<HospitalModel> hospitalModelArrayList;

    public HospitalAdapter(Context context, ArrayList<HospitalModel> hospitalModelArrayList) {
        this.context = context;
        this.hospitalModelArrayList = hospitalModelArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_hospital, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        HospitalModel model = hospitalModelArrayList.get(position);
        holder.hospitalName.setText(model.getName());
        holder.hospitalAddress.setText(model.getAddress());


        // Set the hospital image using Glide
        Glide.with(context).load(model.getImageUrl()).into(holder.hospitalImage);
    }

    @Override
    public int getItemCount() {
        return hospitalModelArrayList.size();
    }

    // Update the adapter's data and notify the RecyclerView of the change
    public void setHospitalData(ArrayList<HospitalModel> updatedData) {
        hospitalModelArrayList = updatedData;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView hospitalImage;
        private final TextView hospitalName;
        private final TextView hospitalAddress;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            hospitalImage = itemView.findViewById(R.id.hospita_image);
            hospitalName = itemView.findViewById(R.id.hospital_name);
            hospitalAddress = itemView.findViewById(R.id.hospital_addres);
        }
    }
}
