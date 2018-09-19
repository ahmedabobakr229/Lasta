package com.example.document.last;

import android.app.Activity;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.widget.PopupMenu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by document on 24/09/2017.
 */

public class myAdapter extends BaseAdapter {



    Activity activity;

    ArrayList<String>mo_title;
    ArrayList<String> vote_co;
    ArrayList<String> vote_av;
    ArrayList<String> poster;
    ArrayList<String> over;
    ArrayList<Integer> mid;

    public myAdapter(Activity activity, ArrayList<String> mo_title, ArrayList<String> vote_co, ArrayList<String> vote_av, ArrayList<String> poster, ArrayList<String> over, ArrayList<Integer> id) {
        this.activity = activity;
        this.mo_title = mo_title;
        this.vote_co = vote_co;
        this.vote_av = vote_av;
        this.poster = poster;
        this.over = over;
        this.mid= id;
    }

    @Override
    public int getCount() {
        return mo_title.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final View view = activity.getLayoutInflater().inflate(R.layout.row_view,null);
        final ImageView imageView = (ImageView)view.findViewById(R.id.image);
        Picasso.with(activity).load(poster.get(position)).resize(400 , 400).into(imageView);
        imageView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                final Dialog dialog = new Dialog(activity);
                dialog.setContentView(view);
                imageView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        dialog.dismiss();
                        return false;
                    }
                });
                dialog.show();
                return false;
            }
        });
        imageView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                PopupMenu popup_meneu = new PopupMenu(activity,imageView);
                popup_meneu.getMenuInflater().inflate(R.menu.context,popup_meneu.getMenu());
                popup_meneu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

                    @Override
                    public boolean onMenuItemClick(MenuItem item) {

                        int id = item.getItemId();
                        if(id==R.id.add_fav){
                            Database data = new Database(activity);
                            SQLiteDatabase database = data.getWritableDatabase();
                            ContentValues values = new ContentValues();
                            values.put(Database.COULOMN_IMAGE,poster.get(position));
                            values.put(Database.VOTE_AVERAGE,vote_av.get(position));
                            values.put(Database.COULMN_OVER,over.get(position));
                            values.put(Database.COULMN_TITLE,mo_title.get(position));
                            values.put(Database.COULOMN_ID,mid.get(position));
                            values.put(Database.COULMN_DATE,"release_date");
                            database.insert(Database.TABLE_NAME,null,values);
                            Toast.makeText(activity, "Added to favourite", Toast.LENGTH_SHORT).show();
                        }
                        else if (id==R.id.details){
                            Toast.makeText(activity, "details", Toast.LENGTH_SHORT).show();
                            Intent I = new Intent(activity,Details.class);
                            I.putExtra("title",mo_title.get(position));
                            activity.startActivity(I);

                        }
                        return true;
                    }
                });

                popup_meneu.show();
                return true;

            }
        });
        return view;
    }



}
