package lico.example.update;

import android.graphics.Bitmap;

import java.io.ByteArrayOutputStream;

/**
 * Created by Administrator on 2015/8/24.
 */
public class FormImage {
    private String mName ;

    private String mFileName ;

    private String mValue ;

    private String mMime ;

    private Bitmap mBitmap ;

    public FormImage(Bitmap mBitmap) {
        this.mBitmap = mBitmap;
    }

    public String getName() {
//        return mName;
        return "uploadimg" ;
    }

    public String getFileName() {
        return "test.png";
    }

    public byte[] getValue() {
        ByteArrayOutputStream bos = new ByteArrayOutputStream() ;
        mBitmap.compress(Bitmap.CompressFormat.JPEG,80,bos) ;
        return bos.toByteArray();
    }

    public String getMime() {
        return "image/png";
    }
}
