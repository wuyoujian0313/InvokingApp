package com.wuyj.pluginapp;

import android.content.ComponentName;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

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
}
