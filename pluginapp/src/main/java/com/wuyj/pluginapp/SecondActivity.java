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

        Button button = (Button)findViewById(R.id.backButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent();
                setResult(0,intent);
                intent.putExtra("key","第三方的数据");
                finish();
            }
        });


        Button button1 = (Button)findViewById(R.id.backButton1);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent();
                intent.setComponent(new ComponentName("com.wuyj.invokingapp", "com.wuyj.invokingapp.MainActivity"));
                intent.putExtra("key1", "value1");
                intent.putExtra("key2", "value2");
                startActivity(intent);
            }
        });
    }
}
