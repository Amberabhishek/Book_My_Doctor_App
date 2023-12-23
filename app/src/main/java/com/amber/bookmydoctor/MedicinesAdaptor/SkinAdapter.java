package com.amber.bookmydoctor.MedicinesAdaptor;

// Import statements
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.amber.bookmydoctor.MedicinesAllListModel.SkinModel;
import com.amber.bookmydoctor.R;  // Replace with your actual package name
import com.squareup.picasso.Picasso;
import java.util.List;

public class SkinAdapter extends RecyclerView.Adapter<SkinAdapter.ViewHolder> {

    private Context context;
    private List<SkinModel> skinList;

    public SkinAdapter(Context context, List<SkinModel> skinList) {
        this.context = context;
        this.skinList = skinList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_medicines_all_category, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SkinModel skinModel = skinList.get(position);

        // Populate the views in item_medicine.xml with data from the SkinModel object
        holder.medicineNameTextView.setText(skinModel.getMedicineName());
        holder.companyNameTextView.setText(skinModel.getCompanyName());
        holder.medicinePriceTextView.setText(skinModel.getMedicinePrice());

        // Use Picasso to load the image from the URL into the ImageView
        Picasso.get().load(skinModel.getImageUrl()).into(holder.itemImageView);
    }

    @Override
    public int getItemCount() {
        return skinList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // Declare views in item_medicine.xml
        public ImageView itemImageView;
        public TextView medicineNameTextView;
        public TextView companyNameTextView;
        public TextView medicinePriceTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // Initialize views
            itemImageView = itemView.findViewById(R.id.leftImage);
            medicineNameTextView = itemView.findViewById(R.id.textView1);
            companyNameTextView = itemView.findViewById(R.id.textView2);
            medicinePriceTextView = itemView.findViewById(R.id.textView3);
        }
    }
}

