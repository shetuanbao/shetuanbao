package com.community.shetuanbao.Personal;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.community.shetuanbao.R;
import com.community.shetuanbao.utils.ACache;
import com.community.shetuanbao.utils.F_GetBitmap;
import com.community.shetuanbao.utils.RoundImageView;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import org.json.JSONException;

import java.io.ByteArrayOutputStream;
import java.util.Hashtable;

public class PersonalQrActivity extends Activity{
    private ImageView qr=null;
    private String userId = null;
    RoundImageView touxiang;
    TextView name;
    TextView fanhui;
    Bitmap touxiang2=null;
    byte all_image[]=null;
    String name2 = null;
    TextView baocun;
    private  static final int QR_WIDTH=300;
    private static final int QR_HEIGHT=300;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_personal_qr);

        fanhui=(TextView)findViewById(R.id.erweima_fanhui);
        fanhui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        name=(TextView)findViewById(R.id.erweima_text);
        touxiang=(RoundImageView)findViewById(R.id.QR_1);
        qr=(ImageView)findViewById(R.id.QR);
        baocun=(TextView)findViewById(R.id.baocun);
        try {
            userId = new Integer(ACache.get(PersonalQrActivity.this).getAsJSONObject("user").getInt("userId")).toString();
            final Bitmap bitmap=createBitmap(userId);
            qr.setImageBitmap(bitmap);
            baocun.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                    all_image = stream.toByteArray();
                    F_GetBitmap.setInSDBitmap(all_image, name2+".png");
                    Toast.makeText(PersonalQrActivity.this, "保存成功!!",
                            Toast.LENGTH_LONG).show();
                }
            });

            name2 = ACache.get(PersonalQrActivity.this).getAsJSONObject("user").getString("userName");
            name.setText(name2);

            touxiang2 = ACache.get(PersonalQrActivity.this).getAsBitmap("userphoto");
            if (F_GetBitmap.bitmap != null && !F_GetBitmap.bitmap.isRecycled()) {
                F_GetBitmap.bitmap = null;
            }
            touxiang.setImageBitmap(touxiang2);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private Bitmap createBitmap(String text) {
        Bitmap bitmap = null;
        try {
            Hashtable<EncodeHintType, String> hints = new Hashtable<EncodeHintType, String>();
            hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
            BitMatrix bitMatrix = new QRCodeWriter().encode(text,
                    BarcodeFormat.QR_CODE, QR_WIDTH, QR_HEIGHT, hints);
            int[] pixels = new int[QR_WIDTH * QR_HEIGHT];
            for (int y = 0; y < QR_HEIGHT; y++) {
                for (int x = 0; x < QR_WIDTH; x++) {
                    if (bitMatrix.get(x, y)) {
                        pixels[y * QR_WIDTH + x] = 0xff000000;
                    } else {
                        pixels[y * QR_WIDTH + x] = 0xffffffff;
                    }

                }
            }
            bitmap = Bitmap.createBitmap(QR_WIDTH, QR_HEIGHT,
                    Bitmap.Config.ARGB_8888);
            bitmap.setPixels(pixels, 0, QR_WIDTH, 0, 0, QR_WIDTH, QR_HEIGHT);

        } catch (WriterException e) {
            e.printStackTrace();
        }
        return bitmap;
    }
}
