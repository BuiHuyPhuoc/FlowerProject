package com.example.appdemo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.appdemo.Class.HoaDon;
import com.example.appdemo.R;
import com.example.appdemo.model.DatabaseHelper;

import java.util.ArrayList;

public class HoaDonAdapter extends ArrayAdapter {
    ArrayList<HoaDon> hoaDons;
    Context context;
    DatabaseHelper databaseHelper;
    public HoaDonAdapter(Context context, ArrayList<HoaDon> hoaDons){
        super(context, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, hoaDons);
        this.context = context;
        this.hoaDons = hoaDons;
        databaseHelper = new DatabaseHelper(context, "DBFlowerShop.sqlite", null, 1);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        convertView = inflater.inflate(android.R.layout.simple_spinner_dropdown_item, parent, false);
        String item = hoaDons.get(position).toString();
        TextView textView = convertView.findViewById(android.R.id.text1);
        textView.setText(item);
        return convertView;
    }
}
