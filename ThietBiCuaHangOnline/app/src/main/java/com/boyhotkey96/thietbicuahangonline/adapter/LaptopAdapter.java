package com.boyhotkey96.thietbicuahangonline.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.boyhotkey96.thietbicuahangonline.R;
import com.boyhotkey96.thietbicuahangonline.model.SanPham;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class LaptopAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<SanPham> arrayListLaptop;

    public LaptopAdapter(Context context, ArrayList<SanPham> arrayListLaptop) {
        this.context = context;
        this.arrayListLaptop = arrayListLaptop;
    }

    @Override
    public int getCount() {
        return arrayListLaptop.size();
    }

    @Override
    public Object getItem(int i) {
        return arrayListLaptop.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;

        if (view == null) {
            viewHolder = new ViewHolder();
            view = LayoutInflater.from(context).inflate(R.layout.row_laptop, null);
            viewHolder.txtTenlaptop = view.findViewById(R.id.tvTenlaptop);
            viewHolder.txtGialaptop = view.findViewById(R.id.tvGialaptop);
            viewHolder.txtMotalaptop = view.findViewById(R.id.tvMotalaptop);
            viewHolder.imgLaptop = view.findViewById(R.id.imgLaptop);

            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        SanPham sanPham = (SanPham) getItem(i);
        viewHolder.txtTenlaptop.setText(sanPham.getTensanpham());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        viewHolder.txtGialaptop.setText(decimalFormat.format(sanPham.getGiasanpham()).replaceAll(",", "."));
        //Mo ta DienThoai toi da 2 dong
        viewHolder.txtMotalaptop.setMaxLines(2);
        //Hien dau 3 cham (...) khi max 2 line.
        viewHolder.txtMotalaptop.setEllipsize(TextUtils.TruncateAt.END);
        viewHolder.txtMotalaptop.setText(sanPham.getMotasanpham());
        Picasso.get().load(sanPham.getHinhanhsanpham())
                .placeholder(R.drawable.ic_noimage)
                .error(R.drawable.ic_error)
                .into(viewHolder.imgLaptop);

        return view;
    }

    public class ViewHolder {
        private TextView txtTenlaptop, txtGialaptop, txtMotalaptop;
        private ImageView imgLaptop;
    }
}
