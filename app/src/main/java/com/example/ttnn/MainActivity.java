package com.example.ttnn;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.transition.Slide;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import static android.widget.Toast.LENGTH_LONG;

public class MainActivity extends AppCompatActivity {

    GridView gridView;
    GridAdapter_Main mainAdapter;
    ArrayList<GridItem_Main> itemList = new ArrayList<GridItem_Main>();
    TextView tvDangky;


    ViewPager Slider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvDangky = findViewById(R.id.tvDangky);
        tvDangky.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, DangKyTaiKhoan.class);
                startActivity(intent);
            }
        });

        Slider = findViewById(R.id.MainActivity_Slider);
        SliderAdapter sliderAdapter = new SliderAdapter(this);
        Slider.setAdapter(sliderAdapter);
        Slider.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                                           @Override
                                           public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                                           }
                                           @Override
                                           public void onPageSelected(int position) {
                                               Toast.makeText(MainActivity.this, ""+position, Toast.LENGTH_SHORT).show();
                                           }
                                           @Override
                                           public void onPageScrollStateChanged(int state) {
                                           }
                                       });


                //------------------------------------------------------------------------------------------

        gridView = (GridView) findViewById(R.id.GridView_Main);
        GridAdapter_Main adapter;

        itemList.add(new GridItem_Main(R.drawable.ic_person,"Cá nhan"));
        itemList.add(new GridItem_Main(R.drawable.ic_call,"Gọi khẩn cấp"));
        itemList.add(new GridItem_Main(R.drawable.ic_dangki,"Đăng kí khám"));
        itemList.add(new GridItem_Main(R.drawable.ic_edit,"Chỉnh đăng ký khám"));
        itemList.add(new GridItem_Main(R.drawable.ic_setting,"Tùy chọn"));
        itemList.add(new GridItem_Main(R.drawable.ic_price, "Bảng giá khám"));

        mainAdapter = new GridAdapter_Main(this, itemList);
        gridView.setAdapter(mainAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(i == 0)
                {
                    Intent canhan = new Intent(MainActivity.this, DangnhapDangky.class);
                    startActivity(canhan);
                }
                else if (i == 1)
                {
                    /*
                    Intent call = new Intent(Intent.ACTION_DIAL);
                    call.setData(Uri.parse("tel:0134567"));
                    startActivity(call);*/
                    Intent intent = new Intent(MainActivity.this, ChiTietLienHe.class);
                    startActivity(intent);
                }
                else if (i == 2)
                {
                    Intent dangky = new Intent(MainActivity.this, DangKyKham.class);
                    startActivity(dangky);
                }
                else if (i == 3)
                {
                    Intent intent = new Intent(MainActivity.this, TuyChinhDangKyKham.class);
                    startActivity(intent);
                }
                else if(i == 4)
                {
                    Intent tuychon = new Intent(MainActivity.this, TuyChon.class);
                    startActivity(tuychon);
                }
            }
        });


    }
}