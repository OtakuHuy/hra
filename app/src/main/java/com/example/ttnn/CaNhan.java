package com.example.ttnn;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

public class CaNhan extends AppCompatActivity {

    String get_ca_nhan = "http://192.168.1.5:8070/khambenh/get_canhan.php";
    String SHARED_PREF_NAME = "session";
    public static final String DEFAULT = "N/A";
    ImageView imgBack;
    TextView tvMa, tvHoTen, tvNgayThangNam, tvSDT, tvDiaChi, tvTuoi;
    Button btnXemBenhAn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ca_nhan);
        final SharedPreferences sharedPreferences = getSharedPreferences("MyData", MODE_PRIVATE);
        String pas = "1";
        pas = sharedPreferences.getString("name", "0");

        tvMa = findViewById(R.id.CaNhan_tvMaBHYT);
        tvMa.setText(pas);

        tvTuoi = findViewById(R.id.CaNhan_tvTuoi);
        tvHoTen = findViewById(R.id.CaNhan_tvHoTen);
        tvNgayThangNam = findViewById(R.id.CaNhan_tvDay);
        tvSDT = findViewById(R.id.CaNhan_tvSDT);
        tvDiaChi = findViewById(R.id.CaNhan_tvDiaChi);


        //------------------------------------------------------------------------------------------
        //imgBack
        imgBack = findViewById(R.id.CaNhan_imgBack);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CaNhan.this, MainActivity.class);
                startActivity(intent);
            }
        });
        //------------------------------------------------------------------------------------------
        //


        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, get_ca_nhan,
                new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    tvHoTen.setText(""+jsonObject.getString("ho")+" "+jsonObject.getString("ten"));
                    tvSDT.setText(jsonObject.getString("sdt"));
                    tvDiaChi.setText(jsonObject.getString("diachi"));
                    String ngay = jsonObject.getString("ngaysinh");
                    String thang = jsonObject.getString("thangsinh");
                    String nam_char = jsonObject.getString("namsinh");
                    tvNgayThangNam.setText(ngay+"/"+thang+"/"+nam_char);

                    Calendar cal = Calendar.getInstance(TimeZone.getDefault());
                    int day = cal.get(Calendar.DATE);
                    int month = cal.get(Calendar.MONTH);
                    int year = cal.get(Calendar.YEAR);
                    int get_nam = Integer.parseInt(nam_char);
                    int tuoi = year - get_nam;
                    String tuoi_c = Integer.toString(tuoi);
                    tvTuoi.setText(tuoi_c);

                } catch (JSONException e) {
                    Toast.makeText(CaNhan.this, "Lỗi 1"+e.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(CaNhan.this, "Lỗi 2"+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("mabhyt",sharedPreferences.getString("name", "1"));
                return params;
            }
        };
        requestQueue.add(stringRequest);



        //-----------------------------------------------------------------------------------------
        //Button xem benh an
        btnXemBenhAn = findViewById(R.id.CaNhan_btnXemBenhAn);
        btnXemBenhAn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CaNhan.this, DanhSachBenhAn.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(CaNhan.this, MainActivity.class);
        startActivity(intent);
    }
}


