package com.amber.bookmydoctor.AllActivity.PatientBookingActivity;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.amber.bookmydoctor.Doctor;
import com.amber.bookmydoctor.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class DoctorListAdapter extends RecyclerView.Adapter<DoctorListAdapter.ViewHolder> {
    private List<Doctor> doctors;
    private Context context;

    public DoctorListAdapter(Context context, List<Doctor> doctors) {
        this.context = context;
        this.doctors = doctors;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_doctor_list_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Doctor doctor = doctors.get(position);

        holder.doctorName.setText(doctor.getName());
        holder.speciality.setText(doctor.getDoctorType());
        holder.about.setText(doctor.getAbout());
        Picasso.get().load(doctor.getImageUrl()).into(holder.drImage);
        holder.city.setText(doctor.getCity());
    }

    @Override
    public int getItemCount() {
        return doctors.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView doctorName;
        TextView speciality;
        TextView about;
        ImageView drImage;
        Button bookingBtn;
        TextView city;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            doctorName = itemView.findViewById(R.id.doctor_name);
            speciality = itemView.findViewById(R.id.dr_speciality);
            about = itemView.findViewById(R.id.dr_about);
            drImage = itemView.findViewById(R.id.dr_image);
            bookingBtn = itemView.findViewById(R.id.booking_btn);
            city = itemView.findViewById(R.id.dr_city);

            bookingBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Doctor selectedDoctor = doctors.get(getAdapterPosition());
                    goAppointmentBooking(selectedDoctor);
                }
            });
        }
    }

    private void goAppointmentBooking(Doctor doctor) {
        Intent intent = new Intent(context, ApointmentBookingActivity.class);

        // Pass specific data to the intent
        intent.putExtra("doctorImage", doctor.getImageUrl());
        intent.putExtra("doctorType", doctor.getDoctorType());
        intent.putExtra("doctorName", doctor.getName());
        intent.putExtra("doctorID", doctor.getDid());

        context.startActivity(intent);
    }

}
