package com.example.mobileapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class TextProductAdapter extends ArrayAdapter<Product> {
    private List<Product> products;

    public TextProductAdapter(@NonNull Context context, @NonNull List<Product> productList) {
        super(context, 0, productList);
        products = new ArrayList<>(productList);
    }

    @NonNull
    @Override
    public Filter getFilter() {
        return userFilter;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.textview_adapter_product, parent, false);
        }

        TextView txtUsername = convertView.findViewById(R.id.lblitemname);

        Product product = getItem(position);

        if (convertView != null) {
            txtUsername.setText(product.getItemName());
        }

        return convertView;
    }

    private Filter userFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            FilterResults results = new FilterResults();
            List<Product> suggestions = new ArrayList<>();

            if (charSequence == null || charSequence.length() == 0) {
                suggestions.addAll(products);
            } else {
                String filterPattern = charSequence.toString().toLowerCase().trim();

                for (Product product : products) {
                    if (product.getItemName().toLowerCase().contains(filterPattern)) {
                        suggestions.add(product);
                    }
                }
            }
            results.values = suggestions;
            results.count = suggestions.size();

            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            clear();
            addAll((List) filterResults.values);
            notifyDataSetChanged();
        }

        @Override
        public CharSequence convertResultToString(Object resultValue) {
            Product product = (Product) resultValue;
            return product.getItemName();
        }
    };
}