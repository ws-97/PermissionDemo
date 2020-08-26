package com.example.permissiondemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.permission.kit.OnPermissionListener;
import com.permission.kit.PermissionKit;

import java.util.ArrayList;
import java.util.List;

public class PermissionKitDemoActivity extends AppCompatActivity implements View.OnClickListener {
    Button button,btnOther,btnPermissionKit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permission_kit_demo);
        btnPermissionKit=findViewById(R.id.btn_permissionKit);
        btnPermissionKit.setOnClickListener(this);
    }

    private static final String TAG = "PermissionKitDemoActivi";
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_permissionKit:

                PermissionKit.getInstance().requestPermission(this, 200, new OnPermissionListener() {
                    @Override
                    public void onSuccess(int requestCode, String... permissions) {
                        Log.e(TAG, "onSuccess: " );
                        PermissionKit.getInstance().guideSetting(PermissionKitDemoActivity.this,true,
                                200,null,permissions);
                    }

                    @Override
                    public void onFail(int requestCode, String... permissions) {

                        Log.e(TAG, "onFail: 不同意此项权限，将无法使用" );

                        PermissionKit.getInstance().guideSetting(PermissionKitDemoActivity.this,true,
                                200,null,permissions);
                    }
                }, Manifest.permission.CALL_PHONE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        List<String> unAgreeList=new ArrayList<>();
        for(int i=0;i<grantResults.length;i++){
            if(grantResults[i]!= PackageManager.PERMISSION_GRANTED){
                unAgreeList.add(permissions[i]);
            }
        }
        PermissionKit.getInstance().requestPermissionsResult(requestCode,permissions,unAgreeList);
    }
}