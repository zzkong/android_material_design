package lico.example.interactor;

import android.content.Context;
import android.graphics.Bitmap;

import lico.example.bean.UserInfo;

/**
 * Created by zwl on 2015/10/28.
 * 这里定义需要哪些方法、具体实现在HomeInteractorImpl中
 */
public interface HomeInteractor {

        UserInfo getUserInfo();

        void blurBitmap(Context context, Bitmap bitmap);
}
