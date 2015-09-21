package lico.example.activity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.VolleyError;

import java.util.ArrayList;
import java.util.List;

import lico.example.R;
import lico.example.update.ResponseListener;
import lico.example.update.UploadApi;

/**
 * Created by Administrator on 2015/8/24.
 */
public class UpdateActivity extends Activity{
    Button updateBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update);

        updateBtn = (Button) findViewById(R.id.update_button);

        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImg();
            }
        });
    }


    public void uploadImg(){
        List<Bitmap> mList = new ArrayList<Bitmap>();
        Bitmap bitmap1 = BitmapFactory.decodeResource(getResources(), R.drawable.ic_add_black) ;
        mList.add(bitmap1);
        Bitmap bitmap2 = BitmapFactory.decodeResource(getResources(), R.drawable.img_feed_bottom_1) ;
        mList.add(bitmap2);
        Bitmap bitmap3 = BitmapFactory.decodeResource(getResources(), R.drawable.img_feed_bottom_2) ;
        mList.add(bitmap3);
        Bitmap bitmap4 = BitmapFactory.decodeResource(getResources(), R.drawable.img_feed_center_2) ;
        mList.add(bitmap4);
        Bitmap bitmap5 = BitmapFactory.decodeResource(getResources(), R.drawable.img_toolbar_logo) ;
        mList.add(bitmap5);
        Bitmap bitmap6 = BitmapFactory.decodeResource(getResources(), R.drawable.abc_switch_thumb_material) ;
        mList.add(bitmap6);
        Bitmap bitmap7 = BitmapFactory.decodeResource(getResources(), R.drawable.abc_btn_check_to_on_mtrl_015) ;
        mList.add(bitmap7);
        UploadApi.uploadImg(bitmap7, new ResponseListener<String>() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.v("zgy", "===========VolleyError=========" + error);
                Toast.makeText(UpdateActivity.this, "上传失败", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(String response) {
          //      response = response.substring(response.indexOf("img src="));
          //      response = response.substring(8, response.indexOf("/>"));
                Log.v("zgy", "===========onResponse=========" + response);
                Log.e("", "上传成功:"+response);
                Toast.makeText(UpdateActivity.this, "上传成功", Toast.LENGTH_SHORT).show();
            }
        }) ;
    }
}
