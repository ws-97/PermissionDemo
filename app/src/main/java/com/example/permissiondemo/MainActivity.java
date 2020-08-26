package com.example.permissiondemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button button,btnOther,btnPermissionKit,btnACPPermission;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        button =findViewById(R.id.btn_applyPermission);
        button.setOnClickListener(this);
        btnOther=findViewById(R.id.btn_applyPermissionByOther);
        btnOther.setOnClickListener(this);
        btnPermissionKit=findViewById(R.id.btn_permissionKit);
        btnPermissionKit.setOnClickListener(this);
        btnACPPermission=findViewById(R.id.btn_ACPPermission);
        btnACPPermission.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_applyPermission:
                call();
            case R.id.btn_applyPermissionByOther:

                startActivity(new Intent(this,SecondActivity.class));
                break;
            case R.id.btn_permissionKit:
                startActivity(new Intent(this,PermissionKitDemoActivity.class));
                break;
            case R.id.btn_ACPPermission:
                startActivity(new Intent(this,ACPActivity.class));
        }
    }
    public void call(){
        //检查App是否有permission.CALL_PhONE的权限
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE)
        != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission
            .CALL_PHONE},1);
        }else{
            callPhone();
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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode==1){
            if(grantResults[0]==PackageManager.PERMISSION_GRANTED){
                callPhone();
            }else{
                if(!ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.CALL_PHONE)){
                    AlertDialog dialog=new AlertDialog.Builder(this)
                            .setMessage("该功能需要访问电话的权限，不开启将无法正常工作！")
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            }).create();
                    dialog.show();
                }

                Toast.makeText(this,"权限被拒绝",Toast.LENGTH_SHORT).show();
            }
//            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}