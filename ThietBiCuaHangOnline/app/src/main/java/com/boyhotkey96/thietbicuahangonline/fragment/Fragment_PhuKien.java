package com.boyhotkey96.thietbicuahangonline.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
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
import com.boyhotkey96.thietbicuahangonline.activity.ChiTietSanPham;
import com.boyhotkey96.thietbicuahangonline.adapter.PhuKienAdapter;
import com.boyhotkey96.thietbicuahangonline.model.SanPham;
import com.boyhotkey96.thietbicuahangonline.ultil.CheckConnectInternet;
import com.boyhotkey96.thietbicuahangonline.ultil.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Fragment_PhuKien extends Fragment {

    private ArrayList<SanPham> arrayListPhukien;
    private PhuKienAdapter phuKienAdapter;
    private ListView lvPhukien;
    private View view;
    private int maloaiphukien = 4;
    private int page = 1;
    //Loadmore
    private View footerLoad;
    private boolean isLoading = false;
    private boolean limitData = false;
    private HandlerLoad handlerLoad;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_phu_kien_layout, container, false);
        if (CheckConnectInternet.haveNetworkConnection(getContext())) {
            AnhXa();
            GetDataPhuKien(page);
            LoadMoreData();
        } else {
            CheckConnectInternet.ShowToast(getContext(), "Bạn hay kiểm tra lại kết nối Internet!");
        }

        return view;
    }

    private void AnhXa() {
        //DienThoai
        lvPhukien = view.findViewById(R.id.lvPhukien);
        arrayListPhukien = new ArrayList<>();
        phuKienAdapter = new PhuKienAdapter(getContext(), arrayListPhukien);
        lvPhukien.setAdapter(phuKienAdapter);
        //LodeMore
        handlerLoad = new HandlerLoad();
        footerLoad = LayoutInflater.from(getContext()).inflate(R.layout.progressbar_load, null);
    }

    private void GetDataPhuKien(int Page) {
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        String ddDienThoai = Server.dddienthoai + String.valueOf(Page);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ddDienThoai,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response != null && response.length() != 2) {
                            //remove khi co du lieu
                            lvPhukien.removeFooterView(footerLoad);
                            try {
                                JSONArray jsonArray = new JSONArray(response);
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    int masp = jsonObject.getInt("masanpham");
                                    String tensp = jsonObject.getString("tensanpham");
                                    int giasp = jsonObject.getInt("giasanpham");
                                    String hinhsp = jsonObject.getString("hinhanhsanpham");
                                    String motasp = jsonObject.getString("motasanpham");
                                    int maloaisanpham = jsonObject.getInt("maloaisanpham");

                                    arrayListPhukien.add(new SanPham(masp, tensp, giasp, hinhsp, motasp, maloaisanpham));
                                    phuKienAdapter.notifyDataSetChanged();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            limitData = true;
                            lvPhukien.removeFooterView(footerLoad);
                            CheckConnectInternet.ShowToast(getContext(), "Đã hết dữ liệu");
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), "Lỗi: " + error.getMessage().toString(), Toast.LENGTH_SHORT).show();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> param = new HashMap<String, String>();
                param.put("maloaisanpham", String.valueOf(maloaiphukien));
                return param;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void LoadMoreData() {
        lvPhukien.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getContext(), ChiTietSanPham.class);
                //Truyen object du lieu qua mang hinh khac thi model phai implements Serializable ben class model.
                intent.putExtra("thongtinsanpham", arrayListPhukien.get(i));
                startActivity(intent);
            }
        });
        lvPhukien.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {

            }
            @Override
            public void onScroll(AbsListView absListView, int firstItem, int visibleItem, int totalItem) {
                if (firstItem + visibleItem == totalItem && totalItem != 0 && isLoading == false && limitData == false) {
                    isLoading = true;
                    ThreadData threadData = new ThreadData();
                    threadData.start();
                }
            }
        });
    }

    public class HandlerLoad extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    lvPhukien.addFooterView(footerLoad);
                    break;
                case 1:
                    //page++;
                    GetDataPhuKien(++page);
                    isLoading = false;
                    break;
            }
            super.handleMessage(msg);
        }
    }

    public class ThreadData extends Thread {
        @Override
        public void run() {
            handlerLoad.sendEmptyMessage(0);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //Phuoc thuc obtainMessage() lien ket Thread voi Hanler.
            Message message = handlerLoad.obtainMessage(1);
            handlerLoad.sendMessage(message);
            super.run();
        }
    }
}