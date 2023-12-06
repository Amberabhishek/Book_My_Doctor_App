package com.amber.bookmydoctor.DoctorFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.amber.bookmydoctor.PatientDetails;
import com.amber.bookmydoctor.R; // Add this import

import java.util.List;

public class PatientAdapter extends RecyclerView.Adapter<PatientAdapter.PatientViewHolder> {

    private List<PatientDetails> patientList;

    public PatientAdapter(List<PatientDetails> patientList) {
        this.patientList = patientList;
    }

    @NonNull
    @Override
    public PatientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_patient_appointment, parent, false);
        return new PatientViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PatientViewHolder holder, int position) {
        PatientDetails patient = patientList.get(position);

        // Bind data to the ViewHolder
        if (holder.nameTextView != null) {
            holder.nameTextView.setText(patient.getUserName());
        }
        if (holder.emailTextView != null) {
            holder.emailTextView.setText(patient.getUserEmail());
        }
        if (holder.dateTextView != null) {
            holder.dateTextView.setText(patient.getSelectedDate());
        }
        if (holder.timeTextView != null) {
            holder.timeTextView.setText(patient.getSelectedTimeSlot());
        }
        if (holder.phoneTextView != null) {
            holder.phoneTextView.setText("Phone: " + patient.getUserPhone());
        }
    }


    @Override
    public int getItemCount() {
        return patientList.size();
    }

    public static class PatientViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView, emailTextView, dateTextView, timeTextView, phoneTextView;

        public PatientViewHolder(@NonNull View itemView) {
            super(itemView);

            nameTextView = itemView.findViewById(R.id.nameTextView);
            emailTextView = itemView.findViewById(R.id.emailTextView);
            dateTextView = itemView.findViewById(R.id.dateTextView);
            timeTextView = itemView.findViewById(R.id.timeTextView);
            phoneTextView = itemView.findViewById(R.id.phoneTextView);
        }
    }
}
