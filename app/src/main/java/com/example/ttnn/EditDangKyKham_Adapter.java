package com.example.ttnn;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class EditDangKyKham_Adapter extends ArrayAdapter {

    Context context;
    ArrayList<ListView_EditDangKyKham_Item> list = new ArrayList<>();

    public EditDangKyKham_Adapter(@NonNull Context context, ArrayList<ListView_EditDangKyKham_Item> list) {
        super(context, R.layout.edit_dangkykham_listview, list);
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.edit_dangkykham_listview, parent, false);
        }

        ListView_EditDangKyKham_Item item = list.get(position);
        TextView MaDangKy = convertView.findViewById(R.id.EditDangKyKham_ListView_Item_tvMaDangKy);
        TextView NgayDangKy = convertView.findViewById(R.id.EditDangKyKham_ListView_Item_tvNgayDangKy);

        MaDangKy.setText(item.MaDangKy);
        NgayDangKy.setText(item.NgayDangKy);

        return convertView;
    }
}
