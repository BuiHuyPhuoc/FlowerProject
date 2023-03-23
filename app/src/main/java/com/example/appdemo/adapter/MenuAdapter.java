package com.example.appdemo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.appdemo.R;
import com.example.appdemo.model.ItemMenu;

import java.util.List;
public class MenuAdapter extends BaseAdapter {
    List<ItemMenu> list;
    private int layout;
    Context context;

    public MenuAdapter(List<ItemMenu> list, int layout, Context context) {
        this.list = list;
        this.layout = layout;
        this.context = context;
    }

    public MenuAdapter(Context context, List<ItemMenu> list) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    private class ViewHolder{
        TextView tv;
        ImageView img;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (view == null){
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(layout,null);
            viewHolder = new ViewHolder();

            viewHolder.tv =(TextView) view.findViewById(R.id.tv_item);
            viewHolder.img =(ImageView) view.findViewById(R.id.imgItem);
            view.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.tv.setText(list.get(i).tenMenu);
        viewHolder.img.setImageResource(list.get(i).icon);

        return view;
    }
}
