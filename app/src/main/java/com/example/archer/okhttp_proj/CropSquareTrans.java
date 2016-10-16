package com.example.archer.okhttp_proj;

import android.graphics.Bitmap;

import com.squareup.picasso.Transformation;

/**
 * Created by Archer on 2016/10/15.
 * <p>
 * 描述:
 * <p>
 * 作者
 */

public class CropSquareTrans implements Transformation{
    @Override
    public Bitmap transform(Bitmap source) {
        int size =Math.min(source.getWidth(),source.getHeight());//得到图片的最小值
        int x=(source.getWidth()-size)/2;
        int y=(source.getHeight()-size)/2;
        Bitmap result=Bitmap.createBitmap(source,x,y,size,size);

        if (result!=null){
            source.recycle();
        }
        return result;


    }

    @Override
    public String key() {


        return "square";
    }
}
