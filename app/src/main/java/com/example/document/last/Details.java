package com.example.document.last;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class Details extends AppCompatActivity {

    ImageView image;
    TextView text;
    EditText edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        image = (ImageView)findViewById(R.id.image_de);
        text = (TextView)findViewById(R.id.text_de);
        edit = (EditText)findViewById(R.id.edit_de);
         String TITLE=   getIntent().getExtras().getString("title");
        text.setText(TITLE);


    }
}
