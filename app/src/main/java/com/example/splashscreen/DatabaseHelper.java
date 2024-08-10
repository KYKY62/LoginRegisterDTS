package com.example.splashscreen;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "App.db";
    public static final String TABLE_NAME = "user_table";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "EMAIL";
    public static final String COL_3 = "PASSWORD";

    private static final String TABLE_MAHASISWA = "mahasiswa";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAMA = "nama";
    private static final String COLUMN_NOMOR = "nomor";
    private static final String COLUMN_TANGGALLAHIR = "tanggal_lahir";
    private static final String COLUMN_JENISKELAMIN = "jenis_kelamin";
    private static final String COLUMN_ALAMAT = "alamat";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, EMAIL TEXT, PASSWORD TEXT)");

        String createTable = "CREATE TABLE " + TABLE_MAHASISWA + " ("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_NAMA + " TEXT, "
                + COLUMN_NOMOR + " TEXT, "
                + COLUMN_TANGGALLAHIR + " TEXT, "
                + COLUMN_JENISKELAMIN + " TEXT, "
                + COLUMN_ALAMAT + " TEXT)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MAHASISWA);
        onCreate(db);
    }

    public boolean insert(String email, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, email);
        contentValues.put(COL_3, password);
        long result = db.insert(TABLE_NAME, null, contentValues);
        return result != -1;
    }

    public boolean checkEmail(String email) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE EMAIL = ?", new String[]{email});
        if (cursor.getCount() > 0) return true;
        else return false;
    }

    public boolean checkEmailPassword(String email, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE EMAIL = ? AND PASSWORD = ?", new String[]{email, password});
        if (cursor.getCount() > 0) return true;
        else return false;
    }

    public long addMahasiswa(Mahasiswa mahasiswa) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAMA, mahasiswa.getNama());
        values.put(COLUMN_NOMOR, mahasiswa.getNomor());
        values.put(COLUMN_TANGGALLAHIR, mahasiswa.getTanggalLahir());
        values.put(COLUMN_JENISKELAMIN, mahasiswa.getJenisKelamin());
        values.put(COLUMN_ALAMAT, mahasiswa.getAlamat());
        long id = db.insert(TABLE_MAHASISWA, null, values);
        db.close();
        return id;
    }

    public Mahasiswa getMahasiswa(long id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_MAHASISWA,
                new String[]{COLUMN_ID, COLUMN_NAMA,COLUMN_NOMOR, COLUMN_TANGGALLAHIR, COLUMN_JENISKELAMIN, COLUMN_ALAMAT},
                COLUMN_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
        Mahasiswa mahasiswa = new Mahasiswa(
                cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)),
                cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAMA)),
                cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NOMOR)),
                cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TANGGALLAHIR)),
                cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_JENISKELAMIN)),
                cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ALAMAT)));
        cursor.close();
        return mahasiswa;
    }

    public List<Mahasiswa> getAllMahasiswa() {
        List<Mahasiswa> mahasiswaList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_MAHASISWA + " ORDER BY " + COLUMN_NAMA + " ASC";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Mahasiswa mahasiswa = new Mahasiswa();
                mahasiswa.setId(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)));
                mahasiswa.setNama(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAMA)));
                mahasiswa.setNomor(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NOMOR)));
                mahasiswa.setTanggalLahir(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TANGGALLAHIR)));
                mahasiswa.setJenisKelamin(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_JENISKELAMIN)));
                mahasiswa.setAlamat(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ALAMAT)));
                mahasiswaList.add(mahasiswa);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return mahasiswaList;
    }
    public int updateMahasiswa(Mahasiswa mahasiswa) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAMA, mahasiswa.getNama());
        values.put(COLUMN_NOMOR, mahasiswa.getNomor());
        values.put(COLUMN_TANGGALLAHIR, mahasiswa.getTanggalLahir());
        values.put(COLUMN_JENISKELAMIN, mahasiswa.getJenisKelamin());
        values.put(COLUMN_ALAMAT, mahasiswa.getAlamat());
        return db.update(TABLE_MAHASISWA, values, COLUMN_ID + " = ?", new String[]{String.valueOf(mahasiswa.getId())});
    }
    public void deleteMahasiswa(Mahasiswa mahasiswa) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_MAHASISWA, COLUMN_ID + " = ?", new String[]{String.valueOf(mahasiswa.getId())});
        db.close();
    }

}




