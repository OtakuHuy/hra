package com.example.ttnn;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class XemBenhAn extends AppCompatActivity {

    private static final String get_benh_an = "http://192.168.1.5:8070/khambenh/get_benhan.php";

    ImageView imgBack;

    TextView tvMaBenhAn, tvMaBHYT, tvNgaykham, tvThangkham, tvNamkham, tvTenBS, tvKhoa, tvTenBenh, tvXN, tvXN1, tvXN2,
                tvThuoc, tvThuoc1, tvThuoc2;

    String ma;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xem_benh_an);

        tvMaBenhAn = findViewById(R.id.XemBenhAn_tvMa);
        tvMaBHYT = findViewById(R.id.XemBenhAn_tvMaBHYT);
        tvNgaykham = findViewById(R.id.XemBenhAn_tvNgayKham);
        tvThangkham = findViewById(R.id.XemBenhAn_tvThangKham);
        tvNamkham = findViewById(R.id.XemBenhAn_tvNamKham);
        tvTenBS = findViewById(R.id.XemBenhAn_tvBacSi);
        tvKhoa = findViewById(R.id.XemBenhAn_tvTenKhoa);
        tvTenBenh = findViewById(R.id.XemBenhAn_tvTenBenh);
        tvXN = findViewById(R.id.XemBenhAn_tvXN);
        tvXN1 = findViewById(R.id.XemBenhAn_tvXN1);
        tvXN2 = findViewById(R.id.XemBenhAn_tvXN2);
        tvThuoc = findViewById(R.id.XemBenhAn_tvThuoc);
        tvThuoc1 = findViewById(R.id.XemBenhAn_tvThuoc1);
        tvThuoc2 = findViewById(R.id.XemBenhAn_tvThuoc2);

        imgBack = findViewById(R.id.XemBenhAn_imgBack);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(XemBenhAn.this, MainActivity.class);
                startActivity(intent);
            }
        });

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if(bundle != null){
            ma = bundle.getString("MaBenhAn");
        }

        LayBenhAn();

    }

    void LayBenhAn(){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, get_benh_an,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);

                             tvMaBenhAn.setText(jsonObject.getString("MABENHAN"));

                            tvMaBHYT.setText(jsonObject.getString("MaBHYT"));

                            tvNgaykham.setText(jsonObject.getString("ngaykham"));

                            tvThangkham.setText(jsonObject.getString("thangkham"));

                            tvNamkham.setText(jsonObject.getString("namkham"));

                            tvTenBS.setText(jsonObject.getString("TENBACSI"));

                            tvKhoa.setText(jsonObject.getString("TENKHOA"));

                            tvTenBenh.setText(jsonObject.getString("TENBENH"));

                            tvXN.setText(jsonObject.getString("XETNGHIEM"));

                            tvXN1.setText(jsonObject.getString("XETNGHIEM1"));

                            tvXN2.setText(jsonObject.getString("XETNGHIEM2"));

                            tvThuoc.setText(jsonObject.getString("THUOC"));

                            tvThuoc1.setText(jsonObject.getString("THUOC1"));

                            tvThuoc2.setText(jsonObject.getString("THUOC2"));


                        } catch (JSONException e) {
                            Toast.makeText(XemBenhAn.this, "Lỗi 1:"+e.getMessage(), Toast.LENGTH_SHORT).show();
                            Log.d("Lỗi 1", e.getMessage());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(XemBenhAn.this, "Lỗi 2:"+error.getMessage(), Toast.LENGTH_SHORT).show();
                        Log.d("Lỗi 2", error.getMessage());
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("mabenhan",ma);
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
}