package com.example.raf0c.movies.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.example.raf0c.movies.R;
import com.example.raf0c.movies.adapters.ImageItemAdapter;
import com.example.raf0c.movies.bean.ImageItem;
import com.example.raf0c.movies.constants.Constants;
import com.example.raf0c.movies.controller.ApplicationController;
import com.example.raf0c.movies.utils.BitmapLruCache;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by raf0c on 16/08/15.
 */
public class InTheatersFragment extends Fragment {

    private ImageItemAdapter mAdapter;
    private LinearLayout myLayout;
    private ListView mListView;
    private String mURL;
    private ImageLoader mImageLoader;
    private ArrayList<ImageItem> records;

    public InTheatersFragment(){

    }

    public static Fragment newInstance(Context context) {
        InTheatersFragment f = new InTheatersFragment();

        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        myLayout = (LinearLayout) inflater.inflate(R.layout.fragment_intheaters, container, false);
        mListView = (ListView) myLayout.findViewById(R.id.list1);
        mAdapter = new ImageItemAdapter(getActivity());
        mListView.setAdapter(mAdapter);
        mURL = Constants.API_INTHEATERS +"?apikey=" + Constants.API_KEY;
        mImageLoader = new ImageLoader(ApplicationController.getInstance().getRequestQueue(), new BitmapLruCache());

        fetch(mURL);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.e("synopsis  del: ", position + " la sinopsis es : " + records.get(position).getSynopsis());
                displayInfoDialogView(records.get(position).getUrl(),records.get(position).getSynopsis());
            }
        });

        return myLayout;
    }

    /*
    * In theaters
    *
    *  http://api.rottentomatoes.com/api/public/v1.0/lists/movies/in_theaters.json?apikey=yy5at44a4hzqqbsgnm4u47ju
    *
    * */

    private void fetch(String url) {
        JsonObjectRequest request = new JsonObjectRequest(url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        try {
                            List<ImageItem> imageRecords = parse(jsonObject);
                            mAdapter.swapImageRecords(imageRecords);
                        }
                        catch(JSONException e) {
                            Toast.makeText(getActivity(), "Unable to parse data: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Toast.makeText(getActivity(), "Unable to fetch data: " + volleyError.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        ApplicationController.getInstance().getRequestQueue().add(request);
    }

    private List<ImageItem> parse(JSONObject json) throws JSONException {

        records = new ArrayList<ImageItem>();

        JSONArray jsonMovies = json.getJSONArray("movies");

        for(int i =0; i < jsonMovies.length(); i++) {

            JSONObject jsonImage = jsonMovies.getJSONObject(i);

            String title = jsonImage.getString("title");
            String synopsis = jsonImage.getString("synopsis");

            JSONObject images_obj = jsonImage.getJSONObject("posters");
            String url = images_obj.getString("detailed");

            ImageItem record = new ImageItem(url, title, synopsis);
            records.add(record);
        }

        return records;
    }

    private void displayInfoDialogView(String urlImage, String synop) {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
        alertDialog.setTitle("Movie Info");
        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.movie_selected, null);
        alertDialog.setView(view);
        NetworkImageView imageView = (NetworkImageView) view.findViewById(R.id.nivSelected);
        TextView textView = (TextView) view.findViewById(R.id.tvSynopsis);
        textView.setText("Synopsis : " + synop);
        imageView.setImageUrl(urlImage, mImageLoader);
        alertDialog.create().show();
    }
}
