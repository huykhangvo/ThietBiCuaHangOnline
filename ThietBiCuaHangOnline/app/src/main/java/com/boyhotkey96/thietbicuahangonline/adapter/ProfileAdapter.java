package com.boyhotkey96.thietbicuahangonline.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.boyhotkey96.thietbicuahangonline.R;
import com.boyhotkey96.thietbicuahangonline.activity.UpdateProfile;
import com.boyhotkey96.thietbicuahangonline.model.Profile;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class ProfileAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Profile> arrayListProfile;

    public ProfileAdapter(Context context, ArrayList<Profile> arrayListProfile) {
        this.context = context;
        this.arrayListProfile = arrayListProfile;
    }

    @Override
    public int getCount() {
        return arrayListProfile.size();
    }

    @Override
    public Object getItem(int i) {
        return arrayListProfile.get(i);
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
            view = LayoutInflater.from(context).inflate(R.layout.row_profile, null);
            viewHolder.btnUpdate = view.findViewById(R.id.btnUpdate);
            viewHolder.tvName = view.findViewById(R.id.tvName);
            viewHolder.tvEmail = view.findViewById(R.id.tvEmail);
            viewHolder.tvNgaytao = view.findViewById(R.id.tvNgaytao);
            viewHolder.imgProfile = view.findViewById(R.id.imgProfile);
            viewHolder.tvNgaysinh = view.findViewById(R.id.tvNgaysinh);
            viewHolder.tvSodienthoai = view.findViewById(R.id.tvSodienthoai);
            viewHolder.tvDiachi = view.findViewById(R.id.tvDiachi);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        final Profile profile = (Profile) getItem(i);
        if (profile.getImage().equals("")) {
            viewHolder.imgProfile.setImageResource(R.drawable.ic_noimage);
        } else {
            Picasso.get().load(profile.getImage())
                    .placeholder(R.drawable.ic_noimage)
                    .error(R.drawable.ic_error)
                    .into(viewHolder.imgProfile);
        }
        viewHolder.tvName.setText("Name: " + profile.getName());
        viewHolder.tvEmail.setText("Email: " + profile.getEmail());
        viewHolder.tvNgaytao.setText("Ngày tạo: " + profile.getNgaytao());

        /*SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/mm/yyyy");
        //Log.d("ZZZ", String.valueOf(simpleDateFormat.format(profile.getNgaysinh())));
        viewHolder.tvNgaysinh.setText("Ngày sinh: " + simpleDateFormat.format(Date.parse(profile.getNgaysinh())));*/

        viewHolder.tvNgaysinh.setText("Ngày sinh: " + profile.getNgaysinh());
        viewHolder.tvSodienthoai.setText("Số điện thoại: " + profile.getSodienthoai());
        viewHolder.tvDiachi.setText("Địa chỉ: " + profile.getDiachi());
        viewHolder.btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, UpdateProfile.class);
                intent.putExtra("dataProfile", profile);
                context.startActivity(intent);
                ((Activity)context).overridePendingTransition(R.anim.user_open_in, R.anim.user_open_out);
                ((Activity)context).finish();
            }
        });

        return view;
    }

    public class ViewHolder {
        private AppCompatButton btnUpdate;
        private ImageView imgProfile;
        private TextView tvName, tvEmail, tvNgaytao, tvNgaysinh, tvSodienthoai, tvDiachi;
    }
}
