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

public class PhuKienAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<SanPham> arrayListPhukien;

    public PhuKienAdapter(Context context, ArrayList<SanPham> arrayListPhukien) {
        this.context = context;
        this.arrayListPhukien = arrayListPhukien;
    }

    @Override
    public int getCount() {
        return arrayListPhukien.size();
    }

    @Override
    public Object getItem(int i) {
        return arrayListPhukien.get(i);
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
            view = LayoutInflater.from(context).inflate(R.layout.row_phukien, null);
            viewHolder.imgPhukien = view.findViewById(R.id.imgPhukien);
            viewHolder.tvTenphukien = view.findViewById(R.id.tvTenphukien);
            viewHolder.tvGiaphukien = view.findViewById(R.id.tvGiaphukien);
            viewHolder.tvMotaphukien = view.findViewById(R.id.tvMotaphukien);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        SanPham sanPham = (SanPham) getItem(i);
        Picasso.get().load(sanPham.getHinhanhsanpham())
                .placeholder(R.drawable.ic_noimage)
                .error(R.drawable.ic_error)
                .into(viewHolder.imgPhukien);
        viewHolder.tvTenphukien.setMaxLines(2);
        viewHolder.tvTenphukien.setText(sanPham.getTensanpham());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        viewHolder.tvGiaphukien.setText(decimalFormat.format(sanPham.getGiasanpham()).replaceAll(",", "."));
        viewHolder.tvMotaphukien.setMaxLines(2);
        viewHolder.tvMotaphukien.setEllipsize(TextUtils.TruncateAt.END);
        viewHolder.tvMotaphukien.setText(sanPham.getMotasanpham());

        return view;
    }

    public class ViewHolder {
        private ImageView imgPhukien;
        private TextView tvTenphukien, tvGiaphukien, tvMotaphukien;
    }
}
