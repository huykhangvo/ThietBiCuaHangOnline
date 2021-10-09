package com.boyhotkey96.thietbicuahangonline.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.boyhotkey96.thietbicuahangonline.R;
import com.boyhotkey96.thietbicuahangonline.model.GioHang;
import com.boyhotkey96.thietbicuahangonline.model.SanPham;
import com.boyhotkey96.thietbicuahangonline.ultil.Server;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ChiTietSanPham extends AppCompatActivity {

    int masp = 0;
    int masp2 = 0;
    String tenchitiet = "";
    int giachitiet = 0;
    String hinhanhchitiet = "";
    String motachitiet = "";
    int maloaisp = 0;
    private Toolbar toolbar;
    private ImageView imgChitietsanpham;
    private TextView tvTenchitietsanpham, tvGiachitietsanpham, tvMotachitietsanpham;
    private Spinner spinner;
    private Button btnThemgiohang;
    private ToggleButton tgYeuthich;
    private SanPham sanPham;
    private SharedPreferences sharedPreferences, sharedPreferencesyeuthich;
    private ArrayList<Integer> code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_san_pham);
        AnhXa();
        ActionBar();
        GetInformation();
        //Xu ly Spinner
        CatchEvenSpinner();
        //Buton'
        EventButton();
    }

    private void AnhXa() {
        toolbar = findViewById(R.id.toolbar);
        imgChitietsanpham = findViewById(R.id.imgChitietsanpham);
        tvTenchitietsanpham = findViewById(R.id.tvTenchitietsanpham);
        tvGiachitietsanpham = findViewById(R.id.tvGiachitietsanpham);
        tvMotachitietsanpham = findViewById(R.id.tvMotachitietsanpham);
        spinner = findViewById(R.id.spinnerChitietsanpham);
        btnThemgiohang = findViewById(R.id.btnThemgiohang);
        tgYeuthich = findViewById(R.id.tgYeuthich);
    }

    private void ActionBar() {
        setSupportActionBar(toolbar);
        toolbar.setTitle("Chi tiết sản phẩm");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void GetInformation() {
        sanPham = (SanPham) getIntent().getSerializableExtra("thongtinsanpham");
        masp = sanPham.getMasanpham();
        tenchitiet = sanPham.getTensanpham();
        giachitiet = sanPham.getGiasanpham();
        hinhanhchitiet = sanPham.getHinhanhsanpham();
        motachitiet = sanPham.getMotasanpham();
        maloaisp = sanPham.getMaloaisanpham();
        tvTenchitietsanpham.setText(tenchitiet);
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        tvGiachitietsanpham.setText("Giá: " + decimalFormat.format(giachitiet).replaceAll(",", ".") + "đ");
        Picasso.get().load(hinhanhchitiet)
                .placeholder(R.drawable.ic_noimage)
                .error(R.drawable.ic_error)
                .into(imgChitietsanpham);
        tvMotachitietsanpham.setText(motachitiet);
    }

    private void EventButton() {
        btnThemgiohang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Navigation.arraylistGiohang.size() > 0) {
                    int sl = Integer.parseInt(spinner.getSelectedItem().toString());
                    boolean exsits = false;
                    for (int i = 0; i < Navigation.arraylistGiohang.size(); i++) {
                        if (Navigation.arraylistGiohang.get(i).getIdsp() == masp) {
                            Navigation.arraylistGiohang.get(i).setSoluongsp(Navigation.arraylistGiohang.get(i).getSoluongsp() + sl);
                            if (Navigation.arraylistGiohang.get(i).getSoluongsp() >= 10) {
                                Navigation.arraylistGiohang.get(i).setSoluongsp(10);
                            }
                            Navigation.arraylistGiohang.get(i).setGiasp(giachitiet * Navigation.arraylistGiohang.get(i).getSoluongsp());
                            exsits = true;
                        }
                    }
                    if (exsits == false) {
                        int soluong = Integer.parseInt(spinner.getSelectedItem().toString());
                        long giamoi = soluong * giachitiet;
                        Navigation.arraylistGiohang.add(new GioHang(masp, tenchitiet, giamoi, hinhanhchitiet, soluong));
                    }
                } else {
                    int soluong = Integer.parseInt(spinner.getSelectedItem().toString());
                    long giamoi = soluong * giachitiet;
                    Navigation.arraylistGiohang.add(new GioHang(masp, tenchitiet, giamoi, hinhanhchitiet, soluong));
                }
                Intent intent = new Intent(ChiTietSanPham.this, com.boyhotkey96.thietbicuahangonline.activity.GioHang.class);
                startActivity(intent);
            }
        });
        /*sharedPreferencesyeuthich = getSharedPreferences("shareyeuthich", MODE_PRIVATE);
        //int masp2 = sharedPreferencesyeuthich.getInt("masanpham", 0);
        boolean save = sharedPreferencesyeuthich.getBoolean("savepress", true);*/
        RequestQueue requestQueue = Volley.newRequestQueue(ChiTietSanPham.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.ddgetyeuthich,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response != null) {
                            try {
                                JSONArray jsonArray = new JSONArray(response);
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    masp2 = jsonObject.getInt("masanpham");
                                    Log.d("UIUI 2", String.valueOf(masp2));

                                    code = new ArrayList<Integer>(100);
                                    code.add(masp2);
                                    for (int j = 0; j < code.size(); j++) {
                                        Log.d("UIUI", String.valueOf(masp) + " " + code.get(j));
                                        if (code.get(j) == masp) {
                                            tgYeuthich.setChecked(true);
                                            break;
                                        }

                                    }
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ChiTietSanPham.this, "Lỗi Activity Yêu Thích: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
        ) {
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

        tgYeuthich.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            int yeuthich = 0;
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (yeuthich == 0) {
                    sharedPreferences = getSharedPreferences("dataLogin", Context.MODE_PRIVATE);
                    //lay gia tri unique_id
                    final int sno = sharedPreferences.getInt("SNO", 0);
                    Log.d("SNO", String.valueOf(sno));
                    RequestQueue requestQueue = Volley.newRequestQueue(ChiTietSanPham.this);
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.ddinsertyeuthich,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    if (response.equals("success")) {
                                        Toast.makeText(ChiTietSanPham.this, response, Toast.LENGTH_SHORT).show();
                                    }
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Toast.makeText(ChiTietSanPham.this, "Lỗi: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                    ) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> params = new HashMap<>();
                            params.put("masanpham", String.valueOf(sanPham.getMasanpham()));
                            params.put("tensanpham", sanPham.getTensanpham());
                            params.put("giasanpham", String.valueOf(sanPham.getGiasanpham()));
                            params.put("hinhanhsanpham", sanPham.getHinhanhsanpham());
                            params.put("motasanpham", sanPham.getMotasanpham());
                            params.put("maloaisanpham", String.valueOf(sanPham.getMaloaisanpham()));
                            params.put("sno", String.valueOf(sno));
                            return params;
                        }
                    };
                    requestQueue.add(stringRequest);
                    yeuthich = 1;
                    /*SharedPreferences.Editor editor = sharedPreferencesyeuthich.edit();
                    editor.putBoolean("savepress", true); // value to store
                    editor.commit();*/
                } else if (yeuthich == 1) {
                    sharedPreferences = getSharedPreferences("dataLogin", Context.MODE_PRIVATE);
                    //lay gia tri unique_id
                    final int sno = sharedPreferences.getInt("SNO", 0);
                    RequestQueue requestQueue = Volley.newRequestQueue(ChiTietSanPham.this);
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.dddeleteyeuthich,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    if (response.equals("success")) {
                                        Toast.makeText(ChiTietSanPham.this, "Xoá thành công", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Toast.makeText(ChiTietSanPham.this, "Lỗi Xoá Yêu Thích: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                    ) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> params = new HashMap<>();
                            params.put("masanpham", String.valueOf(masp));
                            params.put("sno", String.valueOf(sno));
                            return params;
                        }
                    };
                    requestQueue.add(stringRequest);
                    yeuthich = 0;
                    /*SharedPreferences.Editor editor = sharedPreferencesyeuthich.edit();
                    editor.putBoolean("savepress", false); // value to store
                    editor.commit();*/
                }
            }
        });
    }

    private void CatchEvenSpinner() {
        Integer[] soluong = new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        ArrayAdapter<Integer> arrayAdapter = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_dropdown_item, soluong);
        //arrayAdapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
        spinner.setAdapter(arrayAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_giohang:
                Intent intentGiohang = new Intent(this, com.boyhotkey96.thietbicuahangonline.activity.GioHang.class);
                startActivity(intentGiohang);
                break;
            case R.id.menu_yeuthich:
                Intent intentYeuthich = new Intent(this, YeuThich.class);
                startActivity(intentYeuthich);
                break;
            case R.id.menu_profile:
                startActivity(new Intent(getApplicationContext(), Profile.class));
                finish();
                break;
            case R.id.menu_logout:
                startActivity(new Intent(this, Login.class));
                finish();
        }
        return super.onOptionsItemSelected(item);
    }
}

