package com.example.archer.okhttp_proj;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private ImageView imageView;
    private  final static int SUCCESS_STATUS=1;
    private  final static int FAIL_STATUS=0;

    private final  static String TAG=MainActivity.class.getSimpleName();

    private OkHttpClient mClient;

    private Handler mHandler=new Handler(){

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case SUCCESS_STATUS:
//这个时候需要解码图片展示出来
                    byte[] result= (byte[]) msg.obj;
//                    Bitmap bitmap= BitmapFactory.decodeByteArray(result,0,result.length);
Bitmap bitmap=new CropSquareTrans().transform(BitmapFactory.decodeByteArray(result,0,result.length));
                    imageView.setImageBitmap(bitmap);

                    break;
                case FAIL_STATUS:

                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button = (Button) findViewById(R.id.button);
        imageView= (ImageView) findViewById(R.id.imageView);
        mClient=new OkHttpClient();
        String image_path = "http://www.hq.xinhuanet.com/photo/2008-11/12/xinsrc_5831105121112593526741.jpg";
        final Request request=new Request.Builder().url(image_path).build();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mClient.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        Message message=mHandler.obtainMessage();
                        if (response.isSuccessful()){
                            message.what=SUCCESS_STATUS;
                            message.obj=response.body().bytes();

                            mHandler.sendMessage(message);
                        }else {
                            mHandler.sendEmptyMessage(FAIL_STATUS);
                        }
                    }
                });


            }
        });
    }
}
