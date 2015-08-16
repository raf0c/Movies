package com.example.raf0c.movies.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.raf0c.movies.R;
import com.example.raf0c.movies.bean.RowItem;

import java.util.List;

/**
 * Created by raf0c on 16/08/15.
 */
public class DrawerAdapter  extends BaseAdapter {

    Context context;
    List<RowItem> rowItem;

    public DrawerAdapter(Context context, List<RowItem> rowItem) {
        this.context = context;
        this.rowItem = rowItem;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.item_row, null);
        }
        ImageView imgIcon = (ImageView) convertView.findViewById(R.id.rowIcon);
        TextView txtTitle = (TextView) convertView.findViewById(R.id.rowText);
        RowItem row_pos = rowItem.get(position);

        // setting the image resource and title
        imgIcon.setImageResource(row_pos.getIcon());
        txtTitle.setText(row_pos.getName());
        return convertView;
    }

    @Override
    public int getCount() {
        return rowItem.size();
    }

    @Override
    public Object getItem(int position) {
        return rowItem.get(position);
    }

    @Override
    public long getItemId(int position) {
        return rowItem.indexOf(getItem(position));
    }
}
