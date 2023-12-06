package com.amber.bookmydoctor.AllActivity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.amber.bookmydoctor.Medicine;
import com.bumptech.glide.Glide;
import com.amber.bookmydoctor.R;  // Replace with your actual package name

import java.util.List;

public class MedicineAdapter extends RecyclerView.Adapter<MedicineAdapter.MedicineViewHolder> {
    private List<Medicine> medicineList;

    public MedicineAdapter(List<Medicine> medicineList) {
        this.medicineList = medicineList;
    }

    @NonNull
    @Override
    public MedicineViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_medicines_all_category, parent, false);
        return new MedicineViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MedicineViewHolder holder, int position) {
        Medicine medicine = medicineList.get(position);
        holder.bind(medicine);
    }

    @Override
    public int getItemCount() {
        return medicineList.size();
    }

    public void addMedicine(Medicine medicine) {
    }

    static class MedicineViewHolder extends RecyclerView.ViewHolder {
        private ImageView leftImage;
        private TextView textViewName;
        private TextView textViewCompany;
        private TextView textViewPrice;

        MedicineViewHolder(View itemView) {
            super(itemView);
            leftImage = itemView.findViewById(R.id.leftImage);
            textViewName = itemView.findViewById(R.id.textView1);
            textViewCompany = itemView.findViewById(R.id.textView2);
            textViewPrice = itemView.findViewById(R.id.textView3);
        }

        void bind(Medicine medicine) {
            // Load image using Glide
            Glide.with(itemView)
                    .load(medicine.getImageUrl())  // Replace with the actual URL of the image
                    .placeholder(R.drawable.ic_person_icon)  // Placeholder image while loading
                    .error(R.drawable.ic_person_icon)  // Image to display in case of an error
                    .into(leftImage);

            textViewName.setText(medicine.getName());
            textViewCompany.setText(medicine.getCompany());
            textViewPrice.setText("₹ " + medicine.getPrice());
        }
    }
}
