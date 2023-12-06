package com.amber.bookmydoctor.AllActivity.MedicinesActivity;

// Import statements
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.amber.bookmydoctor.AllActivity.MedicineAdapter;
import com.amber.bookmydoctor.Medicine;
import com.amber.bookmydoctor.R;  // Replace with your actual package name

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ShopListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MedicineAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicines_recycler_view);

        recyclerView = findViewById(R.id.medicine_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new MedicineAdapter(new ArrayList<>());
        recyclerView.setAdapter(adapter);

        // Replace this URL with the actual endpoint for retrieving medicine data
        String medicineDataUrl = "https://healthplus.flipkart.com/";

        new RetrieveMedicineDataTask().execute(medicineDataUrl);
    }

    private class RetrieveMedicineDataTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            String urlString = params[0];
            try {
                URL url = new URL(urlString);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                try {
                    InputStream in = urlConnection.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                    StringBuilder result = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        result.append(line);
                    }
                    return result.toString();
                } finally {
                    urlConnection.disconnect();
                }
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String result) {
            if (result != null) {
                try {
                    List<Medicine> medicineList = new ArrayList<>();
                    JSONArray jsonArray = new JSONArray(result);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String name = jsonObject.getString("name");
                        String company = jsonObject.getString("company");
                        String price = jsonObject.getString("price");
                        String imageUrl = jsonObject.getString("imageUrl");

                        // Add medicine to the list
                        addMedicineToList(name, company, price, imageUrl);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                // Handle error
            }
        }
    }

    private void addMedicineToList(String name, String company, String price, String imageUrl) {
        adapter.addMedicine(new Medicine(name, company, price, imageUrl));
    }
}
