package com.example.akshat.remider;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.TextView;

/**
 * Created by akshat on 19/6/17.
 */

public class StatusActivity extends AppCompatActivity {

    private TextView Name;
    private TextView Status;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int Width=dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(Width*.8),(int)(height*.5));

        Name = (TextView)findViewById(R.id.title);
        Status = (TextView)findViewById(R.id.status);

        Intent intent = getIntent();
        String name = intent.getStringExtra("NAME");
        String status = intent.getStringExtra("STATUS");
        Log.v(name,status);
        Name.setText(name);
        Status.setText(status);
    }
}
