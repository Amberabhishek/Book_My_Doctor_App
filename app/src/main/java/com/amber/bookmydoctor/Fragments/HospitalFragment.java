package com.amber.bookmydoctor.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.amber.bookmydoctor.HospitalAdapter;
import com.amber.bookmydoctor.HospitalModel;
import com.amber.bookmydoctor.LocationClient;
import com.amber.bookmydoctor.R;

import java.util.ArrayList;

public class HospitalFragment extends Fragment {
    private RecyclerView recyclerView;
    private HospitalAdapter adapter;
    private LocationClient lc;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_hospital, container, false);

        recyclerView = rootView.findViewById(R.id.idRVCourse);

        // Create an ArrayList of HospitalModel with hospital data
        ArrayList<HospitalModel> hospitalModelArrayList = new ArrayList<>();

        // Initialize the HospitalAdapter and pass the hospital data
        adapter = new HospitalAdapter(getActivity(), hospitalModelArrayList);

        // Set up the RecyclerView with the adapter
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);

        lc = new LocationClient(getActivity(), adapter);
        lc.locationsender();
        lc.nearbySearcher();

        return rootView;
    }
}
