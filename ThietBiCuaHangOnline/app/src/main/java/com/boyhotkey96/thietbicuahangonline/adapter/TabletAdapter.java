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

public class TabletAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<SanPham> arrayListTablet;

    public TabletAdapter(Context context, ArrayList<SanPham> arrayListTablet) {
        this.context = context;
        this.arrayListTablet = arrayListTablet;
    }

    @Override
    public int getCount() {
        return arrayListTablet.size();
    }

    @Override
    public Object getItem(int i) {
        return arrayListTablet.get(i);
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
            view = LayoutInflater.from(context).inflate(R.layout.row_tablet, null);
            viewHolder.imgTablet = view.findViewById(R.id.imgTablet);
            viewHolder.tvTentablet = view.findViewById(R.id.tvTentablet);
            viewHolder.tvGiatablet = view.findViewById(R.id.tvGiatablet);
            viewHolder.tvMotatablet = view.findViewById(R.id.tvMotatablet);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        SanPham sanPham = (SanPham) getItem(i);
        Picasso.get().load(sanPham.getHinhanhsanpham())
                .placeholder(R.drawable.ic_noimage)
                .error(R.drawable.ic_error)
                .into(viewHolder.imgTablet);
        viewHolder.tvTentablet.setText(sanPham.getTensanpham());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        viewHolder.tvGiatablet.setText(decimalFormat.format(sanPham.getGiasanpham()).replaceAll(",", "."));
        viewHolder.tvMotatablet.setMaxLines(2);
        viewHolder.tvMotatablet.setEllipsize(TextUtils.TruncateAt.END);
        viewHolder.tvMotatablet.setText(sanPham.getMotasanpham());

        return view;
    }

    public class ViewHolder {
        private ImageView imgTablet;
        private TextView tvTentablet, tvGiatablet, tvMotatablet;
    }
}
