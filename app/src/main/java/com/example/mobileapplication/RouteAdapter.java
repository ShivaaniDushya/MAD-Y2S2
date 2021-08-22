package com.example.mobileapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RouteAdapter extends RecyclerView.Adapter<RouteAdapter.MyViewHolder> {

   private Context context;
   private Activity activity;
   private ArrayList route_id,
                     route_start_location,
                     route_end_location,
                     route_distance,
                     route_created_date,
                     route_modified_date,
                     route_isdefault;

   RouteAdapter(Activity activity,
                Context context,
                ArrayList route_id,
                ArrayList route_start_location,
                ArrayList route_end_location,
                ArrayList route_distance,
                ArrayList route_created_date,
                ArrayList route_modified_date,
                ArrayList route_isdefault)

   {
       Log.d("workflow","RouteAdapter Constructor Called");
          this.activity=activity;
          this.context=context;
          this.route_id=route_id;
          this.route_start_location=route_start_location;
          this.route_end_location=route_end_location;
          this.route_distance=route_distance;
          this.route_created_date=route_created_date;
          this.route_modified_date=route_modified_date;
          this.route_isdefault=route_isdefault;
   }



    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       LayoutInflater inflater =LayoutInflater.from(context);
       View view=inflater.inflate(R.layout.my_row,parent,false);

       return new MyViewHolder(view);

       }

    @Override
    public void onBindViewHolder(@NonNull RouteAdapter.MyViewHolder holder,int position) {
        Log.d("workflow","RouteAdapter onBindViewHolder method  Called");

        holder.rid_txt.setText(String.valueOf(route_id.get(position)));
        holder.start_loc_txt.setText(String.valueOf(route_start_location.get(position)));
        holder.end_loc_txt.setText(String.valueOf(route_end_location.get(position)));
        holder.distance_txt.setText(String.valueOf(route_distance.get(position)));
        holder.created_date_txt.setText(String.valueOf(route_created_date.get(position)));
        holder.modified_date_txt.setText(String.valueOf(route_modified_date.get(position)));
        holder.is_default_txt.setText(String.valueOf(route_isdefault.get(position)));
        //Recyclerview OnclickLister
        if(Integer.parseInt(String.valueOf(route_isdefault.get(position)))==1){
            holder.imageView.setVisibility(View.VISIBLE);
        }
        else
        {
            holder.imageView.setVisibility(View.GONE);
        }
        holder.imgbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(context,ModifyRoute.class);
                intent.putExtra("rid",String.valueOf(route_id.get(position)));
                intent.putExtra("startloc",String.valueOf(route_start_location.get(position)));
                intent.putExtra("endloc",String.valueOf(route_end_location.get(position)));
                intent.putExtra("distance",String.valueOf(route_distance.get(position)));
                intent.putExtra("created",String.valueOf(route_created_date.get(position)));
                intent.putExtra("modified",String.valueOf(route_modified_date.get(position)));
                intent.putExtra("isdefaullt",String.valueOf(route_isdefault.get(position)));
                activity.startActivityForResult(intent,1);

                Log.d("valies",String.valueOf(route_id.get(position)));
            }
        });


    }

    @Override
    public int getItemCount() {
        return route_id.size();      }

    class MyViewHolder extends RecyclerView.ViewHolder{

       TextView rid_txt,start_loc_txt,end_loc_txt,distance_txt,created_date_txt,modified_date_txt,is_default_txt;
       LinearLayout mainLayout;
       ImageButton imgbtn;
       ImageView imageView;

       MyViewHolder(@NonNull View itemView) {
            super(itemView);
            rid_txt=itemView.findViewById(R.id.rid_txt);
            start_loc_txt=itemView.findViewById(R.id.start_loc_txt);
           end_loc_txt=itemView.findViewById(R.id.end_loc_txt);
           distance_txt=itemView.findViewById(R.id.distance_txt);
           created_date_txt=itemView.findViewById(R.id.created_date_txt);
           modified_date_txt=itemView.findViewById(R.id.modified_date_txt);
           is_default_txt= itemView.findViewById(R.id.is_default_txt);
           imageView=itemView.findViewById(R.id.img_is_dif);
           mainLayout = itemView.findViewById(R.id.mainLayout);

           imgbtn=itemView.findViewById(R.id.imageButton);
           Animation translate_anim= AnimationUtils.loadAnimation(context,R.anim.translate_anim);
           mainLayout.setAnimation(translate_anim);
        }
    }

}
