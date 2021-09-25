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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobileapplication.database.DBHelper;

import java.util.ArrayList;

public class ItemAdapter<itemURL> extends RecyclerView.Adapter <ItemAdapter.MyViewHolder>{

    private Context context;
    private Activity activity;
    private ArrayList item_code,
            item_name,
            item_brand,
            item_count,
            item_buy_price,
            item_sell_price,
            item_description;

    private int result;

    ItemAdapter(Activity activity,
                 Context context,
                 ArrayList item_code,
                 ArrayList item_name,
                 ArrayList item_brand,
                 ArrayList item_count,
                 ArrayList item_buy_price,
                 ArrayList item_sell_price,
                 ArrayList item_description
               ) {

        Log.d("workflow","ItemAdapter Constructor Called");
        this.activity=activity;
        this.context=context;
        this.item_code=item_code;
        this.item_name=item_name;
        this.item_brand=item_brand;
        this.item_count=item_count;
        this.item_buy_price=item_buy_price;
        this.item_sell_price=item_sell_price;
        this.item_description=item_description;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater =LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.my_row_item,parent,false);

        return new MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ItemAdapter.MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Log.d("workflow","ItemAdapter onBindViewHolder method  Called");

        holder.item_code_txt.setText(String.valueOf(item_code.get(position)));
        holder.item_name_txt.setText(String.valueOf(item_name.get(position)));
        holder.item_brand_txt.setText(String.valueOf(item_brand.get(position)));
        holder.item_count_txt.setText(String.valueOf(item_count.get(position)));
        holder.item_byuprice_txt.setText(String.valueOf(item_buy_price.get(position)));
        holder.item_sellprice_txt.setText(String.valueOf(item_sell_price.get(position)));
        holder.item_description_txt.setText(String.valueOf(item_description.get(position)));
        //Recyclerview OnclickLister

        holder.button_view_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(context,Edit_item_Activity.class);
                intent.putExtra("itemCode",String.valueOf(item_code.get(position)));
                intent.putExtra("itemName",String.valueOf(item_name.get(position)));
                intent.putExtra("itemBrand",String.valueOf(item_brand.get(position)));
                intent.putExtra("itemCount",String.valueOf(item_count.get(position)));
                intent.putExtra("itemBuyPrice",String.valueOf(item_buy_price.get(position)));
                intent.putExtra("itemSellPrice",String.valueOf(item_sell_price.get(position)));
                intent.putExtra("itemDescription",String.valueOf(item_description.get(position)));
                activity.startActivityForResult(intent,1);

                Log.d("values",String.valueOf(item_code.get(position)));
            }
        });

        holder.deleteBtn.setOnClickListener(v -> {
            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
            dialogBuilder.setTitle("Are you sure?");
            dialogBuilder.setMessage("Do you really want to delete this item? This Process cannot be undone.");
            dialogBuilder.setPositiveButton("Yes", (dialog, which) -> {
                DBHelper dbHelper=new DBHelper(context);
                dbHelper.deleteItem(String.valueOf(item_code.get(position)));
                String delMsg;
                if (result < 1) {
                    delMsg = "Item deleted unsuccessful.";
                }
                else {
                    delMsg = "Item deleted successful.";
                }
                Intent intent = new Intent(context, Items.class)
                        .putExtra("passMessage", delMsg);
                context.startActivity(intent);
            });
            dialogBuilder.setNegativeButton("No", (dialog, which) -> {

            });
            dialogBuilder.show();
        });

        holder.viewBtn.setOnClickListener(v -> {
            Intent intent = new Intent(context, View_item_Activity.class);
            intent.putExtra("ItemCode", String.valueOf(item_code.get(position)));
            context.startActivity(intent);
        });


    }



    @Override
    public int getItemCount() {
        return item_code.size();      }



    class MyViewHolder extends RecyclerView.ViewHolder{

        Button deleteBtn;
        Button viewBtn;
        ImageButton editBtn;
        TextView item_code_txt,item_name_txt,item_brand_txt,item_count_txt,item_byuprice_txt,item_sellprice_txt,item_description_txt;
        LinearLayout mainLayout;
        Button button_view_item;

       public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            item_code_txt=itemView.findViewById(R.id.item_code_best);
            item_name_txt=itemView.findViewById(R.id.item_name_best);
            item_brand_txt=itemView.findViewById(R.id.item_brand_best);
            item_count_txt=itemView.findViewById(R.id.item_count_best);
            item_byuprice_txt=itemView.findViewById(R.id.item_buy_best);
            item_sellprice_txt=itemView.findViewById(R.id.item_sell_best);
            item_description_txt= itemView.findViewById(R.id.item_description_txt);
            deleteBtn = itemView.findViewById(R.id.button_delete_item);
            viewBtn = itemView.findViewById(R.id.button_view);
            //editBtn = itemView.findViewById(R.id.imageView_edit);


            mainLayout = itemView.findViewById(R.id.mainLayout);

            button_view_item=itemView.findViewById(R.id.Item_single_view);
            // Animation translate_anim= AnimationUtils.loadAnimation(context,R.anim.translate_anim);
            //mainLayout.setAnimation(translate_anim);
        }
    }


}
