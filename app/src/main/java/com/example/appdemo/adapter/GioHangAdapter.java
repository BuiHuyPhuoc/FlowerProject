package com.example.appdemo.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.media.Image;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.appdemo.Interface.IImageClickListener;
import com.example.appdemo.R;
import com.example.appdemo.Utils;
import com.example.appdemo.model.EvenBus.TinhTongEvent;
import com.example.appdemo.model.GioHang;

import org.greenrobot.eventbus.EventBus;

import java.text.DecimalFormat;
import java.util.List;

public class GioHangAdapter extends RecyclerView.Adapter<GioHangAdapter.MyViewHolder> {
    Context context;
    List<GioHang> gioHangList;

    public GioHangAdapter(Context context, List<GioHang> gioHangList) {
        this.context = context;
        this.gioHangList = gioHangList;
    }


    //tạo một viewholder xem mới
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_giohang,parent,false);
        return new MyViewHolder(view);
    }

    //xử lí các dữ liệu item
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        GioHang gioHang = gioHangList.get(position);
        holder.item_giohang_tensp.setText(gioHang.getTenSp());
        holder.item_giohang_sl.setText((gioHang.getSoluong() + " "));
        Glide.with(context).load(gioHang.getHinhSp()).into(holder.item_giohang_image);
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.item_giohang_gia.setText(decimalFormat.format((gioHang.getDONGIA())) + " VNĐ");
        long gia = gioHang.getSoluong() * gioHang.getDONGIA();
        holder.item_giohang_gia2.setText(decimalFormat.format(gia) + " VNĐ");
        //holder.item_giohang_gia2.setText(decimalFormat.format(gioHang.getDONGIA()) + " đ");
        //holder.item_giohang_gia2.setText(decimalFormat.format(gia) + " VNĐ");
        holder.setListener(new IImageClickListener() {
            @Override
            public void onImageClick(View view, int pos, int giatri) {
                //Log.d("TAG","onImageClick: "+pos + "...."+giatri);
                if (giatri == 1)
                {
                    if (gioHangList.get(pos).getSoluong() > 1){
                        int soluongmoi = gioHangList.get(pos).getSoluong()-1;
                        gioHangList.get(pos).setSoluong(soluongmoi);

                        holder.item_giohang_sl.setText((gioHangList.get(pos).getSoluong() + " "));
                        long gia = gioHangList.get(pos).getSoluong() * gioHangList.get(pos).getDONGIA();
                        holder.item_giohang_gia2.setText(decimalFormat.format(gia) + " VNĐ");
                        EventBus.getDefault().postSticky(new TinhTongEvent());//bắt sk tính tổng cho all sp

                    }else if (gioHangList.get(pos).getSoluong() == 1){
                        AlertDialog.Builder builder = new AlertDialog.Builder(view.getRootView().getContext());
                        builder.setTitle("Thông báo");
                        builder.setMessage("Bạn có muốn xóa sản phẩm này khỏi giỏ hàng ?");
                        builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Utils.manggiohang.remove(pos);
                                notifyDataSetChanged();
                                EventBus.getDefault().postSticky(new TinhTongEvent());//bắt sk tính tổng cho all sp
                            }
                        });
                        builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        });
                        builder.show();


                    }
                }else if (giatri == 2){
                    if (gioHangList.get(pos).getSoluong() < 11) {
                        int soluongmoi = gioHangList.get(pos).getSoluong()+1;
                        gioHangList.get(pos).setSoluong(soluongmoi);
                    }

                    holder.item_giohang_sl.setText((gioHangList.get(pos).getSoluong() + " "));
                    long gia = gioHangList.get(pos).getSoluong() * gioHangList.get(pos).getDONGIA();
                    holder.item_giohang_gia2.setText(decimalFormat.format(gia) + " VNĐ");
                    EventBus.getDefault().postSticky(new TinhTongEvent());//bắt sk tính tổng cho all sp
                }


            }
        });
    }
    @Override
    public int getItemCount() {
        return gioHangList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView item_giohang_image,item_giohang_cong,item_giohang_tru;
        TextView item_giohang_tensp,item_giohang_gia,item_giohang_sl,item_giohang_gia2;
        IImageClickListener listener;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            item_giohang_image = itemView.findViewById(R.id.item_giohang_image);
            item_giohang_gia = itemView.findViewById(R.id.item_giohang_gia);
            item_giohang_sl = itemView.findViewById(R.id.item_giohang_sl);
            item_giohang_gia2 = itemView.findViewById(R.id.item_giohang_gia2);
            item_giohang_tensp = itemView.findViewById(R.id.item_giohang_tensp);
            item_giohang_tru = itemView.findViewById(R.id.item_giohang_tru);
            item_giohang_cong = itemView.findViewById(R.id.item_giohang_cong);

            //event click
            item_giohang_cong.setOnClickListener(this);
            item_giohang_tru.setOnClickListener(this);
        }

        public void setListener(IImageClickListener listener) {
            this.listener = listener;
        }

        @Override
        public void onClick(View view) {
            if (view == item_giohang_tru) {
                listener.onImageClick(view, getAbsoluteAdapterPosition(), 1);
                //1 là trừ
            }
            else if (view == item_giohang_cong){
                listener.onImageClick(view, getAbsoluteAdapterPosition(), 2);
                //2 là cộng
            }
        }
    }
}
