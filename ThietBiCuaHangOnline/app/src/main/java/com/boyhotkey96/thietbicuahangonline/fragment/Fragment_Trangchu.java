package com.boyhotkey96.thietbicuahangonline.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.boyhotkey96.thietbicuahangonline.R;
import com.boyhotkey96.thietbicuahangonline.adapter.SanPhamMoiNhatAdapter;
import com.boyhotkey96.thietbicuahangonline.model.SanPham;
import com.boyhotkey96.thietbicuahangonline.ultil.CheckConnectInternet;
import com.boyhotkey96.thietbicuahangonline.ultil.Server;
import com.smarteist.autoimageslider.SliderLayout;
import com.smarteist.autoimageslider.SliderView;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;

public class Fragment_Trangchu extends Fragment {

    int index, top;
    private ViewFlipper viewFlipper;
    private SliderLayout sliderLayout;
    private RecyclerView recyclerview;
    private View view;
    //San pham moi
    private ArrayList<SanPham> arrayListSanPham;
    private SanPhamMoiNhatAdapter sanPhamMoiNhatAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_trangchu_layout, container, false);

        if (CheckConnectInternet.haveNetworkConnection(getContext())) {
            AnhXa();
            ActionSlider();
            GetDuLieuSanPhamMoiNhat();
        } else {
            CheckConnectInternet.ShowToast(getContext(), "Bạn hãy kiểm tra lại kết nối Interner!");
        }

        return view;
    }

    private void AnhXa() {
        sliderLayout = view.findViewById(R.id.sliderLayout);
        recyclerview = view.findViewById(R.id.recyclerview);
        //San pham moi
        arrayListSanPham = new ArrayList<>();
        sanPhamMoiNhatAdapter = new SanPhamMoiNhatAdapter(getContext(), arrayListSanPham);
        recyclerview.setHasFixedSize(true);
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 2);

        //recyclerview.setNestedScrollingEnabled(false);

        recyclerview.setLayoutManager(layoutManager);
        recyclerview.setAdapter(sanPhamMoiNhatAdapter);
    }

    //no using
    private void ActionViewfipper() {
        //String[] ads = new String[]{"https://cdn.tgdd.vn/qcao/31_08_2018_16_26_56_Nokia61PLus_800-300.png"};
        ArrayList<String> mangquangcao = new ArrayList<>();
        /*mangquangcao.add("https://cdn.tgdd.vn/qcao/22_09_2018_18_13_35_j4-j6-800-300.png");
        mangquangcao.add("https://cdn.tgdd.vn/qcao/31_08_2018_16_26_56_Nokia61PLus_800-300.png");
        mangquangcao.add("https://cdn.tgdd.vn/qcao/10_09_2018_11_27_47_Black-to-school-800-300.png");
        mangquangcao.add("https://cdn.tgdd.vn/qcao/05_09_2018_11_05_39_OPPO-F9-6GB-800-300.png");*/
        Collections.addAll(mangquangcao,
                "https://cdn.tgdd.vn/qcao/22_09_2018_18_13_35_j4-j6-800-300.png",
                "https://cdn.tgdd.vn/qcao/31_08_2018_16_26_56_Nokia61PLus_800-300.png",
                "https://cdn.tgdd.vn/qcao/10_09_2018_11_27_47_Black-to-school-800-300.png",
                "https://cdn.tgdd.vn/qcao/05_09_2018_11_05_39_OPPO-F9-6GB-800-300.png");
        // Enumeration<String> e = Collections.enumeration(mangquangcao);
        for (int i = 0; i < mangquangcao.size(); i++) {
            ImageView imageView = new ImageView(getContext());
            Picasso.get().load(mangquangcao.get(i)).into(imageView);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            viewFlipper.addView(imageView);
        }
        viewFlipper.setFlipInterval(5000);
        viewFlipper.setAutoStart(true);
        //Create effect for ads of viewflipper
        Animation animation_slide_in = AnimationUtils.loadAnimation(getContext(), R.anim.slide_in_right);
        Animation animation_slide_out = AnimationUtils.loadAnimation(getContext(), R.anim.slide_out_right);
        viewFlipper.setInAnimation(animation_slide_in);
        viewFlipper.setOutAnimation(animation_slide_out);
    }

    private void ActionSlider() {
        String[] url = {
                "https://cdn.tgdd.vn/qcao/22_09_2018_18_13_35_j4-j6-800-300.png",
                "https://cdn.tgdd.vn/qcao/31_08_2018_16_26_56_Nokia61PLus_800-300.png",
                "https://cdn.tgdd.vn/qcao/10_09_2018_11_27_47_Black-to-school-800-300.png",
                "https://cdn.tgdd.vn/qcao/05_09_2018_11_05_39_OPPO-F9-6GB-800-300.png"
        };
        for (int i = 0; i < 4; i++) {
            SliderView sliderView = new SliderView(getContext());
            sliderView.setImageUrl(url[i])
                    .setImageScaleType(ImageView.ScaleType.FIT_XY);
                    //.setDescription("setDescription " + (i + 1));
            final int finalI = i;
            sliderView.setOnSliderClickListener(new SliderView.OnSliderClickListener() {
                @Override
                public void onSliderClick(SliderView sliderView) {
                    //Toast.makeText(getContext(), "This is slider " + (finalI + 1), Toast.LENGTH_SHORT).show();
                    //Toast.makeText(getContext(), "This is slider " + (sliderLayout.getCurrentPagePosition() + 1), Toast.LENGTH_SHORT).show();
                }
            });
            sliderLayout.setIndicatorAnimation(SliderLayout.Animations.NONE); //set indicator animation by using SliderLayout.Animations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
            sliderLayout.setScrollTimeInSec(2); //set scroll delay in seconds :
            sliderLayout.addSliderView(sliderView);
        }
    }

    private void GetDuLieuSanPhamMoiNhat() {
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Server.ddsanphammoinhat,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d("TRANGCHU", response.toString());
                        if (response != null) {
                            for (int i = 0; i < response.length(); i++) {
                                try {
                                    JSONObject jsonObject = response.getJSONObject(i);
                                    int masp = jsonObject.getInt("masanpham");
                                    String tensp = jsonObject.getString("tensanpham");
                                    int giasp = jsonObject.getInt("giasanpham");
                                    String hinhsp = jsonObject.getString("hinhanhsanpham");
                                    String motasp = jsonObject.getString("motasanpham");
                                    int maloaisp = jsonObject.getInt("maloaisanpham");
                                    Log.d("masp", String.valueOf(masp));

                                    arrayListSanPham.add(new SanPham(masp, tensp, giasp, hinhsp, motasp, maloaisp));
                                    sanPhamMoiNhatAdapter.notifyDataSetChanged();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("CCC", error.getMessage());
                        Toast.makeText(getContext(), "Lỗi tc: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
        );
        requestQueue.add(jsonArrayRequest);
    }

}
