package com.example.document.last;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    GridView grid;
    ArrayList<String> mo_title;
    ArrayList<String>vote_co;
    ArrayList<String>vote_av;
    ArrayList<String>poster;
    ArrayList<String>over;
    ArrayList<String>date;
    ArrayList<Integer>mid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mo_title=new ArrayList<String>();
        vote_co=new ArrayList<String>();
        vote_av=new ArrayList<String>();
        poster=new ArrayList<String>();
        over=new ArrayList<String>();
        date=new ArrayList<String>();
        mid=new ArrayList<Integer>();
        grid = (GridView)findViewById(R.id.grid);
        Data data = new Data("http://api.themoviedb.org/3/discover/movie?sort_by=popularity.desc&api_key=d04160312987af22a80ba27b59cd080c");
        data.execute();
        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
});
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.option_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.popular){
            Data data = new Data("http://api.themoviedb.org/3/discover/movie?sort_by=popularity.desc&api_key=d04160312987af22a80ba27b59cd080c");
            data.execute();
        } else if (id == R.id.fav){
            mo_title.clear();
            poster.clear();
            vote_av.clear();
            vote_co.clear();
            over.clear();
            mid.clear();
            Database database = new Database(MainActivity.this);
            SQLiteDatabase sqLiteDatabase = database.getReadableDatabase();
            Cursor cursor = sqLiteDatabase.rawQuery("select * from "+Database.TABLE_NAME,null);

            if (cursor.moveToFirst()){
                do {
                    String name =   cursor.getString(cursor.getColumnIndex(Database.COULMN_TITLE));
                    mo_title.add(name);
                    String image =   cursor.getString(cursor.getColumnIndex(Database.COULOMN_IMAGE));
                    poster.add(image);
                    String voteav =   cursor.getString(cursor.getColumnIndex(Database.VOTE_AVERAGE));
                    vote_av.add(voteav);
                    String overview =   cursor.getString(cursor.getColumnIndex(Database.COULMN_OVER));
                    over.add(overview);
                    int moid = cursor.getInt(cursor.getColumnIndex(Database.COULOMN_ID));
                    mid.add(moid);
                }while(cursor.moveToNext());
            }
            myAdapter adapter = new myAdapter(MainActivity.this,mo_title,vote_co,vote_av,poster,over,mid);
            grid.setAdapter(adapter);

        }
        else if(id==R.id.toprated){
            Data data = new Data("http://api.themoviedb.org/3/discover/movie?sort_by=toprated.desc&api_key=d04160312987af22a80ba27b59cd080c");
            data.execute();
        }
        return super.onOptionsItemSelected(item);
    }

    class Data extends AsyncTask<Object, Object, String> {
        String src;

        public Data(String src) {
            this.src = src;
        }

        @Override
        protected String doInBackground(Object... params) {
            String response = "";

            try {

                String line = "";
                URL url = new URL(src);
                URLConnection urlConnection = url.openConnection();
                urlConnection.connect();
                InputStream input = url.openStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(input));

                while ((line = reader.readLine()) != null) {
                    response = response + line;
                }


            } catch (MalformedURLException e) {

                e.printStackTrace();

            } catch (IOException e) {

                e.printStackTrace();

            }

            return response;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                JSONObject object = new JSONObject(s);
                JSONArray contacts = object.getJSONArray("results");
                mo_title.clear();
                vote_av.clear();
                vote_co.clear();
                poster.clear();
                over.clear();
                date.clear();
                mid.clear();
                for (int i = 0; i < contacts.length(); i++) {

                    JSONObject contacts_obj = contacts.getJSONObject(i);
                    String title = contacts_obj.getString("title");
                    String vote_count = contacts_obj.getString("vote_count");
                    String vote_average = contacts_obj.getString("vote_average");
                    String poster_path = contacts_obj.getString("poster_path");
                    String overview = contacts_obj.getString("overview");
                    String datee = contacts_obj.getString("release_date");
                    int m_id = contacts_obj.getInt("id");

                    mo_title.add(title);
                    vote_co.add(vote_count);
                    vote_av.add(vote_average);
                    poster.add("http://image.tmdb.org/t/p/w185"+poster_path);
                    over.add(overview);
                    date.add(datee);
                    mid.add(m_id);

                }
                myAdapter adapter = new myAdapter(MainActivity.this,mo_title,vote_co,vote_av,poster,over,mid);
                grid.setAdapter(adapter);


            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }
}

