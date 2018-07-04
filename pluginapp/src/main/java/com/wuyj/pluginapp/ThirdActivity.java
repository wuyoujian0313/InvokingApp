package com.wuyj.pluginapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class ThirdActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        Button browerButton = (Button)findViewById(R.id.Brower);
        browerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                brower();
            }
        });

        Button smsButton = (Button)findViewById(R.id.SMS);
        smsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sms();
            }
        });

        Button selPhotos = (Button)findViewById(R.id.Photo);
        selPhotos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectPhotos();
            }
        });


        Button dialButton = (Button)findViewById(R.id.DIAL);
        dialButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dial();
            }
        });

        Button emailButton = (Button)findViewById(R.id.EMAIL);
        emailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email();
            }
        });
    }

    void dial() {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:10086"));
        startActivity(intent);
    }

    void sms() {
        Uri uri = Uri.parse("smsto:18600746313");
        Intent intent = new Intent(Intent.ACTION_VIEW,uri);
        startActivity(intent);
    }

    void selectPhotos() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");//相片类型
        startActivityForResult(intent, 2000);
    }

    void email() {
        // 必须明确使用mailto前缀来修饰邮件地址,如果使用
// intent.putExtra(Intent.EXTRA_EMAIL, email)，结果将匹配不到任何应用
        Uri uri = Uri.parse("mailto:wuyoujian0313@qq.com");
        String[] email = {"wuyoujian0313@163.com"};
        Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
        intent.putExtra(Intent.EXTRA_CC, email); // 抄送人
        intent.putExtra(Intent.EXTRA_SUBJECT, "这是邮件的主题部分"); // 主题
        intent.putExtra(Intent.EXTRA_TEXT, "这是邮件的正文部分"); // 正文
        startActivity(Intent.createChooser(intent, "请选择邮件类应用"));
    }

    void brower() {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("http://www.baidu.com"));
        startActivity(intent);
    }

    void saveToFile() {
        String data = "date to save";
        FileOutputStream out = null;
        BufferedWriter writer = null;
        try {
            out = openFileOutput("data", Context.MODE_PRIVATE);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(out);
            writer = new BufferedWriter(outputStreamWriter);
            writer.write(data);

            byte[] array = data.getBytes();
            String newoData = new String(array);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {

            try {
                if (writer != null) {
                    writer.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public void onActivityResult(int req, int res, Intent data) {
        switch (req) {
            case 2000:
                if (res == RESULT_OK) {
                    try {
                        if (Build.VERSION.SDK_INT >= 19) {
                            Uri uri = data.getData();
                            Bitmap bit = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri));
                            compressImage(bit);
                        } else {
                            // 4.4以下系统使用这个方法处理图片

                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else{
                }

                break;

            default:
                break;
        }
    }

    public static Bitmap zoomImage(Bitmap bgimage, double newWidth,
                                   double newHeight) {
        // 获取这个图片的宽和高
        float width = bgimage.getWidth();
        float height = bgimage.getHeight();
        // 创建操作图片用的matrix对象
        Matrix matrix = new Matrix();
        // 计算宽高缩放率
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // 缩放图片动作
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap bitmap = Bitmap.createBitmap(bgimage, 0, 0, (int) width,
                (int) height, matrix, true);
        return bitmap;
    }

    private void compressImage(Bitmap bitmap) {
        if (bitmap.getByteCount() > 0) {
            // 缩放图片大小
            Bitmap bmp = zoomImage(bitmap,bitmap.getWidth()*0.3,bitmap.getHeight()*0.3);
            if (bmp.getByteCount() <= 0) return;

            // 压缩图片
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bmp.compress(Bitmap.CompressFormat.JPEG, 20, baos);

            Bitmap result = BitmapFactory.decodeByteArray(baos.toByteArray(), 0, baos.toByteArray().length);

            ImageView imageView = (ImageView)findViewById(R.id.image);
            imageView.setImageBitmap(result);
        }
    }

}
