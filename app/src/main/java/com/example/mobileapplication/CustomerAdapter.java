package com.example.mobileapplication;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobileapplication.database.DBHelper;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.ArrayList;

public class CustomerAdapter extends RecyclerView.Adapter<CustomerAdapter.MyViewHolder> {

    private final Context context;
    private final ArrayList<String> customer_id;
    private final ArrayList<String> customer_name;
    private final ArrayList<String> store_name;
    private final ArrayList<String> address;
    private final ArrayList<String> pp_img_uri;
    private final ArrayList<String> sp_img_uri;

    CustomerAdapter(Context context,
                    ArrayList<String> customer_id,
                    ArrayList<String> customer_name,
                    ArrayList<String> store_name,
                    ArrayList<String> address,
                    ArrayList<String> pp_img_uri,
                    ArrayList<String> sp_img_uri) {
        this.context = context;
        this.customer_id = customer_id;
        this.customer_name = customer_name;
        this.store_name = store_name;
        this.address = address;
        this.pp_img_uri = pp_img_uri;
        this.sp_img_uri = sp_img_uri;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.customer_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.customer_name_txt.setText(String.valueOf(customer_name.get(position)));
        holder.store_name_txt.setText(String.valueOf(store_name.get(position)));
        holder.address_txt.setText(String.valueOf(address.get(position)));
        if (!(String.valueOf(pp_img_uri.get(position)).equals("null"))) {
            Log.d("workflow", "No img - " + String.valueOf(pp_img_uri.get(position)).length());
            holder.pp_img.setImageURI(Uri.parse(String.valueOf(pp_img_uri.get(position))));
        }
        if (!(String.valueOf(sp_img_uri.get(position)).equals("null"))) {
            holder.sp_img.setImageURI(Uri.parse(String.valueOf(sp_img_uri.get(position))));
        }

        holder.deleteBtn.setOnClickListener(v -> {
            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
            dialogBuilder.setTitle("Are you sure?");
            dialogBuilder.setMessage("Do you really want to delete this customer? This Process cannot be undone.");
            dialogBuilder.setPositiveButton("Yes", (dialog, which) -> {
                DBHelper dbHelper=new DBHelper(context);
                long result = dbHelper.deleteCustomer(String.valueOf(customer_id.get(position)));
                String delMsg;
                if (result < 1) {
                    delMsg = "Customer deleted unsuccessful.";
                }
                else {
                    delMsg = "Customer deleted successful.";
                }
                Intent intent = new Intent(context, Customers.class)
                        .putExtra("passMessage", delMsg);
                context.startActivity(intent);
            });
            dialogBuilder.setNegativeButton("No", (dialog, which) -> {

            });
            dialogBuilder.show();
        });
        holder.viewBtn.setOnClickListener(v -> {
            Intent intent = new Intent(context, ViewCustomer.class);
            intent.putExtra("CustomerID", String.valueOf(customer_id.get(position)));
            context.startActivity(intent);
        });
        holder.editBtn.setOnClickListener(v -> {
            Intent intent = new Intent(context, AddCustomer.class);
            intent.putExtra("CustomerID", String.valueOf(customer_id.get(position)));
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return customer_id.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView customer_name_txt;
        TextView store_name_txt;
        TextView address_txt;
        ImageView sp_img;
        ShapeableImageView pp_img;
        MaterialButton deleteBtn, viewBtn, editBtn;
        LinearLayout snack;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            customer_name_txt = itemView.findViewById(R.id.customer_name_txt);
            store_name_txt = itemView.findViewById(R.id.store_name_txt);
            address_txt = itemView.findViewById(R.id.address_txt);
            pp_img = itemView.findViewById(R.id.store_owner_pic);
            sp_img = itemView.findViewById(R.id.store_pic);
            deleteBtn = itemView.findViewById(R.id.delete);
            viewBtn = itemView.findViewById(R.id.view);
            editBtn = itemView.findViewById(R.id.edit);
            snack = itemView.findViewById(R.id.recycleLinear);
        }
    }
}
