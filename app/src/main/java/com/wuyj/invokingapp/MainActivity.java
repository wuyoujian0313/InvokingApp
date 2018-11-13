package com.wuyj.invokingapp;

import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

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

                /* 在AndroidManifest.xml给SecondActivity配置intent-filter

                <intent-filter>
                    <action android:name="android.intent.action.VIEW" />
                    <category android:name="android.intent.category.DEFAULT" />
                </intent-filter>
                 */
                /*我们就可以打开处理那个默认的Activity，在这里传入了intent，然后在SencondActivity里的onCreate里通过：
                  Intent intent = getIntent();判断一下是否有值，如果值说明是融合模式调用的；没有就是正常的启动，如果正常的启动也传入了
                  intent那就加上一个标识位判断被调用的来源
                 */

//                Intent intent = new Intent();
//                intent.setComponent(new ComponentName("com.wuyj.pluginapp", "com.wuyj.pluginapp.SecondActivity"));
//                intent.putExtra("key1", "value1");
//                intent.putExtra("key2", "value2");
//                startActivityForResult(intent, 1000);
//                textView.setText("我去调用第三方APP");

//                Intent intent = new Intent();
//                //intent.setComponent(new ComponentName("com.example.neusoft.project", "com.example.neusoft.project.BillSelect"));
//                intent.setComponent(new ComponentName("com.example.neusoftGov.project", "com.example.neusoftGov.project.GovernmentBillSelect"));
//                intent.putExtra("user", "AGTF5823");
//                intent.putExtra("epartchCode", "0871");
//                intent.putExtra("cityCode", "A0AM");
//                intent.putExtra("departId", "7E51F");
//                intent.putExtra("staffId", "AGTT7499");
//                intent.putExtra("departName", "官渡省信息技术中心测试专用工号");
//                intent.putExtra("staffName", "赵磊");
//                intent.putExtra("pwd", "");
//                startActivityForResult(intent, 1000);
//                textView.setText("调用第三方app去啰！");


                PackageManager pm = getPackageManager();
                try {
                    PackageInfo pi = pm.getPackageInfo(getPackageName(), 0);
                    String versionName = pi.versionName;
                    int versioncode = pi.versionCode;
                    Log.d("packageInfo",versionName + ":" + versioncode);
                } catch (Exception e) {

                }


                // 调用浪潮云南集客app
                // 获取当前时间
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// HH:mm:ss
                Date date = new Date(System.currentTimeMillis());
                String nowDate = simpleDateFormat.format(date);
                //组织传入的json字符串
                String staffId = "yuanmin";
                String json = "{\"userName\":\""+staffId+"\",\"timestamp\":\""+nowDate+"\"}";
                //json加密
                String key = "www.asiainfo.com";
                try {
                    String  token = AESEncrypt.encrypt(json,key);
                    // 调用浪潮云南集客app
                    Intent intent = new Intent();
                    intent.setComponent(new ComponentName("com.inspur.SmartApp", "com.inspur.SmartApp.MainActivity"));
                    intent.putExtra("token", token);
                    startActivityForResult(intent, 1001);
                    textView.setText("调用第三方app去啰！");
                } catch (Exception e) {

                }
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1000 && resultCode == 0 && data != null) {
            String value = data.getStringExtra("key");
            TextView textView = (TextView)findViewById(R.id.text);
            value = textView.getText() + "，获取的数据是：" + value;
            textView.setText(value);
        }
    }

    @Override
    public void onBackPressed() {
    }
}
