package com.example.ttnn;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.style.TabStopSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class XemDangKyKham extends AppCompatActivity {

    TextView tvMaBHYT, tvTen, tvLuaChon, tvNgayKham;
    Button btnXoa, btnSua;
    EditDangKyKham_Adapter adapter;

    String get_date, ngay, thang, nam;
    static final String lay_toanbodangkykham = "http://192.168.1.5:8070/khambenh/lay_toanbodangkykham.php";
    static final String xoa_dangky = "http://192.168.1.5:8070/khambenh/xoadangky.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_xem_dang_ky_kham);

                tvMaBHYT = findViewById(R.id.XemDangKyKham_tvMaBHYT);
                tvTen = findViewById(R.id.XemDangKyKham_tvHoTen);
                tvLuaChon = findViewById(R.id.XemDangKyKham_tvLuachon);
                tvNgayKham = findViewById(R.id.XemDangKyKham_tvNgayDangKy);

                //Nut xoa



                Intent intent = getIntent();
                Bundle bundle = intent.getExtras();

                if(bundle != null){
                    get_date = bundle.getString("ngay");
                }
                String catchuoi[] = get_date.split("/");
                ngay = catchuoi[0];
                thang = catchuoi[1];
                nam = catchuoi[2];

                LayToanBoDangKyKham();

                btnXoa = findViewById(R.id.XemDangKyKham_btnXoa);
                btnXoa.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        XoaDangKy();
                        Intent intent = new Intent(XemDangKyKham.this, TuyChinhDangKyKham.class);
                        startActivity(intent);
                    }
                });


    }

    void LayToanBoDangKyKham(){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, lay_toanbodangkykham,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject object = new JSONObject(response);
                            JSONArray jsonArray = object.getJSONArray("danhsach");
                            for(int i = 0; i < jsonArray.length(); i++){
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                tvMaBHYT.setText(jsonObject.getString("mabhyt"));
                                tvTen.setText(jsonObject.getString("hoten"));
                                tvLuaChon.setText(jsonObject.getString("luachon"));
                                tvNgayKham.setText(jsonObject.getString("ngaykham")+"/"+jsonObject.getString("thangkham")
                                    +"/"+jsonObject.getString("namkham"));
                            }
                        } catch (JSONException e) {
                            Toast.makeText(XemDangKyKham.this, "Lỗi 1:"+e.getMessage(), Toast.LENGTH_SHORT).show();
                            Log.d("LỖI 1:", e.getMessage());
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(XemDangKyKham.this, "Lỗi 2:"+error.getMessage(), Toast.LENGTH_SHORT).show()  ;
                Log.d("LỖI 2:", error.getMessage());
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<String,String>();
                params.put("ngay", ngay);
                params.put("thang", thang);
                params.put("nam", nam);
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    void XoaDangKy(){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, xoa_dangky,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            if(success.equals("1")){
                                Toast.makeText(XemDangKyKham.this, "Xóa đăng ký thành công.", Toast.LENGTH_SHORT).show();
                            }
                            else{
                                Toast.makeText(XemDangKyKham.this, "Xoá không thành công.", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            Toast.makeText(XemDangKyKham.this, "Lỗi 1:"+e.getMessage(), Toast.LENGTH_SHORT).show();
                            Log.d("Lỗi 1:", e.getMessage());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(XemDangKyKham.this, "Lỗi 2:"+error.getMessage(), Toast.LENGTH_SHORT).show();
                        Log.d("Lỗi 2:", error.getMessage());
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("ngay",ngay);
                params.put("thang", thang);
                params.put("nam", nam);
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
}