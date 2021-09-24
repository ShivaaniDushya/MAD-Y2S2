package com.example.mobileapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.MyViewHolder> {

    private List<Product> productList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView itemCode, total;
        public EditText itemQty;
        public ImageButton btnDelete;

        public MyViewHolder(View view) {
            super(view);
            itemCode = (TextView) view.findViewById(R.id.lblcode);
            itemQty = (EditText) view.findViewById(R.id.txtqty);
            total = (TextView) view.findViewById(R.id.lbamount);
            btnDelete = (ImageButton) view.findViewById(R.id.itemdel);
        }
    }


    public ProductAdapter(List<Product> moviesList) {
        this.productList = moviesList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.product_item_view, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Product product = productList.get(position);
        holder.itemCode.setText(Integer.toString(product.getItemCode()));
        holder.itemQty.setText(Integer.toString(product.getQty()));
        holder.total.setText(Float.toString(product.getPrice()));
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                productList.remove(position);
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }
}
