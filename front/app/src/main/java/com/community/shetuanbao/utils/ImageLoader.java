package com.community.shetuanbao.utils;

import android.graphics.Bitmap;

public abstract class ImageLoader {
    private ImageStrategy strategy;
    private byte[] all_image;
    public Bitmap loadImage(String image, ImageStrategy strategy){
        //如果图片没有在sd开中
        if(F_GetBitmap.isEmpty(image)){
            //读取图片
            setStrategy(strategy);
            this.all_image=strategy.getImagebyte(image);
            //把图片存入sd卡
            F_GetBitmap.setInSDBitmap(all_image,image);
            //把图片读取成
            return onLoadImage(all_image,image);
        }
        else{
            return onLoadImage(all_image,image);
        }
    }

    public abstract Bitmap onLoadImage(byte all_image[],String image);

    public void setStrategy(ImageStrategy strategy){
        this.strategy=strategy;
    }
}