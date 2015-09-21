package lico.example.update;

import android.graphics.Bitmap;

import com.android.volley.Request;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/8/24.
 */
public class UploadApi {

    /**
     * 上传图片接口
     * @param bitmap 需要上传的图片
     * @param listener 请求回调
     */
    public static void uploadImg(Bitmap bitmap,ResponseListener listener){
        List<FormImage> imageList = new ArrayList<FormImage>() ;
//        for (int i = 0; i < bitmap.size(); i++){
//            imageList.add(new FormImage(bitmap.get(i))) ;
//        }
        imageList.add(new FormImage(bitmap));
        Request request = new PostUploadRequest("http://upload.intsuremax.com/file/Upload",imageList,listener) ;
        VolleyUtil.getRequestQueue().add(request) ;
    }
}
