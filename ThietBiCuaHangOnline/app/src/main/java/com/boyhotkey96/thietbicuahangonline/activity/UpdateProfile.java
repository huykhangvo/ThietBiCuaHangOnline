package com.boyhotkey96.thietbicuahangonline.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.boyhotkey96.thietbicuahangonline.R;
import com.boyhotkey96.thietbicuahangonline.model.Profile;
import com.boyhotkey96.thietbicuahangonline.retrofit2.DataClient;
import com.boyhotkey96.thietbicuahangonline.ultil.Constants;
import com.boyhotkey96.thietbicuahangonline.ultil.Server;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;

public class UpdateProfile extends AppCompatActivity {

    // Storage Permissions
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    private ImageView imgAvatar;
    private EditText edtName, edtNgaysinh, edtSodienthoai, edtDiachi;
    private Button btnXacnhan, btnTrove;
    private String name, ngaysinh, sodienthoai, diachi;
    private int REQUEST_CODE_IMG = 1;
    private String unique_id = "";
    private String realpath = "";
    private String realpath2;
    private Bitmap bitmap;
    private String image;

    //Xin Permissions
    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);
        AnhXa();

        //Lay unique_id cua user
        Intent intent = getIntent();
        Profile profile = (Profile) intent.getSerializableExtra("dataProfile");
        unique_id = profile.getUnique_id();

        //Lay du lieu tu server len de settext
        GetDataProfile();
        //Click ImageView
        imgAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Xin quyen API >= 23
                verifyStoragePermissions(UpdateProfile.this);
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_PICK);
                startActivityForResult(intent, REQUEST_CODE_IMG);
            }
        });
        //Click Xac nhac
        btnXacnhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name = edtName.getText().toString().trim();
                ngaysinh = edtNgaysinh.getText().toString().trim();
                sodienthoai = edtSodienthoai.getText().toString().trim();
                diachi = edtDiachi.getText().toString().trim();
                if (name.matches("") || ngaysinh.length() == 0 || sodienthoai.equals("") || diachi.equals("")) {
                    Toast.makeText(UpdateProfile.this, "Vui lòng nhập đủ thông tin!", Toast.LENGTH_SHORT).show();
                } else {
                    if (realpath2 == realpath) {
                        CapNhatProfileAvatar();
                    } else {
                        CapNhatProfile();
                    }
                }
            }
        });

        btnTrove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UpdateProfile.this, com.boyhotkey96.thietbicuahangonline.activity.Profile.class));
                overridePendingTransition(R.anim.user_close_in, R.anim.user_close_out);
                finish();
            }
        });
    }

    private void AnhXa() {
        imgAvatar = findViewById(R.id.imgAvatar);
        edtName = findViewById(R.id.edtName);
        edtNgaysinh = findViewById(R.id.edtNgaysinh);
        edtSodienthoai = findViewById(R.id.edtSodienthoai);
        edtDiachi = findViewById(R.id.edtDiachi);
        btnXacnhan = findViewById(R.id.btnXacnhan);
        btnTrove = findViewById(R.id.btnTrove);
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
                                    //String unique_id = jsonObject.getString("unique_id");
                                    String name = jsonObject.getString("name");
                                    //String email = jsonObject.getString("email");
                                    //String ngaytao = jsonObject.getString("created_at");
                                    image = jsonObject.getString("image");
                                    String ngaysinh = jsonObject.getString("ngaysinh");
                                    String sodienthoai = jsonObject.getString("sodienthoai");
                                    String diachi = jsonObject.getString("diachi");
                                    if (image.equals("")) {
                                        imgAvatar.setImageResource(R.drawable.ic_noimage);
                                    } else {
                                        Picasso.get().load(image)
                                                .error(R.drawable.ic_error)
                                                .into(imgAvatar);
                                    }
                                    edtName.setText(name);
                                    edtNgaysinh.setText(ngaysinh);
                                    edtSodienthoai.setText(sodienthoai);
                                    edtDiachi.setText(diachi);
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
                        Toast.makeText(UpdateProfile.this, "Lỗi GetDataProfile: " + error.getMessage(), Toast.LENGTH_SHORT).show();
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

    private void CapNhatProfile() {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.ddupdateprofile,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("UPDATE", response);
                        if (response.trim().equals("success")) {
                            Toast.makeText(UpdateProfile.this, "Đã cập nhật thành công", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(UpdateProfile.this, com.boyhotkey96.thietbicuahangonline.activity.Profile.class));
                            overridePendingTransition(R.anim.user_close_in, R.anim.user_close_out);
                            finish();
                        } else {
                            Toast.makeText(UpdateProfile.this, "Cập nhật lỗi!", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(UpdateProfile.this, "Lỗi: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<String, String>();
                params.put("unique_id", unique_id);
                params.put("name", edtName.getText().toString().trim());
                params.put("ngaysinh", edtNgaysinh.getText().toString().trim());
                params.put("sodienthoai", edtSodienthoai.getText().toString().trim());
                params.put("diachi", edtDiachi.getText().toString().trim());
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void CapNhatProfileAvatar() {
        File file = new File(realpath);
        String file_path = file.getAbsolutePath();
        Log.d("BBB", file_path);
        String[] mangtenfile = file_path.split("\\.");
        file_path = mangtenfile[0] + System.currentTimeMillis() + "." + mangtenfile[1];
        Log.d("CCC", file_path);

        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);

        MultipartBody.Part body = MultipartBody.Part.createFormData("uploaded_file", file_path, requestBody);

        DataClient dataClient = Constants.getData();
        Call<String> callback = dataClient.UploadPhoto(body);
        callback.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, retrofit2.Response<String> response) {
                Log.d("KKK2", response.toString());
                if (response != null) {
                    String message = response.body();
                    Log.d("KKK", message);
                    if (message.length() > 0) {
                        DataClient insertdata = Constants.getData();
                        Call<String> callback = insertdata.InsertData(unique_id, Constants.Base_Url + "image/" + message, name, ngaysinh, sodienthoai, diachi);
                        callback.enqueue(new Callback<String>() {
                            @Override
                            public void onResponse(Call<String> call, retrofit2.Response<String> response) {
                                String result = response.body();
                                Log.d("PPP", result);
                                if (result.equals("success")) {
                                    Toast.makeText(UpdateProfile.this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(UpdateProfile.this, com.boyhotkey96.thietbicuahangonline.activity.Profile.class));
                                    overridePendingTransition(R.anim.user_close_in, R.anim.user_close_out);
                                    finish();
                                } else {
                                    Toast.makeText(UpdateProfile.this, "Lỗi update", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<String> call, Throwable t) {
                                Toast.makeText(UpdateProfile.this, "Lỗi null: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.d("LOI", t.getMessage());
                Toast.makeText(UpdateProfile.this, "Lỗi gi z: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_IMG && resultCode == RESULT_OK && data != null) {
            Uri uri = data.getData();
            realpath = getRealPathFromURI(uri);
            realpath2 = realpath;
            //Picasso.get().load(path).into(image);
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                imgAvatar.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    public String getRealPathFromURI(Uri contentUri) {
        String path = null;
        String[] proj = {MediaStore.MediaColumns.DATA};
        Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
        if (cursor.moveToFirst()) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
            path = cursor.getString(column_index);
        }
        cursor.close();
        return path;
    }
}
