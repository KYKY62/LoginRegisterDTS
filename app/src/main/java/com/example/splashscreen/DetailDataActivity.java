package com.example.splashscreen;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Html;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class DetailDataActivity extends AppCompatActivity {
    private EditText etTitle, etContent, etId, etTtl;
    private DatabaseHelper dbHelper;
    private Mahasiswa mahasiswa;
    private RadioGroup rgJenisKelamin;
    private RadioButton rbLakiLaki, rbPerempuan;
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

        dbHelper = new DatabaseHelper(this);

        rgJenisKelamin = findViewById(R.id.rgJenisKelamin);
        rbLakiLaki = findViewById(R.id.rbLakiLaki);
        rbPerempuan = findViewById(R.id.rbPerempuan);


        if (mahasiswa != null) {
            if (mahasiswa.getJenisKelamin().equals("Laki-laki")) {
                rbLakiLaki.setChecked(true);
            } else if (mahasiswa.getJenisKelamin().equals("Perempuan")) {
                rbPerempuan.setChecked(true);
            }
        }

        if (getIntent().hasExtra("id")) {
            int mahasiswaId = getIntent().getIntExtra("id", -1);
            mahasiswa = dbHelper.getMahasiswa(mahasiswaId);
            if (mahasiswa != null) {
                etId.setText(mahasiswa.getNomor());
                etTitle.setText(mahasiswa.getNama());
                etTtl.setText(mahasiswa.getTanggalLahir());
                if (mahasiswa.getJenisKelamin().equals("Laki-laki")) {
                    rbLakiLaki.setChecked(true);
                } else if (mahasiswa.getJenisKelamin().equals("Perempuan")) {
                    rbPerempuan.setChecked(true);
                }
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