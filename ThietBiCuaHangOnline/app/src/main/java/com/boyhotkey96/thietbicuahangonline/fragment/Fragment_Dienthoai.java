package com.boyhotkey96.thietbicuahangonline.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
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
import com.boyhotkey96.thietbicuahangonline.adapter.DienThoaiAdapter;
import com.boyhotkey96.thietbicuahangonline.model.SanPham;
import com.boyhotkey96.thietbicuahangonline.ultil.CheckConnectInternet;
import com.boyhotkey96.thietbicuahangonline.ultil.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Fragment_Dienthoai extends Fragment {

    private ArrayList<SanPham> arrayListDienThoai;
    private DienThoaiAdapter dienThoaiAdapter;
    private ListView lvDienThoai;
    private View view;
    private int maloaidienthoai = 1;
    private int page = 1;
    //Loadmore
    private View footerLoad;
    private boolean isLoading = false;
    private boolean limitData = false;
    private HandlerLoad handlerLoad;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_dienthoai_layout, container, false);
        if (CheckConnectInternet.haveNetworkConnection(getContext())) {
            AnhXa();
            GetDataDienThoai(page);
            LoadMoreData();
        } else {
            CheckConnectInternet.ShowToast(getContext(), "B???n hay ki???m tra l???i k???t n???i Internet!");
        }

        return view;
    }

    private void AnhXa() {
        //DienThoai
        lvDienThoai = view.findViewById(R.id.lvDienthoai);
        arrayListDienThoai = new ArrayList<>();
        dienThoaiAdapter = new DienThoaiAdapter(getContext(), arrayListDienThoai);
        lvDienThoai.setAdapter(dienThoaiAdapter);
        //LodeMore
        handlerLoad = new HandlerLoad();
        footerLoad = LayoutInflater.from(getContext()).inflate(R.layout.progressbar_load, null);
    }

    private void GetDataDienThoai(int Page) {
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        String ddDienThoai = Server.dddienthoai + String.valueOf(Page);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ddDienThoai,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("DIENTHOAI", response);
                        if (response != null && response.length() != 2) {
                            //remove khi co du lieu
                            lvDienThoai.removeFooterView(footerLoad);
                            try {
                                JSONArray jsonArray = new JSONArray(response);
                                Log.d("PPP", response);
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    int masp = jsonObject.getInt("masanpham");
                                    String tensp = jsonObject.getString("tensanpham");
                                    int giasp = jsonObject.getInt("giasanpham");
                                    String hinhsp = jsonObject.getString("hinhanhsanpham");
                                    String motasp = jsonObject.getString("motasanpham");
                                    int maloaisanpham = jsonObject.getInt("maloaisanpham");

                                    arrayListDienThoai.add(new SanPham(masp, tensp, giasp, hinhsp, motasp, maloaisanpham));
                                    dienThoaiAdapter.notifyDataSetChanged();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            limitData = true;
                            lvDienThoai.removeFooterView(footerLoad);
                            CheckConnectInternet.ShowToast(getContext(), "???? h???t d??? li???u");
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), "L???i: " + error.getMessage().toString(), Toast.LENGTH_SHORT).show();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> param = new HashMap<String, String>();
                param.put("maloaisanpham", String.valueOf(maloaidienthoai));
                return param;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void LoadMoreData() {
        lvDienThoai.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getContext(), ChiTietSanPham.class);
                //Truyen object du lieu qua man hinh khac thi model phai implements Serializable ben class model.
                intent.putExtra("thongtinsanpham", arrayListDienThoai.get(i));
                startActivity(intent);
            }
        });

        lvDienThoai.setOnScrollListener(new AbsListView.OnScrollListener() {
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
                    lvDienThoai.addFooterView(footerLoad);
                    break;
                case 1:
                    //page++;
                    GetDataDienThoai(++page);
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
