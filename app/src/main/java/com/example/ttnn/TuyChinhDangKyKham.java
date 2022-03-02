package com.example.ttnn;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
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

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TuyChinhDangKyKham extends AppCompatActivity {

    ImageView imgBack;

    ListView lvEditDangKyKham;

    ArrayList<ListView_EditDangKyKham_Item> Listview_item = new ArrayList<>();
    EditDangKyKham_Adapter adapter;


    String lay_dangkykham = "http://192.168.1.5:8070/khambenh/lay_dangkykham.php";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tuy_chinh_dang_ky_kham);

        imgBack = findViewById(R.id.EditDangKyKham_imgBack);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TuyChinhDangKyKham.this, MainActivity.class);
                startActivity(intent);
            }
        });
        lvEditDangKyKham = findViewById(R.id.EditDangKyKham_ListView);
        lvEditDangKyKham.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TextView get = (TextView) view.findViewById(R.id.EditDangKyKham_ListView_Item_tvNgayDangKy);
                String values = get.getText().toString();
                Intent intent = new Intent(TuyChinhDangKyKham.this, XemDangKyKham.class);
                intent.putExtra("ngay", values);
                startActivity(intent);
            }
        });



        adapter = new EditDangKyKham_Adapter(this, Listview_item);
        lvEditDangKyKham.setAdapter(adapter);

        LayDangKyKham();



        //Công việc còn lại của bên này: tạo xml custom Listview, nạp Listview, code ButtonEdit và Button sửa
    }

    void LayDangKyKham(){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, lay_dangkykham,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject object = new JSONObject(response);
                            JSONArray jsonArray = object.getJSONArray("danhsach");
                            for( int i = 0; i < jsonArray.length(); i++){
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                String luachon = jsonObject.getString("luachon");
                                String ngaykham = jsonObject.getString("ngaykham")+"/"+jsonObject.getString("thangkham")
                                        +"/"+jsonObject.getString("namkham");
                                ListView_EditDangKyKham_Item item = new ListView_EditDangKyKham_Item(luachon, ngaykham);
                                Listview_item.add(item);
                            }
                        } catch (JSONException e) {
                            Toast.makeText(TuyChinhDangKyKham.this, "Lỗi 1:"+e.getMessage(), Toast.LENGTH_SHORT).show();
                            Log.d("Lỗi 2:", e.getMessage());
                        }
                        adapter.notifyDataSetChanged();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(TuyChinhDangKyKham.this, "Lỗi 2:"+error.getMessage(), Toast.LENGTH_SHORT).show();
                        Log.d("Lỗi 2:", error.getMessage());
                    }
                })
        {
            SharedPreferences sharedPreferences = getSharedPreferences("MyData", MODE_PRIVATE);
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                params.put("mabhyt", sharedPreferences.getString("name","0"));
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(TuyChinhDangKyKham.this, MainActivity.class);
        startActivity(intent);
    }
}