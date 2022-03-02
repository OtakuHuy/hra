package com.example.ttnn;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
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
import java.util.HashMap;
import java.util.Map;

public class DanhSachBenhAn extends AppCompatActivity {

    String get_danhsachbenhan = "http://192.168.1.5:8070/khambenh/get_danhsachbenhan.php";
    ImageView imgBack;
    ListView lvDanhSachBenhAn;

    Custom_Adapter_Listview_DanhSachBenhAn adapter = null;
    ArrayList<Listview_DanhSachBenhAn_Item> list = new ArrayList<Listview_DanhSachBenhAn_Item>();

    TextView tvTestMa, tvTestNgay, tvTestThang, tvTestNam;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danh_sach_benh_an);


        imgBack = findViewById(R.id.DanhSachBenhAn_imgBack);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DanhSachBenhAn.this, MainActivity.class);
                startActivity(intent);
            }
        });


        lvDanhSachBenhAn = findViewById(R.id.DanhSachBenhAn_ListView);
        LayDanhSachBenhAn();
        adapter = new Custom_Adapter_Listview_DanhSachBenhAn(this, list);
        lvDanhSachBenhAn.setAdapter(adapter);

        lvDanhSachBenhAn.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long id) {
                TextView get = (TextView) view.findViewById(R.id.DanhSachBenhAn_tvMa);
                String values = get.getText().toString();
                Intent intent = new Intent(DanhSachBenhAn.this, XemBenhAn.class);
                intent.putExtra("MaBenhAn", values);
                startActivity(intent);
            }
        });


    }

    void LayDanhSachBenhAn() {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, get_danhsachbenhan,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject object = new JSONObject(response);
                            JSONArray jsonArray = object.getJSONArray("danhsach");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    /*Toast.makeText(DanhSachBenhAn.this, "Ma:"+jsonObject.getString("mabenhan"), Toast.LENGTH_SHORT).show();
                                    Toast.makeText(DanhSachBenhAn.this, "Ngay:"+jsonObject.getString("ngaykham"), Toast.LENGTH_SHORT).show();
                                    Toast.makeText(DanhSachBenhAn.this, "Thang:"+jsonObject.getString("thangkham"), Toast.LENGTH_SHORT).show();
                                    Toast.makeText(DanhSachBenhAn.this, "Nam:"+jsonObject.getString("namkham"), Toast.LENGTH_SHORT).show();*/
                                Listview_DanhSachBenhAn_Item item = new Listview_DanhSachBenhAn_Item(jsonObject.getString("mabenhan"),
                                        jsonObject.getString("ngaykham"), jsonObject.getString("thangkham"),
                                        jsonObject.getString("namkham"));
                                list.add(item);
                            }
                        } catch (JSONException e) {
                            Toast.makeText(DanhSachBenhAn.this, "Lỗi 1:" + e.getMessage(), Toast.LENGTH_SHORT).show();
                            Log.d("Lỗi 2:", e.getMessage());
                        }
                        adapter.notifyDataSetChanged();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(DanhSachBenhAn.this, "Lỗi 2:" + error.getMessage(), Toast.LENGTH_SHORT).show();
                        Log.d("Lỗi 2:", error.getMessage());
                    }
                }) {
            SharedPreferences sharedPreferences = getSharedPreferences("MyData", MODE_PRIVATE);

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("mabhyt", sharedPreferences.getString("name", "0"));
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
}