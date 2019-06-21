package com.community.shetuanbao.utils;

import android.graphics.Bitmap;

public class LocalLoader extends ImageLoader{
    @Override
    public Bitmap onLoadImage(byte all_image[], String image) {
        if (F_GetBitmap.bitmap != null && !F_GetBitmap.bitmap.isRecycled()) {
            F_GetBitmap.bitmap = null;
        }
        return F_GetBitmap.getSDBitmap(image);
    }
}