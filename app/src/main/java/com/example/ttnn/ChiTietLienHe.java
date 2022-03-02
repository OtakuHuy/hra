package com.example.ttnn;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.IntentSender;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

public class ChiTietLienHe extends AppCompatActivity {

    ImageView imgBack;
    Button btnKhanCap, btnCovid, btnTongDai, btnBYT;

    int REQUEST_CHECK_SETTINGS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_lien_he);



        btnKhanCap = findViewById(R.id.ChiTietLienHe_btnGoiKhanCap);
        btnCovid = findViewById(R.id.ChiTietLienHe_btnGoiDuongDayNong);
        btnTongDai = findViewById(R.id.ChiTietLienHe_btnGoiTongDai);
        btnBYT = findViewById(R.id.ChiTietLienHe_btnGoiTongDaiBYT);

        imgBack = findViewById(R.id.ChiTietLienHe_imgBack);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ChiTietLienHe.this, MainActivity.class);
                startActivity(intent);
            }
        });

        btnKhanCap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String socapcuu = "";
                createLocationRequest();
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:"+socapcuu));
                startActivity(intent);
                Toast.makeText(ChiTietLienHe.this, "Đang gọi cấp cứu!!!", Toast.LENGTH_SHORT).show();
            }
        });

        btnCovid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String so_hot_line = "";
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:"+so_hot_line));
                startActivity(intent);
                Toast.makeText(ChiTietLienHe.this, "Đang gọi cấp cứu!!!", Toast.LENGTH_SHORT).show();
            }
        });

        btnTongDai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String so_tongdai_benhvien = "";
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:"+so_tongdai_benhvien));
                startActivity(intent);
                Toast.makeText(ChiTietLienHe.this, "Đang gọi cấp cứu!!!", Toast.LENGTH_SHORT).show();
            }
        });

        btnBYT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String so_BYT = "";
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:"+so_BYT));
                startActivity(intent);
                Toast.makeText(ChiTietLienHe.this, "Đang gọi cấp cứu!!!", Toast.LENGTH_SHORT).show();
            }
        });


    }

    protected void createLocationRequest() {


        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(5000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest);

        SettingsClient client = LocationServices.getSettingsClient(this);
        Task<LocationSettingsResponse> task = client.checkLocationSettings(builder.build());



        task.addOnSuccessListener(this, new OnSuccessListener<LocationSettingsResponse>() {
            @Override
            public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                // All location settings are satisfied. The client can initialize
                // location requests here.
                // ...

                Toast.makeText(ChiTietLienHe.this, "Gps already open",
                        Toast.LENGTH_LONG).show();
                Log.d("location settings",locationSettingsResponse.toString());
            }
        });

        task.addOnFailureListener(this, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                if (e instanceof ResolvableApiException) {
                    // Location settings are not satisfied, but this can be fixed
                    // by showing the user a dialog.
                    try {
                        // Show the dialog by calling startResolutionForResult(),
                        // and check the result in onActivityResult().
                        ResolvableApiException resolvable = (ResolvableApiException) e;
                        resolvable.startResolutionForResult(ChiTietLienHe.this,
                                REQUEST_CHECK_SETTINGS);
                    } catch (IntentSender.SendIntentException sendEx) {
                        // Ignore the error.
                    }
                }
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==REQUEST_CHECK_SETTINGS){

            if(resultCode==RESULT_OK){

                Toast.makeText(this, "Gps opened", Toast.LENGTH_SHORT).show();
                //if user allows to open gps
                Log.d("result ok",data.toString());

            }else if(resultCode==RESULT_CANCELED){

                Toast.makeText(this, "refused to open gps",
                        Toast.LENGTH_SHORT).show();
                // in case user back press or refuses to open gps
                Log.d("result cancelled",data.toString());
            }
        }
    }
}