package com.example.ttnn;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class Custom_Adapter_Listview_DanhSachBenhAn extends ArrayAdapter {
    Context context;
    ArrayList<Listview_DanhSachBenhAn_Item> list = new ArrayList<Listview_DanhSachBenhAn_Item>();

    public Custom_Adapter_Listview_DanhSachBenhAn(@NonNull Context context, ArrayList<Listview_DanhSachBenhAn_Item> list) {
        super(context, R.layout.custom_listview_danhsachbenhan, list);
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.custom_listview_danhsachbenhan, parent, false);
        }

        Listview_DanhSachBenhAn_Item dulieu = list.get(position);
        TextView MaBenhAn = convertView.findViewById(R.id.DanhSachBenhAn_tvMa);
        TextView Ngay = convertView.findViewById(R.id.DanhSachBenhAn_tvNgay);
        TextView Thang = convertView.findViewById(R.id.DanhSachBenhAn_tvThang);
        TextView Nam = convertView.findViewById(R.id.DanhSachBenhAn_tvNam);

        MaBenhAn.setText(dulieu.MaBenhAn);
        Ngay.setText(dulieu.Ngaykham);
        Thang.setText(dulieu.Thangkham);
        Nam.setText(dulieu.NamKham);

        return convertView;
    }
}
