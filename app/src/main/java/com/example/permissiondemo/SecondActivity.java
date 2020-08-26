package com.example.permissiondemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

@RuntimePermissions
public class SecondActivity extends AppCompatActivity implements View.OnClickListener {

    private Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        button=findViewById(R.id.btn_applyPermission);
        button.setOnClickListener(this);
    }

    private static final String TAG = "SecondActivity";
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_applyPermission:

                Log.e(TAG, "onClick: ");
                call();
                break;

        }
    }
    @NeedsPermission(Manifest.permission.CALL_PHONE)
    void call(){
        Log.e(TAG, "call: " );
        Intent intent=new Intent(Intent.ACTION_CALL);
        Uri data=Uri.parse("tel:"+"10086");
        intent.setData(data);
        try{
            startActivityForResult(intent,0);
        }catch (SecurityException e){
            e.printStackTrace();
        }
    }

    @OnShowRationale(Manifest.permission.CALL_PHONE)
    void showWhy(final PermissionRequest request){
        new AlertDialog.Builder(this)
                .setMessage("您可能需要打电话给客服")
                .setPositiveButton("我知道了", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        request.proceed();//再次执行权限请求
                    }
                })
                .show();
    }
    @OnPermissionDenied(Manifest.permission.CALL_PHONE)
    void showDenied(){
        Toast.makeText(this,"您拒绝了此项权限",Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        SecondActivityPermissionsDispatcher.onRequestPermissionsResult(this,requestCode,grantResults);
    }
}