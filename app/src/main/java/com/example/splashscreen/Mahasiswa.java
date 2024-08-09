package com.example.splashscreen;

public class Mahasiswa {
    private int id;
    private String nama;
    private String tanggalLahir;
    private String jenisKelamin;
    private String alamat;
    private String nomor;

    public Mahasiswa() {}

    public Mahasiswa(int id, String nama,String nomor, String tanggalLahir, String jenisKelamin, String alamat) {
        this.id = id;
        this.nama = nama;
        this.nomor= nomor;
        this.tanggalLahir = tanggalLahir;
        this.jenisKelamin = jenisKelamin;
        this.alamat = alamat;

    }

    // Getter dan Setter
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNama() { return nama; }
    public void setNama(String nama) { this.nama = nama; }

    public String getTanggalLahir() { return tanggalLahir; }
    public void setTanggalLahir(String tanggalLahir) { this.tanggalLahir = tanggalLahir; }

    public String getJenisKelamin() { return jenisKelamin; }
    public void setJenisKelamin(String jenisKelamin) { this.jenisKelamin = jenisKelamin; }

    public String getAlamat() { return alamat; }
    public void setAlamat(String alamat) { this.alamat = alamat; }

    public String getNomor() { return nomor; }
    public void setNomor(String nomor) { this.nomor = nomor; }


}
