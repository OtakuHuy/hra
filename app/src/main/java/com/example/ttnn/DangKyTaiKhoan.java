package com.example.ttnn;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DownloadManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.time.Year;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

public class DangKyTaiKhoan extends AppCompatActivity {

    TextView tvKetQua;
    EditText edtMaBHYT, edtMK, edtMK2, edtHo, edtTen, edtSDT, edtDiaChi, edtNgay, edtThang, edtNam;
    Button btnKiemTra, btnDangKy;
    ImageView imgBack;
    DatePicker datePicker;

    String lay_mabhyt_sosanh = "http://192.168.1.5:8070/khambenh/laymabhyt_sosanh.php";
    String post_ca_nhan = "http://192.168.1.5:8070/khambenh/put_canhan.php";
    String ngay;
    String thang;
    String nam;




    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_ky_tai_khoan);

        imgBack = findViewById(R.id.DangKy_imgBack);

        tvKetQua = findViewById(R.id.Dangky_TvKetQuaKiemTra);

        edtMaBHYT = findViewById(R.id.Dangky_edtMaBHYT);
        edtMK = findViewById(R.id.Dangky_edtMatKhau);
        edtMK2 = findViewById(R.id.Dangky_edtMatKhau2);
        edtHo = findViewById(R.id.Dangky_edtHo);
        edtTen = findViewById(R.id.Dangky_edtTen);
        edtSDT = findViewById(R.id.Dangky_edtSoDT);
        edtDiaChi = findViewById(R.id.Dangky_edtDiaChi);
        edtNgay = findViewById(R.id.Dangky_edtNgay);
        edtThang = findViewById(R.id.Dangky_edtThang);
        edtNam = findViewById(R.id.Dangky_edtNam);

        btnDangKy = findViewById(R.id.Dangky_btnDangKy);
        btnKiemTra = findViewById(R.id.Dangky_btnKiemTra);

        //datePicker = findViewById(R.id.Dangky_DatePicker);



        //----------------------------------------------------------------------------------------
        //----------------------------------------------------------------------------------------
        //Image Back
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(DangKyTaiKhoan.this, MainActivity.class);
                startActivity(intent);
            }
        });
        //----------------------------------------------------------------------------------------
        //Nut kiem tra Ma BHYT
        final RequestQueue requestQueue = Volley.newRequestQueue(this);

        btnKiemTra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, lay_mabhyt_sosanh, null, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                    JSONObject jsonObject = response.getJSONObject(i);
                                    if(edtMaBHYT.getText().toString().trim().equals(jsonObject.getString("mabhyt"+""))){
                                        tvKetQua.setText("M?? BHYT h???p l???");
                                        break;
                                    }
                                    tvKetQua.setText("M?? BHYT kh??ng h???p l???, xin nh???p l???i");
                                    edtMaBHYT.setText(null);
                                }

                        } catch (JSONException e) {
                            Toast.makeText(DangKyTaiKhoan.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(DangKyTaiKhoan.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
                requestQueue.add(jsonArrayRequest);
            }
        });
        //----------------------------------------------------------------------------------------
        //DatePicker

        //----------------------------------------------------------------------------------------
        btnDangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Calendar cal = Calendar.getInstance(TimeZone.getDefault());
                int day = cal.get(Calendar.DATE);
                int month = cal.get(Calendar.MONTH);
                int year = cal.get(Calendar.YEAR);



                if(edtMaBHYT.getText().toString().isEmpty()){
                    Toast.makeText(DangKyTaiKhoan.this, "M?? BHYT kh??ng ???????c ????? tr???ng.", Toast.LENGTH_SHORT).show();
                }else
                if(edtMK.getText().toString().isEmpty()){
                    Toast.makeText(DangKyTaiKhoan.this, "M???t kh???u kh??ng ???????c ????? tr???ng.", Toast.LENGTH_SHORT).show();
                }else
                if(edtMK2.getText().toString().isEmpty()){
                    Toast.makeText(DangKyTaiKhoan.this, "X??c nh???n m???t kh???u kh??ng ???????c ????? tr???ng.", Toast.LENGTH_SHORT).show();
                }else
                if(!edtMK.getText().toString().equals(edtMK2.getText().toString())){
                    Toast.makeText(DangKyTaiKhoan.this, "M???t kh???u x??c nh???n kh??ng ????ng", Toast.LENGTH_SHORT).show();
                }else
                if(edtHo.getText().toString().isEmpty()){
                    Toast.makeText(DangKyTaiKhoan.this, "H??? kh??ng ???????c ????? tr???ng.", Toast.LENGTH_SHORT).show();
                }else
                if(edtTen.getText().toString().isEmpty()){
                    Toast.makeText(DangKyTaiKhoan.this, "T??n kh??ng ???????c ????? tr???ng", Toast.LENGTH_SHORT).show();
                }else
                if(edtSDT.getText().toString().isEmpty()){
                    Toast.makeText(DangKyTaiKhoan.this, "Xin vui l??ng nh???p ?????a ch???.", Toast.LENGTH_SHORT).show();
                }else
                if(edtDiaChi.getText().toString().isEmpty()){
                    Toast.makeText(DangKyTaiKhoan.this, "Xin vui l??ng nh???p s??? ??i???n tho???i.", Toast.LENGTH_SHORT).show();
                }else
                if (edtNgay.getText().toString().isEmpty()) {
                        Toast.makeText(DangKyTaiKhoan.this, "Ng??y sinh kh??ng ???????c ????? tr???ng.", Toast.LENGTH_SHORT).show();
                    } else
                        if (edtThang.getText().toString().isEmpty()) {
                        Toast.makeText(DangKyTaiKhoan.this, "Th??ng sinh kh??ng ???????c ????? tr???ng.", Toast.LENGTH_SHORT).show();
                    } else if (edtNam.getText().toString().isEmpty()) {
                        Toast.makeText(DangKyTaiKhoan.this, "N??m sinh kh??ng ???????c ????? tr???ng.", Toast.LENGTH_SHORT).show();
                    }else
                    if (Integer.parseInt(edtNam.getText().toString()) >= year) {
                        Toast.makeText(DangKyTaiKhoan.this, "Ng??y sinh kh??ng th??? l???n h??n ng??y hi???n t???i", Toast.LENGTH_SHORT).show();
                    } else if ((Integer.parseInt(edtThang.getText().toString())+1) >= month && Integer.parseInt(edtNam.getText().toString()) >= year) {
                        Toast.makeText(DangKyTaiKhoan.this, "Ng??y sinh kh??ng th??? l???n h??n ng??y hi???n t???i", Toast.LENGTH_SHORT).show();
                    } else if (Integer.parseInt(edtNgay.getText().toString()) >= day && (Integer.parseInt(edtThang.getText().toString())+1) >= month && Integer.parseInt(edtNam.getText().toString())>= year) {
                        Toast.makeText(DangKyTaiKhoan.this, "Ng??y sinh kh??ng th??? l???n h??n ng??y hi???n t???i", Toast.LENGTH_SHORT).show();
                    } else {
                    PostCaNhan();
                    Intent intent = new Intent(DangKyTaiKhoan.this, MainActivity.class);
                    startActivity(intent);
                }
            }
        });
    }


    void PostCaNhan(){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, post_ca_nhan, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String success = jsonObject.getString("success");
                    if(success.equals("1")){
                        Toast.makeText(DangKyTaiKhoan.this, "????ng k?? th??nh c??ng", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    Toast.makeText(DangKyTaiKhoan.this, "L???i 1:"+e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(DangKyTaiKhoan.this, "L???i 2:"+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("mabhyt", edtMaBHYT.getText().toString());
                params.put("matkhau", edtMK.getText().toString());
                params.put("ho", edtHo.getText().toString());
                params.put("ten", edtTen.getText().toString());
                params.put("sdt", edtSDT.getText().toString());
                params.put("diachi", edtDiaChi.getText().toString());
                params.put("ngaysinh", edtNgay.getText().toString());
                params.put("thangsinh", edtThang.getText().toString());
                params.put("namsinh", edtNam.getText().toString());
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
}
