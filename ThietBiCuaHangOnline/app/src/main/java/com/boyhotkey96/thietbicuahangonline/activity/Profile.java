package com.boyhotkey96.thietbicuahangonline.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
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
import com.boyhotkey96.thietbicuahangonline.ultil.Constants;
import com.boyhotkey96.thietbicuahangonline.ultil.Server;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Profile extends AppCompatActivity {

    private Toolbar toolbar;
    private ListView lvProfile;
    private ArrayList<com.boyhotkey96.thietbicuahangonline.model.Profile> arrayListProfile;
    private ProfileAdapter profileAdapter;
    private SharedPreferences sharedPreferences;
    private String unique_id = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        AnhXa();
        ActionBar();

        sharedPreferences = getSharedPreferences("dataLogin", Context.MODE_PRIVATE);
        //lay gia tri unique_id
        unique_id = sharedPreferences.getString(Constants.UNIQUE_ID, "");
        //unique_id = sharedPreferences.getString("UNIQUE_ID", "");

        /*nhanemail = getIntent().getExtras().getString("guiemail");
        Log.d("NHANEMAIL", nhanemail);*/

        GetDataProfile();
    }

    private void AnhXa() {
        lvProfile = findViewById(R.id.lvProfile);
        toolbar = findViewById(R.id.toolbar);
        arrayListProfile = new ArrayList<>();
        profileAdapter = new ProfileAdapter(this, arrayListProfile);
        lvProfile.setAdapter(profileAdapter);
    }

    private void ActionBar() {
        setSupportActionBar(toolbar);
        toolbar.setTitle("Thông tin thành viên");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Profile.this, Navigation.class));
                overridePendingTransition(R.anim.user_close_in, R.anim.user_close_out);
                finish();
            }
        });
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
                                arrayListProfile.clear();
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    String unique_id = jsonObject.getString("unique_id");
                                    String name = jsonObject.getString("name");
                                    String email = jsonObject.getString("email");
                                    String ngaytao = jsonObject.getString("created_at");
                                    String image = jsonObject.getString("image");
                                    String ngaysinh = jsonObject.getString("ngaysinh");
                                    String sodienthoai = jsonObject.getString("sodienthoai");
                                    String diachi = jsonObject.getString("diachi");
                                    com.boyhotkey96.thietbicuahangonline.model.Profile profile = new com.boyhotkey96.thietbicuahangonline.model.Profile(
                                            unique_id, name, email, ngaytao, image, ngaysinh, sodienthoai, diachi
                                    );
                                    arrayListProfile.add(profile);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            profileAdapter.notifyDataSetChanged();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Profile.this, "Lỗi: " + error.getMessage(), Toast.LENGTH_SHORT).show();
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
}
