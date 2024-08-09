package com.example.splashscreen;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AddEditNoteActivity extends AppCompatActivity {
    private EditText etTitle, etContent, etId, etTtl, etJkl;
    private Button btnSave;
    private DatabaseHelper dbHelper;
    private Mahasiswa mahasiswa;
    private boolean isEdit = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_edit_note);

        etTitle = findViewById(R.id.etTitle);
        etContent = findViewById(R.id.etContent);
        etId = findViewById(R.id.etNomor);
        etTtl = findViewById(R.id.etTanggallahir);
        etJkl = findViewById(R.id.etJeniskelamin);

        btnSave = findViewById(R.id.btnSave);
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

        etTtl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                // Membuat DatePickerDialog
                DatePickerDialog datePickerDialog = new DatePickerDialog(AddEditNoteActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDay) {
                                // Format tanggal ke yyyy-MM-dd
                                String selectedDate = selectedYear + "-" + (selectedMonth + 1) + "-" + selectedDay;
                                etTtl.setText(selectedDate);
                            }
                        }, year, month, day);
                datePickerDialog.show();
            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String titles = etTitle.getText().toString().trim();
                String content = etContent.getText().toString().trim();
                String nomors = etId.getText().toString().trim();
                String tanggalLahir = etTtl.getText().toString().trim();
                String jenisKelamin = etJkl.getText().toString().trim();
                String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",
                        Locale.getDefault()).format(new Date());
                if (titles.isEmpty() || content.isEmpty() || nomors.isEmpty() || tanggalLahir.isEmpty() || jenisKelamin.isEmpty()) {
                    Toast.makeText(AddEditNoteActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (isEdit) {
                    mahasiswa.setNama(titles);
                    mahasiswa.setAlamat(content);
                    mahasiswa.setNomor(nomors);
                    mahasiswa.setTanggalLahir(tanggalLahir);
                    mahasiswa.setJenisKelamin(jenisKelamin);
                    dbHelper.updateMahasiswa(mahasiswa);

                } else {
                    mahasiswa = new Mahasiswa();
                    mahasiswa.setNama(titles);
                    mahasiswa.setAlamat(content);
                    mahasiswa.setNomor(nomors);
                    mahasiswa.setTanggalLahir(tanggalLahir);
                    mahasiswa.setJenisKelamin(jenisKelamin);
                    dbHelper.addMahasiswa(mahasiswa);
                }
                finish();
            }
        });

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            if (isEdit){
                actionBar.setTitle(Html.fromHtml("<font color='#FFFFFF'>Update Data</font>"));
            }else{
                actionBar.setTitle(Html.fromHtml("<font color='#FFFFFF'>Input Data</font>"));
            }

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