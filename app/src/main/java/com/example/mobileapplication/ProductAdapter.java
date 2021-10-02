package com.example.mobileapplication;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.MyViewHolder> {

    View rootView;
    Context context;

    ArrayList<Float> AmtArray = new ArrayList<>();
    TextView totamount;

    Calculations calculations = new Calculations();

    private List<Product> productList;

    public ProductAdapter(List<Product> itemsList) {
        this.productList = itemsList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView itemCode, amount, totamount, unitprice, qty;
        public EditText itemQty;
        public ImageButton btnIncrease, btnDelete;
        public Button btnDecrease;

        public MyViewHolder(View view) {
            super(view);
            itemCode = (TextView) view.findViewById(R.id.lblcode);
            itemQty = (EditText) view.findViewById(R.id.txtqty);
            amount = (TextView) view.findViewById(R.id.lbamount);
            unitprice = (TextView) view.findViewById(R.id.lblunitprice);
            totamount = (TextView) rootView.findViewById(R.id.totamount);
            btnIncrease = (ImageButton) view.findViewById(R.id.btnIncrease);
            btnDelete = (ImageButton) view.findViewById(R.id.itemdel);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.product_item_view, parent, false);

        context = parent.getContext();
        rootView = ((Activity) context).getWindow().getDecorView().findViewById(android.R.id.content);
        totamount = (TextView) rootView.findViewById(R.id.totamount);

        return new MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Product product = productList.get(position);
        holder.itemCode.setText(Integer.toString(product.getItemCode()));
        holder.itemQty.setText(Integer.toString(product.getQty()));
        holder.unitprice.setText(Float.toString(product.getUnitprice()));
        holder.amount.setText(Float.toString(product.getUnitprice() * product.getQty()));
        holder.btnIncrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Double Amt = Double.valueOf(product.getUnitprice());
                product.setQty(product.getQty() + 1);
                product.setPrice(calculations.calcPrice(product.getUnitprice(), product.getQty()));
                //product.setPrice(product.getUnitprice() * product.getQty());
                productList.set(position, product);
                notifyDataSetChanged();
                calculateTotal(totamount);
            }
        });


        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                productList.remove(position);
                notifyDataSetChanged();
                calculateTotal(totamount);
            }
        });


    }

    public void calculateTotal(TextView totamount){

        Double TotalAmt = 0.0;
        for (int i = 0; i < productList.size(); i++) {
            Product pro = productList.get(i);
            TotalAmt += pro.getPrice();
        }
        totamount.setText(String.valueOf(TotalAmt));
    }


    @Override
    public int getItemCount() {
        return productList.size();
    }

}


