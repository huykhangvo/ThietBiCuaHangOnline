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

public class DienThoaiAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<SanPham> arrayListDienThoai;

    public DienThoaiAdapter(Context context, ArrayList<SanPham> arrayListDienThoai) {
        this.context = context;
        this.arrayListDienThoai = arrayListDienThoai;
    }

    @Override
    public int getCount() {
        return arrayListDienThoai.size();
    }

    @Override
    public Object getItem(int i) {
        return arrayListDienThoai.get(i);
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
            view = LayoutInflater.from(context).inflate(R.layout.row_dienthoai, null);
            viewHolder.txtTendienthoai = view.findViewById(R.id.tvTendienthoai);
            viewHolder.txtGiadienthoai = view.findViewById(R.id.tvGiadienthoai);
            viewHolder.txtMotadienthoai = view.findViewById(R.id.tvMotadienthoai);
            viewHolder.imgDienthoai = view.findViewById(R.id.imgDienthoai);

            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        SanPham sanPham = (SanPham) getItem(i);
        viewHolder.txtTendienthoai.setText(sanPham.getTensanpham());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        viewHolder.txtGiadienthoai.setText(decimalFormat.format(sanPham.getGiasanpham()).replaceAll(",", "."));
        //Mo ta DienThoai toi da 2 dong
        viewHolder.txtMotadienthoai.setMaxLines(2);
        //Hien dau 3 cham (...) khi max 2 line.
        viewHolder.txtMotadienthoai.setEllipsize(TextUtils.TruncateAt.END);
        viewHolder.txtMotadienthoai.setText(sanPham.getMotasanpham());
        Picasso.get().load(sanPham.getHinhanhsanpham())
                .placeholder(R.drawable.ic_noimage)
                .error(R.drawable.ic_error)
                .into(viewHolder.imgDienthoai);

        return view;
    }

    public class ViewHolder {
        private TextView txtTendienthoai, txtGiadienthoai, txtMotadienthoai;
        private ImageView imgDienthoai;
    }
}
