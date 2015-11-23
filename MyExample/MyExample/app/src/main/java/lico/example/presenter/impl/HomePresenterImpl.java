package lico.example.presenter.impl;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import lico.example.listener.AsyncListener;
import lico.example.interactor.HomeInteractor;
import lico.example.InterfaceView.HomeView;
import lico.example.interactor.impl.HomeInteractorImpl;
import lico.example.fragment.BaseFragment;
import lico.example.presenter.HomePresenter;

/**
 * Created by zwl on 2015/10/28.
 */
public class HomePresenterImpl implements HomePresenter, AsyncListener<Bitmap>{

    private Context mContext = null;
    private HomeView mHomeView = null;
    private HomeInteractor mHomeInteractor = null;
    private ImageView mHeadImg;

    public HomePresenterImpl(Context context, HomeView homeView) {
        if(null == homeView){
            throw new IllegalArgumentException("Constructor's parameters must not be Null");
        }
        mContext = context;
        mHomeView = homeView;
        mHomeInteractor = new HomeInteractorImpl(this);
    }

    @Override
    public void initialized() {
        mHomeView.initalizeViews(mHomeInteractor.getUserInfo());
    }

    @Override
    public void blurBitmap(ImageView imageView, Bitmap bitmap) {
        mHeadImg = imageView;
        mHomeInteractor.blurBitmap(mContext, bitmap);
    }

    @Override
    public void setNewRootFragment(BaseFragment baseFragment, int index) {

    }

    @Override
    public void onSuccess(int eventTag, Bitmap data) {
        mHomeView.setHeadBg(data);
    }

    @Override
    public void onError(String msg) {

    }

    @Override
    public void onException(String msg) {

    }
}
