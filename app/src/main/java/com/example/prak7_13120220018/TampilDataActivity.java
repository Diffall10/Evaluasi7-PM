package com.example.prak7_13120220018;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.net.http.HttpException;
import android.os.Bundle;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;

public class TampilDataActivity extends AppCompatActivity {
    private TableLayout tblMhs;
    private TableRow tr;
    private TextView col1, col2, col3;
    private RestHelper restHelper;
    private String stb, nama, angkatan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tampil_data);

        restHelper = new RestHelper(this);
        tblMhs = findViewById(R.id.tbl_mhs);
        tampilData();

    }

    private void tampilTblMhs(ArrayList<Mahasiswa> arrListMhs) {
        tblMhs.removeAllViews();

        tr = new TableRow(this);
        col1 = new TextView(this);
        col2 = new TextView(this);
        col3 = new TextView(this);

        col1.setText("Stambuk");
        col2.setText("Nama");
        col3.setText("Angkatan");

        col1.setWidth(200);
        col2.setWidth(300);
        col3.setWidth(150);

        tr.addView(col1);
        tr.addView(col2);
        tr.addView(col3);

        tblMhs.addView(tr);

        for(final Mahasiswa mhs: arrListMhs) {
            tr = new TableRow(this);
            col1 = new TextView(this);
            col2 = new TextView(this);
            col3 = new TextView(this);

            col1.setText(mhs.getStb());
            col2.setText(mhs.getNama());
            col3.setText(String.valueOf(mhs.getAngkatan()));

            col1.setWidth(200);
            col2.setWidth(300);
            col3.setWidth(150);

            tr.addView(col1);
            tr.addView(col2);
            tr.addView(col3);

            tblMhs.addView(tr);

            tr.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    for (int i=0; i<tblMhs.getChildCount(); i++) {
                        stb = mhs.getStb();
                        nama = mhs.getNama();
                        angkatan = String.valueOf(mhs.getAngkatan());
                        if (tblMhs.getChildAt(i) == view)
                            tblMhs.getChildAt(i).setBackgroundColor(Color.LTGRAY);
                        else
                            tblMhs.getChildAt(i).setBackgroundColor(Color.WHITE);
                    }
                }
            });
        }
    }
    private void tampilData() {

        restHelper.getDataMhs(new RestCallbackMahasiswa() {
            @Override
            public void requestDataMhsSuccess(ArrayList<Mahasiswa> arrayList) {
                tampilTblMhs(arrayList);
            }
        });
    }

    public void btnEditClick(View view) {
        Intent intent = new Intent();
        intent.putExtra("stb", stb);
        intent.putExtra("nama", nama);
        intent.putExtra("angkatan", angkatan);
        setResult(1, intent);
        finish();
    }

    public void btnHapusClick(View view) {
        if (stb == null) return;
        restHelper.hapusData(stb, new RestCallbackMahasiswa() {
            @Override
            public void requestDataMhsSuccess(ArrayList<Mahasiswa> arrayList) {
                tampilTblMhs(arrayList);
            }
        });
    }
}