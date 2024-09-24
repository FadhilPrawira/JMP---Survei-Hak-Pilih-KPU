package xyz.fadhilprawira.jmp_surveihakpilihkpu.entity;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Voter implements Parcelable {

    private int id;
    private String nama;
    private String nik;
    private String alamat;
    private String noHp;
    private String jenisKelamin;
    private String gambar;
    private String tanggalInput;

    public Voter(String nama, String nik, String alamat, String noHp, String jenisKelamin, String gambar, int id, String tanggalInput) {

        this.nama = nama;
        this.nik = nik;
        this.alamat = alamat;
        this.noHp = noHp;
        this.jenisKelamin = jenisKelamin;
        this.gambar = gambar;
        this.id = id;
        this.tanggalInput = tanggalInput;
    }

    protected Voter(Parcel in) {
        id = in.readInt();
        nama = in.readString();
        nik = in.readString();
        alamat = in.readString();
        noHp = in.readString();
        jenisKelamin = in.readString();
        gambar = in.readString();
        tanggalInput = in.readString();
    }

    public static final Creator<Voter> CREATOR = new Creator<Voter>() {
        @Override
        public Voter createFromParcel(Parcel in) {
            return new Voter(in);
        }

        @Override
        public Voter[] newArray(int size) {
            return new Voter[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getNik() {
        return nik;
    }

    public void setNik(String nik) {
        this.nik = nik;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getNoHp() {
        return noHp;
    }

    public void setNoHp(String noHp) {
        this.noHp = noHp;
    }

    public String getJenisKelamin() {
        return jenisKelamin;
    }

    public void setJenisKelamin(String jenisKelamin) {
        this.jenisKelamin = jenisKelamin;
    }

    public String getGambar() {
        return gambar;
    }

    public void setGambar(String gambar) {
        this.gambar = gambar;
    }


    public String getTanggalInput() {
        return tanggalInput;
    }

    public void setTanggalInput(String tanggalInput) {
        this.tanggalInput = tanggalInput;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(nama);
        dest.writeString(nik);
        dest.writeString(alamat);
        dest.writeString(noHp);
        dest.writeString(jenisKelamin);
        dest.writeString(gambar);
        dest.writeString(tanggalInput);
    }
}
