package com.community.shetuanbao.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.lang.ref.SoftReference;

public class NetLoader extends ImageLoader {
    @Override
    public Bitmap onLoadImage(byte all_image[], String image){
        InputStream input = null;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 1;
        input = new ByteArrayInputStream(all_image);
        @SuppressWarnings({ "rawtypes", "unchecked" })
        SoftReference softRef = new SoftReference(BitmapFactory.decodeStream(input, null, options));
        return (Bitmap) softRef.get();
    }
}