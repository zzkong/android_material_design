package lico.example.interactor.impl;

import android.content.Context;
import android.graphics.Bitmap;

import lico.example.listener.AsyncListener;
import lico.example.interactor.HomeInteractor;
import lico.example.bean.UserInfo;
import lico.example.utils.BitmapUtils;

/**
 * Created by Administrator on 2015/10/28.
 */
public class HomeInteractorImpl implements HomeInteractor{

    private AsyncListener<Bitmap> mAsyncListener = null;

    public HomeInteractorImpl(AsyncListener<Bitmap> mAsyncListener) {
        this.mAsyncListener = mAsyncListener;
    }

    @Override
    public UserInfo getUserInfo() {
        UserInfo info = new UserInfo();
        info.name = "Lico";
        info.email = "github_zzkong@163.com";
        info.phone = "137982041**";
        return info;
    }

    @Override
    public void blurBitmap(final Context context, final Bitmap bitmap) {
        new Thread(){
            @Override
            public void run() {
                Bitmap bmap = BitmapUtils.blurBitmap(context, bitmap, 15.5f);
                mAsyncListener.onSuccess(1, bmap);
            }
        }.start();
    }
}
