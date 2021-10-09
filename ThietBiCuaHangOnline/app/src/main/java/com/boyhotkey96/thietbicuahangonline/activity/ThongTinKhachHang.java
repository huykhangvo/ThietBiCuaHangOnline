package com.boyhotkey96.thietbicuahangonline.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.boyhotkey96.thietbicuahangonline.R;
import com.boyhotkey96.thietbicuahangonline.adapter.ProfileAdapter;
import com.boyhotkey96.thietbicuahangonline.ultil.CheckConnectInternet;
import com.boyhotkey96.thietbicuahangonline.ultil.Constants;
import com.boyhotkey96.thietbicuahangonline.ultil.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

public class ThongTinKhachHang extends AppCompatActivity {

    private EditText edtTenkh, edtDiachikh, edtSdtkh, edtEmailkh, edtYeucaukh;
    private Button btnXacnhan, btnTrove;
    private SharedPreferences sharedPreferences;
    //set TTKH
    private String unique_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_tin_khach_hang);
        AnhXa();
        GetDataProfile();
        if (CheckConnectInternet.haveNetworkConnection(ThongTinKhachHang.this)) {
            EvenButton();
        } else {
            CheckConnectInternet.ShowToast(ThongTinKhachHang.this, "Bạn hãy kiểm tra lại kết nối");
        }
    }

    private void AnhXa() {
        edtTenkh = findViewById(R.id.edtTenkh);
        edtDiachikh = findViewById(R.id.edtDiachikh);
        edtSdtkh = findViewById(R.id.edtSdtkh);
        edtEmailkh = findViewById(R.id.edtEmailkh);
        edtYeucaukh = findViewById(R.id.edtYeucaukh);
        btnXacnhan = findViewById(R.id.btnXacnhan);
        btnTrove = findViewById(R.id.btnTrove);
    }

    private void GetDataProfile() {
        sharedPreferences = getSharedPreferences("dataLogin", Context.MODE_PRIVATE);
        //lay gia tri unique_id
        unique_id = sharedPreferences.getString(Constants.UNIQUE_ID, "");
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.ddprofile,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response != null) {
                            try {
                                JSONArray jsonArray = new JSONArray(response);
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    String name = jsonObject.getString("name");
                                    String email = jsonObject.getString("email");
                                    String sodienthoai = jsonObject.getString("sodienthoai");
                                    String diachi = jsonObject.getString("diachi");

                                    edtTenkh.setText(name);
                                    edtDiachikh.setText(diachi);
                                    edtSdtkh.setText(sodienthoai);
                                    edtEmailkh.setText(email);
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
                        Toast.makeText(ThongTinKhachHang.this, "Lỗi: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<String, String>();
                params.put("iduser", unique_id);
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void EvenButton() {
        btnXacnhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sharedPreferences = getSharedPreferences("dataLogin", Context.MODE_PRIVATE);
                //lay gia tri unique_id
                final int sno = sharedPreferences.getInt("SNO", 0);
                Log.d("SNO", String.valueOf(sno));
                final String ten = edtTenkh.getText().toString().trim();
                final String diachi = edtDiachikh.getText().toString().trim();
                final String sdt = edtSdtkh.getText().toString().trim();
                final String email = edtEmailkh.getText().toString().trim();
                Calendar cal = Calendar.getInstance();
                Date currentLocalTime = cal.getTime();
                DateFormat date = new SimpleDateFormat("dd-MM-yyy HH:mm:ss");
                final String ngaydat = date.format(currentLocalTime);
                final String yeucau = edtYeucaukh.getText().toString().trim();
                if (!ten.isEmpty() && !diachi.isEmpty() && !sdt.isEmpty() && !email.isEmpty()) {
                    RequestQueue requestQueue = Volley.newRequestQueue(ThongTinKhachHang.this);
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.dddonhang,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(final String madonhang) {
                                    Log.d("madonhang", madonhang);
                                    if (Integer.parseInt(madonhang) > 0) {
                                        RequestQueue queue = Volley.newRequestQueue(ThongTinKhachHang.this);
                                        StringRequest request = new StringRequest(Request.Method.POST, Server.ddchitietdonhang,
                                                new Response.Listener<String>() {
                                                    @Override
                                                    public void onResponse(String response) {
                                                        Log.d("trave", response);
                                                        if (response.equals("success")) {
                                                            Navigation.arraylistGiohang.clear();
                                                            Toast.makeText(ThongTinKhachHang.this, "Bạn đã thêm dữ liệu giỏ hàng thành công", Toast.LENGTH_SHORT).show();
                                                            Intent intent = new Intent(ThongTinKhachHang.this, Navigation.class);
                                                            startActivity(intent);
                                                            Toast.makeText(ThongTinKhachHang.this, "Mời bạn tiếp tục mua hàng", Toast.LENGTH_SHORT).show();
                                                        } else {
                                                            Toast.makeText(ThongTinKhachHang.this, "Dữ liệu giỏ hàng của bạn đã bị lỗi", Toast.LENGTH_SHORT).show();
                                                        }
                                                    }
                                                },
                                                new Response.ErrorListener() {
                                                    @Override
                                                    public void onErrorResponse(VolleyError error) {
                                                        Toast.makeText(ThongTinKhachHang.this, "Lỗi: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                                                    }
                                                }
                                        ) {
                                            @Override
                                            protected Map<String, String> getParams() throws AuthFailureError {
                                                JSONArray jsonArray = new JSONArray();
                                                for (int i = 0; i < Navigation.arraylistGiohang.size(); i++) {
                                                    JSONObject jsonObject = new JSONObject();
                                                    try {
                                                        jsonObject.put("madonhang", madonhang);
                                                        jsonObject.put("masanpham", Navigation.arraylistGiohang.get(i).getIdsp());
                                                        jsonObject.put("tensanpham", Navigation.arraylistGiohang.get(i).getTensp());
                                                        jsonObject.put("giasanpham", Navigation.arraylistGiohang.get(i).getGiasp());
                                                        jsonObject.put("soluongsanpham", Navigation.arraylistGiohang.get(i).getSoluongsp());
                                                    } catch (JSONException e) {
                                                        e.printStackTrace();
                                                    }
                                                    jsonArray.put(jsonObject);
                                                }
                                                HashMap<String, String> hashMap = new HashMap<String, String>();
                                                hashMap.put("json", jsonArray.toString());

                                                return hashMap;
                                            }
                                        };
                                        queue.add(request);
                                    }
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Toast.makeText(ThongTinKhachHang.this, "Lỗi: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                    ) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            HashMap<String, String> hashMap = new HashMap<String, String>();
                            hashMap.put("sno", String.valueOf(sno));
                            hashMap.put("tenkhachhang", ten);
                            hashMap.put("diachi", diachi);
                            hashMap.put("sodienthoai", sdt);
                            hashMap.put("email", email);
                            hashMap.put("ngaydat", ngaydat);
                            hashMap.put("yeucau", yeucau);
                            return hashMap;
                        }
                    };
                    requestQueue.add(stringRequest);
                } else {
                    CheckConnectInternet.ShowToast(ThongTinKhachHang.this, "Hãy kiểm tra lại dữ liệu");
                }
            }
        });
        btnTrove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
