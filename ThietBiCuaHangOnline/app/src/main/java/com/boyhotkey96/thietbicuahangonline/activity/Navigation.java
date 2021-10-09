package com.boyhotkey96.thietbicuahangonline.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.style.TextAppearanceSpan;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
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
import com.boyhotkey96.thietbicuahangonline.fragment.Fragment_Dienthoai;
import com.boyhotkey96.thietbicuahangonline.fragment.Fragment_GioiThieu;
import com.boyhotkey96.thietbicuahangonline.fragment.Fragment_Laptop;
import com.boyhotkey96.thietbicuahangonline.fragment.Fragment_PhuKien;
import com.boyhotkey96.thietbicuahangonline.fragment.Fragment_Tablet;
import com.boyhotkey96.thietbicuahangonline.fragment.Fragment_ThongTin;
import com.boyhotkey96.thietbicuahangonline.fragment.Fragment_Trangchu;
import com.boyhotkey96.thietbicuahangonline.model.GioHang;
import com.boyhotkey96.thietbicuahangonline.ultil.CheckConnectInternet;
import com.boyhotkey96.thietbicuahangonline.ultil.Constants;
import com.boyhotkey96.thietbicuahangonline.ultil.Server;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Navigation extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    //Gio hang
    public static ArrayList<GioHang> arraylistGiohang;
    // Nhan nut back
    boolean twice;
    private ImageView imgUser;
    private RelativeLayout layout;
    private String unique_id = "";
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private NavigationView navigationview;
    //set header
    private TextView tvUser, tvEmail;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        AnhXa();
        if (CheckConnectInternet.haveNetworkConnection(this)) {
            ActionBar();
            GetDataProfile();
            imgUser.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(Navigation.this, Profile.class));
                    overridePendingTransition(R.anim.user_open_in, R.anim.user_open_out);
                    finish();
                }
            });
        } else {
            CheckConnectInternet.ShowToast(this, "Bạn hãy kiểm tra lại kết nối Internet!");
            //finish();
        }
        SetInfo();
    }

    private void AnhXa() {
        layout = findViewById(R.id.layout);
        imgUser = findViewById(R.id.imgUser);
        tvUser = findViewById(R.id.tvUser);
        tvEmail = findViewById(R.id.tvEmail);
        drawerLayout = findViewById(R.id.drawerlayout);
        toolbar = findViewById(R.id.toolbar);
        navigationview = findViewById(R.id.navigationview);
        if (arraylistGiohang != null) {

        } else {
            arraylistGiohang = new ArrayList<>();
        }
    }

    private void ActionBar() {
        //getSupportActionBar().hide();
        setSupportActionBar(toolbar);
        //Hien nut Mui ten Navigation //Set icon open & close.
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //Để biểu tượng bánh hamburger hoạt động để cho biết ngăn kéo đang được mở và đóng, chúng ta cần sử dụng lớp ActionBarDrawerToggle .
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(Navigation.this, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close);
        //Chúng ta cần kết hợp DrawerLayout và Toolbar với nhau
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        //Set icon Naigation
        //toolbar.setNavigationIcon(android.R.drawable.ic_menu_sort_by_size);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
        navigationview.setNavigationItemSelectedListener(this);
        //Checked Item menu có i = 0.
        //navigationview.getMenu().getItem(0).setChecked(true);
        //Checked Trang chu.
        navigationview.setCheckedItem(R.id.item_trangchu);

        navigationview.getMenu().performIdentifierAction(R.id.item_trangchu, 0);
        navigationview.setSelected(false);
        Menu menu = navigationview.getMenu();

        MenuItem phukien = menu.findItem(R.id.item_phukien);
        SpannableString s = new SpannableString(phukien.getTitle());
        s.setSpan(new TextAppearanceSpan(this, R.style.TextPhuKien), 0, s.length(), 0);
        phukien.setTitle(s);
    }

    private void SetInfo() {
        sharedPreferences = getSharedPreferences("dataLogin", Context.MODE_PRIVATE);
        //lay gia tri unique_id
        unique_id = sharedPreferences.getString(Constants.UNIQUE_ID, "");
        Log.d("UNIQUE_ID", unique_id);
        //tvUser.setText(sharedPreferences.getString(Constants.NAME, ""));
        //tvEmail.setText(sharedPreferences.getString(Constants.EMAIL, ""));
    }

    private void GetDataProfile() {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.ddprofile,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("GETPROFILe", response);
                        if (response != null) {
                            try {
                                JSONArray jsonArray = new JSONArray(response);
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    String image = jsonObject.getString("image");
                                    String name = jsonObject.getString("name");
                                    String email = jsonObject.getString("email");
                                    tvUser.setText(name);
                                    tvEmail.setText(email);
                                    if (image.equals("")) {
                                        //imgUser.setImageResource(R.drawable.ic_profile);
                                    } else {
                                        Picasso.get().load(image)
                                                .error(R.drawable.ic_profile)
                                                .into(imgUser);
                                        final ImageView img = new ImageView(Navigation.this);
                                        Picasso.get().load(image).into(img, new Callback() {
                                            @Override
                                            public void onSuccess() {
                                                img.setColorFilter(Color.parseColor("#80000000"), PorterDuff.Mode.DARKEN);
                                                layout.setBackgroundDrawable(img.getDrawable());
                                            }

                                            @Override
                                            public void onError(Exception e) {

                                            }
                                        });
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
                        Toast.makeText(Navigation.this, "Lỗi Navigation: " + error.getMessage(), Toast.LENGTH_SHORT).show();
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

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_trangchu:
                toolbar.setTitle("MuaMua Shop");
                getSupportFragmentManager().beginTransaction().replace(R.id.content_layout, new Fragment_Trangchu()).commit();
                break;
            case R.id.item_dienthoai:
                toolbar.setTitle("Điện thoại");
                getSupportFragmentManager().beginTransaction().replace(R.id.content_layout, new Fragment_Dienthoai()).commit();
                break;
            case R.id.item_laptop:
                toolbar.setTitle("Laptop");
                getSupportFragmentManager().beginTransaction().replace(R.id.content_layout, new Fragment_Laptop()).commit();
                break;
            case R.id.item_maytinhbang:
                toolbar.setTitle("Máy tính bảng");
                getSupportFragmentManager().beginTransaction().replace(R.id.content_layout, new Fragment_Tablet()).commit();
                break;
            case R.id.item_phukien:
                toolbar.setTitle("Phụ kiện");
                getSupportFragmentManager().beginTransaction().replace(R.id.content_layout, new Fragment_PhuKien()).commit();
                break;
            case R.id.item_gioithieu:
                toolbar.setTitle("Giới thiệu");
                getSupportFragmentManager().beginTransaction().replace(R.id.content_layout, new Fragment_GioiThieu()).commit();
                break;
            case R.id.item_thongtin:
                toolbar.setTitle("Thông tin");
                getSupportFragmentManager().beginTransaction().replace(R.id.content_layout, new Fragment_ThongTin()).commit();
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);

        return true;
    }

    @Override
    public void onPostCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onPostCreate(savedInstanceState, persistentState);
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

    @Override
    public void onBackPressed() {
        if (twice == false) {
            Toast.makeText(this, "Ấn back 2 lần để thoát!", Toast.LENGTH_SHORT).show();
        }
        // Thoat app khi double click nut back.
        Log.d(this.getClass().getName(), "Click: ");
        if (twice == true) {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            //overridePendingTransition(R.anim.end_in_activity, R.anim.end_out_activity);
        }

        //super.onBackPressed();
        // Click tra ve true (double click chay if o tren) sau 3s tra ve false
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                twice = false;
                Log.d(this.getClass().getName(), "TWICE: " + twice);
            }
        }, 3000);
        twice = true;
        Log.d(this.getClass().getName(), "TWICE: " + twice);
    }
}
