package com.example.quiz1pemrogramanbergerakrahmatzuhrihafidz;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    private static final int HARGA_SGS = 12999999;
    private static final int HARGA_AA5 = 9999999;
    private static final int HARGA_MP3 = 28999999;

    private int jumlahSGS = 0;
    private int jumlahAA5 = 0;
    private int jumlahMP3 = 0;

    private TextView textViewTotal;
    private TextView textViewBarang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewTotal = findViewById(R.id.textViewTotal);
        textViewBarang = findViewById(R.id.textViewBarang);
    }

    public void tambahBarang(View view) {
        EditText editTextKode = findViewById(R.id.editTextKode);
        EditText editTextJumlah = findViewById(R.id.editTextJumlah);
        int jumlah = 0;
        String kode = editTextKode.getText().toString().trim();

        try {
            jumlah = Integer.parseInt(editTextJumlah.getText().toString());
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Masukkan jumlah barang", Toast.LENGTH_SHORT).show();
            return;
        }

        switch (kode) {
            case "SGS":
                jumlahSGS += jumlah;
                break;
            case "AA5":
                jumlahAA5 += jumlah;
                break;
            case "MP3":
                jumlahMP3 += jumlah;
                break;
            default:
                Toast.makeText(this, "Kode barang tidak valid", Toast.LENGTH_SHORT).show();
                return;
        }

        // Tampilkan pesan sukses atau update UI lainnya jika diperlukan
        Toast.makeText(this, "Barang berhasil ditambahkan", Toast.LENGTH_SHORT).show();
        updateBarangText();
    }

    public void checkout(View view) {
        int totalPembelian = (jumlahSGS * HARGA_SGS) + (jumlahAA5 * HARGA_AA5) + (jumlahMP3 * HARGA_MP3);

        // Hitung diskon berdasarkan membership yang dipilih
        int diskon = 0;
        RadioGroup radioGroupMembership = findViewById(R.id.radioGroupMembership);

        // Periksa semua radio button
        RadioButton radioButtonGold = findViewById(R.id.radioButtonGold);
        RadioButton radioButtonSilver = findViewById(R.id.radioButtonSilver);
        RadioButton radioButtonBiasa = findViewById(R.id.radioButtonBiasa);

        // Periksa apakah salah satu radio button dipilih
        if (!radioButtonGold.isChecked() && !radioButtonSilver.isChecked() && !radioButtonBiasa.isChecked()) {
            Toast.makeText(this, "Pilih jenis diskon terlebih dahulu", Toast.LENGTH_SHORT).show();
            return;
        }

        // Tentukan diskon berdasarkan radio button yang dipilih
        if (radioButtonGold.isChecked()) {
            diskon = (int) (totalPembelian * 0.1);
        } else if (radioButtonSilver.isChecked()) {
            diskon = (int) (totalPembelian * 0.05);
        } else if (radioButtonBiasa.isChecked()) {
            diskon = (int) (totalPembelian * 0.02);
        }

        // Diskon tambahan jika total pembelian di atas 10 juta
        if (totalPembelian > 10000000) {
            diskon += 100000;
        }

        int totalPembayaran = totalPembelian - diskon;

        DecimalFormat formatter = new DecimalFormat("#,###");
        String strDiskon = formatter.format(diskon);
        String strTotalPembelian = formatter.format(totalPembelian);
        String strTotalPembayaran = formatter.format(totalPembayaran);

        textViewTotal.setText("Total Pembelian: Rp" + strTotalPembelian + "\nDiskon: Rp" + strDiskon + "\nTotal Pembayaran: Rp" + strTotalPembayaran);
    }

    private void updateBarangText() {
        String strBarang = "Barang yang dibeli:\n";
        if (jumlahSGS > 0) {
            strBarang += "Samsung Galaxy S (SGS): " + jumlahSGS + "\n";
        }
        if (jumlahAA5 > 0) {
            strBarang += "Acer Aspire 5 (AA5): " + jumlahAA5 + "\n";
        }
        if (jumlahMP3 > 0) {
            strBarang += "Macbook Pro M3 (MP3): " + jumlahMP3 + "\n";
        }
        textViewBarang.setText(strBarang);
    }
}
