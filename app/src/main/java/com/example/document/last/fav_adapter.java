package com.example.document.last;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.ArrayList;

/**
 * Created by document on 28/09/2017.
 */

public class fav_adapter extends BaseAdapter {
  private   Activity activity;
  private   ArrayList<String>arrayList;

    public fav_adapter(Activity activity, ArrayList<String> arrayList) {
        this.activity = activity;
        this.arrayList = arrayList;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return 0;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = activity.getLayoutInflater().inflate(R.layout.fav_row,null);
        ImageView image = (ImageView)view.findViewById(R.id.image_fav);
        //image.setImageResource(arrayList.get(position));

        return view;
    }
}
