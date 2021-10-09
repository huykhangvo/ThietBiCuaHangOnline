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

public class YeuThichAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<SanPham> arrayListYeuthich;

    public YeuThichAdapter(Context context, ArrayList<SanPham> arrayListYeuthich) {
        this.context = context;
        this.arrayListYeuthich = arrayListYeuthich;
    }

    @Override
    public int getCount() {
        return arrayListYeuthich.size();
    }

    @Override
    public Object getItem(int i) {
        return arrayListYeuthich.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (view == null) {
            viewHolder = new ViewHolder();
            view = LayoutInflater.from(context).inflate(R.layout.row_yeuthich, null);
            viewHolder.imgSanphamyeuthich = view.findViewById(R.id.imgSanphamyeuthich);
            viewHolder.tvTensanphamyeuthich = view.findViewById(R.id.tvTensanphamyeuthich);
            viewHolder.tvGiasanphamyeuthich = view.findViewById(R.id.tvGiasanphamyeuthich);
            viewHolder.tvMotasanphamyeuthich = view.findViewById(R.id.tvMotasanphamyeuthich);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        SanPham sanPham = (SanPham) getItem(i);
        Picasso.get().load(sanPham.getHinhanhsanpham())
                .placeholder(R.drawable.ic_noimage)
                .error(R.drawable.ic_error)
                .into(viewHolder.imgSanphamyeuthich);
        viewHolder.tvTensanphamyeuthich.setMaxLines(2);
        viewHolder.tvTensanphamyeuthich.setText(sanPham.getTensanpham());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        viewHolder.tvGiasanphamyeuthich.setText(decimalFormat.format(sanPham.getGiasanpham()).replaceAll(",", "."));
        viewHolder.tvMotasanphamyeuthich.setMaxLines(2);
        viewHolder.tvMotasanphamyeuthich.setEllipsize(TextUtils.TruncateAt.END);
        viewHolder.tvMotasanphamyeuthich.setText(sanPham.getMotasanpham());

        return view;
    }

    public class ViewHolder {
        private TextView tvTensanphamyeuthich, tvGiasanphamyeuthich, tvMotasanphamyeuthich;
        private ImageView imgSanphamyeuthich;
    }
}
