package com.boyhotkey96.thietbicuahangonline.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.boyhotkey96.thietbicuahangonline.R;
import com.boyhotkey96.thietbicuahangonline.adapter.GioHangAdapter;
import com.boyhotkey96.thietbicuahangonline.ultil.CheckConnectInternet;

import java.text.DecimalFormat;

public class GioHang extends AppCompatActivity {

    private Toolbar toolbar;
    private TextView tvThongbao;
    private ListView lvGiohang;
    private static TextView tvTongtien;
    private Button btnThanhtoanhang;
    private Button btnTieptucmuahang;
    private GioHangAdapter gioHangAdapter;

    public static void EvenUltil() {
        long tongtien = 0;
        for (int i = 0; i < Navigation.arraylistGiohang.size(); i++) {
            tongtien += Navigation.arraylistGiohang.get(i).getGiasp();
        }
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        tvTongtien.setText(decimalFormat.format(tongtien).replace(",", ".") + "đ");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gio_hang);
        AnhXa();
        ActionBar();
        CheckData();
        EvenUltil();
        CactOnItemListView();
        EventButton();
    }

    private void AnhXa() {
        toolbar = findViewById(R.id.toolbar);
        lvGiohang = findViewById(R.id.lvGiohang);
        tvThongbao = findViewById(R.id.tvThongbao);
        tvTongtien = findViewById(R.id.tvTongtien);
        btnThanhtoanhang = findViewById(R.id.btnThanhtoanhang);
        btnTieptucmuahang = findViewById(R.id.btnTieptucmuahang);
        gioHangAdapter = new GioHangAdapter(this, Navigation.arraylistGiohang);
        lvGiohang.setAdapter(gioHangAdapter);
    }

    private void ActionBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        toolbar.setTitle("Giỏ hàng");
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void CheckData() {
        if (Navigation.arraylistGiohang.size() <= 0) {
            gioHangAdapter.notifyDataSetChanged();
            tvThongbao.setVisibility(View.VISIBLE);
            lvGiohang.setVisibility(View.INVISIBLE);
        } else {
            gioHangAdapter.notifyDataSetChanged();
            tvThongbao.setVisibility(View.INVISIBLE);
            lvGiohang.setVisibility(View.VISIBLE);
        }
    }

    private void EventButton() {
        btnTieptucmuahang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(GioHang.this, Navigation.class));
            }
        });
        btnThanhtoanhang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Navigation.arraylistGiohang.size() > 0) {
                    startActivity(new Intent(GioHang.this, ThongTinKhachHang.class));
                } else {
                    CheckConnectInternet.ShowToast(GioHang.this, "Giỏ hàng của bạn chưa có sản phẩm để thanh toán");

                }
            }
        });
    }

    private void CactOnItemListView() {
        lvGiohang.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int position, long l) {
                AlertDialog.Builder builder = new AlertDialog.Builder(GioHang.this);
                builder.setTitle("Xác nhận xoá sản phẩm");
                builder.setMessage("Bạn có chắc chắn muốn xoá sản phẩm này không?");
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (Navigation.arraylistGiohang.size() <= 0) {
                            tvThongbao.setVisibility(View.VISIBLE);
                        } else {
                            Navigation.arraylistGiohang.remove(position);
                            gioHangAdapter.notifyDataSetChanged();
                            EvenUltil();
                            if (Navigation.arraylistGiohang.size() <= 0) {
                                tvThongbao.setVisibility(View.VISIBLE);
                            } else {
                                tvThongbao.setVisibility(View.INVISIBLE);
                                gioHangAdapter.notifyDataSetChanged();
                                EvenUltil();
                            }
                        }
                    }
                });
                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        gioHangAdapter.notifyDataSetChanged();
                        EvenUltil();
                    }
                });
                builder.show();

                return true;
            }
        });
    }
}
