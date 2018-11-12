package com.wuyj.pluginapp;

import android.Manifest;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = (Button)findViewById(R.id.pluginButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this,SecondActivity.class);
                // 用requestCode=2000调起受理页面，那么在onActivityResult里如果requestCode==2000就是受理页面的返回
                startActivityForResult(intent, 2000);
            }
        });

        // 提示开启相机权限
        //requestPermission(Manifest.permission.CAMERA, 3000);
        requestPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, 3000);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 2000) {
            String back = data.getStringExtra("back");

            if (back.equalsIgnoreCase("back")) {
                // 没有受理直接返回的状态
                // 拿到 data，处理自己的返回逻辑，不要调用finish,不然就回到我们的页面了。
            } else if (back.equalsIgnoreCase("success")) {
                // 成功受理返回的状态：
                setResult(0,data);
                finish();
            } else {
                // 其他异常状态
                finish();
            }
        }
    }

    @Override
    public void onBackPressed() {
        /*
        // 需要重写这个函数，防止用户按硬件的返回按钮返回。要看你们的情况，需不需要支持返回，需要返回的话就是这样的：
        Intent intent = new Intent();
        // 不受理，直接返回
        intent.putExtra("status", "3");
        setResult(0,intent);
        finish();
        */
    }

    // 检查某一项权限是否开启，并提示用户开启
    private void requestPermission(String permission, int requestCode) {
        int checkSelfPermission = ActivityCompat.checkSelfPermission(this, permission);
        if ( Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission == PackageManager.PERMISSION_DENIED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, permission)) {
                // 用户勾选了禁止后不在询问的选择

            } else {
                // 没有勾选，就提示申请权限
                ActivityCompat.requestPermissions(this, new String[]{permission}, requestCode);
            }
        }  else {
            //直接执行相应操作了
            // 执行自己后续的业务逻辑，比如拍照等等；
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 3000) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
               // 已经开启权限
                // 执行自己后续的业务逻辑，比如拍照等等；
            } else {
                // Permission Denied
                // 用户选择了禁止使用
                Toast.makeText(MainActivity.this, "您没有授权该权限，请在设置中打开授权", Toast.LENGTH_SHORT).show();
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
