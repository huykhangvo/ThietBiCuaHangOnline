package com.boyhotkey96.thietbicuahangonline.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.boyhotkey96.thietbicuahangonline.R;
import com.boyhotkey96.thietbicuahangonline.adapter.YeuThichAdapter;
import com.boyhotkey96.thietbicuahangonline.model.SanPham;
import com.boyhotkey96.thietbicuahangonline.ultil.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class YeuThich extends AppCompatActivity {

    private Toolbar toolbar;
    private ArrayList<SanPham> arrayListYeuthich;
    private YeuThichAdapter yeuThichAdapter;
    private TextView tvThongbao;
    private ListView lvYeuthich;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danh_sach_mong_muon);
        AnhXa();
        //CheckData();
        ActionBar();
        GetDataYeuThich();
        yeuThichAdapter.notifyDataSetChanged();
        lvYeuthich.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(YeuThich.this, ChiTietSanPham.class);
                intent.putExtra("thongtinsanpham", arrayListYeuthich.get(i));
                startActivity(intent);
            }
        });
        lvYeuthich.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int position, long l) {
                AlertDialog.Builder builder = new AlertDialog.Builder(YeuThich.this);
                builder.setTitle("Xác nhận xoá sản phẩm");
                builder.setMessage("Bạn có chắc chắn muốn xoá sản phẩm này không?");
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        sharedPreferences = getSharedPreferences("dataLogin", Context.MODE_PRIVATE);
                        //lay gia tri unique_id
                        final int sno = sharedPreferences.getInt("SNO", 0);
                        RequestQueue requestQueue = Volley.newRequestQueue(YeuThich.this);
                        StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.dddeleteyeuthich,
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        if (response.equals("success")) {
                                            Toast.makeText(YeuThich.this, "Đã xoá", Toast.LENGTH_SHORT).show();
                                            //yeuThichAdapter.notifyDataSetChanged();
                                            GetDataYeuThich();
                                        }
                                    }
                                },
                                new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        Toast.makeText(YeuThich.this, "Lỗi Xoá Yêu Thích: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                        ) {
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                Map<String, String> params = new HashMap<>();
                                SanPham sanPham = arrayListYeuthich.get(position);
                                int masp = sanPham.getMasanpham();
                                params.put("masanpham", String.valueOf(masp));
                                params.put("sno", String.valueOf(sno));
                                return params;
                            }
                        };
                        requestQueue.add(stringRequest);
                    }
                });
                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                builder.show();

                return true;
            }
        });
    }

    private void AnhXa() {
        toolbar = findViewById(R.id.toolbar);
        tvThongbao = findViewById(R.id.tvThongbao);
        lvYeuthich = findViewById(R.id.lvYeuthich);
        arrayListYeuthich = new ArrayList<SanPham>();
        yeuThichAdapter = new YeuThichAdapter(YeuThich.this, arrayListYeuthich);
        lvYeuthich.setAdapter(yeuThichAdapter);
        tvThongbao.setVisibility(View.INVISIBLE);
        lvYeuthich.setVisibility(View.INVISIBLE);
    }

    private void CheckData() {
        if (arrayListYeuthich.size() <= 0) {
            yeuThichAdapter.notifyDataSetChanged();
            tvThongbao.setVisibility(View.VISIBLE);
            lvYeuthich.setVisibility(View.INVISIBLE);
        } else {
            yeuThichAdapter.notifyDataSetChanged();
            tvThongbao.setVisibility(View.INVISIBLE);
            lvYeuthich.setVisibility(View.VISIBLE);
        }
    }

    private void ActionBar() {
        setSupportActionBar(toolbar);
        toolbar.setTitle("Danh sách sản phẩm yêu thích");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void GetDataYeuThich() {
        RequestQueue requestQueue = Volley.newRequestQueue(YeuThich.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.ddgetyeuthich,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response != null) {
                            try {
                                JSONArray jsonArray = new JSONArray(response);
                                arrayListYeuthich.clear();
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    int masp = jsonObject.getInt("masanpham");
                                    String tensp = jsonObject.getString("tensanpham");
                                    int giasp = jsonObject.getInt("giasanpham");
                                    String hinhsp = jsonObject.getString("hinhanhsanpham");
                                    String motasp = jsonObject.getString("motasanpham");
                                    int maloaisanpham = jsonObject.getInt("maloaisanpham");

                                    arrayListYeuthich.add(new SanPham(masp, tensp, giasp, hinhsp, motasp, maloaisanpham));
                                }
                                yeuThichAdapter.notifyDataSetChanged();
                                CheckData();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(YeuThich.this, "Lỗi Activity Yêu Thích: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                sharedPreferences = getSharedPreferences("dataLogin", Context.MODE_PRIVATE);
                int sno = sharedPreferences.getInt("SNO", 0);
                params.put("sno", String.valueOf(sno));
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
}
