package com.boyhotkey96.thietbicuahangonline.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.boyhotkey96.thietbicuahangonline.R;
import com.boyhotkey96.thietbicuahangonline.activity.ChiTietSanPham;
import com.boyhotkey96.thietbicuahangonline.model.SanPham;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class SanPhamMoiNhatAdapter extends RecyclerView.Adapter<SanPhamMoiNhatAdapter.ViewHolder> {

    private Context context;
    private ArrayList<SanPham> arrayListSanpham;

    public SanPhamMoiNhatAdapter(Context context, ArrayList<SanPham> arrayListSanpham) {
        this.context = context;
        this.arrayListSanpham = arrayListSanpham;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_sanphammoinhat, null);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SanPham sanPham = arrayListSanpham.get(position);

        holder.txtTensanpham.setText(sanPham.getTensanpham());
        holder.txtTensanpham.setMaxLines(2);
        //holder.txtTensanpham.setEllipsize(TextUtils.TruncateAt.END);
        /*SpannableString content = new SpannableString("Text with underline span");
        content.setSpan(new UnderlineSpan(), 10, 19, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);*/
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        //String sss = decimalFormat.format(sanPham.getGiasanpham());
        holder.txtGiasanpham.setText("Giá: " + decimalFormat.format(sanPham.getGiasanpham()).replaceAll(",", "."));
        Picasso.get().load(sanPham.getHinhanhsanpham())
                .placeholder(R.drawable.ic_noimage)
                .error(R.drawable.ic_error)
                .into(holder.imgHinhsanpham);
    }

    @Override
    public int getItemCount() {
        return arrayListSanpham.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView txtTensanpham, txtGiasanpham;
        public ImageView imgHinhsanpham;

        public ViewHolder(View itemView) {
            super(itemView);
            txtTensanpham = itemView.findViewById(R.id.tvTensanpham);
            txtGiasanpham = itemView.findViewById(R.id.tvGiasanpham);
            imgHinhsanpham = itemView.findViewById(R.id.imgSanpham);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, ChiTietSanPham.class);
                    intent.putExtra("thongtinsanpham", arrayListSanpham.get(getPosition()));
                    //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                    //CheckConnectInternet.ShowToast(context, arrayListSanpham.get(getPosition()).getTensanpham());
                }
            });
        }
    }
}
