package com.example.splashscreen;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.AdapterView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;


import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class home_activity extends AppCompatActivity {
    private ListView listView;

    private DatabaseHelper dbHelper;
    private List<Mahasiswa> mahasiswaList;
    private ArrayAdapter<String> adapter;
    private List<String> titlesList;
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);

        listView = findViewById(R.id.listView);

        fab = findViewById(R.id.fab);
        dbHelper = new DatabaseHelper(this);
        mahasiswaList = new ArrayList<>();
        titlesList = new ArrayList<>();

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setTitle(Html.fromHtml("<font color='#FFFFFF'>Data Mahasiswa</font>"));
            actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#008B8B")));
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.baseline_arrow_back_24);
        }

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(home_activity.this,
                        AddEditNoteActivity.class);
                startActivity(intent);
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

                new AlertDialog.Builder(home_activity.this)
                        .setTitle("Pilihan Aksi")
                        .setItems(new CharSequence[]
                                {"Lihat Data", "Update Data", "Hapus Data"}, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                switch (which) {
                                    case 0: // Lihat Data
                                        Intent lihatData = new Intent(home_activity.this,
                                                DetailDataActivity.class);
                                        lihatData.putExtra("id", mahasiswaList.get(position).getId());
                                        startActivity(lihatData);
                                        break;
                                    case 1: // Update Data
                                        Intent updateData = new Intent(home_activity.this,
                                                AddEditNoteActivity.class);
                                        updateData.putExtra("id", mahasiswaList.get(position).getId());
                                        startActivity(updateData);
                                        break;
                                    case 2: // Hapus Data
                                        new AlertDialog.Builder(home_activity.this)
                                                .setTitle("Hapus Data")
                                                .setMessage("Apakah Anda yakin ingin menghapus data ini?")
                                                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                                    public void onClick(DialogInterface dialog, int whichButton) {
                                                        // Hapus item setelah pengguna mengonfirmasi
                                                        dbHelper.deleteMahasiswa(mahasiswaList.get(position));
                                                        // Muat ulang data setelah penghapusan
                                                        loadNotes();
                                                        Toast.makeText(home_activity.this, "Data berhasil dihapus", Toast.LENGTH_SHORT).show();
                                                    }})
                                                .setNegativeButton(android.R.string.no, null) // Tidak melakukan apa-apa jika "No" dipilih
                                                .show();
                                        break;
                                }
                            }
                        })
                        .show();
            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long id) {
               new AlertDialog.Builder(home_activity.this)
                       .setTitle("Hapus Data")
                       .setMessage("Apakah Anda yakin ingin menghapus data ini?")
                       .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                           public void onClick(DialogInterface dialog, int whichButton) {
                               // Hapus item setelah pengguna mengonfirmasi
                               dbHelper.deleteMahasiswa(mahasiswaList.get(position));
                               // Muat ulang data setelah penghapusan
                               loadNotes();
                               Toast.makeText(home_activity.this, "Data berhasil dihapus", Toast.LENGTH_SHORT).show();
                           }})
                       .setNegativeButton(android.R.string.no, null) // Tidak melakukan apa-apa jika "No" dipilih
                       .show();
                return true;
            }
        });



    }
    @Override
    protected void onResume() {
        super.onResume();
        loadNotes();
    }
    private void loadNotes() {
        mahasiswaList = dbHelper.getAllMahasiswa();
        titlesList.clear();
        for (Mahasiswa mahasiswa : mahasiswaList) {
            titlesList.add(mahasiswa.getNama()+ "\n" + mahasiswa.getAlamat());
        }
        adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, titlesList);
        listView.setAdapter(adapter);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
