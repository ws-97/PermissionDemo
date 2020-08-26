package com.example.permissiondemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.mylhyl.acp.Acp;
import com.mylhyl.acp.AcpListener;
import com.mylhyl.acp.AcpOptions;

import java.util.List;

public class ACPActivity extends AppCompatActivity  implements View.OnClickListener {

    Button button,btnOther,btnPermissionKit,btnACPPermission;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a_c_p);
        btnACPPermission=findViewById(R.id.btn_ACPPermission);
        btnACPPermission.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_ACPPermission:
                Acp.getInstance(this).request(new AcpOptions.Builder()
                        .setPermissions(Manifest.permission.CALL_PHONE)
                        .build(), new AcpListener() {
                    @Override
                    public void onGranted() {
                        callPhone();
                    }

                    @Override
                    public void onDenied(List<String> permissions) {

                        Toast.makeText(ACPActivity.this,"权限拒绝",Toast.LENGTH_SHORT).show();
                    }
                });
        }
    }
    public void callPhone(){
        Intent intent=new Intent(Intent.ACTION_CALL);
        Uri data=Uri.parse("tel:"+"10086");
        intent.setData(data);
        try{
            startActivity(intent);
        }catch (SecurityException e){
            e.printStackTrace();
        }
    }

}