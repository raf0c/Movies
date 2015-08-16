package com.example.raf0c.movies.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.example.raf0c.movies.R;
import com.example.raf0c.movies.bean.ImageItem;
import com.example.raf0c.movies.controller.ApplicationController;
import com.example.raf0c.movies.utils.BitmapLruCache;

import java.util.List;

/**
 * Created by raf0c on 16/08/15.
 */
public class ImageItemAdapter extends ArrayAdapter<ImageItem> {
    private ImageLoader mImageLoader;

    public ImageItemAdapter(Context context) {
        super(context, R.layout.image_list_item);

        mImageLoader = new ImageLoader(ApplicationController.getInstance().getRequestQueue(), new BitmapLruCache());
    }

    // CHANGE THIS TO VIEWHOLDER AND USE RECYCLER

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.image_list_item, parent, false);
        }

        NetworkImageView imageView = (NetworkImageView) convertView.findViewById(R.id.image1);
        TextView textView = (TextView) convertView.findViewById(R.id.text1);

        ImageItem imageRecord = getItem(position);

        imageView.setImageUrl(imageRecord.getUrl(), mImageLoader);
        textView.setText(imageRecord.getTitle());

        return convertView;
    }

    public void swapImageRecords(List<ImageItem> objects) {
        clear();

        for(ImageItem object : objects) {
            add(object);
        }

        notifyDataSetChanged();
    }
}