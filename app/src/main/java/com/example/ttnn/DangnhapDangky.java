package com.example.ttnn;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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

import java.util.HashMap;
import java.util.Map;

public class DangnhapDangky extends AppCompatActivity {

    ImageView imgBack;
    Button btnDangNhap;
    EditText edtMa, edtPass;
    String url_check_dangnhap = "http://192.168.1.5:8070/khambenh/check_dangnhap.php";
    String SHARED_PREF_NAME = "session";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dangnhap_dangky);

        edtMa = findViewById(R.id.DangnhapDangky_edtMaBHYT);
        edtPass = findViewById(R.id.DangnhapDangky_MatKhau);


        //----------------------------------------------------------------------------------------------------------------------------------------------------------
        //Nut back
        imgBack = findViewById(R.id.DangnhapDangky_imgBack);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent imgBack = new Intent(DangnhapDangky.this, MainActivity.class);
                startActivity(imgBack);
            }
        });

        //------------------------------------------------------------------------------------------------------------------------------------------------------------

        //------------------------------------------------------------------------------------------------------------------------------------------------------------
        //Button DangNhap
        btnDangNhap = findViewById(R.id.DangnhapDangky_btnDangNhap);
        btnDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String pas = edtMa.getText().toString();
                SharedPreferences sharedPreferences = getSharedPreferences("MyData", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("name", pas);
                editor.commit();
                editor.apply();
                Login();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        CheckSession();
    }

    private void CheckSession(){
        SessionManagement sessionManagement = new SessionManagement(this);
        int status = sessionManagement.getSession();
        if(status != -1){
            Intent intent = new Intent(DangnhapDangky.this, CaNhan.class);
            startActivity(intent);
        }
        else
        {
            //Do nothing
        }
    }

    void Login(){


        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url_check_dangnhap, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.contains("1"))
                {
                    User user = new User(1, edtMa.getText().toString());
                    SessionManagement sessionManagement = new SessionManagement(DangnhapDangky.this);
                    sessionManagement.saveSession(user);

                    Toast.makeText(DangnhapDangky.this, "Login thành công", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(DangnhapDangky.this, CaNhan.class);
                    startActivity(intent);

                }
                else
                {
                    Toast.makeText(DangnhapDangky.this, "Login thất bại", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(DangnhapDangky.this, "Lỗi 2:"+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("mabhyt", edtMa.getText().toString());
                params.put("matkhau", edtPass.getText().toString());
                return params;
            }
        };

        requestQueue.add(stringRequest);
    }
}