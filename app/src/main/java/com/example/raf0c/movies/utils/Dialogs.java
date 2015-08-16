package com.example.raf0c.movies.utils;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.example.raf0c.movies.R;

/**
 * Created by raf0c on 16/08/15.
 */
public class Dialogs {

    public static void displayInfoDialogView(String urlImage, String synop, String title, Activity activity, ImageLoader mImageLoader) {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(activity);
        alertDialog.setTitle("Movie Info");
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.movie_selected, null);
        alertDialog.setView(view);
        NetworkImageView imageView = (NetworkImageView) view.findViewById(R.id.nivSelected);
        TextView tvTitle = (TextView) view.findViewById(R.id.tvTitle);
        TextView tvSynopsis = (TextView) view.findViewById(R.id.tvSynopsis);
        tvTitle.setText(title);
        tvSynopsis.setText("Synopsis : " + synop);
        imageView.setImageUrl(urlImage, mImageLoader);
        alertDialog.create().show();
    }
}
