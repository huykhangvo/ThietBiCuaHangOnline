package com.boyhotkey96.thietbicuahangonline.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.boyhotkey96.thietbicuahangonline.R;
import com.boyhotkey96.thietbicuahangonline.activity.Navigation;
import com.boyhotkey96.thietbicuahangonline.model.GioHang;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class GioHangAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<GioHang> arrayListGioHang;

    public GioHangAdapter(Context context, ArrayList<GioHang> arrayListGioHang) {
        this.context = context;
        this.arrayListGioHang = arrayListGioHang;
    }

    @Override
    public int getCount() {
        return arrayListGioHang.size();
    }

    @Override
    public Object getItem(int i) {
        return arrayListGioHang.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        final ViewHolder viewHolder;
        if (view == null) {
            viewHolder = new ViewHolder();
            view = LayoutInflater.from(context).inflate(R.layout.row_giohang, null);
            viewHolder.txtTengiohang = view.findViewById(R.id.tvTengiohang);
            viewHolder.txtGiamonhang = view.findViewById(R.id.tvGiamonhang);
            viewHolder.imgGiohang = view.findViewById(R.id.imgGiohang);
            viewHolder.btnTru = view.findViewById(R.id.btnTru);
            viewHolder.btnGiatri = view.findViewById(R.id.btnGiatri);
            viewHolder.btnCong = view.findViewById(R.id.btnCong);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        GioHang gioHang = (GioHang) getItem(i);
        viewHolder.txtTengiohang.setText(gioHang.getTensp());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        viewHolder.txtGiamonhang.setText(decimalFormat.format(gioHang.getGiasp()).replace(",", ".") + "đ");
        Picasso.get().load(gioHang.getHinhsp())
                .placeholder(R.drawable.ic_noimage)
                .error(R.drawable.ic_error)
                .into(viewHolder.imgGiohang);
        viewHolder.btnGiatri.setText(String.valueOf(gioHang.getSoluongsp()));
        //Xu ly Button Tang giam
        int sl = Integer.parseInt(viewHolder.btnGiatri.getText().toString());
        if (sl >= 10) {
            viewHolder.btnCong.setVisibility(View.INVISIBLE);
            viewHolder.btnTru.setVisibility(View.VISIBLE);
        } else if (sl <= 1) {
            viewHolder.btnTru.setVisibility(View.INVISIBLE);
        } else if (sl >= 1) {
            viewHolder.btnTru.setVisibility(View.VISIBLE);
            viewHolder.btnCong.setVisibility(View.VISIBLE);
        }
        viewHolder.btnCong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int slmoinhat = Integer.parseInt(viewHolder.btnGiatri.getText().toString()) + 1;
                int slhientai = Navigation.arraylistGiohang.get(i).getSoluongsp();
                long giahientai = Navigation.arraylistGiohang.get(i).getGiasp();
                Navigation.arraylistGiohang.get(i).setSoluongsp(slmoinhat);
                long giamoinhat = (giahientai * slmoinhat) / slhientai;
                Navigation.arraylistGiohang.get(i).setGiasp(giamoinhat);
                DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
                viewHolder.txtGiamonhang.setText(decimalFormat.format(giamoinhat).replace(",", ".") + "đ");
                //set Tong tien
                com.boyhotkey96.thietbicuahangonline.activity.GioHang.EvenUltil();;
                if (slmoinhat > 9) {
                    viewHolder.btnCong.setVisibility(View.INVISIBLE);
                    viewHolder.btnTru.setVisibility(View.VISIBLE);
                    viewHolder.btnGiatri.setText(String.valueOf(slmoinhat));
                } else {
                    viewHolder.btnCong.setVisibility(View.VISIBLE);
                    viewHolder.btnTru.setVisibility(View.VISIBLE);
                    viewHolder.btnGiatri.setText(String.valueOf(slmoinhat));
                }
            }
        });
        viewHolder.btnTru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int slmoinhat = Integer.parseInt(viewHolder.btnGiatri.getText().toString()) - 1;
                int slhientai = Navigation.arraylistGiohang.get(i).getSoluongsp();
                long giahientai = Navigation.arraylistGiohang.get(i).getGiasp();
                Navigation.arraylistGiohang.get(i).setSoluongsp(slmoinhat);
                long giamoinhat = (giahientai * slmoinhat) / slhientai;
                Navigation.arraylistGiohang.get(i).setGiasp(giamoinhat);
                DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
                viewHolder.txtGiamonhang.setText(decimalFormat.format(giamoinhat).replace(",", ".") + "đ");
                //set Tong tien
                com.boyhotkey96.thietbicuahangonline.activity.GioHang.EvenUltil();;
                if (slmoinhat < 2) {
                    viewHolder.btnTru.setVisibility(View.INVISIBLE);
                    viewHolder.btnCong.setVisibility(View.VISIBLE);
                    viewHolder.btnGiatri.setText(String.valueOf(slmoinhat));
                } else {
                    viewHolder.btnCong.setVisibility(View.VISIBLE);
                    viewHolder.btnTru.setVisibility(View.VISIBLE);
                    viewHolder.btnGiatri.setText(String.valueOf(slmoinhat));
                }
            }
        });

        return view;
    }

    public class ViewHolder {
        private TextView txtTengiohang, txtGiamonhang;
        private ImageView imgGiohang;
        private Button btnTru, btnGiatri, btnCong;
    }
}
