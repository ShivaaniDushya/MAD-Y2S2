package com.example.mobileapplication;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.mobileapplication.database.DBHelper;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

public class SalesOrderAdapter extends RecyclerView.Adapter <SalesOrderAdapter.MyViewHolder> {

    private Context context;
    private Activity activity;
    private ArrayList inv_id,
                      cus_id,
                      del_date,
                      inv_amt,
                      balance,
                      created_date,
                      isurgent;

    private int result;

    SalesOrderAdapter(Activity activity,
                      Context context,
                      ArrayList inv_id,
                      ArrayList cus_id,
                      ArrayList del_date,
                      ArrayList inv_amt,
                      ArrayList balance,
                      ArrayList created_date,
                      ArrayList isurgent)
    {
        Log.d("workflow","SalesOrderAdapter constructor called");
        this.activity=activity;
        this.context=context;
        this.inv_id=inv_id;
        this.cus_id=cus_id;
        this.del_date=del_date;
        this.inv_amt=inv_amt;
        this.balance=balance;
        this.created_date=created_date;
        this.isurgent=isurgent;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.sales_order_row,parent,false);

        return new SalesOrderAdapter.MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull SalesOrderAdapter.MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Log.d("workflow","SalesOrderAdapter onBindViewHolder method called");

        holder.inv_txt.setText(String.valueOf(inv_id.get(position)));
        holder.cus_txt.setText(String.valueOf(cus_id.get(position)));
        holder.del_date_txt.setText(String.valueOf(del_date.get(position)));
        holder.inv_amt_txt.setText(String.valueOf(inv_amt.get(position)));
        holder.balance_txt.setText(String.valueOf(balance.get(position)));
        holder.created_date_txt.setText(String.valueOf(created_date.get(position)));
        holder.is_urgent_txt.setText(String.valueOf(isurgent.get(position)));


        holder.btnpay.setOnClickListener (v -> {
            Intent intent = new Intent(context, UpdatePayment.class);
            intent.putExtra("inv_id",String.valueOf(inv_id.get(position)));
            Log.d("values",String.valueOf(inv_id.get(position)));
            intent.putExtra("balance",String.valueOf(balance.get(position)));
            context.startActivity(intent);
        });


        holder.btndel.setOnClickListener(v -> {
            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
            dialogBuilder.setTitle("Delete Sales Order");
            dialogBuilder.setMessage("Do you really want to delete this sales order?");
            dialogBuilder.setPositiveButton("Yes", (dialog, which) -> {
                DBHelper dbHelper=new DBHelper(context);
                dbHelper.deleteSalesOrder(String.valueOf(inv_id.get(position)));
                String delMsg;
                if (result < 1) {
                    delMsg = "Unable to delete sales order.";
                }
                else {
                    delMsg = "Sales order deleted successfully.";
                }
                Intent intent = new Intent(context, Sales.class)
                        .putExtra("passMessage", delMsg);
                context.startActivity(intent);
            });
            dialogBuilder.setNegativeButton("No", (dialog, which) -> {

            });
            dialogBuilder.show();
        });
    }

    @Override
    public int getItemCount() {
        return inv_id.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView inv_txt, cus_txt, del_date_txt, inv_amt_txt, balance_txt, created_date_txt, is_urgent_txt;
        LinearLayout mainLayout;
        MaterialButton btndel;
        Button btnpay;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            inv_txt=itemView.findViewById(R.id.txtinv);
            cus_txt=itemView.findViewById(R.id.txtcus);
            del_date_txt=itemView.findViewById(R.id.txtdeldate);
            inv_amt_txt=itemView.findViewById(R.id.txtinvamt);
            balance_txt=itemView.findViewById(R.id.txtbal);
            created_date_txt=itemView.findViewById(R.id.txtcreateddate);
            is_urgent_txt= itemView.findViewById(R.id.txturg);
            btnpay=itemView.findViewById(R.id.btnpay);
            btndel=itemView.findViewById(R.id.btndel);
            mainLayout = itemView.findViewById(R.id.mainLayout);
        }
    }


}
