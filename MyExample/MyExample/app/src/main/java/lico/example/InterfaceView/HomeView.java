package lico.example.InterfaceView;

import android.graphics.Bitmap;

import lico.example.bean.UserInfo;

/**
 * Created by Administrator on 2015/10/28.
 */
public interface HomeView {

    void initalizeViews(UserInfo userInfo);

    void setHeadBg(Bitmap bitmap);
}
