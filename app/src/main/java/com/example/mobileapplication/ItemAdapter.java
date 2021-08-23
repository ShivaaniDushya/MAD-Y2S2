package com.example.mobileapplication;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ItemAdapter extends RecyclerView.Adapter {
    public ItemAdapter(Items items, Items items1, ArrayList<String> item_code, ArrayList<String> item_name, ArrayList<String> item_brand, ArrayList<String> item_count, ArrayList<String> buyPrice_item, ArrayList<String> sellPrice_item, ArrayList<String> item_descrip) {
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
