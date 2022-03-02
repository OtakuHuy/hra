package com.example.ttnn;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class GridAdapter_Main extends ArrayAdapter {
    Context context;
    ArrayList<GridItem_Main> gridItem_mains;

    public GridAdapter_Main(@NonNull Context context, ArrayList<GridItem_Main> gridItem_mains) {
        super(context, R.layout.gridview_main_item, gridItem_mains);
        this.context = context;
        this.gridItem_mains = gridItem_mains;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView == null)
        {
            convertView = LayoutInflater.from(context).inflate(R.layout.gridview_main_item, parent, false);
        }

        GridItem_Main gridItem_main = gridItem_mains.get(position);
        ImageView Hinh = convertView.findViewById(R.id.GridItem_Main_Layout_Img);
        TextView Chu = convertView.findViewById(R.id.GridItem_Main_Layout_Text);

        Hinh.setImageResource(gridItem_main.Hinh);
        Chu.setText(gridItem_main.Chu);

        return convertView;
    }
}
