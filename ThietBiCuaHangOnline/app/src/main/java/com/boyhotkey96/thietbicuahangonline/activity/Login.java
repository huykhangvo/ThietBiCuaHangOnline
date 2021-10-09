package com.boyhotkey96.thietbicuahangonline.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.boyhotkey96.thietbicuahangonline.Interface.RequestInterface;
import com.boyhotkey96.thietbicuahangonline.R;
import com.boyhotkey96.thietbicuahangonline.model.ServerRequest;
import com.boyhotkey96.thietbicuahangonline.model.ServerResponse;
import com.boyhotkey96.thietbicuahangonline.model.User;
import com.boyhotkey96.thietbicuahangonline.ultil.Constants;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Login extends AppCompatActivity implements View.OnClickListener {

    private EditText edtEmail, edtPassword;
    private CheckBox checkBox;
    private AppCompatButton btnDangnhap;
    private TextView tvDangky;
    private ProgressBar progressBar;
    private SharedPreferences sharedPreferences;

    private View snackbar;

    private String nhanemail, nhanpassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        AnhXa();

        //Vua Login vao no se set gia tri khi minh checked. Cai nay chay dau tien
        sharedPreferences = getApplication().getSharedPreferences("dataLogin", Context.MODE_PRIVATE);
        //lay gia tri
        edtEmail.setText(sharedPreferences.getString("taikhoan", ""));
        edtPassword.setText(sharedPreferences.getString("matkhau", ""));
        checkBox.setChecked(sharedPreferences.getBoolean("checked", false));
        //Khi minh Register thanh cong se nhan email password va set email password cho no. Cai nay chay thu 2
        if (getIntent().getExtras() != null) {
            nhanemail = getIntent().getStringExtra("emailRegister");
            nhanpassword = getIntent().getStringExtra("passwordRegister");
            edtEmail.setText(nhanemail);
            edtPassword.setText(nhanpassword);
        }
    }

    private void AnhXa() {
        snackbar = findViewById(R.id.snackbar);
        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);
        checkBox = findViewById(R.id.checkbox);
        btnDangnhap = findViewById(R.id.btnDangnhap);
        tvDangky = findViewById(R.id.tvDangky);
        progressBar = findViewById(R.id.progress);
        btnDangnhap.setOnClickListener(this);
        tvDangky.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnDangnhap:
                String email = edtEmail.getText().toString();
                String password = edtPassword.getText().toString();
                if (!email.isEmpty() && !password.isEmpty()) {
                    progressBar.setVisibility(View.VISIBLE);
                    loginProcess(email, password);
                    //neu co check
                    if (checkBox.isChecked()) {
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("taikhoan", email);
                        editor.putString("matkhau", password);
                        editor.putBoolean("checked", true);
                        editor.commit();
                    } else {
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.remove("taikhoan");
                        editor.remove("matkhau");
                        editor.remove("checked");
                        editor.commit();
                    }
                } else {
                    Snackbar.make(snackbar, "Không được để trống", Snackbar.LENGTH_LONG).show();
                }
                break;
            case R.id.tvDangky:
                //Snackbar.make(getView(), "Hello", Snackbar.LENGTH_LONG).show();
                goToRegister();
                break;
        }
    }

    private void loginProcess(String email, String password) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RequestInterface requestInterface = retrofit.create(RequestInterface.class);

        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        ServerRequest request = new ServerRequest();
        request.setOperation("login");
        request.setUser(user);

        Call<ServerResponse> response = requestInterface.operation(request);
        response.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                Log.d("LOGIN RESULT", response.body().getResult());
                Log.d("LOGIN MESSAGE", response.body().getMessage());
                ServerResponse resp = response.body();
                Snackbar.make(snackbar, resp.getMessage(), Snackbar.LENGTH_LONG).show();
                if (resp.getResult().equals("success")) {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean(Constants.IS_LOGGED_IN, true);
                    editor.putInt("SNO", resp.getUser().getSno());
                    editor.putString(Constants.EMAIL, resp.getUser().getEmail());
                    editor.putString(Constants.NAME, resp.getUser().getName());
                    editor.putString(Constants.UNIQUE_ID, resp.getUser().getUnique_id());
                    editor.commit();
                    goToMain();
                } else {
                    progressBar.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
                progressBar.setVisibility(View.INVISIBLE);
                Log.d(Constants.TAG, "failed");
                Snackbar.make(snackbar, t.getMessage(), Snackbar.LENGTH_LONG).show();
            }
        });
    }

    private void goToRegister() {
        startActivity(new Intent(this, Register.class));
        overridePendingTransition(R.anim.user_open_in, R.anim.user_open_out);
    }

    private void goToMain() {
        startActivity(new Intent(this, Navigation.class));
        overridePendingTransition(R.anim.splash_in, R.anim.splash_out);
        finish();
    }
}