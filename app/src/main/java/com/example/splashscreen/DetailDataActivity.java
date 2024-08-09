package com.example.splashscreen;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Html;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class DetailDataActivity extends AppCompatActivity {
    private EditText etTitle, etContent, etId, etTtl, etJkl;
    private DatabaseHelper dbHelper;
    private Mahasiswa mahasiswa;
    private boolean isEdit = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detail_data);

        etTitle = findViewById(R.id.etTitleDetail);
        etContent = findViewById(R.id.etContentDetail);
        etId = findViewById(R.id.etNomorDetail);
        etTtl = findViewById(R.id.etTanggallahirDetail);
        etJkl = findViewById(R.id.etJeniskelaminDetail);

        dbHelper = new DatabaseHelper(this);

        if (getIntent().hasExtra("id")) {
            int mahasiswaId = getIntent().getIntExtra("id", -1);
            mahasiswa = dbHelper.getMahasiswa(mahasiswaId);
            if (mahasiswa != null) {
                etId.setText(mahasiswa.getNomor());
                etTitle.setText(mahasiswa.getNama());
                etTtl.setText(mahasiswa.getTanggalLahir());
                etJkl.setText(mahasiswa.getJenisKelamin());
                etContent.setText(mahasiswa.getAlamat());
                isEdit = true;
            }
        }

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setTitle(Html.fromHtml("<font color='#FFFFFF'>Detail Data</font>"));
            actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#008B8B")));
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.baseline_arrow_back_24);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}