package com.example.appdemo.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.appdemo.R;
import com.example.appdemo.activity.ChiTietSPActivity;
import com.example.appdemo.model.SanPhamMoi;

import java.text.DecimalFormat;
import java.util.List;

public class SanPhamAdapter extends RecyclerView.Adapter<SanPhamAdapter.MyViewHolder> {

    List<SanPhamMoi> array;
    Context context;

    public SanPhamAdapter(Context context, List<SanPhamMoi> array) {
        this.context = context;
        this.array = array;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_sp_moi,parent,false);
        return new SanPhamAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        SanPhamMoi sanPhamMoi = array.get(position);

        holder.tensp.setText(sanPhamMoi.getTENSP());
        //holder.giatien.setText(Long.toString(sanPhamMoi.getDONGIA()) + "VND");
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.giatien.setText("Giá: "+decimalFormat.format(Double.parseDouble(String.valueOf(sanPhamMoi.getDONGIA()))) + " VNĐ");
        Glide.with(context).load(sanPhamMoi.getHINHANH()).into(holder.imgHinhanh);
//        holder.imgHinhanh.setImageResource(sanPhamMoi.getHINHANH());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, ChiTietSPActivity.class);
                i.putExtra("chitiet",sanPhamMoi.getTENSP());
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);
            }
        });


    }

    @Override
    public int getItemCount() {
        return array.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView giatien, tensp;
        ImageView imgHinhanh;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            giatien = itemView.findViewById(R.id.tvPrice);
            tensp = itemView.findViewById(R.id.tvTittle);
            imgHinhanh = itemView.findViewById(R.id.imgItemSp);
        }
    }
}
