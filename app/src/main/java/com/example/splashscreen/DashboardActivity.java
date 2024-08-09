package com.example.splashscreen;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class DashboardActivity extends AppCompatActivity {
Button btnlh,btnid,btninfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_dashboard);

        btnlh = findViewById(R.id.btnlihatdata);
        btnid = findViewById(R.id.btninputdata);
        btninfo = findViewById(R.id.btninformasi);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setTitle(Html.fromHtml("<font color='#FFFFFF'>Dashboard</font>"));
            actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#008B8B")));
            actionBar.setDisplayHomeAsUpEnabled(false);

        }

        btnlh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardActivity.this, home_activity.class);
                startActivity(intent);
            }
        });

        btnid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardActivity.this, AddEditNoteActivity.class);
                startActivity(intent);
            }
        });

        btninfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardActivity.this, InformasiActivity.class);
                startActivity(intent);
            }
        });


    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}