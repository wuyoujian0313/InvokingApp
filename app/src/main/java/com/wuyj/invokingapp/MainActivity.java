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

//                Intent intent = new Intent();
//                intent.setComponent(new ComponentName("com.wuyj.pluginapp", "com.wuyj.pluginapp.MainActivity"));
//                intent.putExtra("key1", "value1");
//                intent.putExtra("key2", "value2");
//                startActivityForResult(intent, 1000);
//                textView.setText("我去调用第三方APP");

                Intent intent = new Intent();
                intent.setComponent(new ComponentName("com.example.neusoft.project", "com.example.neusoft.project.BillSelect"));
                intent.putExtra("user", "AGTF5823");
                intent.putExtra("epartchCode", "0871");
                intent.putExtra("cityCode", "A0AM");
                intent.putExtra("departId", "7E51F");
                intent.putExtra("staffId", "AGTT7499");
                intent.putExtra("departName", "官渡省信息技术中心测试专用工号");
                intent.putExtra("staffName", "赵磊");
                intent.putExtra("pwd", "");
                startActivityForResult(intent, 1000);

                textView.setText("调用第三方app去啰！");
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

    @Override
    public void onBackPressed() {
    }
}
