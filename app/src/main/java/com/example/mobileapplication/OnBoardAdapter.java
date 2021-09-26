package com.example.mobileapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

public class OnBoardAdapter extends PagerAdapter {
    Context context;
    LayoutInflater layoutInflater;

    public OnBoardAdapter(Context context) {
        this.context = context;
    }

    int[] images = {
            R.drawable.ic_language,
            R.drawable.ic_map,
            R.drawable.ic_easy
    };

    int[] titles = {
            R.string.heading_trilingual,
            R.string.heading_search_nearby_shops,
            R.string.heading_easy_to_use
    };

    int[] description = {
            R.string.text_trilingual,
            R.string.text_search_nearby_shops,
            R.string.text_easy_to_use
    };

    @Override
    public int getCount() {
        return titles.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == (RelativeLayout) object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.on_board_pager, container, false);

        ImageView imageView = view.findViewById(R.id.storySet);
        TextView title = view.findViewById(R.id.splash_title);
        TextView desc = view.findViewById(R.id.splash_desc);

        imageView.setImageResource(images[position]);
        title.setText(titles[position]);
        desc.setText(description[position]);

        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((RelativeLayout) object);
    }
}
