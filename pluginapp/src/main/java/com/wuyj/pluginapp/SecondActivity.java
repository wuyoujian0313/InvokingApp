package com.wuyj.pluginapp;

import android.content.ComponentName;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        Intent intent = getIntent();
        if (intent != null) {
            // 说明是融合模式进入的

        }

        Button button = (Button)findViewById(R.id.backButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent();
                // 设置一个返回状态标识：这是受理成功的
                intent.putExtra("back","success");
                // 成功受理
                intent.putExtra("status", "1");
                setResult(0,intent);
                intent.putExtra("key","第三方的数据");
                finish();
            }
        });

        Button button2 = (Button)findViewById(R.id.backButton2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent();
                // 设置一个返回状态标识：这是直接返回到查询页面
                intent.putExtra("back","back");
                // 后面填充自己的其他业务数据
                // 不受理，直接返回
                intent.putExtra("status", "3");

                setResult(0,intent);
                finish();
            }
        });

        Button button1 = (Button)findViewById(R.id.backButton1);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(SecondActivity.this,ThirdActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {

        // 注意这是，手机的硬件返回按钮，也相当于直接返回到查询页面
        Intent intent = new Intent();
        // 设置一个返回状态标识：这是直接返回到查询页面
        intent.putExtra("back","back");
        // 不受理，直接返回
        intent.putExtra("status", "3");
        // 后面填充自己的其他业务数据
        setResult(0,intent);
        finish();
    }
}
