package com.example.appdemo.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appdemo.Admin.UseVoucherActivity;
import com.example.appdemo.Class.SanPham;
import com.example.appdemo.R;
import com.example.appdemo.activity.MainActivity;
import com.example.appdemo.model.DatabaseHelper;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class UseVoucherAdapter extends RecyclerView.Adapter<UseVoucherAdapter.ViewHolder> {
    Context context;
    ArrayList<SanPham> sanPhamArrayList;
    DatabaseHelper databaseHelper;
    String maVoucher;
    private OnItemClickListener listener;
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public UseVoucherAdapter(Context context, ArrayList<SanPham> sanPhams, String maVoucher){
        this.context = context;
        this.sanPhamArrayList = sanPhams;
        databaseHelper = new DatabaseHelper(context, "DBFlowerShop.sqlite", null, 1);
        this.maVoucher = maVoucher;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_usevoucher, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        SanPham sanPham = sanPhamArrayList.get(position);
        holder.tvMaSP.setText(sanPham.getMASP());
        holder.tvTenSP.setText(sanPham.getTENSP());
        holder.tvGiaSP.setText(sanPham.getDONGIA().toString());
        Cursor cursor = databaseHelper.GetData("Select* from VOUCHER_DETAIL Where MASP = '"+sanPham.getMASP()+"'");
        holder.tvSoVoucher.setText("Sản phẩm đang có: "+cursor.getCount()+" voucher.");
        holder.imgAvatar.setImageResource(sanPham.getHINHANH());
        Cursor cursor1 = databaseHelper.GetData("Select* from VOUCHER_DETAIL Where MASP = '"+sanPham.getMASP()+"' and MAVOUCHER='"+maVoucher+"'");
        if (cursor1.getCount() == 1) holder.status = true; else holder.status = false;
        if (!holder.status)
            holder.tvStatus.setText("Chưa được sử dụng.");
        else
            holder.tvStatus.setText("Đã được sử dụng.");
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onItemClick(sanPhamArrayList.get(position));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return sanPhamArrayList.size();
    }
    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvMaSP;
        TextView tvTenSP;
        TextView tvGiaSP;
        TextView tvSoVoucher;
        ImageView imgAvatar;
        TextView tvStatus;
        boolean status;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // Ánh xạ view
            tvMaSP = itemView.findViewById(R.id.tvMaSP);
            tvTenSP = itemView.findViewById(R.id.tvTenSP);
            tvGiaSP = itemView.findViewById(R.id.tvGiaSP);
            tvSoVoucher = itemView.findViewById(R.id.tvSoVoucher);
            imgAvatar = itemView.findViewById(R.id.imgAva);
            tvStatus = itemView.findViewById(R.id.tvStatus);
        }
    }
    public interface OnItemClickListener {
        void onItemClick(SanPham item);
    }


}
