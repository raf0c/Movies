package com.example.raf0c.movies;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.example.raf0c.movies.adapters.ImageItemAdapter;
import com.example.raf0c.movies.bean.ImageItem;
import com.example.raf0c.movies.constants.Constants;
import com.example.raf0c.movies.controller.ApplicationController;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by raf0c on 16/08/15.
 */
public class SearchResultsActivity extends AppCompatActivity {

    private RecyclerView rv;
    private List<ImageItem> imageRecords;
    private ImageItemAdapter mAdapter;
    private String mURL;
    private ArrayList<ImageItem> records;
    private Activity activity;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        activity = this;

        rv=(RecyclerView)findViewById(R.id.cardList);
        LinearLayoutManager llm = new LinearLayoutManager(getApplicationContext());
        rv.setLayoutManager(llm);
        rv.setHasFixedSize(true);
        imageRecords = new ArrayList<>();

        mURL = Constants.API_URL + Constants.API_MOVIES + "?apikey=" + Constants.API_KEY + "&q=";
        handleIntent(getIntent());

    }

    public void displayInfoDialogView(String urlImage, String synop, String title, Activity activity, ImageLoader mImageLoader) {

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

    private void searchMovies(String query) {
        JsonObjectRequest request = new JsonObjectRequest(mURL + Uri.encode(query), null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        try {

                            imageRecords = parse(jsonObject);
                            mAdapter = new ImageItemAdapter(getApplicationContext(),imageRecords);
                            rv.setAdapter(mAdapter);

                            mAdapter.setOnItemClickListener(new ImageItemAdapter.OnItemClickListener() {
                                public void onItemClick(String url, String synopsis, String title, ImageLoader mImageLoader) {

                                    displayInfoDialogView(url, synopsis, title, activity, mImageLoader);

                                }
                            });
                            mAdapter.notifyDataSetChanged();

                        }
                        catch(JSONException e) {
                            Toast.makeText(getApplicationContext(), "Unable to parse data: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Toast.makeText(getApplicationContext(), "Unable to fetch data: " + volleyError.getMessage(), Toast.LENGTH_SHORT).show();
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

    @Override
    protected void onNewIntent(Intent intent) {
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {

        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            searchMovies(query);
        }
    }
}
