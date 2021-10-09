package com.boyhotkey96.thietbicuahangonline.activity;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.boyhotkey96.thietbicuahangonline.Interface.RequestInterface;
import com.boyhotkey96.thietbicuahangonline.R;
import com.boyhotkey96.thietbicuahangonline.model.ServerRequest;
import com.boyhotkey96.thietbicuahangonline.model.ServerResponse;
import com.boyhotkey96.thietbicuahangonline.model.User;
import com.boyhotkey96.thietbicuahangonline.ultil.Constants;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Register extends AppCompatActivity implements View.OnClickListener {

    private EditText edtName, edtEmail, edtPassword, edtPassword2;
    private AppCompatButton btnDangky;
    private TextView tvDangnhap;
    private ProgressBar progress;
    private View snackbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        AnhXa();
    }

    private void AnhXa() {
        snackbar = findViewById(R.id.snackbar);
        btnDangky = findViewById(R.id.btnDangky);
        edtName = findViewById(R.id.edtName);
        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);
        edtPassword2 = findViewById(R.id.edtPassword2);
        tvDangnhap = findViewById(R.id.tvDangnhap);
        edtPassword2 = findViewById(R.id.edtPassword2);
        progress = findViewById(R.id.progress);
        btnDangky.setOnClickListener(this);
        tvDangnhap.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tvDangnhap:
                tvgotoLogin();
                break;
            case R.id.btnDangky:
                String name = edtName.getText().toString();
                String email = edtEmail.getText().toString();
                String password = edtPassword.getText().toString();
                String password2 = edtPassword2.getText().toString();
                if (!name.isEmpty() && !email.isEmpty() && !password.isEmpty() && !password2.isEmpty()) {
                    if (password.equals(password2)) {
                        progress.setVisibility(View.VISIBLE);
                        registerProcess(name, email, password);
                    } else {
                        Toast.makeText(this, "Mật khẩu không khớp", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Snackbar.make(snackbar, "Không được để trống", Snackbar.LENGTH_LONG).show();
                }
                break;
        }
    }

    private void registerProcess(String name, String email, String password) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RequestInterface requestInterface = retrofit.create(RequestInterface.class);

        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(password);

        ServerRequest request = new ServerRequest();
        request.setOperation("register");
        request.setUser(user);

        Call<ServerResponse> response = requestInterface.operation(request);
        response.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, retrofit2.Response<ServerResponse> response) {
                Log.d("REGISTER RESULT", response.body().getResult());
                Log.d("REGISTER MESSAGE", response.body().getMessage());
                ServerResponse resp = response.body();
                //Snackbar.make(snackbar, resp.getMessage(), Snackbar.LENGTH_LONG).show();
                //progress.setVisibility(View.INVISIBLE);
                if (resp.getResult().equals("success")) {
                    Toast.makeText(Register.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                    progress.setVisibility(View.INVISIBLE);
                    btngotoLogin();
                } else {
                    Snackbar.make(snackbar, resp.getMessage(), Snackbar.LENGTH_LONG).show();
                    progress.setVisibility(View.INVISIBLE);
                }
            }
            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
                progress.setVisibility(View.INVISIBLE);
                Log.d(Constants.TAG, "failed");
                Snackbar.make(snackbar, t.getLocalizedMessage(), Snackbar.LENGTH_LONG).show();
            }
        });
    }

    private void tvgotoLogin() {
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
        overridePendingTransition(R.anim.user_close_in, R.anim.user_close_out);
        finish();
    }

    private void btngotoLogin() {
        Intent intent = new Intent(this, Login.class);
        intent.putExtra("emailRegister", edtEmail.getText().toString());
        intent.putExtra("passwordRegister", edtPassword.getText().toString());
        startActivity(intent);
        overridePendingTransition(R.anim.user_close_in, R.anim.user_close_out);
        finish();
    }
}