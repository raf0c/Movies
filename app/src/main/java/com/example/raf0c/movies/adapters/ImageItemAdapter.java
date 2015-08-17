package com.example.raf0c.movies.adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
public class ImageItemAdapter extends RecyclerView.Adapter<ImageItemAdapter.ImagesViewHolder>  {

    private  ImageLoader mImageLoader;
    private  List<ImageItem> rowItem;
    private  OnItemClickListener listener;

    public ImageItemAdapter(Context context, List<ImageItem> rowItem) {
        this.rowItem = rowItem;
        mImageLoader = new ImageLoader(ApplicationController.getInstance().getRequestQueue(), new BitmapLruCache());
    }

    public  class ImagesViewHolder extends RecyclerView.ViewHolder {

        TextView title;
        NetworkImageView movie_pic;
        CardView cardview;
        private OnItemClickListener listener;

        public ImagesViewHolder(View itemView, final OnItemClickListener listener) {

            super(itemView);
            title = (TextView) itemView.findViewById(R.id.text1);
            movie_pic = (NetworkImageView) itemView.findViewById(R.id.image1);
            cardview = (CardView) itemView.findViewById(R.id.card_view);
            cardview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(listener!=null){

                        listener.onItemClick(rowItem.get(getPosition()).getUrl(), rowItem.get(getPosition()).getSynopsis(), rowItem.get(getPosition()).getTitle(), mImageLoader);

                    }
                }
            });
        }


    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public ImagesViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.image_list_item, viewGroup, false);
        ImagesViewHolder pvh = new ImagesViewHolder(v,listener);

        return pvh;
    }

    @Override
    public void onBindViewHolder(ImagesViewHolder imagesViewHolder, int i) {
        imagesViewHolder.title.setText(rowItem.get(i).getTitle());
        imagesViewHolder.movie_pic.setImageUrl(rowItem.get(i).getUrl(), mImageLoader);
    }

    @Override
    public int getItemCount() {
        return rowItem.size();
    }

//    public void swapImageRecords(List<ImageItem> objects) {
//        clear();
//
//        for(ImageItem object : objects) {
//            add(object);
//        }
//
//        notifyDataSetChanged();
//    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }

    public interface OnItemClickListener{
        public void onItemClick(String url, String synopsis, String title, ImageLoader mImageLoader);
    }
}