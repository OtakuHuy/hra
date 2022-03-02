package com.example.ttnn;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

public class DangKyKham extends AppCompatActivity {

    String lay_bacsi_khoa = "http://192.168.1.5:8070/khambenh/lay_bacsi_khoa.php";
    String put_dangkykham = "http://192.168.1.5:8070/khambenh/put_dangkykham.php";
    String get_canhan = "http://192.168.1.5:8070/khambenh/get_canhan.php";

    TextView tvMaBHYT, tvHoTen;
    Spinner spnKhoa, spnBuoi;
    EditText edtNgay, edtThang, edtNam;
    ImageView imgBack;

    Button btnHoanTat;

    ArrayList<Array_DangKyTuyChon> spn_data = new ArrayList<>();
    ArrayList<String> buoi = new ArrayList<>();
    ArrayList<String> khoa_bacsi = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_ky_kham);

        tvMaBHYT = findViewById(R.id.Dangkykham_tvMaBHYT);
        tvHoTen = findViewById(R.id.Dangkykham_tvHoTen);
        edtNgay = findViewById(R.id.Dangkykham_edtNgay);
        edtThang = findViewById(R.id.Dangkykham_edtThang);
        edtNam = findViewById(R.id.Dangkykham_edtNam);
        spnKhoa = findViewById(R.id.Dangkykham_spnKhoa);
        spnBuoi = findViewById(R.id.Dangkykham_spnBuoi);

        imgBack = findViewById(R.id.Dangkykham_imgBack);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DangKyKham.this, MainActivity.class);
                startActivity(intent);
            }
        });

        //Lấy danh sách bác sĩ nạp vào để user lựa chọn
        Lay_BacSi_Khoa();

        buoi = new ArrayList<>();
        buoi.add("Sáng");
        buoi.add("Trưa");
        ArrayAdapter adapter_buoi = new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, buoi);
        spnBuoi.setAdapter(adapter_buoi);
        khoa_bacsi.add("Tự động");
        ArrayAdapter adapter_khoa_bacsi = new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, khoa_bacsi);
        spnKhoa.setAdapter(adapter_khoa_bacsi);

        SharedPreferences sharedPreferences = getSharedPreferences("MyData", MODE_PRIVATE);
        tvMaBHYT.setText(sharedPreferences.getString("name", "0"));


        //Lấy lại data người dùng nạp vào form
        LayDataUser();


        btnHoanTat = findViewById(R.id.Dangkykham_btnHoanTat);
        btnHoanTat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance(TimeZone.getDefault());
                if(edtNgay.getText().toString().isEmpty()){
                    Toast.makeText(DangKyKham.this, "Ngày muốn khám không thể để trống.", Toast.LENGTH_SHORT).show();
                }else
                if(edtThang.getText().toString().isEmpty()){
                    Toast.makeText(DangKyKham.this, "Tháng muốn khám không thể để trống", Toast.LENGTH_SHORT).show();
                }else
                if(edtNam.getText().toString().isEmpty()){
                    Toast.makeText(DangKyKham.this, "Năm muốn khám không thể để trống", Toast.LENGTH_SHORT).show();
                }else
                if(Integer.parseInt(edtNam.getText().toString()) < cal.get(Calendar.YEAR)){
                    Toast.makeText(DangKyKham.this, "Không thể chọn năm trong quá khứ.", Toast.LENGTH_SHORT).show();
                }else
                if(Integer.parseInt(edtThang.getText().toString()) < (cal.get(Calendar.MONTH)+1) && Integer.parseInt(edtNam.getText().toString()) <= cal.get(Calendar.YEAR)){
                    Toast.makeText(DangKyKham.this, "Không thể chọn tháng trong quá khứ.", Toast.LENGTH_SHORT).show();
                }else
                if(Integer.parseInt(edtNgay.getText().toString()) <= (cal.get(Calendar.DATE)+1) && Integer.parseInt(edtThang.getText().toString()) < cal.get(Calendar.MONTH) && Integer.parseInt(edtNam.getText().toString()) < cal.get(Calendar.YEAR) ) {
                    Toast.makeText(DangKyKham.this, "Không thể chọn ngày trong quá khứ.", Toast.LENGTH_SHORT).show();
                } else {
                    NapDangKy();
                    Intent intent = new Intent(DangKyKham.this, MainActivity.class);
                    startActivity(intent);
                }
            }
        });

    }



    void Lay_BacSi_Khoa(){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, lay_bacsi_khoa, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                            try {
                                for(int i = 0; i < response.length(); i++){
                                    String mabacsi;
                                    JSONObject jsonObject = response.getJSONObject(i);
                                    String dump_string = jsonObject.getString("MAKHOA");
                                    mabacsi = jsonObject.getString("MABACSI");
                                    String item ="["+mabacsi+"] "+jsonObject.getString("TENKHOA")
                                            +" - "+jsonObject.getString("TENBACSI");
                                    khoa_bacsi.add(item);

                                }
                            } catch (JSONException e) {
                                Toast.makeText(DangKyKham.this, "Lỗi 1:"+e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(DangKyKham.this, "Lỗi 2:"+error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
        requestQueue.add(jsonArrayRequest);
    }

    void NapDangKy() {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, put_dangkykham,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String result = jsonObject.getString("success");
                            if(result.equals("1")){
                                Toast.makeText(DangKyKham.this, "Đăng ký thành công!!!", Toast.LENGTH_SHORT).show();
                            }
                            else{
                                Toast.makeText(DangKyKham.this, "Đâng ký thất bại!!!", Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            Toast.makeText(DangKyKham.this, "Lỗi 1:"+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(DangKyKham.this, "Lỗi 2:"+error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                })
                {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("mabhyt", tvMaBHYT.getText().toString());
                        params.put("hoten", tvHoTen.getText().toString());
                        params.put("luachon", spnKhoa.getSelectedItem().toString());
                        params.put("ngaykham", edtNgay.getText().toString());
                        params.put("thangkham", edtThang.getText().toString());
                        params.put("namkham", edtNam.getText().toString());
                        params.put("buoi", spnBuoi.getSelectedItem().toString());
                        return params;
                    }
                };
        requestQueue.add(stringRequest);
    }

    void LayDataUser(){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, get_canhan,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            tvHoTen.setText(jsonObject.getString("ho") + " " + jsonObject.getString("ten"));
                            String dump = jsonObject.getString("sdt");
                            String dump1 = jsonObject.getString("diachi");
                            String dump2 = jsonObject.getString("ngaysinh");
                            String dump3 = jsonObject.getString("thangsinh");
                            String dump4 = jsonObject.getString("namsinh");
                        } catch (JSONException e) {
                            Toast.makeText(DangKyKham.this, "Lỗi 1:" + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(DangKyKham.this, "Lỗi 2:" + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> pagrams = new HashMap<String, String>();
                pagrams.put("mabhyt", tvMaBHYT.getText().toString());
                return pagrams;
            }
        };
        requestQueue.add(stringRequest);
    }
}
