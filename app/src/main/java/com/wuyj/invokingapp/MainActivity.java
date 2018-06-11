package com.wuyj.invokingapp;

import android.content.ComponentName;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView textView = (TextView)findViewById(R.id.text);

        Button button = (Button)findViewById(R.id.pluginButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent();
                intent.setComponent(new ComponentName("com.wuyj.pluginapp", "com.wuyj.pluginapp.MainActivity"));
                intent.putExtra("key1", "value1");
                intent.putExtra("key2", "value2");
                startActivityForResult(intent, 1000);

                textView.setText("我去调用第三方APP");
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String value = data.getStringExtra("key");
        TextView textView = (TextView)findViewById(R.id.text);
        value = textView.getText() + "，获取的数据是：" + value;
        textView.setText(value);
    }
}
